/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *     accountability and the service delivery of the government  organizations.
 *
 *      Copyright (C) 2016  eGovernments Foundation
 *
 *      The updated version of eGov suite of products as by eGovernments Foundation
 *      is available at http://www.egovernments.org
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program. If not, see http://www.gnu.org/licenses/ or
 *      http://www.gnu.org/licenses/gpl.html .
 *
 *      In addition to the terms of the GPL license to be adhered to in using this
 *      program, the following additional terms are to be complied with:
 *
 *          1) All versions of this program, verbatim or modified must carry this
 *             Legal Notice.
 *
 *          2) Any misrepresentation of the origin of the material is prohibited. It
 *             is required that all modified versions of this material be marked in
 *             reasonable ways as different from the original version.
 *
 *          3) This license does not grant any rights to any user of the program
 *             with regards to rights under trademark law for use of the trade names
 *             or trademarks of eGovernments Foundation.
 *
 *    In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.pgr.service.es;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.egov.pgr.utils.constants.PGRConstants.DASHBOARD_GROUPING_CITY;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.egov.eis.entity.Assignment;
import org.egov.eis.service.AssignmentService;
import org.egov.infra.admin.master.entity.User;
import org.egov.infra.admin.master.entity.es.CityIndex;
import org.egov.infra.admin.master.service.CityService;
import org.egov.infra.admin.master.service.DepartmentService;
import org.egov.infra.admin.master.service.es.CityIndexService;
import org.egov.infra.config.mapper.BeanMapperConfiguration;
import org.egov.infra.persistence.entity.enums.UserType;
import org.egov.pgr.entity.Complaint;
import org.egov.pgr.entity.Escalation;
import org.egov.pgr.entity.enums.ComplaintStatus;
import org.egov.pgr.entity.enums.ReceivingMode;
import org.egov.pgr.entity.es.ComplaintDashBoardRequest;
import org.egov.pgr.entity.es.ComplaintDashBoardResponse;
import org.egov.pgr.entity.es.ComplaintIndex;
import org.egov.pgr.entity.es.ComplaintSourceResponse;
import org.egov.pgr.repository.es.ComplaintIndexRepository;
import org.egov.pgr.repository.es.util.ComplaintElasticsearchUtils;
import org.egov.pgr.service.ComplaintService;
import org.egov.pgr.service.EscalationService;
import org.egov.pims.commons.Designation;
import org.egov.pims.commons.Position;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.bucket.filter.Filter;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.tophits.TopHits;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComplaintIndexService {

    @Autowired
    private CityService cityService;

    @Autowired
    private EscalationService escalationService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private Environment environment;

    @Autowired
    private CityIndexService cityIndexService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ComplaintIndexRepository complaintIndexRepository;

    @Autowired
    private BeanMapperConfiguration beanMapperConfiguration;

    @Autowired
    private ComplaintService complaintService;

    public void createComplaintIndex(final Complaint complaint) {
        final Map<String, Object> cityMap = cityService.cityDataAsMap();
        final ComplaintIndex complaintIndex = new ComplaintIndex(cityMap);
        beanMapperConfiguration.map(complaint, complaintIndex);

        if (complaint.getReceivingMode().equals(ReceivingMode.MOBILE)
                && complaint.getCreatedBy().getType().equals(UserType.CITIZEN))
            complaintIndex.setSource(environment.getProperty("complaint.source.citizen.app"));
        if (complaint.getReceivingMode().equals(ReceivingMode.MOBILE)
                && complaint.getCreatedBy().getType().equals(UserType.EMPLOYEE))
            complaintIndex.setSource(environment.getProperty("complaint.source.emp.app"));
        else if (complaint.getReceivingMode().equals(ReceivingMode.WEBSITE)
                && complaint.getCreatedBy().getType().equals(UserType.CITIZEN))
            complaintIndex.setSource(environment.getProperty("complaint.source.portal.citizen"));
        else if (complaint.getReceivingMode().equals(ReceivingMode.WEBSITE)
                && complaint.getCreatedBy().getType().equals(UserType.SYSTEM))
            complaintIndex.setSource(environment.getProperty("complaint.source.portal.anonymous"));
        else if (complaint.getReceivingMode().equals(ReceivingMode.WEBSITE)
                && complaint.getCreatedBy().getType().equals(UserType.EMPLOYEE))
            complaintIndex.setSource(environment.getProperty("complaint.source.emp.website"));
        else if (complaint.getReceivingMode().equals(ReceivingMode.CALL)
                && complaint.getCreatedBy().getType().equals(UserType.EMPLOYEE))
            complaintIndex.setSource(environment.getProperty("complaint.source.website.emp.phone"));
        else if (complaint.getReceivingMode().equals(ReceivingMode.EMAIL)
                && complaint.getCreatedBy().getType().equals(UserType.EMPLOYEE))
            complaintIndex.setSource(environment.getProperty("complaint.source.website.emp.email"));
        else if (complaint.getReceivingMode().equals(ReceivingMode.MANUAL)
                && complaint.getCreatedBy().getType().equals(UserType.EMPLOYEE))
            complaintIndex.setSource(environment.getProperty("complaint.source.website.emp.manual"));

        final Position position = complaint.getAssignee();
        final List<Assignment> assignments = assignmentService.getAssignmentsForPosition(position.getId(), new Date());
        final User assignedUser = !assignments.isEmpty() ? assignments.get(0).getEmployee() : null;
        complaintIndex.setComplaintSLADays(complaint.getComplaintType().getSlaHours());
        complaintIndex.setInitialFunctionaryName(Objects.nonNull(assignedUser) ? assignedUser.getName()
                : "NO ASSIGNMENT" + ":" + position.getDeptDesig().getDesignation().getName());
        complaintIndex.setInitialFunctionarySLADays(getFunctionarySlaDays(complaint));
        complaintIndex.setCurrentFunctionaryName(Objects.nonNull(assignedUser) ? assignedUser.getName()
                : "NO ASSIGNMENT" + ":" + position.getDeptDesig().getDesignation().getName());
        complaintIndex.setCurrentFunctionaryMobileNumber(Objects.nonNull(assignedUser)
                ? assignedUser.getMobileNumber() : EMPTY);
        complaintIndex.setCurrentFunctionarySLADays(getFunctionarySlaDays(complaint));

        complaintIndexRepository.save(complaintIndex);
    }

    public void updateComplaintIndex(final Complaint complaint, final Long approvalPosition,
            final String approvalComment) {
        ComplaintIndex complaintIndex = complaintIndexRepository.findByCrn(complaint.getCrn());
        Date currentDate = new Date();
        final String status = complaintIndex.getComplaintStatusName();
        beanMapperConfiguration.map(complaint, complaintIndex);

        final Position position = complaint.getAssignee();
        final List<Assignment> assignments = assignmentService.getAssignmentsForPosition(position.getId(), currentDate);
        final User assignedUser = !assignments.isEmpty() ? assignments.get(0).getEmployee() : null;
        // If complaint is forwarded
        if (approvalPosition != null && !approvalPosition.equals(Long.valueOf(0))) {
            complaintIndex
                    .setCurrentFunctionaryName(Objects.nonNull(assignedUser) ? assignedUser.getName()
                            : "NO ASSIGNMENT" + ":" + position.getDeptDesig().getDesignation().getName());
            complaintIndex.setCurrentFunctionaryMobileNumber(Objects.nonNull(assignedUser)
                    ? assignedUser.getMobileNumber() : EMPTY);
            complaintIndex.setCurrentFunctionaryAssigneddate(currentDate);
            complaintIndex.setCurrentFunctionarySLADays(getFunctionarySlaDays(complaint));
        }
        complaintIndex.setComplaintFields();
        complaintIndex.setInitialFunctionaryFields();
        complaintIndex.setCurrentFunctionaryFields();
        if (complaintIndex.getComplaintStatusName().equalsIgnoreCase(ComplaintStatus.COMPLETED.toString())
                || complaintIndex.getComplaintStatusName().equalsIgnoreCase(ComplaintStatus.WITHDRAWN.toString())
                || complaintIndex.getComplaintStatusName().equalsIgnoreCase(ComplaintStatus.REJECTED.toString())) {
            complaintIndex.setClosed(true);
            complaintIndex.setComplaintIsClosed("Y");
            complaintIndex.setIfClosed(1);
            complaintIndex.setClosedByFunctionaryName(
                    assignedUser != null ? assignedUser.getName()
                            : "NO ASSIGNMENT" + ":" + position.getDeptDesig().getDesignation().getName());
            final long duration = Math.abs(complaintIndex.getCreatedDate().getTime() - currentDate.getTime())
                    / (24 * 60 * 60 * 1000);
            complaintIndex.setComplaintDuration(duration);
            if (duration < 3)
                complaintIndex.setDurationRange("(<3 days)");
            else if (duration < 7)
                complaintIndex.setDurationRange("(3-7 days)");
            else if (duration < 15)
                complaintIndex.setDurationRange("(8-15 days)");
            else if (duration < 30)
                complaintIndex.setDurationRange("(16-30 days)");
            else
                complaintIndex.setDurationRange("(>30 days)");
        } else {
            complaintIndex.setClosed(false);
            complaintIndex.setComplaintIsClosed("N");
            complaintIndex.setIfClosed(0);
            complaintIndex.setComplaintDuration(0);
            complaintIndex.setDurationRange("");
        }
        // update status related fields in index
        complaintIndex = updateComplaintIndexStatusRelatedFields(complaintIndex);
        // If complaint is re-opened
        if (complaintIndex.getComplaintStatusName().equalsIgnoreCase(ComplaintStatus.REOPENED.toString()) &&
                !status.contains("REOPENED")) {
            complaintIndex.setComplaintReOpenedDate(currentDate);
            complaintIndex.setClosed(false);
            complaintIndex.setComplaintIsClosed("N");
            complaintIndex.setIfClosed(0);
        }
        // If complaint is rejected update the Reason For Rejection with comments
        if (complaintIndex.getComplaintStatusName().equalsIgnoreCase(ComplaintStatus.REJECTED.toString()))
            complaintIndex.setReasonForRejection(approvalComment);

        complaintIndexRepository.save(complaintIndex);
    }

    // This method is used to populate PGR index during complaint escalation
    public void updateComplaintEscalationIndexValues(final Complaint complaint) {
        Date currentDate = new Date();
        ComplaintIndex complaintIndex = complaintIndexRepository.findByCrn(complaint.getCrn());
        beanMapperConfiguration.map(complaint, complaintIndex);

        final Position position = complaint.getAssignee();
        final List<Assignment> assignments = assignmentService.getAssignmentsForPosition(position.getId(), currentDate);
        final User assignedUser = !assignments.isEmpty() ? assignments.get(0).getEmployee() : null;
        // Update current Functionary Complaint index variables
        complaintIndex
                .setCurrentFunctionaryName(Objects.nonNull(assignedUser) ? assignedUser.getName()
                        : "NO ASSIGNMENT" + ":" + position.getDeptDesig().getDesignation().getName());
        complaintIndex.setCurrentFunctionaryMobileNumber(Objects.nonNull(assignedUser)
                ? assignedUser.getMobileNumber() : EMPTY);
        complaintIndex.setCurrentFunctionaryAssigneddate(currentDate);
        complaintIndex.setCurrentFunctionarySLADays(getFunctionarySlaDays(complaint));
        complaintIndex.setComplaintFields();
        complaintIndex.setInitialFunctionaryFields();
        complaintIndex.setCurrentFunctionaryFields();
        int escalationLevel = complaintIndex.getEscalationLevel();
        // For Escalation level1
        if (escalationLevel == 0) {
            complaintIndex.setEscalation1FunctionaryName(
                    Objects.nonNull(assignedUser) ? assignedUser.getName()
                            : "NO ASSIGNMENT" + ":" + position.getDeptDesig().getDesignation().getName());
            complaintIndex.setEscalation1FunctionaryAssigneddate(new Date());
            complaintIndex.setEscalation1FunctionarySLADays(getFunctionarySlaDays(complaint));
            complaintIndex.setEscalation1FunctionaryAgeingFromDue(0);
            complaintIndex.setEscalation1FunctionaryIsSLA("Y");
            complaintIndex.setEscalation1FunctionaryIfSLA(1);
            complaintIndex.setEscalationLevel(++escalationLevel);
        } else if (escalationLevel == 1) {
            // update escalation level 2 fields
            complaintIndex.setEscalation2FunctionaryName(
                    Objects.nonNull(assignedUser) ? assignedUser.getName()
                            : "NO ASSIGNMENT" + ":" + position.getDeptDesig().getDesignation().getName());
            complaintIndex.setEscalation2FunctionaryAssigneddate(new Date());
            complaintIndex.setEscalation2FunctionarySLADays(getFunctionarySlaDays(complaint));
            complaintIndex.setEscalation2FunctionaryAgeingFromDue(0);
            complaintIndex.setEscalation2FunctionaryIsSLA("Y");
            complaintIndex.setEscalation2FunctionaryIfSLA(1);
            complaintIndex.setEscalationLevel(++escalationLevel);
        } else if (escalationLevel == 2) {
            // update escalation level 3 fields
            complaintIndex.setEscalation3FunctionaryName(
                    Objects.nonNull(assignedUser) ? assignedUser.getName()
                            : "NO ASSIGNMENT" + ":" + position.getDeptDesig().getDesignation().getName());
            complaintIndex.setEscalation3FunctionaryAssigneddate(new Date());
            complaintIndex.setEscalation3FunctionarySLADays(getFunctionarySlaDays(complaint));
            complaintIndex.setEscalation3FunctionaryAgeingFromDue(0);
            complaintIndex.setEscalation3FunctionaryIsSLA("Y");
            complaintIndex.setEscalation3FunctionaryIfSLA(1);
            complaintIndex.setEscalationLevel(++escalationLevel);
        }
        complaintIndex.setFirstLevelEscalationFields();
        complaintIndex.setSecondLevelEscalationFields();
        complaintIndex.setThirdLevelEscalationFields();
        // update status related fields in index
        complaintIndex = updateComplaintIndexStatusRelatedFields(complaintIndex);

        complaintIndexRepository.save(complaintIndex);
    }

    @Transactional
    public void updateAllOpenComplaintIndex() {
        final List<Complaint> openComplaints = complaintService.getOpenComplaints();
        List<ComplaintIndex> complaintIndexList = new ArrayList<>();
        for (final Complaint complaint : openComplaints) {
            // fetch the complaint from index and then update the new fields
            ComplaintIndex complaintIndex = complaintIndexRepository.findByCrn(complaint.getCrn());
            if (Objects.nonNull(complaintIndex)) {
                beanMapperConfiguration.map(complaint, complaintIndex);
                complaintIndex.setComplaintFields();
                complaintIndex.setInitialFunctionaryFields();
                complaintIndex.setCurrentFunctionaryFields();
                complaintIndex.setFirstLevelEscalationFields();
                complaintIndex.setSecondLevelEscalationFields();
                complaintIndex.setThirdLevelEscalationFields();
                // update status related fields in index
                complaintIndex = updateComplaintIndexStatusRelatedFields(complaintIndex);
                complaintIndexList.add(complaintIndex);
            }
        }
        complaintIndexRepository.save(complaintIndexList);
    }

    private ComplaintIndex updateComplaintIndexStatusRelatedFields(final ComplaintIndex complaintIndex) {
        if (complaintIndex.getComplaintStatusName().equalsIgnoreCase(ComplaintStatus.PROCESSING.toString())
                || complaintIndex.getComplaintStatusName().equalsIgnoreCase(ComplaintStatus.FORWARDED.toString())
                || complaintIndex.getComplaintStatusName().equalsIgnoreCase(ComplaintStatus.REGISTERED.toString())) {
            complaintIndex.setInProcess(1);
            complaintIndex.setAddressed(0);
            complaintIndex.setRejected(0);
        }
        if (complaintIndex.getComplaintStatusName().equalsIgnoreCase(ComplaintStatus.COMPLETED.toString())
                || complaintIndex.getComplaintStatusName().equalsIgnoreCase(ComplaintStatus.WITHDRAWN.toString())) {
            complaintIndex.setInProcess(0);
            complaintIndex.setAddressed(1);
            complaintIndex.setRejected(0);
        }
        if (complaintIndex.getComplaintStatusName().equalsIgnoreCase(ComplaintStatus.REJECTED.toString())) {
            complaintIndex.setInProcess(0);
            complaintIndex.setAddressed(0);
            complaintIndex.setRejected(1);
        }
        if (complaintIndex.getComplaintStatusName().equalsIgnoreCase(ComplaintStatus.REOPENED.toString())) {
            complaintIndex.setInProcess(1);
            complaintIndex.setAddressed(0);
            complaintIndex.setRejected(0);
            complaintIndex.setReOpened(1);
        }
        return complaintIndex;
    }

    private long getFunctionarySlaDays(final Complaint complaint) {
        final Position position = complaint.getAssignee();
        final Designation designation = position.getDeptDesig().getDesignation();
        final Escalation complaintEscalation = escalationService
                .getEscalationBycomplaintTypeAndDesignation(complaint.getComplaintType().getId(), designation.getId());
        if (complaintEscalation != null)
            return complaintEscalation.getNoOfHrs();
        else
            return 0;
    }

    // These are the methods for dashboard api's written

    public Map<String, Object> getGrievanceReport(final ComplaintDashBoardRequest complaintDashBoardRequest) {
        final String groupByField = ComplaintElasticsearchUtils.getAggregationGroupingField(complaintDashBoardRequest);
        final Map<String, SearchResponse> response = complaintIndexRepository.findAllGrievanceByFilter(
                complaintDashBoardRequest,
                getFilterQuery(complaintDashBoardRequest), groupByField);

        final SearchResponse consolidatedResponse = response.get("consolidatedResponse");
        final SearchResponse tableResponse = response.get("tableResponse");
        final HashMap<String, Object> result = new HashMap<>();
        final List<ComplaintDashBoardResponse> responseDetailsList = new ArrayList<>();
        final ValueCount totalCount = response.get("consolidatedResponse").getAggregations().get("countAggregation");
        result.put("TotalComplaint", totalCount.getValue());
        final Filter filter = consolidatedResponse.getAggregations().get("agg");
        final Avg averageAgeing = filter.getAggregations().get("AgeingInWeeks");
        result.put("AvgAgeingInWeeks", averageAgeing.getValue() / 7);
        Range satisfactionAverage = consolidatedResponse.getAggregations().get("excludeZero");
        final Avg averageSatisfaction = satisfactionAverage.getBuckets().get(0).getAggregations().get("satisfactionAverage");
        result.put("AvgCustomeSatisfactionIndex", averageSatisfaction.getValue());

        if (isNotBlank(complaintDashBoardRequest.getUlbCode())) {
            final CityIndex city = cityIndexService.findOne(complaintDashBoardRequest.getUlbCode());
            result.put("regionName", city.getRegionname());
            result.put("districtName", city.getDistrictname());
            result.put("ulbCode", city.getCitycode());
            result.put("ulbGrade", city.getCitygrade());
            result.put("ulbName", city.getName());
            result.put("domainURL", city.getDomainurl());
        }

        // To get the count of closed and open complaints
        Terms terms = consolidatedResponse.getAggregations().get("closedCount");
        for (final Bucket bucket : terms.getBuckets())
            if (bucket.getKeyAsNumber().intValue() == 1)
                result.put("ClosedComplaints", bucket.getDocCount());
            else
                result.put("OpenComplaints", bucket.getDocCount());

        // To get the count of closed and open complaints
        terms = consolidatedResponse.getAggregations().get("slaCount");
        for (final Bucket bucket : terms.getBuckets())
            if (bucket.getKeyAsNumber().intValue() == 1)
                result.put("WithinSLACount", bucket.getDocCount());
            else
                result.put("OutSideSLACount", bucket.getDocCount());
        final Range currentYearCount = consolidatedResponse.getAggregations().get("currentYear");
        result.put("CYTDComplaint", currentYearCount.getBuckets().get(0).getDocCount());

        final Range todaysCount = consolidatedResponse.getAggregations().get("todaysComplaintCount");
        result.put("todaysComplaintsCount", todaysCount.getBuckets().get(0).getDocCount());

        // For Dynamic results based on grouping fields
        terms = tableResponse.getAggregations().get("groupByField");
        for (final Bucket bucket : terms.getBuckets()) {
            final ComplaintDashBoardResponse responseDetail = populateResponse(complaintDashBoardRequest, bucket, groupByField);
            responseDetail.setTotalComplaintCount(bucket.getDocCount());

            satisfactionAverage = bucket.getAggregations().get("excludeZero");
            final Avg groupByFieldAverageSatisfaction = satisfactionAverage.getBuckets().get(0).getAggregations()
                    .get("groupByFieldSatisfactionAverage");
            if (Double.isNaN(groupByFieldAverageSatisfaction.getValue()))
                responseDetail.setAvgSatisfactionIndex(0);
            else
                responseDetail.setAvgSatisfactionIndex(groupByFieldAverageSatisfaction.getValue());

            final Terms openAndClosedTerms = bucket.getAggregations().get("groupFieldWiseOpenAndClosedCount");
            for (final Bucket closedCountbucket : openAndClosedTerms.getBuckets())
                if (closedCountbucket.getKeyAsNumber().intValue() == 1) {
                    responseDetail.setClosedComplaintCount(closedCountbucket.getDocCount());
                    final Terms slaTerms = closedCountbucket.getAggregations().get("groupByFieldSla");
                    for (final Bucket slaBucket : slaTerms.getBuckets())
                        if (slaBucket.getKeyAsNumber().intValue() == 1)
                            responseDetail.setClosedWithinSLACount(slaBucket.getDocCount());
                        else
                            responseDetail.setClosedOutSideSLACount(slaBucket.getDocCount());
                    // To set Ageing Buckets Result
                    final Range ageingRange = closedCountbucket.getAggregations().get("groupByFieldAgeing");
                    Range.Bucket rangeBucket = ageingRange.getBuckets().get(0);
                    responseDetail.setAgeingGroup1(rangeBucket.getDocCount());
                    rangeBucket = ageingRange.getBuckets().get(1);
                    responseDetail.setAgeingGroup2(rangeBucket.getDocCount());
                    rangeBucket = ageingRange.getBuckets().get(2);
                    responseDetail.setAgeingGroup3(rangeBucket.getDocCount());
                    rangeBucket = ageingRange.getBuckets().get(3);
                    responseDetail.setAgeingGroup4(rangeBucket.getDocCount());
                } else {
                    responseDetail.setOpenComplaintCount(closedCountbucket.getDocCount());
                    final Terms slaTerms = closedCountbucket.getAggregations().get("groupByFieldSla");
                    for (final Bucket slaBucket : slaTerms.getBuckets())
                        if (slaBucket.getKeyAsNumber().intValue() == 1)
                            responseDetail.setOpenWithinSLACount(slaBucket.getDocCount());
                        else
                            responseDetail.setOpenOutSideSLACount(slaBucket.getDocCount());
                }
            responseDetailsList.add(responseDetail);
        }
        result.put("responseDetails", responseDetailsList);

        final List<ComplaintDashBoardResponse> complaintTypeList = new ArrayList<>();
        // For complaintTypeWise result
        terms = tableResponse.getAggregations().get("complaintTypeWise");
        for (final Bucket bucket : terms.getBuckets()) {
            ComplaintDashBoardResponse complaintType = new ComplaintDashBoardResponse();
            complaintType = setDefaultValues(complaintType);
            complaintType.setComplaintTypeName(bucket.getKey().toString());
            complaintType.setTotalComplaintCount(bucket.getDocCount());

            satisfactionAverage = bucket.getAggregations().get("excludeZero");
            final Avg complaintTypeAverageSatisfaction = satisfactionAverage.getBuckets().get(0).getAggregations()
                    .get("complaintTypeSatisfactionAverage");
            if (Double.isNaN(complaintTypeAverageSatisfaction.getValue()))
                complaintType.setAvgSatisfactionIndex(0);
            else
                complaintType.setAvgSatisfactionIndex(complaintTypeAverageSatisfaction.getValue());

            final Terms openAndClosedTerms = bucket.getAggregations().get("complaintTypeWiseOpenAndClosedCount");
            for (final Bucket closedCountbucket : openAndClosedTerms.getBuckets())
                if (closedCountbucket.getKeyAsNumber().intValue() == 1) {
                    complaintType.setClosedComplaintCount(closedCountbucket.getDocCount());
                    final Terms slaTerms = closedCountbucket.getAggregations().get("complaintTypeSla");
                    for (final Bucket slaBucket : slaTerms.getBuckets())
                        if (slaBucket.getKeyAsNumber().intValue() == 1)
                            complaintType.setClosedWithinSLACount(slaBucket.getDocCount());
                        else
                            complaintType.setClosedOutSideSLACount(slaBucket.getDocCount());
                    

                    // To set Ageing Buckets Result
                    final Range ageingRange = closedCountbucket.getAggregations().get("ComplaintTypeAgeing");
                    Range.Bucket rangeBucket = ageingRange.getBuckets().get(0);
                    complaintType.setAgeingGroup1(rangeBucket.getDocCount());
                    rangeBucket = ageingRange.getBuckets().get(1);
                    complaintType.setAgeingGroup2(rangeBucket.getDocCount());
                    rangeBucket = ageingRange.getBuckets().get(2);
                    complaintType.setAgeingGroup3(rangeBucket.getDocCount());
                    rangeBucket = ageingRange.getBuckets().get(3);
                    complaintType.setAgeingGroup4(rangeBucket.getDocCount());
                } else {
                    complaintType.setOpenComplaintCount(closedCountbucket.getDocCount());
                    final Terms slaTerms = closedCountbucket.getAggregations().get("complaintTypeSla");
                    for (final Bucket slaBucket : slaTerms.getBuckets())
                        if (slaBucket.getKeyAsNumber().intValue() == 1)
                            complaintType.setOpenWithinSLACount(slaBucket.getDocCount());
                        else
                            complaintType.setOpenOutSideSLACount(slaBucket.getDocCount());
                }
            complaintTypeList.add(complaintType);
        }
        result.put("complaintTypes", complaintTypeList);

        return result;
    }

    public Map<String, Object> getComplaintTypeReport(final ComplaintDashBoardRequest complaintDashBoardRequest) {
        final String groupByField = ComplaintElasticsearchUtils.getAggregationGroupingField(complaintDashBoardRequest);
        final SearchResponse complaintTypeResponse = complaintIndexRepository.findAllGrievanceByComplaintType(
                complaintDashBoardRequest,
                getFilterQuery(complaintDashBoardRequest),
                groupByField);

        final HashMap<String, Object> result = new HashMap<>();
        if (isNotBlank(complaintDashBoardRequest.getUlbCode())) {
            final CityIndex city = cityIndexService.findOne(complaintDashBoardRequest.getUlbCode());
            result.put("regionName", city.getRegionname());
            result.put("districtName", city.getDistrictname());
            result.put("ulbCode", city.getCitycode());
            result.put("ulbGrade", city.getCitygrade());
            result.put("ulbName", city.getName());
            result.put("domainURL", city.getDomainurl());
        }
        final List<ComplaintDashBoardResponse> responseDetailsList = new ArrayList<>();

        // For Dynamic results based on grouping fields
        final Terms terms = complaintTypeResponse.getAggregations().get("groupByField");
        for (final Bucket bucket : terms.getBuckets()) {

            final ComplaintDashBoardResponse responseDetail = populateResponse(complaintDashBoardRequest, bucket, groupByField);
            responseDetail.setTotalComplaintCount(bucket.getDocCount());

            final Terms openAndClosedTerms = bucket.getAggregations().get("closedComplaintCount");
            for (final Bucket closedCountbucket : openAndClosedTerms.getBuckets())
                if (closedCountbucket.getKeyAsNumber().intValue() == 1)
                    responseDetail.setClosedComplaintCount(closedCountbucket.getDocCount());
                else
                    responseDetail.setOpenComplaintCount(closedCountbucket.getDocCount());
            
            responseDetailsList.add(responseDetail);
        }
        result.put("complaints", responseDetailsList);
        return result;
    }
    
  //This method is used to return all functionary details response
    public Map<String,Object> getAllFunctionaryResponse(final ComplaintDashBoardRequest complaintDashBoardRequest){
    	final SearchResponse complaintTypeResponse = complaintIndexRepository.findByAllFunctionary(complaintDashBoardRequest,
                getFilterQuery(complaintDashBoardRequest));
    	final HashMap<String, Object> result = new HashMap<>();
    	final List<ComplaintDashBoardResponse> responseDetailsList = new ArrayList<>();
    	//Fetch ulblevel aggregation
    	final Terms ulbTerms = complaintTypeResponse.getAggregations().get("ulbwise");
    	for(final Bucket ulbBucket : ulbTerms.getBuckets()){
    		final Terms departmentTerms = ulbBucket.getAggregations().get("departmentwise");
    		//Fetch departmentLevel data in each ulb
    		for(final Bucket departmentBucket : departmentTerms.getBuckets()){
    			final Terms functionaryTerms = departmentBucket.getAggregations().get("functionarywise");
    			//Fetch functionaryLevel data in each department
    			for(final Bucket functionaryBucket : functionaryTerms.getBuckets()){
    				ComplaintDashBoardResponse responseDetail = new ComplaintDashBoardResponse();
    				responseDetail.setTotalComplaintCount(functionaryBucket.getDocCount());
    				responseDetail.setFunctionaryName(functionaryBucket.getKeyAsString());
    				
    				TopHits topHits = functionaryBucket.getAggregations().get("complaintrecord");
    				SearchHit[] hit = topHits.getHits().getHits();
    				responseDetail.setUlbCode(hit[0].field("cityCode").getValue());
    				responseDetail.setUlbName(hit[0].field("cityName").getValue());
    				responseDetail.setDistrictName(hit[0].field("cityDistrictName").getValue());
    				responseDetail.setDepartmentName(hit[0].field("departmentName").getValue());
    				
    				final Terms openAndClosedTerms = functionaryBucket.getAggregations().get("closedComplaintCount");
    	            for (final Bucket closedCountbucket : openAndClosedTerms.getBuckets()){
    	                if (closedCountbucket.getKeyAsNumber().intValue() == 1)
    	                    responseDetail.setClosedComplaintCount(closedCountbucket.getDocCount());
    	                else
    	                    responseDetail.setOpenComplaintCount(closedCountbucket.getDocCount());
    	            }
    	            responseDetailsList.add(responseDetail);
    			}
    		}
    	}
    	result.put("complaints", responseDetailsList);
    	return result;
    }
    
    

    public Map<String, Object> getSourceWiseResponse(final ComplaintDashBoardRequest complaintDashBoardRequest) {
        final String groupByField = ComplaintElasticsearchUtils.getAggregationGroupingField(complaintDashBoardRequest);
        final SearchResponse sourceWiseResponse = complaintIndexRepository.findAllGrievanceBySource(complaintDashBoardRequest,
                getFilterQuery(complaintDashBoardRequest), groupByField);

        final HashMap<String, Object> result = new HashMap<>();
        final List<ComplaintSourceResponse> responseDetailsList = new ArrayList<>();

        if (isNotBlank(complaintDashBoardRequest.getUlbCode())) {
            final CityIndex city = cityIndexService.findOne(complaintDashBoardRequest.getUlbCode());
            result.put("regionName", city.getRegionname());
            result.put("districtName", city.getDistrictname());
            result.put("ulbCode", city.getCitycode());
            result.put("ulbGrade", city.getCitygrade());
            result.put("ulbName", city.getName());
            result.put("domainURL", city.getDomainurl());
        }
        Terms terms = sourceWiseResponse.getAggregations().get("groupByField");
        for (final Bucket bucket : terms.getBuckets()) {
            final ComplaintSourceResponse complaintSouce = new ComplaintSourceResponse();
            CityIndex city;
            if ("cityRegionName".equals(groupByField))
                complaintSouce.setRegionName(bucket.getKeyAsString());
            if ("cityGrade".equals(groupByField))
                complaintSouce.setUlbGrade(bucket.getKeyAsString());
            if ("cityDistrictCode".equals(groupByField)) {
                city = cityIndexService.findByDistrictCode(bucket.getKeyAsString());
                complaintSouce.setDistrictName(city.getDistrictname());
            }
            if ("cityCode".equals(groupByField)) {
                city = cityIndexService.findByCitycode(bucket.getKeyAsString());
                complaintSouce.setDistrictName(city.getDistrictname());
                complaintSouce.setUlbName(city.getName());
                complaintSouce.setUlbGrade(city.getCitygrade());
                complaintSouce.setUlbCode(city.getCitycode());
                complaintSouce.setDomainURL(city.getDomainurl());
            }
            if ("departmentName".equals(groupByField))
                complaintSouce.setDepartmentName(bucket.getKeyAsString());
            if ("wardName".equals(groupByField))
                complaintSouce.setWardName(bucket.getKeyAsString());
            if ("localityName".equals(groupByField))
                complaintSouce.setLocalityName(bucket.getKeyAsString());
            if ("currentFunctionaryName".equals(groupByField)) {
                complaintSouce.setFunctionaryName(bucket.getKeyAsString());
                final String mobileNumber = complaintIndexRepository.getFunctionryMobileNumber(bucket.getKeyAsString());
                complaintSouce.setFunctionaryMobileNumber(mobileNumber);
            }
            final List<HashMap<String, Long>> list = new ArrayList<>();
            final Terms sourceTerms = bucket.getAggregations().get("groupByFieldSource");
            for (final Bucket sourceBucket : sourceTerms.getBuckets()) {
                final HashMap<String, Long> sourceMap = new HashMap<>();
                sourceMap.put(sourceBucket.getKeyAsString(), sourceBucket.getDocCount());
                list.add(sourceMap);
            }
            complaintSouce.setSourceList(list);
            responseDetailsList.add(complaintSouce);
        }
        result.put("responseDetails", responseDetailsList);

        final List<ComplaintSourceResponse> complaintTypeList = new ArrayList<>();
        terms = sourceWiseResponse.getAggregations().get("complaintTypeWise");
        for (final Bucket bucket : terms.getBuckets()) {
            final ComplaintSourceResponse complaintSouce = new ComplaintSourceResponse();
            complaintSouce.setComplaintTypeName(bucket.getKey().toString());
            final List<HashMap<String, Long>> list = new ArrayList<>();
            final Terms sourceTerms = bucket.getAggregations().get("complaintTypeWiseSource");
            for (final Bucket sourceBucket : sourceTerms.getBuckets()) {
                final HashMap<String, Long> sourceMap = new HashMap<>();
                sourceMap.put(sourceBucket.getKeyAsString(), sourceBucket.getDocCount());
                list.add(sourceMap);
            }
            complaintSouce.setSourceList(list);
            complaintTypeList.add(complaintSouce);
        }
        result.put("complaintTypeWise", complaintTypeList);
        return result;

    }
    
    public Iterable<ComplaintIndex> searchComplaintIndex(final BoolQueryBuilder searchQuery) {
        return complaintIndexRepository.search(searchQuery);
    }

    private BoolQueryBuilder getFilterQuery(final ComplaintDashBoardRequest complaintDashBoardRequest) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().filter(termQuery("registered", 1));
        if (isNotBlank(complaintDashBoardRequest.getRegionName()))
            boolQuery = boolQuery.filter(matchQuery("cityRegionName", complaintDashBoardRequest.getRegionName()));
        if (isNotBlank(complaintDashBoardRequest.getUlbGrade()))
            boolQuery = boolQuery.filter(matchQuery("cityGrade", complaintDashBoardRequest.getUlbGrade()));
        if (isNotBlank(complaintDashBoardRequest.getDistrictName()))
            boolQuery = boolQuery
                    .filter(matchQuery("cityDistrictName", complaintDashBoardRequest.getDistrictName()));
        if (isNotBlank(complaintDashBoardRequest.getUlbCode()))
            boolQuery = boolQuery.filter(matchQuery("cityCode", complaintDashBoardRequest.getUlbCode()));
        if (isNotBlank(complaintDashBoardRequest.getWardNo()))
            boolQuery = boolQuery.filter(matchQuery("wardNo", complaintDashBoardRequest.getWardNo()));
        if (isNotBlank(complaintDashBoardRequest.getDepartmentCode()))
            boolQuery = boolQuery
                    .filter(matchQuery("departmentCode", complaintDashBoardRequest.getDepartmentCode()));
        if (isNotBlank(complaintDashBoardRequest.getFromDate()) &&
                isNotBlank(complaintDashBoardRequest.getToDate()))
            boolQuery = boolQuery.must(rangeQuery("createdDate")
                    .from(complaintDashBoardRequest.getFromDate())
                    .to(complaintDashBoardRequest.getToDate()));
        if (isNotBlank(complaintDashBoardRequest.getComplaintTypeCode()))
            boolQuery = boolQuery.filter(matchQuery("complaintTypeCode",
                    complaintDashBoardRequest.getComplaintTypeCode()));

        return boolQuery;
    }

    private ComplaintDashBoardResponse populateResponse(final ComplaintDashBoardRequest complaintDashBoardRequest,
            final Bucket bucket,
            final String groupByField) {
        ComplaintDashBoardResponse responseDetail = new ComplaintDashBoardResponse();

        responseDetail = setDefaultValues(responseDetail);

        CityIndex city;

        if ("cityRegionName".equals(groupByField))
            responseDetail.setRegionName(bucket.getKeyAsString());
        if ("cityGrade".equals(groupByField))
            responseDetail.setUlbGrade(bucket.getKeyAsString());
        if ("cityCode".equals(groupByField) && DASHBOARD_GROUPING_CITY.equalsIgnoreCase(complaintDashBoardRequest.getType())) {
            city = cityIndexService.findOne(bucket.getKeyAsString());
            responseDetail.setUlbName(city.getName());
            responseDetail.setUlbCode(city.getCitycode());
        }

        if ("cityDistrictCode".equals(groupByField)) {
            city = cityIndexService.findByDistrictCode(bucket.getKeyAsString());
            responseDetail.setDistrictName(city.getDistrictname());
        }
        // When UlbGrade is selected group by Ulb
        if ("cityCode".equals(groupByField) &&
                !complaintDashBoardRequest.getType().equals(DASHBOARD_GROUPING_CITY)) {
            city = cityIndexService.findOne(bucket.getKeyAsString());
            responseDetail.setDistrictName(city.getDistrictname());
            responseDetail.setUlbName(city.getName());
            responseDetail.setUlbGrade(city.getCitygrade());
            responseDetail.setUlbCode(city.getCitycode());
            responseDetail.setDomainURL(city.getDomainurl());
        }
        // When UlbCode is passed without type group by department else by type
        if ("departmentName".equals(groupByField))
            responseDetail.setDepartmentName(bucket.getKeyAsString());
        if ("wardName".equals(groupByField))
            responseDetail.setWardName(bucket.getKeyAsString());
        if ("localityName".equals(groupByField))
            responseDetail.setLocalityName(bucket.getKeyAsString());
        if ("currentFunctionaryName".equals(groupByField)) {
            responseDetail.setFunctionaryName(bucket.getKeyAsString());
            final String mobileNumber = complaintIndexRepository.getFunctionryMobileNumber(bucket.getKeyAsString());
            responseDetail.setFunctionaryMobileNumber(mobileNumber);
        }
        return responseDetail;
    }

    private ComplaintDashBoardResponse setDefaultValues(final ComplaintDashBoardResponse response) {
        response.setDistrictName(EMPTY);
        response.setUlbName(EMPTY);
        response.setWardName(EMPTY);
        response.setDepartmentName(EMPTY);
        response.setFunctionaryName(EMPTY);
        response.setLocalityName(EMPTY);
        response.setComplaintTypeName(EMPTY);
        response.setUlbGrade(EMPTY);
        response.setUlbCode(EMPTY);
        response.setDomainURL(EMPTY);

        return response;
    }

    public List<String> getSourceNameList() {
        return Arrays.asList(environment.getProperty("all.complaint.sources").split(","));
    }
}
/*
 * eGov suite of products aim to improve the internal efficiency,transparency,
 *    accountability and the service delivery of the government  organizations.
 *
 *     Copyright (C) <2015>  eGovernments Foundation
 *
 *     The updated version of eGov suite of products as by eGovernments Foundation
 *     is available at http://www.egovernments.org
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program. If not, see http://www.gnu.org/licenses/ or
 *     http://www.gnu.org/licenses/gpl.html .
 *
 *     In addition to the terms of the GPL license to be adhered to in using this
 *     program, the following additional terms are to be complied with:
 *
 *         1) All versions of this program, verbatim or modified must carry this
 *            Legal Notice.
 *
 *         2) Any misrepresentation of the origin of the material is prohibited. It
 *            is required that all modified versions of this material be marked in
 *            reasonable ways as different from the original version.
 *
 *         3) This license does not grant any rights to any user of the program
 *            with regards to rights under trademark law for use of the trade names
 *            or trademarks of eGovernments Foundation.
 *
 *   In case of any queries, you can reach eGovernments Foundation at contact@egovernments.org.
 */

package org.egov.pgr.web.controller.reports;

import org.apache.commons.io.IOUtils;
import org.egov.pgr.service.ComplaintTypeService;
import org.egov.pgr.service.reports.ComplaintTypeWiseReportService;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.egov.infra.web.utils.WebUtils.toJSON;

@Controller
@RequestMapping(value = {"/report", "/public/report"})
public class ComplaintTypeWiseReportController {

    private final ComplaintTypeWiseReportService complaintTypeReportService;

    @Autowired
    public ComplaintTypeWiseReportController(final ComplaintTypeWiseReportService complaintTypeReportService,
            final ComplaintTypeService complaintTypeService) {
        this.complaintTypeReportService = complaintTypeReportService;
    }

    @ModelAttribute
    public void getReportHelper(final Model model) {
        model.addAttribute("reportHelper", new ReportHelper());

    }

    @RequestMapping(method = RequestMethod.GET, value = "/complaintTypeReport")
    public String searchAgeingReportByBoundaryForm(final Model model) {
        return "complaintTypeReport-search";
    }

    @ExceptionHandler(Exception.class)
    @RequestMapping(value = "/complaintTypeReport/resultList-update", method = RequestMethod.GET)
    public @ResponseBody void springPaginationDataTablesUpdate(@RequestParam final String complaintType,
            @RequestParam final String complaintTypeWithStatus, @RequestParam final String status,
            @RequestParam final String complaintDateType, @RequestParam final DateTime fromDate,
            @RequestParam final DateTime toDate, final HttpServletRequest request, final HttpServletResponse response)
                    throws IOException {
        SQLQuery complaintTypeReportQuery = null;
        List<DrillDownReportResult> complaintTypeReportResult = null;
        String result = null;
        if (complaintTypeWithStatus != null && status != null && !"".equals(complaintTypeWithStatus)
                && !"".equals(status)) {

            complaintTypeReportQuery = complaintTypeReportService.getComplaintTypeWiseReportQuery(fromDate,
                    toDate, complaintDateType, complaintTypeWithStatus, status);
            complaintTypeReportQuery.setResultTransformer(Transformers.aliasToBean(DrillDownReportResult.class));
            complaintTypeReportResult = complaintTypeReportQuery.list();
            result = new StringBuilder("{ \"data\":").append(toJSON(complaintTypeReportResult, DrillDownReportResult.class,
                     DrillDownReportWithcompTypeAdaptor.class)).append("}")
                    .toString();

        } else {
            complaintTypeReportQuery = complaintTypeReportService.getComplaintTypeWiseReportQuery(fromDate,
                    toDate, complaintType, complaintDateType);
            complaintTypeReportQuery.setResultTransformer(Transformers.aliasToBean(DrillDownReportResult.class));
            complaintTypeReportResult = complaintTypeReportQuery.list();
            result = new StringBuilder("{ \"data\":").append(toJSON(complaintTypeReportResult, DrillDownReportResult.class,
                     DrillDownReportHelperAdaptor.class)).append("}")
                    .toString();
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        IOUtils.write(result, response.getWriter());

    }
}

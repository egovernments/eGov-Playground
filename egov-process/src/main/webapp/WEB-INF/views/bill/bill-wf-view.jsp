<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>
<form:form role="form" action="/egov-process/bill/update"
		   modelAttribute="bill" id="billform"
		   cssClass="form-horizontal form-groups-bordered"
>
<div class="main-content">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title">Bill</div>
				</div>
				<div class="panel-body custom">
					<div class="row add-border">
						<div class="col-xs-3 add-margin">
							<spring:message code="lbl.fund" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							${bill.fund.name}</div>
						<div class="col-xs-3 add-margin">
							<spring:message code="lbl.department" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							${bill.department.name}</div>
					</div>
					<div class="row add-border">
						<div class="col-xs-3 add-margin">
							<spring:message code="lbl.billnumber" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							${bill.billNumber}</div>
						<div class="col-xs-3 add-margin">
							<spring:message code="lbl.billdate" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							<fmt:formatDate pattern="MM/dd/yyyyy" value="${bill.billDate}" />
						</div>
					</div>
					<div class="row add-border">
						<div class="col-xs-3 add-margin">
							<spring:message code="lbl.billamount" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							${bill.billAmount}</div>
						<div class="col-xs-3 add-margin">
							<spring:message code="lbl.billtype" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							${bill.billType}</div>
					</div>
					 
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" name="bill" value="${bill.id}" />
	<input type="hidden" name="taskId" value="${bill.taskId}" />
	<div class="panel-heading">
		<div class="panel-title">Workflow details</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label text-right">Message</label>
		<div class="col-sm-3 add-margin">
			<form:input path="message"
						class="form-control text-left " />

		</div>
	</div>
	<div class="form-group">
		<div class="text-center">
			<button type='submit' class='btn btn-primary' id="buttonSubmit">
				<spring:message code='lbl.update' />
			</button>
			<a href='javascript:void(0)' class='btn btn-default'
			   onclick='self.close()'><spring:message code='lbl.close' /></a>
		</div>
	</div>
	</div>
	</form:form>
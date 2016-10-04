<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>
<form:form role="form" action="search" modelAttribute="inbox"
	id="inboxsearchform" cssClass="form-horizontal form-groups-bordered"
	enctype="multipart/form-data">
	
		
	
</form:form>
<div class="row display-hide report-section">
	<div class="col-md-12 table-header text-left">Inbox</div>
	<div class="col-md-12 form-group report-table-container">
		<table class="table table-bordered table-hover multiheadertbl"
			id="resultTable">
			<thead>
				<tr>
					<th><spring:message code="lbl.taskid" /></th>
					<th><spring:message code="lbl.sender" /></th>
					<th><spring:message code="lbl.natureofwork" /></th>
					<th><spring:message code="lbl.details" /></th>
					<th><spring:message code="lbl.link" /></th>
				</tr>
			</thead>
		</table>
	</div>
</div>


<link rel="stylesheet"
	href="<c:url value='/resources/global/css/bootstrap/bootstrap-datepicker.css' context='/egov-process'/>" />
<script type="text/javascript"
	src="<c:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.min.js' context='/egov-process'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/global/js/jquery/plugins/datatables/dataTables.bootstrap.js' context='/egov-process'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/global/js/jquery/plugins/datatables/dataTables.tableTools.js' context='/egov-process'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/global/js/jquery/plugins/datatables/TableTools.min.js' context='/egov-process'/>"></script>

<script type="text/javascript"
	src="<c:url value='/resources/global/js/bootstrap/typeahead.bundle.js' context='/egov-process'/>"></script>
<script
	src="<c:url value='/resources/global/js/jquery/plugins/jquery.inputmask.bundle.min.js' context='/egov-process'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/global/js/jquery/plugins/jquery.validate.min.js' context='/egov-process'/>"></script>
<script
	src="<c:url value='/resources/global/js/bootstrap/bootstrap-datepicker.js' context='/egov-process'/>"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<c:url value='/resources/app/js/inboxHelper.js'/>"></script>
<script>
	callAjaxSearch();
</script>
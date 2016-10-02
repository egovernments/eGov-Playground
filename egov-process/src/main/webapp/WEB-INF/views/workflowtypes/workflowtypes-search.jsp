<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>
<form:form role="form" action="search" modelAttribute="workflowTypes" id="workflowTypessearchform" cssClass="form-horizontal form-groups-bordered" enctype="multipart/form-data">
<div class="main-content"><div class="row"><div class="col-md-12"><div class="panel panel-primary" data-collapsed="0"><div class="panel-heading"><div class="panel-title">SearchWorkflowTypes</div></div><div class="panel-body"><div class="form-group">
<label class="col-sm-3 control-label text-right"><spring:message code="lbl.type" />
</label><div class="col-sm-3 add-margin">
<form:input  path="type" class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="120"  />
<form:errors path="type" cssClass="error-msg" /></div><label class="col-sm-3 control-label text-right"><spring:message code="lbl.classname" />
</label><div class="col-sm-3 add-margin">
<form:input  path="className" class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="120"  />
<form:errors path="className" cssClass="error-msg" /></div></div>
<div class="form-group">
<label class="col-sm-3 control-label text-right"><spring:message code="lbl.businesskey" />
</label><div class="col-sm-3 add-margin">
<form:input  path="businessKey" class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="50"  />
<form:errors path="businessKey" cssClass="error-msg" /></div><input type="hidden" id="mode" name="mode" value="${mode}"/><div class="form-group"><div class="text-center"><button type='button' class='btn btn-primary' id="btnsearch"><spring:message code='lbl.search'/></button><a href='javascript:void(0)' class='btn btn-default' onclick='self.close()'><spring:message code='lbl.close' /></a></div></div></div></div></div></div></div></form:form><div class="row display-hide report-section">
<div class="col-md-12 table-header text-left">WorkflowTypes Search Result</div>
<div class="col-md-12 form-group report-table-container">
<table class="table table-bordered table-hover multiheadertbl" id="resultTable">
<thead> <tr> <th><spring:message code="lbl.type" /></th><th><spring:message code="lbl.classname" /></th><th><spring:message code="lbl.servicename" /></th><th><spring:message code="lbl.businesskey" /></th><th><spring:message code="lbl.link" /></th><th><spring:message code="lbl.displayname" /></th><th><spring:message code="lbl.enabled" /></th></tr></thead>   </table> </div></div> <script> 
$('#btnsearch').click(function(e){
 if($('form').valid()){
 }else{
 e.preventDefault();
 }  });
</script>
<link rel="stylesheet" href="<c:url value='/resources/global/css/font-icons/entypo/css/entypo.css' context='/egov-process'/>"/><link rel="stylesheet" href="<c:url value='/resources/global/css/bootstrap/bootstrap-datepicker.css' context='/egov-process'/>"/><script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.min.js' context='/egov-process'/>"></script><script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/dataTables.bootstrap.js' context='/egov-process'/>"></script><script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/dataTables.tableTools.js' context='/egov-process'/>"></script><script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/TableTools.min.js' context='/egov-process'/>"></script><script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.columnFilter.js' context='/egov-process'/>"></script><script type="text/javascript" src="<c:url value='/resources/global/js/bootstrap/typeahead.bundle.js' context='/egov-process'/>"></script><script src="<c:url value='/resources/global/js/jquery/plugins/jquery.inputmask.bundle.min.js' context='/egov-process'/>"></script><script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/jquery.validate.min.js' context='/egov-process'/>"></script><script  src="<c:url value='/resources/global/js/bootstrap/bootstrap-datepicker.js' context='/egov-process'/>"  type="text/javascript"></script><script type="text/javascript" src="<c:url value='/resources/app/js/workflowTypesHelper.js'/>"></script> 
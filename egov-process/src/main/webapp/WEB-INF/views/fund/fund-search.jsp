<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>
<form:form role="form" action="search" modelAttribute="fund" id="fundsearchform" cssClass="form-horizontal form-groups-bordered" >
<div class="main-content"><div class="row"><div class="col-md-12"><div class="panel panel-primary" data-collapsed="0"><div class="panel-heading"><div class="panel-title">SearchFund</div></div><div class="panel-body"><div class="form-group">
<label class="col-sm-3 control-label text-right"><spring:message code="lbl.name" />
</label><div class="col-sm-3 add-margin">
<form:input  path="name" class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="20"  />
<form:errors path="name" cssClass="error-msg" /></div><label class="col-sm-3 control-label text-right"><spring:message code="lbl.code" />
</label><div class="col-sm-3 add-margin">
<form:input  path="code" class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="20"  />
<form:errors path="code" cssClass="error-msg" /></div></div>
<div class="form-group">
<label class="col-sm-3 control-label text-right"><spring:message code="lbl.active" />
</label><div class="col-sm-3 add-margin">
 <form:checkbox path="active" /><form:errors path="active" cssClass="error-msg" /></div><input type="hidden" id="mode" name="mode" value="${mode}"/><div class="form-group"><div class="text-center"><button type='button' class='btn btn-primary' id="btnsearch"><spring:message code='lbl.search'/></button><a href='javascript:void(0)' class='btn btn-default' onclick='self.close()'><spring:message code='lbl.close' /></a></div></div></div></div></div></div></div></form:form><div class="row display-hide report-section">
<div class="col-md-12 table-header text-left">Fund Search Result</div>
<div class="col-md-12 form-group report-table-container">
<table class="table table-bordered table-hover multiheadertbl" id="resultTable">
<thead> <tr> <th><spring:message code="lbl.name" /></th><th><spring:message code="lbl.code" /></th><th><spring:message code="lbl.active" /></th></tr></thead>   </table> </div></div> <script> 
$('#btnsearch').click(function(e){
 if($('form').valid()){
 }else{
 e.preventDefault();
 }  });
</script>
<link rel="stylesheet" href="<c:url value='/resources/global/css/font-icons/entypo/css/entypo.css' context='/egov-process'/>"/><link rel="stylesheet" href="<c:url value='/resources/global/css/bootstrap/bootstrap-datepicker.css' context='/egov-process'/>"/><script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.min.js' context='/egov-process'/>"></script><script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/dataTables.bootstrap.js' context='/egov-process'/>"></script><script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/dataTables.tableTools.js' context='/egov-process'/>"></script><script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/TableTools.min.js' context='/egov-process'/>"></script><script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/datatables/jquery.dataTables.columnFilter.js' context='/egov-process'/>"></script><script type="text/javascript" src="<c:url value='/resources/global/js/bootstrap/typeahead.bundle.js' context='/egov-process'/>"></script><script src="<c:url value='/resources/global/js/jquery/plugins/jquery.inputmask.bundle.min.js' context='/egov-process'/>"></script><script type="text/javascript" src="<c:url value='/resources/global/js/jquery/plugins/jquery.validate.min.js' context='/egov-process'/>"></script><script  src="<c:url value='/resources/global/js/bootstrap/bootstrap-datepicker.js' context='/egov-process'/>"  type="text/javascript"></script><script type="text/javascript" src="<c:url value='/resources/app/js/fundHelper.js'/>"></script> 
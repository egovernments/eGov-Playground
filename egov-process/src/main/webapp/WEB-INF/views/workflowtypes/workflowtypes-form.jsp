<div class="main-content"><div class="row"><div class="col-md-12"><div class="panel panel-primary" data-collapsed="0"><div class="panel-heading"><div class="panel-title">WorkflowTypes</div></div><div class="panel-body"><div class="form-group">
<label class="col-sm-3 control-label text-right"><spring:message code="lbl.type" />
</label><div class="col-sm-3 add-margin">
<form:input  path="type" class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="120"  />
<form:errors path="type" cssClass="error-msg" /></div><label class="col-sm-3 control-label text-right"><spring:message code="lbl.classname" />
</label><div class="col-sm-3 add-margin">
<form:input  path="className" class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="120"  />
<form:errors path="className" cssClass="error-msg" /></div></div>
<div class="form-group">
<label class="col-sm-3 control-label text-right"><spring:message code="lbl.servicename" />
</label><div class="col-sm-3 add-margin">
<form:input  path="serviceName" class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="120"  />
<form:errors path="serviceName" cssClass="error-msg" /></div><label class="col-sm-3 control-label text-right"><spring:message code="lbl.businesskey" />
</label><div class="col-sm-3 add-margin">
<form:input  path="businessKey" class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="50"  />
<form:errors path="businessKey" cssClass="error-msg" /></div></div>
<div class="form-group">
<label class="col-sm-3 control-label text-right"><spring:message code="lbl.link" />
</label><div class="col-sm-3 add-margin">
<form:input  path="link" class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="0"  />
<form:errors path="link" cssClass="error-msg" /></div><label class="col-sm-3 control-label text-right"><spring:message code="lbl.displayname" />
</label><div class="col-sm-3 add-margin">
<form:input  path="displayName" class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="0"  />
<form:errors path="displayName" cssClass="error-msg" /></div></div>
<div class="form-group">
<label class="col-sm-3 control-label text-right"><spring:message code="lbl.enabled" />
</label><div class="col-sm-3 add-margin">
 <form:checkbox path="enabled" /><form:errors path="enabled" cssClass="error-msg" /></div><label class="col-sm-3 control-label text-right"><spring:message code="lbl.grouped" />
</label><div class="col-sm-3 add-margin">
 <form:checkbox path="grouped" /><form:errors path="grouped" cssClass="error-msg" /></div></div>
 <input type="hidden" name="workflowTypes" value="${workflowTypes.id}" />
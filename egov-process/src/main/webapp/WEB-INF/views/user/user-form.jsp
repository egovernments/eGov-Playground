<div class="main-content"><div class="row"><div class="col-md-12"><div class="panel panel-primary" data-collapsed="0"><div class="panel-heading"><div class="panel-title">User</div></div><div class="panel-body"><div class="form-group">
<label class="col-sm-3 control-label text-right"><spring:message code="lbl.username" />
</label><div class="col-sm-3 add-margin">
<form:input  path="userName" class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="20"  />
<form:errors path="userName" cssClass="error-msg" /></div><label class="col-sm-3 control-label text-right"><spring:message code="lbl.firstname" />
</label><div class="col-sm-3 add-margin">
<form:input  path="firstName" class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="20"  />
<form:errors path="firstName" cssClass="error-msg" /></div></div>
<div class="form-group">
<label class="col-sm-3 control-label text-right"><spring:message code="lbl.lastname" />
</label><div class="col-sm-3 add-margin">
<form:input  path="lastName" class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="20"  />
<form:errors path="lastName" cssClass="error-msg" /></div><label class="col-sm-3 control-label text-right"><spring:message code="lbl.email" />
</label><div class="col-sm-3 add-margin">
<form:input  path="email" class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="20"  />
<form:errors path="email" cssClass="error-msg" /></div></div>
<div class="form-group">
<label class="col-sm-3 control-label text-right"><spring:message code="lbl.password" />
</label><div class="col-sm-3 add-margin">
<form:input  path="password" class="form-control text-left patternvalidation" data-pattern="alphanumeric" maxlength="20"  />
<form:errors path="password" cssClass="error-msg" /></div><label class="col-sm-3 control-label text-right"><spring:message code="lbl.version" />
</label><div class="col-sm-3 add-margin">
 <form:input path="version" class="form-control text-right patternvalidation" data-pattern="number"  />
<form:errors path="version" cssClass="error-msg" /></div></div>
 <input type="hidden" name="user" value="${user.id}" />
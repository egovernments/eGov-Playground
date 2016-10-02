<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="main-content"><div class="row"><div class="col-md-12"><div class="panel panel-primary" data-collapsed="0"><div class="panel-heading"><div class="panel-title">User</div></div><div class="panel-body custom"><div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.username" />
</div><div class="col-sm-3 add-margin view-content">
${user.userName}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.firstname" />
</div><div class="col-sm-3 add-margin view-content">
${user.firstName}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.lastname" />
</div><div class="col-sm-3 add-margin view-content">
${user.lastName}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.email" />
</div><div class="col-sm-3 add-margin view-content">
${user.email}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.password" />
</div><div class="col-sm-3 add-margin view-content">
${user.password}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.version" />
</div><div class="col-sm-3 add-margin view-content">
${user.version}
</div></div>
</div></div></div></div><div class="row text-center"><div class="add-margin"><a href="javascript:void(0)" class="btn btn-default" onclick="self.close()">Close</a></div></div>
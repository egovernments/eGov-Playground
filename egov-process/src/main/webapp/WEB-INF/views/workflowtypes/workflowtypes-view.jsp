<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="main-content"><div class="row"><div class="col-md-12"><div class="panel panel-primary" data-collapsed="0"><div class="panel-heading"><div class="panel-title">WorkflowTypes</div></div><div class="panel-body custom"><div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.type" />
</div><div class="col-sm-3 add-margin view-content">
${workflowTypes.type}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.classname" />
</div><div class="col-sm-3 add-margin view-content">
${workflowTypes.className}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.servicename" />
</div><div class="col-sm-3 add-margin view-content">
${workflowTypes.serviceName}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.businesskey" />
</div><div class="col-sm-3 add-margin view-content">
${workflowTypes.businessKey}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.link" />
</div><div class="col-sm-3 add-margin view-content">
${workflowTypes.link}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.displayname" />
</div><div class="col-sm-3 add-margin view-content">
${workflowTypes.displayName}
</div></div>
<div class="row add-border"><div class="col-xs-3 add-margin"><spring:message code="lbl.enabled" />
</div><div class="col-sm-3 add-margin view-content">
${workflowTypes.enabled}
</div><div class="col-xs-3 add-margin"><spring:message code="lbl.grouped" />
</div><div class="col-sm-3 add-margin view-content">
${workflowTypes.grouped}
</div></div>
</div></div></div></div><div class="row text-center"><div class="add-margin"><a href="javascript:void(0)" class="btn btn-default" onclick="self.close()">Close</a></div></div>
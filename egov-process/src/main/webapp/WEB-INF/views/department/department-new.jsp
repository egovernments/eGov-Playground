<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" errorPage="/error/error.jsp" pageEncoding="UTF-8"  %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<form:form role="form" action="create" modelAttribute="department" method="post"
	name="departmentform" cssClass="form-horizontal form-groups-bordered"
	>
	<div class="main-content">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title">Department</div>
				</div>
				<div class="panel-body">
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.name" /> </label>
						<div class="col-sm-3 add-margin">
							<form:input path="name"
								class="form-control text-left patternvalidation"
								data-pattern="alphanumeric" maxlength="20" />
							<form:errors path="name" cssClass="error-msg" />
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.code" /> </label>
						<div class="col-sm-3 add-margin">
							<form:input path="code"
								class="form-control text-left patternvalidation"
								data-pattern="alphanumeric" maxlength="20" />
							<form:errors path="code" cssClass="error-msg" />
						</div>
						<input type="hidden" name="department.kk" value="mani"/>
						<input type="hidden" name="id" value="mani"/>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.active" /> </label>
						<div class="col-sm-3 add-margin">
							<form:checkbox path="active" />
							<form:errors path="active" cssClass="error-msg" />
						</div>
						
					</div>
					
	</div>
	</div>
	</div>
	</div>
	<div class="form-group">
		<div class="text-center">
			<button type='submit' class='btn btn-primary' id="buttonSubmit">
				<spring:message code='lbl.create' />
			</button>
			<a href='javascript:void(0)' class='btn btn-default'
				onclick='self.close()'><spring:message code='lbl.close' /></a>
		</div>
	</div>
</form:form>
<script>
	$('#buttonSubmit').click(function(e) {
		if ($('#departmentform').valid()) {
		} else {
			e.preventDefault();
		}
	});
</script>
<script type="text/javascript" src="<c:url value='/resources/app/js/departmentHelper.js'/>"></script>

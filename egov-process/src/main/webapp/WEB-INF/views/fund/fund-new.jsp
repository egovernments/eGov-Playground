<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>  
 	 
<form:form role="form" action="create" modelAttribute="fund" method="post"
	id="fundform" name="fundform" cssClass="form-horizontal form-groups-bordered">
	<%@ include file="fund-form.jsp" %>
	</div>
	</div>
	</div>
	</div>
	<div class="form-group">
		<div class="text-center">
			<button type='submit' class='btn btn-primary' id="buttonSubmit">
				Create
			</button>
			<a href='javascript:void(0)' class='btn btn-default'
				onclick='self.close()'>Close </a>
		</div>
	</div>
</form:form>
<script>
	 
</script>
<script type="text/javascript" 	src="<c:url value='/resources/app/js/fundHelper.js'/>"/>
   
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>

  <link rel="stylesheet" href='/egov-process/resources/global/css/bootstrap/bootstrap.css' >
	    <link rel="stylesheet" href='/egov-process/resources/global/css/font-icons/font-awesome/css/font-awesome.min.css' >
		<link rel="stylesheet" href='/egov-process/resources/global/css/bootstrap/typeahead.css' >
		<link rel="stylesheet" href='/egov-process/resources/global/css/egov/custom.css' >
		<link rel="stylesheet" href='/egov-process/resources/global/css/bootstrap/bootstrap-datepicker.css'  />
				
		<script src='/egov-process/resources/global/js/jquery/jquery.js' ></script>
		<script src='/egov-process/resources/global/js/bootstrap/bootstrap.js' ></script>
		<script src='/egov-process/resources/global/js/bootstrap/bootbox.min.js' ></script>
		<script src='/egov-process/resources/global/js/jquery/plugins/jquery.validate.min.js' ></script>
		<script src='/egov-process/resources/global/js/egov/custom.js' ></script>	
<% try{ %>		 
<form:form role="form" action="create" modelAttribute="fund"
	id="fundform" cssClass="form-horizontal form-groups-bordered"
	enctype="multipart/form-data">

	</div>
	</div>
	</div>
	</div>
	<div class="form-group">
		<div class="text-center">
			<button type='submit' class='btn btn-primary' id="buttonSubmit">
				 sdfadfsd
			</button>
			<a href='javascript:void(0)' class='btn btn-default'
				onclick='self.close()'>dsfsda </a>
		</div>
	</div>
</form:form>
<script>
	$('#buttonSubmit').click(function(e) {
		if ($('form').valid()) {
		} else {
			e.preventDefault();
		}
	});
</script>
<script type="text/javascript"
	src="<c:url value='/resources/app/js/fundHelper.js'/>"></script>
    <%} catch(Exception e)
    {
    	e.printStackTrace();
    }
    %>
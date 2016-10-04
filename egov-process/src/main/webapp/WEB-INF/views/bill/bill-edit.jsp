<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>
<form:form role="form" action="/egov-process/bill/update"
	modelAttribute="bill" id="billform"
	cssClass="form-horizontal form-groups-bordered"
	>
	<%@ include file="bill-form.jsp"%>
	<input type="hidden" name="bill" value="${bill.id}" />
	<input type="hidden" name="taskId" value="${bill.taskId}" />
	<div class="panel-heading">
					<div class="panel-title">Workflow details</div>
				</div>
	<div class="form-group">
						<label class="col-sm-3 control-label text-right">Message</label>
						<div class="col-sm-3 add-margin">
							<form:input path="message"
								class="form-control text-left " />
							
						</div>
   </div>
					
	
	</div>
	</div>
	</div>
	</div>
	<div class="form-group">
		<div class="text-center">
			<button type='submit' class='btn btn-primary' id="buttonSubmit">
				<spring:message code='lbl.update' />
			</button>
			<a href='javascript:void(0)' class='btn btn-default'
				onclick='self.close()'><spring:message code='lbl.close' /></a>
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
	src="<c:url value='/resources/app/js/billHelper.js'/>"></script>

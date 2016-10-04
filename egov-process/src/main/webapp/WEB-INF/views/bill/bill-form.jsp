<div class="main-content">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title">Bill</div>
				</div>
				<div class="panel-body">
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.fund" /> </label>
						<div class="col-sm-3 add-margin">
							<form:select path="fund" id="fund" cssClass="form-control"
								cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
							 <form:options items="${funds}" itemValue="id"
									itemLabel="name" />
							</form:select>
							<form:errors path="fund" cssClass="error-msg" />
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.department" /> </label>
						<div class="col-sm-3 add-margin">
							<form:select path="department" id="department"
								cssClass="form-control" cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${departments}" itemValue="id"
									itemLabel="name" />
							</form:select>
							 
							<form:errors path="department" cssClass="error-msg" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.billnumber" /> </label>
						<div class="col-sm-3 add-margin">
							<form:input path="billNumber"
								class="form-control text-left patternvalidation"
								data-pattern="alphanumeric" maxlength="20" />
							<form:errors path="billNumber" cssClass="error-msg" />
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.billdate" /> </label>
						<div class="col-sm-3 add-margin">
							<form:input path="billDate" class="form-control datepicker"
								data-date-end-date="0d" data-inputmask="'mask': 'd/m/y'" />
							<form:errors path="billDate" cssClass="error-msg" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.billamount" /> </label>
						<div class="col-sm-3 add-margin">
							<form:input path="billAmount"
								class="form-control text-right patternvalidation"
								data-pattern="number" />
							<form:errors path="billAmount" cssClass="error-msg" />
						</div>
						<label class="col-sm-3 control-label text-right"><spring:message
								code="lbl.billtype" /> </label>
						<div class="col-sm-3 add-margin">
							<form:select path="billType" id="billType"
								cssClass="form-control" cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${billtypes}"/>
							</form:select>
							<form:errors path="billType" cssClass="error-msg" />
						</div>
					</div>
					 <%@include file="../billdetails/billdetails-formtable.jsp" %>
						 
					
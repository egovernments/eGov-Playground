<%try{ %>
<div class="row">
	<div class="col-sm-12">
		<br>
		<table class="table table-bordered" id="result">
			<thead>
				<th><spring:message code="lbl.glcode" /></th>
				<th><spring:message code="lbl.coaname" /></th>
				<th><spring:message code="lbl.debit" /></th>
				<th><spring:message code="lbl.credit" /></th>
				<th><spring:message code="lbl.delete" /></th>
			</thead>
			<c:choose>
				<c:when test="${not empty bill.getDetails()}">
					<tbody>
						<c:forEach items="${bill.details}" var="details" varStatus="vs">
							<tr id="resultrow${vs.index}">
							<td>
								<form:input path="details[${vs.index}].glcode"
									class="form-control text-left patternvalidation"
									data-pattern="alphanumeric" maxlength="20" />
								<form:errors path="details[${vs.index}].glcode"
									cssClass="error-msg" />
								</td>
								<td>
								<form:input path="details[${vs.index}].coaName"
									class="form-control text-left patternvalidation"
									data-pattern="alphanumeric" maxlength="20" />
								<form:errors path="details[${vs.index}].coaName"
									cssClass="error-msg" />
								</td>
								<td>
								<form:input path="details[${vs.index}].debit"
									class="form-control text-right patternvalidation"
									data-pattern="number" />
								<form:errors path="details[${vs.index}].debit"
									cssClass="error-msg" />
								</td>
								<td>
								<form:input path="details[${vs.index}].credit"
									class="form-control text-right patternvalidation"
									data-pattern="number" />
								<form:errors path="details[${vs.index}].credit"
									cssClass="error-msg" />
								 
								<input type="hidden" name="details[0].bill" id="details[0].bill"/>
								</td>
								
								<td><span class="add-padding">
										<button type="button" id="del-row" class="btn btn-primary"
											onclick="deleteThisRow(this)" data-idx="${vs.index}">Delete
											Row</button> </i>
								</span></td>
							</tr>
						</c:forEach>
					</tbody>
				</c:when>
				<c:otherwise>
					<tbody>
						<tr id="resultrow0">
						   <td>
							<form:input path="details[0].glcode"
								class="form-control text-left patternvalidation"
								data-pattern="alphanumeric" maxlength="20" />
							<form:errors path="details[0].glcode" cssClass="error-msg" />
							</td>
							<td>
							<form:input path="details[0].coaName"
								class="form-control text-left patternvalidation"
								data-pattern="alphanumeric" maxlength="20" />
							<form:errors path="details[0].coaName" cssClass="error-msg" />
							</td>
							<td>
							<form:input path="details[0].debit"
								class="form-control text-right patternvalidation"
								data-pattern="number" />
							<form:errors path="details[0].debit" cssClass="error-msg" />
							</td>
							<td>
							<form:input path="details[0].credit"
								class="form-control text-right patternvalidation"
								data-pattern="number" />
							<form:errors path="details[0].credit" cssClass="error-msg" />
							<input type="hidden" name="details[0].bill" id="details[0].bill"/>
							</td>
							
							<td><span class="add-padding">
									<button type="button" id="del-row" class="btn btn-primary"
										onclick="deleteThisRow(this)" data-idx="${vs.index}">Delete
										Row</button> </i>
							</span></td>
						</tr>
					 
					</tbody>
				</c:otherwise>
			</c:choose>
			<tbody>
		</table>
	</div>
	<div class="col-sm-12 text-center">
		<button type="button" id="addrow" class="btn btn-primary">Add	Row</button>
	</div>
</div>
<%}catch(Exception e){e.printStackTrace();}%>
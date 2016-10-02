<div class=" row">
	<div class=" col-sm-12">
		<br>
		<table class=" table table-bordered" id=" result">
			<thead>
				<th><spring:message code="lbl.glcode" /></th>
				<th><spring:message code="lbl.coaname" /></th>
				<th><spring:message code="lbl.debit" /></th>
				<th><spring:message code="lbl.credit" /></th>
				<th><spring:message code="lbl.bill" /></th>
				<th><spring:message code="lbl.version" /></th>
			</thead>
			<c:choose>
				<c:when test="${not empty details.getDetails()}">
					<tbody>
						<c:forEach items="${details.details}" var="details" varStatus="vs">
							<tr id="resultrow${vs.index}">
								<form:input path="details[${vs.index}].glcode"
									class="form-control text-left patternvalidation"
									data-pattern="alphanumeric" maxlength="20" />
								<form:errors path="details[${vs.index}].glcode"
									cssClass="error-msg" />
								</td>
								<form:input path="details[${vs.index}].coaName"
									class="form-control text-left patternvalidation"
									data-pattern="alphanumeric" maxlength="20" />
								<form:errors path="details[${vs.index}].coaName"
									cssClass="error-msg" />
								</td>
								<form:input path="details[${vs.index}].debit"
									class="form-control text-right patternvalidation"
									data-pattern="number" />
								<form:errors path="details[${vs.index}].debit"
									cssClass="error-msg" />
								</td>
								<form:input path="details[${vs.index}].credit"
									class="form-control text-right patternvalidation"
									data-pattern="number" />
								<form:errors path="details[${vs.index}].credit"
									cssClass="error-msg" />
								</td>
								<form:select path="details[${vs.index}].bill"
									id="details[${vs.index}].bill" cssClass="form-control"
									cssErrorClass="form-control error">
									<form:option value="">
										<spring:message code="lbl.select" />
									</form:option>
									<form:options items="${bills}" itemValue="id" itemLabel="name" />
								</form:select>
								<form:input path="details[${vs.index}].bill"
									class="form-control text-right patternvalidation"
									data-pattern="number" />
								<form:errors path="details[${vs.index}].bill"
									cssClass="error-msg" />
								</td>
								<form:input path="details[${vs.index}].version"
									class="form-control text-right patternvalidation"
									data-pattern="number" />
								<form:errors path="details[${vs.index}].version"
									cssClass="error-msg" />
								</td>
								<td><span class=" add-padding">
										<button type=" button" id=" del-row" class=" btn btn-primary"
											onclick=" deleteThisRow(this)" data-idx=" ${vs.index}">Delete
											Row</button> </i>
								</span></td>
							</tr>
						</c:forEach>
					</tbody>
				</c:when>
				<c:otherwise>
					<tbody>
						<tr id="resultrow${vs.index}">
						   <td>
							<form:input path="details[0].glcode"
								class="form-control text-left patternvalidation"
								data-pattern="alphanumeric" maxlength="20" />
							<form:errors path="details[0].glcode" cssClass="error-msg" />
							</td>
							<form:input path="details[0].coaName"
								class="form-control text-left patternvalidation"
								data-pattern="alphanumeric" maxlength="20" />
							<form:errors path="details[0].coaName" cssClass="error-msg" />
							</td>
							<form:input path="details[0].debit"
								class="form-control text-right patternvalidation"
								data-pattern="number" />
							<form:errors path="details[0].debit" cssClass="error-msg" />
							</td>
							<form:input path="details[0].credit"
								class="form-control text-right patternvalidation"
								data-pattern="number" />
							<form:errors path="details[0].credit" cssClass="error-msg" />
							</td>
							<form:select path="details[0].bill" id="details[0].bill"
								cssClass="form-control" cssErrorClass="form-control error">
								<form:option value="">
									<spring:message code="lbl.select" />
								</form:option>
								<form:options items="${bills}" itemValue="id" itemLabel="name" />
							</form:select>
							<form:input path="details[0].bill"
								class="form-control text-right patternvalidation"
								data-pattern="number" />
							<form:errors path="details[0].bill" cssClass="error-msg" />
							</td>
							<form:input path="details[0].version"
								class="form-control text-right patternvalidation"
								data-pattern="number" />
							<form:errors path="details[0].version" cssClass="error-msg" />
							</td>
							<td><span class=" add-padding">
									<button type=" button" id=" del-row" class=" btn btn-primary"
										onclick=" deleteThisRow(this)" data-idx=" ${vs.index}">Delete
										Row</button> </i>
							</span></td>
						</tr>
					 
					</tbody>
				</c:otherwise>
			</c:choose>
			<tbody>
		</table>
	</div>
	<div class=" col-sm-12 text-center">
		<button type=" button" id=" addrow" class=" btn btn-primary">Add
			Row</button>
	</div>
</div>

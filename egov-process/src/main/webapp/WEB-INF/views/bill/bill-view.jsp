<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/taglibs.jsp"%>
<div class="main-content">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-primary" data-collapsed="0">
				<div class="panel-heading">
					<div class="panel-title">Bill</div>
				</div>
				<div class="panel-body custom">
					<div class="row add-border">
						<div class="col-xs-3 add-margin">
							<spring:message code="lbl.fund" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							${bill.fund.name}</div>
						<div class="col-xs-3 add-margin">
							<spring:message code="lbl.department" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							${bill.department.name}</div>
					</div>
					<div class="row add-border">
						<div class="col-xs-3 add-margin">
							<spring:message code="lbl.billnumber" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							${bill.billNumber}</div>
						<div class="col-xs-3 add-margin">
							<spring:message code="lbl.billdate" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							<fmt:formatDate pattern="MM/dd/yyyyy" value="${bill.billDate}" />
						</div>
					</div>
					<div class="row add-border">
						<div class="col-xs-3 add-margin">
							<spring:message code="lbl.billamount" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							${bill.billAmount}</div>
						<div class="col-xs-3 add-margin">
							<spring:message code="lbl.billtype" />
						</div>
						<div class="col-sm-3 add-margin view-content">
							${bill.billType}</div>
					</div>
					 
				</div>
			</div>
		</div>
	</div>
	<div class="row text-center">
		<div class="add-margin">
			<a href="javascript:void(0)" class="btn btn-default"
				onclick="self.close()">Close</a>
		</div>
	</div>
<%@ include file="../header.jsp" %>

<div class="container">

<c:if test="${not empty msg}">
	<div class="alert alert-${css} alert-dismissible" role="alert">
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">x</span>
		</button>
		<strong>${msg}</strong>
	</div>
</c:if>

<h1>Client Details</h1>
<hr />
<a href="${contextPath}/clients">Return to Client Management</a>
<hr />

<div class="row">
	<label class="col-sm-2">Name</label>
	<div class="col-sm-10">${client.name}</div>
</div>

<div class="row">
	<label class="col-sm-2">Custody Status</label>
	<div class="col-sm-10">${client.custodyStatus.displayText}</div>
</div>

<div class="row">
	<label class="col-sm-2">Current Cases</label>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Case Number</th>
				<th>Date Opened</th>
				<th>Date Closed</th>
			</tr>
		</thead>
		<c:forEach items="${client.cases}" var="courtCase">
			<tr>
				<td>
					<spring:url value="/cases/${courtCase.id}" var="caseUrl" />
					<a href="${caseUrl}">${courtCase.caseNumber}</a>
				</td>
				<td>${courtCase.dateOpened}</td>
				<td>${courtCase.dateClosed}</td>
			</tr>
		</c:forEach>
	</table>
</div>
	
</div>
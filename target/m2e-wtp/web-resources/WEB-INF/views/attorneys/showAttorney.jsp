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

<h1>Attorney Details</h1>
<hr />
<a href="${contextPath}/employeeManagement">Return to Employee Management</a>
<hr />

<div class="row">
	<label class="col-sm-2">Name</label>
	<div class="col-sm-10">${attorney.name}</div>
</div>

<div class="row">
	<label class="col-sm-2">Employment Status</label>
	<div class="col-sm-10">${attorney.employmentStatus}</div>
</div>

<div class="row">
	<label class="col-sm-2">Investigator</label>
	<c:choose>
		<c:when test="${attorney.employmentStatus == 'ACTIVE'}">
			<div class="col-sm-10">${attorney.investigator}</div>
		</c:when>
		<c:otherwise>
			<div class="col-sm-10">None assigned</div>
		</c:otherwise>
	</c:choose>
</div>
<br />

<div class="row">
	<label class="col-sm-2">Current Caseload</label>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Case Number</th>
				<th>Client</th>
			</tr>
		</thead>
		<c:forEach items="${caseload}" var="courtCase">
			<tr>
				<td>
					<spring:url value="/cases/${courtCase.id}" var="caseUrl" />
					<a href="${caseUrl}">${courtCase.caseNumber}</a>
				</td>
				<td>
					<spring:url value="/clients/${courtCase.client.id}" var="clientUrl" />
					<a href="${clientUrl}">${courtCase.client.name}</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
	
</div>
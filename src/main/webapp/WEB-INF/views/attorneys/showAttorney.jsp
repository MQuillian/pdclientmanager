<%@ include file="../header.jsp" %>

<h1>Attorney Details</h1>
<hr />
<a href="${contextPath}/employeeManagement">Return to Employee Management</a>
<hr />

<div class="row">
	<label class="col-sm-2">Name</label>
	<div class="col-sm-10">${attorney.name}</div>
</div>

<div class="row">
	<label class="col-sm-2">Working Status</label>
	<div class="col-sm-10">${attorney.workingStatus.displayText}</div>
</div>

<div class="row">
	<label class="col-sm-2">Investigator</label>
	<c:choose>
		<c:when test="${attorney.investigator != NULL}">
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
	
<%@ include file="../footer.jsp" %>
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

<h1>Full Case List</h1>
<hr />
<a href="${contextPath}/caseManagement">Return to Case Management</a>
<hr />

<table class="table table-striped">
	<thead>
		<tr>
			<th>Case Number</th>
			<th>Client</th>
			<th>Case Status</th>
			<th>Attorney</th>
		</tr>
	</thead>
	<c:forEach items="${openCases}" var="courtCase">
		<tr>
			<td>${courtCase.caseNumber}</td>
			<td>${courtCase.client.name}</td>
			<td>${courtCase.caseStatus}</td>
			<td>${courtCase.attorney.name}</td>
			<td>
				<div class="btn-group" role="group">
					<spring:url value="/cases/${courtCase.id}" var="courtCaseUrl" />
					<spring:url value="/cases/${courtCase.id}/update" var="updateUrl" />
					<spring:url value="/cases/${courtCase.id}/delete" var="deleteUrl" />
					
					<button class="btn btn-info"
						onclick="location.href='${courtCaseUrl}'">Details</button>
					<button class="btn btn-primary"
						onclick="location.href='${updateUrl}'">Update</button>
					<form:form action="${deleteUrl}" method="post">
						<button class="btn btn-danger" type="submit">Delete</button>
					</form:form>
				</div>
			</td>
		</tr>
	</c:forEach>
</table>

</div>

<%@ include file="../footer.jsp" %>
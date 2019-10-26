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

<h1>Full Attorney List</h1>
<hr />
<a href="${contextPath}/employeeManagement">Return to Employee Management</a>
<hr />

<table class="table table-striped">
	<thead>
		<tr>
			<th>Name</th>
			<th>Working Status</th>
			<th>Investigator</th>
		</tr>
	</thead>
	<c:forEach items="${attorneyList}" var="attorney">
		<tr>
			<td>${attorney.name}</td>
			<td>${attorney.workingStatus}</td>
			<c:choose>
				<c:when test="${attorney.workingStatus == 'ACTIVE'}">
					<td>${attorney.investigator}</td>
				</c:when>
				<c:otherwise>
					<td>None assigned</td>
				</c:otherwise>
			</c:choose>
			<td>
				<spring:url value="/attorneys/${attorney.id}" var="attorneyUrl" />
				<spring:url value="/attorneys/${attorney.id}/update" var="updateUrl" />
				<spring:url value="/attorneys/${attorney.id}/delete" var="deleteUrl" />
				
				<button class="btn btn-info"
					onclick="location.href='${attorneyUrl}'">Details</button>
				<button class="btn btn-primary"
					onclick="location.href='${updateUrl}'">Update</button>
				<form:form action="${deleteUrl}" method="post">
					<button class="btn btn-danger" type="submit">Delete</button>
				</form:form>
			</td>
		</tr>
	</c:forEach>
</table>

</div>

<%@ include file="../footer.jsp" %>
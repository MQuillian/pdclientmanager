<%@ include file="../header.jsp" %>

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
			<th></th>
		</tr>
	</thead>
	<c:forEach items="${attorneyList}" var="attorney">
		<tr>
			<td>${attorney.name}</td>
			<td>${attorney.workingStatus.displayText}</td>
			<c:choose>
				<c:when test="${attorney.investigator != NULL}">
					<td>${attorney.investigator}</td>
				</c:when>
				<c:otherwise>
					<td>None assigned</td>
				</c:otherwise>
			</c:choose>
			<td>
				<div class="btn-group" role="group">
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
				</div>
			</td>
		</tr>
	</c:forEach>
</table>

<%@ include file="../footer.jsp" %>
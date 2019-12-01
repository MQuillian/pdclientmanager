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

<h1>Full Investigator List</h1>
<hr />
<a href="${contextPath}/employeeManagement">Return to Employee Management</a>
<hr />

<table class="table table-striped">
	<thead>
		<tr>
			<th>Name</th>
			<th>Working Status</th>
		</tr>
	</thead>
	<c:forEach items="${investigatorList}" var="investigator">
		<tr>
			<td>${investigator.name}</td>
			<td>${investigator.workingStatus}</td>
			<td>
				<div class="btn-group" role="group">
					<spring:url value="/investigators/${investigator.id}" var="investigatorUrl" />
					<spring:url value="/investigators/${investigator.id}/update" var="updateUrl" />
					<spring:url value="/investigators/${investigator.id}/delete" var="deleteUrl" />
					
					<button class="btn btn-info"
						onclick="location.href='${investigatorUrl}'">Details</button>
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
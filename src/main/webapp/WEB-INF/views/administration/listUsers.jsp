<%@ include file="../header.jsp" %>

<h1>Full User List</h1>
<hr />
<a href="${contextPath}/admin">Return to System Management</a>
<hr />

<table class="table table-striped">
	<thead>
		<tr>
			<th>Username</th>
			<th>Email</th>
			<th>Roles</th>
			<th></th>
		</tr>
	</thead>
	<c:forEach items="${userList}" var="user">
		<tr>
			<td>${user.username}</td>
			<td>${user.email}</td>
			<td>
				<c:forEach items="${user.authorities}" var="authority">
					${authority}
				</c:forEach>
			</td>
			<td>
				<div class="btn-group" role="group">
					<spring:url value="/admin/users/${user.id}/update" var="updateUrl" />
					<spring:url value="/admin/users//${user.id}/delete" var="deleteUrl" />
					
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
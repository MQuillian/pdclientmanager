<%@ include file="../header.jsp" %>

<c:choose>
	<c:when test="${userForm['new']}">
		<h1>Add User</h1>
	</c:when>
	<c:otherwise>
		<h1>Update User</h1>
	</c:otherwise>
</c:choose>

<hr />
<a href="${contextPath}/admin">Return to System Management</a>
<hr />

<spring:url value="/admin/users" var="userActionUrl" />

<form:form class="form-horizontal" method="post"
	modelAttribute="userForm" action="${userActionUrl}">
	
	<form:hidden path="id" />
	
	<spring:bind path="username">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Username</label>
			<div class="col-sm-10">
				<form:input path="username" type="text" class="form-control"
					id="username" placeholder="Username" />
				<form:errors path="username" class="control-label" />
			</div>
		</div>
	</spring:bind>
	
	<spring:bind path="email">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Email</label>
			<div class="col-sm-10">
				<form:input path="email" type="email" class="form-control"
					id="email" placeholder="Email" />
				<form:errors path="email" class="control-label" />
			</div>
		</div>
	</spring:bind>
	<spring:bind path="password">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Password</label>
			<div class="col-sm-10">
				<form:input path="password" type="password" class="form-control"
					id="password" placeholder="Password" />
				<form:errors path="password" class="control-label" />
			</div>
			<br />
		</div>
	</spring:bind>
	<spring:bind path="matchingPassword">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Confirm password</label>
			<div class="col-sm-10">
				<form:input path="matchingPassword" type="password" class="form-control"
					id="matchingPassword" placeholder="Password" />
				<form:errors path="matchingPassword" class="control-label" />
			</div>
			<br />
		</div>
	</spring:bind>
	
	<spring:bind path="roles">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">User Permissions</label>
			<div class="col-sm-5">
				<form:checkboxes path="roles" element="li style='list-style-type:none;'" items="${availableRoles}"
					size="5"
					cssStyle="margin-right: 5px; margin-left: 5px" />
				<form:errors path="roles" class="control-label" />
			</div>
			<br />
		</div>
	</spring:bind>
	
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<c:choose>
				<c:when test="${userForm['new']}">
					<button type="submit" class="btn-lg btn-primary pull-right">Add</button>
				</c:when>
				<c:otherwise>
					<button type="submit" class="btn-lg btn-primary pull-right">Update</button>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</form:form>

<%@ include file="../footer.jsp" %>
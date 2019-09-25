<%@ include file="../header.jsp" %>

<div class="container">

<c:choose>
	<c:when test="${attorneyForm['new']}">
		<h1>Add Attorney</h1>
	</c:when>
	<c:otherwise>
		<h1>Update Attorney</h1>
	</c:otherwise>
</c:choose>
<hr />
<a href="${contextPath}/employeeManagement">Return to Employee Management</a>
<hr />

<spring:url value="/attorneys" var="attorneyActionUrl" />

<form:form class="form-horizontal" method="post"
	modelAttribute="attorneyForm" action="${attorneyActionUrl}">
	
	<form:hidden path="id" />
	
	<spring:bind path="name">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Name</label>
			<div class="col-sm-10">
				<form:input path="name" type="text" class="form-control"
					id="name" placeholder="Name" />
				<form:errors path="name" class="control-label" />
			</div>
		</div>
	</spring:bind>
	
	<spring:bind path="employmentStatus">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Employment Status</label>
			<div class="col-sm-10">
				<label class="radio-inline">
					<form:radiobutton path="employmentStatus" value="ACTIVE" />Active
				</label>
				<label class="radio-inline">
					<form:radiobutton path="employmentStatus" value="INACTIVE" />Inactive
				</label>
				<br />
				<form:errors path="employmentStatus" class="control-label" />
			</div>
		</div>
	</spring:bind>
	
	<spring:bind path="investigatorId">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Investigator</label>
			<div class="col-sm-5">
				<form:select path="investigatorId" items="${activeInvestigators}"
					itemLabel="name" itemValue="id" size="5" class="form-control" />
				<form:errors path="investigatorId" class="control-label" />
			</div>
			<br />
		</div>
	</spring:bind>
	
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<c:choose>
				<c:when test="${attorneyForm['new']}">
					<button type="submit" class="btn-lg btn-primary pull-right">Add</button>
				</c:when>
				<c:otherwise>
					<button type="submit" class="btn-lg btn-primary pull-right">Update</button>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</form:form>

</div>
<%@ include file="../footer.jsp" %>
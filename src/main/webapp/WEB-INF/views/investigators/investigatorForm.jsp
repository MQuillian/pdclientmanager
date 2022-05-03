<%@ include file="../header.jsp" %>

<c:choose>
	<c:when test="${investigatorForm['new']}">
		<h1>Add Investigator</h1>
	</c:when>
	<c:otherwise>
		<h1>Update Investigator</h1>
	</c:otherwise>
</c:choose>
<hr />
<a href="${contextPath}/employeeManagement">Return to Employee Management</a>
<hr />

<spring:url value="/investigators" var="investigatorActionUrl" />

<form:form class="form-horizontal" method="post"
	modelAttribute="investigatorForm" action="${investigatorActionUrl}">
	
	<form:hidden path="id" />
	
	<spring:bind path="name">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Name</label>
			<div class="col-sm-10">
				<form:input path="name" type="text" class="form-control"
					id="name" placeholder="Name" />
				<form:errors path="name" class="control-label" cssClass="error"/>
			</div>
		</div>
	</spring:bind>
	
	<spring:bind path="workingStatus">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Working Status</label>
			<div class="col-sm-10">
				<label class="radio-inline">
					<form:radiobutton path="workingStatus" value="ACTIVE" />Active
				</label>
				<label class="radio-inline">
					<form:radiobutton path="workingStatus" value="INACTIVE" />Inactive
				</label>
				<br />
				<form:errors path="workingStatus" class="control-label" cssClass="error" />
			</div>
		</div>
	</spring:bind>
	
	<spring:bind path="assignedAttorneysIds">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Assigned Attorneys</label>
			<div class="col-sm-5">
				<form:checkboxes path="assignedAttorneysIds" element="li style='list-style-type:none;'" items="${activeAttorneys}"
					itemLabel="name" itemValue="id" size="5"
					cssStyle="margin-right: 5px; margin-left: 5px" />
				<form:errors path="assignedAttorneysIds" class="control-label" />
			</div>
			<br />
		</div>
	</spring:bind>
	
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<c:choose>
				<c:when test="${investigatorForm['new']}">
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
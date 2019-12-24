<%@ include file="../header.jsp" %>

<div class="container">

<c:choose>
	<c:when test="${judgeForm['new']}">
		<h1>Add Judge</h1>
	</c:when>
	<c:otherwise>
		<h1>Update Judge</h1>
	</c:otherwise>
</c:choose>
<hr />
<a href="${contextPath}/judges">Return to Judge Management</a>
<hr />

<spring:url value="/judges" var="judgeActionUrl" />

<form:form class="form-horizontal" method="post"
	modelAttribute="judgeForm" action="${judgeActionUrl}">
	
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
				<form:errors path="workingStatus" class="control-label" />
			</div>
		</div>
	</spring:bind>
	
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<c:choose>
				<c:when test="${judgeForm['new']}">
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
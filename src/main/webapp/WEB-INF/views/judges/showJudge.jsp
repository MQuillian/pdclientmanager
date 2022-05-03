<%@ include file="../header.jsp" %>

<h1>Judge Details</h1>
<hr />
<a href="${contextPath}/judges">Return to Judge Management</a>
<hr />

<div class="row">
	<label class="col-sm-2">Name</label>
	<div class="col-sm-10">${judge.name}</div>
</div>

<div class="row">
	<label class="col-sm-2">Working Status</label>
	<div class="col-sm-10">${judge.workingStatus.displayText}</div>
</div>

<div class="btn-group" role="group">
	<spring:url value="/judges/${judge.id}/update" var="updateUrl" />
	<spring:url value="/judges/${judge.id}/delete" var="deleteUrl" />

	<button class="btn btn-primary"
		onclick="location.href='${updateUrl}'">Update</button>
	<form:form action="${deleteUrl}" method="post">
		<button class="btn btn-danger" type="submit">Delete</button>
	</form:form>
</div>
	
<%@ include file="../footer.jsp" %>
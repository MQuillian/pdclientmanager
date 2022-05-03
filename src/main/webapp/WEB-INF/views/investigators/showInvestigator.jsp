<%@ include file="../header.jsp" %>

<h1>Investigator Details</h1>
<hr />
<a href="${contextPath}/employeeManagement">Return to Employee Management</a>
<hr />

<div class="row">
	<label class="col-sm-2">Name</label>
	<div class="col-sm-10">${investigator.name}</div>
</div>

<div class="row">
	<label class="col-sm-2">Working Status</label>
	<div class="col-sm-10">${investigator.workingStatus.displayText}</div>
</div>

<div>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Assigned Attorneys</th>
			</tr>
		</thead>
		<c:forEach items="${assignedAttorneys}" var="attorney">
			<tr>
				<td>
					<spring:url value="/attorneys/${attorney.id}" var="attorneyUrl" />
					<a href="${attorneyUrl}">${attorney.name}</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
<div class="btn-group" role="group">
	<spring:url value="/investigators/${investigator.id}/update" var="updateUrl" />
	<spring:url value="/investigators/${investigator.id}/delete" var="deleteUrl" />
	
	<button class="btn btn-primary"
		onclick="location.href='${updateUrl}'">Update</button>
	<form:form action="${deleteUrl}" method="post">
		<button class="btn btn-danger" type="submit">Delete</button>
	</form:form>
</div>
	
<%@ include file="../footer.jsp" %>
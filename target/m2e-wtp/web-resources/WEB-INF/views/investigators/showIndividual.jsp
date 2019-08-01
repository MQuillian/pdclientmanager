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

<h1>Investigator Details</h1>
<hr />
<a href="${contextPath}/employeeManagement">Return to Employee Management</a>
<hr />

<div class="row">
	<label class="col-sm-2">Name</label>
	<div class="col-sm-10">${investigator.name}</div>
</div>

<div class="row">
	<label class="col-sm-2">Employment Status</label>
	<div class="col-sm-10">${investigator.employmentStatus}</div>
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
	
</div>
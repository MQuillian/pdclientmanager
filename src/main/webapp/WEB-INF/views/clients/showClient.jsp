<%@ include file="../header.jsp" %>

<h1>Client Details</h1>
<hr />
<a href="${contextPath}/clients">Return to Client Management</a>
<hr />

<div class="row">
	<label class="col-sm-2">Name</label>
	<div class="col-sm-10">${client.name}</div>
</div>

<div class="row">
	<label class="col-sm-2">Incarceration date</label>
	<div class="col-sm-10">${client.incarcerationDate}</div>
</div>

<div class="row">
	<label class="col-sm-2">Release date</label>
	<div class="col-sm-10">${client.releaseDate}</div>
</div>

<div class="row">
	<label class="col-sm-2">Custody Status</label>
	<div class="col-sm-10">${client.custodyStatus.displayText}</div>
</div>

<div class="row">
	<label class="col-sm-2">Current Cases</label>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Case Number</th>
				<th>Date Opened</th>
				<th>Date Closed</th>
			</tr>
		</thead>
		<c:forEach items="${client.cases}" var="courtCase">
			<tr>
				<td>
					<spring:url value="/cases/${courtCase.id}" var="caseUrl" />
					<a href="${caseUrl}">${courtCase.caseNumber}</a>
				</td>
				<td>${courtCase.dateOpened}</td>
				<td>${courtCase.dateClosed}</td>
			</tr>
		</c:forEach>
	</table>
</div>
<div class="btn-group" role="group">
	<spring:url value="/clients/${client.id}/update" var="updateUrl" />
	<spring:url value="/clients/${client.id}/delete" var="deleteUrl" />
	
	<button class="btn btn-primary"
		onclick="location.href='${updateUrl}'">Update</button>
	<form:form action="${deleteUrl}" method="post">
		<button class="btn btn-danger" type="submit">Delete</button>
	</form:form>
</div>
	
<%@ include file="../footer.jsp" %>
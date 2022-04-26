<%@ include file = "header.jsp" %>

<div class="row">
	<div class="col">
		<h1>Home</h1>
		<hr>
		<a href="admin">System Management</a>
		<br>
		<a href="calendar/management">Calendar Management</a>
		<br>
		<a href="cases">Case Management</a>
		<br>
		<a href="clients">Client Management</a>
		<br>
		<a href="employeeManagement">Employee Management</a>
		<br>
		<a href="individualStats">Individual Statistics</a>
		<br>
		<br>
		<form:form action="reset" method="post">
			<button class="btn btn-danger" type="submit">
			Reset Data (FOR DEMO USE ONLY)</button>
		</form:form>
	</div>
	<div class="col-8">
	<h2>Upcoming Events</h2>
	<hr>
	<table class="table table-striped" id="homeCalendar">
	<thead>
		<tr>
			<th>Start Time</th>
			<th>Description</th>
			<th>Case Number</th>
		</tr>
	</thead>
	
	<c:forEach items="${events}" var="caseEvent">
		<tr>
			<td>${caseEvent.prettyStartTime}</td>
			<td>${caseEvent.description}</td>
			<td><a href="cases/byCaseNumber/${caseEvent.caseNumber}">${caseEvent.caseNumber}</a></td>
			<td>
				<div class="btn-group" role="group">
					<spring:url value="/calendar/${caseEvent.id}/update" var="updateUrl" />
					<spring:url value="/calendar/${caseEvent.id}/deleteFromHome" var="deleteUrl" />
					
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
</div>

<%@ include file = "footer.jsp" %>
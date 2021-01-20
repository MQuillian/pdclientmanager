<%@ include file = "header.jsp" %>

<div class="row">
	<div class="col">
		<h1>Home</h1>
		<hr>
		CASE SEARCH UI
		<br>
		PENDING CT DATE UI
		<br>
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
		STATISTICS UI
		<br>
		<a href="individualStats">Individual Statistics</a>
		<br>
		<a href="officeStats">Office Statistics</a>
		<hr>
		<form:form action="reset" method="post">
			<button class="btn btn-danger" type="submit">
			Reset Data (FOR DEMO USE ONLY)</button>
		</form:form>
	</div>
	<div class="col">
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
			<td>${caseEvent.caseNumber}</td>
		</tr>
	</c:forEach>
	</table>
	</div>
</div>

<%@ include file = "footer.jsp" %>
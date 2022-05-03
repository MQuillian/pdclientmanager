<%@ include file = "../header.jsp" %>

<div class="row">
	<div class="col">
		<h1>Calendar Management</h1>
		<hr>
		<a href="${contextPath}/">Return to Home</a>
		<hr>
		<a href="multiCaseEventForm">Add Cases for Single Courtdate</a>
		<br>
		<a href="caseEventForm">Add Event</a>
		<br>
	</div>
	<div class="col-9">
	<h2>Upcoming Events</h2>
	<hr>
	<table class="table table-striped" id="homeCalendar">
	<thead>
		<tr>
			<th>Start Time</th>
			<th>End Time</th>
			<th>Description</th>
			<th>Case Number</th>
		</tr>
	</thead>
	
	<c:forEach items="${events}" var="caseEvent">
		<tr>
			<td>${caseEvent.prettyStartTime}</td>
			<td>${caseEvent.prettyEndTime}</td>
			<td>${caseEvent.description}</td>
			<td><a href="${contextPath}/cases/byCaseNumber/${caseEvent.caseNumber}">${caseEvent.caseNumber}</a></td>
			<td>
				<div class="btn-group" role="group">
					<spring:url value="${caseEvent.id}/update" var="updateUrl" />
					<spring:url value="${caseEvent.id}/delete" var="deleteUrl" />
					
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

<%@ include file = "../footer.jsp" %>
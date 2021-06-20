<%@ include file = "header.jsp" %>

<h1>Individual Stats Page</h1>
<hr>
<a href="${contextPath}/">Return to Home</a>
<hr>
<div class="row">
	<div class="col-3">
		<label for="attorneySelect">Attorney: </label>
		<select id="attorneySelect">
			<c:forEach items="${activeAttorneys}" var="attorney">
				<option value=<c:out value="${attorney.id}"/>>${attorney.name}</option>
			</c:forEach>
		</select>
	</div>
	<div class="col-3">
		<button type="button" id="generateStatsButton" class="btn btn-primary pull-right"
			onClick="generateStats()">Generate statistics</button>
	</div>
</div>
<div class="row">
	<span class="col" id="totalCases">Current caseload:</span>
</div>
<div class="row">
	<span class="col" id="inCustodyCases">Current in-custody caseload:</span>
</div>
<div class="row">
	<span class="col" id="totalClients">Number of clients:</span>
</div>
<div class="row">
	<span class="col" id="inCustodyClients">Number of in-custody clients:</span>
</div>
<hr>
<h2>Aging Report</h2>
<table class="table table-striped">
	<thead>
		<tr>
			<th>Client</th>
			<th>Incarceration date</th>
		</tr>
	</thead>
	<tbody id="agingReportTable">
	</tbody>
</table>

<script src="<spring:url value="/resources/js/statsPageScript.js" />"></script>

<%@ include file = "footer.jsp" %>
<%@ include file = "../header.jsp" %>
<h1>Office Statistics</h1>
<hr />
<a href="${contextPath}/admin">Return to System Management</a>
<hr />

<div class="row">
	<label class="col-sm-3">Total open cases</label>
	<div class="col-sm-8">${officeStats.totalCases}</div>
</div>

<div class="row">
	<label class="col-sm-3">Total open in-custody cases</label>
	<div class="col-sm-8">${officeStats.totalInCustodyCases}</div>
</div>

<div class="row">
	<label class="col-sm-3">Average age of all open cases</label>
	<div class="col-sm-8">${officeStats.roundedTotalAvgAge} days</div>
</div>

<div class="row">
	<label class="col-sm-3">Average age of in-custody cases</label>
	<div class="col-sm-8">${officeStats.roundedInCustodyAvgAge} days</div>
</div>

<%@ include file = "../footer.jsp" %>
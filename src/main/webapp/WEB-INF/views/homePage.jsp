<%@ include file = "header.jsp" %>

<div class="container">

<c:if test="${not empty msg}">
	<div class="alert alert-${css} alert-dismissible" role="alert">
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">x</span>
		</button>
		<strong>${msg}</strong>
	</div>
</c:if>

<h1>Home</h1>
<hr>
CASE SEARCH UI
<br>
PENDING CT DATE UI
<br>
<a href="admin">System Management</a>
<br>
<a href="pendingCourt">Pending Court Dates</a>
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

<%@ include file = "footer.jsp" %>
<%@ include file="../header.jsp" %>

<h1>Charge Details</h1>
<hr />
<a href="${contextPath}/charges">Return to Charge Management</a>
<hr />

<div class="row">
	<label class="col-sm-2">Name</label>
	<div class="col-sm-10">${charge.name}</div>
</div>

<div class="row">
	<label class="col-sm-2">Statute Number</label>
	<div class="col-sm-10">${charge.statute}</div>
</div>
	
<%@ include file="../footer.jsp" %>
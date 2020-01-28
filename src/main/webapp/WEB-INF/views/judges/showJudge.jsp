<%@ include file="../header.jsp" %>

<h1>Judge Details</h1>
<hr />
<a href="${contextPath}/judges">Return to Judge Management</a>
<hr />

<div class="row">
	<label class="col-sm-2">Name</label>
	<div class="col-sm-10">${judge.name}</div>
</div>

<div class="row">
	<label class="col-sm-2">Working Status</label>
	<div class="col-sm-10">${judge.workingStatus.displayText}</div>
</div>
	
<%@ include file="../footer.jsp" %>
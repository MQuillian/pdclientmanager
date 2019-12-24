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
	
</div>
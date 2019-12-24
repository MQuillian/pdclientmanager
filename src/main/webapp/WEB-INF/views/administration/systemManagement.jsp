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

<h1>System Management</h1>
<hr>
<a href="${contextPath}/">Return to Home</a>
<hr>

<a href="judges">Judge Management</a>
<br>
<a href="charges">Charge Database Management</a>
<br>
<a href="officeStats">Office Statistics</a>
<hr>

</div>

<%@ include file="../footer.jsp" %>
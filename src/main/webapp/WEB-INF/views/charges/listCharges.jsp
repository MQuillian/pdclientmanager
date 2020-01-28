<%@ include file="../header.jsp" %>

<h1>Full Charge List</h1>
<hr />
<a href="${contextPath}/charges">Return to Charge Management</a>
<hr />

<table class="table table-striped">
	<thead>
		<tr>
			<th>Name</th>
			<th>Statute Number</th>
		</tr>
	</thead>
	<c:forEach items="${chargeList}" var="charge">
		<tr>
			<td>${charge.name}</td>
			<td>${charge.statute}</td>
			<td>
				<div class="btn-group" role="group">
					<spring:url value="/charges/${charge.id}" var="chargeUrl" />
					<spring:url value="/charges/${charge.id}/update" var="updateUrl" />
					<spring:url value="/charges/${charge.id}/delete" var="deleteUrl" />
					
					<button class="btn btn-info"
						onclick="location.href='${chargeUrl}'">Details</button>
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

<%@ include file="../footer.jsp" %>
<%@ include file="../header.jsp" %>

<h1>Full Judge List</h1>
<hr />
<a href="${contextPath}/judges">Return to Judge Management</a>
<hr />

<table class="table table-striped">
	<thead>
		<tr>
			<th>Name</th>
			<th>Working Status</th>
		</tr>
	</thead>
	<c:forEach items="${judgeList}" var="judge">
		<tr>
			<td>${judge.name}</td>
			<td>${judge.workingStatus.displayText}</td>
			<td>
				<div class="btn-group" role="group">
					<spring:url value="/judges/${judge.id}" var="judgeUrl" />
					<spring:url value="/judges/${judge.id}/update" var="updateUrl" />
					<spring:url value="/judges/${judge.id}/delete" var="deleteUrl" />
					
					<button class="btn btn-info"
						onclick="location.href='${judgeUrl}'">Details</button>
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
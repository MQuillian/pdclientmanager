<%@ include file="../header.jsp" %>

<h1>Full Case List</h1>
<hr />
<a href="${contextPath}/cases">Return to Case Management</a>
<hr />

<table class="table table-striped">
	<thead>
		<tr>
			<th>Case Number</th>
			<th>Client</th>
			<th>Case Status</th>
			<th>Attorney</th>
		</tr>
	</thead>
	
	<c:forEach items="${cases}" var="courtCase">
		<tr>
			<td>${courtCase.caseNumber}</td>
			<td>${courtCase.client.name}</td>
			<td>
				<c:choose>
					<c:when test="${empty courtCase.dateClosed}">
						Open
					</c:when>
					<c:otherwise>
						Closed
					</c:otherwise>
				</c:choose>
			<td>${courtCase.attorney.name}</td>
			<td>
				<div class="btn-group" role="group">
					<spring:url value="/cases/${courtCase.id}" var="courtCaseUrl" />
					<spring:url value="/cases/${courtCase.id}/update" var="updateUrl" />
					<spring:url value="/cases/${courtCase.id}/delete" var="deleteUrl" />
					
					<button class="btn btn-info"
						onclick="location.href='${courtCaseUrl}'">Details</button>
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

<hr>
<div class="panel-footer" id="page-links-footer"></div>

<script>	
	var size = ${size};
	var page = ${page};
	var totalPages = ${totalPages};
</script>
<script src="<spring:url value="/resources/js/pageLinkScript.js" />"></script>

<%@ include file="../footer.jsp" %>
<%@ include file="../header.jsp" %>

<h1>Documents Attached to ${caseNumber}</h1>
<hr />
<a href="${contextPath}/cases">Return to Case Management</a>
<hr />

<table class="table table-striped">
	<thead>
		<tr>
			<th>File name</th>
			<th></th>
		</tr>
	</thead>
	
	<c:forEach items="${documents}" var="document">
		<tr>
			<td>${document}</td>
			<td>
				<div class="btn-group" role="group">
					<spring:url value="/documents/${caseId}/view/${document}" var="viewUrl" />
					<spring:url value="/documents/${caseId}/delete/${document}" var="deleteUrl" />
					
					<button class="btn btn-primary"
						onclick="window.open('${viewUrl}', '_blank')">View</button>
					<form:form action="${deleteUrl}" method="post">
						<button class="btn btn-danger" type="submit">Delete</button>
					</form:form>
				</div>
			</td>
		</tr>
	</c:forEach>
</table>

<%@ include file="../footer.jsp" %>
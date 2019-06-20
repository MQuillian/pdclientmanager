<%@ include file = "header.jsp" %>

<h1>Search results for ${searchTerm}</h1>
<br><br>

<table>
	<tr>
		<td><strong>Name</strong></td>
	</tr>
	<c:forEach items="${attorneys}" var="attorney">
		<tr>
			<td>${attorney.name}</td>
		</tr>
	</c:forEach>
</table>

<%@ include file = "footer.jsp" %>
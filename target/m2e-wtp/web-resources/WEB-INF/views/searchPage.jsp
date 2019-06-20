<%@ include file = "header.jsp" %>

<h1>Let's search for stuff</h1>

<form:form action="searchAttorney" method="get">
	<table>
		<tr>
			<td>Search for attorney</td>
			<td>
				<input name="searchTerm" type="text" /> <br />
			</td>
		</tr>
		<tr>
			<td colspan="2"><button type="submit">Submit</button></td>
		</tr>
	</table>
</form:form>

<%@ include file = "footer.jsp" %>
<%@ include file = "header.jsp" %>

<h1>Employee Management</h1>
<hr>
<a href="${contextPath}/">Return to Home</a>
<hr>
Current attorneys
<hr>
	<table>
		<tr>
			<td><strong>Name</strong></td>
		</tr>
		<c:forEach items="${activeAttorneys}" var="attorney">
			<tr>
				<td><a href="attorneys/${attorney.id}">${attorney.name}</a></td>
			</tr>
		</c:forEach>
	</table>
<br>
	<a href="attorneys/add">Add new attorney</a>
	<a href="attorneys">View all attorneys</a>
<hr>
Current investigators
<hr>
	<table>
		<tr>
			<td><strong>Name</strong></td>
		</tr>
		<c:forEach items="${activeInvestigators}" var="investigator">
			<tr>
				<td><a href="manageInvestigator/${investigator.id}">${investigator.name}</a></td>
			</tr>
		</c:forEach>
	</table>
<br>
	<a href="addInvestigatorForm">Add new investigator</a>
		
<%@ include file = "footer.jsp" %>
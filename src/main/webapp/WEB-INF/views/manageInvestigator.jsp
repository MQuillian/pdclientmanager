<%@ include file = "header.jsp" %>

<h1>Investigator Management - ${investigator.name}</h1>
<hr>
<a href="${contextPath}/">Return to Home</a>
<hr>
<br>
Update attorney information: <br>
<form:form action="updateInvestigator" method="post" modelAttribute="investigator">
	
	<table>
		<tr>
			<td>Name</td>
			<td>
				<form:input path="name" />
				<form:errors path="name" />
			</td>
		</tr>
		<tr>
			<td>Employment Status</td>
			<td>
				<form:radiobutton path="employmentStatus" value="ACTIVE" label="Active" />
				<form:radiobutton path="employmentStatus" value="INACTIVE" label="Inactive" />
			</td>
		</tr>
		<tr>
			<td><form:hidden path="id" /></td>
		</tr>
		<tr>
			<td colspan="2"><button type="submit">Update</button>
		</tr>
	</table>
</form:form>

<%@ include file = "footer.jsp" %>
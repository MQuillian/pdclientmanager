<%@ include file = "../header.jsp" %>

<h1>Add attorney</h1>
<form:form action="addAttorney" method="post" modelAttribute="attorney">
	<table>
		<tr>
			<td>Name:</td>
			<td>
				<form:input path="name" /> <br />
				<form:errors path="name" cssClass="error" />
			</td>
			<td>Employment status:</td>
			<td>
				<form:radiobutton path="employmentStatus" value="ACTIVE" label="Active" />
				<form:radiobutton path="employmentStatus" value="INACTIVE" label="Inactive" />
				<form:errors path="employmentStatus" cssClass="error" />
			</td>
			<td>Assigned investigator:</td>
			<td>
				<c:forEach items="${activeInvestigators}" var="investigator">
					<form:radiobutton path="investigator" label="${investigator.name}" value="${investigator.id}" />
				</c:forEach>
			</td>
		</tr>
		<tr>
			<td colspan="2"><button type="submit">Submit</button></td>
		</tr>
	</table>
</form:form>
<br>
<a href="employeeManagement">Return to Employee Management</a>

<%@ include file = "../footer.jsp" %>
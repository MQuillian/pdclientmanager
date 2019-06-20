<%@ include file = "header.jsp" %>

<h1>Add investigator</h1>
<br>
<a href="employeeManagement">Return to Employee Management</a>
<form:form action="addInvestigator" method="post" modelAttribute="investigator">
	<table>
		<tr>
			<td>Name:</td>
			<td>
				<form:input path="name" /> <br />
				<form:errors path="name" cssClass="error" />
			</td>
		<tr>
			<td>Employment status:</td>
			<td>
				<form:radiobutton path="employmentStatus" value="ACTIVE" label="Active" />
				<form:radiobutton path="employmentStatus" value="INACTIVE" label="Inactive" />
				<form:errors path="employmentStatus" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td>Assigned attorneys: </td>
			<td>
				<form:checkboxes path="assignedAttorneys" items="${activeAttorneys}" itemLabel="name" itemValue="id" />
				<form:errors path="assignedAttorneys" cssClass="error" />
			</td>
		</tr>
		<tr>
			<td colspan="2"><button type="submit">Submit</button></td>
		</tr>
	</table>
</form:form>

<%@ include file = "footer.jsp" %>
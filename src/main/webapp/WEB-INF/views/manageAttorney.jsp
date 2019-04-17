<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>PDCM - Attorney Management</title>
		<style type="text/css">
			.error {
				color: red;
			}
			table {
				width: 50%;
				border-collapse: collapse;
				border-space: 0px;
			}
			table td {
				border: 1px solid #56454;
				padding: 20px;
			}
		</style>
	</head>
	<body>
		<h1>Attorney Management - ${attorney.name}</h1>
		<br>
		Update attorney information: <br>
		<form:form action="updateAttorney" method="post" modelAttribute="attorney">
			
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
	</body>
</html>
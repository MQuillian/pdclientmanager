<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Public Defender Client Manager</title>
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
		<h1>Home</h1>
		<hr>
		CASE SEARCH UI
		<br>
		PENDING CT DATE UI
		<br>
		<a href="pendingCourt">Pending Court Dates</a>
		<br>
		<a href="caseManagement">Case Management</a>
		<br>
		<a href="employeeManagement">Employee Management</a>
		<br>
		STATISTICS UI
		<br>
		<a href="individualStats">Individual Statistics</a>
		<br>
		<a href="officeStats">Office Statistics</a>
	</body>
</html>
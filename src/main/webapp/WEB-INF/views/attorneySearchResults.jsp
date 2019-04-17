<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>PDCM - Attorney Search</title>
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
	</body>
</html>
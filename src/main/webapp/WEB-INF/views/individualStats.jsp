<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
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
		<h1>Individual Stats Page</h1>
		<hr>
		<a href="${contextPath}/">Return to Home</a>
		<hr>
		STATS REGARDING CURRENT CASELOAD E.G. NUMBER OF CASES, CLIENTS IN CUSTODY, ETC.
	</body>
</html>
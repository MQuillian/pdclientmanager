<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<link rel="stylesheet" href="<spring:url value="/resources/css/jquery-ui.min.css" />">
<link rel="stylesheet" href="<spring:url value="/resources/css/bootstrap.min.css" />">
<link rel="stylesheet" href="<spring:url value="/resources/css/global.css" />">
<script> var contextPathVar = "${pageContext.request.contextPath}"</script>
<script src="<spring:url value="/resources/js/jquery-3.4.1.js" />"></script>
<script src="<spring:url value="/resources/js/jquery-ui.min.js" />"></script>
<script src="<spring:url value="/resources/js/bootstrap.min.js" />"></script>
</head>
<body>
<div class="container">
	<c:if test="${param.error == true}">
		<div class="alert alert-danger alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">x</span>
			</button>
			<strong><c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" /></strong>
		</div>
	</c:if>
	<c:if test="${param.logout == true}">
		<div class="alert alert-success alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">x</span>
			</button>
			<strong>Successfully logged out</strong>
		</div>
	</c:if>
   <h1>Custom Login Page</h1>
   <form name='f' action="/pdclientmanager/handle_login" method='POST'>
      <table>
         <tr>
            <td>User:</td>
            <td><input type='text' name='username' value=''></td>
         </tr>
         <tr>
            <td>Password:</td>
            <td><input type='password' name='password' /></td>
         </tr>
         <tr>
            <td><input name="submit" type="submit" value="submit" /></td>
         </tr>
      </table>
  </form>
</div>
</body>
</html>
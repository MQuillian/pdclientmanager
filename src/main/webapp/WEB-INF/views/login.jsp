<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log In</title>
<link rel="stylesheet" href="<spring:url value="/resources/css/jquery-ui.min.css" />">
<link rel="stylesheet" href="<spring:url value="/resources/css/bootstrap.min.css" />">
<link rel="stylesheet" href="<spring:url value="/resources/css/global.css" />">
<script> var contextPathVar = "${pageContext.request.contextPath}"</script>
<script src="<spring:url value="/resources/js/jquery-3.4.1.js" />"></script>
<script src="<spring:url value="/resources/js/jquery-ui.min.js" />"></script>
<script src="<spring:url value="/resources/js/bootstrap.min.js" />"></script>
</head>
<body>

<div class="login-img">
<div class="background-mask">
<div class="container" style="padding-top:15px">
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
	<div class="container">
		<div class="d-flex justify-content-center h-100" style="padding-top:20vh">
			<div class="card" style="background-color:rgba(255,255,255,0.9)">
				<div class="card-header">
					<h3>Login</h3>
				</div>
				<div class="card-body">
					<form name="f" action="${contextPath}/handle_login" method="POST">
						<div class="input-group form-group">
							<input type="text" name="username" class="form-control" placeholder="Username">
						</div>
						<div class="input-group form-group">
							<input type="password" name="password" class="form-control" placeholder="Password">
						</div>
						<div class="form-group">
							<input type="submit" value="Submit" class="btn float-right login_btn">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
</div>
</body>
</html>
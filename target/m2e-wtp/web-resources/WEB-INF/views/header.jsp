<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Public Defender Client Manager</title>
		<!-- Despite having all webjars dependencies in pom.xml and on classpath, 
		and adding a resource handler as necessary, it's still broke AF
		 so we're doing it this way until further notice. All dependencies/resource handlers
		 will remain as comments to denote their use and to (hopefully) be
		 used at a later time... -->
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<script src="//code.jquery.com/jquery-1.12.4.js"></script>
  		<script src="//code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
	</head>
	<body>
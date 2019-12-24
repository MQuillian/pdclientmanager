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
		<link rel="stylesheet" href="<spring:url value="/resources/css/jquery-ui.min.css" />">
		<link rel="stylesheet" href="<spring:url value="/resources/css/bootstrap.min.css" />">
		<link rel="stylesheet" href="<spring:url value="/resources/css/universal-form.css" />">
		<script> var contextPathVar = "${pageContext.request.contextPath}"</script>
		<script src="<spring:url value="/resources/js/jquery-3.4.1.js" />"></script>
  		<script src="<spring:url value="/resources/js/jquery-ui.min.js" />"></script>
		<script src="<spring:url value="/resources/js/bootstrap.min.js" />"></script>
	</head>
	<body>
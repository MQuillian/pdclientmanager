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
		<link rel="stylesheet" href="<spring:url value="/resources/css/global.css" />">
		<script> var contextPathVar = "${pageContext.request.contextPath}"</script>
		<script src="<spring:url value="/resources/js/jquery-3.4.1.js" />"></script>
  		<script src="<spring:url value="/resources/js/jquery-ui.min.js" />"></script>
		<script src="<spring:url value="/resources/js/bootstrap.min.js" />"></script>
	</head>
	<body>
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
		  <a class="navbar-brand" href="${contextPath}">PDClientManager</a>
		
		  <div class="collapse navbar-collapse" id="navbarSupportedContent">
		    <ul class="navbar-nav mr-auto">
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="casesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		        	Cases
		        </a>
		        <div class="dropdown-menu" aria-labelledby="casesDropdown">
		        	<a class="dropdown-item" href="${contextPath}/cases">Case management</a>
		        	<a class="dropdown-item" href="${contextPath}/cases/list">Full list</a>
		        	<a class="dropdown-item" href="${contextPath}/cases/add">Add case</a>
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="casesDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		        	Clients
		        </a>
		        <div class="dropdown-menu" aria-labelledby="casesDropdown">
		        	<a class="dropdown-item" href="${contextPath}/clients">Client management</a>
		        	<a class="dropdown-item" href="${contextPath}/clients/list">Full list</a>
		        	<a class="dropdown-item" href="${contextPath}/clients/add">Add client</a>
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          Employees
		        </a>
		        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
		          <a class="dropdown-item" href="${contextPath}/attorneys">Attorneys</a>
		          <a class="dropdown-item" href="${contextPath}/investigators">Investigators</a>
		          <div class="dropdown-divider"></div>
		          <a class="dropdown-item" href="#">Something else here</a>
		        </div>
		      </li>
		    </ul>
		    <form:form class="form-inline my-2 my-lg-0" method="get" modelAttribute="searchTerm" action="${contextPath}/cases/list/searchResults">
		      <input class="form-control mr-sm-2" type="search" name="q" placeholder="Search cases" aria-label="Search cases">
		      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
		    </form:form>
		    <a href="${contextPath}/logout" class="btn btn-danger ml-2 my-sm-0">
	          Logout
	        </a>
		  </div>
		</nav>
		
		<div class="container">

		<c:if test="${not empty msg}">
			<div class="alert alert-${css} alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">x</span>
			</button>
			<strong>${msg}</strong>
			</div>
		</c:if>
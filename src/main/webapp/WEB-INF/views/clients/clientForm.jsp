<%@ include file="../header.jsp" %>

<div class="container">

<c:choose>
	<c:when test="${clientForm['new']}">
		<h1>Add Client</h1>
	</c:when>
	<c:otherwise>
		<h1>Update Client</h1>
	</c:otherwise>
</c:choose>
<hr />
<a href="${contextPath}/clients">Return to Client Management</a>
<hr />

<spring:url value="/clients" var="clientActionUrl" />

<form:form class="form-horizontal" method="post"
	modelAttribute="clientForm" action="${clientActionUrl}">
	
	<form:hidden path="id" />
	
	<spring:bind path="name">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Name</label>
			<div class="col-sm-10">
				<form:input path="name" type="text" class="form-control"
					id="name" placeholder="Name" />
				<form:errors path="name" class="control-label" />
			</div>
		</div>
	</spring:bind>
	
	<spring:bind path="custodyStatus">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Custody Status</label>
			<div class="col-sm-10">
				<label class="radio-inline">
					<form:radiobutton path="custodyStatus" value="IN_CUSTODY" />In custody
				</label>
				<label class="radio-inline">
					<form:radiobutton path="custodyStatus" value="OUT_ON_BOND" />Out on bond
				</label>
				<br />
				<form:errors path="custodyStatus" class="control-label" />
			</div>
		</div>
	</spring:bind>
	
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<c:choose>
				<c:when test="${clientForm['new']}">
					<button type="submit" class="btn-lg btn-primary pull-right">Add</button>
				</c:when>
				<c:otherwise>
					<button type="submit" class="btn-lg btn-primary pull-right">Update</button>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</form:form>

</div>
<%@ include file="../footer.jsp" %>
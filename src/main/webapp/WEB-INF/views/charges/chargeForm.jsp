<%@ include file="../header.jsp" %>

<c:choose>
	<c:when test="${chargeForm['new']}">
		<h1>Add Charge</h1>
	</c:when>
	<c:otherwise>
		<h1>Update Charge</h1>
	</c:otherwise>
</c:choose>
<hr />
<a href="${contextPath}/charges">Return to Charge Management</a>
<hr />

<spring:url value="/charges" var="chargeActionUrl" />

<form:form class="form-horizontal" method="post"
	modelAttribute="chargeForm" action="${chargeActionUrl}">
	
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
	
	<spring:bind path="statute">
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-2 control-label">Statute number</label>
			<div class="col-sm-10">
				<form:input path="statute" type="text" class="form-control"
					id="statute" placeholder="Statute number" />
				<form:errors path="statute" class="control-label" />
			</div>
		</div>
	</spring:bind>
	
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<c:choose>
				<c:when test="${chargeForm['new']}">
					<button type="submit" class="btn-lg btn-primary pull-right">Add</button>
				</c:when>
				<c:otherwise>
					<button type="submit" class="btn-lg btn-primary pull-right">Update</button>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</form:form>

<%@ include file="../footer.jsp" %>
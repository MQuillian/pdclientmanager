<%@ include file="../header.jsp" %>

<fmt:setBundle basename="/pdclientmanager/src/main/resources/messages.properties" var="lang"/>

<h1>Caseload Reassignment</h1>
<hr />
<a href="${contextPath}/cases">Return to Case Management</a>
<hr />

<spring:url value="/cases/reassignment" var="reassignmentUrl" />

<form:form class="form-horizontal" modelAttribute="reassignmentForm" method="post" action="${reassignmentUrl}">
    
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-5 control-label">Reassign caseload from:</label>
			<div class="col-sm-2">
				<select name="prevAssignedAttorneyId" size="5" required>
					<c:forEach var="attorney"  items="${activeAttorneys}">
						<option value="${attorney.id}">${attorney.name}</option>
					</c:forEach>
				</select>
			</div>
			<br />
		</div>
	
		<div class="form-group ${status.error ? 'has-error' : ''}">
			<label class="col-sm-5 control-label">Reassign caseload to:</label>
			<div class="col-sm-5">
				<select name="newAssignedAttorneyId" size="5" required>
					<c:forEach var="attorney"  items="${activeAttorneys}">
						<option value="${attorney.id}">${attorney.name}</option>
					</c:forEach>
				</select>
			</div>
			<br />
		</div>
		
		<div class="form-group">
			<label class="col-sm-5 control-label">Select cases to reassign:</label>
			<div class="col-sm-10">
				<div class="radio">
					<input type="radio" name="reassignedCases" value="openCases" checked />Open cases only
				</div>
				<div class="radio">
					<input type="radio" name="reassignedCases" value="allCases" />Open AND closed cases
				</div>
				<br />
			</div>
		</div>
		
		<button type="submit" class="btn-lg btn-primary pull-right">Reassign caseload</button>
</form:form>

<%@ include file="../footer.jsp" %>
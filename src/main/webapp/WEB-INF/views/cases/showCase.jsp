<%@ include file="../header.jsp" %>

<div class="container">

<c:if test="${not empty msg}">
	<div class="alert alert-${css} alert-dismissible" role="alert">
		<button type="button" class="close" data-dismiss="alert" aria-label="Close">
			<span aria-hidden="true">x</span>
		</button>
		<strong>${msg}</strong>
	</div>
</c:if>

<h1>Case Details</h1>
<hr />
<a href="${contextPath}/caseManagement">Return to Case Management</a>
<hr />

<div class="row">
	<label class="col-sm-2">Case Number</label>
	<div class="col-sm-10">${courtCase.caseNumber}</div>
</div>

<div class="row">
	<label class="col-sm-2">Client</label>
	<spring:url value="/clients/${courtCase.client.id}" var="clientUrl" />
	<div class="col-sm-10">
		<a href="${clientUrl}">${courtCase.client.name}</a>
	</div>
</div>

<div class="row">
	<label class="col-sm-2">Case Status</label>
	<div class="col-sm-10">${courtCase.caseStatus}</div>
</div>

<div class="row">
	<label class="col-sm-2">Attorney</label>
	<spring:url value="/attorneys/${courtCase.attorney.id}" var="attorneyUrl" />
	<div class="col-sm-10">
		<a href="${attorneyUrl}">${courtCase.attorney.name}</a>
	</div>
</div>

<div class="row">
	<label class="col-sm-2">Date Opened</label>
	<div class="col-sm-10">${courtCase.dateOpened}</div>
	<label class="col-sm-2">Date Closed</label>
	<c:choose>
		<c:when test="${courtCase.dateClosed != NULL}">
			<div class="col-sm-10">${courtCase.dateClosed}</div>
		</c:when>
	</c:choose>
</div>

<div class="row">
	<label class="col-sm-2">Charged Counts</label>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>Count</th>
				<th>Statute</th>
				<th>Charge</th>
			</tr>
		</thead>
		<c:forEach items="${courtCase.chargedCounts}" var="chargedCount">
			<tr>
				<td>
					<c:out value="${chargedCount.value.countNumber}"/>
				</td>
				<td>
					<c:out value="${chargedCount.value.charge.statute}"/>
				</td>
				<td>
					<c:out value="${chargedCount.value.charge.name}"/>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
<br />
	
</div>
<%@ include file="../header.jsp" %>

<fmt:setBundle basename="/pdclientmanager/src/main/resources/messages.properties" var="lang"/>

<c:choose>
	<c:when test="${caseForm['new']}">
		<h1>Add Case</h1>
	</c:when>
	<c:otherwise>
		<h1>Update Case</h1>
	</c:otherwise>
</c:choose>
<hr />
<a href="${contextPath}/cases">Return to Case Management</a>
<hr />

<spring:url value="/cases" var="caseActionUrl" />

<form:form class="form-horizontal" modelAttribute="caseForm" method="post" action="${caseActionUrl}">
    
    <fmt:bundle basename="messages">
    
    <form:input type="hidden" path="id" name="id" id="formId" />
    
    <div class="row">
    	<form:errors cssClass="error"/>
    </div>
    
    <div class="row">
    	<label for="dateOpened">Date Opened: </label>
    	<form:input type="text" path="dateOpened" name="dateOpened" id="dateOpened" />
    	<form:errors path="dateOpened" cssClass="error"/>
    </div>
    
    <div class="row">
    	<label for="dateClosed">Date Closed: </label>
    	<form:input type="text" path="dateClosed" name="dateClosed" id="dateClosed" />
    	<form:errors path="dateClosed" cssClass="error" />
    </div>
    
    <div class="row">
    	<label for="caseNumber">Case Number: </label>
    	<form:input type="text" path="caseNumber" name="caseNumber" id="caseNumber" />
    	<form:errors path="caseNumber" cssClass="error"/>
   	</div>
    
    <div class="row">
    	<label for="clientId">Client Name: </label>
    	<form:input type="text" path="clientName" id="clientName"/>
    	<form:input type="hidden" path="clientId" id="clientHiddenField" />
    	<form:errors path="clientId" cssClass="error"/>
    </div>
    
    <div class="row">
    	<label for="judgeId">Judge Name: </label>
    	<form:select path="judgeId" id="judgeId" items="${activeJudges}"
    		itemLabel="name" itemValue="id" />
    	<form:errors path="judgeId" cssClass="error" />
   	</div>
   	
   	<div class="row">
   		<label for="attorneyId">Attorney Name: </label>
   		<form:select path="attorneyId" id="attorneyId" items="${activeAttorneys}"
   			itemLabel="name" itemValue="id" />
   		<form:errors path="attorneyId" cssClass="error" />
 	</div>
	
	<div class="row">
        <div class="large-8 large-offset-2 columns">
        	<form:errors path="chargedCountsIds" cssClass="error" />
	            <table id="myTable" class="hover large-12 columns">
	              <thead>
	                <tr>
	                  <th>Charged Counts</th>
	                </tr>
	              </thead>
	              <tbody id="chargeTable">
	              	<c:forEach items="${caseForm.chargedCountsIds}" var="chargedCount" varStatus="status">
	              		<tr>
	              			<td>
	              				<c:set var="countNumber" value="${chargedCount.key}" />
	              				<label for="count${countNumber}">Count ${countNumber}</label>
	              				<input id="count${chargedCount.key}"
	              					name="chargedCountsStrings[${countNumber}]" 
	              					class="charge-input ui-autocomplete-input"
	              					autocomplete="off"
	              					value="${caseForm.chargedCountsStrings[countNumber]}">
              					<input id="count${countNumber}hidden" type="hidden"
              						name="chargedCountsIds[${countNumber}]"
              						class="hidden-charge-input"
              						value="${chargedCount.value}">
           						<c:choose>
	              					<c:when test="${chargedCount.value == null }">
	              						<span id="count${countNumber}.errors" class="error">
	              							<fmt:message key="NotEmpty.caseForm.chargedCountsIds" />
	              						</span>
	              					</c:when>
	              				</c:choose>
	              			</td>
              			</tr>
	              	</c:forEach>
	              </tbody>
	            </table>
        </div>    
    </div>       
      
    <div class="row">
        <div class="large-4 large-offset-2 columns">
            <button type="button" class="button expanded" onclick="newRow()">Add new row</button>
        </div>
        <div class=" large-4 large-offeset-2 columns">
            <button type="button" class="button expanded" onclick="deleteRow()">Delete latest row</button>
        </div>
    </div>
<br>
	
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<c:choose>
				<c:when test="${caseForm['new']}">
					<button type="submit" id="save" class="btn-lg btn-primary pull-right">Add</button>
				</c:when>
				<c:otherwise>
					<button type="submit" id="save" class="btn-lg btn-primary pull-right">Update</button>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	
	<div>
		<label>"${clientNameJson}"</label>
	</div>
	
	</fmt:bundle>
</form:form>

<script src="<spring:url value="/resources/js/caseFormScript.js" />"></script>
<%@ include file="../footer.jsp" %>
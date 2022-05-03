<%@ include file="../header.jsp" %>

<fmt:setBundle basename="/pdclientmanager/src/main/resources/messages.properties" var="lang"/>

<hr />
<a href="${contextPath}/calendar/management">Return to Calendar Management</a>
<hr />

<spring:url value="/calendar/multipleEvents" var="calendarActionUrl" />

<form:form id="multiEventForm" modelAttribute="caseEventsList" method="post" action="${calendarActionUrl}" >
    
    <fmt:bundle basename="messages">
    
    <div class="form-row">
    	<form:errors cssClass="error"/>
    </div>
    <div class="form-row">
	    <div class="form-group col-md-3">
	    	<label for="startTime">Court start date/time: </label>
	    	<input type="datetime-local" name="startTime" id="startTime" class="form-control" required/>
	    	<span id="startTime.errors" class="error"></span>
	    </div>
	    <div class="form-group col-md-3">
	    	<label for="endTime">Court end date/time: </label>
	    	<input type="datetime-local" name="endTime" id="endTime" class="form-control" required/>
    		<span id="endTime.errors" class="error"></span>
		</div>
    </div>
	
	<div class="form-row">
        <div>
            <table id="myTable" class="table-striped col-xs-12">
                <thead>
	               <tr>
	                   <th scope="col">#</th>
	                   <th scope="col">Case Number</th>
	                   <th scope="col">Client</th>
	                   <th scope="col">Attorney</th>
	               </tr>
                </thead>
              	<tbody id="caseEventTable">
              	</tbody>
            </table>
        </div>    
    </div>       
  
    <div class="form-row">
        <div class="col-xs-3">
            <button type="button" class="btn btn-secondary" onclick="newRow()">Add new row</button>
        </div>
        <div class="col-xs-3">
            <button type="button" class="btn btn-secondary" onclick="deleteRow()">Delete latest row</button>
        </div>
    </div>
	<form:errors path="caseEvents" class="control-label" cssClass="error" />
	<span id="submitValidationErrors" cssClass="error"></span>
<br>
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<button type="submit" id="save" class="btn-lg btn-primary pull-right">Save events</button>
		</div>
	</div>
	
	</fmt:bundle>
</form:form>

<script src="<spring:url value="/resources/js/multiCaseEventFormScript.js" />"></script>
<%@ include file="../footer.jsp" %>
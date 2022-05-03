<%@ include file="../header.jsp" %>

<fmt:setBundle basename="/pdclientmanager/src/main/resources/messages.properties" var="lang"/>

<hr />
<a href="${contextPath}/calendar/management">Return to Calendar Management</a>
<hr />

<spring:url value="/calendar/singleEvent" var="calendarActionUrl" />

<form:form id="caseEventForm" modelAttribute="caseEvent" method="post" action="${calendarActionUrl}">
    
    <fmt:bundle basename="messages">
    
    <form:hidden id="eventId" path="id" />
    
    <div class="form-row">
    	<form:errors cssClass="error"/>
    </div>
    
    <div class="form-row">
	    <div class="form-group col-md-3">
	    	<label for="startTime">Event start date/time: </label>
	    	<form:input type="datetime-local" path="startTime" id="startTime" max="9999:12:31T23:59" class="form-control" required="true"/>
	    	<span id="startTime.errors" class="error"></span>
	    </div>
	    <div class="form-group col-md-3">
	    	<label for="endTime">Event end date/time: </label>
	    	<form:input type="datetime-local" path="endTime" id="endTime" max="9999:12:31T23:59" class="form-control" required="true"/>
    		<span id="endTime.errors" class="error"></span>
		</div>
    </div>
    
    <div class="form-row">
        <div>
            <table id="myTable" class="table-striped col-xs-12">
                <thead>
	               <tr>
	                   <th scope="col">Case Number</th>
	                   <th scope="col">Client</th>
	                   <th scope="col">Attorney</th>
	                   <th scope="col">Custody Status</th>
	               </tr>
                </thead>
              	<tbody id="caseEventTable">
              		<tr>
              			<td>
              				<form:input id="caseNumberInput" path="caseNumber" type="text" class="form-control"
							placeholder="Case number" />
							<form:input id="caseIdField" path="caseId" type="hidden" />
              			</td>
              			<td>
              				<span id="clientField"></span>
              			</td>
              			<td>
              				<form:input id="attorneyField" path="attorney" type="text" class="form-control" readonly="true" />
           				</td>
           				<td>
           					<span id="custodyField"></span>
						</td>
              		</tr>
              	</tbody>
            </table>
        </div>  
    </div>
    <span id="attorney.errors" class="error"></span>
    <form:errors path="attorney" class="control-label" cssClass="error"/>
<br> 
	
	<div class="form-group">
		<label class="col-sm-2 control-label">Event type</label>
		<div class="col-sm-10">
			<label class="radio-inline">
				<form:radiobutton path="description" value="Court appearance" />Court appearance
			</label>
			<label class="radio-inline">
				<form:radiobutton path="description" value="Appointment" />Appointment
			</label>
			<br />
			<form:errors path="description" class="control-label" cssClass="error" />
		</div>
	</div>    
<br>
	
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<button type="submit" id="save" class="btn-lg btn-primary pull-right">Save event</button>
		</div>
	</div>
	
	</fmt:bundle>
</form:form>

<script src="<spring:url value="/resources/js/singleCaseEventFormScript.js" />"></script>
<%@ include file="../footer.jsp" %>
const caseAutocompleteOptions = {
		source : function(request, response) {
			$.ajax({
				url : contextPathVar + "/autocomplete/casesByCaseNumber",
				dataType : "json",
				data : {
					q : request.term
				},
				success : function(data) {
					response(data);
				}
			});
		},
		minLength : 4,
		focus: function( event, ui ) {
		      event.preventDefault();
		      $(this).val(ui.item.label);
	    },
	    change: function (event, ui) {
	        if (!ui.item) {
	            this.value = '';
	            
	            const clientField = this.id + ".client";
	            $('#' + clientField).text('');
	            
	            const attorneyField = this.id + ".attorney";
	            $('#' + attorneyField).val('');
	            
	            const custodyField = this.id + ".custodyStatus";
	            $('#' + custodyField).text('');
	            
	            const errorField = this.id + ".errors";
	            document.getElementById(errorField).innerHTML = 'Please enter a valid case number';
        	}
	    },
	    select: function( event, ui ) {
		     event.preventDefault();
		     $(this).val(ui.item.caseNumber);
	            
     		 const clientField = this.id + ".client";
             document.getElementById(clientField).innerHTML = ui.item.clientName;
            
             const attorneyField = this.id + ".attorney";
             document.getElementById(attorneyField).value = ui.item.attorneyName;
             
             const custodyField = this.id + ".custodyStatus";
	         document.getElementById(custodyField).innerHTML = ui.item.custodyStatus;
	    }
	}

function updateFormOnStartTimeChange() {
	const startTimeValue = document.getElementById('startTime').value;
	
	const defaultEndTime = startTimeValue.substring(0, 11).concat("17:00");
	document.getElementById('endTime').value = defaultEndTime;
}

function validateEndTime() {
	const endTimeElement = document.getElementById('endTime');
	const endTimeValue = endTimeElement.value;
	const startTimeValue = document.getElementById('startTime').value;
	const endTimeIsValid = true;
	
	const endTimeYear = endTimeValue.substring(0,4);
	const startTimeYear = startTimeValue.substring(0,4);

	if(endTimeYear < startTimeYear) {
		endTimeIsValid = false;
		
	} else if(endTimeYear === startTimeYear) {
		const endTimeMonth = endTimeValue.substring(5,7);
		const startTimeMonth = startTimeValue.substring(5,7);
		
		if(endTimeMonth < startTimeMonth) {
			endTimeIsValid = false;
			
		} else if(endTimeMonth === startTimeMonth) {
			const endTimeDay = endTimeValue.substring(8,10);
			const startTimeDay = startTimeValue.substring(8,10);
			
			if(endTimeDay < startTimeDay) {
				endTimeIsValid = false;
				
			} else if(endTimeDay === startTimeDay) {
				const endTimeHour = endTimeValue.substring(11,13);
				const startTimeHour = startTimeValue.substring(11,13);
				
				if(endTimeHour < startTimeHour) {
					endTimeIsValid = false;
					
				} else if(endTimeHour === startTimeHour) {
					const endTimeMinute = endTimeValue.substring(14,16);
					const startTimeMinute = startTimeValue.substring(14,16);
					
					if(endTimeMinute <= startTimeMinute) {
						endTimeIsValid = false;
					}
				}
			}
		}
	}
	
	if(endTimeIsValid) {
		handleValidEndTime();
	} else {
		handleEndTimeError(endTimeElement);
	}
}

function handleEndTimeError(endTimeElement) {
	endTimeElement.value = '';
	document.getElementById('endTime.errors').innerHTML = 'Please enter an end time AFTER the selected start time';
}

function handleValidEndTime() {
	document.getElementById('endTime.errors').innerHTML = '';
}

function newRow() {
    const table = document.getElementById("caseEventTable");
    const row = table.insertRow(table.getElementsByTagName("tr").length);
    const caseEventNumber = table.getElementsByTagName("tr").length - 1;
    
    // CREATE LABEL FOR NEW ROW
    const cell0 = row.insertCell(0);    
    const label = document.createElement('LABEL');
	label.htmlFor = "caseEvent" + caseEventNumber;
	label.innerText = (caseEventNumber + 1) + ". ";
    cell0.appendChild(label);
        
     // CREATE HIDDEN INPUTS TO STORE TIMES AND INVESTIGATOR IN NEW ROW
    const startTimeInput = document.createElement('INPUT');
	startTimeInput.id = "startTime" + caseEventNumber + "hidden";
	startTimeInput.type = "hidden";
	startTimeInput.name = "caseEvents[" + caseEventNumber + "].startTime";
	startTimeInput.max = "9999:12:31T23:59";
	cell0.appendChild(startTimeInput);
    	
	const endTimeInput = document.createElement('INPUT');
	endTimeInput.id = "endTime" + caseEventNumber + "hidden";
	endTimeInput.type = "hidden";
	endTimeInput.name = "caseEvents[" + caseEventNumber + "].endTime";
	endTimeInput.max = "9999:12:31T23:59";
	cell0.appendChild(endTimeInput);
	
	const descriptionInput = document.createElement('INPUT');
	descriptionInput.id = "descriptionInput" + caseEventNumber + "hidden";
	descriptionInput.type = "hidden";
	descriptionInput.name = "caseEvents[" + caseEventNumber + "].description";
	descriptionInput.value = "Court appearance";
	cell0.appendChild(descriptionInput);

    // CREATE INPUT FOR NEW ROW
	const cell1 = row.insertCell(1);
    const caseNumberInput = document.createElement('INPUT');
    caseNumberInput.id = "caseEvent" + caseEventNumber;
    caseNumberInput.name= "caseEvents[" + caseEventNumber + "].caseNumber";
    caseNumberInput.classList.add('caseEventCaseNumberInput');
    caseNumberInput.placeholder = "Case number";
    $(caseNumberInput).autocomplete(caseAutocompleteOptions);
    cell1.appendChild(caseNumberInput);
    
    // CREATE SPAN FOR CASEEVENT INFO
    const cell2 = row.insertCell(2);
    const clientSpan = document.createElement('SPAN');
    clientSpan.id = "caseEvent" + caseEventNumber + ".client";
    clientSpan.innerHTML = "";
    cell2.appendChild(clientSpan);
    
 // CREATE INPUT FOR NEW ROW
	const cell3 = row.insertCell(3);
    const attorneyInput = document.createElement('INPUT');
    attorneyInput.id = "caseEvent" + caseEventNumber + ".attorney";
    attorneyInput.name= "caseEvents[" + caseEventNumber + "].attorney";
    attorneyInput.setAttribute('disabled', true);
    attorneyInput.classList.add('attorneyInput','form-control-plaintext','padding-m');
    attorneyInput.placeholder = "";
    cell3.appendChild(attorneyInput);
    
 // CREATE SPAN FOR CUSTODYSTATUS
    const cell4 = row.insertCell(4);
    const custodySpan = document.createElement('SPAN');
    custodySpan.id = "caseEvent" + caseEventNumber + ".custodyStatus";
    custodySpan.innerHTML = "";
    cell4.appendChild(custodySpan);
    	
    // CREATE ERROR FIELD FOR NEW ROW
   	const errorField = document.createElement('DIV');
	errorField.id = "caseEvent" + caseEventNumber + ".errors";
	errorField.classList.add('error');
	cell1.appendChild(errorField);
}
	
function deleteRow(){
    const table = document.getElementById("caseEventTable");
    const rowCount = table.rows.length;

    if(rowCount>1){            
        table.deleteRow(-1);
    }
}

// Set up form and create/populate fields as needed
(function(){
	//Set min attribute of startTime
	const currentDateString = new Date().toISOString();
	const minDateString = currentDateString.substring(0, 11).concat("00:00");
	document.getElementById('startTime').setAttribute("min", minDateString)
	
	//Start event listeners on startTime and endTime
	document.getElementById('startTime').addEventListener('focusout', 
			function() {
		updateFormOnStartTimeChange();
	});
	document.getElementById('endTime').addEventListener('focusout', 
			function() {
		validateEndTime();
	});
	
	document.getElementById('multiEventForm').addEventListener('submit',
			function() {
		const caseEvents = document.getElementById('caseEventTable').rows;
		const startTime = document.getElementById('startTime').value;
		const endTime = document.getElementById('endTime').value;

		for(i = 0; i < caseEvents.length; i++) {
			document.getElementById('startTime' + i + 'hidden').value = startTime;
			document.getElementById('endTime' + i + 'hidden').value = endTime;
		}
	});
	
	newRow();
})();
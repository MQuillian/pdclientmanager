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
	            
	            $('#clientField').text('');
	            $('#attorneyField').val('');
	            $('#custodyField').text('');
	            document.getElementById('errorField').innerHTML = 'Please enter a valid case number';
        	}
	    },
	    select: function( event, ui ) {
		     event.preventDefault();
		     $(this).val(ui.item.caseNumber);
	            
             document.getElementById('clientField').innerHTML = ui.item.clientName;
             document.getElementById('attorneyField').value = ui.item.attorneyName;
	         document.getElementById('custodyField').innerHTML = ui.item.custodyStatus;
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

// Set up form and create/populate fields as needed
(function(){
	//Set min attribute of startTime
	const currentDateString = new Date().toISOString();
	const minDateString = currentDateString.substring(0, 11).concat("00:00");
	
	$('#caseNumberInput').autocomplete(caseAutocompleteOptions);
	
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
	
	if(document.getElementById('eventId').value !== '') {
		$.ajax({url: contextPathVar + "/autocomplete/casesByCaseNumber",
			dataType : "json",
			data : {
				q : document.getElementById('caseNumberInput').value
			},
			success: function(result){
    			document.getElementById('clientField').innerHTML = result[0].clientName;
    			document.getElementById('custodyField').innerHTML = result[0].custodyStatus;
  }});
	}
})();
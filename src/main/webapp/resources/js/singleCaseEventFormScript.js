var caseAutocompleteOptions = {
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
	let startTimeValue = document.getElementById('startTime').value;
	
	let defaultEndTime = startTimeValue.substring(0, 11).concat("17:00");
	document.getElementById('endTime').value = defaultEndTime;
}

function validateEndTime() {
	let endTimeElement = document.getElementById('endTime');
	let endTimeValue = endTimeElement.value;
	let startTimeValue = document.getElementById('startTime').value;
	let endTimeIsValid = true;
	
	let endTimeYear = endTimeValue.substring(0,4);
	let startTimeYear = startTimeValue.substring(0,4);

	if(endTimeYear < startTimeYear) {
		endTimeIsValid = false;
		
	} else if(endTimeYear === startTimeYear) {
		let endTimeMonth = endTimeValue.substring(5,7);
		let startTimeMonth = startTimeValue.substring(5,7);
		
		if(endTimeMonth < startTimeMonth) {
			endTimeIsValid = false;
			
		} else if(endTimeMonth === startTimeMonth) {
			let endTimeDay = endTimeValue.substring(8,10);
			let startTimeDay = startTimeValue.substring(8,10);
			
			if(endTimeDay < startTimeDay) {
				endTimeIsValid = false;
				
			} else if(endTimeDay === startTimeDay) {
				let endTimeHour = endTimeValue.substring(11,13);
				let startTimeHour = startTimeValue.substring(11,13);
				
				if(endTimeHour < startTimeHour) {
					endTimeIsValid = false;
					
				} else if(endTimeHour === startTimeHour) {
					let endTimeMinute = endTimeValue.substring(14,16);
					let startTimeMinute = startTimeValue.substring(14,16);
					
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
	let currentDateString = new Date().toISOString();
	let minDateString = currentDateString.substring(0, 11).concat("00:00");
	
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
})();
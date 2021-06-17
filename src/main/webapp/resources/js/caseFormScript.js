const clientAutocompleteOptions = {
		source : function(request, response) {
			$.ajax({
				url : contextPathVar + "/autocomplete/clientsByName",
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
	            
	            $('#clientHiddenField').val('');
	            if(document.getElementById('clientId.errors') === null) {
	            	$(this).after("<span id='clientId.errors' class='error'></span>");
	            }
	            
	            document.getElementById('clientId.errors').innerHTML = 'Please enter an existing client';
        	}
	    },
	    select: function( event, ui ) {
		     event.preventDefault();
		     $(this).val(ui.item.label);
		     $('#clientHiddenField').val(ui.item.value);
		     if(document.getElementById('clientId.errors') !== null) {
		    	 document.getElementById('clientId.errors').innerHTML = '';
		     }
	    }
	}

const chargeAutocompleteOptions = {
		source : function(request, response) {
			$.ajax({
				url : contextPathVar + "/autocomplete/chargesByNameOrStatute",
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
	            
	            var hiddenField = this.id + "hidden";
	            $('#' + hiddenField).val('');
	            
	            var errorField = this.id + ".errors";
	            document.getElementById(errorField).innerHTML = 'Please enter a charge';
        	}
	    },
	    select: function( event, ui ) {
		     event.preventDefault();
		     $(this).val(ui.item.label);
		     var hiddenField = this.id + "hidden";
		     $('#' + hiddenField).val(ui.item.value);
		     var errorField = this.id + ".errors";
		     document.getElementById(errorField).innerHTML = '';
	    }
	}

function newRow() {
    const table = document.getElementById("chargeTable");
    const row = table.insertRow(table.getElementsByTagName("tr").length);
    const countNumber = table.getElementsByTagName("tr").length;
    
    // CREATE LABEL FOR NEW ROW
     cell1 = row.insertCell(0);    
    const label = document.createElement('LABEL');
    		label.htmlFor = "count" + countNumber;
    		label.innerText = "Count " + countNumber + ": ";
        cell1.appendChild(label); 

    // CREATE INPUT FOR NEW ROW
    const input = document.createElement('INPUT');
        input.id = "count" + countNumber;
        input.name= "chargedCountsStrings[" + countNumber + "]";
        input.classList.add('charge-input');
        $(input).autocomplete(chargeAutocompleteOptions);
        cell1.appendChild(input);
        
    // CREATE HIDDEN FIELD TO STORE SELECTION IN NEW ROW
    const chargeIdInput = document.createElement('INPUT');
    	chargeIdInput.id = "count" + countNumber + "hidden";
    	chargeIdInput.type="hidden";
    	chargeIdInput.name = "chargedCountsIds[" + countNumber + "]";
    	chargeIdInput.classList.add('hidden-charge-input');
    	cell1.appendChild(chargeIdInput);
    	
    // CREATE ERROR FIELD FOR NEW ROW
   	const errorField = document.createElement('DIV');
    	errorField.id = "count" + countNumber + ".errors";
    	errorField.classList.add('error');
    	cell1.appendChild(errorField);
}
	
function deleteRow(){
    const table = document.getElementById("chargeTable");
    const rowCount = table.rows.length;

    if(rowCount>1){            
        table.deleteRow(-1);
    }
}

// Set up form and create/populate fields as needed
(function(){
	$('#clientName').autocomplete(clientAutocompleteOptions);
	if(document.getElementById("formId").value === '') {
		newRow();
	} else {
		$(".charge-input").autocomplete(chargeAutocompleteOptions);
	}
})();
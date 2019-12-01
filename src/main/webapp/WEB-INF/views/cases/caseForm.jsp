<%@ include file="../header.jsp" %>
<div class="container">

<c:choose>
	<c:when test="${caseForm['new']}">
		<h1>Add Case</h1>
	</c:when>
	<c:otherwise>
		<h1>Update Case</h1>
	</c:otherwise>
</c:choose>
<hr />
<a href="${contextPath}/caseManagement">Return to Case Management</a>
<hr />

<spring:url value="/cases" var="caseActionUrl" />

<form class="form-horizontal" method="post" action="${caseActionUrl}">
	
		<div class="row">
	        <div class="large-8 large-offset-2 columns">
	            <h2>This is the table</h2>
                    <table id="myTable" class="hover large-12 columns">
                      <thead>
                        <tr>
                          <th>Charged Counts</th>
                        </tr>
                      </thead>
                      <tbody id="bannerTable">
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
</form>

</div>


<script>
	var options = {
		source : function(request, response) {
			$.ajax({
				url : "${pageContext.request.contextPath}/charges/autocomplete_req",
				dataType : "json",
				data : {
					q : request.term
				},
				success : function(data) {
					//alert(data);
					console.log(data);
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
	            $(this).after("<span id='" + this.id + "error' style='color:red'>Please enter a charge</span>");
        	}
	    },
	    select: function( event, ui ) {
		     event.preventDefault();
		     $(this).val(ui.item.label);
		     var hiddenField = this.id + "hidden";
		     console.log(hiddenField + " - " + ui.item.value);
		     $('#' + hiddenField).val(ui.item.value);
		     var errorField = this.id + "error";
		     document.getElementById(this.id + 'error').innerHTML = '';
	    }
	}
	
	$(function() {
		$(".charge-input").autocomplete(options);
	});

	function newRow() {
	    var table = document.getElementById("bannerTable");
	    var row = table.insertRow(table.getElementsByTagName("tr").length);
	    
	    var cell1 = row.insertCell(0);    
	    var label = document.createElement('LABEL');
	    		label.htmlFor = "count" + table.getElementsByTagName("tr").length;
	    		label.innerText = "Count " + table.getElementsByTagName("tr").length + ": ";
	        cell1.appendChild(label); 

	    var input = document.createElement('INPUT');
	        input.id="count" + table.getElementsByTagName("tr").length;
	        input.classList.add('charge-input');
	        $(input).autocomplete(options);
	        cell1.appendChild(input);
	        
        var chargeIdInput = document.createElement('INPUT');
        	chargeIdInput.id = "count" + table.getElementsByTagName("tr").length + "hidden";
        	chargeIdInput.type="hidden";
        	chargeIdInput.classList.add('hidden-charge-input');
        	cell1.appendChild(chargeIdInput);
        	
       	var errorField = document.createElement('DIV');
        	errorField.id = "count" + table.getElementsByTagName("tr").length + "error";
        	cell1.appendChild(errorField);
	        
	        console.log($( "#count" +table.getElementsByTagName("tr").length).autocomplete( "instance" ));
	}
	
	//CREATE NEW BANNER - DELETE ROW
	function deleteRow(){
	    var table = document.getElementById("bannerTable");
	    var rowCount = table.rows.length;
	
	    if(rowCount>1){            
	        table.deleteRow(-1);
	    }
	}
	
	function validateForm() {
		console.log("WE VALIDIN NOW BITCH");
		$("form").find('.hidden-charge-input').each(function() {
			console.log("CHECKING INPUT OF " + this.id);
			if(this.value == "") {
				alert("YOU DUN FUCKED UP A-ARON");
				return false;
			}
			
			return true;
		});
	}
	
	function createJson() {
		var data = $('body').map(function() {
			var obj = {};
			
			var charges = [];
			$(this).find('.hidden-charge-input').each(function() {
				charges.push(this.value);
			});
			
			obj.charges = charges;
			return obj;
		}).get();
		console.log(JSON.stringify(data));
	}
	
	function handleSubmit() {
		$(document).on('click', '#save', function(event) {
			event.preventDefault();
			if(validateForm()) {
				createJson();
			}
			return false;
		});
	}
	
	(function(){
		newRow();
		handleSubmit();
	})();
</script>
<%@ include file="../footer.jsp" %>
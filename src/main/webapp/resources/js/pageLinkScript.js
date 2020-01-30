(function(){
	
	var pageLinks = document.createElement("ul");
	pageLinks.classList.add('pagination');
	document.getElementById("page-links-footer").appendChild(pageLinks);
	
	for(i = 0; i < totalPages; i++) {
		var listItem = document.createElement("li");
		listItem.classList.add('page-item');
		var  link = document.createElement("a");
		var linkText = document.createTextNode(i + 1);
		link.appendChild(linkText);
		link.title = i + 1;
		link.href = "list?page=" + i;
		link.classList.add('page-link');
		listItem.appendChild(link);
		pageLinks.appendChild(listItem);
	}
})();

//var table = document.getElementById("chargeTable");
//var row = table.insertRow(table.getElementsByTagName("tr").length);
//var countNumber = table.getElementsByTagName("tr").length;
//
//// CREATE LABEL FOR NEW ROW
//var cell1 = row.insertCell(0);    
//var label = document.createElement('LABEL');
//		label.htmlFor = "count" + countNumber;
//		label.innerText = "Count " + countNumber + ": ";
//    cell1.appendChild(label); 
//
//// CREATE INPUT FOR NEW ROW
//var input = document.createElement('INPUT');
//    input.id = "count" + countNumber;
//    input.name= "chargedCountsStrings[" + countNumber + "]";
//    input.classList.add('charge-input');
//    $(input).autocomplete(chargeAutocompleteOptions);
//    cell1.appendChild(input);
//    
//// CREATE HIDDEN FIELD TO STORE SELECTION IN NEW ROW
//var chargeIdInput = document.createElement('INPUT');
//	chargeIdInput.id = "count" + countNumber + "hidden";
//	chargeIdInput.type="hidden";
//	chargeIdInput.name = "chargedCountsIds[" + countNumber + "]";
//	chargeIdInput.classList.add('hidden-charge-input');
//	cell1.appendChild(chargeIdInput);
//	
//// CREATE ERROR FIELD FOR NEW ROW
//	var errorField = document.createElement('DIV');
//	errorField.id = "count" + countNumber + ".errors";
//	errorField.classList.add('error');
//	cell1.appendChild(errorField);

//<c:if test="${cases.size() > 0}">
//<div class="panel-footer page-links">
//	<ul class="pagination">
//		<c:forEach begin="0" end="${totalPages - 1}" var="page">
//			<li class="page-item">
//				<a href="list?page=${page}" class="page-link">${page + 1}</a>
//			</li>
//		</c:forEach>
//	</ul>
//</div>
//</c:if>
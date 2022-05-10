function generateStats() {
	$.ajax({url: contextPathVar + "/cases/caseloadStats",
			dataType : "json",
			data : {
				q : document.getElementById('attorneySelect').value
			},
			success: function(result){
				document.getElementById('totalCases').innerHTML = "Current caseload: " + result.totalCases;
				document.getElementById('inCustodyCases').innerHTML = "Current in-custody caseload: " + result.inCustodyCases;
				document.getElementById('totalClients').innerHTML = "Number of clients: " + result.totalClients;
				document.getElementById('inCustodyClients').innerHTML = "Number of in-custody clients: " + result.inCustodyClients;
				
				let newTable = document.createElement('tbody');
				for(i = 0; i < result.agingReport.length; i++) {
				    let row = newTable.insertRow(i);
				    
				    // CREATE SPAN FOR NAME
				    let cell0 = row.insertCell(0);
					cell0.innerHTML = result.agingReport[i].name;
				
					// CREATE SPAN FOR INCARCERATION DATE
					let cell1 = row.insertCell(1);
					cell1.innerHTML = result.agingReport[i].incarcerationDate;
				}
				
				let oldTable = document.getElementById("agingReportTable");
				oldTable.parentNode.replaceChild(newTable, oldTable);
  }});
}
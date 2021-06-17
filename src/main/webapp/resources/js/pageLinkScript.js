(function(){
	
	const pageLinks = document.createElement("ul");
	pageLinks.classList.add('pagination');
	document.getElementById("page-links-footer").appendChild(pageLinks);
	
	for(i = 0; i < totalPages; i++) {
		let listItem = document.createElement("li");
		listItem.classList.add('page-item');
		let link = document.createElement("a");
		let linkText = document.createTextNode(i + 1);
		link.appendChild(linkText);
		link.title = i + 1;
		link.href = "list?page=" + i;
		link.classList.add('page-link');
		listItem.appendChild(link);
		pageLinks.appendChild(listItem);
	}
})();
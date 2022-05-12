<%@ include file="../header.jsp" %>

<fmt:setBundle basename="/pdclientmanager/src/main/resources/messages.properties" var="lang"/>

<h1>Upload File for ${caseNumber}</h1>
<hr />
<a href="${contextPath}/cases/byCaseNumber/${caseNumber}">Return to ${caseNumber}</a>
<hr />

<spring:url value="/documents/${caseId}/${caseNumber}/upload" var="uploadActionUrl" />

<form:form class="form-horizontal" modelAttribute="document" enctype="multipart/form-data" method="post" action="${uploadActionUrl}">
    
    <fmt:bundle basename="messages">
    
    <div class="row">
    	<form:errors cssClass="error"/>
    </div>
    
    <div class="form-group">
    	<label for="fileName">File name: </label>
    	<input type="text" name="fileName" path="fileName" id="fileName" size="30" required />
    </div>
    <div class="form-group">
    	<p>Select PDF file to upload (16MB max):</p>
    </div>
    <div class="form-group">
    	<input type="file" path="file" id="file" name="file" accept=".pdf" required />
    </div>
    
<br>
	
	<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
			<button type="submit" class="btn-lg btn-primary pull-right">Submit</button>
		</div>
	</div>
	
	</fmt:bundle>
</form:form>
<%@ include file="../footer.jsp" %>
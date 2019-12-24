<%@ include file = "header.jsp" %>

<h1>
An error was experienced! Sorry the program sucks. Or maybe you just broke it. I dunno. Tough to say.
</h1>
<br>
HTTP request - ${url}
<br>
${error.message}
<br>
${error.debugMessage}
<br>
${error.stackTrace}


<%@ include file = "footer.jsp" %>
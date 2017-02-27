<html>
<head>
</head>
<body>
	<%
		java.util.List errorMsgs = (java.util.List) request.getAttribute("errorMsgs");
		out.println(errorMsgs);
	%>
</body>
</html>
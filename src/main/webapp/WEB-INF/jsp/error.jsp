<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
</head>
<body>

	${errorMessage}
 	<br/>
	<br/>
	<a href="login.jsp">Back to sign in page</a>
	
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	
</body>
</html>
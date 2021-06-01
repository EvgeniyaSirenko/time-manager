<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main page</title>
</head>
<body>
		You are logged as ${participant.firstName}

	<form action="controller" method="get">			
		<input type="hidden" name="command" value="logout" />
		<input type="submit" value="Sign out"/>
	</form>
	
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login page</title>
</head>
<body>
	<form action="controller" method="get">
		<input type="hidden" name="command" value="login" />
		<input name="login" value="login"/><br/>
		<input type="password" name="password"/><br/>
		<input type="submit" value="Login"/>
	</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login page</title>
</head>
<body>

	<h1>Authorization</h1>
	<p>Fill in this form to sign in</p>
	<hr/>
		
	<form action="controller" method="get">
	
	
		<input type="hidden" name="command" value="login" />
		<label for="login">Login</label>
		<input name="login" /><br/>
		<label for="password">Password</label>
		<input type="password" name="password"/><br/>
		<input type="submit" value="Sign in"/>
	</form>
	<hr/>
	
	
	<br/>
	Not registered yet? <a href="registration.jsp"> Register</a>
	
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration page</title>
</head>
<body>
	<h1>Registration</h1>
	<p>Fill in this form to create an account</p>
	<hr/>
	<form action="controller" method="get">

		<input type="hidden" name="command" value="registration" />
		
<%-- is it correct? --%>		
		<input type="hidden" name="roleId" value="1" />
		
		<label for="firstName">First Name</label>
		<input type="text" name="firstName" required/><br/>
		<label for="lastName">Last Name</label>
		<input type="text" name="lastName" required/><br/>
		<label for="login">Login</label>
		<input name="login" required/><br/>
		<label for="password">Password</label>
		<input type="password" name="password" required/><br/>
<%--		
		<label for="localeName"><b>Language</b></label>
		<input type="text" name="localeName"/><br/>
--%> 		
		<input type="submit" value="Register"/>
	</form>
	<hr/>
	
	<br/>
	Already registered? <a href="login.jsp"> Sign in</a>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="footer" uri="/WEB-INF/tld/footerTag.tld" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags/" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<h:head></h:head>
</head>
<body>
	<div id="fixedHeader">
		<form action="controller" method="post">
			<input type="hidden" name="command" value="login" /> 
			<label for="login"><fmt:message key="login_jsp.label.login" /></label>
			<input	name="login" /><br /> 
			<label for="password"><fmt:message	key="login_jsp.label.password" /></label> 
			<input type="password"	name="password" /><br /> 
			<input type="submit" value="<fmt:message key="login_jsp.button.login"/>">
		</form><br>
		<form action="registration.jsp" method="post">
			<p>
				<input type="submit" value="<fmt:message key="login_jsp.label.register"/>" />
			</p>
		</form>
	<footer:footerTag></footer:footerTag>
	</div>
</body>
</html>
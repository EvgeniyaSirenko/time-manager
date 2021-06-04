<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration page</title>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<div id="fixedHeader">
	<form action="controller" method="get">

		<input type="hidden" name="command" value="registration" />
		<input type="hidden" name="roleId" value="1" />
		<label for="firstName"><fmt:message key="participant_account_jsp.label.first_name" /></label>
		<input type="text" name="firstName" required/><br>
		<label for="lastName"><fmt:message key="participant_account_jsp.label.last_name" /></label>
		<input type="text" name="lastName" required/><br>
		<label for="login"><fmt:message key="login_jsp.label.login"/></label>
		<input name="login" required/><br>
		<label for="password"><fmt:message key="login_jsp.label.password" /></label>
		<input type="password" name="password" required/><br>		
		<input type="submit" value="Register"/>
	</form>
	
	<br>
	<a href="login.jsp"><fmt:message key="login_jsp.button.login"/></a>

<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</div>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<div id="fixedHeader">		
	<form id="login_form" action="controller" method="get">
	
	
		<input type="hidden" name="command" value="login" />
		<label for="login"><fmt:message key="login_jsp.label.login"/></label>
		<input name="login" /><br/>
		<label for="password"><fmt:message key="login_jsp.label.password"/></label>
		<input type="password" name="password"/><br/>
		<input type="submit" value="<fmt:message key="login_jsp.button.login"/>">
	</form>
	
	
	<br>
	<a href="registration.jsp"><fmt:message key="login_jsp.label.register" /></a>
	

	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	
</div>	
	
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/jspf/directive/taglib.jspf" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<%@ include file="/jspf/head.jspf" %>
</head>
<body>
<div id="fixedHeader">		
	<form action="controller" method="post">
		<input type="hidden" name="command" value="login" />
		<label for="login"><fmt:message key="login_jsp.label.login"/></label>
		<input name="login" /><br/>
		<label for="password"><fmt:message key="login_jsp.label.password"/></label>
		<input type="password" name="password"/><br/>
		<input type="submit" value="<fmt:message key="login_jsp.button.login"/>">
	</form>	
	<br>
	<form action="controller" method="get">
		<input type="hidden" name="command" value="registration" />		
		<input type="submit" value="<fmt:message key="login_jsp.label.register"/>"/>
	</form>		
<%@ include file="/jspf/footer.jspf"%>	
</div>	
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration page</title>
<%@ include file="/jspf/head.jspf"%>
</head>
<body>
	<div id="fixedHeader">
		<form action="controller" method="post">
			<input type="hidden" name="command" value="registration" /> 
			<input	type="hidden" name="roleId" value="1" /> 
			<input	type="hidden" name="localeName" value="en" /> 
				<label for="firstName"><fmt:message	key="participant_account_jsp.label.first_name" /></label> 
					<input	type="text" name="firstName" required /><br>
				 <label	for="lastName"><fmt:message	key="participant_account_jsp.label.last_name" /></label> 
					<input	type="text" name="lastName" required /><br>
				<label for="login"><fmt:message	key="login_jsp.label.login" /></label> 
					<input name="login" required /><br>
				<label for="password"><fmt:message key="login_jsp.label.password" /></label> 
					<input type="password" name="password" required /><br> 
					<input type="submit" value="Register" />
		</form>
		<br>
		<form action="login.jsp" method="post">
				<input type="submit"
					value="<fmt:message key="header_jspf.anchor.login"/>" />
		</form>
		<%@ include file="/jspf/footer.jspf"%>
	</div>
</body>
</html>
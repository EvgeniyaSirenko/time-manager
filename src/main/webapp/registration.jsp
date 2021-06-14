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
					<input	type="text" pattern="[A-Za-z]{3}" oninvalid="setCustomValidity('Please use only Latin letters, not less than 3')" name="firstName" required /><br>
				 <label	for="lastName"><fmt:message	key="participant_account_jsp.label.last_name" /></label> 
					<input	type="text" pattern="[A-Za-z]{3}" oninvalid="setCustomValidity('Please use only Latin letters, not less than 3')" name="lastName" required /><br>
				<label for="login"><fmt:message	key="login_jsp.label.login" /></label> 
					<input name="login" pattern="[A-Za-z]{5}" oninvalid="setCustomValidity('Please use only Latin letters, not less than 5')" required /><br>
				<label for="password"><fmt:message key="login_jsp.label.password" /></label> 
					<input type="password" pattern="[A-Za-z0-9]{5}" oninvalid="setCustomValidity('Please use numbers and Latin letters, not less than 5')" name="password" required /><br> 
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
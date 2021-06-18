<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="footer" uri="/WEB-INF/tld/footerTag.tld" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags/" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration page</title>
<h:head></h:head>
</head>
<body>
	<div id="fixedHeader">
		<form action="controller" method="post">
			<input type="hidden" name="command" value="registration" /> 
			<input	type="hidden" name="roleId" value="1" /> 			
			<label for="firstName"><fmt:message	key="participant_account_jsp.label.first_name" /></label> 
				<input	type="text" pattern="[A-Za-zа-яА-Я]{3,15}" 
				oninvalid="setCustomValidity('Please use only Latin letters, 3-15 symbols')" name="firstName" required /><br>
			 <label	for="lastName"><fmt:message	key="participant_account_jsp.label.last_name" /></label> 
				<input	type="text" pattern="[A-Za-zа-яА-Я]{3,15}" 
				oninvalid="setCustomValidity('Please use only Latin letters, 3-15 symbols')" name="lastName" required /><br>
			<label for="login"><fmt:message	key="login_jsp.label.login" /></label> 
				<input type="text" pattern="[A-Za-zа-яА-Я]{3,15}" 
				oninvalid="setCustomValidity('Please use only Latin letters, 3-15 symbols')" name="login" required /><br>
			<label for="password"><fmt:message key="login_jsp.label.password" /></label> 
				<input type="password" pattern="[A-Za-z0-9]{3,15}" 
				oninvalid="setCustomValidity('Please use numbers and Latin letters, 5-15 symbols')" name="password" required /><br> 
				<fmt:message key="participant_account_jsp.label.localization" />
				<select name="localeToSet">
					<c:forEach var="localeToSet" items="${locales}">
						<c:set var="selected" value="${localeToSet.key == currentLocale ? 'selected' : '' }"/>
							<option value="${localeToSet.key}" ${selected}>${localeToSet.key}</option>
					</c:forEach>
				</select> 
				<br> <br>	
				<input type="submit" value="Register" />
		</form>
		<br>
		<form action="login.jsp" method="post">
			<input type="submit" value="<fmt:message key="header_jspf.anchor.login"/>" />
		</form>
		<footer:footerTag></footer:footerTag>
	</div>
</body>
</html>
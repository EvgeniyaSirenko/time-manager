<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<fmt:setLocale value="${sessionScope.defaultLocale}"/>
<fmt:setBundle basename="resources"/>
				
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Account</title>
<%@ include file="/jspf/head.jspf"%>
</head>
<c:if test="${not empty participant}">
	<div id="fixedHeader">
		<form action="controller" method="get">
			<input type="hidden" name="command" value="participantMainPage" />
			<input type="submit" value="<fmt:message key="header_jspf.anchor.main_page"/>" />
		</form>
	</div>
<body>
	<div id="fixedHeader">
		<form id="form" action="controller" method="post">
			<input type="hidden" name="command" value="participantUpdateAccount" />
			<fmt:message key="participant_account_jsp.label.localization" />
			<select name="localeToSet">
				<c:choose>
					<c:when test="${not empty defaultLocale}">
						<option value="">${defaultLocale}</option>
					</c:when>
					<c:otherwise>
						<option value="" />
					</c:otherwise>
				</c:choose>
				<c:forEach var="localeToSet" items="${locales}">
						<c:set var="selected" value="${localeToSet.key == currentLocale ? 'selected' : '' }"/>
							<option value="${localeToSet.key}" ${selected}>${localeToSet.key}</option>
				</c:forEach>
			</select> <br> <br>
	<div>
		<fmt:message key="participant_account_jsp.label.first_name" /><br> 
		<input pattern="[A-Za-zа-яА-Я]{3,15}" oninvalid="setCustomValidity('Please use only Latin letters, 3-15 symbols')" 
			name="firstName" value="${participant.firstName}" required />
	</div>
	<div>
		<fmt:message key="participant_account_jsp.label.last_name" /><br> 
		<input pattern="[A-Za-zа-яА-Я]{3,15}" oninvalid="setCustomValidity('Please use only Latin letters, 3-15 symbols')" 
			name="lastName" value="${participant.lastName}" required />
	</div>
	<div>
		<fmt:message key="participant_account_jsp.label.login" /><br> 
		<input pattern="[A-Za-zа-яА-Я]{3,15}" oninvalid="setCustomValidity('Please use only Latin letters, 3-15 symbols')" 
			name="login" value="${participant.login}" required />
	</div>
	<div>
		<fmt:message key="participant_account_jsp.label.password" /><br> 
		<input pattern="[A-Za-z0-9]{3,15}" oninvalid="setCustomValidity('Please use numbers and Latin letters, 5-15 symbols')" 
			name="password" type="password" value="${participant.password}" required />
	</div>
		<input type="submit" value='<fmt:message key="participant_account_jsp.button.update"/>'>
		</form>
	</div>
</c:if>
<c:if test="${empty participant and title ne 'Login'}">
	<div id="fixedHeader">
		<form action="login.jsp" method="post">
			<input type="submit" value="<fmt:message key="header_jspf.anchor.login"/>" />
		</form>
	</div>
</c:if>
<div id="fixedHeader">
	<%@ include file="/jspf/footer.jspf"%>

</div>
</body>
</html>
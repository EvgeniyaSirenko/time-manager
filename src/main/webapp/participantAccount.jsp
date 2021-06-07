<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Account</title>
<%@ include file="/jspf/head.jspf"%>
</head>

<c:if test="${not empty participant}">
	<div id="fixedHeader">
		<a href="controller?command=participantMainPage"> <fmt:message
				key="header_jspf.anchor.main_page" />
		</a>
	</div>

	<body>

		<div id="fixedHeader">
			<form id="form" action="controller" method="post">
				<input type="hidden" name="command" value="participantUpdateAccount" />
				<fmt:message key="participant_account_jsp.label.localization" />
				<select name="localeToSet">
				
<!-- 				
					<c:choose>
						<c:when test="${not empty defaultLocale}">
							<option value="">${defaultLocale}[Default]</option>
						</c:when>
						<c:otherwise>
							<option value="" />
						</c:otherwise>
					</c:choose>
-->
					<c:forEach var="localeName" items="${locales}">
						<option value="${localeName}">${localeName}</option>
					</c:forEach>
				</select> 
				
				<br> <br>

				<div>
					<fmt:message key="participant_account_jsp.label.first_name" />
					<br> <input name="firstName" value="${participant.firstName}">
				</div>
				<div>
					<fmt:message key="participant_account_jsp.label.last_name" />
					<br> <input name="lastName" value="${participant.lastName}">
				</div>
				<div>
					<fmt:message key="login_jsp.label.login" />
					<br> <input name="login" value="${participant.login}">
				</div>
				<div>
					<fmt:message key="login_jsp.label.password" />
					<br> <input name="password" type="password"
						value="${participant.password}">
				</div>
				<p>
					<input type="submit"
						value='<fmt:message key="participant_account_jsp.button.update"/>'>
				</p>
			</form>
</c:if>

<c:if test="${empty participant and title ne 'Login'}">
	<div id="fixedHeader">
		<a href="login.jsp"> <fmt:message key="header_jspf.anchor.login" />
		</a>
	</div>
</c:if>

<%@ include file="/jspf/footer.jspf"%>

</div>
</body>
</html>
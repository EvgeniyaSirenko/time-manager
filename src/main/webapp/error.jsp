<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="footer" uri="/WEB-INF/tld/footerTag.tld" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags/" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
<h:head></h:head>
</head>
<body>
	<div id="fixedHeader">
		<c:set var="message" value="${requestScope['javax.servlet.error.message']}"/>		
		<c:if test="${not empty message}">
			<p>${message}</p>
		</c:if>
		<p>${errorMessage}</p>
		<form action="login.jsp" method="post">
			<input type="submit" value="<fmt:message key="error_jsp.label.back_to_sign_in"/>" />
		</form>
		<footer:footerTag></footer:footerTag>
	</div>
</body>
</html>
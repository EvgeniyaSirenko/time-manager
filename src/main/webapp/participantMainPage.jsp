<%@ page import="com.epam.db.Role"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="footer" uri="/WEB-INF/tld/footerTag.tld" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags/" %>

<fmt:setLocale value="${sessionScope.defaultLocale}"/>
<fmt:setBundle basename="resources"/>
				
<!DOCTYPE html>
<html>
<head>
<title>Main page</title>
<h:head></h:head>
</head>
<c:if test="${not empty participant}">
<body>
<div id="fixedHeader">
<c:if test="${empty participant and title ne 'Login'}">
	<div id="fixedHeader">
		<form action="login.jsp" method="post">
			<input type="submit" value="<fmt:message key="header_jspf.anchor.login"/>" />
		</form>
	</div>
</c:if> 
${participant.firstName}&nbsp;&nbsp;
	<a href="controller?command=logout">
		<fmt:message key="header_jspf.anchor.logout" />
	</a><br><br>
<c:choose>
	<c:when test="${participantRole.name == 'client'}">
		<p>
			<a href="controller?command=participantAccount"> 
				<fmt:message key="header_jspf.anchor.my_account" />
			</a>
		</p>
		<p>
			<a href="controller?command=participantActivities"> 
				<fmt:message key="header_jspf.anchor.my_activities" />
			</a>
		</p>
			<a href="controller?command=participantChooseNewActivity"> 
				<fmt:message key="header_jspf.anchor.add_activity" />
			</a>
	</c:when>
</c:choose>
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
<footer:footerTag></footer:footerTag>
</div>
</body>
</html>
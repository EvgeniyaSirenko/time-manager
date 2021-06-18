<%@ page import="com.epam.db.Role"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="footer" uri="/WEB-INF/tld/footerTag.tld" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags/" %>

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
	<c:when test="${participantRole.name == 'admin' }">
		<p>
			<a href="controller?command=activitiesToApprove">
				<fmt:message key="header_jspf.anchor.activities_to_approve" />
			</a>
		</p>
		<p>
			<a href="controller?command=activitiesToDelete"> 
				<fmt:message key="header_jspf.anchor.activities_to_delete" />
			</a>
		</p>
		<p>
			<a href="controller?command=categories"> 
				<fmt:message key="header_jspf.anchor.categories" />
			</a>
		</p>
		<p>
			<a href="controller?command=activities"> 
				<fmt:message key="header_jspf.anchor.activities" />
			</a>
		</p>
		<p>
			<a href="controller?command=participants"> 
				<fmt:message key="header_jspf.anchor.participants" />
			</a>
		</p>
			<a href="controller?command=reports"> 
				<fmt:message key="header_jspf.anchor.reports" />
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
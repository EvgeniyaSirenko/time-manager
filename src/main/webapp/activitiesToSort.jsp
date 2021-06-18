<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="footer" uri="/WEB-INF/tld/footerTag.tld" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags/" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Activities to sort</title>
<h:head></h:head>
</head>
<c:if test="${not empty participant}">
	<div id="fixedHeader">
		<form action="controller" method="get">
			<input type="hidden" name="command" value="adminMainPage" /> 
			<input type="submit" value="<fmt:message key="header_jspf.anchor.main_page"/>" />
		</form>
	</div>
<body>
	<div id="fixedHeader">
		<table id="activities_table">
			<thead>
				<tr>
					<td>№</td>
					<td><a href="controller?command=reportsSortActivities">
						<fmt:message key="reports.label.activity"/></a></td>
					<td><a href="controller?command=reportsSortActivitiesByCategory"> 
						<fmt:message key="reports.label.category"/></a></td>
					<td><a href="controller?command=reportsSortActivitiesByParticipantsQuantity">
						<fmt:message key="reports.label.participants_quantity"/></a></td>
				</tr>
			</thead>
			<c:set var="k" value="0" />
			<c:forEach var="bean" items="${categoryActivityParticipantBeansList}">
				<c:set var="k" value="${k+1}" />
				<tr>
					<td><c:out value="${k}" /></td>
					<td><c:out value="${bean.activityName}" /></td>
					<td><c:out value="${bean.categoryName}" /></td>
					<td><c:out value="${bean.participantId}" /></td>
				</tr>
			</c:forEach>
		</table>
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
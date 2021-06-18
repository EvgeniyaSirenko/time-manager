<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="footer" uri="/WEB-INF/tld/footerTag.tld" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags/" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Categories</title>
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
		<p>
			<a href="controller?command=adminCreateNewActivity"> 
				<fmt:message key="header_jspf.anchor.create_activity" />
			</a>
		</p>
		<table id="activities_table">
			<thead>
				<tr>
					<td>№</td>
					<td><fmt:message key="activities.label.activity_name" /></td>
					<td><fmt:message key="activities_to_delete_jsp.label.reaction" /></td>
					<td><fmt:message key="activities_to_delete_jsp.label.reaction" /></td>
				</tr>
			</thead>
			<c:set var="k" value="0" />
			<c:forEach var="bean" items="${activitiesList}">
			<c:set var="k" value="${k+1}" />
				<tr>
					<td><c:out value="${k}" /></td>
					<td><c:out value="${bean.name}" /></td>
					<td>
						<form action="controller" method="post">
							<input type="hidden" name="command" value="updateActivity" />
							<input type=hidden name="activityName" value="${bean.name}" />
							<input type="submit" value="<fmt:message key="categories.button.update"/>" />
						</form>
					</td>
					<td>
						<form action="controller" method="post">
							<input type="hidden" name="command" value="deleteActivity" />
							<input type=hidden name="activityName" value="${bean.name}" />
							<input type="submit" value="<fmt:message key="categories.button.delete"/>" />
						</form>
					</td>
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
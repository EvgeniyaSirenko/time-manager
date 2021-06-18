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
<title>My activities</title>
<h:head></h:head>
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
		<table id="activities_table">
			<thead>
				<tr>
					<td>№</td>
					<td><fmt:message key="participant_activities_jsp.label.activity" /></td>
					<td><fmt:message key="participant_activities_jsp.label.duration" /></td>
					<td><fmt:message key="participant_activities_jsp.label.add_duration" /></td>
					<td><fmt:message key="participant_activities_jsp.label.request_to" /></td>
				</tr>
			</thead>
			<c:set var="k" value="0" />
				<c:forEach var="bean" items="${participantActivityBeansList}">
				<c:set var="k" value="${k+1}" />
					<tr>
						<td><c:out value="${k}" /></td>
						<td>${bean.activityName}</td>
						<td>${bean.activityDuration}</td>
						<td>
							<form action="controller" method="post">
								<input type="hidden" name="command" value="addActivityDuration" />
								<input type="hidden" name="activityId" value="${bean.activityId}" />									<input type="number" min="1" step=1 name="duration" id="duration" required /> 
								<input	type="submit" value="<fmt:message key="participant_activities_jsp.button.add" />" />
							</form>
						</td>
						<td>
							<form action="controller?command=participantDeleteActivity" method="post">
								<input type="hidden" name="command"	value="participantDeleteActivity" />
								<input type=hidden	name="activityName" value="${bean.activityName}" /> 
								<input	type=hidden name="participantLogin" value="${bean.participantLogin}" /> 
								<input type="submit" value="<fmt:message key="participant_activities_jsp.button.delete"/>" />
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
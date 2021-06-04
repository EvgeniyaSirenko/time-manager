<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/jspf/directive/taglib.jspf"%>


<!DOCTYPE html>
<html>
<head>
<title>My activities</title>
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
		
		
	<form action="controller" method="post">
		<fmt:message key="settings_jsp.label.set_locale"/>:
		<select name="locale">
			<c:forEach items="${applicationScope.locales}" var="locale">
				<c:set var="selected" value="${locale.key == currentLocale ? 'selected' : '' }"/>
				<option value="${locale.key}" ${selected}>${locale.value}</option>
			</c:forEach>
		</select>
		<input type="submit" value="<fmt:message key='settings_jsp.form.submit_save_locale'/>">
		
	</form>



			<td class="content">

				<table id="activities_table">
					<thead>
						<tr>
							<td>№</td>
							<td>Activity</td>
							<td>Duration (min)</td>
							<td>Add duration</td>
							<td>Request to</td>
						</tr>
					</thead>
					<c:set var="k" value="0" />
					<c:forEach var="bean" items="${activitiesList}">
						<c:set var="k" value="${k+1}" />
						<tr>
							<td><c:out value="${k}" /></td>
							<td>${bean.name}</td>
							<td>${bean.duration}</td>
							<td>Minutes to add</td>
							<td>Delete activity</td>
						</tr>
					</c:forEach>
				</table>
			</td>
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
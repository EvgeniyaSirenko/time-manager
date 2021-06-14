<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reports</title>
<%@ include file="/jspf/head.jspf"%>
</head>
<c:if test="${not empty participant}">
	<div id="fixedHeader">
		<form action="controller" method="get">
			<input type="hidden" name="command" value="adminMainPage" /> <input
				type="submit"
				value="<fmt:message key="header_jspf.anchor.main_page"/>" />
		</form>
	</div>
	<body>
		<div id="fixedHeader">
			<td class="content">
				<table id="activities_table">
					<p>
						<fmt:message key="reports.label.table_head" />
					</p>

					
						<tr>
							<td>№</td>
							<td><fmt:message key="reports.label.participant_login" /></td>
							<td><fmt:message key="reports.label.activities_quantity" /></td>
							<td><fmt:message key="reports.label.total_duration" /></td>
						</tr>
					</thead>
					<c:set var="k" value="0" />
					<c:forEach var="bean" items="${participantActivityBeansList}">
						<c:set var="k" value="${k+1}" />
						<tr>
							<td><c:out value="${k}" /></td>
							<td><c:out value="${bean.participantLogin}" /></td>
							<td><c:out value="${bean.activityId}" /></td>
							<td><c:out value="${bean.activityDuration}" /></td>
						</tr>
					</c:forEach>
				</table> <br>
				<p>
					<a href="controller?command=reportsSortActivities"> 
						<fmt:message key="reports.label.sort_activities" />
					</a>
				</p>
					<a href="controller?command=reportsFilterActivities"> 
						<fmt:message key="reports.label.filter_activities" />
					</a>
			</td>
		</div>

</c:if>

<c:if test="${empty participant and title ne 'Login'}">
	<div id="fixedHeader">
		<form action="login.jsp" method="post">
				<input type="submit"
					value="<fmt:message key="header_jspf.anchor.login"/>" />
		</form>
	</div>
</c:if>
<div id="fixedHeader">
	<%@ include file="/jspf/footer.jspf"%>
</div>
</body>
</html>
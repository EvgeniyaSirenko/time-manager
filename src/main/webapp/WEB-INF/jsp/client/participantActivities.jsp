<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>


<!DOCTYPE html>
<html>
<head>
<title>Main page</title>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<c:if test="${not empty participant}">


<div id="fixedHeader" >
	<a href="controller?command=participantMainPage">
		<fmt:message key="header_jspf.anchor.participantMainPage"/>
	</a> 
</div>
<body>

<div id="main-container">

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
						<c:set var="k" value="0"/>
						<c:forEach var="bean" items="${activitiesList}">
						<c:set var="k" value="${k+1}"/> 
						<tr>
							<td><c:out value="${k}"/></td>
							<td>${bean.name}</td>
							<td>${bean.duration}</td>
							<td>Minutes to add</td>
							<td>Delete activity</td>
						</tr>
					</c:forEach>
				</table>
			</td>
			
				// TODO ---------->   add button "deleting request"	(with !!! admin confirmation)	<br/>	
				// TODO ---------->   add button "add duration" (no confirmation needed)
</c:if>
				
<c:if test="${empty participant and title ne 'Login'}">
	<div id="rightHeader">
		<a href="login.jsp">
			<fmt:message key="header_jspf.anchor.login"/>
		</a>
	</div>
</c:if>				
			
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</div>
</body>
</html>
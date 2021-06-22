<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="footer" uri="/WEB-INF/tld/footerTag.tld" %>
<%@ taglib prefix="h" tagdir="/WEB-INF/tags/" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Participants</title>
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
		
	Page ${page} of ${pageCount} 
	
	|

	<c:choose>
		<c:when test="${page - 1 > 0}">
			<a href="controller?command=participants&page=${page-1}&recordsPerPage=${recordsPerPage}">Previous</a>
		</c:when>
		<c:otherwise>
			Previous
		</c:otherwise>
	</c:choose>

	<c:forEach var="p" begin="${1}" end="${nOfPages}">
		<c:choose>
			<c:when test="${page == p}">${p}</c:when>
			<c:otherwise>
				<a href="controller?command=participants&page=${p}&recordsPerPage=${recordsPerPage}">${p}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>

	<c:choose>
		<c:when test="${page + 1 <= nOfPages}">
			<a href="controller?command=participants&page=${page+1}&recordsPerPage=${recordsPerPage}">Next</a>
		</c:when>
		<c:otherwise>
			Next
		</c:otherwise>
	</c:choose>

	|
	
	<br><br>	
	<table id="activities_table">
		<thead>
			<tr>
				<td><fmt:message key="participants.label.participant_name" /></td>
				<td><fmt:message key="activities_to_delete_jsp.label.reaction" /></td>
			</tr>
		</thead>
		<c:forEach var="bean" items="${participantsList}">
			<tr>
				<td><c:out value="${bean.login}" /></td>
				<td>
					<form action="controller?command=deleteParticipant" method="post">
						<input type="hidden" name="command" value="deleteParticipant" />
						<input type=hidden name="participantLogin" value="${bean.login}" /> 
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
<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main page</title>
</head>
<body>

<%-- Comment
	<c:if test="${loggedParticipant.login == 'admin'}">
	</c:if>
--%>
	 
	 You are logged as ${participant.firstName}

		<form action="controller" method="get">						
			<input type="hidden" name="command" value="logout" />
			<input type="submit" value="Sign out"/>
		</form>	
	
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
	
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/jspf/directive/taglib.jspf" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
<%@ include file="/jspf/head.jspf" %>
</head>
<body>
<div id="fixedHeader">

	${errorMessage}
 	<br/>
	<br/>
		<form action="login.jsp" method="post">
			<p>
				<input type="submit" value="<fmt:message key="error_jsp.label.back_to_sign_in"/>"/>
			</p>
		</form>		
	<%@ include file="/jspf/footer.jspf"%>
</div>	
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>
<div id="fixedHeader">

	${errorMessage}
 	<br/>
	<br/>
	<a href="login.jsp">Back to sign in page</a>
	
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</div>	
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<head>
<title>Main page</title>
<%@ include file="/WEB-INF/jspf/head.jspf"%>
</head>

<c:if test="${not empty participant}">
	<body>
		<div id="main-container">

			<%@ include file="/WEB-INF/jspf/header.jspf"%>
</c:if>
<c:if test="${empty participant and title ne 'Login'}">
	<div id="fixedHeader">
		<a href="login.jsp"> <fmt:message key="header_jspf.anchor.login" />
		</a>
	</div>
</c:if>
<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</div>
</body>
</html>
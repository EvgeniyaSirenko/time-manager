<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<fmt:setLocale value="${sessionScope.defaultLocale}"/>
<fmt:setBundle basename="resources"/>
				
<!DOCTYPE html>
<html>
<head>
<title>Main page</title>
<%@ include file="/jspf/head.jspf"%>
</head>
<body>
	<c:if test="${not empty participant}">
		<%@ include file="/jspf/header.jspf"%>
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
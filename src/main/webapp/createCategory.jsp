<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/jspf/directive/taglib.jspf" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create category</title>
<%@ include file="/jspf/head.jspf" %>
</head>
<c:if test="${not empty participant}">


	<div id="fixedHeader">
		<a href="controller?command=adminMainPage"> <fmt:message
				key="header_jspf.anchor.main_page" />
		</a>
	</div>
<body>
<div id="fixedHeader">
	<form action="controller" method="post">
		<input type="hidden" name="command" value="createCategory" />
		<label for="name"><fmt:message key="create_category_jsp.label.category_name" /></label>
		<input type="text" name="name" required/><br>

		<input type="submit" value="Create"/>
	</form>

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
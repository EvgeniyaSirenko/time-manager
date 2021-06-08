<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/jspf/directive/taglib.jspf"%>


<!DOCTYPE html>
<html>
<head>
<title>Create new activity</title>
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
		<input type="hidden" name="command" value="adminSaveCreatedActivity" />
		
		<div>
		<fmt:message key="create_activity_jsp.label.activity_name"/>:&nbsp;
		<input type="text" name="name" required/>
		</div>
		<div>
		<label for="name"><fmt:message key="create_category_jsp.label.category_name" /></label>
		<select name="category">
			<c:forEach items="${categoriesList}" var="category">
				<option value="${category.id}">${category.name}</option>
			</c:forEach>
		</select>
		</div>
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
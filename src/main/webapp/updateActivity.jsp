<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Update activity</title>
<%@ include file="/jspf/head.jspf"%>
</head>

<c:if test="${not empty participant}">
	<div id="fixedHeader">
		<a href="controller?command=adminMainPage"> <fmt:message
				key="header_jspf.anchor.main_page" />
		</a>
	</div>
	<body>
		<div id="fixedHeader">
			<form id="form" action="controller" method="post">
				<input type="hidden" name="command" value="saveUpdatedActivity" />

				<div>
					<fmt:message key="activities.label.activity_name" />
					<input name="name" value="${activity.name}" required />
				</div>
<!-- 
				<fmt:message key="activities.label.choose_category" />:&nbsp; 
				<select name="category">
					<c:forEach items="${categoriesList}" var="category">
						<option value="${category.id}">${category.name}</option>
					</c:forEach>
				</select>
 -->				
				<p>
					<input type="submit"
						value='<fmt:message key="activities.button.update"/>'>
				</p>
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
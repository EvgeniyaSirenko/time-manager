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
		<form action="controller" method="get">
			<input type="hidden" name="command" value="adminMainPage" />
			<input type="submit" value="<fmt:message key="header_jspf.anchor.main_page"/>"/>
		</form>	
	</div>
	<body>
		<div id="fixedHeader">
			<form id="form" action="controller" method="post">
				<input type="hidden" name="command" value="saveUpdatedActivity" />

				<div>
					<fmt:message key="activities.label.activity_name" />
					<input type=hidden name="oldActivityName" value="${bean.activityName}"/>								
					<input name="activity" value="${categoryActivityBean.activityName}" required />
				</div>
				<div>
				<fmt:message key="activities.label.choose_category" />:&nbsp; 
				<select name="category">
					<c:choose>
							<option value="">${defaultCategory}</option>
					</c:choose>
									
					<c:forEach items="${categoryActivityBeansList}" var="bean">
						<option value="${bean.categoryName}">${bean.categoryName}</option>
					</c:forEach>
				</select>
				</div>
				<p>
					<input type="submit"
						value='<fmt:message key="activities.button.update"/>'>
				</p>
			</form>
		</div>
</c:if>
<c:if test="${empty participant and title ne 'Login'}">
	<div id="fixedHeader">
		<form action="controller" method="get">
			<input type="hidden" name="command" value="login" />		
			<input type="submit" value="<fmt:message key="header_jspf.anchor.login"/>"/>
		</form>	
	</div>
</c:if>
<div id="fixedHeader">
<%@ include file="/jspf/footer.jspf"%>
</div>
</body>
</html>
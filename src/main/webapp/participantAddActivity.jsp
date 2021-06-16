<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<fmt:setLocale value="${sessionScope.defaultLocale}"/>
<fmt:setBundle basename="resources"/>

<!DOCTYPE html>
<html>
<head>
<title>My activities</title>
<%@ include file="/jspf/head.jspf"%>
</head>
<c:if test="${not empty participant}">

	<div id="fixedHeader">
		<form action="controller" method="get">
			<input type="hidden" name="command" value="participantMainPage" /> <input
				type="submit"
				value="<fmt:message key="header_jspf.anchor.main_page"/>" />
		</form>
	</div>
	<body>


		<div id="fixedHeader">

			<form action="controller" method="post">
				<input type="hidden" name="command" value="participantAddActivity" />
				<fmt:message key="participant_account_jsp.label.choose_activity" />
				:&nbsp; <select name="activity">
					<c:forEach items="${activitiesList}" var="activity">
						<option value="${activity.id}">${activity.name}</option>
					</c:forEach>
				</select> <input type="submit"
					value="<fmt:message key='participant_account_jsp.button.send_request'/>">

			</form>
		</div>
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
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Account</title>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
</head>
<c:if test="${not empty participant}">
<div id="fixedHeader" >
	<a href="controller?command=participantMainPage">
		<fmt:message key="header_jspf.anchor.participantMainPage"/>
	</a> 
</div>
<body>
<div id="main-container">

			<td class="content">	
			
				<%-- CONTENT --%>

				<form id="settings_form" action="controller" method="get">
					<input type="hidden" name="command" value="participantUpdate" />
<%-- 
					<div>
						<p>
							<fmt:message key="settings_jsp.label.localization"/>
						</p>
						<select name="localeToSet">
							<c:choose>
								<c:when test="${not empty defaultLocale}">
									<option value="">${defaultLocale}[Default]</option>
								</c:when>
								<c:otherwise>
									<option value=""/>
								</c:otherwise>
							</c:choose>
														
							<c:forEach var="localeName" items="${locales}">
								<option value="${localeName}">${localeName}</option>							
							</c:forEach>
						</select>
						
 --%>						
	<div>
		<p>
			<fmt:message key="settings_jsp.label.first_name"/>
		</p>
			<input name="firstName" value="${participant.firstName}">
	</div>
						
	<div>
		<p>
			<fmt:message key="settings_jsp.label.last_name"/>
		</p>
			<input name="lastName" value="${participant.lastName}">
	</div>
	
	<div>
		<p>
			<fmt:message key="settings_jsp.label.login"/>
		</p>
			<input name="login" value="${participant.login}">
	</div>
	
	<div>
		<p>
			<fmt:message key="settings_jsp.label.password"/>
		</p>
			<input name="password"  type="password" value="${participant.password}">
	</div>
								
					<input type="submit" value='<fmt:message key="settings_jsp.button.update"/>'><br/>
				</form> 
				
				<%-- CONTENT --%>	
			</td>
			
</c:if>
				
<c:if test="${empty participant and title ne 'Login'}">
	<div id="rightHeader">
		<a href="login.jsp">
			<fmt:message key="header_jspf.anchor.login"/>
		</a>
	</div>
</c:if>					
			
	<%@ include file="/WEB-INF/jspf/footer.jspf"%>
</div>
</body>
</html>
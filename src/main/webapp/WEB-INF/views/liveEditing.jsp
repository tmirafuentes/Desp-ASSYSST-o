<%--
  Created by IntelliJ IDEA.
  User: Troy Mirafuentes
  Date: 2/1/2019
  Time: 1:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:url value="/css/assystxStyle.css" var="jstlCss" />
    <link rel="stylesheet" type="text/css" href="${jstlCss}">
    <meta charset="UTF-8">
</head>
<body>
<div id = "header">
    <p id = "system_title"> ASSYSTX </p>
    <c:choose>
        <c:when test="${pageContext.request.userPrincipal.name != null}">
            <a id="user_icon" href="/account">${pageContext.request.userPrincipal.name}</a> <!--leads to account settings-->
            <form id="logoutForm" method="POST" action="${contextPath}/logout">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
            <a id="user_icon" onclick="document.forms['logoutForm'].submit()">Logout</a>
        </c:when>
        <c:otherwise>
            <a href ="#" id = "user_icon">User</a>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>

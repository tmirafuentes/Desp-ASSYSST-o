<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 03-Feb-19
  Time: 3:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>
<div id = "header">
    <p id = "system_title"> ASSYSTX </p>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <a onclick="document.forms['logoutForm'].submit()" id="user_icon">${loggedUser}</a>
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
        <a onclick="document.forms['logoutForm'].submit()">Logout</a>
    </c:if>
</div>
<div id = "smaller_header">
        <p id = "last_edited">Updated as of 1 hour ago by</p>
        <p id = "total_load">Total Load: <c:out value="${facLoadInfo}"/> </p>
        <p id = "modify_offering"> Raise Concerns</p>
</div>
</body>
</html>

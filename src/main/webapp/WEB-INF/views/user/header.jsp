<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 03-Feb-19
  Time: 3:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
        <a href ="${contextPath}/revision-history" id = "last_edited"></a>
        <a href ="#" id="users_title"></a>
        <div id="online_icons">
        </div>
        <p id = "modify_offering"> Modify Offering</p>
    </div>
</html>

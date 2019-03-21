<%@ page import="org.springframework.security.web.WebAttributes" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>ASSYSTX</title>
    <c:url value="/css/mainStyle.css" var="mainCss" />
    <link rel="stylesheet" type="text/css" href="${mainCss}">
    <!-- <script src="myScript.js"/> -->
</head>
<body>
<div id = "header">
    <p id = "system_title"> ASSYSTX </p>
</div>
<div id = "main_content">
    <form method="POST" action="/signin">
    <table id = "table_login">
        <tr>
            <td><p class = "p_table_label"> ID Number</p></td>
        </tr>
        <tr><!-- APO: 22131451     CVC: 22742131 -->
            <td><input type="text" id="text_ID" name="username" value="22742131 "></td>
        </tr>
        <tr>
            <td><p class = "p_table_label">Password</p></td>
        </tr>
        <tr>
            <td><input type="password"  id="text_password" name="password" value="iLoveCCS"></td>
        </tr>
        <tr>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <td><button id="button_submit" type="submit">Submit</button></td>
        </tr>
    </table>
        <div id="login_error_message">
            Error = ${error}
        </div>
    </form>
</div>
<div id = "smaller_header">
</div>
</body>

</html>
<%@ page import="org.springframework.security.web.WebAttributes" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<meta name="viewport" content="width=device-width, initial-scale=1.0">

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Unnamed System</title>
    <link rel="stylesheet" type="text/css" href="../css/assystxLoginStyle.css">
    <!-- <script src="myScript.js"/> -->
</head>
<body>
<div id = "header">
    <p id = "system_title"> Unnamed System </p>
</div>
<div id = "main_content">
    <form method="POST" action="${contextPath}/signin">
    <table id = "table_login">
        <tr>
            <td><p class = "p_table_label"> ID Number</p></td>
        </tr>
        <tr>
            <td><input type="text" id="text_ID" name="username"></td>
        </tr>
        <tr>
            <td><p class = "p_table_label">Password</p></td>
        </tr>
        <tr>
            <td><input type="password"  id="text_password" name="password"></td>
        </tr>
        <tr>
            <td><button id="button_submit" type="submit">Submit</button></td>
        </tr>
    </table>
    </form>
</div>
<div id = "smaller_header">
</div>
</body>

</html>
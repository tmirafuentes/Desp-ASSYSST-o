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
<div id = "smaller_header">
    <a href ="ASSYSTXRevision.html" id = "last_edited">Last edited 1 hour ago</a>
    <a href ="#" id = "online_icons">Online Users</a>
    <p id = "modify_offering"> Modify Offering</p>
</div>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 03-Feb-19
  Time: 3:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<html>
<head>
    <title>Unnamed System</title>
    <title>ASSYSTX</title>
    <c:url value="/css/assystxStyle.css" var="mainCss" />
    <c:url value="/css/jquery/jquery-ui.css" var="jqueryCss" />
    <c:url value="/scripts/jquery/jquery-3.3.1.min.js" var="minJquery" />
    <c:url value="/scripts/jquery/jquery-ui.js" var="uiJquery" />
    <c:url value="/scripts/assystxMainScript.js" var="mainScript" />

    <link rel="stylesheet" type="text/css" href="${mainCss}">
    <link rel="stylesheet" type="text/css" href="${jqueryCss}">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
    <script src="${minJquery}"></script>
    <script src="${uiJquery}"></script>
    <script src="${mainScript}"></script>
</head>
<body>
<%@ include file="leftFacultyLoad.jsp" %>
<%@ include file="../user/header.jsp" %>
<div id = "main_content">
    <table id = "generated_list">
        <tr>
            <th>Last name</th>
            <th>First name</th>
            <th>Teaching Load</th>
            <th>Admin Load</th>
            <th>Research Load</th>
            <th>Total Load</th>
        </tr>
        <tr>
            <td>Deja</td>
            <td>Jordan</td>
            <td>12</td>
            <td>3</td>
            <td>3</td>
            <td>18</td>
        </tr>
        <tr>
            <td>Deja</td>
            <td>Jordan</td>
            <td>12</td>
            <td>3</td>
            <td>3</td>
            <td>18</td>
        </tr>
    </table>
</div>
<%@ include file="rightFacultyLoad.jsp" %>

</body>

</html>
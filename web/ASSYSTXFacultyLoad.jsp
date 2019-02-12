<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 03-Feb-19
  Time: 3:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<html>
<head>
    <title>Unnamed System</title>
    <link rel="stylesheet" type="text/css" href="../src/main/resources/static/css/assystxStyle2.css">
    <link rel="stylesheet" type="text/css" href="css/jquery/jquery-ui.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
    <script src="scripts/jquery/jquery-3.3.1.min.js"></script>
    <script src="scripts/jquery/jquery-ui.js"></script>
    <script src="scripts/assystxFacultyLoadScript.js"></script>
</head>
<body>
<%@ include file="leftFacultyLoad.jsp" %>
<%@ include file="header.jsp" %>
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
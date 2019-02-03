<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 03-Feb-19
  Time: 3:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<html>
<head>
    <title>Unnamed System</title>
    <link rel="stylesheet" type="text/css" href="css/assystxStyle.css">
    <link rel="stylesheet" type="text/css" href="css/jquery/jquery-ui.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
    <script src="scripts/jquery/jquery-3.3.1.min.js"></script>
    <script src="scripts/jquery/jquery-ui.js"></script>
    <script src="scripts/assystxAPOScript.js"></script>
</head>
<body>
<%@ include file="leftAPO.jsp" %>
<%@ include file="header.jsp" %>
<div id = "main_content">
    <table id = "generated_list">
        <tr>
            <th>Course</th>
            <th>Section</th>
            <th>Day</th>
            <th>Time</th>
            <th>Room</th>
            <th>Faculty</th>
        </tr>
        <tr>
            <td>INOVATE</td>
            <td>S17</td>
            <td>TH</td>
            <td>0730 - 900</td>
            <td>G206</td>
            <td>Jordan Deja</td>
        </tr>
        <tr>
            <td>INOVATE</td>
            <td>S17</td>
            <td>TH</td>
            <td>0730 - 900</td>
            <td>G206</td>
            <td>Jordan Deja</td>
        </tr>
    </table>
</div>
<%@ include file="rightAPOChair.jsp" %>
</body>

</html>

<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 03-Feb-19
  Time: 3:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<html>
<head>
    <title>Unnamed System</title>
    <link rel="stylesheet" type="text/css" href="../src/main/resources/static/css/assystxStyle2.css">
    <!-- <script src="myScript.js"/> -->
</head>
<body>
<div id = "header">
    <p id = "system_title"> Unnamed System </p>
</div>
<div id = "login_main_content">
    <table id = "table_login">
        <tr>
            <td><p class = "p_table_label"> ID Number</p></td>
        </tr>
        <tr>
            <td><input id = "text_section" placeholder = "" id="text_ID"></td>
        </tr>
        <tr>
            <td><p class = "p_table_label"> Password</p></td>
        </tr>
        <tr>
            <td><input  type = "password" id = "text_section" placeholder = "" id="text_password"></td>
        </tr>
        <tr>
            <td><button id="login_button_submit">Submit</button></td>
        </tr>
    </table>
</div>
<div id = "login_smaller_header">
</div>
</body>

</html>
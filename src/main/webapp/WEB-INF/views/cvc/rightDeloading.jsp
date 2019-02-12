<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 04-Feb-19
  Time: 3:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/assystxStyle.css">
    <link rel="stylesheet" type="text/css" href="css/jquery/jquery-ui.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
    <script src="scripts/jquery/jquery-3.3.1.min.js"></script>
    <script src="scripts/jquery/jquery-ui.js"></script>
    <script src="scripts/assystxDeloadScript.js"></script>
</head>
<body>
<div id = "right_side">
    <table id = "load_img_names">
        <tr>
            <td><td><img src id="deload_image_faculty"></td></td>
        </tr>
        <tr>
            <th>Last Name</th>
        </tr>
        <tr>
            <td><p class = "p_faculty" id = "p_faculty_lname">Deja</p></td>
        </tr>
        <tr>
            <th>First Name</th>
        </tr>
        <tr>
            <td><p class = "p_faculty" id = "p_faculty_fname">Jordan</p></td>
        </tr>
    </table>
    <table id = "fType_department">
        <tr>
            <th>Faculty Type</th>
            <th>Department</th>
        </tr>
        <tr>
            <th><p class = "p_faculty" id = "p_faculty_type">PT</p></th>
            <th><p class = "p_faculty" id = "p_faculty_department">ST</p></th>
        </tr>
    </table>
    <p class = "p_faculty" id="load_summary">Load Summary</p>
    <table id = "load_list">
        <tr>
            <th>Admin Load: </th>
            <td><p class = "p_faculty" id = "p_admin_load">0</p></td>
        </tr>
        <tr>
            <th>Teaching Load: </th>
            <td><p class = "p_faculty" id = "p_teaching_load">9</p></td>
        </tr>
        <tr>
            <th>Research Load: </th>
            <td><p class = "p_faculty" id = "p_research_load">0</p></td>
        </tr>
        <tr>
            <th>Total Current Load: </th>
            <td><p class = "p_faculty" id = "p_total_load">9</p></td>
        </tr>
    </table>
    <button id="deload_button">Deload Faculty</button>

</div>
</body>
</html>

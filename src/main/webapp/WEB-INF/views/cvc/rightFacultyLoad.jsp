<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 03-Feb-19
  Time: 3:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>
<div id = "right_side">
    <table id = "table_faculty_name">
        <tr>
            <th>Last Name</th>
            <th>First Name</th>
        </tr>
        <tr>
            <td><p class = "p_faculty" id = "p_faculty_lname">Jordan</td>
            <td><p class = "p_faculty" id = "p_faculty_fname">Deja</td>
        </tr>
    </table>
    <table id = "table_preferences">
        <tr>
            <td><p class = "p_faculty" id = "p_faculty_type">Full-Time</p></td>
        </tr>
        <tr>
            <td><p class = "p_faculty" id = "p_faculty_department">Software Technology</p></td>
        </tr>
        <!--
        <tr>
            <th>Preferences:</th>
        </tr>
        <tr>
            <td><div id ="div_preferences"></div></td>
        </tr>
        -->
        <p class = "p_faculty" id="label_currLoad">Current Load: </p>
    </table>
    <table id = "table_curr_load">
        <tr>
            <th>Course Code</th>
            <th>Day</th>
            <th>Time</th>
            <th>Room</th>
        </tr>
        <tr class = "generated_current_load">
            <td>INOVATE</td>
            <td>WF</td>
            <td>0915 - 1045</td>
            <td>G201</td>
        </tr>
    </table>
    <!--
    <table id = "table_currentlytaking">
        <tr>
            <th>Current Load:</th>
        </tr>
        <tr>
            <td><div id ="div_load"></div></td>
        </tr>
        <tr>
            <td><button id="load_button_assign_faculty">Load Faculty</button></td>
        </tr>
    </table>
    -->
</div>
</body>
</html>

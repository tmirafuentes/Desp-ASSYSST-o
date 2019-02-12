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
            <td><img src  id="image_faculty"></img></td>
        </tr>
        <tr>
            <td><p class = "p_faculty" id = "p_faculty_type">Full-Time Professor</p></td>
        </tr>
        <tr>
            <td><p class = "p_faculty" id = "p_faculty_department">Software Technology Department</p></td>
        </tr>
        <tr>
            <th>Preferences:</th>
        </tr>
        <tr>
            <td><div id ="div_preferences"></div></td>
        </tr>
    </table>

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

</div>
</body>
</html>

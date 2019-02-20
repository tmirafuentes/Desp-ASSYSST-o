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
<div id = "right_side" class="modify_sidebar">
    <table id = "table_faculty_name">
        <tr>
            <th>
                <p class="facultyInfoTitles" id="facultyInfoTitles_name"></p>
            </th>
    </table>
    <table id = "table_preferences">
        <tr>
            <td><p class = "facultyInfoTitles" id = "facultyInfoTitles_type">Full-Time</p></td>
        </tr>
        <tr>
            <td><p class = "facultyInfoTitles" id = "facultyInfoTitles_department">Software Technology</p></td>
        </tr>
        <!--
        <tr>
            <th>Preferences:</th>
        </tr>
        <tr>
            <td><div id ="div_preferences"></div></td>
        </tr>
        -->
        <tr>
            <td>
                <p class = "facultyInfoTitles" id="label_currLoad">Current Load:</p>
            </td>
            <td>
                <p class = "facultyInfoTitles" id="facultyInfo_currLoad"></p>
            </td>
        </tr>
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
    <table class = "rightDeloadingLoadingButtons">
        <tr>
            <td>
                <button class="facultyInfoButtons">Assign Load</button>
            </td>
            <td>
                <button class="facultyInfoButtons" id="modFacDeloadButton">Deload</button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>

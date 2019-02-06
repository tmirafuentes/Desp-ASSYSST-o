<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 03-Feb-19
  Time: 3:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div id = "right_side">
    <table id = "modify_class_section">
        <tr>
            <th>Class Type</th>
            <th>Section</th>
        </tr>
        <tr>
            <td><select id = "select_right_class_type"> </select></td>
            <td><input id = "text_section" placeholder = ""></td>
        </tr>
    </table>
    <table id = "modify_time">
        <tr>
            <th>Start Time</th>
            <th>End Time</th>

        </tr>
        <tr>
            <td><select id = "select_right_start_timeblock"><option value="" disabled selected>HH:MM</option></select></td>
            <td><select id = "select_right_end_timeblock"><option value="" disabled selected>HH:MM</option></select></td>

        </tr>
    </table>
    <table id = "modify_days">
        <tr>
            <th>Day 1</th>
            <th>Day 2</th>
        </tr>
        <tr>
            <td><select id = "select_day1"> </select></td>
            <td><select id = "select_day2"> </select></td>
        </tr>
    </table>
    <table id = "modify_room">
        <tr>
            <th>Room</th>
        </tr>
        <tr>
            <td><input id = "text_room"></input></td>
            <td><button id="button_assign_room">Change Room</button></td>
        </tr>
    </table>
    <table id = "modify_faculty">
        <tr>
            <th>Faculty</th>
        </tr>
        <tr>
            <td><select id = "select_faculty"></select></td>
            <td><button id="button_assign_faculty">Re-Assign</button></td>
        </tr>
    </table>
    <table id = "modify_concerns">
        <tr>
            <th>Concerns</th>
        </tr>
        <tr>
            <td><textarea  id="area_concerns"></textarea></td>
        </tr>
    </table>
    <table id = "modify_button_concerns">
        <tr>
            <td><button id="button_submit_concerns">Submit</button></td>
            <td><button id="button_cancel_concerns">Cancel</button></td>
        </tr>
    </table>
</div>
</body>
</html>

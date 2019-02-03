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
            <td><select id = "select_right_start_timeblock"> </select></td>
            <td><select id = "select_right_end_timeblock"> </select></td>

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

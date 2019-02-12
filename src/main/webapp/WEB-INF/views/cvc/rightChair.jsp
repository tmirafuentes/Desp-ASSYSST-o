<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 03-Feb-19
  Time: 3:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div id = "right_side">
    <table id = "modify_class_section" class="modify_sidebar">
        <tr>
            <th>Class Type</th>
            <th>Section</th>
        </tr>
        <tr>
            <td><select id = "select_right_class_type"> </select></td>
            <td><input id = "text_section" type="text" value="${selOffering.section}"></td>
        </tr>
    </table>
    <table id = "modify_time" class="modify_sidebar">
        <tr>
            <th>Start Time</th>
            <th>End Time</th>

        </tr>
        <tr>
            <td><select id = "select_right_start_timeblock"> </select></td>
            <td><select id = "select_right_end_timeblock"> </select></td>

        </tr>
    </table>
    <table id = "modify_days" class="modify_sidebar">
        <tr>
            <th>Day 1</th>
            <th>Day 2</th>
        </tr>
        <tr>
            <td>
                <select id = "select_day1">
                    <c:forEach items="${allDays}" var="letDay1">
                        <option value="${letDay1}">
                            <c:out value="${letDay1}" />
                        </option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <select id = "select_day2">
                    <c:forEach items="${allDays}" var="letDay2">
                        <option value="${letDay2}">
                            <c:out value="${letDay2}" />
                        </option>
                    </c:forEach>
                </select>
            </td>
        </tr>
    </table>
    <table id = "modify_room" class="modify_sidebar">
        <tr>
            <th>Room</th>
        </tr>
        <tr>
            <td><input id = "text_room"></input></td>
            <td><button id="button_assign_room">Change Room</button></td>
        </tr>
    </table>
    <table id = "modify_faculty" class="modify_sidebar">
        <tr>
            <th>Faculty</th>
        </tr>
        <tr>
            <td><select id = "select_faculty"></select></td>
            <td><button id="button_assign_faculty">Re-Assign</button></td>
        </tr>
    </table>
    <table id = "modify_concerns" class="modify_sidebar">
        <tr>
            <th>Concerns</th>
        </tr>
        <tr>
            <td><textarea  id="area_concerns"></textarea></td>
        </tr>
    </table>
    <table id = "modify_button_concerns" class="modify_sidebar">
        <tr>
            <td><button id="button_submit_concerns">Submit</button></td>
            <td><button id="button_cancel_concerns">Cancel</button></td>
        </tr>
    </table>
</div>
</body>
</html>

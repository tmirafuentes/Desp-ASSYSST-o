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
<div class = "rightSidebar">
    <form id="modify_offering_form">
     <table id = "modifyOfferingSidebar" class="modify_sidebar" >
        <tr>
            <th>Class Type</th>
            <th>Section</th>
        </tr>
        <tr>
            <td>
                <!-- Offering Type/Status -->
                <select name="classStatus" id="select_right_class_type">
                    <option value="Regular">Regular</option>
                    <option value="Elective">Elective</option>
                    <option value="Special">Special</option>
                    <option value="Dissolved">Dissolved</option>
                </select>
            </td>
            <td>
                <!-- Offering Section -->
                <input name="classSection" id = "text_section" type="text" />
            </td>
        </tr>
        <tr>
            <th>Start Time</th>
            <th>End Time</th>
        </tr>
        <tr>
            <td>
                <!-- Start Time -->
                <input name="startTime" type="time" id="select_right_start_timeblock" />
                <input id = "startTimeHolder" hidden>
            </td>
            <td>
                <!-- End Time -->
                <input name="endTime" type="time" id="select_right_end_timeblock" />
                <input id = "endTimeHolder" hidden>
            </td>
        </tr>
        <tr>
            <th>Day 1</th>
            <th>Day 2</th>
        </tr>
        <tr>
            <td>
                <!-- Day 1 -->
                <select name="day1" id = "select_day1">
                    <option value="-" selected="selected">-</option>
                    <c:forEach items="${allDays}" var="letDay1">
                        <option value="${letDay1}">
                            <c:out value="${letDay1}" />
                        </option>
                    </c:forEach>
                </select>
            </td>
            <td>
                <!-- Day 2 -->
                <select name="day2" id = "select_day2">
                    <option value="-" selected="selected">-</option>
                    <c:forEach items="${allDays}" var="letDay2">
                        <option value="${letDay2}">
                            <c:out value="${letDay2}" />
                        </option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <th>Room</th>
        </tr>
        <tr>
            <td>
                <!-- Room Code -->
                <input name="roomCode" id = "text_room" type="text" readonly="readonly" />
            </td>
            <td>
                <button id="modOffRoomButton" class="modOffUsableButton" type="button">Change Room</button>
            </td>
        </tr>
        <tr>
            <th>Faculty</th>
        </tr>
        <tr>
            <td>
                <input name="faculty" id = "select_faculty" type="text" readonly="readonly" />
            </td>
            <td>
                <button id="modOffFacultyButton" class="modOffBlockedButton" type="button" disabled>Assign</button>
            </td>
        </tr>
        <!--<tr>
            <th>Concerns</th>
        </tr>
        <tr>
            <td><textarea id="area_concerns"></textarea></td>
        </tr>-->
        <tr>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input name="offeringId" type="hidden" id="text_offId" />
            <td class="alignButton"><button id="button_submit_modifyOffering" type="submit">Submit</button></td>
            <td class="alignButton"><button id="button_cancel_concerns" type="reset">Cancel</button></td>
        </tr>
    </table>
    </form>
</div>
</body>
</html>

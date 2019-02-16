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
    <form:form method="POST" modelAttribute="offerModifyForm">
        <table id = "modifyOfferingTypeSection" class="modify_sidebar">
            <tr>
                <th>Class Type</th>
                <th>Section</th>
            </tr>
            <tr>
                <td>
                    <!-- Offering Type -->
                    <spring:bind path="classStatus">
                        <form:select path="classStatus" id = "select_right_class_type">
                            <form:option value="Regular">Regular</form:option>
                            <form:option value="Elective">Elective</form:option>
                            <form:option value="Special">Special</form:option>
                            <form:option value="Dissolved">Dissolved</form:option>
                        </form:select>
                    </spring:bind>
                </td>
                <td>
                    <!-- Offering Section -->
                    <spring:bind path="classSection">
                        <form:input path="classSection" id = "text_section" type="text" />
                    </spring:bind>
                </td>
            </tr>
        </table>
        <table id = "modifyOfferingTime" class="modify_sidebar">
            <td>
                <!-- Start Time -->
                <spring:bind path="startTime">
                    <form:input path="startTime" type="time" id="select_right_start_timeblock" />
                </spring:bind>
            </td>
            <td>
                <!-- End Time -->
                <spring:bind path="endTime">
                    <form:input path="endTime" type="time" id="select_right_end_timeblock" />
                </spring:bind>
            </td>
        </table>
        <table id = "modifyOfferingDays" class="modify_sidebar">
            <tr>
                <th>Day 1</th>
                <th>Day 2</th>
            </tr>
            <tr>
                <td>
                    <!-- Day 1 -->
                    <spring:bind path="day1">
                        <form:select path="day1" id = "select_day1">
                            <form:option value="-" selected="selected">-</form:option>
                            <c:forEach items="${allDays}" var="letDay1">
                                <form:option value="${letDay1}">
                                    <c:out value="${letDay1}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </spring:bind>
                </td>
                <td>
                    <!-- Day 2 -->
                    <spring:bind path="day2">
                        <form:select path="day2" id = "select_day2">
                            <form:option value="-" selected="selected">-</form:option>
                            <c:forEach items="${allDays}" var="letDay2">
                                <form:option value="${letDay2}">
                                    <c:out value="${letDay2}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </spring:bind>
                </td>
            </tr>
        </table>
        <table id = "modifyOfferingRoom" class="modify_sidebar">
            <tr>
                <th>Room</th>
            </tr>
            <tr>
                <td>
                    <spring:bind path="roomCode">
                        <form:input path="roomCode" id = "text_room" type="text" disabled="true"/>
                    </spring:bind>
                </td>
                <td>
                    <button id="modOffRoomButton" class="modOffBlockedButton" type="button" disabled>Change Room</button>
                </td>
            </tr>
        </table>
        <table id = "modifyOfferingFaculty" class="modify_sidebar">
            <tr>
                <th>Faculty</th>
            </tr>
            <tr>
                <td>
                    <spring:bind path="facultyName">
                        <form:input path="facultyName" id = "select_faculty" type="text" disabled="true"/>
                    </spring:bind>
                </td>
                <td>
                    <button id="modOffFacultyButton" class="modOffUsableButton" type="button">Re-Assign</button>
                </td>
            </tr>
        </table>
        <table id = "modifyOfferingConcerns" class="modify_sidebar">
            <tr>
                <th>Concerns</th>
            </tr>
            <tr>
                <td><textarea  id="area_concerns"></textarea></td>
            </tr>
        </table>
        <table id = "modifyOfferingButtons" class="modify_sidebar">
            <tr>
                <td><button id="button_submit_concerns">Submit</button></td>
                <td><button id="button_cancel_concerns">Cancel</button></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>

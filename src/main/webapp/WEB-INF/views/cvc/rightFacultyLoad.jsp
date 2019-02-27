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
    </table>
    <p class = "facultyInfoTitles" id="label_currLoad">Current Load: </p>
    <table class = "rightDeloadingLoadingButtons">
        <tr>
            <td>
                <button class="facultyInfoButtons">Assign Load</button>
            </td>
            <td>
                <button class="facultyInfoButtons" id="modFacDeloadButton">Deload</button>
                <input type="text" id="deload_id" hidden>
                <input type="text" id="right_total_load" hidden>
            </td>
        </tr>
    </table>
    <div class="generatedFacultyLoadTable">
        <div class="generatedLoad">
            <div class="genLoadRows">
                <div class="genLoadCols">Course Code</div>
                <div class="genLoadCols">Section</div>
                <div class="genLoadCols">Day</div>
                <div class="genLoadCols">Time</div>
                <div class="genLoadCols">Room</div>
            </div>
            <c:forEach items="${allOfferings}" var="offering">
                <div class="genLoadRows">
                    <div class="genLoadCols" name="courseCode">
                        <c:out value="${offering.course.courseCode}" />
                        <input type="text" id='Offeringfaculty_id' value="${offering.faculty.userId}" hidden>
                    </div>
                    <div class="genLoadCols" name="section">
                        <c:out value="${offering.section}" />
                    </div>
                    <div class="genLoadCols" name="time">
                        <c:forEach items="${offering.daysSet}" var="days" varStatus="dCtr">
                            <c:out value="${days.classDay}" />
                        </c:forEach>
                    </div>
                    <div class="genLoadCols" name="days">
                        <c:forEach items="${offering.daysSet}" var="time" begin="0" end="0">
                            <c:out value="${time.beginTime}" /> - <c:out value="${time.endTime}" />
                        </c:forEach>
                    </div>
                    <div class="genLoadCols" name="room">
                        <c:forEach items="${offering.daysSet}" var="rooms" begin="0" end="0">
                            <c:out value="${rooms.room.roomCode}" />
                        </c:forEach>
                    </div>
                    <div class = "genLoadCols" name="id" hidden>
                        <c:out value="${offering.faculty.userId}" />
                    </div>
                </div>

            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>

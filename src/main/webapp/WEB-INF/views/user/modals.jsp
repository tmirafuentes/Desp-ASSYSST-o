<%--
  Created by IntelliJ IDEA.
  User: Troy Mirafuentes
  Date: 2/13/2019
  Time: 7:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <body>
        <!-- Modal for Assign Room -->
        <div class="divModals" id="modalAssignRoom">
            <table class="modal_header">
                <tr>
                    <th>Search</th>
                    <th margin-left="50px">Room Type</th>
                    <th>Building</th>
                </tr>
                <tr>
                    <td>
                        <input class = 'modal_search' id='input_search_room'>
                        <button id='button_search_room'><i class='fas fa-search'></i></button>
                    </td>
                    <td>
                        <select class = 'modal_select' id='select_room_type'>
                            <option value="All">All</option>
                            <c:forEach items="${allRoomTypes}" var="roomType">
                                <option value="${roomType}"><c:out value="${roomType}" /></option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <select class = 'modal_select' id='select_building'>
                            <option value='All'>All</option>
                            <c:forEach items='${allBuildings}' var='building'>
                                <option value='${building.bldgName}'><c:out value='${building.bldgName}' /></option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>
            <table id="modal_table_room">
                <tr>
                    <th>Room</th>
                    <th>Room Type</th>
                    <th>Building</th>
                    <th>Capacity</th>
                    <th>Assign</th>
                </tr>
                <c:forEach items="${allRooms}" var="room">
                    <tr>
                        <td>${room.roomCode}</td>
                        <td>${room.roomType}</td>
                        <td>${room.building.bldgName}</td>
                        <td>${room.roomCapacity}</td>
                        <td><button class = 'assign_modal_buttons'>Assign</button></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <!-- Modal for Concerns -->
        <div class="divModals" id="modalConcerns">
            <table class='concern_entry'>
            <tr>
                <td class ='concern_name'>Ryan Dimaunahan</td>
                <td class ='concern_time'>1:29 PM</td>
                </tr>
            <tr>
                <td colspan='2' class ='concern_message'>Hello Sir Ryan, Concern lang po. Si Doc Mc ay bawal na mag-stay ng gabi so no night classes. Tnx po.</td>
                </tr>
            </table>
        </div>

        <!-- Modal for Add Course Offering -->
        <div class="divModals" id="modalAddOffering">
            <table class="modal_header">
                <tr>
                    <th>Degree Program</th>
                    <th>Batch</th>
                    <th>Academic Year</th>
                    <th>Term</th>
                    <th>Search</th>
                </tr>
                <tr>
                    <td><select class = 'modal_select' id='select_degree'></select></td>
                    <td><select class = 'modal_select' id='select_batch'></select></td>
                    <td><select class = 'modal_select' id='select_academic_year'></select></td>
                    <td><select class = 'modal_select' id='select_term'></select></td>
                    <td><input class = 'modal_search' id='modal_input_search_course'><button id='button_search_course'><i class='fas fa-search'></i></button></td>
                </tr>
            </table>
            <table id='modal_table_add_courses'>
                <tr>
                    <th>Course</th>
                    <th>Name</th>
                    <th>Units</th>
                    <th>Add</th>
                </tr>
                <c:forEach items="${allCourses}" var="course">
                    <tr>
                        <td>${course.courseCode}</td>
                        <td>${course.courseName}</td>
                        <td>${course.units}</td>
                        <td><button class = 'add_modal_buttons'>+</button></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td>INOVATE</td>
                    <td>Technology and Innovation Management</td>
                    <td>3.0</td>
                    <td><button class = 'add_modal_buttons'>+</button></td>
                </tr>
            </table>
        </div>

        <div class="divModals" id="modalAssignFaculty">
        </div>
    </body>
</html>

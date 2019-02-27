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
                    <td>
                        <select class = 'modal_select' id='select_degree'>
                            <option value="All">All</option>
                            <c:forEach items="${allDegrees}" var="degreeType">
                                <option value="${degreeType.degreeName}"><c:out value="${degreeType.degreeName}" /></option>
                            </c:forEach>
                        </select>
                    </td>
                    <!--<td><select class = 'modal_select' id='select_batch'></select></td>
                    <td><select class = 'modal_select' id='select_academic_year'></select></td>
                    <td><select class = 'modal_select' id='select_term'>
                        <option value="All">All</option>
                        <option value="First">1st</option>
                        <option value="Second">2nd</option>
                        <option value="Third">3rd</option>
                    </select></td>-->
                    <td><input class = 'modal_search' id='modal_input_search_course'><button id='button_search_course'><i class='fas fa-search fa-lg'></i></button></td>
                </tr>
            </table>
            <form:form method="POST" action="/apo/add-offering" modelAttribute="addOfferingForm">
                <table id='modal_table_add_courses'>
                    <tr>
                        <th>Course</th>
                        <th>Name</th>
                        <th>Units</th>
                        <th>Add</th>
                    </tr>
                    <c:forEach items="${allCourses}" var="course">
                        <tr>
                            <td class="course_code">
                                    ${course.courseCode}
                            </td>
                            <td class="course_name">
                                    ${course.courseName}
                            </td>
                            <td class="course_units">
                                    ${course.units}
                            </td>
                            <td>
                                <button type="submit" class = 'add_modal_buttons add_offer_btns' value="${course.courseCode}">
                                    +
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <spring:bind path="courseCode">
                    <form:input type="text" path="courseCode" id="add_offer_field" hidden="hidden" />
                </spring:bind>
            </form:form>
        </div>

        <!-- Modal for Assign Room -->
        <div class="divModals" id="modalAssignRoom">
            <table class="modal_header">
                <tr>
                    <th>Search</th>
                    <%--<th margin-left="50px">Room Type</th>
                    <th>Building</th> --%>
                </tr>
                <tr>
                    <td>
                        <input class = 'modal_search' id='input_search_room'>
                        <button id='button_search_room'><i class='fas fa-search'></i></button>
                    </td>
                    <!--
                    <td>
                        <select class = 'modal_select' id='select_room_type'>
                            <option value="All">All</option>
                            <%--
                            <c:forEach items="${allRoomTypesModal}" var="roomType">
                                <option value="${roomType}"><c:out value="${roomType}"/></option>
                            </c:forEach>
                            --%>
                        </select>
                    </td>
                    <td>
                        <select class = 'modal_select' id='select_building'>
                            <option value='All'>All</option>
                            <%--
                            <c:forEach items='${allBuildings}' var='building'>
                                <option value='${building.bldgName}'><c:out value='${building.bldgName}' /></option>
                            </c:forEach>
                            --%>
                        </select>
                    </td>
                    -->
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
                        <td><button class = 'assign_modal_buttons assignRoomBtns' value="${room.roomCode}" type="button">Assign</button></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <!-- Modal for Assigning Faculty -->
        <div class="divModals" id="modalAssignFaculty">
            <table class="modal_header">
                <tr>
                    <!--<th>Degree Program</th>-->
                    <!--<th>Batch</th>-->
                    <!--<th>Academic Year</th>-->
                    <!--<th>Term</th>-->
                    <th>Search</th>
                    <th>Recommendation</th>
                    <th>Sort By</th>
                </tr>
                <tr>
                    <td><input class = 'modal_search' id='modal_input_search_faculty'><button id='button_search_faculty'><i class='fas fa-search'></i></button></td>
                    <td><select class = 'modal_select' id='select_recommend'></select></td>
                    <td><select class = 'modal_select' id='select_sort'></select></td>
                    <%--<td><select class = 'modal_select' id='select_degree'>
                        <option value="All">All</option>
                        <%--<c:forEach items="${allDegrees}" var="degreeType">
                            <option value="${degreeType.degreeName}"><c:out value="${degreeType.degreeName}" /></option>
                        </c:forEach>
                    </select></td>
                    <td><select class = 'modal_select' id='select_batch'></select></td>
                    <td><select class = 'modal_select' id='select_academic_year'></select></td>
                    <td><select class = 'modal_select' id='select_term'>
                        <option value="All">All</option>
                        <option value="First">1st</option>
                        <option value="Second">2nd</option>
                        <option value="Third">3rd</option>
                    </select></td>--%>
                    <td><input class = 'modal_search' id='modal_input_search_course'><button id='button_search_course'><i class='fas fa-search fa-lg'></i></button></td>
                </tr>
            </table>
            <table id="modal_table_assign_faculty">
                <tr>
                    <th>Last Name</th>
                    <th>First Name</th>
                    <th>Teaching Load</th>
                    <th>Admin Load</th>
                    <th>Research Load</th>
                    <th>Total Load</th>
                    <th>Assign</th>
                </tr>
                <c:forEach items="${allFacultyLoad}" var="facLoad">
                    <tr>
                        <td>${facLoad.faculty.lastName}</td>
                        <td>${facLoad.faculty.firstName}</td>
                        <td>${facLoad.teachingLoad}</td>
                        <td>${facLoad.adminLoad}</td>
                        <td>${facLoad.researchLoad}</td>
                        <td>${facLoad.totalLoad}</td>
                        <td>
                            <button class = 'add_modal_buttons assignFacultyBtns' value="${facLoad.faculty.lastName}, ${facLoad.faculty.firstName}" type="button">Assign</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>

        </div>
        <!-- Modal for Deloading Faculty -->
        <div class="divModals" id="modalDeloadFaculty">
            <form:form method="POST" modelAttribute="facultyloadModifyForm">
                <spring:bind path="loadId">
                    <form:input path="loadId" type="hidden" id="modal_deload_id"/>
                </spring:bind>
                <input type="text" id="modal_deload_id" hidden>

                <p id="dialog_professor_name"></p>
                <table id="dialog_load_table">
                    <tr>
                        <td>
                            <p class="p_modal" id="modal_current_load">Total Current Load: </p>
                        </td>
                        <td>
                        </td>
                        <p id="p_total_load"></p>
                    </tr>
                    <tr>
                        <td>
                            <p class="p_modal">Deloading Code</p>
                        </td>
                        <td>
                            <spring:bind path="deloadType">
                                <form:select path="deloadType" id = "select_deload">
                                    <c:forEach items="${alldeloadInstances}" var="loadType">
                                        <form:option value="${loadType.deloading.units}">
                                            <c:out value="${loadType.deloading.deloadCode}" />
                                        </form:option>
                                    </c:forEach>
                                </form:select>
                            </spring:bind>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <p class="p_modal">Deloaded Units</p>
                        </td>
                        <td>
                            <spring:bind path="deloadUnits">
                                <form:input path="deloadUnits" type="number" id="input_deload" />
                            </spring:bind>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <button class="modal_buttons" id="deload_confirm">Confirm</button>
                        </td>
                        <td>
                            <button class="modal_buttons" id="delaod_reset">Reset</button>
                        </td>
                    </tr>
                </table>
            </form:form>
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
                    <th>Search</th>
                    <%--<th>Recommendation</th>
                    <th>Sort By</th>--%>
                </tr>
                <tr>
                    <td><input class = 'modal_search' id='modal_input_search_faculty'><button id='button_search_faculty'><i class='fas fa-search'></i></button></td>
                    <%--<td><select class = 'modal_select' id='select_recommend'></select></td>
                    <td><select class = 'modal_select' id='select_sort'></select></td>--%>
                </tr>
            </table>
        </div>
    </body>
</html>

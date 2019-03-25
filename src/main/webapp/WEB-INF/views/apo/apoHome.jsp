<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 03-Feb-19
  Time: 3:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
    <head>
        <title>ASSYSTX</title>

        <meta name="_csrf" content="${_csrf.token}"/>
        <!-- default header name is X-CSRF-TOKEN -->
        <meta name="_csrf_header" content="${_csrf.headerName}"/>

        <c:url value="/css/mainStyle.css" var="mainCss" />
        <c:url value="/css/jquery/jquery-ui.css" var="jqueryCss" />
        <c:url value="/scripts/jquery/jquery-3.3.1.min.js" var="minJquery" />
        <c:url value="/scripts/jquery/jquery-ui.js" var="uiJquery" />
        <c:url value="/scripts/assystxMainScript.js" var="mainScript" />
        <c:url value="/scripts/assystxAJAXScript.js" var="ajaxScript" />
        <c:url value="/scripts/assystxFilterScript.js" var="filterScript" />
        <c:url value="/scripts/assystxDesignScript.js" var="designScript" />
        <link rel="stylesheet" type="text/css" href="${mainCss}">
        <link rel="stylesheet" type="text/css" href="${jqueryCss}">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
        <script src="${minJquery}"></script>
        <script src="${uiJquery}"></script>
        <script src="${mainScript}"></script>
        <script src="${ajaxScript}"></script>
        <script src="${filterScript}"></script>
        <script src="${designScript}"></script>
    </head>
    <body>
        <!-- Filter Sidebar for APO -->
        <%@ include file="leftAPO.jsp" %>

        <!-- General Header for ASSYSTX -->
        <%@ include file="../user/header.jsp" %>
        <input type="text" id="input_userID" value="${userID}" hidden>
        <!-- Collaborative Workspace for ASSYSTX -->
        <div class="collabWorkspace cwOfferings">
            <div class="generatedContent">
                <div class="genContentRows">
                    <div class="genContentCols">Course</div>
                    <div class="genContentCols">Section</div>
                    <div class="genContentCols">Day</div>
                    <div class="genContentCols">Time</div>
                    <div class="genContentCols">Room</div>
                    <div class="genContentCols">Faculty</div>
                </div>
            </div>
            <div class = "filter_comments">No Results Found</div>
        </div>

        <!-- Modify Sidebar for APO -->
        <%@ include file="../apo/rightAPO.jsp" %>

        <!-- Add Course Offering Modal for APO -->
        <div class="divModals" id="modalAddOffering">
            <table class="modal_header">
                <!--<tr>
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
                    <td><select class = 'modal_select' id='select_batch'></select></td>
                    <td><select class = 'modal_select' id='select_academic_year'></select></td>
                    <td><select class = 'modal_select' id='select_term'>
                        <option value="All">All</option>
                        <option value="First">1st</option>
                        <option value="Second">2nd</option>
                        <option value="Third">3rd</option>
                    </select></td>
                    <td><input class = 'modal_search' id='modal_input_search_course'><button id='button_search_course'><i class='fas fa-search fa-lg'></i></button></td>
                </tr>-->
            </table>
            <%--<form:form method="POST" action="/apo/add-offering" modelAttribute="addOfferingForm">--%>
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
            <%--<spring:bind path="courseCode">
                <form:input type="text" path="courseCode" id="add_offer_field" hidden="hidden" />
            </spring:bind>
        </form:form> --%>
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

        <!-- Modal for Concerns -->
        <div class="divModals" id="modalConcerns">
            <div id="concerns_tabs">
                <div class="concerns_buttons" id="button-concern-threads">
                    Threads
                </div>
                <div class="concerns_buttons" id="button-concern-compose">
                    Compose
                </div>
            </div>
            <div id="concerns_body">
                <div id = "concerns_list">
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
                <table id="concern_compose">
                    <tr>
                        <td class="compose_addressbar">To:</td>
                        <td class="compose_addressbar"><select  id="concern_receiver">
                            <c:forEach items="${allUsers}" var="user">
                                <option value="${user}">
                                    <c:out value="${user}" />
                                </option>
                            </c:forEach>
                        </select></td>
                    </tr>
                    <tr>
                        <td colspan="2"><textarea id="concern_content">This is a dummy text</textarea></td>
                    </tr>
                    <tr>
                        <td colspan="2" id="concern_button_submit"><button id="compose_submit" type="submit">Submit</button></td>
                    </tr>
                </table>
            </div>
        </div>
    </body>
</html>

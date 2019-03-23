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
        <%@ include file="leftChair.jsp" %>

        <!-- General Header for ASSYSTX -->
        <%@ include file="../user/header.jsp" %>
        <input type="text" id="input_userID" value="${userID}" hidden>
        <!-- Collaborative Workspace for ASSYSTX -->
        <div class="collabWorkspace cwOfferings">
            <form:form method="get">
                <c:choose>
                    <c:when test="${empty allOfferings}">
                        No offerings to display.
                    </c:when>
                    <c:otherwise>
                        <div class="generatedContent">
                            <div class="genContentRows">
                                <div class="genContentCols">Course</div>
                                <div class="genContentCols">Section</div>
                                <div class="genContentCols">Day</div>
                                <div class="genContentCols">Time</div>
                                <div class="genContentCols">Room</div>
                                <div class="genContentCols">Faculty</div>
                            </div>
                            <c:forEach items="${allOfferings}" var="offering">
                                <c:set var="currOffer" value="${offering.offeringId}" />
                                <c:set var="selOffer" value="${selOffering.offeringId}" />
                                <c:choose>
                                    <c:when test="${currOffer == selOffer}">
                                        <div class="genContentRows selectedOffering">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="genContentRows">
                                    </c:otherwise>
                                </c:choose>
                                    <!-- Course Code of Offering -->
                                    <div class="genContentCols" name="courseCode">
                                        <c:out value="${offering.course.courseCode}" />
                                    </div>
                                    <!-- Section of Offering -->
                                    <div class="genContentCols genList_section">
                                        <c:choose>
                                            <c:when test="${empty offering.section}">
                                                None
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${offering.section}" />
                                                <input type="text" id='off_section' value="${offering.section}" hidden>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <!-- Days Held of Offering -->
                                    <div class="genContentCols" name="days">
                                        <c:choose>
                                            <c:when test="${empty offering.daysSet}">
                                                None
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach items="${offering.daysSet}" var="days" varStatus="dCtr">
                                                    <c:out value="${days.classDay}" />
                                                    <input type="text" id='off_day${dCtr.count}' value="${days.classDay}" hidden>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <!-- Time Slot of Offering -->
                                    <div class="genContentCols">
                                        <c:choose>
                                            <c:when test="${empty offering.daysSet}">
                                                Unassigned
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach items="${offering.daysSet}" var="time" begin="0" end="0">
                                                    <c:out value="${time.beginTime}" /> - <c:out value="${time.endTime}" />
                                                    <input type="text" value="${time.beginTime}" id="off_startTime" hidden>
                                                    <input type="text" value="${time.endTime}" id="off_EndTime" hidden>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <!-- Room of Offering -->
                                    <div class="genContentCols">
                                        <c:choose>
                                            <c:when test="${empty offering.daysSet}">
                                                Unassigned
                                                <input type="text" value="Unassigned" id="off_room" hidden>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach items="${offering.daysSet}" var="rooms" begin="0" end="0">
                                                    <c:choose>
                                                        <c:when test="${rooms.room.roomCode == 'No Room'}">
                                                            Unassigned
                                                            <input type="text" value="Unassigned" id="off_room" hidden>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:out value="${rooms.room.roomCode}" />
                                                            <input type="text" value="${rooms.room.roomCode}" id="off_room" hidden>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <!-- Faculty of Offering -->
                                    <div class="genContentCols">
                                        <c:choose>
                                            <c:when test="${offering.faculty.userId  == 11111111 || empty offering.faculty.userId}">
                                                Unassigned
                                                <input type="text" value="Unassigned" id="off_faculty" hidden>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${offering.faculty.firstName} ${offering.faculty.lastName}"/>
                                                <input type="text" value="${offering.faculty.lastName}, ${offering.faculty.firstName}" id="off_faculty" hidden>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <!-- Other Information -->
                                    <input type="text" id='off_status' value="${offering.status}" hidden>
                                    <input type="text" id="off_id" value="${offering.offeringId}" hidden>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </form:form>
        </div>

        <!-- Modify Sidebar for Chair -->
        <%@ include file="rightChair.jsp" %>

        <!-- Modal for Assigning Faculty -->
        <div class="divModals" id="modalAssignFaculty">
            <table class="modal_header">
                <tr>
                    <!--<th>Degree Program</th>-->
                    <!--<th>Batch</th>-->
                    <!--<th>Academic Year</th>-->
                    <!--<th>Term</th>-->
                    <%-- <th>Search</th>
                     <th>Recommendation</th>
                     <th>Sort By</th>--%>
                 </tr>
                 <tr>
                     <%--<td><input class = 'modal_search' id='modal_input_search_faculty'><button id='button_search_faculty'><i class='fas fa-search'></i></button></td>
                     <td><select class = 'modal_select' id='select_recommend'></select></td>
                     <td><select class = 'modal_select' id='select_sort'></select></td>
                     <td><select class = 'modal_select' id='select_degree'>
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

        <!-- Modal for Concerns -->
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

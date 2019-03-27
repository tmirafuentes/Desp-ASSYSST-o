<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 03-Feb-19
  Time: 3:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        <!-- Filter Sidebar for CVC - Faculty Load -->
        <%@ include file="leftFacultyLoad.jsp" %>

        <!-- General Header for ASSYSTX -->
        <%@ include file="../user/header.jsp" %>

        <!-- Collaborative Workspace for ASSYSTX -->
        <div class="collabWorkspace cwFacultyLoad">
                        <div class="generatedContent">
                            <div class="genContentRows">
                                <div class="genContentCols">Name</div>
                                <div class="genContentCols">Teaching Load</div>
                                <div class="genContentCols">Admin Load</div>
                                <div class="genContentCols">Research Load</div>
                                <div class="genContentCols">Total Load</div>
                                <div class="genContentCols">Deload</div>
                            </div>
                        </div>

        </div>

        <%-- View Faculty Information
        <%@ include file="rightFacultyLoad.jsp" %>--%>
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

        <!-- Modal for new Faculty -->
        <div class="divModals" id="modalNewFaculty">
            <table id = "table_add_faculty">
                <tr>
                    <td>Faculty Last Name </td>
                    <td><input type = "text" id = "add_faculty_last_name"> </td>
                </tr>
                <tr>
                    <td>Faculty First Name </td>
                    <td><input type = "text" id = "add_faculty_first_name"> </td>
                </tr>
                <tr>
                    <td>Department </td>
                    <td><select id = "add_faculty_departments">
                        <c:forEach items="${allDepartments}" var="department">
                            <option value="${department}">
                                <c:out value="${department}" />
                            </option>
                        </c:forEach>
                    </select></td>
                </tr>
                <tr>
                    <td>Faculty Type </td>
                    <td><select id = "add_faculty_type">
                        <option value = "full">Full</option>
                        <option value = "part_time">Part-Time</option>
                    </select></td>
                </tr>
                <tr>
                    <td><button id="new_faculty_submit" type="submit">Submit</button></td>
                    <td><button id="new_faculty_cancel">Cancel</button></td>
                </tr>
            </table>
        </div>
        <!-- Create new course modal -->
        <!-- Modal for Adding a New Course -->
        <div class="divModals" id="modalNewCourse">
            <table id = "table_add_course">
                <tr>
                    <td>Course Name : </td>
                    <td><input type="text" id = "add_course_name"></td>
                </tr>
                <tr>
                    <td>Course Code : </td>
                    <td><input type="text" id = "add_course_code"></td>
                </tr>
                <tr>
                    <td>Department : </td>
                    <td><select id = "add_course_department">
                        <c:forEach items="${allDepartments2}" var="department">
                            <option value="${department}">
                                <c:out value="${department}" />
                            </option>
                        </c:forEach>
                    </select></td>
                </tr>
                <tr>
                    <td>Units : </td>
                    <td><input id = "add_course_units" type="number" min="0">
                    </td>
                </tr>
                <tr>
                    <td><button id="new_course_submit" type="submit">Submit</button></td>
                    <td><button id="new_course_cancel">Cancel</button></td>
                </tr>
            </table>
        </div>
        <div class = "divModals" id = "modalDeloading">

            <table id = "table_deload">
                <tr>
                    <td id = "deload_name"></td>
                </tr>
                <tr>
                    <td id = "deload_totalLoad"></td>
                </tr>
                <tr>
                    <td>Deloaded Units: <input type="number" min="0" id="deload_units" disabled> </td>
                </tr>
                <tr>
                    <td>Deload Type: <select id="deload_type">
                        <option value="0">-</option>
                        <c:forEach items="${allDeloading}" var="generatedDeloading">
                            <option value="${generatedDeloading.units}">
                                <c:out value="${generatedDeloading.deloadCode}" />
                            </option>
                        </c:forEach>
                    </select>
                        <input id="deload_code_holder" hidden>
                    </td>
                </tr>
                <tr>
                    <td><button id="submit_deload">Confirm</button></td>
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
                </div>
            </div>
        </div>

    </body>
</html>
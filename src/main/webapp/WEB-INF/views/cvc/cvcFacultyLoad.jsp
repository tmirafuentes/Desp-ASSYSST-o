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
        <c:url value="/scripts/assystxFilterScript.js" var="filterScript" />

        <link rel="stylesheet" type="text/css" href="${mainCss}">
        <link rel="stylesheet" type="text/css" href="${jqueryCss}">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
        <script src="${minJquery}"></script>
        <script src="${uiJquery}"></script>
        <script src="${mainScript}"></script>
        <script src="${filterScript}"></script>
    </head>
    <body>
        <!-- Filter Sidebar for CVC - Faculty Load -->
        <%@ include file="leftFacultyLoad.jsp" %>

        <!-- General Header for ASSYSTX -->
        <%@ include file="../user/header.jsp" %>

        <!-- Collaborative Workspace for ASSYSTX -->
        <div class="collabWorkspace cwFacultyLoad">
            <form:form method="get">
                <c:choose>
                    <c:when test="${empty allFacultyLoad}">
                        No faculty load to display.
                    </c:when>
                    <c:otherwise>
                        <div class="generatedContent">
                            <div class="genContentRows">
                                <div class="genContentCols">Name</div>
                                <div class="genContentCols">Teaching Load</div>
                                <div class="genContentCols">Admin Load</div>
                                <div class="genContentCols">Research Load</div>
                                <div class="genContentCols">Total Load</div>
                            </div>
                            <c:forEach items="${allFacultyLoad}" var="facLoad">
                                <div class="genContentRows">
                                    <!-- Faculty Name -->
                                    <div class="genContentCols" name="facultyName">
                                        <c:out value="${facLoad.faculty.lastName}" />, <c:out value="${facLoad.faculty.firstName}" />
                                        <input type="text" id='faculty_Lname' value="${facLoad.faculty.lastName}" hidden>
                                        <input type="text" id='faculty_Fname' value="${facLoad.faculty.firstName}" hidden>
                                        <input type="text" id='faculty_department' value="${facLoad.department.deptName}" hidden>
                                        <input type="text" id='faculty_id' value="${facLoad.faculty.userId}" hidden>
                                        <input type="text" id='db_deload_id' value="${facLoad.loadId}" hidden>
                                        <input type="text" id='total_load' value="${facLoad.totalLoad}" hidden>
                                    </div>
                                    <!-- Teaching Load -->
                                    <div class="genContentCols" name="teachLoad">
                                        <c:out value="${facLoad.teachingLoad}" />
                                    </div>
                                    <!-- Admin Load -->
                                    <div class="genContentCols" name="adminLoad">
                                        <c:out value="${facLoad.adminLoad}" />
                                    </div>
                                    <!-- Research Load -->
                                    <div class="genContentCols" name="resLoad">
                                        <c:out value="${facLoad.researchLoad}" />
                                    </div>
                                    <!-- Total Load -->
                                    <div class="genContentCols" name="totalLoad">
                                        <c:out value="${facLoad.totalLoad}" />
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </form:form>
        </div>

        <!-- View Faculty Information -->
        <%@ include file="rightFacultyLoad.jsp" %>

        <!-- Modal for Deloading Faculty -->
        <div class="divModals" id="modalDeloadFaculty">
            <form:form method="POST" modelAttribute="facultyDeloadForm" action="/cvc/deload-faculty">
                <spring:bind path="loadId">
                    <form:input path="loadId" type="text" hidden="true" id="modal_deload_id"/>
                </spring:bind>
                <spring:bind path="deloadType">
                    <form:input path="deloadType" type="text" hidden="true" id="modal_deload_code" />
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
                            <spring:bind path="deloadCode">
                                <form:select path="deloadCode" id = "select_deload">
                                    <form:option value="">Select Code</form:option>
                                    <c:forEach items="${allDeloading}" var="deloadCode">
                                        <form:option value="${deloadCode.deloading.units}"><c:set var="delCode" value="${deloadCode.deloading.deloadCode}" /><c:out value="${delCode}" /></form:option>
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
                            <button class="modal_buttons" id="deload_confirm" type="submit">Confirm</button>
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
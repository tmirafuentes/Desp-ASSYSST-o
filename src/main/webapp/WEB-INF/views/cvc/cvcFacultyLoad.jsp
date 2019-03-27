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
                            </div>
                        </div>

        </div>

        <!-- View Faculty Information -->
        <%@ include file="rightFacultyLoad.jsp" %>
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
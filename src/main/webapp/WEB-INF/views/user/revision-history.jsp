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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <c:url value="/scripts/assystxDesignScript.js" var="designScript" />
        <link rel="stylesheet" type="text/css" href="${mainCss}">
        <link rel="stylesheet" type="text/css" href="${jqueryCss}">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
        <script src="${minJquery}"></script>
        <script src="${uiJquery}"></script>
        <script src="${mainScript}"></script>
        <script src="${ajaxScript}"></script>
        <script src="${designScript}"></script>
    </head>
    <body>
        <!-- Filter Sidebar-->
        <c:choose>
            <c:when test="${userType == 'cvc'}">
                <%@ include file="../cvc/leftChair.jsp" %>
            </c:when>
            <c:when test="${userType == 'apo'}">
                <%@ include file="../apo/leftAPO.jsp" %>
            </c:when>
        </c:choose>

        <!-- Revision History Header for ASSYSTX -->
        <%@ include file="revision-history-header.jsp" %>

        <!-- Revision History Workspace for ASSYSTX -->
        <div class="collabWorkspace">
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
        </div>

        <!-- Revision History Menu for ASSYSTX -->
        <div class="rightSidebar">
            <div id = "revisionWrapper" style="overflow: auto;">
                <c:choose>
                    <c:when test="${empty revHistory}">
                        No revisions yet.
                    </c:when>
                    <c:otherwise>
                        <c:set var="basisDate" value="" scope="session" />
                        <c:set var="prevTime" value="" scope="session" />
                        <c:forEach items="${revHistory}" var="revisions">
                            <!-- Check Date to group same days -->
                            <c:set var="itDate" value="${revisions.timestamp}" />

                            <fmt:formatDate type="date" dateStyle="LONG" value="${basisDate}" var="fmtBasisDate" />
                            <fmt:formatDate type="date" dateStyle="LONG" value="${itDate}" var="fmtItDate" />

                            <!-- If BasisDate is empty (start of iteration ) or different itDate and basisDate -->
                            <c:if test="${empty basisDate || fmtBasisDate ne fmtItDate}">
                                <c:set var="basisDate" value="${itDate}"/>
                                <div class = "date_specified">
                                    <p class = "p_date">
                                        <c:out value="${fmtItDate}" />
                                    </p>
                                </div>
                                <c:set var="prevTime" value="" />
                            </c:if>

                            <!-- Check Time to group same modifications -->
                            <fmt:formatDate type="date" dateStyle="LONG" value="${basisDate}" var="fmtBasisDate" />
                            <fmt:formatDate type="time" timeStyle="short" value="${itDate}" var="fmtItTime" />

                            <!-- Remove duplicate revisions -->
                            <c:if test="${fmtBasisDate eq fmtItDate}">
                                <c:if test="${prevTime ne fmtItTime}">
                                    <!-- Add within the day -->
                                    <div class ="revision_holder">
                                        <table class="revision_entry">
                                            <tr>
                                                <td class = "revision_person">
                                                    <c:out value="${revisions.fullname}" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class = "revision_position">
                                                    <c:out value="${revisions.position}" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class = "revision_time">
                                                    <fmt:formatDate value="${itDate}" type="TIME" timeStyle="short" var="fmtItTime" />
                                                    <c:out value="${fmtItTime}" />
                                                </td>
                                            </tr>
                                        </table>
                                        <input type="hidden" value="${revisions.revNumber}" class="rev-his-id">
                                    </div>
                                    <c:set value="${fmtItTime}" var="prevTime" />
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
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
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
        <link rel="stylesheet" type="text/css" href="${mainCss}">
        <link rel="stylesheet" type="text/css" href="${jqueryCss}">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
        <script src="${minJquery}"></script>
        <script src="${uiJquery}"></script>
        <script src="${mainScript}"></script>
    </head>
    <body>
        <!-- Filter Sidebar for APO -->
        <%@ include file="../apo/leftAPO.jsp" %>

        <!-- Revision History Header for ASSYSTX -->
        <%@ include file="revision-history-header.jsp" %>

        <!-- Revision History Workspace for ASSYSTX -->
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

        <!-- Revision History Menu for ASSYSTX -->
        <div class="rightSidebar">
            <div id = "revisionWrapper">
                <div class = "date_specified">
                    <p class = "p_date">Today</p>
                </div>
                <c:choose>
                    <c:when test="${empty revHistory}">
                        No revisions yet.
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${revHistory}" var="revisions">
                            <div class ="revision_holder">
                            <table class="revision_entry">
                                <tr>
                                    <td class = "revision_person">${revisions.fullName}</td>
                                </tr>
                                <tr>
                                    <td class = "revision_position">ST Chair</td>
                                </tr>
                                <tr>
                                    <td class = "revision_time">${revisions.dateModified}</td>
                                </tr>
                            </table>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
                <div class = "date_specified">
                    <p class = "p_date">December 10, 2018</p>
                </div>
                <div class ="revision_holder">
                    <table class="revision_entry">
                        <tr>
                            <td class = "revision_person">Ryan Dimaunahan</td>
                        </tr>
                        <tr>
                            <td class = "revision_position">ST Chair</td>
                        </tr>
                        <tr>
                            <td class = "revision_time">4:19 PM</td>
                        </tr>
                    </table>
                </div>
                <div class ="revision_holder">
                    <table class="revision_entry">
                        <tr>
                            <td class = "revision_person">Ryan Dimaunahan</td>
                        </tr>
                        <tr>
                            <td class = "revision_position">ST Chair</td>
                        </tr>
                        <tr>
                            <td class = "revision_time">4:19 PM</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
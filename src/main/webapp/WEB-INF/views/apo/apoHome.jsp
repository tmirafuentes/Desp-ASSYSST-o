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
        <%@ include file="leftAPO.jsp" %>

        <!-- General Header for ASSYSTX -->
        <%@ include file="../user/header.jsp" %>

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
                                <div class="genContentRows">
                                    <!-- Course Code of Offering -->
                                    <div class="genContentCols" name="courseCode">
                                        <c:out value="${offering.course.courseCode}" />
                                    </div>
                                    <!-- Section of Offering -->
                                    <div class="genContentCols genList_section">
                                        <c:choose>
                                            <c:when test="${empty offering.section}">
                                                None
                                                <input type="text" id='off_section' value="None" hidden>
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${offering.section}" />
                                                <input type="text" id='off_section' value="${offering.section}" hidden>
                                                <input type="text" value="${offering.term}" id="off_term" hidden>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <!-- Days Held of Offering -->
                                    <div class="genContentCols" name="days">
                                        <c:choose>
                                            <c:when test="${empty offering.daysSet}">
                                                None
                                                <input type="text" value="-" id="off_day1" hidden>
                                                <input type="text" value="-" id="off_day2" hidden>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach items="${offering.daysSet}" var="days" varStatus="dCtr">
                                                    <c:out value="${days.classDay}" />
                                                    <input type="text" id='off_day${dCtr.count}' value="${days.classDay}" hidden>
                                                </c:forEach>
                                                <input type = "text" id='off_counter' value = "${dCtr}" hidden>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <!-- Time Slot of Offering -->
                                    <div class="genContentCols">
                                        <c:choose>
                                            <c:when test="${empty offering.daysSet}">
                                                Unassigned
                                                <input type="text" value="--:--" id="off_startTime" hidden>
                                                <input type="text" value="--:--" id="off_EndTime" hidden>
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
                                                            <input type="text" value="${rooms.room.roomType}" id="off_roomtype" hidden>
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

        <!-- Modify Sidebar for APO -->
        <%@ include file="../apo/rightAPO.jsp" %>

        <!-- Load all Modals for ASSYSTX -->
        <%@ include file="../user/modals.jsp" %>
    </body>
</html>

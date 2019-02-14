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
        <c:url value="/css/assystxStyle.css" var="mainCss" />
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
        <div id = "main_content">
            <form:form method="get">
                <c:choose>
                    <c:when test="${empty allOfferings}">
                        No offerings to display.
                    </c:when>
                    <c:otherwise>
                        <div id = "generated_list">
                            <div class="genList_rows">
                                <div class="genList_cols">Course</div>
                                <div class="genList_cols">Section</div>
                                <div class="genList_cols">Day</div>
                                <div class="genList_cols">Time</div>
                                <div class="genList_cols">Room</div>
                                <div class="genList_cols">Faculty</div>
                            </div>
                            <c:forEach items="${allOfferings}" var="offering">
                                <c:set var="currOffer" value="${offering.offeringId}" />
                                <c:set var="selOffer" value="${selOffering.offeringId}" />
                                <c:choose>
                                    <c:when test="${currOffer == selOffer}">
                                        <div class="genList_rows selectedOffering">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="genList_rows">
                                    </c:otherwise>
                                </c:choose>
                                    <!-- Course Code of Offering -->
                                    <div class="genList_cols" name="courseCode">
                                        <c:out value="${offering.course.courseCode}" />
                                    </div>
                                    <!-- Section of Offering -->
                                    <div class="genList_cols genList_section">
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
                                    <div class="genList_cols" name="days">
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
                                    <div class="genList_cols">
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
                                    <div class="genList_cols">
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
                                    <div class="genList_cols">
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

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
    <c:url value="/scripts/assystxAPOScript.js" var="mainScript" />

    <link rel="stylesheet" type="text/css" href="${mainCss}">
    <link rel="stylesheet" type="text/css" href="${jqueryCss}">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
    <script src="${minJquery}"></script>
    <script src="${uiJquery}"></script>
    <script src="${mainScript}"></script>
</head>
<body>
<%@ include file="../leftAPO.jsp" %>
<%@ include file="../header.jsp" %>
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
                            <c:choose>
                                <c:when test="${currOffer == selOffer}">
                                    <a href="/apo/home"><span class="offLink"></span></a>
                                </c:when>
                                <c:otherwise>
                                    <a href="/apo/view?v=${offering.offeringId}"><span class="offLink"></span></a>
                                </c:otherwise>
                            </c:choose>
                            <div class="genList_cols" name="courseCode"><c:out value="${offering.course.courseCode}" /></div>
                            <div class="genList_cols" name="section"><c:out value="${offering.section}" /></div>
                            <div class="genList_cols" name="days">
                                <c:forEach items="${offering.daysSet}" var="days">
                                    <c:out value="${days.classDay}" />
                                </c:forEach>
                            </div>
                            <div class="genList_cols">
                                <c:forEach items="${offering.daysSet}" var="time" begin="0" end="0">
                                    <c:out value="${time.beginTime}" /> - <c:out value="${time.endTime}" />
                                </c:forEach>
                            </div>
                            <div class="genList_cols">
                                <c:forEach items="${offering.daysSet}" var="rooms" begin="0" end="0">
                                    <c:out value="${rooms.room.roomCode}" />
                                </c:forEach>
                            </div>
                            <div class="genList_cols">
                                <c:choose>
                                    <c:when test="${empty offering.faculty}">
                                        None
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${offering.faculty.firstName} ${offering.faculty.lastName}"/>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </form:form>
</div>
<%@ include file="../rightAPOChair.jsp" %>
</body>

</html>

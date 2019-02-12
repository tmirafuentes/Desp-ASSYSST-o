<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 03-Feb-19
  Time: 2:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
<%@ include file="leftChair.jsp" %>
<%@ include file="../user/header.jsp" %>
<div id = "main_content">
    <form:form method="get">
        <c:choose>
            <c:when test="${empty allOfferings}">
                No offerings to display.
            </c:when>
            <c:otherwise>
                <table id = "generated_list">
                    <tr>
                        <th>Course</th>
                        <th>Section</th>
                        <th>Day</th>
                        <th>Time</th>
                        <th>Room</th>
                        <th>Faculty</th>
                    </tr>
                    <c:forEach items="${allOfferings}" var="offering">
                        <tr>
                            <td name="courseCode"><c:out value="${offering.course.courseCode}" /></td>
                            <td name="section"><c:out value="${offering.section}" /></td>
                            <td name="days">
                                <c:forEach items="${offering.daysSet}" var="days">
                                    <c:out value="${days.classDay}" />
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach items="${offering.daysSet}" var="time" begin="0" end="0">
                                    <c:out value="${time.beginTime}" /> - <c:out value="${time.endTime}" />
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach items="${offering.daysSet}" var="rooms" begin="0" end="0">
                                    <c:out value="${rooms.room.roomCode}" />
                                </c:forEach>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${empty offering.faculty}">
                                        None
                                    </c:when>
                                    <c:otherwise>
                                        <c:out value="${offering.faculty.firstName} ${offering.faculty.lastName}"/>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </form:form>
</div>
<%@ include file="rightDeloading.jsp" %>
</body>

</html>
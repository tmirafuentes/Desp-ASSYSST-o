<%--
  Created by IntelliJ IDEA.
  User: Troy Mirafuentes
  Date: 2/1/2019
  Time: 12:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>APO Home</title>
    <c:url value="/css/assystxAPOStyle.css" var="mainCss" />
    <c:url value="/css/jquery/jquery-ui.css" var="jqueryCss" />
    <c:url value="/scripts/jquery/jquery-3.3.1.min.js" var="jqueryMin" />
    <c:url value="/scripts/jquery/jquery-ui.js" var="jqueryUI" />
    <c:url value="/scripts/assystxAPOScript.js" var="mainJs" />

    <link rel="stylesheet" type="text/css" href="${mainCss}">
    <link rel="stylesheet" type="text/css" href="${jqueryCss}">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
    <script src="${jqueryMin}"></script>
    <script src="${jqueryUI}"></script>
    <script src="${mainJs}"></script>
</head>
<body>
<div id = "left_side">
    <!-- Left Filters -->
    <%@ include file="../leftFilters.jsp"%>

    <!-- Menu -->
    <div id = "left_button_holder">
        <button class = left_buttons id="button_add_offering"> Add New Offering </button>
        <button class = left_buttons id ="button_export_schedule"> Export Schedule </button>
        <button class = left_buttons id ="button_concerns"> Concerns </button>
    </div>
</div>

<!-- Header -->
<%@ include file="../header.jsp" %>

<!-- Live Editing Partition -->
<%@ include file="../liveEditing.jsp" %>

<!-- Course Offerings Shared Workspace -->
<div id = "main_content">
    <form:form method="get">
        <c:choose>
            <c:when test="${empty allOfferings}">
                No products to display.
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

<!-- Edit Offering Sidebar -->
<%@ include file="../rightEdit.jsp" %>

</body>
</html>

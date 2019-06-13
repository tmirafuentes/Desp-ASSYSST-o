<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<%--
  Created by IntelliJ IDEA.
  User: Troy Mirafuentes
  Date: 6/10/2019
  Time: 7:18 PM
  To change this template use File | Settings | File Templates.
--%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<html>
    <head>
        <title>ASSYSTX - Workspace History</title>

        <meta name="_csrf" content="${_csrf.token}"/>
        <!-- default header name is X-CSRF-TOKEN -->
        <meta name="_csrf_header" content="${_csrf.headerName}"/>

        <!-- Variables for Stylesheets and Scripts -->
        <c:url value="/css/jquery/jquery-ui.min.css" var="jqueryCss" />
        <c:url value="/css/assystx2-styles/history-page-style.css" var="mainCss" />
        <c:url value="/scripts/jquery/jquery-3.3.1.min.js" var="minJquery" />
        <c:url value="/scripts/jquery/jquery-ui.min.js" var="jqueryUI" />
        <c:url value="/scripts/assystx2-scripts/assystx2-workspace-script.js" var="mainScript" />

        <link rel="stylesheet" type="text/css" href="${jqueryCss}">
        <link rel="stylesheet" type="text/css" href="${mainCss}">
    </head>
    <body>
        <div id="assystx-container">
            <!-- Header -->
            <%@include file="header-screen.jsp"%>

            <!-- Left Partition -->
            <section id="workspace-menu-filters">
                <!-- ASSYSTX Menu -->
                <%@include file="../general-screens/assystx-menu-screen.jsp" %>
            </section>

            <!-- Middle Partition -->
            <section id="workspace-history">
                <section id="workspace-history-container">
                    <div id="workspace-history-box">

                    </div>
                </section>
            </section>

            <!-- Right Partition -->
            <section id="collab-sidebar">
                <!-- Recent Changes -->
                <%@include file="../general-screens/recent-changes-screen.jsp" %>
            </section>
        </div>
    </body>
</html>

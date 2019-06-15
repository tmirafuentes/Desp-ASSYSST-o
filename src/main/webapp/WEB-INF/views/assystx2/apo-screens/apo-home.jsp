<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
    <head>
        <title>ASSYSTX - APO Workspace</title>

        <meta name="_csrf" content="${_csrf.token}"/>
        <!-- default header name is X-CSRF-TOKEN -->
        <meta name="_csrf_header" content="${_csrf.headerName}"/>

        <!-- Variables for Stylesheets and Scripts -->
        <c:url value="/css/jquery/jquery-ui.min.css" var="jqueryCss" />
        <c:url value="/css/assystx2-styles/component-styles/shorten.min.css" var="shortenCss" />
        <c:url value="/css/assystx2-styles/workspace-home-style.css" var="mainCss" />
        <c:url value="/scripts/jquery/jquery-3.3.1.min.js" var="minJquery" />
        <c:url value="/scripts/jquery/jquery-ui.min.js" var="jqueryUI" />
        <c:url value="/scripts/assystx2-scripts/assystx2-workspace-script.js" var="mainScript" />
        <c:url value="/scripts/assystx2-scripts/assystx2-header-user-script.js" var="userScript" />
        <c:url value="/scripts/assystx2-scripts/jquery-shorten.min.js" var="shortenExt" />

        <link rel="stylesheet" type="text/css" href="${jqueryCss}">
        <link rel="stylesheet" type="text/css" href="${shortenCss}">
        <link rel="stylesheet" type="text/css" href="${mainCss}">
    </head>
    <body>
        <div id="assystx-container">
            <!-- Header -->
            <%@include file="../general-screens/header-screen.jsp"%>

            <!-- Left Partition -->
            <section id="workspace-menu">
                <!-- ASSYSTX Menu -->
                <%@include file="../general-screens/assystx-menu-screen.jsp" %>
            </section>

            <!-- Middle Partition -->
            <section id="collab-workspace">
                <!-- Add Offering -->
                <%@include file="add-offering-screen.jsp" %>

                <!-- Filter Offerings -->
                <%@include file="../general-screens/offering-filters-screen.jsp" %>

                <!-- All Offerings -->
                <%@include file="../general-screens/all-offerings-screen.jsp" %>
            </section>

            <!-- Right Partition -->
            <section id="collab-sidebar">
                <!-- Online Users -->
                <%@include file="../general-screens/online-users-screen.jsp" %>

                <!-- Recent Changes -->
                <%@include file="../general-screens/recent-changes-screen.jsp" %>
            </section>
        </div>

        <!-- Feedback Messages -->
        <%@include file="../general-screens/feedback-message-screen.jsp" %>

        <script src="${minJquery}"></script>
        <script src="${jqueryUI}"></script>
        <script src="${shortenExt}"></script>
        <script src="${userScript}"></script>
        <script src="${mainScript}"></script>
    </body>
</html>
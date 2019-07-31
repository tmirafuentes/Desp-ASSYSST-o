<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
    <head>
        <title>ASSYSTX - CVC Workspace</title>

        <meta name="_csrf" content="${_csrf.token}"/>
        <!-- default header name is X-CSRF-TOKEN -->
        <meta name="_csrf_header" content="${_csrf.headerName}"/>

        <!-- Variables for Stylesheets and Scripts -->
        <c:url value="/css/jquery/jquery-ui.min.css" var="jqueryCss" />
        <c:url value="/css/assystx2-styles/component-styles/shorten.min.css" var="shortenCss" />
        <c:url value="/css/assystx2-styles/component-styles/datatables.css" var="dataTablesCss" />
        <c:url value="/css/assystx2-styles/workspace-home-style.css" var="mainCss" />
        <c:url value="/scripts/extension-scripts/jquery/jquery-3.3.1.min.js" var="minJquery" />
        <c:url value="/scripts/extension-scripts/jquery/jquery-ui.min.js" var="jqueryUI" />
        <c:url value="/scripts/extension-scripts/jquery-shorten.min.js" var="shortenExt" />
        <c:url value="/scripts/main-scripts/cvc-workspace-script.js" var="mainScript" />
        <c:url value="/scripts/main-scripts/header-user-script.js" var="userScript" />
        <c:url value="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js" var="dataTablesExt" />
        <c:url value="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js" var="modalScriptExt" />
        <c:url value="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" var="modalCssExt" />

        <link rel="stylesheet" type="text/css" href="${jqueryCss}">
        <link rel="stylesheet" type="text/css" href="${shortenCss}">
        <link rel="stylesheet" type="text/css" href="${modalCssExt}">
        <link rel="stylesheet" type="text/css" href="${mainCss}">
        <link rel="stylesheet" type="text/css" href="${dataTablesCss}">
    </head>
    <body>
        <div id="assystx-container">
            <!-- Header -->
            <%@include file="screens/main-header-screen.jsp"%>

            <!-- Middle Partition -->
            <section id="collab-workspace">
                <!-- All Offerings -->
                <%@include file="screens/all-offerings-screen.jsp" %>
            </section>

            <!-- Right Partition -->
            <section id="collab-sidebar">
                <!-- Recent Changes -->
                <%@include file="screens/recent-changes-screen.jsp" %>
            </section>
        </div>

        <!-- Feedback Messages -->
        <%@include file="screens/feedback-message-screen.jsp" %>

        <!-- Modals -->
        <%@include file="screens/modals-screen.jsp" %>

        <script src="${minJquery}"></script>
        <script src="${jqueryUI}"></script>
        <script src="${shortenExt}"></script>
        <script src="${modalScriptExt}"></script>
        <script src="${dataTablesExt}"></script>
        <script src="${userScript}"></script>
        <script src="${mainScript}"></script>
    </body>
</html>
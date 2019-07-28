<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Troy Mirafuentes
  Date: 6/21/2019
  Time: 11:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>ASSYSTX - Course Profiles</title>

        <meta name="_csrf" content="${_csrf.token}"/>
        <!-- default header name is X-CSRF-TOKEN -->
        <meta name="_csrf_header" content="${_csrf.headerName}"/>

        <!-- Variables for Stylesheets and Scripts -->
        <c:url value="/css/jquery/jquery-ui.min.css" var="jqueryCss" />
        <c:url value="/css/assystx2-styles/component-styles/shorten.min.css" var="shortenCss" />
        <c:url value="/css/assystx2-styles/component-styles/datatables.css" var="dataTablesCss" />
        <c:url value="/css/assystx2-styles/manage-course-style.css" var="mainCss" />
        <c:url value="/scripts/extension-scripts/jquery/jquery-3.3.1.min.js" var="minJquery" />
        <c:url value="/scripts/extension-scripts/jquery/jquery-ui.min.js" var="jqueryUI" />
        <c:url value="/scripts/extension-scripts/jquery-shorten.min.js" var="shortenExt" />
        <c:url value="/scripts/main-scripts/courses-page-script.js" var="mainScript" />
        <c:url value="/scripts/main-scripts/header-user-script.js" var="userScript" />
        <c:url value="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js" var="dataTablesExt" />
        <c:url value="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.js" var="modalScriptExt" />
        <c:url value="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.1/jquery.modal.min.css" var="modalCssExt" />

        <link rel="stylesheet" type="text/css" href="${jqueryCss}" />
        <link rel="stylesheet" type="text/css" href="${dataTablesCss}">
        <link rel="stylesheet" type="text/css" href="${shortenCss}">
        <link rel="stylesheet" type="text/css" href="${modalCssExt}">
        <link rel="stylesheet" type="text/css" href="${mainCss}">
    </head>
    <body>
        <div id="assystx-container">
            <!-- Header -->
            <%@ include file="screens/main-header-screen.jsp" %>

            <!-- Left Partition -->
            <section id="workspace-menu">
                <!-- ASSYSTX Menu -->
                <%@include file="screens/main-menu-screen.jsp" %>
            </section>

            <!-- Select Course Table -->
            <section id="course-profiles-list">

                <c:if test="${userType == 'CVC'}">
                    <!-- Add Course Container -->
                    <section id="add-course-container">
                        <div id="add-course-box">
                            <p class="section-header-text">Create New Course</p>
                            <hr class="section-header-border" />
                            <div id="add-course-form">
                                <input type="text" id="add-course-code" placeholder="Course Code" />
                                <input type="text" id="add-course-name" placeholder="Course Name" />
                                <input type="number" id="add-course-units" placeholder="Units" />
                                <textarea id="add-course-description" placeholder="Course Description"></textarea>
                                <button type="submit" id="add-course-submit" alt="Creates New Course">Save New Course</button>
                            </div>
                        </div>
                    </section>
                </c:if>

                <!-- Course Profiles List -->
                <section id="course-profiles-list-container">
                    <div id="course-profiles-list-box">
                        <!-- Course Profiles Table -->
                        <table id="course-list-table" class="hover row-border order-column">
                            <thead>
                                <tr>
                                    <th>Course Code</th>
                                    <th>Course Name</th>
                                    <th>Units</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>

                            </tbody>
                        </table>
                    </div>
                </section>
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

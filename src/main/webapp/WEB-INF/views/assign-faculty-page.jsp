<%--
  Created by IntelliJ IDEA.
  User: Troy Mirafuentes
  Date: 6/21/2019
  Time: 10:07 PM
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
        <title>ASSYSTX - Assign Faculty</title>

        <meta name="_csrf" content="${_csrf.token}"/>
        <!-- default header name is X-CSRF-TOKEN -->
        <meta name="_csrf_header" content="${_csrf.headerName}"/>

        <!-- Variables for Stylesheets and Scripts -->
        <c:url value="/css/jquery/jquery-ui.min.css" var="jqueryCss" />
        <c:url value="/css/assystx2-styles/assign-faculty-style.css" var="mainCss" />
        <c:url value="/css/assystx2-styles/component-styles/shorten.min.css" var="shortenCss" />
        <c:url value="/scripts/extension-scripts/jquery/jquery-3.3.1.min.js" var="minJquery" />
        <c:url value="/scripts/extension-scripts/jquery/jquery-ui.min.js" var="jqueryUI" />
        <c:url value="/scripts/main-scripts/assign-faculty-script.js" var="mainScript" />
        <c:url value="/scripts/main-scripts/header-user-script.js" var="userScript" />
        <c:url value="/scripts/extension-scripts/jquery-shorten.min.js" var="shortenExt" />

        <link rel="stylesheet" type="text/css" href="${jqueryCss}">
        <link rel="stylesheet" type="text/css" href="${shortenCss}">
        <link rel="stylesheet" type="text/css" href="${mainCss}">
    </head>
    <body>
        <div id="assystx-container">
            <!-- Header -->
            <%@ include file="screens/main-header-screen.jsp" %>

            <!-- Assign Faculty Dropdowns Menu -->
            <section id="assign-faculty-left-partition">
                <!-- Building and Room Menu -->
                <section id="assign-faculty-dropdowns-container" class="assign-dropdowns-container">
                    <div id="assign-faculty-dropdowns-box" class="assign-dropdowns-box">
                        <p class="section-header-text">Filter Faculty</p>
                        <hr class="section-header-border" />
                        <!-- Faculty Filter Menu -->
                        <select id="assign-faculty-filter-menu">
                            <option value="ALL" selected>All Available Faculty</option>
                            <option value="PREFERRED">Course is Preferred by Faculty</option>
                            <option value="PAST">Course is Previously Taught by Faculty</option>
                        </select>
                    </div>
                </section>
                <!-- Assigned Faculty Confirmation and Summary -->
                <section id="assign-faculty-confirm-container" class="assign-confirm-container">
                    <div id="assign-faculty-confirm-box" class="assign-confirm-box">
                        <p class="section-header-text">Assigned Faculty</p>
                        <hr class="section-header-border" />

                        <!-- Confirm Table -->
                        <table id="assign-faculty-confirm-table" class="assign-confirm-table">
                            <tr id="confirm-table-offering-row">
                                <td>Course Offering</td>
                                <td>${courseCode} ${section}</td>
                            </tr>
                            <c:if test="${not empty assignedFaculty}">
                                <tr id="confirm-table-assigned-row">
                                    <td>Current Faculty</td>
                                    <td>${assignedFaculty}</td>
                                </tr>
                            </c:if>
                            <tr>
                                <td colspan="2">
                                    <button id="assign-faculty-cancel" class="assign-confirm-btns assign-cancel-btn">Cancel</button>
                                    <button id="assign-faculty-reset" class="assign-confirm-btns assign-reset-btn">Reset</button>
                                    <button id="assign-faculty-submit" class="assign-confirm-btns assign-submit-btn">Assign Faculty</button>
                                </td>
                            </tr>
                        </table>
                    </div>
                </section>
            </section>

            <!-- Select Faculty Table -->
            <section id="assign-faculty-right-partition">
                <section id="assign-faculty-table-container">
                    <div id="assign-faculty-table-box">
                        <p class="section-header-text">Select Faculty</p>
                        <hr class="section-header-border" />

                        <!-- Faculty Assignment Table -->
                        <table id="faculty-assign-table">
                        </table>
                    </div>
                </section>
            </section>
        </div>

        <!-- Feedback Messages -->
        <%@include file="screens/feedback-message-screen.jsp" %>

        <script src="${minJquery}"></script>
        <script src="${jqueryUI}"></script>
        <script src="${shortenExt}"></script>
        <script src="${userScript}"></script>
        <script src="${mainScript}"></script>
    </body>
</html>

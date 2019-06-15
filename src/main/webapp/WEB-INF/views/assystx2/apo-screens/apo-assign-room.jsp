<%--
  Created by IntelliJ IDEA.
  User: Troy Mirafuentes
  Date: 6/2/2019
  Time: 12:40 AM
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
        <title>ASSYSTX - Assign Room</title>

        <meta name="_csrf" content="${_csrf.token}"/>
        <!-- default header name is X-CSRF-TOKEN -->
        <meta name="_csrf_header" content="${_csrf.headerName}"/>

        <!-- Variables for Stylesheets and Scripts -->
        <c:url value="/css/jquery/jquery-ui.min.css" var="jqueryCss" />
        <c:url value="/css/assystx2-styles/assign-room-style.css" var="mainCss" />
        <c:url value="/css/assystx2-styles/component-styles/shorten.min.css" var="shortenCss" />
        <c:url value="/scripts/jquery/jquery-3.3.1.min.js" var="minJquery" />
        <c:url value="/scripts/jquery/jquery-ui.min.js" var="jqueryUI" />
        <c:url value="/scripts/assystx2-scripts/assystx2-assign-room-script.js" var="mainScript" />
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

            <!-- Assign Room Dropdowns Menu -->
            <section id="assign-room-left-partition">
                <!-- Building and Room Menu -->
                <section id="assign-room-dropdowns-container" class="assign-dropdowns-container">
                    <div id="assign-room-dropdowns-box" class="assign-dropdowns-box">
                        <p class="section-header-text">Select Building and Room</p>
                        <hr class="section-header-border" />
                        <!-- Building Menu -->
                        <select id="assign-room-building-menu">
                            <option value="-" selected>Select building</option>
                        </select>
                        <!-- Room Menu -->
                        <select id="assign-room-room-menu">
                            <option value="-" selected>Select room</option>
                        </select>
                    </div>
                </section>
                <!-- Assigned Room Confirmation and Summary -->
                <section id="assign-room-confirm-container" class="assign-confirm-container">
                    <div id="assign-room-confirm-box" class="assign-confirm-box">
                        <p class="section-header-text">Assigned Room and Timeslot</p>
                        <hr class="section-header-border" />

                        <table id="assign-room-confirm-table" class="assign-confirm-table">
                            <tr id="confirm-table-offering-row">
                                <td>Course Offering</td>
                                <td>${courseCode} ${section}</td>
                            </tr>
                            <tr id="confirm-table-button-row">
                                <td colspan="2">
                                    <button id="assign-room-cancel" formaction="/assystx2/apo" class="assign-confirm-btns assign-cancel-btn">Cancel</button>
                                    <button id="assign-room-reset" type="reset" class="assign-confirm-btns assign-room-btn">Reset</button>
                                    <button id="assign-room-submit" type="submit" class="assign-confirm-btns assign-submit-btn">Assign Room</button>
                                </td>
                            </tr>
                        </table>
                    </div>
                </section>
            </section>

            <!-- Select Time Slots Table -->
            <section id="assign-room-right-partition">
                <section id="assign-room-table-container">
                    <div id="assign-room-table-box">
                        <p class="section-header-text">Select Day and Timeslot</p>
                        <hr class="section-header-border" id="assign-room-table-border" />
                    </div>
                </section>
            </section>
        </div>

        <script src="${minJquery}"></script>
        <script src="${jqueryUI}"></script>
        <script src="${shortenExt}"></script>
        <script src="${userScript}"></script>
        <script src="${mainScript}"></script>
    </body>
</html>

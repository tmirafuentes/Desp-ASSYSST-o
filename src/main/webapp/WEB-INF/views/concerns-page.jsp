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

<c:set var="contextPath" value="${request.getContextPath()}" />
<html>
    <head>
        <title>ASSYSTX - Concerns</title>

        <link rel="icon" type="image/png" href="/images/other-icons/favicon-32x32.png" sizes="32x32" />
        <link rel="icon" type="image/png" href="/images/other-icons/favicon-16x16.png" sizes="16x16" />

        <meta name="_csrf" content="${_csrf.token}"/>
        <!-- default header name is X-CSRF-TOKEN -->
        <meta name="_csrf_header" content="${_csrf.headerName}"/>

        <!-- Variables for Stylesheets and Scripts -->
        <c:url value="/css/jquery/jquery-ui.min.css" var="jqueryCss" />
        <c:url value="/css/assystx2-styles/component-styles/shorten.min.css" var="shortenCss" />
        <c:url value="/css/assystx2-styles/concerns-page-style.css" var="mainCss" />
        <c:url value="/scripts/extension-scripts/jquery/jquery-3.3.1.min.js" var="minJquery" />
        <c:url value="/scripts/extension-scripts/jquery/jquery-ui.min.js" var="jqueryUI" />
        <c:url value="/scripts/extension-scripts/jquery-shorten.min.js" var="shortenExt" />
        <c:url value="/scripts/main-scripts/header-user-script.js" var="userScript" />
        <c:url value="/scripts/main-scripts/concerns-page-script.js" var="mainScript" />

        <link rel="stylesheet" type="text/css" href="${jqueryCss}">
        <link rel="stylesheet" type="text/css" href="${shortenCss}">
        <link rel="stylesheet" type="text/css" href="${mainCss}">
    </head>
    <body>
        <div id="assystx-concerns-container">
            <!-- Header -->
            <%@include file="screens/main-header-screen.jsp"%>

            <!-- Left Partition -->
            <section id="concerns-menu">
                <!-- Menu Container -->
                <section id="menu-container">
                    <div id="menu-box">
                        <p class="section-header-text" id="menu-current-term">Concerns</p>
                        <!-- Main Menu -->
                        <ul class="menu-subset-list" id="menu-list-main">
                            <a href="#inbox" id="concerns-filter-inbox"><li class="menu-selected-page">Inbox</li></a>
                            <a href="#sent" id="concerns-filter-sent"><li>Sent</li></a>
                        </ul>
                    </div>
                </section>
            </section>

            <!-- Middle Partition -->
            <section id="concerns-inbox">
                <section id="concerns-inbox-container">
                    <div id="concerns-inbox-box">
                        <!-- Concerns Table -->
                        <div id="concerns-list-accordion">
                        </div>
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

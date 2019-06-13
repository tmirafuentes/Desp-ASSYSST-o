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

        <meta name="_csrf" content="${_csrf.token}"/>
        <!-- default header name is X-CSRF-TOKEN -->
        <meta name="_csrf_header" content="${_csrf.headerName}"/>

        <!-- Variables for Stylesheets and Scripts -->
        <c:url value="/css/jquery/jquery-ui.min.css" var="jqueryCss" />
        <c:url value="/css/assystx2-styles/concerns-page-style.css" var="mainCss" />
        <c:url value="/scripts/jquery/jquery-3.3.1.min.js" var="minJquery" />
        <c:url value="/scripts/jquery/jquery-ui.min.js" var="jqueryUI" />
        <c:url value="/scripts/assystx2-scripts/assystx2-workspace-script.js" var="mainScript" />

        <link rel="stylesheet" type="text/css" href="${jqueryCss}">
        <link rel="stylesheet" type="text/css" href="${mainCss}">
    </head>
    <body>
        <div id="assystx-container">
            <!-- Header -->
            <%@include file="../general-screens/header-screen.jsp"%>

            <!-- Left Partition -->
            <section id="workspace-menu-filters">
                <!-- ASSYSTX Menu -->
                <%@include file="../general-screens/assystx-menu-screen.jsp" %>
            </section>

            <!-- Middle Partition -->
            <section id="concerns-inbox">
                <section id="concerns-inbox-container">
                    <div id="concerns-inbox-box">
                        <p class="section-header-text">Concerns</p>
                        <hr class="section-header-border" />

                        <select id="concerns-inbox-filter-dropdown">
                            <option value="ALL">All Concerns</option>
                            <option value="RECEIVED">Received</option>
                            <option value="SENT">Sent</option>
                        </select>
                        <!-- Concerns Table -->
                        <table id="concerns-table">
                            <tr>
                                <td>
                                    <img class="concerns-checked-button" src="/images/check-green.png" alt="Mark as acknowledged" />
                                    <h3 class="concerns-course-header">CCPROG1 S12</h3>
                                    <p class="concerns-sender-details">Sent by Hazel Ventura at 12:08 PM</p>
                                    <p class="concerns-sender-body">Hello ST Chair, kailangan ko pa po ba gumawa ng course offerings for CCPROG1? Mauubusan na po kasi ng slots sa room. This is a filler sentence so that I can see the height.</p>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <img class="concerns-checked-button" src="/images/check-green.png" alt="Mark as acknowledged" />
                                    <h3 class="concerns-course-header">CCPROG1 S12</h3>
                                    <p class="concerns-sender-details">Sent by Hazel Ventura at 12:08 PM</p>
                                    <p class="concerns-sender-body">Hello ST Chair, kailangan ko pa po ba gumawa ng course offerings for CCPROG1? Mauubusan na po kasi ng slots sa room. This is a filler sentence so that I can see the height.</p>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <img class="concerns-checked-button" src="/images/check-green.png" alt="Mark as acknowledged" />
                                    <h3 class="concerns-course-header">CCPROG1 S12</h3>
                                    <p class="concerns-sender-details">Sent by Hazel Ventura at 12:08 PM</p>
                                    <p class="concerns-sender-body">Hello ST Chair, kailangan ko pa po ba gumawa ng course offerings for CCPROG1? Mauubusan na po kasi ng slots sa room. This is a filler sentence so that I can see the height.</p>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <img class="concerns-checked-button" src="/images/check-green.png" alt="Mark as acknowledged" />
                                    <h3 class="concerns-course-header">CCPROG1 S12</h3>
                                    <p class="concerns-sender-details">Sent by Hazel Ventura at 12:08 PM</p>
                                    <p class="concerns-sender-body">Hello ST Chair, kailangan ko pa po ba gumawa ng course offerings for CCPROG1? Mauubusan na po kasi ng slots sa room. This is a filler sentence so that I can see the height.</p>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <img class="concerns-checked-button" src="/images/check-green.png" alt="Mark as acknowledged" />
                                    <h3 class="concerns-course-header">CCPROG1 S12</h3>
                                    <p class="concerns-sender-details">Sent by Hazel Ventura at 12:08 PM</p>
                                    <p class="concerns-sender-body">Hello ST Chair, kailangan ko pa po ba gumawa ng course offerings for CCPROG1? Mauubusan na po kasi ng slots sa room. This is a filler sentence so that I can see the height.</p>
                                </td>
                            </tr>
                        </table>
                    </div>
                </section>
            </section>
        </div>
    </body>
</html>

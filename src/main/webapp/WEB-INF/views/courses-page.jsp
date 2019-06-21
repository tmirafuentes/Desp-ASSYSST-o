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

        <link rel="stylesheet" type="text/css" href="/css/assystx2-styles/manage-course-style.css" />
        <link rel="stylesheet" type="text/css" href="/css/jquery/jquery-ui.css" />
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
                        <p class="section-header-text">Course List</p>
                        <hr class="section-header-border" />

                        <!-- Course Profiles Accordion -->
                        <ul id="course-list-header">
                            <li>Code</li>
                            <li>Name</li>
                            <li>Units</li>
                            <li>
                                <select id="course-list-table-filter">
                                    <option value="All">All Courses</option>
                                    <option value="ST" selected>ST Department</option>
                                    <option value="CT">CT Department</option>
                                    <option value="IT">IT Department</option>
                                </select>
                            </li>
                        </ul>
                        <hr id="course-list-header-border" />
                        <div id="course-list-accordion">
                            <h3>
                                    <span class="course-list-code">
                                        COMPRO1
                                    </span>
                                <span class="course-list-title">
                                        Basic C Progamming
                                    </span>
                                <span class="course-list-units">
                                        3.0
                                    </span>
                            </h3>
                            <div class="course-list-accordion-div">
                                <div class="course-list-desc">
                                    <span class="course-list-desc-title">Description</span>
                                    <p class="course-list-desc-content">
                                        Hotter than a Tokyo summer.
                                        In-ring we colder than a Sapporo winter.
                                        That dude that produced the Vice bringing a gift
                                        from Roppongi that's bigger than ever. Roppongi 3K!
                                    </p>
                                </div>
                                <div class="course-list-type">
                                    <ul class="course-list-type-list">
                                        <li class="course-list-type-list-header">
                                            Room Type
                                        </li>
                                        <li class="course-list-type-list-content">
                                            Laboratory
                                        </li>
                                        <li class="course-list-type-list-header">
                                            No. of Hours
                                        </li>
                                        <li class="course-list-type-list-content">
                                            3 Hours
                                        </li>
                                        <li class="course-list-type-list-header">
                                            Status
                                        </li>
                                        <li class="course-list-type-list-content">
                                            Active
                                        </li>
                                    </ul>
                                </div>
                                <div class="course-list-buttons">

                                </div>
                            </div>
                            <h3>
                                    <span class="course-list-code">
                                        DASALGO
                                    </span>
                                <span class="course-list-title">Design and Analysis of Algorithms
                                    </span>
                                <span class="course-list-units">
                                        3.0
                                    </span>
                            </h3>
                            <div class="course-list-accordion-div">
                                <p>
                                    Hotter than a Tokyo summer.
                                    In-ring we colder than a Sapporo winter.
                                    That dude that produced the Vice bringing a gift
                                    from Roppongi that's bigger than ever.
                                </p>
                            </div>
                        </div>
                    </div>
                </section>
            </section>
        </div>

        <!-- Feedback Messages -->
        <%@include file="screens/feedback-message-screen.jsp" %>

        <script src="/scripts/extension-scripts/jquery/jquery-3.3.1.min.js"></script>
        <script src="/scripts/extension-scripts/jquery/jquery-ui.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $("#course-list-accordion").accordion({
                    collapsible: true,
                    heightStyle: "content",
                    active: false
                });
            });
        </script>
    </body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 03-Feb-19
  Time: 3:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<div class="leftSidebar">
    <table class="contentFilters">
        <tr class = "leftSidebarRows">
            <td>
                <p class = "filterTitles"> View Offerings </p>
                <select class = "filterForms" id="select_view_offerings"> </select>
            </td>
        </tr>
        <tr class = "leftSidebarRows">
            <td>
                <p class = "filterTitles"> Search Course </p>
                <form id ="form_search_class">
                    <input class = "filterForms" id="input_search_course"placeholder = "Search Course...">
                    <button id = "submit_left_side_search"><i class="fas fa-search"></i></button>
                </form>
            </td>
        </tr>
        <tr class = "leftSidebarRows">
            <td>
                <p class = "filterTitles"> Class Type </p>
                <select class = "filterForms" id="select_left_class_type"> </select>
            </td>
        </tr>
        <tr class = "leftSidebarRows">
            <td>
                <p class = "filterTitles"> Room Type </p>
                <select class = "filterForms" id="select_room_type"> </select>
            </td>
        </tr>
        <tr class = "leftSidebarRows">
            <td>
                <p class = "filterTitles"> Time Block </p>
                <select class = "filterForms" id="select_left_timeblock"> </select>
            </td>
        </tr>
        <tr class = "leftSidebarRows">
            <td>
                <p class = "filterTitles"> Class Days </p>
            </td>
        </tr>
        <tr class = "leftSidebarRows">
            <td>
                <button class = "filterClassDays" id="class_m"> M </button>
                <button class = "filterClassDays" id="class_t"> T </button>
                <button class = "filterClassDays" id="class_w"> W </button>
                <button class = "filterClassDays" id="class_h"> H </button>
                <button class = "filterClassDays" id="class_f"> F </button>
                <button class = "filterClassDays" id="class_s"> S </button>
            </td>
        </tr>
    </table>
    <div class = "sidebarMenu">
        <!-- Go to Faculty Load Page -->

            <a href="/cvc/manage-load" class = "menuLinks" id="view_faculty_load">
                <div id = "button_view_faculty"> View Faculty <br> Load Information</div>
            </a>


        <!-- Go To Deloading Page -->
        <button class = "menuLinks" id="button_deloading"> Add New Course </button>
        <button class = "menuLinks" id ="button_concerns"> Concerns </button>
    </div>
</div>
</body>
</html>

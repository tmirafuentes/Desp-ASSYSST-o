<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 03-Feb-19
  Time: 3:13 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>
<div id = "left_side">
    <table id = "left_side_content">
        <tr class = "left_side_rows">
            <td class ="left_side_rows_content">
                <p class = "left_side_text"> View Offerings </p>
                <select class = "left_side_select" id="select_view_offerings">
                    <option value="All">All</option>
                </select>
            </td>dfd
        </tr>
        <tr class = "left_side_rows">
            <td class ="left_side_rows_content">
                <p class = "left_side_text"> Search Course </p>
                <form id ="form_search_class">
                    <input class = "left_side_select" id="input_search_course" placeholder = "Search Course...">
                    <button id = "submit_left_side_search"><i class="fas fa-search"></i></button>
                </form>
            </td>
        </tr>
        <tr class = "left_side_rows">
            <td class ="left_side_rows_content">
                <p class = "left_side_text"> Class Type </p>
                <select class = "left_side_select" id="select_left_class_type">
                    <option value="All" selected>All</option>
                    <option value="Regular">Regular</option>
                    <option value="Elective">Elective</option>
                    <option value="Special">Special</option>
                    <option value="Dissolved">Dissolved</option>
                </select>
            </td>
        </tr>
        <tr class = "left_side_rows">
            <td class ="left_side_rows_content">
                <p class = "left_side_text"> Room Type </p>
                <select class = "left_side_select" id="select_room_type">
                    <option value="All" selected>All</option>
                    <option value="Lecture">Lecture</option>
                    <option value="Laboratory">Laboratory</option>
                </select>
            </td>
        </tr>
        <tr class = "left_side_rows">
            <td class ="left_side_rows_content">
                <p class = "left_side_text"> Time Block </p>
                <select class = "left_side_select" id="select_left_timeblock">
                </select>
            </td>
        </tr>
        <tr class = "left_side_rows">
            <td class ="left_side_rows_content">
                <p class = "left_side_text"> Class Days </p>
            </td>
        </tr>
        <tr class = "left_side_rows">
            <td>
                <button class = "class_days" id="class_m"> M </button>
                <button class = "class_days" id="class_t"> T </button>
                <button class = "class_days" id="class_w"> W </button>
                <button class = "class_days" id="class_h"> H </button>
                <button class = "class_days" id="class_f"> F </button>
                <button class = "class_days" id="class_s"> S </button>
            </td>
        </tr>
    </table>
    <div id = "left_button_holder">
        <!--<button class = left_buttons id ="button_upload_flowchart"> Upload Flowchart </button>-->
        <button class = left_buttons id="button_add_offering"> Add New Offering </button>
        <button class = left_buttons id ="button_export_schedule"> Export Schedule </button>
        <button class = left_buttons id ="button_concerns"> Concerns </button>
    </div>
</div>
</body>
</html>

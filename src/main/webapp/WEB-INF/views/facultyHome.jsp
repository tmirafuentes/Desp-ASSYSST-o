<%--
  Created by IntelliJ IDEA.
  User: Troy Mirafuentes
  Date: 2/1/2019
  Time: 1:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Faculty Home</title>
    <link rel="stylesheet" type="text/css" href="../css/assystxStyle.css">
    <link rel="stylesheet" type="text/css" href="../css/jquery/jquery-ui.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
    <script src="../scripts/jquery/jquery-3.3.1.min.js"></script>
    <script src="../scripts/jquery/jquery-ui.js"></script>
    <script src="../scripts/assystxScript.js"></script>
</head>
<body>
<div id = "left_side">
    <table id = "left_side_content">
        <tr class = "left_side_rows">
            <td class ="left_side_rows_content">
                <p class = "left_side_text"> View Offerings </p>
                <select class = "left_side_select" id="select_view_offerings"> </select>
            </td>
        </tr>
        <tr class = "left_side_rows">
            <td class ="left_side_rows_content">
                <p class = "left_side_text"> Search Course </p>
                <form id ="form_search_class">
                    <input class = "left_side_select" id="input_search_course"placeholder = "Search Course...">
                    <button id = "submit_left_side_search"><i class="fas fa-search"></i></button>
                </form>
            </td>
        </tr>
        <tr class = "left_side_rows">
            <td class ="left_side_rows_content">
                <p class = "left_side_text"> Class Type </p>
                <select class = "left_side_select" id="select_left_class_type"> </select>
            </td>
        </tr>
        <tr class = "left_side_rows">
            <td class ="left_side_rows_content">
                <p class = "left_side_text"> Room Type </p>
                <select class = "left_side_select" id="select_room_type"> </select>
            </td>
        </tr>
        <tr class = "left_side_rows">
            <td class ="left_side_rows_content">
                <p class = "left_side_text"> Time Block </p>
                <select class = "left_side_select" id="select_left_timeblock"> </select>
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
        <button class = left_buttons id ="button_view_faculty"> View Faculty<br>Load Information </button>
        <button class = left_buttons id="button_deloading"> Deloading </button>
        <button class = left_buttons id ="button_concerns"> Concerns </button>
    </div>
</div>
<div id = "header">
    <p id = "system_title"> Unnamed System </p>
    <a href ="#" id = "user_icon">User</a>
</div>
<div id = "smaller_header">
    <a href ="ASSYSTXRevision.html" id = "last_edited">Last edited 1 hour ago</a>
    <a href ="#" id = "online_icons">Online Users</a>
    <p id = "modify_offering"> Modify Offering</p>
</div>
<div id = "main_content">
    <table id = "generated_list">
        <tr>
            <th>Course</th>
            <th>Section</th>
            <th>Day</th>
            <th>Time</th>
            <th>Room</th>
            <th>Faculty</th>
        </tr>
        <tr>
            <td>INOVATE</td>
            <td>S17</td>
            <td>TH</td>
            <td>0730 - 900</td>
            <td>G206</td>
            <td>Jordan Deja</td>
        </tr>
        <tr>
            <td>INOVATE</td>
            <td>S17</td>
            <td>TH</td>
            <td>0730 - 900</td>
            <td>G206</td>
            <td>Jordan Deja</td>
        </tr>
    </table>
</div>
<div id = "right_side">
    <table id = "modify_class_section">
        <tr>
            <th>Class Type</th>
            <th>Section</th>
        </tr>
        <tr>
            <td><select id = "select_right_class_type"> </select></td>
            <td><input id = "text_section" placeholder = ""></td>
        </tr>
    </table>
    <table id = "modify_class_time">
        <tr>
            <th>Time Block</th>
            <th>Day 1</th>
            <th>Day 2</th>
        </tr>
        <tr>
            <td><select id = "select_right_timeblock"> </select></td>
            <td><select id = "select_day1"> </select></td>
            <td><select id = "select_day2"> </select></td>
        </tr>
    </table>
    <table id = "modify_room">
        <tr>
            <th>Room</th>
        </tr>
        <tr>
            <td><input id = "text_room"></input></td>
            <td><button id="button_assign_room">Change Room</button></td>
        </tr>
    </table>
    <table id = "modify_faculty">
        <tr>
            <th>Faculty</th>
        </tr>
        <tr>
            <td><select id = "select_faculty"></select></td>
            <td><button id="button_assign_faculty">Re-Assign</button></td>
        </tr>
    </table>
    <table id = "modify_concerns">
        <tr>
            <th>Concerns</th>
        </tr>
        <tr>
            <td><textarea  id="area_concerns"></textarea></td>
        </tr>
    </table>
    <table id = "modify_button_concerns">
        <tr>
            <td><button id="button_submit_concerns">Submit</button></td>
            <td><button id="button_cancel_concerns">Cancel</button></td>
        </tr>
    </table>
</div>
</body>
</html>

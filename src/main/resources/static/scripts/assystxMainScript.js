$(function() {
    var modifyFaculty = $( "<form id='modal_form_faculty'></form>" );
    var modifyHeader = $( "<table id='modal_header'></table>" );
    var modifyFooter = $( "<table id='modal_footer'></table>" );
    var modifyRoom = $( "<table id='modal_table_room'></table>" );
    var addCourse = $( "<table id='modal_table_add_courses'></table>" );
    var concernEntry = $("<table class='concern_entry'>" +
        "<tr>" +
        "<td class ='concern_name'>Ryan Dimaunahan</td>" +
        "<td class ='concern_time'>1:29 PM</td>" +
        "</tr>" +
        "<tr>" +
        "<td colspan='2' class ='concern_message'>Hello Sir Ryan, Concern lang po. Si Doc Mc ay bawal na mag-stay ng gabi so no night classes. Tnx po.</td>" +
        "</tr>" +
        "</table>");
    var roomEntry = $("<tr>" +
        "<td></td>" +
        "<td></td>" +
        "<td></td>" +
        "<td></td>" +
        "<td><button class = 'assign_modal_buttons'>Assign</button></td>" +
        "</tr>");
    var courseEntry = $( "<tr>" +
        "<td>INOVATE</td>" +
        "<td>Technology and Innovation Management</td>" +
        "<td>3.0</td>" +
        "<td><button class = 'add_modal_buttons'>+</button></td>" +
        "</tr>");

    /* Add Div for Dialog/Modal */
    jQuery('<div/>', {
        id: 'div_dialog',
    }).appendTo('body');
    $(".modify_sidebar").hide();

    /* Button Listener for Concerns Modal */
    $("#button_concerns").click(function() {
        $("#div_dialog").dialog({
           title: "Concerns",
           width: 500,
           height: 500,
           modal: true
        });
    });

    /* Button Listener for View Faculty Modal */
    $("#button_view_faculty").click(function() {
        $("#div_dialog").dialog({
            title: "View Faculty",
            width: 500,
            height: 500,
            modal: true
        });
    });

    /* Button Listener for Assign Faculty Modal */
    var modifyFacultyForm = $("<form id='modal_form_faculty'></form>");
    $("#button_assign_faculty").click(function() {
        $(modifyFacultyForm).empty();
        $(modifyFacultyForm).append("<select id='faculty_select_modal'></select> " +
            "<br><button id='faculty_set_button' class='modal_buttons'>Set Faculty</button>" +
            "<button id='faculty_cancel_button' class='modal_buttons'>Cancel</button>");
        $("#div_dialog").append(modifyFacultyForm);
        $("#div_dialog").dialog({
            title: "Assign Faculty",
            width: 300,
            height: 250,
            modal: true
        });
    });

    /* Button Listener for Flowchart Modal */
    $("#button_upload_flowchart").click(function (){
        $("#div_dialog").dialog({
            title:"Upload Flowchart",
            width:300,
            height:300,
            modal:true
        })
    });

    /* Button Listener for Add Offering Modal */
    $("#button_add_offering").click(function (){
        $(modifyHeader).empty();
        $(addCourse).empty();
        $("#div_dialog").empty();
        $(modifyFooter).empty();
        $(modifyHeader).append("<tr>" +
            "<th>Degree Program</th>" +
            "<th>Batch</th>" +
            "<th>Academic Year</th>" +
            "<th>Term</th>" +
            "<th>Search</th>" +
            "<tr><form id='add_course_form'>" +
            "<td><select class = 'modal_select' id='select_degree'></select></td>" +
            "<td><select class = 'modal_select' id='select_batch'></select></td>" +
            "<td><select class = 'modal_select' id='select_academic_year'></select></td>" +
            "<td><select class = 'modal_select' id='select_term'></select></td>" +
            "<td><input class = 'modal_search' id='modal_input_search_course'><button id='button_search_course'><i class='fas fa-search'></i></button></td>" +
            "</form>" +
            "</tr>" +
            "</tr>");
        $(addCourse).append("<tr>" +
            "<th>Course</th>" +
            "<th>Name</th>" +
            "<th>Units</th>" +
            "<th>Add</th>" +
            "</tr>" +
            "<tr>" +
            "<td>INOVATE</td>" +
            "<td>Technology and Innovation Management</td>" +
            "<td>3.0</td>" +
            "<td><button class = 'add_modal_buttons'>+</button></td>" +
            "</tr>");
        $(modifyFooter).append("<tr>" +
            "<td><button id='course_done_button' class='modal_buttons'>Done</button></td>" +
            "<td><button id='course_cancel_button' class='modal_buttons'>Cancel</button></td>" +
            "</tr>");
        $("#div_dialog").append(modifyHeader);
        $("#div_dialog").append(addCourse);
        $("#div_dialog").append(modifyFooter);
        $("#div_dialog").dialog({
            title:"Add Course Offerings",
            width:800,
            height:600,
            modal:true
        });
    });

    /* Button Listener for Deload Modal */
    var deloadTable = $( "<form id='modal_form_deload'><table id='dialog_load_table'></table></form>" );
    var professorName = $( "<p id='dialog_professor_name'>Jordan Deja</p>" );
    $("#deload_button").click(function() {
        $("#div_dialog").empty();
        $("#div_dialog").append(professorName);
        $("#div_dialog").append(deloadTable);
        $("#dialog_load_table").append("<tr>" +
            "<td><p class='p_modal'>Total Current Load</p></td>" +
            "<td><p id='p_total_load'></p></td>" +
            "</tr>" +
            "<tr>" +
            "<td><p class='p_modal'>Deloaded units</p></td>" +
            "<td><input type='number' id='input_deload'></td>" +
            "</tr>" +
            "<tr>"  +
            "<td><p class='p_modal'>Remarks</p></td>" +
            "<td><select id='select_deload'></select></td>" +
            "</tr>" +
            "<tr>"  +
            "<td><button class='modal_buttons' id='deload_confirm'>Confirm</button></td>" +
            "<td><button class='modal_buttons' id='deload_cancel'>Cancel</button></td>" +
            "</tr>");
        $("#div_dialog").dialog({
           title: "Deloading",
           width: 450,
           height: 300,
           modal: true,
           close: function(event, ui) {
               $("#dialog_load_table").empty();
           }
        });
    });

    /* Selecting an offering */
    $("#generated_list .genList_rows:not(:first-child)").click(function(){
        //hasClickedTableRow = !hasClickedTableRow;
        /* Check if selected already */
        if ($(this).css("background-color") === "rgb(60, 184, 120)")
        {
            /* Modify row appearance */
            $(".modify_sidebar").find("input:text").val("");
            $(".modify_sidebar").hide();
            $(this).removeClass("selectedOffering");
            $(this).css({'background-color' : '#e2e2e2'});
        }
        /* It has not been selected yet */
        else if ($(this).css("background-color") === "rgb(226, 226, 226)")
        {
            /* Modify row appearance */
            $("#generated_list .genList_rows:not(:first-child)").removeClass("selectedOffering");
            $("#generated_list .genList_rows:not(:first-child)").css({'background-color' : '#e2e2e2'});
            $(this).css({'background-color' : '#3cb878'});
            $(this).addClass("selectedOffering");

            /* Put values into right sidebar */

            /* Class Type */
            $("#select_right_class_type").val($(this).find("#off_status").val());

            /* Section */
            $("#text_section").val($(this).find("#off_section").val());

            /* Time Slots */
            var offStartTime = $(this).find("#off_startTime").val();
            var offEndTime = $(this).find("#off_EndTime").val();
            offStartTime = offStartTime.length > 3 ? offStartTime.substr(0, 2) + ":" + offStartTime.substr(2) :
                            "0" + offStartTime.substr(0, 1) + ":" + offStartTime.substr(1);
            offEndTime = offEndTime.length > 3 ? offEndTime.substr(0, 2) + ":" + offEndTime.substr(2) :
                            "0" + offEndTime.substr(0, 1) + ":" + offEndTime.substr(1);
            console.log("Offstarttime: " + offStartTime);
            $("#select_right_start_timeblock").val(offStartTime);
            $("#select_right_end_timeblock").val(offEndTime);

            /* Days */
            var day1 = $(this).find("#off_day1").val();
            var day2 = $(this).find("#off_day2").val();
            $("#select_day1").val(day1);
            $("#select_day2").val(day2);

            /* Room */
            $("#text_room").val($(this).find("#off_room").val());

            /* Faculty */
            $("#select_faculty").val($(this).find("#off_faculty").val());

            /* Offering */
            $("#text_offId").val($(this).find("#off_id").val());

            /* Show Sidebar */
            $(".modify_sidebar").show();
        }
    });

    /* Button Listener for Add Room */
    $("#button_assign_room").click(function (){
        $(modifyHeader).empty();
        $(modifyRoom).empty();
        $("#div_dialog").empty();
        $(modifyFooter).empty();
        $(modifyHeader).append("<tr>" +
            "<th>Search</th>" +
            "<th margin-left='50px'>Room Type</th>" +
            "<th>Building</th>" +
            "<tr><form id='assign_room_form'>" +
            "<td><input class = 'modal_search' id='input_search_room'><button id='button_search_room'><i class='fas fa-search'></i></button></td>" +
            "<td><select class = 'modal_select' id='select_room_type'></select></td>" +
            "<td><select class = 'modal_select' id='select_building'></select></td>" +
            "</form>" +
            "</tr>" +
            "</tr>");
        $(modifyRoom).append("<tr>" +
            "<th>Room</th>" +
            "<th>Room Type</th>" +
            "<th>Building</th>" +
            "<th>Capacity</th>" +
            "<th>Assign</th>" +
            "</tr>" +
            "<tr>" +
            "<td>G201</td>" +
            "<td>Lecture</td>" +
            "<td>Gokongwei Building</td>" +
            "<td>45</td>" +
            "<td><button class = 'assign_modal_buttons'>Assign</button></td>" +
            "</tr>");
        $(modifyFooter).append("<tr>" +
            "<td><button id='room_done_button' class='modal_buttons'>Done</button></td>" +
            "<td><button id='room_cancel_button' class='modal_buttons'>Cancel</button></td>" +
            "</tr>");
        $("#div_dialog").append(modifyHeader);
        $("#div_dialog").append(modifyRoom);
        $("#div_dialog").append(modifyFooter);
        $("#div_dialog").dialog({
            title:"Assign Room",
            width:800,
            height:600,
            modal:true
        });
    });
});

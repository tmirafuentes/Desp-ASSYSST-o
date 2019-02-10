$(function (){
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

    var hasClickedTableRow = false;
    jQuery('<div/>', {
        id: 'div_dialog',
    }).appendTo('body');
      //hide the right side
      $("#modify_class_section").hide();
      $("#modify_time").hide();
      $("#modify_days").hide();
      $("#modify_room").hide();
      $("#modify_faculty").hide();
      $("#modify_concerns").hide();
      $("#modify_button_concerns").hide();
      $("#modify_offering").hide();

    $("#button_concerns").click(function (){
        //console.log("pumapasok?");
        $("#div_dialog").dialog({
            title:"Concerns",
            width:450,
            height:450,
            modal:true
        })
    })

    $("#button_assign_faculty").click(function (){
        $(modifyFaculty).empty();
        $("#div_dialog").empty();
        $(modifyFaculty).append("<select id='faculty_select_modal'></select> " +
            "<br><button id='faculty_set_button' class='modal_buttons'>Set Faculty</button>" +
            "<button id='faculty_cancel_button' class='modal_buttons'>Cancel</button>");
        $("#div_dialog").append(modifyFaculty);
        $("#div_dialog").dialog({
            title:"Re-assign Faculty",
            width:300,
            height:250,
            modal:true
        })
    })
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
        })
    })
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
        })
    })
    $("#button_upload_flowchart").click(function (){
        $("#div_dialog").dialog({
            title:"Upload Flowchart",
            width:300,
            height:300,
            modal:true
        })
    })

    $("#button_concerns").click(function (){
        $("#div_dialog").empty();
        $("#div_dialog").append(concernEntry);
        $("#div_dialog").dialog({
            title:"Concerns",
            width:450,
            height:450,
            modal:true
        })
    })
    /* Selecting an offering
    var selectedOffering = null;
    $("#generated_list.genList_rows:not(:first-child)").click(function (){
        hasClickedTableRow = !hasClickedTableRow;
        // Check if selected already
        if ($(this).css("background-color") === "rgb(60, 184, 120)")
        {
            $(".modify_sidebar").hide();
            $(this).removeClass("selectedOffering");
            $(this).css({'background-color' : '#e2e2e2'});
        }
        // It has not been selected yet
        else if ($(this).css("background-color") === "rgb(226, 226, 226)")
        {
            $(".modify_sidebar").show();
            $("#generated_list .genList_rows:not(:first-child)").removeClass("selectedOffering");
            $("#generated_list .genList_rows:not(:first-child)").css({'background-color' : '#e2e2e2'});
            $(this).css({'background-color' : '#3cb878'});
            $(this).addClass("selectedOffering");
        }
    })*/
})
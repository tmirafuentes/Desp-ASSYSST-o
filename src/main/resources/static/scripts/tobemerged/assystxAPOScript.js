$(function (){
    var modifyFaculty = $( "<form id='modal_form_faculty'></form>" );
    var modifyHeader = $( "<table id='modal_header'></table>" );
    var modifyFooter = $( "<table id='modal_footer'></table>" );
    var modifyRoom = $( "<table id='modal_table_room'></table>" );
    var concernEntry = $("<table class='concern_entry'>" +
        "<tr>" +
        "<td class ='concern_name'>Ryan Dimaunahan</td>" +
        "<td class ='concern_time'>1:29 PM</td>" +
        "</tr>" +
        "<tr>" +
        "<td colspan='2' class ='concern_message'>Hello Sir Ryan, Concern lang po. Si Doc Mc ay bawal na mag-stay ng gabi so no night classes. Tnx po.</td>" +
        "</tr>" +
        "</table>");
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
    $("#button_upload_flowchart").click(function (){
        $("#div_dialog").dialog({
            title:"Upload Flowchart",
            width:300,
            height:300,
            modal:true
        })
    })

    $("#button_add_offering").click(function (){
        $("#div_dialog").dialog({
            title:"Upload Flowchart",
            width:500,
            height:500,
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
    $("#generated_list tr").click(function (){
        hasClickedTableRow = !hasClickedTableRow;
        if(hasClickedTableRow == true)
        {
            $(this).css({'background-color': '#3cb878'})
        }
        else{
            $(this).css({'background-color': 'white'})
        }
        
        $("#modify_class_section").toggle();
        $("#modify_time").toggle();
        $("#modify_days").toggle();
        $("#modify_room").toggle();
        $("#modify_faculty").toggle();
        $("#modify_concerns").toggle();
        $("#modify_button_concerns").toggle();
        $("#modify_offering").toggle();

    })
})
$(function() {
    /* Add Div for Dialog/Modal */
    jQuery('<div/>', {
        id: 'div_dialog',
    }).appendTo('body');
    $(".modify_sidebar").hide();

    /* Selecting an offering */
    $(".cwOfferings .generatedContent .genContentRows:not(:first-child)").click(function(){
        console.log("Hello Here");

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
            $(".modify_sidebar").find("input:text").val("");
            $(".cwOfferings .generatedContent .genContentRows:not(:first-child)").removeClass("selectedOffering");
            $(".cwOfferings .generatedContent .genContentRows:not(:first-child)").css({'background-color' : '#e2e2e2'});
            $(this).css({'background-color' : '#3cb878'});
            $(this).addClass("selectedOffering");

            /* Put values into right sidebar */

            /* Class Type */
            $("#select_right_class_type").val($(this).find("#off_status").val());

            /* Section */
            $("#text_section").val($(this).find("#off_section").val());

            /* Time Slots */
            if ($(this).find("#off_startTime").val() == "--:--" &&
                $(this).find("#off_EndTime").val() == "--:--")
            {
                $("#select_right_start_timeblock").val("--:--");
                $("#select_right_end_timeblock").val("--:--");
            }
            else
            {
                var offStartTime = $(this).find("#off_startTime").val();
                var offEndTime = $(this).find("#off_EndTime").val();
                offStartTime = offStartTime.length > 3 ? offStartTime.substr(0, 2) + ":" + offStartTime.substr(2) :
                    "0" + offStartTime.substr(0, 1) + ":" + offStartTime.substr(1);
                offEndTime = offEndTime.length > 3 ? offEndTime.substr(0, 2) + ":" + offEndTime.substr(2) :
                    "0" + offEndTime.substr(0, 1) + ":" + offEndTime.substr(1);
                $("#select_right_start_timeblock").val(offStartTime);
                $("#select_right_end_timeblock").val(offEndTime);
            }

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

    /* Selecting a Faculty Load */
    $(".cwFacultyLoad .generatedContent .genContentRows:not(:first-child)").click(function(){
        console.log("Hello Here");
        /* Check if selected already */
        if ($(this).css("background-color") === "rgb(60, 184, 120)")
        {
            /* Modify row appearance */
            $(".modify_sidebar").find("input:text").val("");
            $(".modify_sidebar").hide();
            $(this).removeClass("selectedFaculty");
            $(this).css({'background-color' : '#e2e2e2'});
        }
        /* It has not been selected yet */
        else if ($(this).css("background-color") === "rgb(226, 226, 226)")
        {
            /* Modify row appearance */
            $(".modify_sidebar").find("input:text").val("");
            $(".cwFacultyLoad .generatedContent .genContentRows:not(:first-child)").removeClass("selectedFaculty");
            $(".cwFacultyLoad .generatedContent .genContentRows:not(:first-child)").css({'background-color' : '#e2e2e2'});
            $(this).css({'background-color' : '#3cb878'});
            $(this).addClass("selectedFaculty");

            /* Put values into right sidebar */

            $(".modify_sidebar").show();
        }
    });

    /* New Modals Code */
    $(".divModals").dialog({
        autoOpen: false
    });

    /* Open Assign Room Modal */
    $("#modOffRoomButton").click(function() {
        $("#modalAssignRoom").dialog({
            title:"Assign Room",
            width:800,
            height:600,
            modal:true
        });
        $("#modalAssignRoom").dialog("open");
    });

    /* Assign New Room and Close Modal */
    $(".assignRoomBtns").click(function() {
        var roomCode = $(this).attr("value");
        $("#text_room").val(roomCode);
        $("#modalAssignRoom").dialog("close");
    });

    /* Open Assign Faculty Modal */
    $("#modOffFacultyButton").click(function() {
        $("#modalAssignFaculty").dialog({
            title:"Assign Faculty",
            width:750,
            height:600,
            modal:true
        });
        $("#modalAssignFaculty").dialog("open");
    });

    /* Assign New Faculty and Close Modal */
    $(".assignFacultyBtns").click(function() {
        var facultyCode = $(this).attr("value");
        $("#select_faculty").val(facultyCode);
        $("#modalAssignFaculty").dialog("close");
    });

    /* Open Deload Faculty Modal */
    $("#modFacDeloadButton").click(function() {
        $("#modalDeloadFaculty").dialog({
           title: "Deload Faculty",
           width: 400,
           height: 300,
           modal: true
        });
        $("#modalDeloadFaculty").dialog("open");
    });

    /* Open Add Offering Modal */
    $("#button_add_offering").click(function() {
        $("#modalAddOffering").dialog({
            title:"Add Course Offerings",
            width:800,
            height:600,
            modal:true
        });
        $("#modalAddOffering").dialog("open");
    });

});

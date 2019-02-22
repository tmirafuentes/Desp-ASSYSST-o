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

    /* Filtering Script Part */

    /* Filtering Timeblock */
    $("#select_left_timeblock").change(function() {
        //value from the timeslot filter
        var filterData = $(this).val();
        var rowNumber = 0;
        if(filterData != "All")
        {
            //shows all hidden rows
            $(".genContentCols:nth-child(4)").each(function() {
                    $(this).parent().show();
            });

            //compares the timeblock values
            $(".genContentCols:nth-child(4)").each(function() {
                if(rowNumber != 0) {
                    var cellText = $.trim($(this).text());//value for the timeslots displayed
                    if (filterData != cellText) {//if it doesn't match hide it
                            $(this).parent().hide();
                            rowNumber++;
                    }
                }
                else{
                    rowNumber++;
                }
            });
        }
        else{
            //shows all hidden rows
            $(".genContentCols:nth-child(4)").each(function() {
                $(this).parent().show();
            });
        }

    });
    /* Filtering Class Type */
    $("#select_left_class_type").change(function() {
        var filterData = $(this).val();
        var rowNumber = 0;
        if(filterData != "All") {
            //shows all hidden rows
            $(".genContentRows #off_status").each(function () {
                $(this).parent().parent().show();
            });

            $(".genContentRows #off_status").each(function () {
                if(rowNumber != 0) {
                    var cellText = $.trim($(this).val());//value for the timeslots displayed
                    if (filterData != cellText) {//if it doesn't match hide it
                        $(this).parent().hide();
                        rowNumber++;
                    }
                }
                else{
                    rowNumber++;
                }
            });
        }
        else{
            //shows all hidden rows
            $(".genContentRows #off_status").each(function() {
                $(this).parent().show();
            });
        }
    });
    /* Filtering Class Type */
    $("#select_room_type").change(function() {
        var filterData = $(this).val();
        var rowNumber = 0;
        if(filterData != "All") {
            //shows all hidden rows
            $(".genContentCols #off_roomtype").each(function () {
                $(this).parent().parent().show();
            });

            $(".genContentCols #off_roomtype").each(function () {
                if(rowNumber != 0) {
                    var cellText = $.trim($(this).val());//value for the timeslots displayed
                    if (filterData != cellText) {//if it doesn't match hide it
                        $(this).parent().parent().hide();
                        rowNumber++;
                    }
                }
                else{
                    rowNumber++;
                }
            });
        }
        else{
            //shows all hidden rows
            $(".genContentCols #off_roomtype").each(function() {
                $(this).parent().parent().show();
            });
        }
    });
    /* Filtering Room Type */
    $("#select_room_type").change(function() {
        var filterData = $(this).val();
        var rowNumber = 0;
        if(filterData != "All") {
            //shows all hidden rows
            $(".genContentCols #off_roomtype").each(function () {
                $(this).parent().parent().show();
            });

            $(".genContentCols #off_roomtype").each(function () {
                if(rowNumber != 0) {
                    var cellText = $.trim($(this).val());//value for the timeslots displayed
                    if (filterData != cellText) {//if it doesn't match hide it
                        $(this).parent().parent().hide();
                        rowNumber++;
                    }
                }
                else{
                    rowNumber++;
                }
            });
        }
        else{
            //shows all hidden rows
            $(".genContentCols #off_roomtype").each(function() {
                $(this).parent().parent().show();
            });
        }
    });

    /* Filtering Term
    $("#select_view_offerings").change(function() {
        var filterData = $(this).val();
        var rowNumber = 0;
        console.log("filterData" + filterData);
        if(filterData != "All") {
            //shows all hidden rows
            $(".genContentCols #off_term").each(function () {
                $(this).parent().parent().show();
            });

            $(".genContentCols #off_term").each(function () {
                if(rowNumber != 0) {
                    var cellText = $.trim($(this).val());
                    console.log("cellText" + cellText);
                    if (filterData != cellText) {//if it doesn't match hide it
                        console.log("pumapasok sa hide");
                        $(this).parent().parent().hide();
                        rowNumber++;
                    }
                }
                else{
                    rowNumber++;
                }
            });
        }
        else{
            //shows all hidden rows
            $(".genContentCols #off_term").each(function() {
                $(this).parent().parent().show();
            });
        }
    });*/
            // Get and convert the data for sending
            // Example: This variable contains the selected option-text
        /*var filterData = $(this).text();
        console.log(filterData);

        $(".genContentCols:nth-child(4)").each(function() {
            var cellText = $.trim($(this).text());
            if (cellText.localeCompare(filterData) != 0) {
                console.log("criteria" + filterData);
                console.log("text in cell" + cellText);
                $(this).parent().hide();
            }
        });
        });
    });*/
});

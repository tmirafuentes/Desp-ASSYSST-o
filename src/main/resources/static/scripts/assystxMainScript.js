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

            $(".modify_sidebar").show();
            /* Put values into right sidebar */

            var facultyID = $(this).find("#faculty_id").val();
            var rownumber = 0;
            //hide classes that aren't assigned to him/her
            //hide all rows
            $(".genLoadRows").each(function () {
                if(rownumber > 0)
                {
                    $(this).hide();
                    rownumber++;
                }
                else
                    rownumber++;

            });

            //show all rows that are assigned to him/her
            $(".genLoadCols:nth-child(6)").each(function() {
                var idnumber = $.trim($(this).text());
                if(facultyID == idnumber)
                {
                    $(this).parent().show();
                    $(this).hide();
                }

            });

            /* Faculty Name */
            $("#facultyInfoTitles_name").text($(this).find("#faculty_Lname").val() + ", " + $(this).find("#faculty_Fname").val());
            /* Department*/
            $("#facultyInfoTitles_department").text($(this).find("#faculty_department").val());
            /* Deload ID*/
            $("#deload_id").val($(this).find("#db_deload_id").val());
            /* Total Load*/
            $("#right_total_load").val($(this).find("#total_load").val());
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
        //Pass the deload id value to the dialog
        var deLoadID = $(this).parent().find("#deload_id").val();
        var totalCurrLoad = $(this).parent().find("#right_total_load").val();

        $("#modal_deload_id").val(deLoadID);
        $("#modal_current_load").text("Total Current Load: " + totalCurrLoad);

        $("#modalDeloadFaculty").dialog({
           title: "Deload Faculty",
           width: 400,
           height: 300,
           modal: true
        });
        $("#modalDeloadFaculty").dialog("open");
    });

    /* Select Deload Code and display units */
    $("#select_deload").change(function() {
        $("#input_deload").val($(this).children("option:selected").val());
        $("#modal_deload_code").val();
        $("#modal_deload_code").val($(this).children("option:selected").text());
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

    /* Add New Offering to System */
    $(".add_offer_btns").click(function() {
        var courseCode = $(this).attr("value");
        $("#add_offer_field").val(courseCode);
        $("#modalAddOffering").dialog("close");
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

    /* Filtering Term*/
    $("#select_view_offerings").change(function() {
        var filterData = $(this).val();//gets value for the filter
        var rowNumber = 0;

        $(".genContentCols #off_term").each(function () {
            $(this).parent().parent().show();
        });

        if(filterData != "All") {
                $(".genContentCols #off_term").each(function () {
                    if (rowNumber != 0) {
                        var cellText = $(this).val();
                        if (cellText != filterData) {
                            console.log(cellText + "vs" + filterData);//weird bug where it leaves some
                            $(this).parent().parent().hide();
                        }
                        rowNumber++;
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

    });
    /*Search Course*/
    $("#form_search_class").submit(function(){
        alert("Submitted");
    });

    /*Filtering by class day Monday*/
    $("#class_m").click(function() {
        $(".genContentRows").each(function () {
            $(this).show();
        });
        $(".genContentRows").each(function () {
            //var iterator = $.trim($(this).find("#off_counter").val());
            var textInside = $.trim($(':nth-child(3)', this).text())
            textInside = textInside.replace(/\s+/g, '');
            if(!textInside.includes("M"))
                $(this).hide();
        });
    });
    /*Filtering by class day Tuesday*/
    $("#class_t").click(function() {
        $(".genContentRows").each(function () {
            $(this).show();
        });
        $(".genContentRows").each(function () {
            //var iterator = $.trim($(this).find("#off_counter").val());
            var textInside = $.trim($(':nth-child(3)', this).text())
            textInside = textInside.replace(/\s+/g, '');
            if(!textInside.includes("T"))
                $(this).hide();
        });
    });
    /*Filtering by class day Wednesday*/
    $("#class_w").click(function() {
        $(".genContentRows").each(function () {
            $(this).show();
        });
        $(".genContentRows").each(function () {
            //var iterator = $.trim($(this).find("#off_counter").val());
            var textInside = $.trim($(':nth-child(3)', this).text())
            textInside = textInside.replace(/\s+/g, '');
            if(!textInside.includes("W"))
                $(this).hide();
        });
    });
    /*Filtering by class day Thursday*/
    $("#class_h").click(function() {
        $(".genContentRows").each(function () {
            $(this).show();
        });
        $(".genContentRows").each(function () {
            //var iterator = $.trim($(this).find("#off_counter").val());
            var textInside = $.trim($(':nth-child(3)', this).text())
            textInside = textInside.replace(/\s+/g, '');
            if(!textInside.includes("H"))
                $(this).hide();
        });
    });
    /*Filtering by class day Friday*/
    $("#class_f").click(function() {
        $(".genContentRows").each(function () {
            $(this).show();
        });
        $(".genContentRows").each(function () {
            //var iterator = $.trim($(this).find("#off_counter").val());
            var textInside = $.trim($(':nth-child(3)', this).text())
            textInside = textInside.replace(/\s+/g, '');
            if(!textInside.includes("F"))
                $(this).hide();
        });
    });
    /*Filtering by class day Saturday*/
    $("#class_f").click(function() {
        $(".genContentRows").each(function () {
            $(this).show();
        });

        $(".genContentRows").each(function () {
            var textInside = $.trim($(':nth-child(3)', this).text())
            textInside = textInside.replace(/\s+/g, '');
            if(!textInside.includes("S"))
                $(this).hide();
        });
    });
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

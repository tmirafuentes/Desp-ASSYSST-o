$(function() {
    /* Add Div for Dialog/Modal */
    jQuery('<div/>', {
        id: 'div_dialog',
    }).appendTo('body');
    $(".modify_sidebar").hide();

    //Sorting the days
    /*
    $(".genContentRows").each(function () {
            //var iterator = $.trim($(this).find("#off_counter").val());
            var textInside = $.trim($(':nth-child(3)', this).text())
            textInside = textInside.replace(/\s+/g, '');
            $(':nth-child(3)', this).text(orderDays(textInside))

    });

    function orderDays(days)
    {
        var schoolDays = ['M', 'T', 'W', 'H', 'F', 'S'];
        var newDays = "";
        for (var x = 0; x < days.length; x++)
        {
            if(x < days.length-1)
            {
                if(schoolDays.indexOf(days.charAt(x+1)) <= schoolDays.indexOf(days.charAt(x)))
                    newDays = days.charAt(x) + days.charAt(x+1);
            }

        }
        if(newDays == "")
            return days;
        else
            return newDays;

    }*/

    /* Selecting an offering */
    $(".cwOfferings .generatedContent .genContentRows:not(:first-child)").click(function(){
        console.log("Hello Here");

        //unhide all options
        $("#select_day1  option").each(function() {
            $(this).show()
        });

        $("#select_day2  option").each(function() {
                $(this).show()
        });
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
        showallCourseModalRows();
    });

    /* Add New Offering to System */
    $(".add_offer_btns").click(function() {
        var courseCode = $(this).attr("value");
        $("#add_offer_field").val(courseCode);
        $("#modalAddOffering").dialog("close");
    });

    /* Concerns Modal */
    $("#button_concerns").click(function() {
        $("#modalConcerns").dialog({
            title:"Concerns",
            width:450,
            height:500,
            modal:true
        });
        $("#modalConcerns").dialog("open");
    });

    //setting up the cancel button
    $("#button_cancel_concerns").click(function(){
        $(".genContentRows:not(:first-child)").each(function () {
            $(this).css({'background-color' : '#e2e2e2'});
        });
        $(".modify_sidebar").hide();
    });
    /* Filtering Script Part */


    /* Filtering Term*/

    /*Search Course*/
    $("#submit_left_side_search").click(function(){
        var textSearched = $.trim($("#input_search_course").val())

        $(".filter_comments").hide();

        showallRows();

        $(".genContentRows:visible").each(function () {
            var courseTraverse = $.trim($(':first-child', this).text())

            if(textSearched.toUpperCase() !== courseTraverse.toUpperCase())
                $(this).hide();
        });

        if(countallRows() == 0) {
            $(".filter_comments").show();

        }
    });

    /*Search Course for modals*/
    $("#button_search_course").click(function(){
        var textSearched = $.trim($("#modal_input_search_course").val())
        $(".modal_generated_course_offerings").each(function () {
            var courseTraverse = $(':first-child', this).text();
            courseTraverse = courseTraverse.replace("+", "");
            courseTraverse = $.trim(courseTraverse);
            if(textSearched.toUpperCase() !== courseTraverse.toUpperCase())
                $(this).hide();
        });
    });

    /* General Function for Filters*/
    $(".filterForms").change(function() {
        //alert("hello");
        checkTimeblock();//check timeblock filter
        checkRoomType();//check room type filter
        checkClassType(); //check class type filter
        checkTerm(); //check class type filter

        if(countallRows() <= 0)
        {
            console.log("Show");
            $(".filter_comments").show();
        }

        else
        {
            console.log("Hide");
            $(".filter_comments").hide();
        }
    });

    function checkTimeblock()
    {
        var filterData = $("#select_left_timeblock").val();
        if(filterData != "All")
        {
            $(".genContentCols:nth-child(4):visible").each(function() {
                var cellText = $.trim($(this).val());
                if (filterData != cellText) {
                    $(this).parent().hide();
                }
            });
        }
        else{
            showallRows();
            if($("#select_view_offerings").val() != "All")
                checkTerm();
            if($("#select_left_class_type").val() != "All")
                checkClassType();
            if($("#select_room_type").val() != "All")
                checkRoomType();
        }
    }

    function checkRoomType()
    {
        var filterData = $("#select_room_type").val();
        if(filterData != "All")
        {
            $(".genContentRows:visible").each(function() {
                var cellText = $.trim($("#off_roomtype").val());
                if (filterData != cellText)
                    $(this).hide();
            });
        }
        else{
            showallRows();
            if($("#select_view_offerings").val() != "All")
                checkTerm();
            if($("#select_left_class_type").val() != "All")
                checkClassType();
            if(($("#select_left_timeblock").val() != "All"))
                checkTimeblock();
        }
    }

    function checkClassType()
    {
        var filterData = $("#select_left_class_type").val();
        if(filterData != "All")
        {
            $(".genContentRows:visible").each(function() {
                var cellText = $.trim($("#off_status").val());
                if (filterData != cellText)
                    $(this).hide();
            });
        }
        else{
            console.log(countallRows());
            showallRows();
            if($("#select_view_offerings").val() != "All")
                checkTerm();
            if($("#select_room_type").val() != "All")
                checkRoomType();
            if(($("#select_left_timeblock").val() != "All"))
                checkTimeblock();
        }
    }
    function checkTerm()
    {
        var filterData = $("#select_view_offerings").val();//gets value for the filter
        if(filterData != "All") {
            $(".genContentRows:visible").each(function () {
                var cellText = $.trim($("#off_term").val());
                if (filterData != cellText)
                    $(this).hide();
            });
        }
        else{
            showallRows();
            if($("#select_left_class_type").val() != "All")
                checkClassType();
            if($("#select_room_type").val() != "All")
                checkRoomType();
            if(($("#select_left_timeblock").val() != "All"))
                checkTimeblock();
        }

    }
    function showallRows()
    {
        $(".genContentRows").each(function () {
            $(this).show();
        });
    }
    function showallCourseModalRows()
    {
        $(".modal_generated_course_offerings").each(function () {
            $(this).show();
        });
    }
    function countallRows()
    {
        var numberCtr = 0;
        $(".genContentRows:visible").not(":first").each(function () {
            numberCtr++;
        });
        return numberCtr;
    }

    /* Class Day Filters*/

    /*Filtering by class day Monday*/
    $("#class_m").click(function() {
        var totalResponses = 0;
        $(".filter_comments").hide();
        $(".genContentRows").each(function () {
            $(this).show();
            totalResponses++;
        });
        $(".genContentRows").each(function () {
            //var iterator = $.trim($(this).find("#off_counter").val());
            var textInside = $.trim($(':nth-child(3)', this).text())
            textInside = textInside.replace(/\s+/g, '');
            if(!textInside.includes("M"))
            {
                totalResponses--;
                $(this).hide();
            }

        });
        $(".genContentRows:first-child").show();
        console.log(totalResponses);
        if(totalResponses == 0) {
            $(".filter_comments").show();

        }
    });
    /*Filtering by class day Tuesday*/
    $("#class_t").click(function() {
        var totalResponses = 0;
        $(".filter_comments").hide();
        $(".genContentRows").each(function () {
            $(this).show();
            totalResponses++;
        });
        $(".genContentRows").each(function () {
            //var iterator = $.trim($(this).find("#off_counter").val());
            var textInside = $.trim($(':nth-child(3)', this).text())
            textInside = textInside.replace(/\s+/g, '');
            if(!textInside.includes("T"))
            {
                totalResponses--;
                $(this).hide();
            }
        });
        $(".genContentRows:first-child").show();
        console.log(totalResponses);
        if(totalResponses == 0) {
            $(".filter_comments").show();

        }
    });
    /*Filtering by class day Wednesday*/
    $("#class_w").click(function() {
        var totalResponses = 0;
        $(".filter_comments").hide();
        $(".genContentRows").each(function () {
            $(this).show();
            totalResponses++;
        });
        $(".genContentRows").each(function () {
            //var iterator = $.trim($(this).find("#off_counter").val());
            var textInside = $.trim($(':nth-child(3)', this).text())
            textInside = textInside.replace(/\s+/g, '');
            if(!textInside.includes("W"))
            {
                totalResponses--;
                $(this).hide();
            }
        });
        $(".genContentRows:first-child").show();
        console.log(totalResponses);
        if(totalResponses == 0) {
            $(".filter_comments").show();

        }
    });
    /*Filtering by class day Thursday*/
    $("#class_h").click(function() {
        var totalResponses = 0;
        $(".filter_comments").hide();
        $(".genContentRows").each(function () {
            $(this).show();
            totalResponses++;
        });
        $(".genContentRows").each(function () {
            //var iterator = $.trim($(this).find("#off_counter").val());
            var textInside = $.trim($(':nth-child(3)', this).text())
            textInside = textInside.replace(/\s+/g, '');
            if(!textInside.includes("H"))
            {
                totalResponses--;
                $(this).hide();
            }
        });
        $(".genContentRows:first-child").show();
        console.log(totalResponses);
        if(totalResponses == 0) {
            $(".filter_comments").show();

        }
    });
    /*Filtering by class day Friday*/
    $("#class_f").click(function() {
        var totalResponses = 0;
        $(".filter_comments").hide();
        $(".genContentRows").each(function () {
            $(this).show();
            totalResponses++;
        });
        $(".genContentRows").each(function () {
            //var iterator = $.trim($(this).find("#off_counter").val());
            var textInside = $.trim($(':nth-child(3)', this).text())
            textInside = textInside.replace(/\s+/g, '');
            if(!textInside.includes("F"))
            {
                totalResponses--;
                $(this).hide();
            }
        });
        if(totalResponses == 1) {
            $(".filter_comments").show();

        }
    });
    /*Filtering by class day Saturday*/
    $("#class_f").click(function() {
        var totalResponses = 0;
        $(".filter_comments").hide();
        $(".genContentRows").each(function () {
            $(this).show();
            totalResponses++;
        });

        $(".genContentRows").each(function () {
            var textInside = $.trim($(':nth-child(3)', this).text())
            textInside = textInside.replace(/\s+/g, '');
            if(!textInside.includes("S"))
            {
                totalResponses--;
                $(this).hide();
            }
        });
        $(".genContentRows:first-child").show();
        console.log(totalResponses);
        if(totalResponses == 0) {
            $(".filter_comments").show();

        }
    });
    $("#button_submit_modifyOffering").click(function() {
        showCourses();
        });

    /* Respawn list of courses*/
    function showCourses(){
        var str = $("#offerModifyForm").serialize();
        alert("offer modify working");
        $.ajax({
            type:"post",
            data:str,
            url:"localhost/apo/modifyOffering",
            async: false,
            dataType: "json",
            success:
            /* function(data){
                $.each(response.list, function(index, value){
                    // do whatever you want with your data
                    ;
                });*/
                function(data){
                    $.each(data.list, function(index, value){
                        // do whatever you want with your data
                        console.log("oh no");
                        console.log(data[index]);
                        console.log(data[index]);
                    });
            },
            error:function(data){
                alert("Error: Course Modification failed");
            }
        });

    }
    $("#select_day1").change(function() {
        console.log("change in value detected")
        var currDay1 = $(this).val();
        console.log(currDay1)
        $("#select_day2  option").each(function() {
            console.log($(this).text)
            if($(this).val() == currDay1)
                $(this).hide()
        });
    });
    $("#select_day2").change(function() {
        console.log("change in value detected")
        var currDay2 = $(this).val();
        console.log(currDay1)
        $("#select_day1  option").each(function() {
            console.log($(this).text)
            if($(this).val() == currDay2)
                $(this).hide()
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

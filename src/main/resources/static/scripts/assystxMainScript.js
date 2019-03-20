$(function()
{
    /* Add Div for Dialog/Modal */
    jQuery('<div/>', {
        id: 'div_dialog',
    }).appendTo('body');
    $(".modify_sidebar").hide();

    //Sorting the days

    $(".genContentRows").each(function () {
            var textInside = $.trim($(':nth-child(3)', this).text())
            textInside = textInside.replace(/\s+/g, '');//remove all spaces in the string
            var textDays  = orderDays(textInside);
            //console.log(textDays)
            $('#p_day1', this).text(textDays);
            $('#p_day2', this).hide();

    });

    /* Function to reconstruct the string */
    function orderDays(days)
    {
        var schoolDays = ['M', 'T', 'W', 'H', 'F', 'S', '-'];
        days = find_unique_characters(days);
        var newDays = "";

        for (var x = 0; x < days.length; x++)
            if(x < days.length-1)
                if(schoolDays.indexOf(days.charAt(x)) > schoolDays.indexOf(days.charAt(x+1)))
                    newDays = days.charAt(x + 1) + " " + days.charAt(x);
        if(newDays == "")
            return days.replace(/\s+/g, '');
        else
            return newDays.replace(/\s+/g, '');

    }

    function find_unique_characters(str) {
        var unique = '';
        for (var i = 0; i < str.length; i++) {
            if(unique.indexOf(str.charAt(i))==-1)
                unique += str[i];
        }
        return unique;
    }

    /* Selecting an offering */
    $(".cwOfferings .generatedContent .genContentRowss:not(:first-child)").click(function(){

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

    /* Assign New Room and Close Modal
    $(".assignRoomBtns").submit(function() {
       var roomCode = $(this).parent().parent().find(".available-room-code").text();
       console.log("Selected room code = " + roomCode);
       $("#text_room").val(roomCode);
       $("#modalAssignRoom").dialog("close");
    }); */

    function roomSelect() {
        var roomCode = $(this).attr("value");
        $("#text_room").val(roomCode);
        $("#modalAssignRoom").dialog("close");
    }//$(".assign_modal_buttons assignRoomBtns").click();

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
            width:800,
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



    //$("#button_submit_modifyOffering").click(function() {
    //    showCourses();
    //    });

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
                    /*
                    $.each(data.list, function(index, value){
                        // do whatever you want with your data
                        console.log("oh no");
                        console.log(data[index]);
                        console.log(data[index]);
                    });
                    */
                    alert(data)
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

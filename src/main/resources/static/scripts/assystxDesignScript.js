/**
 *
 * THIS SCRIPT IS FOR
 * ENHANCING THE USER INTERFACE
 * OF THE ASSYSTX SYSTEM.
 *
 */

$(function()
{
    /**
     *
     * SELECTING A
     * COURSE OFFERING
     *
     */
    $(".cwOfferings .generatedContent").on("click", ".genContentRows:not(:first-child)", function()
    {
        /* Check if the offering is selected already; if so, unselect it */
        if ($(this).css("background-color") === "rgb(60, 184, 120)")
        {
            /* Modify course offering row appearance */
            $(".modify_sidebar").find("input:text").val("");
            $(".modify_sidebar").hide();
            $(this).removeClass("selectedOffering");
            $(this).css({'background-color' : '#e2e2e2'});
        }
        /* If not, select it */
        else if ($(this).css("background-color") === "rgb(226, 226, 226)")
        {
            /* Modify course offering row appearance */
            $(".modify_sidebar").find("input:text").val("");
            $("#modify_offering_message").text("");
            $(".cwOfferings .generatedContent .genContentRows:not(:first-child)").removeClass("selectedOffering");
            $(".cwOfferings .generatedContent .genContentRows:not(:first-child)").css({'background-color' : '#e2e2e2'});
            $(this).css({'background-color' : '#3cb878'});
            $(this).addClass("selectedOffering");

            /* Perform AJAX */
            $.ajax({
                type : "POST",
                contentType : 'application/json',
                url : window.location + "/find-offering",
                data : $(this).find(".cols-offid").val(),
                dataType : 'json',
                success : function(result)
                {
                    if(result.status == "Done")
                    {
                        /* Put the values into the right sidebar */
                        $("#select_right_class_type").val(result.data.classStatus); /* Class Type */
                        $("#text_section").val(result.data.classSection); /* Class Section */

                        $("#select_right_start_timeblock").val(result.data.startTime); /* Start Time */
                        $("#select_right_end_timeblock").val(result.data.endTime); /* End Time */

                        $("#startTimeHolder").val(result.data.startTime); /* Start Time */
                        $("#endTimeHolder").val(result.data.endTime); /* End Time */

                        $("#select_day1").val(result.data.day1); /* Class Day 1 */
                        $("#select_day2").val(result.data.day2); /* Class Day 2 */
                        $("#text_room").val(result.data.roomCode); /* Room Code */
                        $("#select_faculty").val(result.data.faculty); /* Faculty Name */
                        $("#text_offId").val(result.data.offeringId); /* Offering ID */

                        /* Show Sidebar */
                        $(".modify_sidebar").show();
                    }
                },
                error : function(e)
                {
                    console.log("ERROR: ", e);
                }
            });
        }
    });

    /* Selecting a Revision in Revision History */
    $("#revisionWrapper").on("click", ".revision_holder", function()
    {
        if ($(this).hasClass("selectedRevision"))
        {
            $(".collabWorkspace .generatedContent .genContentRows:not(:first-child)").remove();
            $(this).removeClass("selectedRevision");
            $(this).css({'background-color' : '#ffffff'});
        }
        else
        {
            $(".collabWorkspace .generatedContent .genContentRows:not(:first-child)").removeClass("selectedRevision");
            $(".cwOfferings .generatedContent .genContentRows:not(:first-child)").css({'background-color' : '#ffffff'});
            $(this).css({'background-color' : '#3cb878'});
            $(this).addClass("selectedRevision");

            /* Perform AJAX */
            $.ajax({
                type : "POST",
                contentType : 'application/json',
                url : window.location + "/find-revision",
                data : $(this).find(".rev-his-id").val(),
                dataType : 'json',
                success : function(result)
                {
                    if(result.status == "Done")
                    {
                        alert("Course Section = " + result.data.classSection);
                    }
                },
                error : function(e)
                {
                    console.log("ERROR: ", e);
                }
            });
        }
    });

    /* Select a Load Row*/
    $(".cwFacultyLoad .generatedContent").on("click", ".genContentRows:not(:first-child)", function()
        //$(".cwOfferings .generatedContent .genContentRows:not(:first-child)").click(function()
    {
        /* Check if the offering is selected already; if so, unselect it */
        if ($(this).css("background-color") === "rgb(60, 184, 120)")
        {
            /* Modify course offering row appearance */
            $(".modify_sidebar").find("input:text").val("");
            $(".modify_sidebar").hide();
            $(this).removeClass("selectedOffering");
            $(this).css({'background-color' : '#e2e2e2'});
        }
        /* If not, select it */
        else if ($(this).css("background-color") === "rgb(226, 226, 226)")
        {
            /* Modify course offering row appearance */
            //$(".modify_sidebar").find("input:text").val("");
            $(".cwFacultyLoad .generatedContent .genContentRows:not(:first-child)").removeClass("selectedOffering");
            $(".cwFacultyLoad .generatedContent .genContentRows:not(:first-child)").css({'background-color' : '#e2e2e2'});
            $(this).css({'background-color' : '#3cb878'});
            $(this).addClass("selectedOffering");

            //console.log($(this).find(".cols-offid").val());
            /* Perform AJAX */
            var courseTraverse = $(':first-child', this).text();
            console.log(courseTraverse);
            $.ajax({
                type : "POST",
                contentType : 'application/json',
                url : window.location + "/person-faculty-load",
                data : courseTraverse,
                dataType : 'json',
                success : function(result)
                {
                    if(result.status == "Done")
                    {
                        /* Remove All The Previous Offerings */
                        $(".generatedFacultyLoadTable .generatedLoad .genLoadRows:not(:first-child)").remove();

                        /* For Each Offering */
                        $.each(result.data, function(i, offering)
                        {
                        /* Put the values into the right sidebar */
                        var courseCode = "<div class='genLoadCols'>" + offering.courseCode + "</div>";
                        var section = "<div class='genLoadCols'>" + offering.classSection + "</div>";
                        var newDays = orderDays(offering.day1 + offering.day2);
                        var days = (offering.day1 != '-') ? "<div class='genLoadCols'>" + newDays + "</div>"
                            : "<div class='genLoadCols'>None</div>";
                        var time = (offering.startTime != ':') ? "<div class='genLoadCols'>" + offering.startTime + " - " + offering.endTime + "</div>"
                            : "<div class='genContentCols'>Unassigned</div>";
                        var room = "<div class='genContentCols'>" + offering.roomCode + "</div>";

                        var offeringRow = "<div class='genLoadRows'>" +
                            "" + courseCode + section + days + time + room +
                            "</div>";
                        console.log("Here with me " + offering.courseCode + offering.classSection + offering.day1 + offering.day2 + offering.startTime + offering.endTime + offering.roomCode)
                        /* Add to UI */
                        $(".generatedFacultyLoadTable .generatedLoad").append(offeringRow);
                        /* Show Sidebar */
                        $(".modify_sidebar").show();
                        });
                    }
                },
                error : function(e)
                {
                    alert("Error!");
                    console.log("ERROR: ", e);
                }
            });
        }
    });
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
    function checkFilters(){
        if($("#select_view_offerings").val() != "All" ||  $("#select_left_class_type").val() != "All" || $("#select_room_type").val() != "All" || $("#select_left_timeblock").val() != "All")
            return true;
        return false;
    }
    function find_unique_characters(str) {
        var unique = '';
        for (var i = 0; i < str.length; i++) {
            if(unique.indexOf(str.charAt(i))==-1)
                unique += str[i];
        }
        return unique;
    }

    /**
     *
     * SELECTING A
     * COURSE OFFERING
     *
     */
});
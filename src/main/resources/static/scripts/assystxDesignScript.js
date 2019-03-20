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
    //$(".cwOfferings .generatedContent .genContentRows:not(:first-child)").click(function()
    {
        console.log("It's Going In");
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
            $(".cwOfferings .generatedContent .genContentRows:not(:first-child)").removeClass("selectedOffering");
            $(".cwOfferings .generatedContent .genContentRows:not(:first-child)").css({'background-color' : '#e2e2e2'});
            $(this).css({'background-color' : '#3cb878'});
            $(this).addClass("selectedOffering");

            console.log($(this).find(".cols-offid").val());
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
                       // console.log(result.data.startTime + result.data.endTime);
                        $("#select_right_start_timeblock").val(result.data.startTime); /* Start Time */
                        $("#select_right_end_timeblock").val(result.data.endTime); /* End Time */
                        console.log("The start timeblock" + result.data.startTime);
                        console.log("The end timeblock" + result.data.endTime);
                        $("#startTimeHolder").val(result.data.startTime); /* Start Time */
                        $("#endTimeHolder").val(result.data.endTime); /* End Time */
                        console.log("The start timeblock" + $("#startTimeHolder").val());
                        console.log("The end timeblock" + $("#endTimeHolder").val());
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
                    alert("Error!");
                    console.log("ERROR: ", e);
                }
            });
        }
    });
});
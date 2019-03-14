/**
 *
 * THIS SCRIPT IS FOR
 * THE COURSE OFFERING MODULE.
 * SPECIFICALLY, IT HANDLES
 * AJAX REQUESTS FOR THE MODULE.
 *
 */

$(function()
{
    showOfferings();

    /* Load All Offerings GET Form AJAX */

    setInterval(function(){
        showOfferings();
    }, 15000);

    /**
     *
     * POST FORMS
     *
     */

    /* Modify Offering POST Form Ajax */
    $("#modify_offering_form").submit(function(e)
    {
        e.preventDefault();

        /* Prepare Form Data */
        var formData = {
            classStatus : $("#select_right_class_type").val(),
            classSection : $("#text_section").val(),
            startTime : $("#select_right_start_timeblock").val(),
            endTime : $("#select_right_end_timeblock").val(),
            day1 : $("#select_day1").val(),
            day2 : $("#select_day2").val(),
            roomCode : $("#text_room").val(),
            faculty : $("#select_faculty").val(),
            offeringId : $("#text_offId").val()
        };

        /* Perform AJAX */
        $.ajax({
            type: 'POST',
            contentType : 'application/json',
            url : window.location + "/modify-offering",
            data : JSON.stringify(formData),
            dataType : 'json',
            success : function(result)
            {
                if(result.status == "Done") {
                    $("#area_concerns").text("Course Offering modified successfully!");

                    /* Refresh Course Offerings */
                    showOfferings();
                }
            },
            error : function(e)
            {
                alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    });

    /**
     *
     * FUNCTIONS
     *
     */

    /* Retrieve All Course Offerings GET Ajax */
    function showOfferings()
    {
        /* Perform AJAX */
        $.ajax({
            type : "GET",
            url : window.location + "/show-offerings",
            success : function(result)
            {
                if(result.status == "Done")
                {
                    /* Keep Track of Selected Offering */
                    var selOffering = $(".selectedOffering").find(".cols-offid").val();
                    console.log("Selected Offering = " + selOffering + " type = " + typeof selOffering);

                    /* Remove All The Previous Offerings */
                    $(".cwofferings .generatedContent .genContentRows:not(:first-child)").remove();

                    /* For Each Offering */
                    $.each(result.data, function(i, offering)
                    {
                        /* Create Divs */
                        var courseCode = "<div class='genContentCols cols-course-code'>" + offering.courseCode + "</div>";
                        var section = "<div class='genContentCols cols-section'>" + offering.classSection + "</div>";
                        var days = (offering.day1 != '-') ? "<div class='genContentCols cols-days'>" + offering.day1 + " " + offering.day2 + "</div>"
                                                          : "<div class='genContentCols cols-days'>None</div>";
                        var time = (offering.startTime != ':') ? "<div class='genContentCols cols-timeslot'>" + offering.startTime + "-" + offering.endTime + "</div>"
                                                               : "<div class='genContentCols cols-timeslot'>Unassigned</div>";
                        var room = "<div class='genContentCols cols-room-code'>" + offering.roomCode + "</div>";
                        var faculty = "<div class='genContentCols cols-faculty'>" + offering.faculty + "</div>";
                        var offerid = "<input class='cols-offid' type='hidden' value='" + offering.offeringId + "'/>";

                        var offeringRow = "<div class='genContentRows'>" +
                            "" + courseCode + section + days + time + room + faculty + offerid +
                            "</div>";

                        /* Add to UI */
                        $(".cwofferings .generatedContent").append(offeringRow);

                        /* Optional: if selected offering, add class */
                        console.log("Sel = " + offering.offeringId + " type = " + typeof offering.offeringId);
                        if(offering.offeringId == parseInt(selOffering)) {
                            $(".cwOfferings .generatedContent .genContentRows:last-child").addClass("selectedOffering");
                            $(".cwOfferings .generatedContent .genContentRows:last-child").css({'background-color' : '#3cb878'});
                        }
                    });
                }
            },
            error : function(e)
            {
                //alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    }
});
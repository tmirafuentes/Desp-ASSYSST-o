$(function()
{
    /**
     *
     * GET FORMS
     *
     */

    /* Load All Offerings GET Form AJAX */
    setInterval(function(){
        showOfferings();
    }, 5000);

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
                    /* Remove All The Previous Offerings */
                    $(".cwofferings .generatedContent .genContentRows:not(:first-child)").remove();

                    /* For Each Offering */
                    $.each(result.data, function(i, offering)
                    {
                        /* Create Divs */
                        var courseCode = "<div class='genContentCols'>" + offering.courseCode + "</div>";
                        var section = "<div class='genContentCols'>" + offering.classSection + "</div>";
                        var days = (offering.day1 != '-') ? "<div class='genContentCols'>" + offering.day1 + " " + offering.day2 + "</div>"
                                                          : "<div class='genContentCols'>None</div>";
                        var time = (offering.startTime != ':') ? "<div class='genContentCols'>" + offering.startTime + "-" + offering.endTime + "</div>"
                                                               : "<div class='genContentCols'>Unassigned</div>";
                        var room = "<div class='genContentCols'>" + offering.roomCode + "</div>";
                        var faculty = "<div class='genContentCols'>" + offering.faculty + "</div>";

                        var offeringRow = "<div class='genContentRows'>" +
                            "" + courseCode + section + days + time + room + faculty +
                            "</div>";

                        /* Add to UI */
                        //$(offeringRow).append(courseCode, section, days, time, room, faculty);
                        console.log(offeringRow);
                        $(".cwofferings .generatedContent").append(offeringRow);

                        console.log("It's done mah n-word");
                    });
                    alert("It's done mah n-word");
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
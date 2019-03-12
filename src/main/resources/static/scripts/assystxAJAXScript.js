$(function() {
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
            success : function(result) {
                if(result.status == "Done") {
                    $("#area_concerns").text("Course Offering modified successfully!");
                }
            },
            error : function(e) {
                alert("Error!");
                console.log("ERROR: ", e);
            }
        })
    })

});
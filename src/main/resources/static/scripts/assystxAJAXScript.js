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
    //showOfferings();

    /* Load All Offerings GET Form AJAX

    setInterval(function(){
        console.log("Updating the System")
        showOfferings();
    }, 15000);*/

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
            startTime : $("#startTimeHolder").val(),
            endTime : $("#endTimeHolder").val(),
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
                    showOfferingsAJAX();
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

    function showOfferingsAJAX()
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
                    //console.log("Selected Offering = " + selOffering + " type = " + typeof selOffering);

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
    //On Click Room Modification Button: For Room Checking
    $("#modOffRoomButton").click(function() {
        var formData = {
            day1 : $("#select_day1").val(),
            day2 : $("#select_day2").val(),
            startTime : $("#startTimeHolder").val().replace(':', ''),
            endTime : $("#endTimeHolder").val().replace(':', '')
        };
        //console.log($("#startTimeHolder").val().replace(':', '') + " " + $("#endTimeHolder").val().replace(':', ''))
        /* Perform AJAX */
        $.ajax({
            type: 'POST',
            contentType : 'application/json',
            url : window.location + "/check-rooms",
            data : JSON.stringify(formData),
            dataType : 'json',
            success : function(result)
            {
                if(result.status == "Done") {
                    /* Remove All The Previous Rooms */
                    $("#modal_table_room tr:not(:first-child)").remove();
                    //onsole.log("Umaabot dito");
                    /* For Each Offering */
                    $.each(result.data, function(i, room)
                    {
                        var buttonVal = room.roomCode;
                        var roomCode = "<tr class='available-room'><td class='available-room-code'>" + room.roomCode + "</td>";
                        var roomType = "<td class='available-room-type'>" + room.roomType + "</td>";
                        var roomBuilding = "<td class='available-room-bldg'>" + room.building + "</td>";
                        var roomCapacity = "<td class='available-room-capacity'>" + room.capacity + "</td>";
                        var buttonRoom = "<td><button class='assign_modal_buttons assignRoomBtns' value='" + room.roomCode + "' type='button'>Assign</button></td></tr>"

                        var roomRow = roomCode + roomRow + roomBuilding + roomCapacity + roomType + buttonRoom;
                        /* Add to UI */
                        $("#modal_table_room").append(roomRow);


                    });
                    function roomSelect() {
                        var roomCode = $(this).attr("value");
                        $("#text_room").val(roomCode);
                        $("#modalAssignRoom").dialog("close");
                    }

                    var roomColumns = document.getElementsByClassName("assign_modal_buttons assignRoomBtns");
                    for (var i = 0; i < roomColumns.length; i++) {
                        var room = roomColumns[i];
                        room.onclick = roomSelect;
                    }
                }
            },
            error : function(e)
            {
                alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    });
    $("#modOffFacultyButton").click(function() {
        var formData = {
            day1 : $("#select_day1").val(),
            day2 : $("#select_day2").val(),
            startTime : $("#startTimeHolder").val().replace(':', ''),
            endTime : $("#endTimeHolder").val().replace(':', '')
        };
        console.log("going in");
        /* Perform AJAX */
        $.ajax({
            type: 'POST',
            contentType : 'application/json',
            url : window.location + "/check-faculty",
            data : JSON.stringify(formData),
            dataType : 'json',
            success : function(result)
            {
                if(result.status == "Done") {
                    /* Remove All The Previous Rooms */
                    $("#modal_table_assign_faculty tr:not(:first-child)").remove();

                    $.each(result.data, function(i, facultyLoad)
                    {
                        var buttonVal = facultyLoad.lastName + ", "  + facultyLoad.firstName;
                        var lastName = "<tr><td>" + facultyLoad.lastName + "</td>";
                        var firstName = "<td>" + facultyLoad.firstName + "</td>";
                        var teachingLoad = "<td>" + facultyLoad.teachingLoad + "</td>";
                        var adminLoad = "<td>" + facultyLoad.adminLoad + "</td>";
                        var researchLoad = "<td>" + facultyLoad.researchLoad + "</td>";
                        var totalLoad = "<td>" + facultyLoad.totalLoad + "</td>";
                        var buttonFaculty = "<td><button class='add_modal_buttons assignFacultyBtns' type='button' value='" + buttonVal + "'>Assign</button></td></tr>"
                        var facultyRow = lastName + firstName + teachingLoad + adminLoad + researchLoad + totalLoad + buttonFaculty;
                        /* Add to UI */
                        $("#modal_table_assign_faculty").append(facultyRow);
                });

                    function facultySelect() {
                        var facultyCode = $(this).attr("value");
                        $("#select_faculty").val(facultyCode);
                        $("#modalAssignFaculty").dialog("close");
                    }

                    var facultyCols = document.getElementsByClassName("add_modal_buttons assignFacultyBtns");
                    for (var i = 0; i < facultyCols.length; i++) {
                        var faculty = facultyCols[i];
                        faculty.onclick = facultySelect;
                    }
                }

            },
            error : function(e)
            {
                alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    });
    /* CONCERNS Part*/
    $("#button_concerns").click(function() {
        showConcernsAJAX();
    });

    $("#button-concern-compose").click(function() {
        $("#concerns_list").hide();
        $("#concern_compose").show();

    });
    $("#button-concern-threads").click(function() {
    $("#concerns_list").show();
    $("#concern_compose").hide();

    });
    $("#compose_submit").click(function() {
        alert(findUser());
    });
    function showConcernsAJAX()
    {
        var tobeSearched = $("#input_userID").val();
        console.log(tobeSearched)
        /* Perform AJAX */
        $.ajax({
            type : "POST",
            url : window.location + "/get-concerns",
            contentType : 'application/json',
            data : JSON.stringify(tobeSearched),
            success : function(result)
            {
                if(result.status == "Done")
                {
                    $.each(result.data, function(i, concern_response) {
                        var senderName = concern_response.senderFirstName + " " + concern_response.senderLastName;
                        var header = "<table class='concern_entry'>"
                        var name = "<tr><td class ='concern_name'>" + senderName + "</td></tr>"
                        var concerm_proper = "<tr> <td colspan='2' class ='concern_message'>" + concern_response.message + "</td></tr></table>"
                        var newConcern = header + name + concerm_proper;

                        $("#concerns_list").append(newConcern);
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
    function sendConcern()
    {
        var tobeSearched = $("#concern_receiver").val();
        $.ajax({
            type : "POST",
            url : window.location + "/find-user",
            contentType : 'application/json',
            data : JSON.stringify(tobeSearched),
            success : function(result)
            {
                if(result.status == "Done") {
                    $.each(result.data, function(i, concern_ID) {
                        console.log("HERE" + concern_ID)
                        return concern_ID;
                    });

                }
            },
            error : function(e)
            {
                console.log("ERROR: ", e);
            }
        });
    }

});
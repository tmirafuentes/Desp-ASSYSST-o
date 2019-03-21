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
});
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
    getConcernNotifications();
    setInterval(function(){
        getConcernNotifications();
    }, 15000);
    /**
     *
     * POST FORMS
     *
     */

    function getConcernNotifications()
    {
        var tobeSearched = $("#input_userID").val();
       // console.log(tobeSearched)
        /* Perform AJAX */
        $.ajax({
            type : "POST",
            url : window.location + "/get-notifications",
            contentType : 'application/json',
            data :tobeSearched,
            success : function(result)
            {
                if(result.status == "Done")
                {
                   // console.log(console.data);
                    if(result.data > 0)
                    {
                        $("#notification_pop").show();
                        $("#notification_pop").html(result.data);
                    }
                    else
                        $("#notification_pop").hide();

                }
            },
            error : function(e)
            {
                //alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    }

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
        if(formData.classStatus != "" && formData.classSection != "" && formData.startTime != "" && formData.endTime != "" && formData.day1 != ""
        && formData.day2 != "" && formData.roomCode != "" && formData.faculty != "" && formData.offeringId != "")
        {
          /* Perform AJAX */
          $.ajax({
              type: 'POST',
              contentType : 'application/json',
              url : window.location + "/modify-offering",
              data : JSON.stringify(formData),
              dataType : 'json',
              beforeSend: function()
              {
                  $("#modify_offering_message").text("Course offering to be updated.");
              },
              success : function(result) {
                  if (result.status == "Done") {
                      $("#modify_offering_message").text("Course offering modified successfully!");
                  }
              }
          });
        }
        else{
            alert("There are blank Values specified in the form. Please fill them up first before proceeding.")
        }

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
            startTime : $("#startTimeHolder").val().replace(/:/g,''),
            endTime : $("#endTimeHolder").val().replace(/:/g,'')
        };
        //console.log($("#startTimeHolder").val().replace(':', '') + " " + $("#endTimeHolder").val().replace(':', ''))
        /* Perform AJAX */
        $.ajax({
            type: 'POST',
            contentType : 'application/json',
            url : window.location + "/check-rooms",
            data : JSON.stringify(formData),
            dataType : 'json',
            beforeSend : function()
            {
                $("#modify_offering_message").text("Course offering is being modified...");
            },
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
       // console.log("going in");
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
        $("#concerns_list table").remove();
        $("#button-concern-threads").css({'background-color' : '#3cb878'});
    });

    $("#button-concern-compose").click(function() {
        $("#concerns_list").hide();
        $("#concern_compose").show();
        $(this).css({'background-color' : '#3cb878'});
        $("#button-concern-threads").css({'background-color' : '#e2e2e2'});
    });

    $("#button-concern-threads").click(function() {
        $(this).css({'background-color' : '#3cb878'});
        $("#concerns_list table").remove();
        $("#button-concern-compose").css({'background-color' : '#e2e2e2'});
        $("#concerns_list").show();
        $("#concern_compose").hide();
        showConcernsAJAX();

    });
    $("#compose_submit").click(function() {
        sendConcern();
    });

    function showConcernsAJAX()
    {
        var tobeSearched = $("#input_userID").val();
        /* Perform AJAX */
        $.ajax({
            type : "POST",
            url : window.location + "/get-concerns",
            contentType : 'application/json',
            data :tobeSearched,
            success : function(result)
            {
                if(result.status == "Done")
                {
                    $.each(result.data, function(i, concern_response) {

                        var header = "<table class='concern_entry'>"
                        var name = "<tr><td class ='concern_name'>" + concern_response.senderName + "</td></tr>"
                        var date = "<tr><td class ='concern_time'>" + concern_response.dateAdded + "</td></tr>"
                        var concerm_proper = "<tr> <td colspan='2' class ='concern_message'>" + concern_response.message + "</td></tr></table>"
                        var newConcern = header + name + date + concerm_proper;

                        $("#concerns_list").prepend(newConcern);
                    });
                    $("#notification_pop").hide();
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
        //console.log("Location: " + window.location);

        var formData = {
            userId : $("#input_userID").val(),
            senderName : $("#concern_receiver").val(),
            message : $("#concern_content").val()
        };
        //console.log(formData.userId)
        $.ajax({
            type : "POST",
            url : window.location + "/post-concern",
            contentType : 'application/json',
            data : JSON.stringify(formData),
            dataType : 'json',
            success : function(result)
            {
                if(result.status == "Done") {
                    alert("Successfully Sent Concern")

                }
            },
            error : function(e)
            {
                console.log("ERROR: ", e);
                alert("ERROR: Concern not sent!")
            }
        });
    }
    /* Adding new Course Offerings */
    $(".add_offer_btns").click(function() {
        var courseCode = $(this).attr("value");

        $.ajax({
            type : "POST",
            url : window.location + "/add-course-offering",
            contentType : 'application/json',
            data : JSON.stringify(courseCode),
            dataType : 'json',
            success : function(result)
            {
                if(result.status == "Done") {
                    alert("Successfully Added New Course Offering")
                }
            },
            error : function(e)
            {
                console.log("ERROR: ", e);
                alert("ERROR: Concern not sent!")
            }
        });

    });
});
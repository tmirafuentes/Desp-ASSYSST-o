/*
 *
 *  ASSYSTX2 SCRIPT
 *  FOR ASSIGN ROOM
 *
 *  This script is for the assign room and timeslot
 *  page of the ASSYSTX2 system.
 *
 */

$(function()
{
   /*
    *
    *   ONLOAD FUNCTIONS
    *   AND VARIABLES
    *
    */
    retrieveBuildingNames();

    /* Day Number */
    var dayNumber = 1;

    /*
     *
     *  EVENT LISTENERS
     *
     */

    /*  This event listener retrieves
     *  the rooms of each building when
     *  a building is selected.
    */
    $("#assign-room-building-menu").on('change', function()
    {
        /* Check if it isn't null value */
        if(this.value != "-") {


            /* Retrieve room names based on selected building */
            retrieveRoomNames(this.value);

            /* Check if timeslot has been selected */
            if ($("#assign-room-timeslot-menu").val() !== "-")
            {
                var bldgCode = this.value;
                displayOccupiedSlotsTable($("#assign-room-timeslot-menu").val(), bldgCode);
            }
        }
        else
        {
            /* Reset room menu */
            //$("#assign-room-room-menu option").slice(1).remove();

            /* Remove room row in confirm table */
            $(".confirm-table-row").remove();

            /* Remove assign room table */
            $("#room-assign-table").remove();

            $("#room-assign-unavailable").hide();
        }
    });

    /*  This event listener retrieves
     *  the available rooms when a
     *  timeslot is selected.
    */
    $("#assign-room-timeslot-menu").on('change', function()
    {
       /* Check if it isn't null value */
       if(this.value != "-")
       {
           /* Retrieve All Days per Room and reflect it in the table */
           var bldgCode = $("#assign-room-building-menu").val();
           displayOccupiedSlotsTable(this.value, bldgCode);
           $("#room-assign-unavailable").hide();
       }
       else
       {
           /* Make every cell considered unavailable */
           $(".room-table-cells").text("");
           $(".room-table-cells").removeClass("room-table-unavailable", "room-table-selected");
           $(".room-table-cells").addClass("room-table-available");

           /* Show unavailable layer */
           var tableHeight = $("#room-assign-table").height(),
               tableWidth = $("#room-assign-table").width(),
               tablePosition = $("#room-assign-table").offset();
           $("#room-assign-unavailable").css("height", tableHeight);
           $("#room-assign-unavailable").css("width", tableWidth);
           $("#room-assign-unavailable").css(tablePosition);
           $("#room-assign-unavailable-message").css("vertical-align", "middle");
           $("#room-assign-unavailable").show();
       }
    });

    /*  This event listener selects
     *  the clicked cell in the
     *  assign room table.
    */
    $("#assign-room-table-box").on('click', ".room-table-available", function()
    {
        /*  Make cell look selected and
         *  update confirm table about the
         *  selected cells.
         */
        if($(this).hasClass('room-table-selected'))
        {
            /* Deselect the cell */
            $(this).removeClass('room-table-selected');

            /* Retrieve the time and day values from the cell */
            var room = $(this).closest("tr").data('room-code');
            var cell_day = $(this).data('day-letter');

            if ($(".confirm-table-row[data-cell-room='" + room + "'][data-cell-day='" + cell_day + "']").length)
            {
                /* There are no more selected cells */
                $(".confirm-table-row[data-cell-room='" + room + "'][data-cell-day='" + cell_day + "']").remove();
                dayNumber--;
            }

            /* Compute hours
            var numHours = $("#assign-room-offering-hours").val();
            var selHours = $("#assign-room-timeslot-menu").find("option:selected").closest("optgroup").attr("label").split(" ");
            var hoursDiff = numHours + Number(selHours[0]);
            $("#assign-room-offering-hours").val(hoursDiff);

            if(hoursDiff > 0)
                $(".room-table-unavailable").addClass("room-table-available").removeClass("room-table-unavailable"); */
        }
        else
        {
            /* Select the cell */
            $(this).addClass('room-table-selected');

            /* Retrieve the time and day values from the cell */
            var timeslot = $("#assign-room-timeslot-menu").val();
            var day = $(this).data('day-letter');
            var room = $(this).closest("tr").data('room-code');

            /* Update confirm table */
            var assignedSlot = room + " " + day + " " + timeslot;
            var colLabel = "Day " + dayNumber;
            if($("#confirm-table-assigned-row").length)
                colLabel = "New " + colLabel;
            dayNumber++;
            var dayRow = createConfirmTableRow(colLabel, assignedSlot, "confirm-table-day-row", day, room);
            $(dayRow).insertBefore("#confirm-table-button-row");

            /* Compute hours
            var numHours = $("#assign-room-offering-hours").val();
            var selHours = $("#assign-room-timeslot-menu").find("option:selected").closest("optgroup").attr("label").split(" ");
            var hoursDiff = numHours - Number(selHours[0]);
            $("#assign-room-offering-hours").val(hoursDiff);

            if(hoursDiff === 0)
                $(".room-table-available").addClass("room-table-unavailable").removeClass("room-table-available"); */

            console.log("Num Hours = " + numHours + "; SelHours = " + selHours + "; HoursDiff = " + hoursDiff);
        }
    });

    /*  This event listener submits
     *  the form for assigning a room.
     */
    $("#assign-room-submit").click(function()
    {
        if ($(".confirm-table-row:contains('Day')").length)
        {
            var formData = {};
            var numDays = 1;

            /* Get Rows that contains Days */
            $(".confirm-table-row:contains('Day')").each(function(i)
            {
                /* Parse String */
                var codeStr = $(this).children(":nth-child(2)").text();
                var codeStrArray = codeStr.split(" ");
                var timeslot = codeStrArray[2].split("-");

                /* Put into Form Data */
                formData["roomCode" + numDays] = codeStrArray[0];
                formData["day" + numDays] = codeStrArray[1];
                formData["startTimeDay" + numDays] = timeslot[0];
                formData["endTimeDay" + numDays] = timeslot[1];

                numDays++;
            });

            /* No second day because numDays didn't reach to 3 */
            if (numDays == 2)
            {
                formData["day" + numDays] = '-';
                formData["startTimeDay" + numDays] = null;
                formData["endTimeDay" + numDays] = null;
            }

            /* Get course code and section */
            var offering = $("#confirm-table-offering-row td:nth-child(2)").text();
            var offeringArray = offering.split(" ");

            /* Put into Form data */
            formData["courseCode"] = offeringArray[0];
            formData["section"] = offeringArray[1];

            /* Submit Form */
            $.ajax({
                type : "POST",
                contentType : "application/json",
                url : window.location + "/update-offering-room",
                data : JSON.stringify(formData),
                dataType : "json",
                success : function(result)
                {
                    if(result.status === "Done")
                    {
                        displayPositiveMessage(result.data);

                        setTimeout(function()
                        {
                            window.location.href = "/";
                        }, 1500);
                    }
                },
                error : function(e)
                {
                    displayNegativeMessage("There was an error while assigning the room.");
                }
            });
        }
    });

    /*
     *
     *  FUNCTION IMPLEMENTATIONS
     *
     */

    /*  This function retrieves all the
     *  building names from the database.
     */
    function retrieveBuildingNames()
    {
        $.ajax({
            type : "GET",
            url : window.location + "/retrieve-building-names",
            success : function(result)
            {
                if(result.status === "Done")
                {
                    $.each(result.data, function(i, building)
                    {
                        var buildingOption = "<option value='" +
                                             building.bldgCode +
                                             "'>" + building.bldgName +
                                             "</option>";
                        $("#assign-room-building-menu").append(buildingOption);
                    });
                }
            }
        });
    }

    /*  This function retrieves all the
     *  room names from the database
     *  based on the selected building.
    */
    function retrieveRoomNames(buildingCode)
    {
        var data = {
            buildingCode : buildingCode
        };

        $.ajax({
            type : "POST",
            contentType : "application/json",
            data : JSON.stringify(data),
            dataType : "json",
            url : window.location + "/retrieve-room-names",
            success : function(result)
            {
                if(result.status == "Done")
                {
                    /* If result is successful, go to room table */
                    createRoomTable(result.data);
                }
            }
        });
    }

    /*  This function retrieves all the occupied
     *  rooms within a given timeslot.
    */
    function displayOccupiedSlotsTable(timeslot, bldgCode)
    {
        /* Process data */
        var data = {
            timeslot : timeslot,
            bldgCode : bldgCode
        };

        $.ajax({
            type : "POST",
            contentType : "application/json",
            data : JSON.stringify(data),
            dataType : "json",
            url : window.location + "/retrieve-occupied-rooms",
            success : function(result)
            {
                if (result.status === "Done")
                {
                    $.each(result.data, function(i, room)
                    {
                        var roomCode = room.roomCode;

                        /* Find row with room code and fill with info */
                        $(".room-table-row[data-room-code='" + roomCode + "'] td:nth-child(2)").text(room.offDay1).removeClass("room-table-available").addClass("room-table-unavailable");
                        $(".room-table-row[data-room-code='" + roomCode + "'] td:nth-child(3)").text(room.offDay2).removeClass("room-table-available").addClass("room-table-unavailable");
                        $(".room-table-row[data-room-code='" + roomCode + "'] td:nth-child(4)").text(room.offDay3).removeClass("room-table-available").addClass("room-table-unavailable");
                        $(".room-table-row[data-room-code='" + roomCode + "'] td:nth-child(5)").text(room.offDay4).removeClass("room-table-available").addClass("room-table-unavailable");
                        $(".room-table-row[data-room-code='" + roomCode + "'] td:nth-child(6)").text(room.offDay5).removeClass("room-table-available").addClass("room-table-unavailable");
                        $(".room-table-row[data-room-code='" + roomCode + "'] td:nth-child(7)").text(room.offDay6).removeClass("room-table-available").addClass("room-table-unavailable");

                        /* Design appropriately the cells */
                        if(room.availDay1)
                            $(".room-table-row[data-room-code='" + roomCode + "'] td:nth-child(2)").removeClass("room-table-unavailable").addClass("room-table-available");
                        if(room.availDay2)
                            $(".room-table-row[data-room-code='" + roomCode + "'] td:nth-child(3)").removeClass("room-table-unavailable").addClass("room-table-available");
                        if(room.availDay3)
                            $(".room-table-row[data-room-code='" + roomCode + "'] td:nth-child(4)").removeClass("room-table-unavailable").addClass("room-table-available");
                        if(room.availDay4)
                            $(".room-table-row[data-room-code='" + roomCode + "'] td:nth-child(5)").removeClass("room-table-unavailable").addClass("room-table-available");
                        if(room.availDay5)
                            $(".room-table-row[data-room-code='" + roomCode + "'] td:nth-child(6)").removeClass("room-table-unavailable").addClass("room-table-available");
                        if(room.availDay6)
                            $(".room-table-row[data-room-code='" + roomCode + "'] td:nth-child(7)").removeClass("room-table-unavailable").addClass("room-table-available");
                    });
                }
            },
            error : function(e)
            {
                displayNegativeMessage("There is an error loading the offerings assigned in this room.");
            }
        })
    }

    /* Create Confirm Table Rows */
    function createConfirmTableRow(firstColLabel, secondColLabel, idName, dataDay, dataRoom)
    {
        var newRow = "<tr data-cell-day='" + dataDay +
                     "' data-cell-room='" + dataRoom +
                     "' class='confirm-table-row' id='" +
                     idName + "'>" +
                     "<td>" + firstColLabel + "</td>" +
                     "<td>" + secondColLabel +
                     "</td></tr>";

        return newRow;
    }

    /* Create the assign room table. */
    function createRoomTable(roomList)
    {
        /* Remove table if there is existing */
        if ($("#room-assign-table").length)
            $("#room-assign-table").remove();

        var roomTable = "<table id='room-assign-table'>" +
                        "<tr>" +
                        "<td class='room-table-header'>ROOMS</td>" +
                        "<td class='room-table-header'>MON</td>" +
                        "<td class='room-table-header'>TUE</td>" +
                        "<td class='room-table-header'>WED</td>" +
                        "<td class='room-table-header'>THU</td>" +
                        "<td class='room-table-header'>FRI</td>" +
                        "<td class='room-table-header'>SAT</td>" +
                        "</tr>";

        /* Add Rooms based on building */
        $.each(roomList, function(i, room)
        {
            roomTable += "<tr class='room-table-row' data-room-code='" + room.roomCode + "'>" +
                         "<td class='room-table-header'>" + room.roomCode + "</td>" +
                         "<td class='room-table-cells' data-day-letter='M'></td>" +
                         "<td class='room-table-cells' data-day-letter='T'></td>" +
                         "<td class='room-table-cells' data-day-letter='W'></td>" +
                         "<td class='room-table-cells' data-day-letter='H'></td>" +
                         "<td class='room-table-cells' data-day-letter='F'></td>" +
                         "<td class='room-table-cells' data-day-letter='S'></td>" +
                         "</tr>";
        });

        /* Close the room assignment table */
        roomTable += "</table>";

        $(roomTable).insertAfter("#assign-room-table-border");
        $(".room-table-cells").addClass("room-table-available");

        /* Show unavailable layer */
        var tableHeight = $("#room-assign-table").height(),
            tableWidth = $("#room-assign-table").width(),
            tablePosition = $("#room-assign-table").offset();
        $("#room-assign-unavailable").height(tableHeight);
        $("#room-assign-unavailable").width(tableWidth);
        $("#room-assign-unavailable").css(tablePosition);
        $("#room-assign-unavailable-message").css("vertical-align", "middle");
        $("#room-assign-unavailable").show();
    }

    /*  This function automates creating a table row
     *  for the timeslot table.
     */
    function assignRoomTableDayTime(hourCell, hourNumberStart, minNumberStart, hourNumberEnd, minNumberEnd)
    {
        var dayLetters = ["M", "T", "W", "H", "F", "S"];

        for(var j=0;j<6;j++)
        {
            hourCell += "<td class='room-table-cells' data-cell-start-min='" +
                timeFormatter(hourNumberStart, minNumberStart, false) +
                "' data-cell-end-min='" +
                timeFormatter(hourNumberEnd, minNumberEnd, false) +
                "' data-cell-day='" + dayLetters[j] + "'></td>";
        }

        return hourCell;
    }

    /* This function formats the time for the timeslot table. */
    function timeFormatter(hourNumber, minNumber, extendNum)
    {
        if(hourNumber < 10 && extendNum)
            return "0" + hourNumber + minNumber;

        return "" + hourNumber + minNumber;
    }

    /*
     *  FEEDBACK MESSAGES
     *  FUNCTION IMPLEMENTATIONS
     *
    */

    /*  This function creates a feedback
     *  message and displays it in the system.
     */
    function displayPositiveMessage(message)
    {
        /* Put message */
        $("#positive-feedback-message").text(message);

        /* Show feedback message */
        $("#positive-feedback-message").slideDown(500, function()
        {
            setTimeout(function()
                {
                    $("#positive-feedback-message").slideUp(500);
                },
                5000);
        });
    }

    function displayNegativeMessage(message)
    {
        /* Put message */
        $("#negative-feedback-message").text(message);

        /* Show feedback message */
        $("#negative-feedback-message").slideDown(500, function()
        {
            setTimeout(function()
                {
                    $("#negative-feedback-message").slideUp(500);
                },
                5000);
        });
    }
});
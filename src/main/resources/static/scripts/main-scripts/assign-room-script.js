/*
 *
 *  ASSYSTX2 SCRIPT
 *  FOR ASSIGN ROOM
 *
 *  This script is for the assign room and timeslot
 *  page of the ASSYSTX2 system.
 *
 */

$(function() {
   /*
    *
    *   ONLOAD FUNCTIONS
    *   AND VARIABLES
    *
    */
    retrieveBuildingNames();
    createTimeslotTable();

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
        if(this.value != "-")
        {
            /* Remove previous options */
            $("#assign-room-room-menu option").slice(1).remove();

            /* Remove existing confirm table rows */
            $(".confirm-table-row").remove();

            /* Retrieve room names based on selected building */
            retrieveRoomNames(this.value);

            /* Create Room row for confirm table */
            var roomRow = createConfirmTableRow("Room", "No Room Selected", "confirm-table-room-row");
            if($("#confirm-table-assigned-row").length)
                $(roomRow).insertAfter("#confirm-table-assigned-row");
            else
                $(roomRow).insertAfter("#confirm-table-offering-row");
        }
        else
        {
            /* Reset room menu */
            $("#assign-room-room-menu option").slice(1).remove();

            /* Remove room row in confirm table */
            $(".confirm-table-row").remove();

            /* Make every cell considered unavailable */
            $(".room-table-cells").text("");
            $(".room-table-cells").removeClass("room-table-available", "room-table-selected");
            $(".room-table-cells").addClass("room-table-unavailable");
        }
    });

    /*  This event listener retrieves
     *  the rooms of each building when
     *  a building is selected.
    */
    $("#assign-room-room-menu").on('change', function()
    {
       /* Check if it isn't null value */
       if(this.value != "-")
       {
           /* Remove existing confirm table rows */
           $(".confirm-table-row").remove();

           var roomColLabel = "Room";
           if ($("#confirm-table-assigned-row").length)
               roomColLabel = "New " + roomColLabel;

           /* Reflect changes in the confirm table */
           var roomRow = createConfirmTableRow(roomColLabel, this.value, "confirm-table-room-row");
           if($("#confirm-table-assigned-row").length)
                $(roomRow).insertAfter("#confirm-table-assigned-row");
           else
                $(roomRow).insertAfter("#confirm-table-offering-row");

           /* Make every cell considered available */
           $(".room-table-cells").text("");
           $(".room-table-cells").removeClass("room-table-unavailable", "room-table-selected");
           $(".room-table-cells").addClass("room-table-available");

           /* TODO: Retrieve All Days per Room and reflect it in the table */
           displayOccupiedSlotsTable(this.value);
       }
       else
       {
           /* Remove existing confirm table rows */
           $(".confirm-table-row").remove();

           var roomColLabel = "Room";
           if ($("#confirm-table-assigned-row").length)
               roomColLabel = "New " + roomColLabel;

           /* Update Room row in confirm table */
           var roomRow = createConfirmTableRow(roomColLabel, "No Room Selected", "confirm-table-room-row");
           if($("#confirm-table-assigned-row").length)
               $(roomRow).insertAfter("#confirm-table-assigned-row");
           else
               $(roomRow).insertAfter("#confirm-table-offering-row");

           /* Make every cell considered unavailable */
           $(".room-table-cells").text("");
           $(".room-table-cells").removeClass("room-table-available", "room-table-selected");
           $(".room-table-cells").addClass("room-table-unavailable");
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
            var cell_begin_time = $(this).data('cell-start-min');
            var cell_end_time = $(this).data('cell-end-min');
            var cell_day = $(this).data('cell-day');

            if ($(".confirm-table-row[data-cell-day='" + cell_day + "']").length)
            {
                /* There are still selected cells for that day */
                if ($(".room-table-selected[data-cell-day='" + cell_day + "']").length)
                {
                    /* Get Minimum Begin Time and Maximum End Time */
                    var min = Number.MAX_VALUE, max = Number.MIN_VALUE;
                    $(".room-table-selected[data-cell-day='" + cell_day + "']").each(function()
                    {
                        cell_start = $(this).data("cell-start-min");
                        cell_end = $(this).data("cell-end-min");

                        /* Get Minimum Begin Time */
                        if (cell_start < min)
                            min = cell_start;

                        if (cell_end > max)
                            max = cell_end;
                    });

                    /* Update Confirm Table */
                    timeslot = cell_day + " " + min + " - " + max;
                    $(".confirm-table-row[data-cell-day='" + cell_day + "'] td:nth-child(2)").text(timeslot);
                }
                /* There are no more selected cells */
                else
                {
                    $(".confirm-table-row[data-cell-day='" + cell_day + "']").remove();
                    dayNumber--;
                }
            }
        }
        else
        {
            /* Select the cell */
            $(this).addClass('room-table-selected');

            /* Retrieve the time and day values from the cell */
            var cell_begin_time = $(this).data('cell-start-min');
            var cell_end_time = $(this).data('cell-end-min');
            var cell_day = $(this).data('cell-day');

            /* Check if there is a row in the confirm table */
            if ($(".confirm-table-row[data-cell-day='" + cell_day + "']").length)
            {
                /* Get Minimum Begin Time and Maximum End Time */
                var min = Number.MAX_VALUE, max = Number.MIN_VALUE;
                $(".room-table-selected[data-cell-day='" + cell_day + "']").each(function()
                {
                    cell_start = $(this).data("cell-start-min");
                    cell_end = $(this).data("cell-end-min");

                    /* Get Minimum Begin Time */
                    if (cell_start < min)
                        min = cell_start;

                    if (cell_end > max)
                        max = cell_end;
                });

                /* Update Confirm Table */
                timeslot = cell_day + " " + min + " - " + max;
                $(".confirm-table-row[data-cell-day='" + cell_day + "'] td:nth-child(2)").text(timeslot);
            }
            else
            {
                /* Update confirm table */
                var timeslot = cell_day + " " + cell_begin_time + " - " + cell_end_time;
                var colLabel = "Day " + dayNumber;
                if($("#confirm-table-assigned-row").length)
                    colLabel = "New " + colLabel;
                dayNumber++;
                var dayRow = createConfirmTableRow(colLabel, timeslot, "confirm-table-day-row", cell_day);
                $(dayRow).insertBefore("#confirm-table-button-row");
            }
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

                /* Put into Form Data */
                formData["day" + numDays] = codeStrArray[0];
                formData["startTimeDay" + numDays] = codeStrArray[1];
                formData["endTimeDay" + numDays] = codeStrArray[3];

                numDays++;
            });

            /* No second day because numDays didn't reach to 3 */
            if (numDays == 2)
            {
                formData["day" + numDays] = '-';
                formData["startTimeDay" + numDays] = null;
                formData["endTimeDay" + numDays] = null;
            }

            /* Get room */
            var roomCode = $("#confirm-table-room-row td:nth-child(2)").text();
            formData["roomCode"] = roomCode;

            /* Get course code and section */
            var offering = $("#confirm-table-offering-row td:nth-child(2)").text();
            var offeringArray = offering.split(" ");

            /* Put into Form data */
            formData["courseCode"] = offeringArray[0];
            formData["section"] = offeringArray[1];

            console.log(formData);

            /* Submit Form */
            $.ajax({
                type : "POST",
                contentType : "application/json",
                url : window.location + "/update-offering-room",
                data : JSON.stringify(formData),
                dataType : "json",
                success : function(result)
                {
                    console.log(result.status);
                    if(result.status == "Done")
                    {
                        createTimeslotTable();
                        displayOccupiedSlotsTable(roomCode);
                        displayPositiveMessage(result.data);

                        setTimeout(function()
                        {
                            window.location.href = "../../../../..";
                        }, 3000);
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
                if(result.status == "Done")
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
        $.ajax({
            type : "POST",
            data : buildingCode,
            url : window.location + "/retrieve-room-names",
            success : function(result)
            {
                if(result.status == "Done")
                {
                    $.each(result.data, function(i, room)
                    {
                        var roomOption = "<option value='" +
                            room.roomCode +
                            "'>" + room.roomCode +
                            "</option>";
                        $("#assign-room-room-menu").append(roomOption);
                    });
                }
            }
        });
    }

    /*  This function retrieves all the offerings
     *  occupying the room in a given term.
    */
    function displayOccupiedSlotsTable(roomCode)
    {
        $.ajax({
            type : "POST",
            data : roomCode,
            url : window.location + "/retrieve-occupying-offerings",
            success : function(result)
            {
                if (result.status == "Done")
                {
                    $.each(result.data, function(i, offering)
                    {
                        /* Get Offering's Day, Start Time, and End Time */
                        var day = offering.day;
                        var begin_time = offering.beginTime;
                        var end_time = offering.endTime;
                        var temp_begin_time = begin_time;

                        /* Retrieve Selected Offering */
                        var selOffering = $("#confirm-table-offering-row td:nth-child(2)").text();

                        /* Iteratively find cells that satisfy the day and time */
                        while(temp_begin_time < end_time)
                        {
                            /* Find cell that satisfy the day and time */
                            var currCell = $("#assign-room-table-box").find("[data-cell-day='" + day + "'][data-cell-start-min='" + temp_begin_time + "']");

                            /* If Selected Offering is assigned in the room, it's allowed to be selected */
                            var currOffering = offering.courseCode + " " + offering.section;
                            if(selOffering !== currOffering)
                            {
                                /* Make it unavailable */
                                currCell.addClass("room-table-unavailable");
                                currCell.css("background-color", "#d3d3d3");
                                currCell.removeClass("room-table-available");

                                /* Display occupying offering */
                                currCell.text(offering.courseCode + " " + offering.section);
                            }

                            /* Make the cell's end time as the new begin time */
                            temp_begin_time = currCell.data('cell-end-min');
                        }
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
    function createConfirmTableRow(firstColLabel, secondColLabel, idName, dataDay)
    {
        var newRow = "<tr data-cell-day= '" + dataDay +
                     "' class='confirm-table-row' id='" +
                     idName + "'>" +
                     "<td>" + firstColLabel + "</td>" +
                     "<td>" + secondColLabel +
                     "</td></tr>";

        return newRow;
    }

    /* Create the assign room timeslot table. */
    function createTimeslotTable()
    {
        /* Remove table if there is existing */
        if ($("#room-assign-table").length)
            $("#room-assign-table").remove();

        var hourCells = "<table id='room-assign-table'>" +
                        "<tr>" +
                        "<td class='room-table-header'></td>" +
                        "<td class='room-table-header'>MON</td>" +
                        "<td class='room-table-header'>TUE</td>" +
                        "<td class='room-table-header'>WED</td>" +
                        "<td class='room-table-header'>THU</td>" +
                        "<td class='room-table-header'>FRI</td>" +
                        "<td class='room-table-header'>SAT</td>" +
                        "</tr>";

        // Add hour cell and first partition
        for(var i=7;i<22;i++)
        {
            hourCells += "<tr data-ref-hour=" + i + ">";
            hourCells += "<td rowspan='4' class='room-table-hour room-table-header'>" +
                         timeFormatter(i, "00", true) + " - " +
                         timeFormatter(i+1, "00", true) +
                         "</td>";
            hourCells = assignRoomTableDayTime(hourCells, i, "00", i, 15);
            hourCells += "</tr>";

            // Add second partition
            hourCells += "<tr>";
            hourCells = assignRoomTableDayTime(hourCells, i, 15, i, 30);
            hourCells += "</tr>";

            // Add third partition
            hourCells += "<tr>";
            hourCells = assignRoomTableDayTime(hourCells, i, 30, i, 45);
            hourCells += "</tr>";

            // Add fourth partition
            hourCells += "<tr class='room-table-header'>";
            hourCells = assignRoomTableDayTime(hourCells, i, 45, i+1, "00");
            hourCells += "</tr>";
        }

        // Close the timeslot table
        hourCells += "</table>";

        $(hourCells).insertAfter("#assign-room-table-border");
        $(".room-table-cells").addClass("room-table-unavailable");
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
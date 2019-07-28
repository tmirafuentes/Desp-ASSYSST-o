/*
 *
 *  ASSYSTX2 SCRIPT
 *  FOR COLLABORATIVE WORKSPACE
 *
 *  This script is for the collaborative workspace
 *  pages of the ASSYSTX2 system. These includes
 *  functions for course offering management, create
 *  a new offering, concerns management, online users,
 *  and revision history/recent changes.
 *
 */

$(function() {
    /*
     *
     *  ONLOAD FUNCTIONS
     *
     */

    /* Initialize DataTables */
    var offeringsTable = $("#all-offerings-table").DataTable({
        "select" : true,
        "stateSave" : true,
        "lengthChange" : false,
        "searching" : true,
        "pageLength" : 10,
        "pagingType" : "numbers",
        "language" : {
            "info" : "Displaying _START_ to _END_ of _TOTAL_ offerings",
            "infoEmpty" : "There are currently no course offerings.",
            "infoFiltered" : "(Filtered from _MAX_ offerings)",
            "search" : "Search offering: ",
            "zeroRecords":    "No matching course offerings found",
            "paginate": {
                "next":       "Next",
                "previous":   "Prev"
            },
        },
        "columnDefs" : [
            {
                "targets" : [0, 7],
                "orderable" : false
            }
        ]
    });

    /* Show Offerings */
    showOfferings();

    /*
     *  COURSE OFFERING MANAGEMENT
     *  EVENT LISTENERS
     *
     */

    /*  This event listener refreshes
     *  the course offerings table.
    $("#all-offerings-refresh").on('click', function()
    {
        showOfferings();
    }); */

    /*
     *  COURSE OFFERING MANAGEMENT
     *  FUNCTION IMPLEMENTATIONS
     *
    */

    /*  This function retrieves a partial list
     *  of course offerings from the database
     *  and displays it in the system.
     */
    function showOfferings()
    {
        $.ajax({
            type : "GET",
            url : window.location + "show-offerings",
            success : function(result)
            {
                /* Code for removing the currently displayed course offerings */
                $("#all-offerings-table tbody tr").remove();

                if(result.status === "Empty")
                {
                    var startPageList = "<ul id='all-offerings-page-menu'>" +
                                        "<li class='unavailable-page'>&nbsp;</li>" +
                                        "<li>" + result.data + "</li>" +
                                        "<li class='unavailable-page'>&nbsp;</li>" +
                                        "</ul>";
                    $(startPageList).appendTo("#all-offerings-box");
                }
                else if(result.status === "Done")
                {
                    /* Display each offering into the system */
                    $.each(result.data, function(i, offering)
                    {
                        var menus = "<div class='all-offerings-row-popup'>" +
                            "<img src='/images/black-icons/vertical-dot-menu.png' class='all-offerings-row-img' />" +
                            "<div class='all-offerings-dropdown-menu'>" +
                            "<form action='/assign-faculty' method='POST'>" +
                            "<input value='" + offering.courseCode + "' name='courseCode' hidden />" +
                            "<input value='" + offering.section + "' name='section' hidden />" +
                            "<button type='submit' class='offering-assign-room-button'>Assign Faculty</button></form>" +
                            "<a href='#raise-concerns-modal' rel='modal:open'><button type='button' class='offering-raise-concerns-button'>Raise Concerns</button></a>" +
                            "<a href='#view-history-modal' rel='modal:open'><button type='button' class='offering-view-history-button'>View Offering History</button></a>" +
                            "<button type='button' class='offering-special-class-button'>Mark as Service Course</button>" +
                            "</div></div>";

                        /* Offering Status Logos */
                        var imgLink = "<img src='/images/other-icons/caution-sign.png' class='all-offerings-row-img' />";

                        /* Create row array */
                        var tempRowArr = [imgLink,
                                        offering.courseCode,
                                        offering.section,
                                        offering.day1 + " " + offering.day2,
                                        offering.startTime + " - " + offering.endTime,
                                        offering.roomCode,
                                        offering.facultyName];
                        tempRowArr.push(menus);

                        /* Add to Row */
                        offeringsTable.row.add(tempRowArr).draw(true);
                    });
                }
            },
            error : function(e)
            {
                var startPageList = "<ul id='all-offerings-page-menu'>" +
                    "<li class='unavailable-page'>&nbsp;</li>" +
                    "<li>" + "There is an error loading the course offerings." + "</li>" +
                    "<li class='unavailable-page'>&nbsp;</li>" +
                    "</ul>";
                $(startPageList).appendTo("#all-offerings-box");
            }
        });
    }

    /*
     *  FILTER OFFERINGS
     *  FUNCTION IMPLEMENTATIONS
     *
    */

    /* Load Courses Available For Dropdown Filter */
    function retrieveFilterCourses()
    {
        $.ajax({
            type : "GET",
            url : window.location + "retrieve-filter-courses",
            success : function(result)
            {
                $.each(result.data, function(i, courseCode)
                {
                    courseOption = "<option value='" + courseCode + "'>" +
                                   courseCode + "</option>";
                    $("#filters-course").append(courseOption);
                });
            }
        });
    }

    /* Load Time Slots Available For Dropdown Filter */
    function retrieveFilterTimeslots()
    {
        $.ajax({
            type : "GET",
            url : window.location + "retrieve-filter-timeslots",
            success : function(result)
            {
                $.each(result.data, function(i, timeslot)
                {
                    timeOption = "<option value='" + timeslot + "'>" +
                        timeslot + "</option>";
                    $("#filters-timeslot").append(timeOption);
                });
            }
        });
    }

    /* Load Buildings Available For Dropdown Filter */
    function retrieveFilterBuildings()
    {
        $.ajax({
            type : "GET",
            url : window.location + "retrieve-filter-rooms",
            success : function(result)
            {
                $.each(result.data, function(i, roomCode)
                {
                    roomOption = "<option value='" + roomCode + "'>" +
                        roomCode + "</option>";
                    $("#filters-room").append(roomOption);
                });
            }
        });
    }

    /*
     *  WORKSPACE HISTORY
     *  EVENT LISTENERS
     *
    */

    /* Event listener for View Offering History. */
    $("#all-offerings-table").on("click", ".offering-view-history-button", function()
    {
        /* Find course offering */
        var courseCode = $(this).closest("tr").find("td:nth-child(2)").text();
        var section = $(this).closest("tr").find("td:nth-child(3)").text();

        var courseSection = courseCode + " " + section;

        /* Get Receiver */
        $.ajax({
            method : "POST",
            url : window.location + "retrieve-offering-history",
            data : courseSection,
            beforeSend : function()
            {
                /* Remove previous history */
                $(".view-history-row").remove();
                $(".view-history-row-border").remove();

                $("#view-history-modal .section-header-text").text(courseSection + " History");
            },
            success : function(result)
            {
                if(result.status === "Done")
                {
                    $.each(result.data, function(i, changes)
                    {
                        var list_row = "<ul class='view-history-row'>" +
                            "<li>" + changes.subject + "</li>" +
                            "<li>by " + changes.fullName + " ";

                        /* Format Timestamp representation */
                        /* Get Times */
                        var revisionDate = new Date(changes.timestamp).getTime();
                        var currDate = new Date().getTime();

                        /* Get Difference */
                        var timeDifference = currDate - revisionDate;

                        /* Get appropriate string for time */
                        if (timeDifference < 60000) // Less than a minute
                        {
                            list_row += "a few seconds ago ";
                        } else if (timeDifference >= 60000 && timeDifference < 3600000) // Less than an hour
                        {
                            var tempTime = Math.floor(timeDifference / 60000);
                            list_row += tempTime + " minute";
                            if (tempTime > 1)
                                list_row += "s";
                            list_row += " ago ";
                        } else if (timeDifference >= 3600000 && timeDifference < 86400000) // Less than a day
                        {
                            var tempTime = Math.floor(timeDifference / 3600000);
                            list_row += tempTime + " hour";
                            if (tempTime > 1)
                                list_row += "s";
                            list_row += " ago ";
                        } else if (timeDifference >= 86400000 && timeDifference < 2678400000) // Less than a month or 30 days
                        {
                            var tempTime = Math.floor(timeDifference / 86400000);
                            list_row += tempTime + " day";
                            if (tempTime > 1)
                                list_row += "s";
                            list_row += " ago ";
                        } else
                        {
                            var revDateAgain = new Date(result.data.timestamp);
                            list_row += "at " + revDateAgain.toLocaleDateString() + " ";
                        }

                        var end_list_row = "</li></ul>";
                        var row_border = "<hr class='view-history-row-border' />";
                        var whole_row = list_row + end_list_row + row_border;

                        $(whole_row).insertAfter("#view-history-header-border");
                    });
                }
            }
        });
    });

    /*
     *  WORKSPACE HISTORY
     *  FUNCTION IMPLEMENTATIONS
     *
    */

    /* Load most recent changes */
    function loadMostRecentChanges()
    {
        $.ajax({
            type : "GET",
            url : window.location + "retrieve-recent-changes",
            success : function(result)
            {
                /* Remove past changes */
                $(".recent-changes-row").remove();
                $(".recent-changes-row-border").remove();

                $.each(result.data, function(i, change)
                {
                    var startList = "<ul class='recent-changes-row'>";
                    var subject = "<li>" + change.subject + "</li>";
                    var revision = "<li>by " + change.fullName + " ";

                    /* Format Timestamp representation */

                    /* Get Times */
                    var revisionDate = new Date(change.timestamp).getTime();
                    var currDate = new Date().getTime();

                    /* Get Difference */
                    var timeDifference = currDate - revisionDate;

                    /* Get appropriate string for time */
                    if (timeDifference < 60000) // Less than a minute
                    {
                        revision += "a few seconds ago ";
                    } else if (timeDifference >= 60000 && timeDifference < 3600000) // Less than an hour
                    {
                        var tempTime = Math.floor(timeDifference / 60000);
                        revision += tempTime + " minute";
                        if (tempTime > 1)
                            revision += "s";
                        revision += " ago ";
                    } else if (timeDifference >= 3600000 && timeDifference < 86400000) // Less than a day
                    {
                        var tempTime = Math.floor(timeDifference / 3600000);
                        revision += tempTime + " hour";
                        if (tempTime > 1)
                            revision += "s";
                        revision += " ago ";
                    } else if (timeDifference >= 86400000 && timeDifference < 2678400000) // Less than a month or 30 days
                    {
                        var tempTime = Math.floor(timeDifference / 86400000);
                        revision += tempTime + " day";
                        if (tempTime > 1)
                            revision += "s";
                        revision += " ago ";
                    } else
                    {
                        var revDateAgain = new Date(result.data.timestamp);
                        revision += "at " + revDateAgain.toLocaleDateString() + " ";
                    }

                    var endList = "</ul><hr class='recent-changes-row-border' />";
                    var entryChange = startList + subject + revision + endList;

                    $(entryChange).insertAfter("#recent-changes-header-border")
                });
            }
        })
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
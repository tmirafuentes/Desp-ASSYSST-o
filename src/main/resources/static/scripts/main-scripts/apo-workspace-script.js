/*
 *
 *  ASSYSTX2 SCRIPT
 *  FOR COLLABORATIVE WORKSPACE
 *
 *  This script is for the collaborative workspace
 *  pages of the ASSYSTX2 system. These includes
 *  functions for course offering management, create
 *  a new offering.
 *
 */

$(function() {
    /*
     *
     *  ONLOAD FUNCTIONS
     *
     */

    /* Initialize DataTables for Offerings */
    var offeringsTable = $("#all-offerings-table").DataTable({
        "ajax" : "/show-offerings",
        "rowId" : "offeringID",
        "autoWidth" : false,
        "stateSave" : true,
        "lengthChange" : false,
        "searching" : true,
        "pageLength" : 10,
        "pagingType" : "numbers",
        "order" : [[1, "asc"]],
        "language" : {
            "info" : "Displaying _START_ to _END_ of _TOTAL_ offerings",
            "infoEmpty" : "There are currently no course offerings.",
            "infoFiltered" : "(Filtered from _MAX_ offerings)",
            "search" : "Search offering: ",
            "zeroRecords":    "No matching course offerings found",
            "paginate": {
                "next":       "Next",
                "previous":   "Prev"
            }
        },
        "dom" : "Bfrtip",
        "buttons" : [
            {
                "text" : "Create New Offering",
                "action" : function (e, dt, node, config)
                {
                    // Fix Modal issues when I have internet
                    $("#create-offering-modal").modal("open");
                }
            },
            {
                "extend" : "excel",
                "text" : "Export Current Schedule",
                "exportOptions" : {
                    "modifier" : {
                        "page" : "current"
                    }
                }
            }
        ],
        "columnDefs" : [
            {
                "orderable" : false,
                "targets" : [0, 7]
            }
        ],
        "columns" : [
            { "data" : function(data, type, dataToSet)
                {
                    /* Offering Status/Type */
                    var offeringType = "";
                    if (data.offeringType === "Special")
                        offeringType += "<span class='offering-status-special'>SPCL</span>";
                    else if (data.offeringType === "Service")
                        offeringType += "<span class='offering-status-service'>SERV</span>";
                    else if (data.offeringType === "Dissolved")
                        offeringType += "<span class='offering-status-dissolved'>DSLV</span>";

                    /* If the offering is currently
                       being modified by a user */
                    if(data.offeringEdited)
                        offeringType = "<img src='/images/other-icons/edit.png' class='datatables-row-img' />";

                    /* If there is an unacknowledged
                       concern about the offering */
                    if(data.relatedConcern)
                        offeringType = "<img src='/images/other-icons/envelope.png' class='datatables-row-img' />";

                    return offeringType;
                }
            },
            { "data" : "courseCode" },
            { "data" : "section" },
            { "data" : "combinedDays" },
            { "data" : "combinedTime" },
            { "data" : "roomCode" },
            { "data" : "facultyName" },
            { "data" : function(data, type, dataToSet)
                {
                    var menus = "";

                    if (data.offeringType === "Dissolved")
                    {
                        menus = "<div class='datatables-row-popup'>" +
                            "<img src='/images/black-icons/vertical-dot-menu.png' class='datatables-row-img' />" +
                            "<div class='datatables-dropdown-menu'>" +
                            "<div class='datatables-row-arrow'></div>" +
                            "<a href='#view-history-modal' rel='modal:open'><button type='button' class='offering-view-history-button'>View Offering History</button></a>" +
                            "</div></div>";
                    }
                    else
                    {
                        /* Drop Down Menu */
                        menus = "<div class='datatables-row-popup'>" +
                            "<img src='/images/black-icons/vertical-dot-menu.png' class='datatables-row-img' />" +
                            "<div class='datatables-dropdown-menu'>" +
                            "<div class='datatables-row-arrow'></div>" +
                            "<form action='/assign-room' method='POST'>" +
                            "<input value='" + data.courseCode + "' name='courseCode' hidden />" +
                            "<input value='" + data.section + "' name='section' hidden />" +
                            "<button type='submit' class='offering-assign-room-button'>Assign Time And Room</button></form>" +
                            "<a href='#raise-concerns-modal' rel='modal:open'><button type='button' class='offering-raise-concerns-button'>Raise Concerns</button></a>" +
                            "<a href='#view-history-modal' rel='modal:open'><button type='button' class='offering-view-history-button'>View Offering History</button></a>" +
                            "<button type='button' class='offering-special-class-button'>Mark as Special Class</button>" +
                            "<button type='button' class='offering-dissolve-offering-button'>Dissolve Offering</button>" +
                            "</div></div>";
                    }

                    return menus;
                }
            }
        ]
    });
    //$("#all-offerings-table").css("width", "95%");

    /* Add Create Offering Button */
    var createOfferingCode = "<div id='all-offerings-table_new_offering' class='create-instance-button'>" +
                             "<a href='#create-offering-modal' rel='modal:open'>" +
                             "<button type='button' class='offering-create-button'>Create New Offering</button>" +
                             "</a></div>";
    //$(createOfferingCode).prependTo("#all-offerings-table_wrapper");

    /* Update Offerings Table */
    setInterval( function () {
        offeringsTable.ajax.reload( null, false ); // user paging is not reset on reload
    }, 5000 );

    /* Retrieve Course Codes for Create New Offering */
    var courseCodes = [];
    retrieveAllCourses();

    /*
     *  COURSE OFFERING MANAGEMENT
     *  EVENT LISTENERS
     *
     */

    /*  This event listener marks the
     *  selected course offering as
     *  a special class.
     */
    $("#all-offerings-box").on('click', ".offering-special-class-button", function()
    {
        /* Retrieve Course Offering */
        var courseCode = $(this).closest("tr").find("td:nth-child(2)").text();
        var section = $(this).closest("tr").find("td:nth-child(3)").text();

        /* Perform AJAX */
        var formData = {
            courseCode : courseCode,
            section : section,
            offeringType : "Special"
        };

        $.ajax({
            method : "POST",
            contentType : "application/json",
            url : window.location + "update-offering-type",
            data : JSON.stringify(formData),
            dataType : "json",
            success : function(result)
            {
                if(result.status === "Done")
                {
                    displayPositiveMessage(result.data);

                    /* Remove Change Special Class Option to Regular */
                    $(this).addClass("offering-regular-offering-button");
                    $(this).removeClass("offering-special-class-button");
                    $(this).text("Mark as Regular Offering");

                }
            }
        });
    });

    /*  This event listener marks the
     *  selected course offering as
     *  a regular offering.
     */
    $("#all-offerings-box").on('click', ".offering-regular-class-button", function()
    {
        /* Retrieve Course Offering */
        var courseCode = $(this).closest("tr").find("td:nth-child(2)").text();
        var section = $(this).closest("tr").find("td:nth-child(3)").text();

        /* Perform AJAX */
        var formData = {
            courseCode : courseCode,
            section : section,
            offeringType : "Special"
        };

        $.ajax({
            method : "POST",
            contentType : "application/json",
            url : window.location + "update-offering-type",
            data : JSON.stringify(formData),
            dataType : "json",
            success : function(result)
            {
                if(result.status === "Done")
                {
                    displayPositiveMessage(result.data);

                    /* Remove Change Special Class Option to Regular */
                    $(this).addClass("offering-regular-offering-button");
                    $(this).removeClass("offering-special-class-button");
                    $(this).text("Mark as Regular Offering");
                }
            }
        });
    });

    /*  This event listener marks the
     *  selected course offering as
     *  a dissolved offering.
     */
    $("#all-offerings-box").on('click', ".offering-dissolve-offering-button", function()
    {
        /* Retrieve Course Offering */
        var courseCode = $(this).closest("tr").find("td:nth-child(2)").text();
        var section = $(this).closest("tr").find("td:nth-child(3)").text();

        /* Perform AJAX */
        var formData = {
            courseCode : courseCode,
            section : section,
            offeringType : "Dissolved"
        };

        $.ajax({
            method : "POST",
            contentType : "application/json",
            url : window.location + "update-offering-type",
            data : JSON.stringify(formData),
            dataType : "json",
            success : function(result)
            {
                if(result.status === "Done")
                {
                    displayPositiveMessage(result.data);

                    /* Remove Change Special Class Option to Regular */
                    $(this).addClass("offering-regular-offering-button");
                    $(this).removeClass("offering-special-class-button");
                    $(this).text("Mark as Regular Offering");

                    /* Add Signifier to the row */
                    $(this).closest("tr").find("td:nth-child(1)").html("<span class='offering-status-dissolved'>DSLV</span>");
                }
            }
        });
    });

    /*
     *  CREATE NEW OFFERING
     *  EVENT LISTENERS
     *
    */

    /* This event listener retrieves
     * the list of courses to guide
     * the APO in creating an offering.
    */
    $("#create-offering-course").autocomplete({
        minLength : 3,
        source : courseCodes
    });

    /*  This event listener submits a form
     *  which includes the course offering
     *  and section to the database.
    */
    $("#create-offering-modal").on('click', "#create-offering-submit", function()
    {
        /* Retrieve input from text fields */
        var courseCode = $("#create-offering-course").val();
        var section = $("#create-offering-section").val();

        /* Check if it's not empty and then submit */
        if (courseCode.length === 7 && section.length <= 4 && section.length > 0)
        {
            createNewOffering(courseCode, section);
        }
        else
        {
            displayNegativeMessage("Invalid input for create new offering!");
        }
    });

    $("#create-offering-modal").on('click', "#create-offering-cancel", function()
    {
       $(".blocker").hide();
       $("#create-offering-modal").modal("close");
    });

    /*
     *  CREATE NEW OFFERING
     *  FUNCTION IMPLEMENTATIONS
     *
    */

    /*  This function retrieves all
     *  course codes in the database.
    */
    function retrieveAllCourses()
    {
        /* Perform AJAX */
        $.ajax({
            type : "GET",
            url : window.location +  "autocomplete-course-code",
            success : function(result)
            {
                if(result.status === "Done")
                {
                    $.each(result.data, function(i, cc)
                    {
                        courseCodes.push(cc);
                    });
                }
            },
            error : function(e)
            {
                console.log("Error = " + e);
            }
        });
    }

    /*  This function creates a new offering
     *  through submitting the course code
     *  and section.
     */
    function createNewOffering(courseCode, section)
    {
        /* Prepare form data */
        var formData =
        {
            courseCode : courseCode,
            section : section
        };

        /* Perform AJAX */
        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : window.location + "create-new-offering",
            data : JSON.stringify(formData),
            dataType : "json",
            success : function(result)
            {
                if(result.status === "Done")
                {
                    offeringsTable.ajax.reload( null, false );

                    /* Reset text fields */
                    $("#create-offering-course").val("");
                    $("#create-offering-section").val("");
                    $(".blocker").hide();
                    $("#create-offering-modal").modal("close");

                    /* Display Message */
                    displayPositiveMessage(result.message);
                }
                else if(result.status === "Error")
                {
                    /* Display Message */
                    displayNegativeMessage(result.data);
                }
            },
            error : function(e)
            {
                displayNegativeMessage("An error occurred while creating a new offering.");
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
                        console.log("Time: " + changes.timestamp);

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
                            var revDateAgain = new Date(changes.timestamp);
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
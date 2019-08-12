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
        "ajax" : "/retrieve-teaching-load",
        "rowId" : "offeringID",
        "autoWidth" : false,
        "stateSave" : true,
        "searching": false,
        "lengthChange" : false,
        "pageLength" : 10,
        "pagingType" : "numbers",
        "order" : [[1, "asc"]],
        "language" : {
            "info" : "Displaying _START_ to _END_ of _TOTAL_ teaching load",
            "infoEmpty" : "There are currently no teaching load.",
            "infoFiltered" : "(Filtered from _MAX_ teaching load)",
            "search" : "Search teaching load: ",
            "zeroRecords":    "No teaching loads for faculty",
            "paginate": {
                "next":       "Next",
                "previous":   "Prev"
            }
        },
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
                    /* Drop Down Menu */
                    var menus = "<div class='datatables-row-popup'>" +
                        "<img src='/images/black-icons/vertical-dot-menu.png' class='datatables-row-img' />" +
                        "<div class='datatables-dropdown-menu'>" +
                        "<div class='datatables-row-arrow'></div>" +
                        "<a href='#raise-concerns-modal' rel='modal:open'><button type='button' class='offering-raise-concerns-button'>Raise Concerns</button></a>" +
                        "</div></div>";

                    return menus;
                }
            }
        ]
    });
    var compareLoadTable = $("#compare-load-table").DataTable({
        "autoWidth" : false,
        "lengthChange" : false,
        "searching": false,
        "pageLength" : 10,
        "pagingType" : "numbers",
        "order" : [[1, "asc"]],
        "language" : {
            "info" : "Displaying _START_ to _END_ of _TOTAL_ teaching load",
            "infoEmpty" : "There are currently no teaching load.",
            "infoFiltered" : "(Filtered from _MAX_ teaching load)",
            "search" : "Search teaching load: ",
            "zeroRecords":    "No teaching loads for faculty",
            "paginate": {
                "next":       "Next",
                "previous":   "Prev"
            }
        },
        "columnDefs" : [
            {
                "orderable" : false,
                "targets" : [0, 7]
            }
        ]
    });

    /* Update Offerings Table */
    setInterval( function () {
        offeringsTable.ajax.reload( null, false ); // user paging is not reset on reload
    }, 30000);

    /* Load Faculty Names */
    retrieveFacultyNames();

    /* Load Deloaded Load */
    retrieveDeloadedLoad();

    /*
     *  FACULTY LOAD
     *  EVENT LISTENER
     *
     */

    /*  This event listener retrieves the
     *  faculty load of the comparee.
     */
    $("#teaching-load-compare-dropdown").change(function()
    {
        if($("#teaching-load-compare-dropdown").find("option:selected").val() === "-")
        {
            /* Remove content and hide table */
            compareLoadTable.clear().draw();
            $("#compare-load-table_wrapper").hide();
        }
        else
        {
            /* Prepare data */
            var formData = {
                facultyName : $("#teaching-load-compare-dropdown").find("option:selected").val()
            };

            /* Perform ajax */
            $.ajax({
                type : "POST",
                contentType : "application/json",
                url : window.location + "retrieve-teaching-load",
                dataType : "json",
                data : JSON.stringify(formData),
                beforeSend : function()
                {
                    /* Remove content */
                    compareLoadTable.clear().draw();
                },
                success : function(result)
                {
                    $.each(result.data, function(i, load)
                    {
                        /* Offering Status/Type */
                        var offeringType = "";
                        if (load.offeringType === "Special")
                            offeringType += "<span class='offering-status-special'>SPCL</span>";
                        else if (load.offeringType === "Service")
                            offeringType += "<span class='offering-status-service'>SERV</span>";
                        else if (load.offeringType === "Dissolved")
                            offeringType += "<span class='offering-status-dissolved'>DSLV</span>";

                        /* If the offering is currently
                           being modified by a user */
                        if(load.offeringEdited)
                            offeringType = "<img src='/images/other-icons/edit.png' class='datatables-row-img' />";

                        /* If there is an unacknowledged
                           concern about the offering */
                        if(load.relatedConcern)
                            offeringType = "<img src='/images/other-icons/envelope.png' class='datatables-row-img' />";

                        /* Drop Down Menu */
                        var menus = "<div class='datatables-row-popup'>" +
                            "<img src='/images/black-icons/vertical-dot-menu.png' class='datatables-row-img' />" +
                            "<div class='datatables-dropdown-menu'>" +
                            "<div class='datatables-row-arrow'></div>" +
                            "<a href='#raise-concerns-modal' rel='modal:open'><button type='button' class='offering-raise-concerns-button'>Raise Concerns</button></a>" +
                            "</div></div>";

                        var row = [offeringType,
                                    load.courseCode,
                                    load.section,
                                    load.combinedDays,
                                    load.combinedTime,
                                    load.roomCode,
                                    load.facultyName,
                                    menus];

                        compareLoadTable.row.add(row).draw(true);
                    });

                    $("#compare-load-table_wrapper").show();
                }
            });
        }
    });

    /*
     *  FACULTY LOAD
     *  FUNCTION IMPLEMENTATIONS
     *
     */

    /*  This function retrieves all the
     *  faculty within the department.
     */
    function retrieveFacultyNames()
    {
        $.ajax({
            method : 'GET',
            url : window.location + 'retrieve-available-faculty',
            success: function(result)
            {
                $.each(result.data, function(i, faculty)
                {
                    var option = "<option value='" + faculty.facultyName + "'>" +
                                 faculty.facultyName +
                                 "</option>";
                    $(option).appendTo("#teaching-load-compare-dropdown");
                });
            }
        });
    }

    /*  This function retrieves all the
     *  deloaded load of the faculty.
     */
    function retrieveDeloadedLoad()
    {
        $.ajax({
            method : 'GET',
            url : window.location + 'retrieve-specific-faculty-profile',
            success: function(result)
            {
                /* Admin Load */
                var admin_load = result.data.adminLoad,
                    admin_units = result.data.adminUnits;

                $("#view-admin-load-table thead th:nth-child(2)").text(admin_units);
                if(admin_units == 0)
                {
                    var load_row =  "<tr>" +
                        "<td>None</td>" +
                        "<td></td>" +
                        "</tr>";

                    $("#view-admin-load-table tbody").append(load_row);
                }
                else
                {
                    $.each(admin_load, function (i, load) {
                        var load_row = "<tr>" +
                            "<td>" + load.loadName + "</td>" +
                            "<td>" + load.loadUnits + "</td>" +
                            "</tr>";

                        $("#view-admin-load-table tbody").append(load_row);
                    });
                }

                /* Research Load */
                var research_load = result.data.researchLoad,
                    research_units = result.data.researchUnits;

                $("#view-research-load-table thead th:nth-child(2)").text(research_units);
                if(research_units == 0)
                {
                    var load_row =  "<tr>" +
                        "<td>None</td>" +
                        "<td></td>" +
                        "</tr>";

                    $("#view-research-load-table tbody").append(load_row);
                }
                else
                {
                    $.each(research_load, function (i, load) {
                        var load_row = "<tr>" +
                            "<td>" + load.loadName + "</td>" +
                            "<td>" + load.loadUnits + "</td>" +
                            "</tr>";

                        $("#view-research-load-table tbody").append(load_row);
                    });
                }
            }
        });
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
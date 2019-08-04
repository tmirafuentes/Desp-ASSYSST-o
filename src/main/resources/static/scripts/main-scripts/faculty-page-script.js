/*
 *
 *  ASSYSTX2 SCRIPT
 *  FOR FACULTY PROFILE PAGES
 *
 *  This script is for the faculty profile
 *  page of the ASSYSTX system.
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

    /* Initialize DataTables for Faculty List */
    var facultyTables = $("#faculty-list-table").DataTable({
        "ajax" : "/retrieve-faculty-list",
        "rowId" : "facultyID",
        "autoWidth" : false,
        "stateSave" : true,
        "lengthChange" : false,
        "searching" : true,
        "pageLength" : 10,
        "pagingType" : "numbers",
        "order" : [[4, "desc"]],
        "language" : {
            "info" : "Displaying _START_ to _END_ of _TOTAL_ faculty profiles",
            "infoEmpty" : "There are currently no faculty profiles.",
            "infoFiltered" : "(Filtered from _MAX_ faculty)",
            "search" : "Search faculty: ",
            "zeroRecords":    "No matching faculty found",
            "paginate": {
                "next":       "Next",
                "previous":   "Prev"
            }
        },
        "columnDefs" : [
            {
                "orderable" : false,
                "targets" : 5
            }
        ],
        "columns" : [
            { "data" : "facultyName" },
            { "data" : "facultyType" },
            { "data" : "department" },
            { "data" : "active" },
            { "data" : "totalUnits" },
            { "data" : function(data, type, dataToSet)
                {
                    var deloadButton = "<a href='#deload-faculty-modal' rel='modal:open'><button type='button' class='deload-faculty-button'>Deload Faculty</button></a>";
                    var inactiveFacultyButton = "<button type='button' class='view-course-history-button'>Set as Inactive Faculty</button>";

                    var menus = "<div class='datatables-row-popup'>" +
                        "<img src='/images/black-icons/vertical-dot-menu.png' class='datatables-row-img' />" +
                        "<div class='datatables-dropdown-menu'>" +
                        "<a href='#view-faculty-load-modal' rel='modal:open'><button type='button' class='view-faculty-load-button'>View Faculty Load</button></a>";

                    if(!$("#workspace-menu-title").text().includes("APO Workspace"))
                    {
                        menus += deloadButton;
                        menus += inactiveFacultyButton;
                    }

                    menus += "</div></div>";

                    return menus;
                }
            }
        ]
    });

    /* Add Create Faculty Button */
    var createFacultyCode = "<div id='faculty_list-table_new_faculty' class='create-instance-button'>" +
        "<a href='#create-faculty-modal' rel='modal:open'>" +
        "<button type='button' class='faculty-create-button'>Create New Faculty</button>" +
        "</a></div>";

    if($("#workspace-menu-title").text().includes("Chairs Workspace"))
        $(createFacultyCode).prependTo("#faculty-list-table_wrapper");

    /* Update Faculty Table */
    setInterval( function () {
        facultyTables.ajax.reload( null, false );
    }, 30000 );

    /*
     *  RETRIEVE FACULTY LOAD
     *  EVENT LISTENER
     *
     */

    /*  This event listener retrieves
     *  a faculty's load.
     */
    $("#faculty-profiles-list-box").on('click', ".view-faculty-load-button", function()
    {
        /* Retrieve Faculty */
        var data = {
            facultyName : $(this).closest("tr").find("td:nth-child(1)").text()
        };

        $.ajax({
            method : "POST",
            contentType : "application/json",
            dataType : "json",
            data : JSON.stringify(data),
            url : window.location + "/retrieve-specific-faculty-profile",
            beforeSend : function()
            {
                $("#view-faculty-load-modal p.section-header-text").text(data.facultyName + "'s Faculty Load");
            },
            success : function(result)
            {
                if(result.status === "Done")
                {
                    /* Remove Tables */
                    $(".form-modal-tables tbody tr").remove();

                    /* Teaching Load */
                    var teaching_load = result.data.teachingLoad,
                        teaching_units = result.data.teachingUnits,
                        teaching_prep = result.data.numPreparations;

                    $("#view-teaching-load-table thead th:nth-child(2)").text(teaching_units);
                    if(teaching_units === 0 && teaching_prep === 0)
                    {
                        var load_row =  "<tr>" +
                            "<td>None</td>" +
                            "<td></td>" +
                            "</tr>";

                        $("#view-teaching-load-table tbody").append(load_row);
                    }
                    else
                    {
                        $.each(teaching_load, function(i, load)
                        {
                            var load_row =  "<tr>" +
                                "<td>" + load.loadName + "</td>" +
                                "<td>" + load.loadUnits + "</td>" +
                                "</tr>";

                            $("#view-teaching-load-table tbody").append(load_row);
                        });
                    }

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
                    if(teaching_units == 0)
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
            }
        });
    });

    /*
     *  DELOADING MODULE
     *  EVENT LISTENERS
     *
    */

    /*  This event listener retrieves
     *  the faculty to be deloaded.
     */
    $("#faculty-profiles-list-box").on('click', ".deload-faculty-button", function()
    {
         /* Find faculty name */
        var facultyName = $(this).closest("tr").find("td:nth-child(1)").text();

        /* Put into modal */
        $("#deload-faculty-name").val(facultyName);
    });

    /*  This event listener retrieves
     *  the deloading instances from
     *  the selected deloading type.
     */
    $("#deload-faculty-type").change(function()
    {
        /* Get selected deloading type */
        var deloadingType = $("#deload-faculty-type").children("option:selected").val();

        if(deloadingType !== "-")
        {
            $.ajax({
                method : "POST",
                contentType : "application/json",
                url : window.location + "/retrieve-deloading-instances",
                data : deloadingType,
                dataType : "json",
                success : function(result)
                {
                    if(result.status === "Done")
                    {
                        /* Remove previous options */
                        $("#deload-faculty-instance option:not(:first-child)").remove();

                        /* Put options to dropdown */
                        $.each(result.data, function(i, deloading)
                        {
                            var deload_option = "<option value='" + deloading.units + "'>" +
                                deloading.deloadCode +
                                "</option>";

                            $(deload_option).appendTo("#deload-faculty-instance");
                        });
                    }
                }

            });
        }
        else
        {
            /* Remove previous options */
            $("#deload-faculty-instance option:not(:first-child)").remove();
        }
    });

    /*  This event listener updates the
     *  units of the selected deloading code.
     */
    $("#deload-faculty-instance").change(function()
    {
        var code = $("#deload-faculty-instance").children("option:selected").val();

        if(code === "-")
            $("#deload-faculty-units-deloaded").val("0.0 units");
        else
            $("#deload-faculty-units-deloaded").val(code + ".0 units");
    });

    /*  This event listener deloads
     *  the faculty in the system.
     */
    $("#deload-faculty-submit").click(function()
    {
        /* Create form data */
        var formData =
        {
            facultyName : $("#deload-faculty-name").val(),
            deloadCode : $("#deload-faculty-instance").children("option:selected").html()
        };

        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : window.location + "/deload-faculty",
            data : JSON.stringify(formData),
            dataType : "json",
            success : function(result)
            {
                if(result.status === "Done")
                {
                    displayPositiveMessage(result.message);

                    $("#deload-faculty-type").children("option:nth-child(1)").selected();
                    $("#deload-faculty-instance").children("option:nth-child(1)").selected();
                    $("#deload-faculty-units-deloaded").val("0.0 units");

                    $("#deload-faculty-modal").modal("close");
                    $(".blocker").hide();
                }
            }
        });
    });

    /*  This event listener closes
     *  the deload faculty modal.
     */
    $("#deload-faculty-cancel").click(function()
    {
        $("#deload-faculty-modal").modal("close");
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
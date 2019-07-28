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

    /* Initialize DataTables */
    var facultyTables = $("#faculty-list-table").DataTable({
        "lengthChange" : false,
        "searching" : true,
        "pageLength" : 10,
        "pagingType" : "numbers",
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
        ]
    });

    /* Retrieve Faculty Profiles */
    retrieveFacultyProfiles();

    /*
     *
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

    /*  This event listener closes
     *  the deload faculty modal.
     */
    $("#deload-faculty-cancel").click(function()
    {
        $("#deload-faculty-modal").modal("close");
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
                    setTimeout(function()
                    {
                        window.location.href = "/";
                    }, 1500);
                    $("#deload-faculty-modal").modal("close");
                }
            }
        });
    });

    /*
     *
     *  FUNCTION IMPLEMENTATIONS
     *
     */

    /*  This function retrieves all the
     *  faculty profiles from the database.
     */
    function retrieveFacultyProfiles()
    {
        $.ajax({
            type : "GET",
            url : window.location + "/retrieve-faculty-list",
            success : function(result)
            {
                 if(result.status === "Done")
                 {
                     /* Load all faculty profiles */
                     $.each(result.data, function(i, faculty)
                     {
                         var deloadButton = "<a href='#deload-faculty-modal' rel='modal:open'><button type='button' class='deload-faculty-button'>Deload Faculty</button></a>";

                         var menus = "<div class='datatables-row-popup'>" +
                             "<img src='/images/black-icons/vertical-dot-menu.png' class='datatables-row-img' />" +
                             "<div class='datatables-dropdown-menu'>";

                         if(result.message !== "Academic Programming Officer")
                             menus += deloadButton;

                         menus += "<a href='#view-faculty-load-modal' rel='modal:open'><button type='button' class='view-faculty-load-button'>View Faculty Load</button></a>" +
                             "<button type='button' class='view-course-history-button'>Set as Inactive Faculty</button>" +
                             "</div></div>";

                         /* Create row array */
                         var tempRowArr = [faculty.facultyName,
                                           faculty.facultyType,
                                           faculty.department,
                                           faculty.active,
                                           faculty.totalUnits];
                         tempRowArr.push(menus);

                         /* Add to Row */
                         facultyTables.row.add(tempRowArr).draw(true);
                     });
                 }
            },
            error : function(e)
            {
                console.log("Error: " + e);
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
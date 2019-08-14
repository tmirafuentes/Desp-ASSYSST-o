/*
 *
 *  ASSYSTX2 SCRIPT
 *  FOR ASSIGN FACULTY
 *
 *  This script is for the assign faculty
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

    retrieveFacultyNames("ALL");

    /*
     *
     *  EVENT LISTENERS
     *
     */

    /*  This event listener retrieves
     *  the faculty when a filter is selected.
    */
    $("#assign-faculty-filter-menu").on('change', function()
    {
        /* Retrieve select value */
        var filterValue = $("#assign-faculty-filter-menu").find("option:selected").val();

        retrieveFacultyNames(filterValue);
    });

    /*  This event listener selects
     *  the clicked cell in the
     *  assign faculty table.
    */
    $("#assign-faculty-table-box").on('click', "#faculty-assign-table td", function()
    {
        /*  Make cell look selected and
         *  update confirm table about the
         *  selected cells.
         */
        if($(this).hasClass('faculty-table-selected'))
        {
            /* Deselect the cell */
            $(this).removeClass('faculty-table-selected');

            /* Remove from confirm table */
            $("#confirm-table-faculty-row").remove();
            $("#confirm-table-load-row").remove();
        }
        else
        {
            /* Deselect everything */
            $(".faculty-table-selected").removeClass('faculty-table-selected');

            /* Select the cell */
            $(this).addClass('faculty-table-selected');

            /* Retrieve Faculty Name */
            var faculty_name = $(this).data('cell-faculty-name');

            /* Check if there is a row in the confirm table */
            if ($("#confirm-table-faculty-row").length)
            {
                /* Update existing row */
                $("#confirm-table-faculty-row td:nth-child(2)").text(faculty_name);
            }
            else
            {
                /* Append to confirm table */
                if($("#confirm-table-assigned-row").length)
                {
                    /* Create rows */
                    var facultyRow = createConfirmTableRow("New Faculty", faculty_name, "confirm-table-faculty-row");
                    $(facultyRow).insertAfter("#confirm-table-assigned-row");
                }
                else
                {
                    /* Create rows */
                    var facultyRow = createConfirmTableRow("Faculty", faculty_name, "confirm-table-faculty-row");
                    $(facultyRow).insertAfter("#confirm-table-offering-row");
                }
            }
        }
    });

    /*  This event listener submits
     *  the form for assigning a faculty.
     */
    $("#assign-faculty-submit").click(function()
    {
        if ($("#confirm-table-faculty-row").length)
        {
            var formData = {};

            /* Get Faculty Name */
            formData["facultyName"] = $("#confirm-table-faculty-row td:nth-child(2)").text();

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
                url : window.location + "/update-offering-faculty",
                data : JSON.stringify(formData),
                dataType : "json",
                success : function(result)
                {
                    if(result.status === "Done")
                    {
                        displayPositiveMessage(result.data);

                        setTimeout(function()
                        {
                            window.location.href = "../../../../..";
                        }, 1500);
                    }
                    else if(result.status === "Overload")
                    {
                        displayNegativeMessage(result.data);
                    }
                },
                error : function(e)
                {
                    displayNegativeMessage("There was an error while assigning the faculty.");
                }
            });
        }
    });

    /*  This event listener returns
 *  the user to home page
 */
    $("#assign-faculty-cancel").click(function()
    {
        window.location.href = "/";
    });

    /*
     *
     *  FUNCTION IMPLEMENTATIONS
     *
     */

    /* Retrieve and display all faculty */
    function retrieveFacultyNames(filter)
    {
        /* Determine URL */
        var url = window.location + "/";
        if(filter === "ALL")
            url += "retrieve-available-faculty";
        else if(filter === "PAST")
            url += "retrieve-previous-experienced-faculty";

        $.ajax({
            method : "GET",
            url : url,
            success : function(result)
            {
                if(result.status === "Done")
                {
                    /* Clear Table */
                    $("#faculty-assign-table").empty();

                    var col_ctr = 1;
                    var output_cell = "";
                    var length = result.data.length;
                    $.each(result.data, function(i, faculty)
                    {
                        var start_row = "<tr>",
                            end_row = "</tr>";

                        var units = "";
                        if(faculty.teachingUnits >= 0 && faculty.teachingUnits < 6)
                            units += "Teaching Load: <span class='faculty-assign-units-green'>" +
                                     faculty.teachingUnits + " units</span>";
                        else if(faculty.teachingUnits >= 6 && faculty.teachingUnits < 12)
                            units += "Teaching Load: <span class='faculty-assign-units-orange'>" +
                                faculty.teachingUnits + " units</span>";
                        else
                            units += "Teaching Load: <span class='faculty-assign-units-red'>" +
                                faculty.teachingUnits + " units</span>";

                        /* Create faculty name cell */
                        var cell_faculty = "<td data-cell-faculty-name='" + faculty.facultyName + "'>" +
                            "<p class='faculty-assign-name'>" + faculty.facultyName + "</p>" +
                            "<p class='faculty-assign-details'>" + faculty.facultyPosition + "<br>" +
                            units + "</td>";

                        /* Start of row */
                        if(col_ctr === 1)
                            output_cell += start_row;

                        output_cell += cell_faculty;
                        col_ctr += 1;
                        length -= 1;

                        /* End of row */
                        if(col_ctr === 6 || length === 0)
                        {
                            output_cell += end_row;
                            col_ctr = 1;

                            /* Add to table */
                            $("#faculty-assign-table").append(output_cell);

                            output_cell = "";
                        }
                    });
                }
            },
            error: function(e)
            {
                console.log("Error: " + e);
            }
        });
    }

    /* Create Confirm Table Rows */
    function createConfirmTableRow(firstColLabel, secondColLabel, idName)
    {
        var newRow = "<tr id='" +
            idName + "'>" +
            "<td>" + firstColLabel + "</td>" +
            "<td>" + secondColLabel +
            "</td></tr>";

        return newRow;
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
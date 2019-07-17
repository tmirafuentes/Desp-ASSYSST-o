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
    retrieveFacultyProfiles();

    /*
     *
     *  EVENT LISTENERS
     *
     */

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
                         /* Create row */
                         var row =  "<tr>" +
                                    "<td>" + faculty.facultyName + "</td>" +
                                    "<td>" + faculty.facultyType + "</td>" +
                                    "<td>" + faculty.department + "</td>" +
                                    "<td>" + faculty.active + "</td>" +
                                    "<td>" + faculty.totalUnits + "</td>" +
                                    "</tr>";

                         $(row).appendTo("#faculty-list-table tbody");
                     });

                     /* Initialize DataTables */
                     $("#faculty-list-table").DataTable({
                         "stateSave" : true,
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
                             },
                         }
                     });

                     /* Initialize accordion
                     $("#course-list-accordion").accordion({
                         collapsible: true,
                         heightStyle: "content",
                         active: false
                     }); */
                 }
            },
            error : function(e)
            {
                console.log("Error: " + e);
            }
        });
    }
});
/*
 *
 *  ASSYSTX2 SCRIPT
 *  FOR COURSE PROFILE PAGES
 *
 *  This script is for the course profile
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
    retrieveCourseProfiles("ALL");

    /*
     *
     *  EVENT LISTENERS
     *
     */

    /*  This event listener is for
     *  the course profiles row.
     */
    $("#course-list-table-filter").change(function()
    {
        var deptCode = $(this).children("option:selected").val();
        $("#course-list-accordion").children().remove();
        retrieveCourseProfiles(deptCode);

        /* Initialize accordion */
        $("#course-list-accordion").accordion({
            collapsible: true,
            heightStyle: "content",
            active: false
        });
    });

    /*
     *
     *  FUNCTION IMPLEMENTATIONS
     *
     */

    /*  This function retrieves all the
     *  course profiles from the database.
     */
    function retrieveCourseProfiles(deptCode)
    {
        $.ajax({
            type : "POST",
            data : deptCode,
            url : window.location + "/retrieve-course-list",
            success : function(result)
            {
                 if(result.status === "Done")
                 {
                     /* Load all course profiles */
                     $.each(result.data, function(i, course)
                     {
                         /* Create row */
                         var row =  "<tr>" +
                                    "<td>" + course.courseCode + "</td>" +
                                    "<td>" + course.courseName + "</td>" +
                                    "<td>" + course.courseUnits + "</td>" +
                                    "</tr>";

                         $(row).appendTo("#course-list-table tbody");
                     });

                     /* Initialize DataTables */
                     $("#course-list-table").DataTable({
                         "stateSave" : true,
                         "lengthChange" : false,
                         "searching" : true,
                         "pageLength" : 10,
                         "pagingType" : "numbers",
                         "language" : {
                             "info" : "Displaying _START_ to _END_ of _TOTAL_ course profiles",
                             "infoEmpty" : "There are currently no course profiles.",
                             "infoFiltered" : "(Filtered from _MAX_ courses)",
                             "search" : "Search course: ",
                             "zeroRecords":    "No matching courses found",
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
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
                 if(result.status == "Done")
                 {
                     /* Load all course profiles */
                     $.each(result.data, function(i, course)
                     {
                         var courseHeader = "<h3>" +
                                            "<span class='course-list-code'>" + course.courseCode + "</span>" +
                                            "<span class='course-list-title'>" + course.courseName + "</span>" +
                                            "<span class='course-list-units'>" + course.courseUnits + "</span>" +
                                            "</h3>";
                         var courseDiv = "<div class='course-list-accordion-div'>" +
                                         "<div class='course-list-desc'>" +
                                         "<span class='course-list-desc-title'>Description</span>" +
                                         "<p class='course-list-desc-content'>" + course.courseDesc + "</p>" +
                                         "</div></div>";

                         var courseProfile = courseHeader + courseDiv;
                         $("#course-list-accordion").append(courseProfile);
                     });

                     /* Initialize accordion */
                     $("#course-list-accordion").accordion({
                         collapsible: true,
                         heightStyle: "content",
                         active: false
                     });
                 }
            },
            error : function(e)
            {
                console.log("Error: " + e);
            }
        });
    }
});
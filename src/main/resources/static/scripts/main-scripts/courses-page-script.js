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

    /* Initialize DataTables */
    var coursesTable = $("#course-list-table").DataTable({
        "stateSave" : true,
        "lengthChange" : false,
        "searching" : true,
        "pageLength" : 10,
        "pagingType" : "numbers",
        "order" : [[0, "asc"]],
        "language" : {
            "info" : "Displaying _START_ to _END_ of _TOTAL_ course profiles",
            "infoEmpty" : "There are currently no course profiles.",
            "infoFiltered" : "(Filtered from _MAX_ courses)",
            "search" : "Search course: ",
            "zeroRecords":    "No matching courses found",
            "paginate": {
                "next":       "Next",
                "previous":   "Prev"
            }
        },
        "columnDefs" : [
            {
                "orderable" : false,
                "targets" : 3
            }
        ]
    });

    /* Add Create Course Button */
    var createCourseCode = "<div id='course_list-table_new_course' class='create-instance-button'>" +
        "<a href='#create-course-modal' rel='modal:open'>" +
        "<button type='button' class='course-create-button'>Create New Course</button>" +
        "</a></div>";
    $(createCourseCode).prependTo("#course-list-table_wrapper");

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
    });

    /*  This event listener retrieves
     *  other course details.
     */
    $("#course-profiles-list-box").on('click', ".view-course-details-button", function()
    {
        /* Retrieve Course */
        var data = {
            courseCode: $(this).closest("tr").find("td:nth-child(1)").text()
        };

        $.ajax({
            method : "POST",
            contentType : "application/json",
            dataType : "json",
            data : JSON.stringify(data),
            url : window.location + "/retrieve-more-course-details",
            beforeSend : function()
            {
                $("#view-course-details-modal p.section-header-text").text(data.courseCode + " Profile");
            },
            success : function(result)
            {
                if(result.status === "Done")
                {
                    $("#view-course-details-modal input").val("");
                    $("#view-course-details-modal textarea").text("");

                    var course_details = result.data;
                    $("#view-course-details-desc").text(course_details.courseDesc);
                    $("#view-course-details-hours").val(course_details.numHours + " Hours");
                    $("#view-course-details-room").val(course_details.roomType);
                    $("#view-course-details-dept").val(course_details.deptName);
                    $("#view-course-details-college").val(course_details.college);
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
                         var menus = "<div class='datatables-row-popup'>" +
                             "<img src='/images/black-icons/vertical-dot-menu.png' class='datatables-row-img' />" +
                             "<div class='datatables-dropdown-menu'>" +
                             "<a href='#modify-course-modal' rel='modal:open'><button type='button' class='modify-course-profile-button'>Modify Course Profile</button></a>" +
                             "<a href='#view-course-details-modal' rel='modal:open'><button type='button' class='view-course-details-button'>View More Details</button></a>" +
                             "</div></div>";

                         /* Create row array */
                         var tempRowArr = [course.courseCode,
                                           course.courseName,
                                           course.courseUnits];
                         tempRowArr.push(menus);

                         /* Add to Row */
                         coursesTable.row.add(tempRowArr).draw(true);
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
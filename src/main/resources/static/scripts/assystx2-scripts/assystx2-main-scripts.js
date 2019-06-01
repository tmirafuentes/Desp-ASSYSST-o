/*
 *
 *  ASSYSTX2 MAIN SCRIPT
 *
 *  This script file includes AJAX functions
 *  for loading course offerings, displaying
 *  online users, and displaying recent changes.
 *
 *  A separate script file will be for enhancing
 *  the user experience of the system.
 *
 */

$(function() {
    /*
     *
     *  ONLOAD FUNCTIONS
     *
     */
    showPartialOfferings(0);

    /*
     *
     *  EVENT LISTENERS
     *
     */

    /*  This event listener retrieves the
     *  previous page of a partial list of
     *  course offerings and displays it in
     *  the system.
     */
    $("#all-offerings-box").on('click', '#offerings-prev-page', function()
    {
        var pageNum = $("#offerings-prev-page").data("page-num");
        showPartialOfferings(pageNum);
    });

    /*  This event listener retrieves the
     *  next page of a partial list of
     *  course offerings and displays it in
     *  the system.
    */
    $("#all-offerings-box").on('click', '#offerings-next-page', function()
    {
        var pageNum = $("#offerings-next-page").data("page-num");
        showPartialOfferings(pageNum);
    });

    /*  This event listener submits a form
     *  which includes the course offering
     *  and section to the database.
    */
    $("#add-offering-box").on('click', "#add-offering-submit", function()
    {
        /* Retrieve input from text fields */
        var courseCode = $("#add-offering-course").val();
        var section = $("#add-offering-section").val();

        /* Check if it's not empty and then submit */
        if (courseCode.length == 7 && section.length <= 3)
        {
            createNewOffering(courseCode, section);
        }
    });

    /*
     *
     *  FUNCTION IMPLEMENTATIONS
     *
     */

    /*  This function retrieves a partial list
     *  of course offerings from the database
     *  and displays it in the system.
     */
    function showPartialOfferings(pageNum)
    {
        $.ajax({
            type : "GET",
            url : window.location + "/show-offerings?page=" + pageNum,
            success : function (result)
            {
                if(result.status == "Done")
                {
                    /* TODO?: Code for keeping track of the selected course offering */

                    /* TODO?: Code for removing the currently displayed course offerings */
                    $(".all-offerings-row:not(:first-child)").remove();
                    $(".all-offerings-row-border").remove();
                    $("#all-offerings-page-menu").remove();

                    /* Display each offering into the system */
                    $.each(result.data.currPartialOfferings, function(i, offering)
                    {
                        /* Create individual list items */
                        var startlist = "<ul class='all-offerings-row'>";
                        var course = "<li class='cols-course'>" + offering.courseCode + "</li>";
                        var section = "<li class='cols-section'>" + offering.section + "</li>";
                        var days = "<li class='cols-days'>" + offering.day1 + " " + offering.day2 + "</li>";
                        var timeslot = "<li class='cols-timeslot'>" + offering.startTime + " - " + offering.endTime + "</li>";
                        var room = "<li class='cols-room'>" + offering.roomCode + "</li>";
                        var faculty = "<li class='cols-faculty'>" + offering.facultyName + "</li>";
                        var menu = "<li>" +
                            "<div class='all-offerings-row-popup'>" +
                            "<img src='/images/black-icons/vertical-dot-menu.png' class='all-offerings-row-img' />" +
                            "<div class='all-offerings-dropdown-menu'>" +
                            "<a href='assign-room.html'>Assign Room</a>" +
                            "<a href='#'>Raise Concerns</a>" +
                            "<a href='#'>Edit Section</a>" +
                            "<a href='#'>View More Details</a>" +
                            "<a href='#'>Dissolve Offerings</a>" +
                            "</div></div></li></ul>";
                        var border = "<hr class='all-offerings-row-border' />";

                        var offering_row = startlist + course + section + days + timeslot + room + faculty + menu + border;

                        /* Insert after border */
                        $(offering_row).insertAfter("#all-offerings-header-border");
                    });

                    /* Add Pagination Features into the system */
                    var startPageList = "<ul id='all-offerings-page-menu'>";
                    var prevPage = "<li ";
                    if (result.data.hasPrev == false) { prevPage = prevPage + "class='unavailable-page'"; }
                    if (result.data.hasPrev == true)
                    {
                        prevPage = prevPage + "><a id='offerings-prev-page' data-page-num='" +
                                   (result.data.currPageNum - 1) + "'> <- Prev </a> </li>";
                    }
                    else { prevPage = prevPage + "> <- Prev </li>"; }
                    var currPage = "<li>Page " + (result.data.currPageNum + 1) + " of " + result.data.totalPages + "</li>";
                    var nextPage = "<li ";
                    if (result.data.hasNext == false) { nextPage = nextPage + "class='unavailable-page'"; }
                    if (result.data.hasNext == true)
                    {
                        nextPage = nextPage + "><a id='offerings-next-page' data-page-num='" +
                            (result.data.currPageNum + 1) + "'> Next -> </a> </li>";
                    }
                    else { nextPage = nextPage + "> Next -> </li>"; }
                    var endPageList = "</ul>";

                    var pageList = startPageList + prevPage + currPage + nextPage + endPageList;

                    $(pageList).appendTo("#all-offerings-box");
                }
            },
            error : function(e)
            {
                console.log("ERROR: ", e);
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
            url : window.location + "/create-new-offering",
            data : JSON.stringify(formData),
            dataType : "json",
            success : function(result)
            {
                if(result.status == "Done")
                {
                    /* Reset text fields */
                    $("#add-offering-course").val("");
                    $("#add-offering-section").val("");

                    console.log("Course Offering successfully added");
                }
            },
            error : function(e)
            {
                console.log("Error: " + e);
            }
        })
    }
});
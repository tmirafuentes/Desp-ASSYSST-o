/*
 *
 *  ASSYSTX2 SCRIPT
 *  FOR COLLABORATIVE WORKSPACE
 *
 *  This script is for the collaborative workspace
 *  pages of the ASSYSTX2 system. These includes
 *  functions for course offering management, create
 *  a new offering, concerns management, online users,
 *  and revision history/recent changes.
 *
 */

$(function() {
    /*
     *
     *  ONLOAD FUNCTIONS
     *
     */

    /* Show Partial Offerings */
    showPartialOfferings(0);

    /* Retrieve Options for Filters
    retrieveFilterCourses();
    retrieveFilterTimeslots();
    retrieveFilterBuildings(); */
    retrieveFilterFaculty();

    /*
     *  COURSE OFFERING MANAGEMENT
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

    /*  This event listener marks the
     *  selected course offering as
     *  a special class.
     */
    $("#all-offerings-box").on('click', ".offering-special-class-button", function()
    {
        /* Change UI of specific offering */
        $(this).closest("ul").css("background-color", "blue");
        //$(".all-offerings-row").contains(this).css("background-color", "blue");

        /* Retrieve Course Offering ID */
        var currOffering = $(this).closest("ul").data("offering-id");
        //var currOffering = $(".all-offerings-row").contains(this).data("offering-id");

        /* Perform AJAX */
        var formData = {
            offeringID : currOffering,
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

    /*
     *  COURSE OFFERING MANAGEMENT
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
            url : window.location + "show-offerings?page=" + pageNum,
            success : function(result)
            {
                /* Code for removing the currently displayed course offerings */
                $("form .all-offerings-row").remove();
                $(".all-offerings-row-border").remove();
                $("#all-offerings-page-menu").remove();

                if(result.status === "Empty")
                {
                    var startPageList = "<ul id='all-offerings-page-menu'>" +
                                        "<li class='unavailable-page'>&nbsp;</li>" +
                                        "<li>" + result.data + "</li>" +
                                        "<li class='unavailable-page'>&nbsp;</li>" +
                                        "</ul>";
                    $(startPageList).appendTo("#all-offerings-box");
                }
                else if(result.status === "Done")
                {
                    /* Display each offering into the system */
                    $.each(result.data.currPartialOfferings, function(i, offering)
                    {
                        /* Create individual list items */
                        var startForm = "<form action='/assign-faculty' method='POST' class='assign-faculty-form'>";
                        var startlist = "<ul class='all-offerings-row' data-offering-id='" + offering.offeringID + "'>";
                        var course = "<li class='cols-course'>" + offering.courseCode +
                                     "<input value='" + offering.courseCode + "' name='courseCode' hidden /> </li>";
                        var section = "<li class='cols-section'>" + offering.section +
                                      "<input value='" + offering.section + "' name='section' hidden /> </li>";
                        var days = "<li class='cols-days'>" + offering.day1 + " " + offering.day2 + "</li>";
                        var timeslot = "<li class='cols-timeslot'>" + offering.startTime + " - " + offering.endTime + "</li>";
                        var room = "<li class='cols-room'>" + offering.roomCode + "</li>";
                        var faculty = "<li class='cols-faculty'>" + offering.facultyName + "</li>";

                        /*TODO: Manipulate Menu depending on user */
                        var menu = "<li>" +
                            "<div class='all-offerings-row-popup'>" +
                            "<img src='/images/black-icons/vertical-dot-menu.png' class='all-offerings-row-img' />" +
                            "<div class='all-offerings-dropdown-menu'>" +
                            "<button type='submit' class='offering-assign-faculty-button'>Assign Faculty</button>" +
                            "<button type='button' class='offering-raise-concerns-button'>Raise Concerns</button>" +
                            "<button type='button' class='offering-view-details-button'>View More Details</button>" +
                            "<button type='button' class='offering-special-class-button'>Mark as Service Course</button>" +
                            "</div></div></li></ul></form>";
                        var border = "<hr class='all-offerings-row-border' />";

                        var offering_row = startForm + startlist + course + section + days + timeslot + room + faculty + menu + border;

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
                var startPageList = "<ul id='all-offerings-page-menu'>" +
                    "<li class='unavailable-page'>&nbsp;</li>" +
                    "<li>" + "There is an error loading the course offerings." + "</li>" +
                    "<li class='unavailable-page'>&nbsp;</li>" +
                    "</ul>";
                $(startPageList).appendTo("#all-offerings-box");
            }
        });
    }

    /*
     *  FILTER OFFERINGS
     *  FUNCTION IMPLEMENTATIONS
     *
    */

    /* Load Courses Available For Dropdown Filter */
    function retrieveFilterCourses()
    {
        $.ajax({
            type : "GET",
            url : window.location + "retrieve-filter-courses",
            success : function(result)
            {
                $.each(result.data, function(i, courseCode)
                {
                    courseOption = "<option value='" + courseCode + "'>" +
                                   courseCode + "</option>";
                    $("#filters-course").append(courseOption);
                });
            }
        });
    }

    /* Load Time Slots Available For Dropdown Filter */
    function retrieveFilterTimeslots()
    {
        $.ajax({
            type : "GET",
            url : window.location + "retrieve-filter-timeslots",
            success : function(result)
            {
                $.each(result.data, function(i, timeslot)
                {
                    timeOption = "<option value='" + timeslot + "'>" +
                        timeslot + "</option>";
                    $("#filters-timeslot").append(timeOption);
                });
            }
        });
    }

    /* Load Buildings Available For Dropdown Filter */
    function retrieveFilterBuildings()
    {
        $.ajax({
            type : "GET",
            url : window.location + "retrieve-filter-rooms",
            success : function(result)
            {
                $.each(result.data, function(i, roomCode)
                {
                    roomOption = "<option value='" + roomCode + "'>" +
                        roomCode + "</option>";
                    $("#filters-room").append(roomOption);
                });
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
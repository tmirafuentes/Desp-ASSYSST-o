/*
 *
 *  ASSYSTX2 SCRIPT
 *  FOR HEADER
 *
 *  This script is for the assign room and timeslot
 *  page of the ASSYSTX2 system.
 *
 */

$(function()
{
    /* Retrieve and Update Concerns */
    retrieveRecentConcerns();
    setInterval(function() {
        updateRecentConcerns();
    }, 5000);

    /* Retrieve and Update Recent Changes */
    loadMostRecentChanges();
    setInterval(function() {
        loadMostRecentChanges();
    }, 5000);

    /*
     *  MENU
     *  EVENT LISTENERS
     *
     */

    /* Toggle for User Icon */
    $(".workspace-user-avatar").click(function()
    {
        /* Hide Concerns Dropdown if ever */
        if ($("#header-dropdown-concerns").is(":visible"))
        {
            $("#header-dropdown-concerns").hide();
        }

        /* Show User Dropdown */
        $("#header-dropdown-user").toggle();
    });

    /* Toggle for Inbox Icon */
    $(".workspace-user-inbox").click(function()
    {
        /* Turn off notification */
        $(".header-concerns-notif").hide();

        var location = window.location.href;
        var url = (location.substr(location.length - 1) === "/") ? "disable-concern-notifs" : "/disable-concern-notifs";
        $.ajax({
            type : "POST",
            url : url,
            success : function(result) {}
        });


        if ($("#header-dropdown-user").is(":visible"))
        {
            $("#header-dropdown-user").hide();
        }

        $("#header-dropdown-concerns").toggle();
    });

    // Close dropdowns
    window.onclick = function(event)
    {
        if(!event.target.matches(".workspace-user-icons") &&
            !event.target.matches(".header-dropdown-content img") &&
            !event.target.matches(".header-dropdown-content li a"))
        {
            $(".header-dropdown-content").hide();
        }
    };

    /*
     *  CONCERNS
     *  EVENT LISTENERS
     *
    */

    /*  This event listener marks the
     *  selected concern as acknowledged.
     */
    $(".header-dropdown-content-list").on("click", "img.dropdown-concerns-unacknowledged", function(e)
    {
        if ($(this).closest("li").hasClass("concerns-dropdown-unmarked"))
        {
            /* Get Concern Number */
            var id = "" + $(this).closest("li").find("p.dropdown-concerns-content").data('concern-num');

            var location = window.location.href;
            var url = (location.substr(location.length - 1) === "/") ? "mark-acknowledged-concern" : "/mark-acknowledged-concern";

            $.ajax({
                method : "POST",
                contentType : "application/json",
                data : id,
                dataType : "json",
                url : url,
                success : function(result)
                {
                    if (result.status === "Done")
                    {
                        /* Remove gray background */
                        $(this).closest("li").removeClass("concerns-dropdown-unmarked");

                        console.log("Concern background is changed");

                        /* Switch to gray button */
                        $(this).addClass("dropdown-concerns-acknowledged");
                        $(this).removeClass("dropdown-concerns-unacknowledged");
                        $(this).attr("src", "/images/gray-icons/check-button.png");

                        console.log("Concern is unmarked");
                    }
                },
                error : function(e)
                {
                    console.log("Error: " + e);
                }
            });
        }
    });

    /*  This event listener marks all
     *  of the recent concerns as acknowledged.
     */
    $("#dropdown-concerns-mark-all").click(function()
    {
        var location = window.location.href;
        var url = (location.substr(location.length - 1) === "/") ? "mark-all-recent-concerns" : "/mark-all-recent-concerns";

        $.ajax({
            method : "GET",
            url : window.location + url,
            success : function(result)
            {
                if (result.status === "Done")
                {
                    $("#header-dropdown-concerns li.concerns-dropdown-unmarked")
                        .removeClass("concerns-dropdown-unmarked")
                        .find("img")
                        .addClass("dropdown-concerns-acknowledged")
                        .removeClass("dropdown-concerns-unacknowledged")
                        .attr("src", "/images/gray-icons/check-button.png");
                }
            }
        });
    });

    /*  This event listener opens the concerns modal
     *  with the appropriate receiver.
     */
    $("#all-offerings-table").on('click', ".offering-raise-concerns-button", function()
    {
        /* Find course offering */
        var courseCode = $(this).closest("tr").find("td:nth-child(2)").text();
        var section = $(this).closest("tr").find("td:nth-child(3)").text();

        /* Get Receiver */
        $.ajax({
            method : "POST",
            url : window.location + "retrieve-concerns-receiver",
            data : courseCode,
            success : function(result)
            {
                if(result.status === "Done")
                {
                    /* Assign Receiver */
                    $("#raise-concerns-receiver").val(result.data);

                    /* Assign Course Offering */
                    $("#raise-concerns-offering").val(courseCode + " " + section);

                    $("#raise-concerns-content").text("Hello! I have a concern. ");
                }
            },
            error : function(e)
            {
                console.log("Error: " + e);
            }
        });
    });

    /* This event listener sends a concern from the modal */
    $("#raise-concerns-submit").on('click', function()
    {
        var formData = {
            receiver : $("#raise-concerns-receiver").val(),
            subject : $("#raise-concerns-offering").val(),
            content : $("#raise-concerns-content").val()
        };

        $.ajax({
            method : "POST",
            contentType : "application/json",
            url : window.location + "send-concern",
            data : JSON.stringify(formData),
            dataType : "json",
            success : function(result)
            {
                if(result.status === "Done")
                {
                    displayPositiveMessage(result.data);
                    $("#raise-concerns-receiver").val("");
                    $("#raise-concerns-offering").val("");
                    $("#raise-concerns-content").val("");
                    $("#raise-concerns-modal").modal('close');
                }
            },
            error : function(e)
            {
                console.log("Error: " + e);
            }
        });
    });

    /*
     *  CONCERNS
     *  FUNCTION IMPLEMENTATIONS
     *
    */

    /*  This function loads the 5 most
     *  recent concerns addressed to the user.
     */
    function retrieveRecentConcerns()
    {
        var location = window.location.href;
        var url = (location.substr(location.length - 1) === "/") ? "retrieve-recent-concerns" : "/retrieve-recent-concerns";

        $.ajax({
            method : "GET",
            url : window.location + url,
            success : function(result)
            {
                if(result.status === "Done")
                {
                    $(".header-dropdown-content-list li:not(:nth-child(1))").remove();
                    $(".header-dropdown-content-list hr.dropdown-concerns-border").remove();

                    var unreadCtr = 0;
                    $.each(result.data, function(i, concern)
                    {
                        var senderUpperCase = concern.sender;
                        senderUpperCase = senderUpperCase.toUpperCase() + ": ";
                        var concernText = "<li";
                                        if(concern.acknowledged === false)
                                        {
                                            concernText += " class='concerns-dropdown-unmarked'";
                                            unreadCtr++;
                                        }
                            concernText += ">" +
                                      "<p class='dropdown-concerns-header'>" + senderUpperCase + concern.subject + "</p>" +
                                      "<p class='dropdown-concerns-time'>" + concern.timestamp + "</p>" +
                                      "<p class='dropdown-concerns-content' data-concern-num='" + concern.id + "'>" + concern.content + "</p>";

                            if(concern.acknowledged === false)
                                concernText += "<img class='dropdown-concerns-unacknowledged' src='/images/green-icons/check-button.png' />";
                            else
                                concernText += "<img class='dropdown-concerns-acknowledged' src='/images/gray-icons/check-button.png' />";

                            concernText += "</li>" +
                                           "<hr class='dropdown-concerns-border' />";

                        $(concernText).insertAfter("#concerns-dropdown-header-border");
                    });
                }

                /* Appear notif if ever */
                if(result.message === false)
                {
                    $(".workspace-user-inbox").attr('src', '/images/white-icons/concerns-inbox.png');
                    $(".header-concerns-notif").text(unreadCtr);
                    $(".header-concerns-notif").show();
                }
            }
        });
    }

    /*  This function updates the recent
     *  concerns addressed to the user.
     */
    function updateRecentConcerns()
    {
        var location = window.location.href;
        var url = (location.substr(location.length - 1) === "/") ? "update-recent-concerns" : "/update-recent-concerns";

        $.ajax({
            method : "GET",
            url : window.location + url,
            success : function(result)
            {
                if(result.status === "Done")
                {
                    $("#header-dropdown-concerns ul li:not(:nth-child(1))").remove();
                    $("#header-dropdown-concerns ul hr.dropdown-concerns-border").remove();

                    var unreadCtr = 0;
                    $.each(result.data, function(i, concern)
                    {
                        var senderUpperCase = concern.sender;
                        senderUpperCase = senderUpperCase.toUpperCase() + ": ";
                        var concernText = "<li";
                        if(concern.acknowledged === false)
                        {
                            concernText += " class='concerns-dropdown-unmarked'";
                            unreadCtr++;
                        }
                        concernText += ">" +
                            "<p class='dropdown-concerns-header'>" + senderUpperCase + concern.subject + "</p>" +
                            "<p class='dropdown-concerns-time'>" + concern.timestamp + "</p>" +
                            "<p class='dropdown-concerns-content' data-concern-num='" + concern.id + "'>" + concern.content + "</p>";

                        if(concern.acknowledged === false)
                            concernText += "<img class='dropdown-concerns-unacknowledged' src='/images/green-icons/check-button.png' />";
                        else
                            concernText += "<img class='dropdown-concerns-acknowledged' src='/images/gray-icons/check-button.png' />";

                        concernText += "</li>" +
                            "<hr class='dropdown-concerns-border' />";

                        $(concernText).insertAfter("#concerns-dropdown-header-border");
                    });
                }

                /* Appear notif if ever */
                if(result.message === false)
                {
                    $(".workspace-user-inbox").attr('src', '/images/white-icons/concerns-inbox.png');
                    $(".header-concerns-notif").text(unreadCtr);
                    $(".header-concerns-notif").show();
                }
            }
        });
    }

    /*
     *  WORKSPACE HISTORY
     *  FUNCTION IMPLEMENTATIONS
     *
    */

    $("#recent-changes-button").click(function()
    {
        $(".recent-changes-row").toggle("fast");
        $(".recent-changes-row-border").toggle("fast");
        if($("#recent-changes-box").hasClass("closed"))
        {
            $("#recent-changes-box").removeClass("closed");
            $(this).attr("src", "/images/black-icons/sort-up.png");
        }
        else
        {
            $("#recent-changes-box").addClass("closed");
            $(this).attr("src", "/images/black-icons/sort-down.png");
        }
    });

    /* Load most recent changes */
    function loadMostRecentChanges()
    {
        var location = window.location.href;
        var url = (location.substr(location.length - 1) === "/") ? "retrieve-recent-changes" : "/retrieve-recent-changes";

        $.ajax({
            type : "GET",
            url : url,
            success : function(result)
            {
                /* Remove past changes */
                $(".recent-changes-row").remove();
                $(".recent-changes-row-border").remove();

                $.each(result.data, function(i, change)
                {
                    var startList = "<ul class='recent-changes-row'>";
                    var subject = "<li>" + change.subject + "</li>";
                    var revision = "<li>by " + change.fullName + " ";

                    /* Format Timestamp representation */

                    /* Get Times */
                    var revisionDate = new Date(change.timestamp).getTime();
                    var currDate = new Date().getTime();

                    /* Get Difference */
                    var timeDifference = currDate - revisionDate;

                    /* Get appropriate string for time */
                    if (timeDifference < 60000) // Less than a minute
                    {
                        revision += "a few seconds ago ";
                    } else if (timeDifference >= 60000 && timeDifference < 3600000) // Less than an hour
                    {
                        var tempTime = Math.floor(timeDifference / 60000);
                        revision += tempTime + " minute";
                        if (tempTime > 1)
                            revision += "s";
                        revision += " ago ";
                    } else if (timeDifference >= 3600000 && timeDifference < 86400000) // Less than a day
                    {
                        var tempTime = Math.floor(timeDifference / 3600000);
                        revision += tempTime + " hour";
                        if (tempTime > 1)
                            revision += "s";
                        revision += " ago ";
                    } else if (timeDifference >= 86400000 && timeDifference < 2678400000) // Less than a month or 30 days
                    {
                        var tempTime = Math.floor(timeDifference / 86400000);
                        revision += tempTime + " day";
                        if (tempTime > 1)
                            revision += "s";
                        revision += " ago ";
                    } else
                    {
                        var revDateAgain = new Date(result.data.timestamp);
                        revision += "at " + revDateAgain.toLocaleDateString() + " ";
                    }

                    var endList = "</ul><hr class='recent-changes-row-border' />";
                    var entryChange = startList + subject + revision + endList;

                    $(entryChange).insertAfter("#recent-changes-header-border");
                });

                /* Check if visible or not */
                if (!$("#recent-changes-box").hasClass("closed"))
                {
                    $(".recent-changes-row").show();
                    $(".recent-changes-row-border").show();
                }
            }
        })
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
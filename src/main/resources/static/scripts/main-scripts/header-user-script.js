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
    retrieveRecentConcerns();
    setInterval(function() {
        retrieveRecentConcerns();
    }, 5000);

    /*
     *  MENU
     *  EVENT LISTENERS
     *
     */

    // Toggles for dropdowns
    $(".workspace-user-avatar").click(function(){
        /* Hide Concerns Dropdown if ever */
        if ($("#header-dropdown-concerns").is(":visible"))
        {
            $(".workspace-user-inbox").attr("src", "/images/black-icons/concerns-inbox.png");
            $("#header-dropdown-concerns").hide();
        }

        /* Change Image */
        if(!$("#header-dropdown-user").is(":visible"))
            $(".workspace-user-avatar").attr("src", "/images/white-icons/user-avatar.png");
        else
            $(".workspace-user-avatar").attr("src", "/images/black-icons/user-avatar.png");

        /* Show User Dropdown */
        $("#header-dropdown-user").toggle();
    });
    $(".workspace-user-inbox").click(function(){
        $(".header-concerns-notif").hide();

        if ($("#header-dropdown-user").is(":visible"))
        {
            $(".workspace-user-avatar").attr("src", "/images/black-icons/user-avatar.png");
            $("#header-dropdown-user").hide();
        }

        /* Change Image */
        if(!$("#header-dropdown-concerns").is(":visible"))
            $(".workspace-user-inbox").attr("src", "/images/white-icons/concerns-inbox.png");
        else
            $(".workspace-user-inbox").attr("src", "/images/black-icons/concerns-inbox.png");

        $("#header-dropdown-concerns").toggle();
    });

    // Close dropdowns
    window.onclick = function(event)
    {
        if(!event.target.matches(".workspace-user-icons") &&
            !event.target.matches(".header-dropdown-content li"))
        {
            $(".header-dropdown-content").hide();
            $(".workspace-user-inbox").attr("src", "/images/black-icons/concerns-inbox.png");
            $(".workspace-user-avatar").attr("src", "/images/black-icons/user-avatar.png");
        }
    }

    /*
     *  CONCERNS
     *  EVENT LISTENERS
     *
    */

    // Mark as acknowledged
    $(".header-dropdown-content-list").on("click", "li:not(:nth-child(1))", function(e)
    {
        e.preventDefault();

        /* TODO: FIX CONCERNS DROPDOWN ACKNOWLEDGE */
        if ($(this).hasClass("concerns-dropdown-unmarked"))
        {
            /* Get Concern Number */
            var id = "" + $(this).data('concern-num');

            console.log("ID Num = " + id);

            /*
            $.ajax({
                method : "POST",
                data : id,
                url : window.location + "/mark-acknowledged-concern",
                success : function(result)
                {
                    if (result.status === "Done")
                    {
                        $(this).removeClass("concerns-unacknowledged");
                    }
                },
                error : function(e)
                {
                    console.log("Error: " + e);
                }
            });*/
        }

        //$(this).parent("li").removeClass("concerns-dropdown-unmarked");
    });

    /* Mark all as acknowledged */
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
                    $("#header-dropdown-concerns li.concerns-dropdown-unmarked").removeClass("concerns-dropdown-unmarked");
            }
        });
    });

    /*  This event listener opens the concerns modal
     *  with the appropriate receiver.
     */
    $("#all-offerings-table").on('click', ".offering-raise-concerns-button", function(){
        /* Find course offering */
        var courseCode = $(this).closest("tr").find("td:nth-child(1)").text();
        var section = $(this).closest("tr").find("td:nth-child(2)").text();

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
                }
            },
            error : function(e)
            {
                console.log("Error: " + e);
            }
        });
    });

    /* This event listener sends a concern from the modal */
    $("#raise-concerns-submit").on('click', function(){
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
                                      "<p class='dropdown-concerns-content' data-concern-num='" + concern.id + "'>" + concern.content + "</p>" +
                                      "</li>" +
                                      "<hr class='dropdown-concerns-border' />";

                        $(concernText).insertAfter("#concerns-dropdown-header-border");
                    });

                    // Shorten concerns dropdown text
                    $(".dropdown-concerns-content").shorten({
                        chars: 40,
                        more: "See More",
                        less: "See Less",
                        ellipses: '...'
                    });

                    /* Appear notif if ever */
                    if(unreadCtr > 0)
                    {
                        $(".workspace-user-inbox").attr('src', '/images/white-icons/concerns-inbox.png');
                        $(".header-concerns-notif").show();
                    }
                }
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
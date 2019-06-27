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

    /*
     *
     *  EVENT LISTENERS
     *
     */
    // Shorten concerns dropdown text
    $(".dropdown-concerns-content").shorten({
        chars: 40,
        more: "See More",
        less: "See Less",
        ellipses: '.....'
    });

    // Mark as acknowledged
    $(".dropdown-concerns-content").on("shorten::expand", function(e){
        e.preventDefault();
        $(this).parent("li").removeClass("concerns-dropdown-unmarked");
    });

    /* Mark all as acknowledged */
    $("#dropdown-concerns-mark-all").click(function(){
        $("#header-dropdown-concerns li.concerns-dropdown-unmarked").removeClass("concerns-dropdown-unmarked");
    });

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
            url : "/retrieve-concerns-receiver",
            data : courseCode,
            success : function(result)
            {
                if(result.status === "Done")
                {
                    console.log("What");

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

        console.log(formData);

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
                    console.log("Hi vlog");
                    displayPositiveMessage(result.data);
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
        $.ajax({
            method : "GET",
            url : window.location + "retrieve-recent-concerns",
            success : function(result)
            {
                if(result.status === "Done")
                {
                    $.each(result.data, function(i, concern)
                    {
                        var concernText = "<li";
                                        if(concern.acknowledged == false)
                                            concernText += " class='concerns-dropdown-unmarked'";
                            concernText += ">" +
                                      "<p class='dropdown-concerns-header'>" + concern.subject + "</p>" +
                                      "<p class='dropdowns-concerns-time'>8:57 PM</p>" +
                                      "<p class='dropdown-concerns-content'>" + concern.content + "</p>" +
                                      "</li>" +
                                      "<hr />";

                        $(concernText).insertAfter("#concerns-dropdown-header-border");
                    });
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
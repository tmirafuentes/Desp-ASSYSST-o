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


});
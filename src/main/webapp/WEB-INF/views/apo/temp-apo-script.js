$(function()
{
    /*
     *
     * MODAL FUNCTIONALITY
     *
     */

    /* New Modals Code */
    $(".assystx-workspace-modals").hide();

    $(".assystx-workspace-modals").dialog({
        autoOpen: false
    });

    /* Add New Offering Modal */
    $(".workspace-add-offering-button").tooltip();

    /* Open Add NEw Offering Modal */
    $(".workspace-add-offering-button").click(function() {
        $(".assystx-workspace-add-offering-modal").dialog({
            title:"Add New Course Offering",
            width:500,
            height:400,
            modal:true
        });
        $(".assystx-workspace-add-offering-modal").dialog("open");
    });

    $("#workspace-navigation-add-offering").click(function() {
        $(".assystx-workspace-add-offering-modal").dialog({
            title:"Add New Course Offering",
            width:500,
            height:400,
            modal:true,
            buttons: {
                "Add": function() {
                    $( this ).dialog( "close" );
                },
                Cancel: function() {
                    $( this ).dialog( "close" );
                }
            }
        });
        $(".assystx-workspace-add-offering-modal").dialog("open");
    });

    $(".workspace-add-more-button").click(function()
    {
        $(".workspace-add-offering-instance").last().clone().prependTo(".add-offering-divider-container");
    })

    /*
     *
     * SIDEBAR FUNCTIONALITY
     *
     */

    /* Selecting the hamburger menu */
    $(".workspace-menu-img").click(function()
    {
        openLeftSidebar();
    });

    /* Closing the hamburger menu */
    $(".workspace-navigation-close").click(function()
    {
       closeLeftSidebar();
    });

   /* Selecting a course offering */
    $(".workspace-main-modify-button").click(function()
    {
        /* Check if selected already */
        if($(this).parent().parent().parent().hasClass("selected-offering"))
        {
            closeRightSidebar();
            $(this).parent().parent().parent().removeClass("selected-offering");
            $(this).parent().parent().parent().css({"background-color" : "#e0e0e0"});
            $(this).parent().parent().parent().css({"color" : "#2b2b2b"});
            $(this).css({"background-color" : "#3cb878"});
            $(this).css({"color" : "#f6f6f6"});
        }
        else
        {
            openRightSidebar();
            $(".workspace-main-rows-single").removeClass("selected-offering");
            $(".workspace-main-rows-single").css({"background-color" : "#e0e0e0"});
            $(".workspace-main-rows-single").css({"color" : "#2b2b2b"});
            $(".workspace-main-rows-single button").css({"background-color" : "#3cb878"});
            $(".workspace-main-rows-single button").css({"color" : "#f6f6f6"});
            $(this).parent().parent().parent().addClass("selected-offering");
            $(this).parent().parent().parent().css({"background-color" : "#3cb878"});
            $(this).parent().parent().parent().css({"color" : "#f6f6f6"});
            $(this).css({"background-color" : "#f6f6f6"});
            $(this).css({"color" : "#3cb878"});
        }
    });

    /* Closing the modify course offering */
    $("#modify-offering-cancel-button").click(function()
    {
        closeRightSidebar();
        $(".workspace-main-rows-single").removeClass("selected-offering");
        $(".workspace-main-rows-single").css({"background-color" : "#e0e0e0"});
        $(".workspace-main-rows-single").css({"color" : "#2b2b2b"});
        $(".workspace-main-rows-single button").css({"background-color" : "#3cb878"});
        $(".workspace-main-rows-single button").css({"color" : "#f6f6f6"});
    });

    $(".workspace-main-rows-single").hover(function()
    {
        $(this).css({"background-color" : "#3cb878"});
        $(this).css({"color" : "#f6f6f6"});
        $(this).find(".workspace-main-modify-button").css({"background-color" : "#f6f6f6"});
        $(this).find(".workspace-main-modify-button").css({"color" : "#3cb878"});
    }, function()
    {
        if (!$(this).hasClass("selected-offering"))
        {
            $(".workspace-main-rows-single").css({"background-color" : "#e0e0e0"});
            $(".workspace-main-rows-single").css({"color" : "#2b2b2b"});
            $(".workspace-main-rows-single button").css({"background-color" : "#3cb878"});
            $(".workspace-main-rows-single button").css({"color" : "#f6f6f6"});
        }
    });


    /*
     *
     * ANIMATION
     * FUNCTIONS
     *
     */

    function openLeftSidebar()
    {
        closeRightSidebar();
        document.getElementById("assystx-workspace-leftSidebar").style.width = "18%";
        document.getElementById("workspace-menu").style.marginLeft = "19%";
    }

    function closeLeftSidebar()
    {
        document.getElementById("assystx-workspace-leftSidebar").style.width = "0";
        document.getElementById("workspace-menu").style.marginLeft = "5%";
    }

    function openRightSidebar()
    {
        closeLeftSidebar();
        document.getElementById("assystx-workspace-rightSidebar").style.width = "25%";
        document.getElementById("assystx-workspace-rightSidebar").style.paddingLeft = "25px";
        document.getElementById("assystx-workspace-content").style.left = "10%";
    }

    function closeRightSidebar()
    {
        document.getElementById("assystx-workspace-rightSidebar").style.width = "0";
        document.getElementById("assystx-workspace-rightSidebar").style.paddingLeft = "0";
        document.getElementById("assystx-workspace-content").style.left = "20%";
    }
});
$(function()
{
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
            $(this).css({"background-color" : "#3cb878"});
            $(this).css({"color" : "#f6f6f6"});
        }
        else
        {
            openRightSidebar();
            $(".workspace-main-rows-single").removeClass("selected-offering");
            $(".workspace-main-rows-single").css({"background-color" : "#e0e0e0"});
            $(".workspace-main-rows-single button").css({"background-color" : "#3cb878"});
            $(".workspace-main-rows-single button").css({"color" : "#f6f6f6"});
            $(this).parent().parent().parent().addClass("selected-offering");
            $(this).parent().parent().parent().css({"background-color" : "#3cb878"});
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
        $(".workspace-main-rows-single button").css({"background-color" : "#3cb878"});
        $(".workspace-main-rows-single button").css({"color" : "#f6f6f6"});
    });

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
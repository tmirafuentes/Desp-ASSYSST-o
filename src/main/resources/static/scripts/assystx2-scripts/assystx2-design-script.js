/*
 *
 *  ASSYSTX2 DESIGN SCRIPT
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
    *   ONLOAD FUNCTIONS
    *   AND VARIABLES
    *
    */
    var allCourseCodes = [];
    console.log("I'm here");
    retrieveAllCourses();
    console.log("And I finished ");
    console.log(allCourseCodes.length);

    /*
     *
     *  EVENT LISTENERS
     *
     */

    /* Course Code field suggests possible
       courses from the typed input */
    $("#add-offering-course").autocomplete({
        source : allCourseCodes
    });

    /* Add Offering partition's front-end
     * functionality of appearing and disappearing
     * text fields */
    $("#add-offering-course").focusin(function(e)
    {
        /* Change color of course code */
        $("#add-offering-course").css("background-color", "#ffffff");

        /* Make the other fields appear */
        $("#add-offering-section").css("visibility", "visible");
        $("#add-offering-room").css("visibility", "visible");
        $("#add-offering-submit").css("visibility", "visible");
    });

    $("#add-offering-course").focusout(function(e)
    {
        /* Make the other fields disappear unless there is content */
        if ($("#add-offering-course").val() == "")
        {
            /* Change color of course code */
            $("#add-offering-course").css("background-color", "#00e08e");

            $("#add-offering-section").css("visibility", "hidden").fadeTo(2000);
            $("#add-offering-section").val("");
            $("#add-offering-room").css("visibility", "hidden").fadeTo(2000);
            $("#add-offering-submit").css("visibility", "hidden");
        }
    });


    /*
     *
     *  FUNCTION IMPLEMENTATIONS
     *
     */

    /* Retrieve all course codes from the database */
    function retrieveAllCourses()
    {
        /* Perform AJAX */
        $.ajax({
            type : "GET",
            url : window.location +  "/autocomplete-course-code",
            success : function(result)
            {
                if(result.status == "Done")
                    returnAllCourses(result.data);
            },
            error : function(e)
            {
                console.log("Error = " + e);
            }
        });
    }

    function returnAllCourses(response)
    {
        $.each(response.suggestedCourses, function(i, courses)
        {
            allCourseCodes.push(courses);
            console.log("I am filling up = " + allCourseCodes.length);
        });
    }
});
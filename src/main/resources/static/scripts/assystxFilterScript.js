$(function() {

/*Search Course for modals*/
$("#button_search_course").click(function(){
    var textSearched = $.trim($("#modal_input_search_course").val())
    $(".modal_generated_course_offerings").each(function () {
        var courseTraverse = $(':first-child', this).text();
        courseTraverse = courseTraverse.replace("+", "");
        courseTraverse = $.trim(courseTraverse);
        if(!checkSearch(textSearched.toUpperCase(), courseTraverse.toUpperCase()))
            $(this).hide();
        alert("huh")
    });
});

/* General Function for Filters*/
    function displayFilter()
    {
        $(".filterForms").change(function() {
            //get all values of filterforms
            //checked via not all
            //alert("hello");
            //checkTimeblock();//check timeblock filter
            //checkRoomType();//check room type filter
            //checkClassType(); //check class type filter
            //checkTerm(); //check class type filter
            /*
            if(countallRows() <= 0)
            {
                console.log("Show");
                $(".filter_comments").show();
            }

            else
            {
                console.log("Hide");
                $(".filter_comments").hide();
            }
            */
        });
    }

function checkTimeblock()
{
    var filterData = $("#select_left_timeblock").val();
    if(filterData != "All")
    {
        console.log("it's going in");
        /* Perform AJAX */
        $.ajax({
            type: 'POST',
            contentType : 'application/json',
            url : window.location + "/time-filter",
            data : JSON.stringify(filterData),
            dataType : 'json',
            success : function(result)
            {
                if(result.status == "Done") {
                    $(".filter_comments").hide();
                    retrieveFilteredOfferings();
                    console.log("it's going in2");
                }
                else{
                    $(".filter_comments").show();
                }
            },
            error : function(e)
            {
                alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    }
    else{
        showallRows();
        if($("#select_view_offerings").val() != "All")
            checkTerm();
        if($("#select_left_class_type").val() != "All")
            checkClassType();
        if($("#select_room_type").val() != "All")
            checkRoomType();
    }
}

function checkRoomType()
{
    var filterData = $("#select_room_type").val();
    if(filterData != "All")
    {
        console.log("it's going in");
        /* Perform AJAX */
        $.ajax({
            type: 'POST',
            contentType : 'application/json',
            url : window.location + "/type-filter",
            data : JSON.stringify(filterData),
            dataType : 'json',
            success : function(result)
            {
                if(result.status == "Done") {
                    $(".filter_comments").hide();
                    retrieveFilteredOfferings();
                    console.log("it's going in2");
                }
                else{
                    $(".filter_comments").show();
                }
            },
            error : function(e)
            {
                alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    }
    else{
        showallRows();
        if($("#select_view_offerings").val() != "All")
            checkTerm();
        if($("#select_left_class_type").val() != "All")
            checkClassType();
        if(($("#select_left_timeblock").val() != "All"))
            checkTimeblock();
    }
}

function checkClassType()
{
    var filterData = $("#select_left_class_type").val();
    if(filterData != "All")
    {
        console.log("it's going in");
        /* Perform AJAX */
        $.ajax({
            type: 'POST',
            contentType : 'application/json',
            url : window.location + "/type-filter",
            data : JSON.stringify(filterData),
            dataType : 'json',
            success : function(result)
            {
                if(result.status == "Done") {
                    $(".filter_comments").hide();
                    retrieveFilteredOfferings();
                    console.log("it's going in2");
                }
                else{
                    $(".filter_comments").show();
                }
            },
            error : function(e)
            {
                alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    }
    else{
        console.log(countallRows());
        showallRows();
        if($("#select_view_offerings").val() != "All")
            checkTerm();
        if($("#select_room_type").val() != "All")
            checkRoomType();
        if(($("#select_left_timeblock").val() != "All"))
            checkTimeblock();
    }
}
    /* Retrieve All Course Offerings GET Ajax */
    function retrieveFilteredOfferings()
    {
        //console.log("Happening");
        /* Perform AJAX */
        $.ajax({
            type : "GET",
            url : window.location + "/get-filtered-offerings",
            success : function(result)
            {
                if(result.status == "Done")
                {
                    console.log("Entering this shit");
                    /* Remove All The Previous Offerings */
                    $(".cwofferings .generatedContent .genContentRows:not(:first-child)").remove();

                    /* For Each Offering */
                    $.each(result.data, function(i, offering)
                    {
                        /* Create Divs */
                        var courseCode = "<div class='genContentCols'>" + offering.courseCode + "</div>";
                        var section = "<div class='genContentCols'>" + offering.classSection + "</div>";
                        var days = (offering.day1 != '-') ? "<div class='genContentCols'>" + offering.day1 + " " + offering.day2 + "</div>"
                            : "<div class='genContentCols'>None</div>";
                        var time = (offering.startTime != ':') ? "<div class='genContentCols'>" + offering.startTime + "-" + offering.endTime + "</div>"
                            : "<div class='genContentCols'>Unassigned</div>";
                        var room = "<div class='genContentCols'>" + offering.roomCode + "</div>";
                        var faculty = "<div class='genContentCols'>" + offering.faculty + "</div>";

                        var offeringRow = "<div class='genContentRows'>" +
                            "" + courseCode + section + days + time + room + faculty +
                            "</div>";

                        /* Add to UI */
                        //$(offeringRow).append(courseCode, section, days, time, room, faculty);
                        $(".cwofferings .generatedContent").append(offeringRow);
                    });
                    //console.log("Pumapasok");
                }
            },
            error : function(e)
            {
                //alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    }
function checkTerm()
{
    var filterData = $("#select_view_offerings").val();//gets value for the filter
    if(filterData != "All") {
        /* Prepare Form Data */
        var filterData = $("#select_view_offerings").val();
        console.log("it's going in");
        /* Perform AJAX */
        $.ajax({
            type: 'POST',
            contentType : 'application/json',
            url : window.location + "/term-filter",
            data : JSON.stringify(filterData),
            dataType : 'json',
            success : function(result)
            {
                if(result.status == "Done") {
                    $(".filter_comments").hide();
                    retrieveFilteredOfferings();
                    console.log("it's going in2");
                }
                else{
                    $(".filter_comments").show();
                }
            },
            error : function(e)
            {
                alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    }
    else{
        //showallRows();
        showOfferings();
        if($("#select_left_class_type").val() != "All")
            checkClassType();
        if($("#select_room_type").val() != "All")
            checkRoomType();
        if(($("#select_left_timeblock").val() != "All"))
            checkTimeblock();
    }

}
    function showOfferings()
    {
        /* Perform AJAX */
        $.ajax({
            type : "GET",
            url : window.location + "/show-offerings",
            success : function(result)
            {
                if(result.status == "Done")
                {
                    /* Remove All The Previous Offerings */
                    $(".cwofferings .generatedContent .genContentRows:not(:first-child)").remove();

                    /* For Each Offering */
                    $.each(result.data, function(i, offering)
                    {
                        /* Create Divs */
                        var courseCode = "<div class='genContentCols'>" + offering.courseCode + "</div>";
                        var section = "<div class='genContentCols'>" + offering.classSection + "</div>";
                        var days = (offering.day1 != '-') ? "<div class='genContentCols'>" + offering.day1 + " " + offering.day2 + "</div>"
                            : "<div class='genContentCols'>None</div>";
                        var time = (offering.startTime != ':') ? "<div class='genContentCols'>" + offering.startTime + "-" + offering.endTime + "</div>"
                            : "<div class='genContentCols'>Unassigned</div>";
                        var room = "<div class='genContentCols'>" + offering.roomCode + "</div>";
                        var faculty = "<div class='genContentCols'>" + offering.faculty + "</div>";

                        var offeringRow = "<div class='genContentRows'>" +
                            "" + courseCode + section + days + time + room + faculty +
                            "</div>";

                        /* Add to UI */
                        //$(offeringRow).append(courseCode, section, days, time, room, faculty);
                        $(".cwofferings .generatedContent").append(offeringRow);
                    });
                    //alert("It's done mah n-word");
                }
            },
            error : function(e)
            {
                //alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    }
function showallRows()
{
    $(".genContentRows").each(function () {
        $(this).show();
    });
}
function showallCourseModalRows()
{
    $(".modal_generated_course_offerings").each(function () {
        $(this).show();
    });
}
function countallRows()
{
    var numberCtr = 0;
    $(".genContentRows:visible").not(":first").each(function () {
        numberCtr++;
    });
    return numberCtr;
}

/* Class Day Filters*/

/*Filtering by class day Monday*/
$("#class_m").click(function() {
    var totalResponses = 0;
    $(".filter_comments").hide();
    $(".genContentRows").each(function () {
        $(this).show();
        totalResponses++;
    });
    $(".genContentRows").each(function () {
        //var iterator = $.trim($(this).find("#off_counter").val());
        var textInside = $.trim($(':nth-child(3)', this).text())
        textInside = textInside.replace(/\s+/g, '');
        if(!textInside.includes("M"))
        {
            totalResponses--;
            $(this).hide();
        }

    });
    $(".genContentRows:first-child").show();
    console.log(totalResponses);
    if(totalResponses == 0) {
        $(".filter_comments").show();

    }
});
/*Filtering by class day Tuesday*/
$("#class_t").click(function() {
    var totalResponses = 0;
    $(".filter_comments").hide();
    $(".genContentRows").each(function () {
        $(this).show();
        totalResponses++;
    });
    $(".genContentRows").each(function () {
        //var iterator = $.trim($(this).find("#off_counter").val());
        var textInside = $.trim($(':nth-child(3)', this).text())
        textInside = textInside.replace(/\s+/g, '');
        if(!textInside.includes("T"))
        {
            totalResponses--;
            $(this).hide();
        }
    });
    $(".genContentRows:first-child").show();
    console.log(totalResponses);
    if(totalResponses == 0) {
        $(".filter_comments").show();

    }
});
/*Filtering by class day Wednesday*/
$("#class_w").click(function() {
    var totalResponses = 0;
    $(".filter_comments").hide();
    $(".genContentRows").each(function () {
        $(this).show();
        totalResponses++;
    });
    $(".genContentRows").each(function () {
        //var iterator = $.trim($(this).find("#off_counter").val());
        var textInside = $.trim($(':nth-child(3)', this).text())
        textInside = textInside.replace(/\s+/g, '');
        if(!textInside.includes("W"))
        {
            totalResponses--;
            $(this).hide();
        }
    });
    $(".genContentRows:first-child").show();
    console.log(totalResponses);
    if(totalResponses == 0) {
        $(".filter_comments").show();

    }
});
/*Filtering by class day Thursday*/
$("#class_h").click(function() {
    var totalResponses = 0;
    $(".filter_comments").hide();
    $(".genContentRows").each(function () {
        $(this).show();
        totalResponses++;
    });
    $(".genContentRows").each(function () {
        //var iterator = $.trim($(this).find("#off_counter").val());
        var textInside = $.trim($(':nth-child(3)', this).text())
        textInside = textInside.replace(/\s+/g, '');
        if(!textInside.includes("H"))
        {
            totalResponses--;
            $(this).hide();
        }
    });
    $(".genContentRows:first-child").show();
    console.log(totalResponses);
    if(totalResponses == 0) {
        $(".filter_comments").show();

    }
});
/*Filtering by class day Friday*/
$("#class_f").click(function() {
    var totalResponses = 0;
    $(".filter_comments").hide();
    $(".genContentRows").each(function () {
        $(this).show();
        totalResponses++;
    });
    $(".genContentRows").each(function () {
        //var iterator = $.trim($(this).find("#off_counter").val());
        var textInside = $.trim($(':nth-child(3)', this).text())
        textInside = textInside.replace(/\s+/g, '');
        if(!textInside.includes("F"))
        {
            totalResponses--;
            $(this).hide();
        }
    });
    if(totalResponses == 1) {
        $(".filter_comments").show();

    }
});
/*Filtering by class day Saturday*/
$("#class_s").click(function() {
    var totalResponses = 0;
    $(".filter_comments").hide();
    $(".genContentRows").each(function () {
        $(this).show();
        totalResponses++;
    });

    $(".genContentRows").each(function () {
        var textInside = $.trim($(':nth-child(3)', this).text())
        textInside = textInside.replace(/\s+/g, '');
        if(!textInside.includes("S"))
        {
            totalResponses--;
            $(this).hide();
        }
    });
    $(".genContentRows:first-child").show();
    console.log(totalResponses);
    if(totalResponses == 0) {
        $(".filter_comments").show();

    }
    });
    $("#submit_left_side_search").click(function(){
        var textSearched = $.trim($("#input_search_course").val())
        console.log(textSearched)
        $(".filter_comments").hide();

        showallRows();
        /*
        $(".genContentRows:visible").each(function () {
            var courseTraverse = $.trim($(':first-child', this).text())
            console.log(courseTraverse)
            if(!checkSearch(textSearched.toUpperCase(), courseTraverse.toUpperCase()))
                $(this).hide();
        });
         */
        console.log("it's going in");
        /* Perform AJAX */
        $.ajax({
            type: 'POST',
            contentType : 'application/json',
            url : window.location + "/search",
            data : JSON.stringify(filterData),
            dataType : 'json',
            success : function(result)
            {
                if(result.status == "Done") {
                    $(".filter_comments").hide();
                    retrieveFilteredOfferings();
                    console.log("it's going in2");
                }
                else{
                    $(".filter_comments").show();
                }
            },
            error : function(e)
            {
                alert("Error!");
                console.log("ERROR: ", e);
            }
        });
        /*
        if(countallRows() == 0) {
            $(".filter_comments").show();

        }
        */
    });

    function checkSearch(searchedString, currentString){
        if(searchedString == currentString)
            return true;
        else if(currentString.includes(searchedString))
            return true;
        else
            return false;
    }
});
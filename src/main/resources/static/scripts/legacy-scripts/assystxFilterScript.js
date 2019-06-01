$(function() {
    var day_clicked = false;
    var search_selected = false;
    var search_fac = false;
    showOfferings();
    showFacultyLoad();
    setInterval(function(){

        console.log("Updating the System")

        if(!window.location.href.indexOf("manage") > -1) {
            if (!search_selected) {
                if (day_clicked) {
                    retrieveDayFilter();
                } else {
                    if (checkFilters()) {
                        retrieveFilteredOfferings();
                    } else {
                        showOfferings();
                    }
                }
            }
        }
        else{
            if(!search_fac)
                showFacultyLoad();
        }



        //if(monday_clicked == true)

    }, 15000);
    /* Function to reconstruct the string */
    function orderDays(days)
    {
        var schoolDays = ['M', 'T', 'W', 'H', 'F', 'S', '-'];
        days = find_unique_characters(days);
        var newDays = "";

        for (var x = 0; x < days.length; x++)
            if(x < days.length-1)
                if(schoolDays.indexOf(days.charAt(x)) > schoolDays.indexOf(days.charAt(x+1)))
                    newDays = days.charAt(x + 1) + " " + days.charAt(x);
        if(newDays == "")
            return days.replace(/\s+/g, '');
        else
            return newDays.replace(/\s+/g, '');

    }
    function checkFilters(){
        if($("#select_view_offerings").val() != "All" ||  $("#select_left_class_type").val() != "All" || $("#select_room_type").val() != "All" || $("#select_left_timeblock").val() != "All")
            return true;
        return false;
    }
    function find_unique_characters(str) {
        var unique = '';
        for (var i = 0; i < str.length; i++) {
            if(unique.indexOf(str.charAt(i))==-1)
                unique += str[i];
        }
        return unique;
    }
//FIND A WAY TO CALL THE DISPLAY FILTER FUNCTION BETWEEN THE SHOWOFFERING
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
    /*
    textSearched.toUpperCase()
    $.ajax({
        type : "POST",
        contentType : 'application/json',
        data : JSON.stringify(textSearched),
        dataType : 'json',
        url : window.location + "/search-course",
        success : function(result)
        {
            console.log("Successfully Filtered");
            //retrieveDayFilter();
        },
        error : function(e)
        {
            alert("Error!");
            console.log("ERROR: ", e);
        }
    });*/
    });
});

/* Day Filters*/
    $("#class_m").click(function(){
        console.log("Monday is clicked")
        day_clicked = true;
        search_selected = false;
        /* Perform AJAX */
        $.ajax({
            type : "POST",
            contentType : 'application/json',
            data : JSON.stringify('M'),
            dataType : 'json',
            url : window.location + "/filter-days",
            success : function(result)
            {
                console.log("Successfully Filtered");
                retrieveDayFilter();
            },
            error : function(e)
            {
                alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    });
    $("#class_t").click(function(){
        console.log("Tuesday is clicked")
        day_clicked = true;
        search_selected = false;
        /* Perform AJAX */
        $.ajax({
            type : "POST",
            contentType : 'application/json',
            data : JSON.stringify('T'),
            dataType : 'json',
            url : window.location + "/filter-days",
            success : function(result)
            {
                console.log("Successfully Filtered");
                retrieveDayFilter();
            },
            error : function(e)
            {
                alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    });
    $("#class_w").click(function(){
        console.log("Wednesday is clicked")
        day_clicked = true;
        search_selected = false;
        /* Perform AJAX */
        $.ajax({
            type : "POST",
            contentType : 'application/json',
            data : JSON.stringify('W'),
            dataType : 'json',
            url : window.location + "/filter-days",
            success : function(result)
            {
                console.log("Successfully Filtered");
                retrieveDayFilter();
            },
            error : function(e)
            {
                alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    });
    $("#class_h").click(function(){
        console.log("Thursday is clicked")
        day_clicked = true;
        search_selected = false;
        /* Perform AJAX */
        $.ajax({
            type : "POST",
            contentType : 'application/json',
            data : JSON.stringify('H'),
            dataType : 'json',
            url : window.location + "/filter-days",
            success : function(result)
            {
                console.log("Successfully Filtered");
                retrieveDayFilter();
            },
            error : function(e)
            {
                alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    });
    $("#class_f").click(function(){
        console.log("Friday is clicked")
        day_clicked = true;
        search_selected = false;
        /* Perform AJAX */
        $.ajax({
            type : "POST",
            contentType : 'application/json',
            data : JSON.stringify('F'),
            dataType : 'json',
            url : window.location + "/filter-days",
            success : function(result)
            {
                console.log("Successfully Filtered");
                retrieveDayFilter();
            },
            error : function(e)
            {
                alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    });
    $("#class_f").click(function(){
        console.log("Friday is clicked")
        day_clicked = true;
        search_selected = false;
        /* Perform AJAX */
        $.ajax({
            type : "POST",
            contentType : 'application/json',
            data : JSON.stringify('F'),
            dataType : 'json',
            url : window.location + "/filter-days",
            success : function(result)
            {
                console.log("Successfully Filtered");
                retrieveDayFilter();
            },
            error : function(e)
            {
                alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    });
    function retrieveDayFilter(){
        /* Remove All The Previous Offerings */
        $.ajax({
            type : "GET",
            url : window.location + "/get-filtered-day",
            success : function(result)
            {
                if(result.status == "Done")
                {
                    /* Keep Track of Selected Offering */
                    var selOffering = $(".selectedOffering").find(".cols-offid").val();
                    //console.log("Selected Offering = " + selOffering + " type = " + typeof selOffering);

                    /* Remove All The Previous Offerings */
                    $(".cwofferings .generatedContent .genContentRows:not(:first-child)").remove();

                    /* For Each Offering */
                    $.each(result.data, function(i, offering)
                    {
                        /* Create Divs */
                        var courseCode = "<div class='genContentCols cols-course-code'>" + offering.courseCode + "</div>";
                        var section = "<div class='genContentCols cols-section'>" + offering.classSection + "</div>";
                        var days = (offering.day1 != '-') ? "<div class='genContentCols cols-days'>" + offering.day1 + " " + offering.day2 + "</div>"
                            : "<div class='genContentCols cols-days'>None</div>";
                        var time = (offering.startTime != ':') ? "<div class='genContentCols cols-timeslot'>" + offering.startTime + "-" + offering.endTime + "</div>"
                            : "<div class='genContentCols cols-timeslot'>Unassigned</div>";
                        var room = "<div class='genContentCols cols-room-code'>" + offering.roomCode + "</div>";
                        var faculty = "<div class='genContentCols cols-faculty'>" + offering.faculty + "</div>";
                        var offerid = "<input class='cols-offid' type='hidden' value='" + offering.offeringId + "'/>";

                        var offeringRow = "<div class='genContentRows'>" +
                            "" + courseCode + section + days + time + room + faculty + offerid +
                            "</div>";

                        /* Add to UI */
                        $(".cwofferings .generatedContent").append(offeringRow);

                        /* Optional: if selected offering, add class */
                        console.log("Sel = " + offering.offeringId + " type = " + typeof offering.offeringId);
                        if(offering.offeringId == parseInt(selOffering)) {
                            $(".cwOfferings .generatedContent .genContentRows:last-child").addClass("selectedOffering");
                            $(".cwOfferings .generatedContent .genContentRows:last-child").css({'background-color' : '#3cb878'});
                        }
                    });
                }
            },
            error : function(e)
            {
                //alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    }
/* General Function for Filters*/
        $(".filterForms").change(function() {
            day_clicked = false;
            search_selected = false;
            console.log("Change Detected")
            var formData = {
                term : $("#select_view_offerings").val(),
                classType : $("#select_left_class_type").val(),
                roomType : $("#select_room_type").val(),
                timeBlock : $("#select_left_timeblock").val(),
            };

            /* Perform AJAX */
            $.ajax({
                type: 'POST',
                contentType : 'application/json',
                url : window.location + "/left-filter",
                data : JSON.stringify(formData),
                dataType : 'json',
                success : function(result)
                {
                  console.log("Successfully Filtered");
                  retrieveFilteredOfferings();
                },
                error : function(e)
                {
                    alert("Error!");
                    console.log("ERROR: ", e);
                }
            });
            //get all values of filterforms
            //checked via not all
        });

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
                    /* Keep Track of Selected Offering */
                    var selOffering = $(".selectedOffering").find(".cols-offid").val();
                    //console.log("Selected Offering = " + selOffering + " type = " + typeof selOffering);

                    /* Remove All The Previous Offerings */
                    $(".cwofferings .generatedContent .genContentRows:not(:first-child)").remove();

                    /* For Each Offering */
                    $.each(result.data, function(i, offering)
                    {
                        /* Create Divs */
                        var courseCode = "<div class='genContentCols cols-course-code'>" + offering.courseCode + "</div>";
                        var section = "<div class='genContentCols cols-section'>" + offering.classSection + "</div>";
                        var newDays = orderDays(offering.day1 + offering.day2);
                        var days = (offering.day1 != '-') ? "<div class='genContentCols .cols-days'>" + newDays + "</div>"
                            : "<div class='genContentCols cols-days'>None</div>";
                        var time = (offering.startTime != ':') ? "<div class='genContentCols cols-timeslot'>" + offering.startTime + "-" + offering.endTime + "</div>"
                            : "<div class='genContentCols cols-timeslot'>Unassigned</div>";
                        var room = "<div class='genContentCols cols-room-code'>" + offering.roomCode + "</div>";
                        var faculty = "<div class='genContentCols cols-faculty'>" + offering.faculty + "</div>";
                        var offerid = "<input class='cols-offid' type='hidden' value='" + offering.offeringId + "'/>";

                        var offeringRow = "<div class='genContentRows'>" +
                            "" + courseCode + section + days + time + room + faculty + offerid +
                            "</div>";

                        /* Add to UI */
                        $(".cwofferings .generatedContent").append(offeringRow);

                        /* Optional: if selected offering, add class */
                        console.log("Sel = " + offering.offeringId + " type = " + typeof offering.offeringId);
                        if(offering.offeringId == parseInt(selOffering)) {
                            $(".cwOfferings .generatedContent .genContentRows:last-child").addClass("selectedOffering");
                            $(".cwOfferings .generatedContent .genContentRows:last-child").css({'background-color' : '#3cb878'});
                        }
                    });
                }
            },
            error : function(e)
            {
                //alert("Error!");
                console.log("ERROR: ", e);
            }
        });
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
                    /* Keep Track of Selected Offering */
                    var selOffering = $(".selectedOffering").find(".cols-offid").val();
                    //console.log("Selected Offering = " + selOffering + " type = " + typeof selOffering);

                    /* Remove All The Previous Offerings */
                    $(".cwofferings .generatedContent .genContentRows:not(:first-child)").remove();

                    /* For Each Offering */
                    $.each(result.data, function(i, offering)
                    {
                        /* Create Divs */
                        var courseCode = "<div class='genContentCols cols-course-code'>" + offering.courseCode + "</div>";
                        var section = "<div class='genContentCols cols-section'>" + offering.classSection + "</div>";
                        var newDays = orderDays(offering.day1 + offering.day2);
                        var days = (offering.day1 != '-') ? "<div class='genContentCols .cols-days'>" + newDays + "</div>"
                            : "<div class='genContentCols cols-days'>None</div>";
                        var time = (offering.startTime != ':') ? "<div class='genContentCols cols-timeslot'>" + offering.startTime + " - " + offering.endTime + "</div>"
                            : "<div class='genContentCols cols-timeslot'>Unassigned</div>";
                        var room = "<div class='genContentCols cols-room-code'>" + offering.roomCode + "</div>";
                        var faculty = "<div class='genContentCols cols-faculty'>" + offering.faculty + "</div>";
                        var offerid = "<input class='cols-offid' type='hidden' value='" + offering.offeringId + "'/>";

                        var offeringRow = "<div class='genContentRows'>" +
                            "" + courseCode + section + days + time + room + faculty + offerid +
                            "</div>";

                        /* Add to UI */
                        $(".cwofferings .generatedContent").append(offeringRow);

                        /* Optional: if selected offering, add class */
                        console.log("Sel = " + offering.offeringId + " type = " + typeof offering.offeringId);
                        if(offering.offeringId == parseInt(selOffering)) {
                            $(".cwOfferings .generatedContent .genContentRows:last-child").addClass("selectedOffering");
                            $(".cwOfferings .generatedContent .genContentRows:last-child").css({'background-color' : '#3cb878'});
                        }
                    });
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
    });/**/
    $("#submit_left_side_search").click(function(){
        search_selected = true;
        var textSearched = $.trim($("#input_search_course").val())
        $(".filter_comments").hide();
        $(".genContentRows:not(:first-child)").each(function () {
            var courseTraverse = $(':first-child', this).text();
            courseTraverse = $.trim(courseTraverse);
            if(!checkSearch(textSearched.toUpperCase(), courseTraverse.toUpperCase()))
                $(this).hide();
        });
    });
    $("#submit_left_side_search").click(function(){
        search_selected = true;
        var textSearched = $.trim($("#input_search_course").val())
        $(".filter_comments").hide();
        if(textSearched.toUpperCase() != "")
        {
            $(".genContentRows:not(:first-child)").each(function () {
                var courseTraverse = $(':first-child', this).text();
                courseTraverse = $.trim(courseTraverse);
                if(!checkSearch(textSearched.toUpperCase(), courseTraverse.toUpperCase()))
                    $(this).hide();
            });
        }
        else
        {
            showOfferings();
            search_selected = false;
        }
    });
    $('#input_search_course').on('input',function(e){
        search_selected = true;
        var textSearched = $.trim($("#input_search_course").val())
        $(".filter_comments").hide();
        if(textSearched.toUpperCase() != "")
        {
            $(".genContentRows:not(:first-child)").each(function () {
                var courseTraverse = $(':first-child', this).text();
                courseTraverse = $.trim(courseTraverse);
                if(!checkSearch(textSearched.toUpperCase(), courseTraverse.toUpperCase()))
                    $(this).hide();
            });
        }
        else
        {
            showOfferings();
            search_selected = false;
        }
    });




    function checkSearch(searchedString, currentString){
        if(searchedString == currentString)
            return true;
        else if(currentString.includes(searchedString))
            return true;
        else
            return false;
    }
    /* Faculty Load Search */
    $('#input_search_faculty').on('input',function(e){
        //alert("detected");
        search_fac = true;
        var textSearched = $.trim($("#input_search_faculty").val())
        if(textSearched.toUpperCase() != "")
        {
            $(".genContentRows:not(:first-child)").each(function () {
                var courseTraverse = $(':first-child', this).text();
                courseTraverse = courseTraverse.replace("Deload", "");
                courseTraverse = $.trim(courseTraverse);
                if(!checkSearch(textSearched.toUpperCase(), courseTraverse.toUpperCase()))
                    $(this).hide();
            });
        }
        else
        {
            showFacultyLoad();
            search_fac = false;
        }
    });

    function showFacultyLoad()
    {
        /* Perform AJAX */
        $.ajax({
            type : "GET",
            url : window.location + "/show-faculty-load",
            success : function(result)
            {
                if(result.status == "Done")
                {
                    /* Keep Track of Selected Offering */
                    //var selFaculty = $(".selectedOffering").find(".cols-offid").val();

                    /* Remove All The Previous Faculty */
                    $(".cwFacultyLoad .generatedContent .genContentRows:not(:first-child)").remove();
                    /* For Each Offering */
                    $.each(result.data, function(i, load)
                    {
                        var buttonVal = load.lastName + ", "  + load.firstName;
                        var facultyName = "<div class='genContentCols'>" + load.lastName +  ', ' + load.firstName + "</div>";
                        var teachingLoad = "<div class='genContentCols'>" + load.teachingLoad + "</div>";
                        var adminLoad = "<div class='genContentCols'>" + load.adminLoad + "</div>";
                        var researchLoad = "<div class='genContentCols'>" + load.researchLoad + "</div>";
                        var totalLoad = "<div class='genContentCols'>" + load.totalLoad + "</div>";
                        var buttonDeload = "<div class = 'genContentCols'><button class='deload_button' value='" + buttonVal + "' type='button'>Deload</button></td></tr>"
                        var facultyRow = "<div class='genContentRows'>" +
                            "" + facultyName + teachingLoad + adminLoad + researchLoad + totalLoad + buttonDeload +
                            "</div>";
                        //console.log(load.lastName + load.firstName + load.adminLoad + load.researchLoad + load.totalLoad)
                        /* Add to UI */
                        $(".cwFacultyLoad .generatedContent").append(facultyRow);

                    });
                    /* Select a Deload button*/
                    function colorChange()
                    //$(".cwOfferings .generatedContent .genContentRows:not(:first-child)").click(function()
                    {
                        /* Check if the offering is selected already; if so, unselect it */
                        if ( $(this).parents(".genContentRows").css("background-color") === "rgb(60, 184, 120)")
                        {
                            $(this).parents(".genContentRows").removeClass("selectedOffering");
                            $(this).parents(".genContentRows").css({'background-color' : '#e2e2e2'});
                        }
                        /* If not, select it */
                        else if ($(this).parents(".genContentRows").css("background-color") === "rgb(226, 226, 226)")
                        {
                            /* Modify course offering row appearance */
                            //$(".modify_sidebar").find("input:text").val("");
                            $(".cwFacultyLoad .generatedContent .genContentRows:not(:first-child)").removeClass("selectedOffering");
                            $(".cwFacultyLoad .generatedContent .genContentRows:not(:first-child)").css({'background-color' : '#e2e2e2'});
                            $(this).parents(".genContentRows").css({'background-color' : '#3cb878'});
                            $(this).parents(".genContentRows").addClass("selectedOffering");

                        }

                        var facultyName = $(this).attr("value");
                        $("#deload_name").text(facultyName)
                        //Assign the Other Values
                        $('#deload_totalLoad').text("Total Load: " + $(this).parent().siblings().eq(4).text());

                        $.ajax({
                            type : "POST",
                            contentType : 'application/json',
                            url : window.location + "/person-faculty-load",
                            data : facultyName,
                            dataType : 'json',
                            success : function(result)
                            {
                                if(result.status == "Done") {
                                    $(".generatedFacultyLoadTable .generatedLoad .genLoadRows:not(:first-child)").remove();
                                    /* For Each Offering */
                                    $.each(result.data, function(i, offering)
                                    {
                                        /* Create Divs */
                                        var courseCode = "<div class='genLoadCols'>" + offering.courseCode + "</div>";
                                        var section = "<div class='genLoadCols'>" + offering.classSection + "</div>";
                                        var newDays = orderDays(offering.day1 + offering.day2);
                                        var days = (offering.day1 != '-') ? "<div class='genLoadCols'>" + newDays + "</div>"
                                            : "<div class='genLoadCols'>None</div>";
                                        var time = (offering.startTime != ':') ? "<div class='genLoadCols'>" + offering.startTime + " - " + offering.endTime + "</div>"
                                            : "<div class='genLoadCols'>Unassigned</div>";
                                        var room = "<div class='genLoadCols'>" + offering.roomCode + "</div>";

                                        var offeringRow = "<div class='genLoadRows'>" +
                                            "" + courseCode + section + days + time + room +
                                            "</div>";

                                        /* Add to UI */
                                        $(".generatedFacultyLoadTable .generatedLoad").append(offeringRow);
                                    });
                                }

                            },
                            error : function(e)
                            {
                                alert("Error!");
                                console.log("ERROR: ", e);
                            }
                        });

                        $("#modalDeloading").dialog({
                            title:"Deload Faculty",
                            width:400,
                            height:500,
                            modal:true
                        });
                        $("#modalDeloading").dialog("open");
                    }

                    var loadColumns = document.getElementsByClassName("deload_button");
                    for (var i = 0; i < loadColumns.length; i++) {
                        var load = loadColumns[i];
                        load.onclick = colorChange;
                    }
                }
            },
            error : function(e)
            {
                //alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    }
});
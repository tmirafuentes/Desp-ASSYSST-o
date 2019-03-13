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
$(".filterForms").change(function() {
    //alert("hello");
    checkTimeblock();//check timeblock filter
    checkRoomType();//check room type filter
    checkClassType(); //check class type filter
    checkTerm(); //check class type filter

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
});

function checkTimeblock()
{
    var filterData = $("#select_left_timeblock").val();
    if(filterData != "All")
    {
        $(".genContentCols:nth-child(4):visible").each(function() {
            var cellText = $.trim($(this).val());
            if (filterData != cellText) {
                $(this).parent().hide();
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
        $(".genContentRows:visible").each(function() {
            var cellText = $.trim($("#off_roomtype").val());
            if (filterData != cellText)
                $(this).hide();
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
        $(".genContentRows:visible").each(function() {
            var cellText = $.trim($("#off_status").val());
            if (filterData != cellText)
                $(this).hide();
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
function checkTerm()
{
    var filterData = $("#select_view_offerings").val();//gets value for the filter
    if(filterData != "All") {
        $(".genContentRows:visible").each(function () {
            var cellText = $.trim($("#off_term").val());
            if (filterData != cellText)
                $(this).hide();
        });
    }
    else{
        showallRows();
        if($("#select_left_class_type").val() != "All")
            checkClassType();
        if($("#select_room_type").val() != "All")
            checkRoomType();
        if(($("#select_left_timeblock").val() != "All"))
            checkTimeblock();
    }

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

        $(".genContentRows:visible").each(function () {
            var courseTraverse = $.trim($(':first-child', this).text())
            console.log(courseTraverse)
            if(!checkSearch(textSearched.toUpperCase(), courseTraverse.toUpperCase()))
                $(this).hide();
        });

        if(countallRows() == 0) {
            $(".filter_comments").show();

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
});
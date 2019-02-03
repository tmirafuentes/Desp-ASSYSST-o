$(function (){
    $("#table_area").hide();
    $("#concerns_right").hide();

    $("#button_concerns").click(function (){
         $("#table_area").toggle();
        $("#concerns_right").toggle();
    })
})
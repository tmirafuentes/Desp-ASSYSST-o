$(function (){
    var hasClickedTableRow = false;
    jQuery('<div/>', {
        id: 'div_dialog',
    }).appendTo('body');

    //hide the right side
    $("#table_faculty_name").hide();
    $("#table_preferences").hide();
    $("#table_currentlytaking").hide();
    $("#load_information").hide();

    $("#button_concerns").click(function (){

        $("#div_dialog").dialog({
            title:"Concerns",
            width:450,
            height:450,
            modal:true
        })
    })
    $("#button_view_faculty").click(function (){

        $("#div_dialog").dialog({
            title:"View Faculty",
            width:450,
            height:450,
            modal:true
        })
    })

    $("#generated_list tr").click(function (){
        hasClickedTableRow = !hasClickedTableRow;
        if(hasClickedTableRow == true)
        {
            $(this).css({'background-color': '#3cb878'})
        }
        else{
            $(this).css({'background-color': 'white'})
        }
        $("#table_faculty_name").toggle();
        $("#table_preferences").toggle();
        $("#table_currentlytaking").toggle();
        $("#load_information").toggle();

    })
})
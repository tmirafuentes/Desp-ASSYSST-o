$(function (){
    var hasClickedRevision = false;
    jQuery('<div/>', {
        id: 'div_dialog',
    }).appendTo('body');


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

    $(".revision_holder").click(function (){
        hasClickedRevision = !hasClickedRevision;
        if(hasClickedRevision == true)
        {
            $(this).css({'background-color': '#3cb878'})
        }
        else{
            $(this).css({'background-color': '#e1e1e1'})
        }

    })
})
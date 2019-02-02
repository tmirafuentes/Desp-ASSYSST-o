$(function (){
    var hasClickedTableRow = false;
    jQuery('<div/>', {
        id: 'div_dialog',
    }).appendTo('body');
      //hide the right side
      $("#modify_class_section").hide();
      $("#modify_class_time").hide();
      $("#modify_room").hide();
      $("#modify_faculty").hide();
      $("#modify_concerns").hide();
      $("#modify_button_concerns").hide();
      $("#modify_offering").hide();

    $("#button_concerns").click(function (){
        console.log("pumapasok?");
        $("#div_dialog").dialog({
            title:"Concerns",
            width:450,
            height:450,
            modal:true
        })
    })
    $("#button_upload_flowchart").click(function (){
        $("#div_dialog").dialog({
            title:"Upload Flowchart",
            width:300,
            height:300,
            modal:true
        })
    })

    $("#button_add_offering").click(function (){
        $("#div_dialog").dialog({
            title:"Upload Flowchart",
            width:500,
            height:500,
            modal:true
        })
    })

    $("#button_concerns").click(function (){
        $("#div_dialog").dialog({
            title:"Concerns",
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
        
        $("#modify_class_section").toggle();
        $("#modify_class_time").toggle();
        $("#modify_room").toggle();
        $("#modify_faculty").toggle();
        $("#modify_concerns").toggle();
        $("#modify_button_concerns").toggle();
        $("#modify_offering").toggle();

    })
})
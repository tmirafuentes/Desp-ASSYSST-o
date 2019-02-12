
$(function (){
    var modifyFaculty = $( "<form id='modal_form_faculty'></form>" );
    var hasClickedTableRow = false;
    jQuery('<div/>', {
        id: 'div_dialog',
    }).appendTo('body');
    //hide the right side
    //$(".modify_sidebar").hide();

    $("#button_concerns").click(function (){
        //console.log("pumapasok?");
        $("#div_dialog").dialog({
            title:"Concerns",
            width:450,
            height:450,
            modal:true
        })
    })

    $("#button_assign_faculty").click(function (){
        $(modifyFaculty).empty();
        $(modifyFaculty).append("<select id='faculty_select_modal'></select> " +
            "<br><button id='faculty_set_button' class='modal_buttons'>Set Faculty</button>" +
            "<button id='faculty_cancel_button' class='modal_buttons'>Cancel</button>");
        $("#div_dialog").append(modifyFaculty);
        $("#div_dialog").dialog({
            title:"Re-assign Faculty",
            width:300,
            height:250,
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
    });

    $("#button_add_offering").click(function (){
        $("#div_dialog").dialog({
            title:"Upload Flowchart",
            width:500,
            height:500,
            modal:true
        })
    });

    $("#button_concerns").click(function (){
        $("#div_dialog").dialog({
            title:"Concerns",
            width:450,
            height:450,
            modal:true
        })
    });

    /* Selecting an offering */
    var selectedOffering = null;
    /*$("#generated_list .genList_rows:not(:first-child)").click(function (){
        hasClickedTableRow = !hasClickedTableRow;
        /* Check if selected already
        if ($(this).css("background-color") === "rgb(60, 184, 120)")
        {
            $(".modify_sidebar").hide();
            $(this).removeClass("selectedOffering");
            $(this).css({'background-color' : '#e2e2e2'});
        }
        /* It has not been selected yet
        else if ($(this).css("background-color") === "rgb(226, 226, 226)")
        {
            $(".modify_sidebar").show();
            $("#generated_list .genList_rows:not(:first-child)").removeClass("selectedOffering");
            $("#generated_list .genList_rows:not(:first-child)").css({'background-color' : '#e2e2e2'});
            $(this).css({'background-color' : '#3cb878'});
            $(this).addClass("selectedOffering");
        }
    })*/
})
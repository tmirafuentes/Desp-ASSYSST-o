$(function (){
    var trActive = true;
    var clickedDiv;

    var deloadTable = $( "<table id='dialog_load_table'></table>" );
    var professorName = $( "<p id='dialog_professor_name'>Jordan Deja</p>" );
    jQuery('<div/>', {
        id: 'div_dialog',
    }).appendTo('body');

    //initializing for deload



    $("#deload_button").click(function (){
        $("#div_dialog").empty();
        $("#div_dialog").append(professorName);
        $("#div_dialog").append(deloadTable);
        $("#dialog_load_table").append("<tr>" +
            "<td><p class='p_modal'>Total Current Load</p></td>" +
            "<td><p id='p_total_load'></p></td>" +
            "</tr>" +
            "<tr>"  +
            "<td><p class='p_modal'>Deloaded units</p></td>" +
            "<td><input type='number' id='input_deload'></td>" +
            "</tr>" +
            "<tr>"  +
            "<td><p class='p_modal'>Remarks</p></td>" +
            "<td><select id='select_deload'></select></td>" +
            "</tr>" +
            "<tr>"  +
            "<td><button class='modal_buttons' id='deload_confirm'>Confirm</button></td>" +
            "<td><button class='modal_buttons' id='deload_cancel'>Cancel</button></td>" +
            "</tr>");
        $("#div_dialog").dialog({
            title:"Deloading",
            width:450,
            height:300,
            modal:true,
            close: function (event, ui) {
                $("#dialog_load_table").empty();
            }
        })

    })

})
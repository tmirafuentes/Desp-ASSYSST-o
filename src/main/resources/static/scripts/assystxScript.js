$(function (){
    var onoffRow = false;
    var trActive = false;
    jQuery('<div/>', {
        id: 'div_dialog',
    }).appendTo('body');
    //console.log("Changes are made");
    //hide the right side
    trActive = hide_right();

    jQuery.fn.extend(
        {
            show_right: function ()
            {
                $("#modify_class_section").show();
                $("#modify_time").show();
                $("#modify_days").show();
                $("#modify_room").show();
                $("#modify_faculty").show();
                $("#modify_concerns").show();
                $("#modify_button_concerns").show();
                $("#modify_offering").show();

                return true;
            }
        })
    jQuery.fn.extend(
        {
            hide_right: function ()
            {
                $("#modify_class_section").hide();
                $("#modify_time").hide();
                $("#modify_days").hide();
                $("#modify_room").hide();
                $("#modify_faculty").hide();
                $("#modify_concerns").hide();
                $("#modify_button_concerns").hide();
                $("#modify_offering").hide();

                return false;
            }
        })
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
    //check if meron nang naka toggle if wala pa:
    //toggle on -> toggle right side
    //toggle off -> toggle right side uncheck na meron naka toggle
    //
    if($('#generated_list').find('tr.activeTr').length !== 0)//if walang active
    {
        $("#generated_list tr").not(":eq(0)").click(function (){//if cinlick
            if(!trActive && !onoffRow){//walang active and walang nakatoggle on
                onoffRow = true;//start the toggling
                $(this).toggleClass("activeTr");
                trActive = show_right();
            }
            else if(trActive && onoffRow)
            {
                onoffRow = false;//tangalin toggle
                $(this).toggleClass("inactiveTr");
                trActive = hide_right();
            }
        })
    }
    else{
        $("#generated_list tr").not(":eq(0)").click(function (){

        })
    }


    $("#generated_list tr").not(":eq(0)").click(function (){

        $(this).toggleClass("activeTr");
        trActive = $("#generated_list tr").not(":eq(0)").toggleClass("activeTr");
        show_right();
    })
})
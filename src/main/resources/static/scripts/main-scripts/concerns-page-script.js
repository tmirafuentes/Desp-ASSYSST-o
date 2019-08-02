$(function() {
    /*
     *
     *  ONLOAD FUNCTIONS
     *
     */
    var options = { collapsible: true, heightStyle: "content", active: false };
    $("#concerns-list-accordion").accordion(options);
    retrieveConcerns("inbox");

    /* Select Inbox */
    $("#concerns-filter-inbox").click(function(e)
    {
        e.preventDefault();

        if(!$(this).find("li").hasClass("menu-selected-page"))
        {
            /* Retrieve received concerns */
            retrieveConcerns("inbox");

            /* Change appearance as selected */
            $("#menu-list-main li").removeClass("menu-selected-page");
            $(this).find("li").addClass("menu-selected-page");
        }
    });

    /* Select Sent */
    $("#concerns-filter-sent").click(function(e)
    {
        e.preventDefault();

        if(!$(this).find("li").hasClass("menu-selected-page"))
        {
            /* Retrieve received concerns */
            retrieveConcerns("sent");

            /* Change appearance as selected */
            $("#menu-list-main li").removeClass("menu-selected-page");
            $(this).find("li").addClass("menu-selected-page");
        }
    });

    /* Mark as acknowledged */
    $("#concerns-list-accordion").on('click', 'h3', function()
    {
        if ($(this).hasClass("concerns-unacknowledged") &&
            $("#concerns-filter-inbox").closest("td").hasClass("concerns-filter-selected"))
        {
            /* Get Concern Number */
            var id = "" + $(this).data('concern-num');

            $.ajax({
                method : "POST",
                data : id,
                url : window.location + "/mark-acknowledged-concern",
                success : function(result)
                {
                    if (result.status === "Done")
                    {
                        $(this).removeClass("concerns-unacknowledged");
                    }
                },
                error : function(e)
                {
                    console.log("Error: " + e);
                }
            });
        }
    });

    function retrieveConcerns(type)
    {
        $.ajax({
            method : "POST",
            url : "/retrieve-concerns-list",
            data : type,
            success : function(result)
            {
                if(result.status === "Done")
                {
                    /* Remove existing in accordion */
                    $("#concerns-list-accordion").empty().accordion("refresh");

                    $.each(result.data, function(i, concern)
                    {
                        var header = "<h3 data-concern-num='" + concern.id + "'";
                        if (concern.acknowledged === false)
                            header += " class='concerns-unacknowledged'";
                        header += ">" +
                                  "<span class='concerns-list-sender'>" + concern.sender + "</span>" +
                                  "<span class='concerns-list-subject'>" + concern.subject + "</span>" +
                                  "<span class='concerns-list-timestamp'>" + concern.timestamp + "</span>" +
                                  "</h3>";

                        var div = "<div class='concerns-list-accordion-div'>" +
                                  "<p class='concerns-list-accordion-content'>" + concern.content + "</p>" +
                                  "</div>";

                        var concern = header + div;

                        $(concern).appendTo("#concerns-list-accordion");
                    });

                    /* Accordion look of table */
                    $("#concerns-list-accordion").accordion("refresh");
                }
            }
        });
    }
});
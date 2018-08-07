/***ADD OFFERINGS GLOBAL VARIABLES***/
var hasInitializedEditableOfferingListenner = false;
var globalStartYear, globalEndYear, globalTerm, globalBatch, globalDegreeProgram, globalStudentCount, globalSectionNumber;
/***ADD OFFERINGS GLOBAL VARIABLES***/
/***GLOBAL VARIABLES FOR ADD NEW OFFERING FUNCTION***/
var tempCourseOfferings = [];
var appenderID = 0;
var tempRooms = [];
var roomAppenderID = 0;
var selectedTemporaryOfferingID = "";
var selectedRoomID = "";
/***GLOBAL VARIABLES FOR ADD NEW OFFERING FUNCTION***/
/********LOADING MODAL VARIABLES********/
var isPure = 0;
/********LOADING MODAL VARIABLES********/
function clickNavigation(current) {
	if(current != "dropdownNav"){
		$('.active').removeClass('active');
		$('#'+current).addClass('active');
		
		$('.activePanel').addClass('inactivePanel');
		$('.activePanel').removeClass('activePanel');
	}
	fixForParallax();
}

function clickSideBar(current) {
	$('.active').removeClass('active');
	$('.sideBar-cell-color').removeClass('sideBar-cell-color');

	$('#'+current).addClass('active');
	$('#'+current).addClass('sideBar-cell-color');
	
	$('.activePanel').addClass('inactivePanel');
	$('.activePanel').removeClass('activePanel');
	
	$("#tableViewFlowchart tbody tr").remove();// dunno where to put this. dito muna for the mean time. what this does is tanggalin laman ng view flowcharts table kapag maykinlick sa side bar kasi d sya nagbabago kapaglumipat ka tapos bumalik
	fixForParallax();
}

function makeActivePanel(panel){
	//hide old Panel by adding the inactivePanel class
	$('.activePanel').addClass('inactivePanel');
	$('.activePanel').removeClass('activePanel');
	
	//set new Panel to activePanel by removing the inactivePanel class
	$(panel).removeClass('inactivePanel');
	$(panel).addClass('activePanel');
}
/***************************FLOWCHARTS**************************************/
function clickFlowcharts(id){
	clickSideBar(id);
	makeActivePanel("#panelFlowcharts");
	updateFlowchartsDropdowns();
	fixForParallax();
}

function updateFlowchartsDropdowns(){

	var collegeId = $('#collegeIDDump').val();
	var deptId = $('#deptIDDump').val();
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateFlowchartDegreeProgramDropdown',
        data:{
        	"collegeId": collegeId,
        	"deptId": deptId
        },
        success: function (data) {
        	$("#flowchartPanelDegreeProgramDropdown").empty(); //removes all options
        	
        	var newoption = "<option selected onclick=\"\" id=\"\">none</option>";
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(i === 0){
        			var newoption = "<option selected onclick=\"\" id=\"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"\" id=\"\">" + currOption + "</option>";
            	}
        		
        		$("#flowchartPanelDegreeProgramDropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	//populate batch
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateFlowchartBatchDropdown',
        data:{
        	"collegeId": collegeId,
        	"deptId": deptId
        },
        success: function (data) {
        	$("#flowchartPanelBatchDropdown").empty(); //removes all options
        	
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(i === 0){
        			var newoption = "<option selected onclick=\"\" id=\"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"\" id=\"\">" + currOption + "</option>";
            	}
        		
        		$("#flowchartPanelBatchDropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	//populate AY
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateFlowchartAYDropdown',
        data:{
        	"collegeId": collegeId,
        	"deptId": deptId
        },
        success: function (data) {
        	$("#flowchartPanelAYDropdown").empty(); //removes all options
        	
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(i === 0){
        			var newoption = "<option selected onclick=\"\" id=\"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"\" id=\"\">" + currOption + "</option>";
            	}
        		
        		$("#flowchartPanelAYDropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

function updateViewFlowchartTable(){
	
	var selectedDegreeProgram = $('#flowchartPanelDegreeProgramDropdown :selected').text();
	var selectedBatch = $('#flowchartPanelBatchDropdown :selected').text();
	var selectedAY = $('#flowchartPanelAYDropdown :selected').text();
	var year = [];
	year = selectedAY.split('-');
	var selectedTerm;
	
	if($('#flowchartPanelRadio1').is(':checked')) selectedTerm = "1";
	else if($('#flowchartPanelRadio2').is(':checked')) selectedTerm = "2";
	else selectedTerm = "3";
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getAllFlowchartCourses',
        data:{
        	"selectedDegreeProgram": selectedDegreeProgram,
        	"selectedBatch": selectedBatch,
        	"selectedTerm": selectedTerm,
        	"startYear": year[0],
        	"endYear": year[1]
        },
        success: function (data) {
        	var headers = "";
        	//console.log(data);
        	$("#tableViewFlowchart tbody tr").remove(); //removes all tr but not thead
        	
        	$.each(data, function(i, currObject){
        		var rows = "";
        		var id = currObject.courseId;
        		var courseCode = currObject.courseCode;
        		var courseName = currObject.courseName;
        		var units = currObject.units;
        		var collegeCode = currObject.college.collegeCode;
        		var deptCode = currObject.department.deptCode;
        	
        		var concatId = currObject.startYear +"-"+ currObject.endYear + "-" + currObject.term;
        		
        		rows += "<tr id=\"" + id + "-id\" class=\"AYRow\">"+
        		        "<td>"+courseCode+"</td>"+
        				"<td>"+courseName+"</td>"+
        				"<td>"+units+"</td>"+
        				"<td>"+collegeCode+"</td>"+
        				"<td>"+deptCode+"</td></tr>";
        				
        		$(rows).appendTo("#tableViewFlowchart tbody");
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	fixForParallax();
}
/***************************FLOWCHARTS**************************************/
/***************************FLOWCHARTS UPLOAD PANEL*******************************/
function clickUploadFlowchart(id){
	clickSideBar(id);
	makeActivePanel("#panelUploadFlowchart");
	fixForParallax();
}

function fileUpload(form, action_url, div_id) {
	var fileVal = $("#flowchartFiles").val();
	
	if(fileVal=='') 
    { 
        $("#" + div_id).html("Error! No file selected. Please select a file and try again.");
        return false; 
    } 
	
    // Create the iframe...
    var iframe = document.createElement("iframe");
    iframe.setAttribute("id", "upload_iframe");
    iframe.setAttribute("name", "upload_iframe");
    iframe.setAttribute("width", "0");
    iframe.setAttribute("height", "0");
    iframe.setAttribute("border", "0");
    iframe.setAttribute("style", "width: 0; height: 0; border: none;");

    // Add to document...
    form.parentNode.appendChild(iframe);
    window.frames['upload_iframe'].name = "upload_iframe";

    iframeId = document.getElementById("upload_iframe");

    // Add event...
    var eventHandler = function () {

            if (iframeId.detachEvent) iframeId.detachEvent("onload", eventHandler);
            else iframeId.removeEventListener("load", eventHandler, false);

            // Message from server...
            if (iframeId.contentDocument) {
                content = iframeId.contentDocument.body.innerHTML;
            } else if (iframeId.contentWindow) {
                content = iframeId.contentWindow.document.body.innerHTML;
            } else if (iframeId.document) {
                content = iframeId.document.body.innerHTML;
            }

            document.getElementById(div_id).innerHTML = "Upload Finished";

            // Del the iframe...
            setTimeout('iframeId.parentNode.removeChild(iframeId)', 250);
            setTimeout(function(){
            	$("#uploadFlowchartModal").modal("toggle");
            }, 1500);
        }

    if (iframeId.addEventListener) iframeId.addEventListener("load", eventHandler, true);
    if (iframeId.attachEvent) iframeId.attachEvent("onload", eventHandler);

    // Set properties of form...
    form.setAttribute("target", "upload_iframe");
    form.setAttribute("action", action_url);
    form.setAttribute("method", "post");
    form.setAttribute("enctype", "multipart/form-data");
    form.setAttribute("encoding", "multipart/form-data");

    // Submit the form...
    form.submit();

    document.getElementById(div_id).innerHTML = "Uploading...";
    
}
/***************************FLOWCHARTS UPLOAD PANEL*******************************/
/***************************BUILDINGS AND ROOMS******************************/
function clickBuildingsAndRooms(id){
	clickSideBar(id);
	makeActivePanel("#panelBuildingsAndRooms");
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getBuildingsAndRooms',
        success: function (data) {
        	var headers = "", bodyStartTag="<tbody>", bodyEndTag="</tbody>";
        	//console.log(data);
        	$("#tableBuildingsAndRooms tbody tr").remove(); //removes all tr but not thead
        	
        	//headers += "<theadid=\"tableBuildingsAndRoomsHeaders\"><tr>" +
        			//"<th>Building</th>" +
        			//"<th>Building Code</th>" +
        			//"<th>Rooms Available</th>" +
        			//"<th></th>" +
        			//"</tr></thead>";
        	//$(headers).appendTo("#tableBuildingsAndRooms tbody");

        	$.each(data, function(i, currObject){
        		var rows = "";
        		var id = currObject.id;
        		var buildingName = currObject.buildingName;
        		var buildingCode = currObject.buildingCode;
        		var roomsAvailable = currObject.roomsAvailable;
        		
        		rows += "<tr id=\"" + id + "-id\" class=\"buildingRow\">"+
        				"<td>"+buildingName+"</td>"+
        				"<td>"+buildingCode+"</td>"+
        				"<td>"+roomsAvailable+"</td>"+
        				//data backdrop static prevents exit from background while data-keyboard false prevents exit using keyboard keys
        				"<td>" + "<a class=\"btn btn-default\" type=\"button\" onclick=\"getRoomsOfBuilding(this.id)\" data-toggle=\"modal\" data-target=\"#viewRoomsModal\" data-backdrop=\"static\" data-keyboard=\"false\" id=\"" + id + "-view\"><span><i class=\"fa fa-search\"></i></span> View</a>" + "</td>"+  
        				"<tr>";
        		//console.log(rows);
        		$(rows).appendTo("#tableBuildingsAndRooms tbody");
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	fixForParallax();
}

function getRoomsOfBuilding(id){
	var arr = [];
	arr = id.split('-'); //arr[0] would be the ID
	
	var selectedRoomType = $('#roomTypeDropdown :selected').text();
	var selectedRoomCapacity = $('#roomCapacityDropdown :selected').text();
	
	populateViewRoomsDropdown(arr[0], selectedRoomType, selectedRoomCapacity);
	getBuildingName(arr[0]);
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getRoomsOfBuilding',
        data: {
        	"buildingId": arr[0],
        	"roomType": selectedRoomType,
			"roomCapacity": selectedRoomCapacity
        },
        success: function (data) {
        	var headers = "";
        	//console.log(data);
        	$("#tableModalBuildingRooms tbody tr").remove(); //removes all tr but not thead

        	$.each(data, function(i, currObject){
        		var rows = "";
        		var id = currObject.id;
        		var roomCode = currObject.roomCode;
        		var roomType = currObject.roomType;
        		var roomCapacity = currObject.roomCapacity;
        		
        		rows += "<tr id=\"" + id + "-id\" class=\"buildingRoomRow\">"+
        				"<td>"+roomCode+"</td>"+
        				"<td>"+roomType+"</td>"+
        				"<td>"+roomCapacity+"</td>"+
        				"<tr>";
        		//console.log(rows);
        		$(rows).appendTo("#tableModalBuildingRooms tbody");
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	
	fixForParallax();
}

function getBuildingName(id){
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getBuildingNameModal',
        data: {
        	"buildingId": id
        },
        success: function (data) {
        	$("#viewRoomsModalTitle").text("View " + data.response +"'s Rooms");
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	
	fixForParallax();
}

function populateViewRoomsDropdown(id, selectedRoomType, selectedRoomCapacity){
	//populate Room Type
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateRoomTypeDropdown',
        success: function (data) {
        	$("#roomTypeDropdown").empty(); //removes all options
        	$("#roomTypeDropdown").removeAttr("onChange");
        	$("#roomTypeDropdown").attr("onChange", "return getRoomsOfBuilding(\""+id+"-type\")");
        	
        	if(selectedRoomType === "All"){
        		$("#roomTypeDropdown").prepend("<option selected onclick=\"getRoomsOfBuilding(this.id)\" id=\""+id+"-type"+"\">All</option>");
        	}else{
        		$("#roomTypeDropdown").prepend("<option onclick=\"getRoomsOfBuilding(this.id)\" id=\""+id+"-type"+"\">All</option>");
        	}
        	
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedRoomType === currOption){
        			var newoption = "<option selected onclick=\"getRoomsOfBuilding(this.id)\" id=\""+id+"-type"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"getRoomsOfBuilding(this.id)\" id=\""+id+"-type"+"\">" + currOption + "</option>";
            	}
        		
        		$("#roomTypeDropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	
	//populate Room Capacity
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateRoomCapacityDropdown',
        success: function (data) {
        	$("#roomCapacityDropdown").empty(); //removes all options
        	$("#roomCapacityDropdown").removeAttr("onChange");
        	$("#roomCapacityDropdown").attr("onChange", "return getRoomsOfBuilding(\""+id+"-capacity\")");

        	if(selectedRoomCapacity === "All"){
        		$("#roomCapacityDropdown").prepend("<option selected onclick=\"getRoomsOfBuilding(this.id)\" id=\""+id+"-type"+"\">All</option>");
        	}else{
        		$("#roomCapacityDropdown").prepend("<option onclick=\"getRoomsOfBuilding(this.id)\" id=\""+id+"-type"+"\">All</option>");
        	}
        	
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedRoomCapacity === currOption){
        			var newoption = "<option selected onclick=\"getRoomsOfBuilding(this.id)\" id=\""+id+"-capacity"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"getRoomsOfBuilding(this.id)\" id=\""+id+"-capacity"+"\">" + currOption + "</option>";
            	}
            	
        		//console.log(rows);
        		$("#roomCapacityDropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

function exitViewRoomsModal(){
	//this function empties the dropdown list again and sets "All" as the default selected option
	$("#roomTypeDropdown").empty(); //removes all options
	$("#roomTypeDropdown").removeAttr("onChange");
	$("#roomTypeDropdown").attr("onChange", "return getRoomsOfBuilding(\"\")");
	$("#roomTypeDropdown").prepend("<option selected>All</option>");
	
	$("#roomCapacityDropdown").empty(); //removes all options
	$("#roomCapacityDropdown").removeAttr("onChange");
	$("#roomCapacityDropdown").attr("onChange", "return getRoomsOfBuilding(\"\")");
	$("#roomCapacityDropdown").prepend("<option selected>All</option>");
	
	closeModal("viewRoomsModal");
}

function exitSetNewOfferingsModal(){
	$('#textStartYearANO').val('');
	$('#textEndYearANO').val('');
	$('#textTermANO').val('');
	//$('#textStudentCountANO').val('');
	//$('#textSectionNumberANO').val('');
}

function exitAddNewOfferingsModal(){
	//this function empties the dropdown list again and sets "All" as the default selected option
	closeModal("addNewOfferingsModal");
	//emptyAddNewOfferingsModal();
}

function exitEditTemporaryOfferingModal(){
	//this function empties the dropdown list again and sets "All" as the default selected option
	/*$("#" + thisModal).addClass('hide');
	$("#" + reOpenModal).removeClass('hide');
	$("#" + reOpenModal).modal('show');*/
	emptyEditTemporaryOfferingModal();
}
/***************************BUILDINGS AND ROOMS******************************/

/**************************ROOM ASSIGNMENT**********************************/
function clickViewRoomAssignment(id){
	clickSideBar(id);
	makeActivePanel("#panelViewRoomAssignment");
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getAllAcademicYears',
        success: function (data) {
        	var headers = "";
        	//console.log(data);
        	$("#tableViewRoomAssignment tbody tr").remove(); //removes all tr but not thead
        	
        	$.each(data, function(i, currObject){
        		var rows = "";
        		var id = currObject.id;
        		var AY = currObject.startYear +"-"+ currObject.endYear;
        		var term = currObject.term;
        		var isPublished = currObject.isPublished;
        		var unPublishedCount = currObject.unPublishedCount;
        		
        		rows += "<tr id=\"" + id + "-id\" class=\"AYRow\">"+
        				"<td>"+AY+"</td>"+
        				"<td>"+term+"</td>";
        				//data backdrop static prevents exit from background while data-keyboard false prevents exit using keyboard keys
        				console.log(unPublishedCount);
        				if(isPublished === "1"){ //because unPublished coutn is the count of all unpublished
        					rows+="<td><a class=\"btn btn-default\" type=\"button\" onclick=\"updateRoomAssignmentModal(this.id)\" data-toggle=\"modal\" data-target=\"#roomAssignmentModal\" data-backdrop=\"static\" data-keyboard=\"false\" id=\"" + id + "-assignrooms\" disabled><span><i class=\"fa fa-map-pin\"></i></span> Assign Rooms</a></td>";  
        				}else{
        					rows+="<td><a class=\"btn btn-default\" type=\"button\" onclick=\"updateRoomAssignmentModal(this.id)\" data-toggle=\"modal\" data-target=\"#roomAssignmentModal\" data-backdrop=\"static\" data-keyboard=\"false\" id=\"" + id + "-assignrooms\"><span><i class=\"fa fa-map-pin\"></i></span> Assign Rooms</a></td>";  
        				}
        				
        				rows+="<tr>";
        		$(rows).appendTo("#tableViewRoomAssignment tbody");
        		
        		if(isPublished === "1"){
        			$("#" + id + "-assignrooms").prop("disabled", true);
        		}
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	fixForParallax();
}

function updateRoomAssignmentModal(id){
	var arr = [];
	arr = id.split('-'); //arr[0] would be startyear, [1], endyear, [2] term, [3] batch, [4] extraneous text
	var startYear = arr[0];
	var endYear = arr[1];
	var term = arr[2];
	var batch = arr[3];
	var concatId = startYear + "-" + endYear + "-" + term + "-" + batch;
	
	$('#assignRoomCurrentAYDump').val(concatId); //for search Listeners cause di nila alam ung ID
	var selectedRoomType = $('#roomTypeRADropdown :selected').text();
	var selectedBuilding = $('#buildingRADropdown :selected').text();
	var searchRoom = $('#searchRoomRA').val();
	var searchOffering = $('#searchOfferingRA').val();
	
	//to initially disable the dropdowns and the input of rooms. Once the user clicks an offering, it will become enabled
	$('#buildingRADropdown').attr('disabled', true);
	$('#roomTypeRADropdown').attr('disabled', true);
	$('#searchRoomRA').prop('disabled', true);
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getOfferingListWithKey',
        data: {
        	"startYear": startYear,
        	"endYear": endYear,
			"term": term,
			"searchKeyword": searchOffering
        },
        success: function (data) {
        	var headers = "";
        	//console.log(data);
        	$("#tableModalRAOfferingList tbody tr").remove(); //removes all tr but not thead

        	$.each(data, function(i, currObject){
        		
        		var id = currObject.offeringId;
        		var degreeProgram = currObject.degreeProgram;
        		var course = currObject.course.courseCode;
        		var section = currObject.section;
        		
        		$.each(currObject.days, function(j, currDay){
        			var rows = "";
        			rows += "<tr id=\"" + id + "-id\" class=\"clicked-offering-inactive offeringRow "+id+"-id\" onclick=\"clickOffering(this.id)\">"+
    				"<td>"+degreeProgram+"</td>"+
    				"<td>"+course+"</td>"+
    				"<td>"+section+"</td>"+
    				"<td>"+currDay.classDay+"</td>"+
    				"<td>"+currDay.beginTime+"</td>"+
    				"<td>"+currDay.endTime+"</td>"
    				
    				if(currDay.room.roomCode === "No Room") rows+="<td>-</td>";
    				else rows+="<td>"+currDay.room.roomCode+"</td>";
        			
    				rows+="<tr>";
		    		//console.log(rows);
		    		$(rows).appendTo("#tableModalRAOfferingList tbody");
        		});
        		
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	
	fixForParallax();
}

function updateRoomList(offeringId){
	var arr = [];
	console.log(offeringId);
	arr = offeringId.split('-'); //arr[0] is ID
	
	$('#assignRoomRoomIDDump').val(offeringId); //for search Listeners cause di nila alam ung ID
	var selectedRoomType = $('#roomTypeRADropdown :selected').text();
	var selectedBuilding = $('#buildingRADropdown :selected').text();
	var searchRoom = $('#searchRoomRA').val();
	
	populateRoomAssignmentModalDropdown(offeringId, selectedRoomType, selectedBuilding);
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getRoomListWithKey',
        data: {
        	"offeringId": arr[0],
			"searchKeyword": searchRoom,
			"roomType": selectedRoomType,
			"building": selectedBuilding
        },
        success: function (data) {
        	
        	$("#tableModalRARoomList tbody tr").remove(); //removes all tr but not thead

        	$.each(data, function(i, currObject){
        		var rows = "";
        		var id = currObject.id;
        		var roomCode = currObject.roomCode;
        		var building = currObject.building.buildingName;
        		var roomType = currObject.roomType;
        		var roomCapacity = currObject.roomCapacity;

        		if(roomCode != "No Room" || roomCode != "None"){ rows += "<tr id=\"" + id + "-id\" class=\"clicked-room-inactive roomRow "+id+"-id\">";
				
				rows+="<td>"+roomCode+"</td>";
    			
				rows+=  "<td>"+roomType+"</td>"+
						"<td>"+building+"</td>"+
						"<td>"+roomCapacity+"</td>"+
						"<td><a class=\"btn btn-default\" type=\"button\" onclick=\"assignThisRoomToCourse(this.id)\" id=\"" + id + "-assign\">Assign</a>" + "</td>"+
						"<tr>";
	    		//console.log(rows);
	    		$(rows).appendTo("#tableModalRARoomList tbody");
				}
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

function populateRoomAssignmentModalDropdown(id, selectedRoomType, selectedBuilding){
	//populate Room Type
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateRoomTypeDropdown',
        success: function (data) {
        	$("#roomTypeRADropdown").empty(); //removes all options
        	$("#roomTypeRADropdown").removeAttr("onChange");
        	$("#roomTypeRADropdown").attr("onChange", "return updateRoomList(\""+id+"-type\")");
        	
        	if(selectedRoomType === "All"){
        		$("#roomTypeRADropdown").prepend("<option selected onclick=\"updateRoomList(this.id)\" id=\""+id+"-type"+"\">All</option>");
        	}else{
        		$("#roomTypeRADropdown").prepend("<option onclick=\"updateRoomList(this.id)\" id=\""+id+"-type"+"\">All</option>");
        	}
        	
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedRoomType === currOption){
        			var newoption = "<option selected onclick=\"updateRoomList(this.id)\" id=\""+id+"-type"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"updateRoomList(this.id)\" id=\""+id+"-type"+"\">" + currOption + "</option>";
            	}
        		
        		$("#roomTypeRADropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	
	//populate Building
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateBuildingDropdown',
        success: function (data) {
        	$("#buildingRADropdown").empty(); //removes all options
        	$("#buildingRADropdown").removeAttr("onChange");
        	$("#buildingRADropdown").attr("onChange", "return updateRoomList(\""+id+"-type\")");
        	
        	if(selectedBuilding === "All"){
        		$("#buildingRADropdown").prepend("<option selected onclick=\"updateRoomList(this.id)\" id=\""+id+"-building"+"\">All</option>");
        	}else{
        		$("#buildingRADropdown").prepend("<option onclick=\"updateRoomList(this.id)\" id=\""+id+"-building"+"\">All</option>");
        	}
        	
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedBuilding === currOption){
        			var newoption = "<option selected onclick=\"updateRoomList(this.id)\" id=\""+id+"-building"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"updateRoomList(this.id)\" id=\""+id+"-building"+"\">" + currOption + "</option>";
            	}
        		
        		$("#buildingRADropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

function clickOffering(id){
	//$('.clicked-offering-active').removeClass('success');
	$('.clicked-offering-active').addClass('clicked-offering-inactive');
	$('.clicked-offering-active').removeClass('clicked-offering-active');
	//$('.sideBar-cell-color').removeClass('sideBar-cell-color');

	$('.'+id).addClass('clicked-offering-active');
	$('.'+id).removeClass('clicked-offering-inactive');
	//$('.'+id).addClass('success');
	//$('#'+id).addClass('sideBar-cell-color');
	
	//once the user clicked on an offering, filters for rooms will now become enabled
	$('#buildingRADropdown').attr('disabled', false);
	$('#roomTypeRADropdown').attr('disabled', false);
	$('#searchRoomRA').prop('disabled', false);
	
	//set hidden input form for later use/for assigning
	$('#assignRoomOfferingIDDump').val(id);
	
	updateRoomList(id);
}

function exitRoomAssignmentModal(){
	//this function empties the dropdown list again and sets "All" as the default selected option
	$("#roomTypeRADropdown").empty(); //removes all options
	$("#roomTypeRADropdown").removeAttr("onChange");
	$("#roomTypeRADropdown").attr("onChange", "return updateRoomAssignmentModal(\"\")");
	$("#roomTypeRADropdown").prepend("<option selected>All</option>");
	
	$("#buildingRADropdown").empty(); //removes all options
	$("#buildingRADropdown").removeAttr("onChange");
	$("#buildingRADropdown").attr("onChange", "return updateRoomAssignmentModal(\"\")");
	$("#buildingRADropdown").prepend("<option selected>All</option>");
	
	$("#tableModalRARoomList tbody tr").remove();
	$("#tableModalRAOfferingList tbody tr").remove();
	
	$("#searchRoomRA").val("");
	$("#searchOfferingRA").val("");
	
	closeModal("roomAssignmentModal");
}

function assignThisRoomToCourse(id){
	var arr = [], arr2 = [];
	
	arr = id.split('-');
	var roomId = arr[0]; //roomID
	
	offeringId = $('#assignRoomOfferingIDDump').val();
	arr2 = offeringId.split('-');
	var offeringId = arr2[0]; //clicked OfferingID
	
	$.ajax({
        type: 'POST',
        dataType: 'json',
        cache: false,
        url: 'initiateRoomAssignment',
        data: {
        	"offeringId": offeringId,
			"roomId": roomId,
        },
        success: function (data) {
        	console.log($('#assignRoomCurrentAYDump').val());
        	updateRoomAssignmentModal($('#assignRoomCurrentAYDump').val());
        	clickOffering($('#assignRoomOfferingIDDump').val());
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

/**************************ROOM ASSIGNMENT**********************************/
/**************************VIEW OFFERINGS**********************************/
function clickViewOfferings(id){
	clickSideBar(id);
	makeActivePanel("#panelViewOfferings");
	
	updateViewOfferingsPanel();
}

function updateViewOfferingsPanel(){
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getAllAcademicYears',
        success: function (data) {
        	var headers = "";
        	//console.log(data);
        	$("#tableViewOfferings tbody tr").remove(); //removes all tr but not thead
        	
        	$.each(data, function(i, currObject){
        		var rows = "";
        		var id = currObject.id;
        		var AY = currObject.startYear +"-"+ currObject.endYear;
        		var term = currObject.term;
        		var isPublished = currObject.isPublished;
        		var unPublishedCount = currObject.unPublishedCount;
        		var concatId = currObject.startYear +"-"+ currObject.endYear + "-" + currObject.term;
        		
        		rows += "<tr id=\"" + id + "-id\" class=\"AYRow\">"+
        				"<td>"+AY+"</td>"+
        				"<td>"+term+"</td>";
        				
		        		if(isPublished === "0"){
							//rows += "<td><a class=\"btn btn-default publish-active\" type=\"button\" onclick=\"publishAYTermOffering(this.id, 1)\"  id=\"" + concatId + "-publish\"><span><i class=\"fa fa-check\"></i></span></a> <a class=\"btn btn-default publish-inactive pushed-option\" type=\"button\" onclick=\"publishAYTermOffering(this.id, 0)\"  id=\"" + concatId + "-unpublish\" disabled><span><i class=\"fa fa-close\"></i></span></a></td>";
							rows += "<td><label class='switch'>" +
								"<input id='" + id + "-id' type='checkbox' onclick='checkTogglePublish(this.id, this)'/>" +
								"<span class='slider round'></span>" +
								"</label></td>";
							rows += "<td><a class=\"btn btn-default\" type=\"button\" onclick=\"viewOfferingsInModal(this.id)\" data-toggle=\"modal\" data-target=\"#viewOfferingsModal\" data-backdrop=\"static\" data-keyboard=\"false\" id=\"" + concatId + "-view\"><span><i class=\"fa fa-search\"></i></span></a> ";
							rows+="<a class=\"btn btn-default edit-button\" type=\"button\" onclick=\"initEditOfferingListModal(this.id)\" id=\"" + concatId + "-edit\"><span><i class=\"fa fa-edit\"></i></span></a>";
							rows+="<a class=\"btn btn-default trash-button\" type=\"button\" onclick=\"confirmDeleteOfferingList(this.id)\" data-toggle=\"modal\" data-target=\"#deleteClickedOfferingList\" data-backdrop=\"static\" data-keyboard=\"false\" id=\"" + concatId + "-view\"><span><i class=\"fa fa-trash\"></i></span></a></td>";	
						}else{
							//rows += "<td><a class=\"btn btn-default publish-active pushed-option\" type=\"button\" onclick=\"publishAYTermOffering(this.id, 1)\"  id=\"" + concatId + "-publish\" disabled><span><i class=\"fa fa-check\"></i></span></a> <a class=\"btn btn-default publish-inactive\" type=\"button\" onclick=\"publishAYTermOffering(this.id, 0)\"  id=\"" + concatId + "-unpublish\"><span><i class=\"fa fa-close\"></i></span></a></td>";
							rows += "<td><label class='switch'>" +
								"<input id='" + id + "-id' type='checkbox' onclick='checkTogglePublish(this.id, this)' checked />" +
								"<span class='slider round'></span>" +
								"</label></td>";
							rows += "<td><a class=\"btn btn-default\" type=\"button\" onclick=\"viewOfferingsInModal(this.id)\" data-toggle=\"modal\" data-target=\"#viewOfferingsModal\" data-backdrop=\"static\" data-keyboard=\"false\" id=\"" + concatId + "-view\"><span><i class=\"fa fa-search\"></i></span></a> ";
							rows+="<a class=\"btn btn-default edit-button\" type=\"button\" id=\"" + concatId + "-edit\" disabled><span><i class=\"fa fa-edit\"></i></span></a>";
							rows+="<a class=\"btn btn-default trash-button\" type=\"button\" id=\"" + concatId + "-delete\" disabled><span><i class=\"fa fa-trash\"></i></span></a></td>";	
						}
        				//data backdrop static prevents exit from background while data-keyboard false prevents exit using keyboard keys
        				
        					rows+="</tr>";
        		$(rows).appendTo("#tableViewOfferings tbody");
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	fixForParallax();
}

function checkTogglePublish(id, checkbox){
//	alert("id: " + id + " | checkbox: " + checkbox.checked);
	if(checkbox.checked){// if it became true after clicked i.e. will be published
//		alert("hey");
		publishAYTermOffering(id, 1);
	} else {
		publishAYTermOffering(id, 0);
	}
}

function viewOfferingsInModal(id){
	var arr = [], daysList = [];  
	
	arr = id.split('-');
	
	var searchKeyword = $('#searchOfferingVO').val();
	var selectedTimeBlock = $('#timeBlockVODropdown :selected').text();
	var selectedStatus = $('#statusVODropdown :selected').text();
	var selectedBatch = $('#batchVODropdown :selected').text();
	var selectedRoomType = $('#roomTypeVODropdown :selected').text();
	var concatId = arr[0] +"-"+ arr[1] + "-" + arr[2];
	//console.log(id);
	
	if(isPure === 0){
		showLoadingModal();
	}
	
	$('#viewOfferingsCurrentAYDump').val(concatId); //for search Listeners cause di nila alam ung ID
	$('#viewOfferingsModalTitle').text("View A.Y " +arr[0]+ "-" + arr[1] + " Term " + arr[2] + " Offerings"); //change modal title
	
	if($('#toggleMVO').prop('checked')) daysList.push("M");
	if($('#toggleTVO').prop('checked')) daysList.push("T");
	if($('#toggleWVO').prop('checked')) daysList.push("W");
	if($('#toggleHVO').prop('checked')) daysList.push("H");
	if($('#toggleFVO').prop('checked')) daysList.push("F");
	if($('#toggleSVO').prop('checked')) daysList.push("S");
	
	daysList.push("none"); //para may value kahit naka untoggle lahat or else error na daysList[] not present
	
	populateViewOfferingsModalDropdownAndToggle(concatId, selectedRoomType, selectedTimeBlock, selectedBatch, selectedStatus); //concatId is startyear-endyear-term
	disableClassDayToggles(true);
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getAllOfferingsWithFilters',
        data: {
        	"startYear": arr[0],
        	"endYear": arr[1],
        	"term": arr[2],
			"searchKeyword": searchKeyword,
			"selectedTimeBlock": selectedTimeBlock,
			"daysList": daysList, //you must pass js array as JSON obj request param should have [] in Java
			"selectedBatch": selectedBatch,
			"selectedStatus": selectedStatus,
			"selectedRoomType": selectedRoomType
        },
        success: function (data) {
        	hideLoadingModal();
        	disableClassDayToggles(true);
        	$("#tableModalVOOfferingList tbody tr").remove(); //removes all tr but not thead

        	$.each(data, function(i, currObject){
        		var rows = "";
        		var offeringId = currObject.offeringId;
        		var degreeProgram = currObject.degreeProgram;
        		var course = currObject.course.courseCode;
        		var section = currObject.section;
        		var batch = currObject.batch;
        		var status = currObject.status;
        		var remarks = currObject.remarks;
        		var facultyName = currObject.faculty.firstName + " " + currObject.faculty.lastName;
        		//currObject.faculty.user.firstName + " " + currObject.faculty.user.lastName;
        		
    			rows += "<tr id=\"" + id + "-id\" class=\"clicked-offering-inactive offeringRow "+id+"-id\">"+
						"<td>"+degreeProgram+"</td>"+
						"<td>"+course+"</td>"+
						"<td>"+section+"</td>"+
						"<td>"+batch+"</td>";
				
    			var classDay="", beginTime="", endTime="", room="";
    			
    			$.each(currObject.days, function(j, currDay){
    				classDay+= currDay.classDay + "<br>";
    				beginTime+= currDay.beginTime + "<br>";
    				endTime+= currDay.endTime + "<br>";
    				
    				if(currDay.room.roomCode === "No Room") room+= "-<br>";
    				else room+= currDay.room.roomCode + "<br>";
    				
        		});
    			rows+=	"<td>"+classDay+"</td>"+
						"<td>"+beginTime+"</td>"+
						"<td>"+endTime+"</td>" +
						"<td>"+room+"</td>"+
						"<td>"+facultyName+"</td>"+
						"<td>"+status+"</td>"+
						"<td>"+remarks+"</td>";
						
						if(currObject.isPublished === "1"){
							rows+= "<td>" +
							/*"<a class=\"btn btn-default edit-button\" type=\"button\" onclick=\"modifyClickedOffering(this.id)\" id=\"" + offeringId + "-modify\" disabled><span><i class=\"fa fa-pencil\"></i></span></a>" +*/
							"<a class=\"btn btn-default trash-button\" type=\"button\" id=\"" + offeringId + "-"+ course + "-" + section +"-delete\" disabled><span><i class=\"fa fa-trash\"></i></span></a>" +
							"</td>";
						}else{
							rows+= "<td>" +
							/*"<a class=\"btn btn-default edit-button\" type=\"button\" onclick=\"modifyClickedOffering(this.id)\" id=\"" + offeringId + "-modify\"><span><i class=\"fa fa-pencil\"></i></span></a>" +*/
							"<a class=\"btn btn-default trash-button\" type=\"button\" onclick=\"confirmDeleteClickedOffering(this.id)\" data-toggle=\"modal\" data-target=\"#deleteClickedOfferingModal\" data-backdrop=\"static\" id=\"" + offeringId + "-"+ course + "-" + section + "-delete\"><span><i class=\"fa fa-trash\"></i></span></a>" +
							"</td>";
						}
						
						"<tr>";
	    		//console.log(rows);
	    		$(rows).appendTo("#tableModalVOOfferingList tbody");
        		
        	});
        	disableClassDayToggles(false);
        },
    	error: function (data){
        	hideLoadingModal();
    		console.log(data);
    	}
    });
}

function populateViewOfferingsModalDropdownAndToggle(AYId, selectedRoomType, selectedTimeBlock, selectedBatch, selectedStatus){
	var arr = [];
	arr = AYId.split('-');
	
	//populate Room Type
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateRoomTypeDropdown',
        success: function (data) {
        	$("#roomTypeVODropdown").empty(); //removes all options
        	$("#roomTypeVODropdown").removeAttr("onChange");
        	$("#roomTypeVODropdown").attr("onChange", "return viewOfferingsInModal(\""+AYId+"-type\")");
        	
        	if(selectedRoomType === "All"){
        		$("#roomTypeVODropdown").prepend("<option selected onclick=\"viewOfferingsInModal(this.id)\" id=\""+AYId+"-type"+"\">All</option>");
        	}else{
        		$("#roomTypeVODropdown").prepend("<option onclick=\"viewOfferingsInModal(this.id)\" id=\""+AYId+"-type"+"\">All</option>");
        	}
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedRoomType === currOption){
        			var newoption = "<option selected onclick=\"viewOfferingsInModal(this.id)\" id=\""+AYId+"-type"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"viewOfferingsInModal(this.id)\" id=\""+AYId+"-type"+"\">" + currOption + "</option>";
            	}
        		
        		$("#roomTypeVODropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	
	//populate Time Block
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateTimeBlockDropdown',
        data: {
        	"startYear": arr[0],
        	"endYear": arr[1],
        	"term": arr[2]
        },
        success: function (data) {
        	$("#timeBlockVODropdown").empty(); //removes all options
        	$("#timeBlockVODropdown").removeAttr("onChange");
        	$("#timeBlockVODropdown").attr("onChange", "return viewOfferingsInModal(\""+AYId+"-type\")");
        	
        	if(selectedTimeBlock === "All"){
        		$("#timeBlockVODropdown").prepend("<option selected onclick=\"viewOfferingsInModal(this.id)\" id=\""+AYId+"-type"+"\">All</option>");
        	}else{
        		$("#timeBlockVODropdown").prepend("<option onclick=\"viewOfferingsInModal(this.id)\" id=\""+AYId+"-type"+"\">All</option>");
        	}
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedTimeBlock === currOption){
        			var newoption = "<option selected onclick=\"viewOfferingsInModal(this.id)\" id=\""+AYId+"-type"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"viewOfferingsInModal(this.id)\" id=\""+AYId+"-type"+"\">" + currOption + "</option>";
            	}
        		
        		$("#timeBlockVODropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	
	//populate Batch
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateBatchDropdown',
        data: {
        	"startYear": arr[0],
        	"endYear": arr[1],
        	"term": arr[2]
        },
        success: function (data) {
        	$("#batchVODropdown").empty(); //removes all options
        	$("#batchVODropdown").removeAttr("onChange");
        	$("#batchVODropdown").attr("onChange", "return viewOfferingsInModal(\""+AYId+"-type\")");
        	
        	if(selectedBatch === "All"){
        		$("#batchVODropdown").prepend("<option selected onclick=\"viewOfferingsInModal(this.id)\" id=\""+AYId+"-type"+"\">All</option>");
        	}else{
        		$("#batchVODropdown").prepend("<option onclick=\"viewOfferingsInModal(this.id)\" id=\""+AYId+"-type"+"\">All</option>");
        	}
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedBatch === currOption){
        			var newoption = "<option selected onclick=\"viewOfferingsInModal(this.id)\" id=\""+AYId+"-type"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"viewOfferingsInModal(this.id)\" id=\""+AYId+"-type"+"\">" + currOption + "</option>";
            	}
        		
        		$("#batchVODropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	
	//populate Status
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateStatusDropdown',
        data: {
        	"startYear": arr[0],
        	"endYear": arr[1],
        	"term": arr[2]
        },
        success: function (data) {
        	$("#statusVODropdown").empty(); //removes all options
        	$("#statusVODropdown").removeAttr("onChange");
        	$("#statusVODropdown").attr("onChange", "return viewOfferingsInModal(\""+AYId+"-type\")");
        	
        	if(selectedStatus === "All"){
        		$("#statusVODropdown").prepend("<option selected onclick=\"viewOfferingsInModal(this.id)\" id=\""+AYId+"-type"+"\">All</option>");
        	}else{
        		$("#statusVODropdown").prepend("<option onclick=\"viewOfferingsInModal(this.id)\" id=\""+AYId+"-type"+"\">All</option>");
        	}
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedStatus === currOption){
        			var newoption = "<option selected onclick=\"viewOfferingsInModal(this.id)\" id=\""+AYId+"-type"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"viewOfferingsInModal(this.id)\" id=\""+AYId+"-type"+"\">" + currOption + "</option>";
            	}
        		
        		$("#statusVODropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}
	
function exitViewOfferingsModal(){
	$("#tableModalVOOfferingList tbody tr").remove();
	
	//this function empties the dropdown list again and sets "All" as the default selected option
	$("#roomTypeVODropdown").empty(); //removes all options
	$("#roomTypeVODropdown").removeAttr("onChange");
	$("#roomTypeVODropdown").attr("onChange", "return updateRoomAssignmentModal(\"\")");
	$("#roomTypeVODropdown").prepend("<option selected>All</option>");
	
	$("#batchVODropdown").empty(); //removes all options
	$("#batchVODropdown").removeAttr("onChange");
	$("#batchVODropdown").attr("onChange", "return updateRoomAssignmentModal(\"\")");
	$("#batchVODropdown").prepend("<option selected>All</option>");
	
	$("#statusVODropdown").empty(); //removes all options
	$("#statusVODropdown").removeAttr("onChange");
	$("#statusVODropdown").attr("onChange", "return updateRoomAssignmentModal(\"\")");
	$("#statusVODropdown").prepend("<option selected>All</option>");
	
	$("#timeBlockVODropdown").empty(); //removes all options
	$("#timeBlockVODropdown").removeAttr("onChange");
	$("#timeBlockVODropdown").attr("onChange", "return updateRoomAssignmentModal(\"\")");
	$("#timeBlockVODropdown").prepend("<option selected>All</option>");
	
	$("#searchOfferingVO").val("");
	closeModal("viewOfferingsModal");
	
	if(isPure === 1){
		isPure = 0;
	}
}

function disableClassDayToggles(state){ //true or false
	if(state === true){
		$('#toggleMVO').bootstrapToggle('disable');
		$('#toggleTVO').bootstrapToggle('disable');
		$('#toggleWVO').bootstrapToggle('disable');
		$('#toggleHVO').bootstrapToggle('disable');
		$('#toggleFVO').bootstrapToggle('disable');
		$('#toggleSVO').bootstrapToggle('disable');
	}else{
		$('#toggleMVO').bootstrapToggle('enable');
		$('#toggleTVO').bootstrapToggle('enable');
		$('#toggleWVO').bootstrapToggle('enable');
		$('#toggleHVO').bootstrapToggle('enable');
		$('#toggleFVO').bootstrapToggle('enable');
		$('#toggleSVO').bootstrapToggle('enable');
	}
}

function publishAYTermOffering(id, state){
	var arr = [];
	
	arr = id.split('-'); //arr[0] startyear, arr[1] endyear, arr[2] term, arr[3] extraneous string
	
	$.ajax({
        type: 'POST',
        dataType: 'json',
        cache: false,
        url: 'publishAYTerm',
        data: {
        	"startYear": arr[0],
        	"endYear": arr[1],
        	"term": arr[2],
			"state": state,
        },
        success: function (data) {
        	updateViewOfferingsPanel();
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}
/**************************VIEW OFFERINGS**********************************/

/**************************ADD NEW OFFERINGS*******************************/

function initAddNewOfferingsModal(){
	globalStartYear = $('#textStartYearANO').val();
	globalEndYear = $('#textEndYearANO').val();
	globalTerm = $('#textTermANO').val();
	//globalStudentCount = $('#textStudentCountANO').val();
	//globalSectionNumber = $('#textSectionNumberANO').val();
	
	$('#textStartYearANO').val('');
	$('#textEndYearANO').val('');
	$('#textTermANO').val('');
	//$('#textStudentCountANO').val('');
	//$('#textSectionNumberANO').val('');
	
	if(globalStartYear.length!=0 && globalEndYear.length!=0 && globalTerm.length!=0 /*&& globalStudentCount.length!=0 && globalSectionNumber.length!=0*/){
		changeANOPanel('divANOSearchCoursesPanel', 'divANORoomAssignmentPanel');
		$('#setNewOfferingsModal').modal('hide');
		$('#addNewOfferingsModal').modal('show');
		$('#saveEditedOfferingsButtonANO').hide();
		$('#saveNewOfferingsButtonANO').show(); //cause we're saving new offerings not through edited DB
		$('#addNewOfferingsModal').removeData('modal').modal({
			keyboard: false,
			backdrop: 'static',
			toggle: 'modal',
			target: '#addNewOfferingsModal'
		});
		$('#addNewOfferingsModalTitle').text('Offerings for AY '+globalStartYear+'-'+globalEndYear+' Term '+ globalTerm);
		
		updateAddNewOfferingsDropdowns();
	}
}

function updateAddNewOfferingsDropdowns(){
	//empty pa to sa simula
	/*var selectedDegreeProgram = $('#selectedDegreeProgramDropdown :selected').text();
	var selectedBatch = $('#batchANODropdown :selected').text();
	var selectedAY = $('#flowchartAYANODropdown :selected').text();*/
	//populate degree program
	
	var collegeId = $('#collegeIDDump').val();
	var deptId = $('#deptIDDump').val();
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateFlowchartDegreeProgramDropdown',
        data:{
        	"collegeId": collegeId,
        	"deptId": deptId
        },
        success: function (data) {
        	$("#flowchartDegreeProgramANODropdown").empty(); //removes all options
        	
        	var newoption = "<option selected onclick=\"\" id=\"\">none</option>";
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(i === 0){
        			var newoption = "<option selected onclick=\"\" id=\"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"\" id=\"\">" + currOption + "</option>";
            	}
        		
        		$("#flowchartDegreeProgramANODropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	//populate batch
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateFlowchartBatchDropdown',
        data:{
        	"collegeId": collegeId,
        	"deptId": deptId
        },
        success: function (data) {
        	$("#flowchartBatchANODropdown").empty(); //removes all options
        	
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(i === 0){
        			var newoption = "<option selected onclick=\"\" id=\"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"\" id=\"\">" + currOption + "</option>";
            	}
        		
        		$("#flowchartBatchANODropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	//populate AY
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateFlowchartAYDropdown',
        data:{
        	"collegeId": collegeId,
        	"deptId": deptId
        },
        success: function (data) {
        	$("#flowchartAYANODropdown").empty(); //removes all options
        	
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(i === 0){
        			var newoption = "<option selected onclick=\"\" id=\"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"\" id=\"\">" + currOption + "</option>";
            	}
        		
        		$("#flowchartAYANODropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

function updateAddNewOfferingsFlowchartCourseList(){
	
	var selectedDegreeProgram = $('#flowchartDegreeProgramANODropdown :selected').text();
	var selectedBatch = $('#flowchartBatchANODropdown :selected').text();
	var selectedAY = $('#flowchartAYANODropdown :selected').text();
	var year = [];
	year = selectedAY.split('-');
	var selectedTerm;
	
	if($('#radio1ANO').is(':checked')) selectedTerm = "1";
	else if($('#radio2ANO').is(':checked')) selectedTerm = "2";
	else selectedTerm = "3";
	
	/**********SET NEW OFFERING's SCHEDULE********/
	//$('#textStartYearANO').val(year[0]);
	//$('#textEndYearANO').val(year[1]);
	//$('#textTermANO').val(selectedTerm);
	/**********SET NEW OFFERING's SCHEDULE********/
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getAllFlowchartCourses',
        data:{
        	"selectedDegreeProgram": selectedDegreeProgram,
        	"selectedBatch": selectedBatch,
        	"selectedTerm": selectedTerm,
        	"startYear": year[0],
        	"endYear": year[1]
        },
        success: function (data) {
        	var headers = "";
        	//console.log(data);
        	$("#tableModalANOFlowchartCoursesList tbody tr").remove(); //removes all tr but not thead
        	
        	$.each(data, function(i, currObject){
        		var rows = "";
        		var id = currObject.courseId;
        		var courseCode = currObject.courseCode;
        		var courseName = currObject.courseName;
        	
        		var concatId = currObject.startYear +"-"+ currObject.endYear + "-" + currObject.term;
        		
        		rows += "<tr id=\"" + id + "-id\" class=\"AYRow\">"+
        				"<td>"+courseCode+"</td>"+
        				"<td>"+courseName+"</td>";
        				
        				rows += "<td>" + "<a class=\"btn btn-default add-button\" type=\"button\" onclick=\"addCourseToTemporaryOfferingTable(this.id)\" id=\"" + id+ "=" +selectedDegreeProgram +"="+ selectedBatch + "=add\"><span><i class=\"fa fa-plus\"></i></span></a></td></tr>"
        		$(rows).appendTo("#tableModalANOFlowchartCoursesList tbody");
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	fixForParallax();
}

function updateAddNewOfferingsCourseList(){
	
	var searchKeyword = $('#searchCourseANO').val();
	
	console.log("searching for: " + searchKeyword);
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getAllCoursesWithCourseKey',
        data:{
        	"searchKeyword": searchKeyword
        },
        success: function (data) {
        	console.log("search success for: " + searchKeyword);
        	var headers = "";
        	//console.log(data);
        	$("#tableModalANOCoursesList tbody tr").remove(); //removes all tr but not thead
        	
        	$.each(data, function(i, currObject){
        		var rows = "";
        		var id = currObject.courseId;
        		var courseCode = currObject.courseCode;
        		var courseName = currObject.courseName;
        	
        		var concatId = currObject.startYear +"-"+ currObject.endYear + "-" + currObject.term;
        		
        		rows += "<tr id=\"" + id + "-id\" class=\"AYRow\">"+
        				"<td>"+courseCode+"</td>"+
        				"<td>"+courseName+"</td>";
        				
        				rows += "<td>" + "<a class=\"btn btn-default add-button\" type=\"button\" onclick=\"addCourseToTemporaryOfferingTable(this.id)\" id=\"" + id + "=none=none=add\"><span><i class=\"fa fa-plus\"></i></span></a></td></tr>"
        		$(rows).appendTo("#tableModalANOCoursesList tbody");
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	fixForParallax();
}

function addCourseToTemporaryOfferingTable(courseID){
//	console.log("appenderID: " + appenderID);
	var arr = [];
	arr = courseID.split("="); //KAYA EQUALS KASI MAY '-' UNG DEGREE PROGRAM SO NAGIGIGNG DELIMETER UN
	//TWO TYPES: IF FLOWCHART UNG PAG IMPORT: arr[0] = courseID, arr[1] = degreeProgram, arr[2] = batch, arr[3] = extra string
	//IF SA ADD FROM DB: arr[0] = courseID, arr[1] = none, arr[2] = none, arr[3] = extra string
	var arraySwapHolder = [];
	var rowIdToBeAnimated = 0;
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getCourseWithID',
        data:{
        	"courseId": arr[0]
        },
        success: function (data) {
        	var headers = "";
        	//console.log(data);        	
        	
        	if(arr[1] === "none" || arr[2] === "none"){
        		var offering = new TempOffering("", (data.courseId + "-" + appenderID), data.courseCode, "", "", "", "Regular", "");
        	}else{
        		var offering = new TempOffering(arr[1], (data.courseId + "-" + appenderID), data.courseCode, "", arr[2], "", "Regular", "");
        	}
        	
        	arraySwapHolder[0] = offering;
        	tempCourseOfferings = $.merge(arraySwapHolder, $.merge([], tempCourseOfferings));
        	appenderID++;
        	
        	$.each(tempCourseOfferings, function(i, currObject){
        		var tempArr = currObject.courseId.split("-");
        		currObject.courseId = tempArr[0] + "-" + i;
        	});
        	
        	updateTemporaryOfferingList();
			
//        	console.log("offeringID: " + arr[0]);

        	rowIdToBeAnimated = arr[0] + "-" + 0 + "-id";
//        	console.log("rowIdToBeAnimated: " + rowIdToBeAnimated);
        	
        	animateColor(rowIdToBeAnimated);
        	
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	
	$.each(tempCourseOfferings, function(i, currObject){
		console.log("newshit: " + currObject.courseId);
	});
	
	fixForParallax();
}

function updateTemporaryOfferingList(){
	$("#tableModalANOOfferingList tbody tr").remove(); //removes all tr but not thead
	//console.log(tempCourseOfferings.length);
	$.each(tempCourseOfferings, function(i, currObject){
		var rows = "";
		var id = currObject.courseId;
		var timeslot = getTimeSlot(currObject.daysList1, currObject.daysList2, currObject.timeSlot1, currObject.timeSlot2);
		var degreeProgram = currObject.degreeProgram;
		var courseCode = currObject.courseCode;
		var section = currObject.section;
		var batch = currObject.batch;//$('#batchANODropdown').find(":selected").text();
		var term = currObject.term;
		var room = currObject.room; //roomCode 
		var status = currObject.status;
		var remarks = currObject.remarks;
		
		/***CHANGE THE ROW COLOR***/
		var foundMatch = 0;
		var arr = [], arr2 = [];
		//console.log($('#assignANORoomOfferingIDDump').val()+"--"+$('#assignANORoomOfferingIDDump').text());
		arr = $('#assignANORoomOfferingIDDump').val().split('-');
		arr2 = id.split('-');
		//console.log("hello"+arr[0]+"-"+arr[1]+"&&"+arr2[0]+"-"+arr2[1]);
		
		if(arr[0] === arr2[0] && arr[1] === arr2[1]){
			foundMatch = 1;
		}
		/***CHANGE THE ROW COLOR***/
		
		if(!foundMatch)
			rows += "<tr id=\"" + id + "-id\" class=\"AYRow\">";
		else rows += "<tr id=\"" + id + "-id\" class=\"AYRow match-room-offering \">";
		
		rows += "<td class=\"editable-cell\" id=\""+id+"-degreeProgram\" contenteditable>"+degreeProgram+"</td>"+
				"<td class=\"editable-cell\" id=\""+id+"-batch\" contenteditable>"+batch+"</td>"+
				"<td id=\""+id+"-courseCode\">"+courseCode+"</td>"+
				"<td class=\"editable-cell\" id=\""+id+"-section\" contenteditable>"+section+"</td>"+
				"<td id=\""+id+"-timeslot\">"+timeslot+"</td>";
				
				if(room === "" || room === "No Room" || room === "None"){
					rows += "<td><a class=\"btn btn-default assign-button\" type=\"button\" onclick=\"checkIfRoomAssignmentPossible(this.id)\" id=\""+id+"-room\"><span><i class=\"fa fa-chain\"></i></span></a></td>"
				}else{
					rows+="<td id=\""+id+"-room\">"+room+" <a class=\"btn btn-default unassign-button\" type=\"button\" onclick=\"unassignRoomToAnOfferingANO(this.id)\" id=\""+id+"-room\"><span><i class=\"fa fa-chain-broken\"></i></span></a></td>";
				}
        		//"<td style=\"text-align: center\"><a class=\"btn btn-default edit-button\" type=\"button\" onclick=\"editTemporaryOffering(this.id)\" data-toggle=\"modal\" data-target=\"#editTemporaryOfferingModal\" data-backdrop=\"static\" id=\""+id+"-edit\"><span><i class=\"fa fa-pencil\"></i></span></a></td>"+
				rows += "<td class=\"editable-cell\" id=\""+id+"-status\" contenteditable>"+status+"</td>"+
				"<td class=\"editable-cell\" id=\""+id+"-remarks\" contenteditable>"+remarks+"</td>";
				
				rows += "<td><a class=\"btn btn-default edit-button\" type=\"button\" onclick=\"editTemporaryOffering(this.id)\" data-toggle=\"modal\" data-target=\"#editTemporaryOfferingModal\" data-backdrop=\"static\" id=\""+id+"-edit\"><span><i class=\"fa fa-pencil\"></i></span></a><a class=\"btn btn-default trash-button\" type=\"button\" onclick=\"confirmDeleteTemporaryOffering(this.id)\" data-toggle=\"modal\" data-target=\"#deleteTemporaryOfferingModal\" data-backdrop=\"static\" id=\""+id+"-delete\"><span><i class=\"fa fa-trash\"></i></span></a></td></tr>"

				$(rows).appendTo("#tableModalANOOfferingList tbody");
	});
	
	
	
	if(hasInitializedEditableOfferingListenner === false)
		initEditableOfferingListeners();  //DO NOT PUT THIS INSIDE .EACH KASI MAG LOLOOP NG ILANG BESES UNG TAWAG DITO
}

function getTimeSlot(days1, days2, time1, time2){
	var timeslot="";
	
	if(days1.length != 0){
		$.each(days1, function(i, day){
			timeslot += day + "";
		});
		timeslot += "<br>" + time1 + "<br>";
	}
	if(days2.length != 0){
		$.each(days2, function(j, day){
			timeslot += day + "";
		});
		timeslot += "<br>" + time2;
	}
	
	if(days1.length === 0 && days2.length ===0) timeslot += "-";
	
	return timeslot;
}

function initEditableOfferingListeners(){
	
	$('.editable-cell').keyup(function() {
		//console.log($(this).attr('id'));
		saveRecentlyEditedTemporaryOfferingCell($(this).attr('id'));
	});
}

function confirmDeleteTemporaryOffering(id){
	var arr = [];
	arr = id.split('-'); //may extra word kaya dapat isplit
	var concatId = arr[0]+"-"+arr[1]; //course code is arr[0], appenderID is arr[1], extra word is arr[2]

	$('#inputTempOfferingIDDump').val(id); //for saving
	$.each(tempCourseOfferings, function(i, currObject){
		if(currObject.courseId === concatId){
			$('#deleteTemporaryOfferingModalTitle').text('Delete ' + currObject.courseCode + ' ' + currObject.section);
			$('#deleteTemporaryOfferingMessage').text('Are you sure you want to delete ' + currObject.courseCode + ' ' + currObject.section + '?');
		}
	});
}

function deleteTemporaryOffering(){
	var arr = [];
	var id = $('#inputTempOfferingIDDump').val();
	arr = id.split('-');
	console.log("inputTempOfferingIDDump:" + $('#inputTempOfferingIDDump').val());
	console.log("arr[1]" + arr[1]);
	
	var concatId = arr[0]+"-"+arr[1]; //course code is arr[0], appenderID is arr[1]
	var hasFound = 0;
	var deleteIndex = 0; //check if element to be deleted is found. If so, all succeedding elements will have their appenderID decreease by 1 to signify change in index
	var i = 0;
	
	$.each(tempCourseOfferings, function(i, currObject){
		if(hasFound === 1){
			arr = currObject.courseId.split('-'); //arr is local variable array. arr[0] is courseId, arr[1] is appenderID. Decrease appenderID by 1
			arr[1] = arr[1] - 1; //decrease by 1
			var newID = arr[0] + "-" + arr[1];
			//console.log(newID);
			currObject.courseId = newID;
		}
		if(currObject.courseId === concatId){
			hasFound = 1;
			//console.log(concatId);
			arr = currObject.courseId.split('-');
			deleteIndex = i;
		}
	});
	
	if(hasFound === 1){
		tempCourseOfferings.splice(deleteIndex, 1); //delete 1 element starting from index <deleteIndex>
		appenderID = appenderID - 1; //cause mababawasan so ung last appenderID is minus 1
		//console.log(tempCourseOfferings.length + " == "+ appenderID + " == " + deleteIndex);
		updateTemporaryOfferingList();
		hasFound = 0;
	}
}
function confirmDeleteOfferingList(id){ //delete for whole list
	var arr = [];
	arr = id.split('-'); //may extra word kaya dapat isplit
	var concatId = arr[0]+"-"+arr[1]+"-"+arr[2]; //startYear is arr[0], endYear is arr[1], term is arr[2], extra word is arr[3]
	$('#viewOfferingsPanelAYDump').val(concatId); //for saving

	$('#deleteClickedOfferingListTitle').text('Delete Offering List for AY ' + arr[0] + '-' + arr[1] + ' Term '+ arr[2]);
	$('#deleteClickedOfferingListMessage').text('Are you sure you want to delete Offering List for AY ' + arr[0] + '-' + arr[1] + ' Term '+ arr[2] + '?');
}

function deleteOfferingList(){
	var arr = [];
	var id = $('#viewOfferingsPanelAYDump').val();
	arr = id.split('-');
	
	$.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'deleteAnOfferingList',
        data: {
        	"startYear" : arr[0],
        	"endYear" : arr[1],
        	"term" : arr[2]
        },
        success: function (data) {
        	console.log(data);
        	updateViewOfferingsPanel();
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

function confirmDeleteClickedOffering(id){ //delete for view
	var arr = [];
	arr = id.split('-'); //may extra word kaya dapat isplit
	var concatId = arr[0]+"-"+arr[1]+"-"+arr[2]; //offeringID is arr[0], courseCode is arr[1], section is arr[2], extra word is arr[3]
    console.log(id);
	$('#viewOfferingsOfferingIDDump').val(id); //for saving

	$('#deleteClickedOfferingModalTitle').text('Delete ' + arr[1] + ' ' + arr[2]);
	$('#deleteClickedOfferingMessage').text('Are you sure you want to delete ' + arr[1] + ' ' + arr[2] + '?');
}

function deleteClickedOffering(){
	var arr = [];
	var id = $('#viewOfferingsOfferingIDDump').val();
	arr = id.split('-');
	
	$.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'deleteAnOffering',
        data: {
        	"offeringId" : arr[0]
        },
        success: function (data) {
        	console.log(data);
        	var AY = $('#viewOfferingsCurrentAYDump').val();
        	//arr = AY.split('-'); // arr[0] = StartYear, arr[1] = EndYear, arr[2] = Term
        	var concatId = AY+"-view";
        	viewOfferingsInModal(concatId); //update view
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

function editTemporaryOffering(id){
	var arr = [];
	arr = id.split('-');
	
	$('#inputTempOfferingIDDump').val(id); //for saving

	var concatId = arr[0]+"-"+arr[1];
	
	$.each(tempCourseOfferings, function(i, currObject){
		console.log();
		if(currObject.courseId === concatId){
			//var tempOffering = new TempOffering($('#flowchartDegreeProgramANODropdown').find(":selected").text(), arr[0], currObject.courseCode, currObject.section, $('#flowchartBatchANODropdown').find(":selected").text(), $('input[name=termRadio]:checked').val(), currObject.status, currObject.remarks);
			var tempOffering = new TempOffering(currObject.degreeProgram, arr[0], currObject.courseCode, currObject.section, currObject.batch, globalTerm, currObject.status, currObject.remarks); //degreeProgram, batch, term are global
			tempOffering.room = currObject.room;
			tempOffering.faculty = currObject.faculty;
			tempOffering.facultyId = currObject.facultyId;
			tempOffering.daysList1 = currObject.daysList1;
			tempOffering.daysList2 = currObject.daysList2;
			tempOffering.timeSlot1 = currObject.timeSlot1;
			tempOffering.timeSlot2 = currObject.timeSlot2;
			initEditTemporaryOfferingModal(tempOffering, concatId, "1"); //meaning local na global variable lang. NOT in DB
		}
	});
}

function getInlineSectionID(id, header){
	var res = id.split("-");
	res = res[0] + "-" + res[1] + "-" + header;
	//console.log("res: " + res);
	return res;
}

function initEditTemporaryOfferingModal(o, id, state){
	initEditTemporaryOfferingTogglesAndDropdowns(1);
	initEditTemporaryOfferingTogglesAndDropdowns(2);
	
	if(state === "1"){ //meaning temporary offering lang. not db offering ung eeditin
		$('#editTemporaryOfferingModalTitle').text("Edit " + o.courseCode + " " + o.section);
		$('#degreeProgramETO').val(o.degreeProgram);
		$('#batchETO').val(o.batch);
		$('#termETO').val(o.term);
		$('#facultyETO').val(o.faculty);
		$('#sectionETO').val(o.section);
		$('#statusETO').val(o.status);
		$('#remarksETO').val(o.remarks);
		$('#saveToDatabaseButton').hide();
		
	}
	/***************POPULATE TIME BLOCKS**********************/
	if(o.daysList1.length === 0 && o.daysList2.length === 0){
		populateTimeBlockEditTemporaryOfferingModal(id, "none", "none");
	}else if(o.daysList1.length != 0 && o.daysList2.length === 0){
		populateTimeBlockEditTemporaryOfferingModal(id, o.timeSlot1, "none");
	}else if(o.daysList1.length == 0 && o.daysList2.length != 0){
		populateTimeBlockEditTemporaryOfferingModal(id, "none", o.timeSlot2);
	}else{
		populateTimeBlockEditTemporaryOfferingModal(id, o.timeSlot1, o.timeSlot2);
	}
    /***************POPULATE TIME BLOCKS**********************/
	/***************TOGGLE DAY BUTTON**********************/
	$.each(o.daysList1, function(i, currDay){
		toggleEditTemporaryOfferingClassDay(currDay, 1);
	});
	$.each(o.daysList2, function(i, currDay){
		toggleEditTemporaryOfferingClassDay(currDay, 2);
	});
	
	/***************TOGGLE DAY BUTTON**********************/
}

function toggleEditTemporaryOfferingClassDay(day, list){ //true or false
	var disable = 1;
	
	if(list === 1){
		disable = 2;
	}
	
	if(day === "M"){
		$('#toggle'+ list +'METO').bootstrapToggle('on');
		$('#toggle'+ disable +'METO').bootstrapToggle('disable');
	}else if(day === "T"){
		$('#toggle'+ list +'TETO').bootstrapToggle('on');
		$('#toggle'+ disable +'TETO').bootstrapToggle('disable');
	}else if(day === "W"){
		$('#toggle'+ list +'WETO').bootstrapToggle('on');
		$('#toggle'+ disable +'WETO').bootstrapToggle('disable');
	}else if(day === "H"){
		$('#toggle'+ list +'HETO').bootstrapToggle('on');
		$('#toggle'+ disable +'HETO').bootstrapToggle('disable');
	}else if(day === "F"){
		$('#toggle'+ list +'FETO').bootstrapToggle('on');
		$('#toggle'+ disable +'FETO').bootstrapToggle('disable');
	}else if(day === "S"){
		$('#toggle'+ list +'SETO').bootstrapToggle('on');
		$('#toggle'+ disable +'SETO').bootstrapToggle('disable');
	}
}

function populateTimeBlockEditTemporaryOfferingModal(id, selectedTimeBlock1, selectedTimeBlock2){
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateTimeBlockETODropdown',
        success: function (data) {
        	$("#timeBlock1ETODropdown").empty(); //removes all options
        	$("#timeBlock2ETODropdown").empty();

        	if(selectedTimeBlock1 === "none"){
	        	var newoption = "<option selected id=\""+id+"-timeBlock1"+"\">none</option>";
        	}else{
        		var newoption = "<option id=\""+id+"-timeBlock1"+"\">none</option>";
        	}
        	
        	if(selectedTimeBlock2 === "none"){
	        	var newoption2 = "<option selected id=\""+id+"-timeBlock2"+"\">none</option>";
        	}else{
        		var newoption2 = "<option id=\""+id+"-timeBlock2"+"\">none</option>";
        	}
        	
        	$("#timeBlock1ETODropdown").append(newoption);
    		$("#timeBlock2ETODropdown").append(newoption2);
    		
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedTimeBlock1 === currOption){
        			var newoption = "<option selected id=\""+id+"-timeBlock1"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option id=\""+id+"-timeBlock1"+"\">" + currOption + "</option>";
            	}
        		
        		if(selectedTimeBlock2 === currOption){
        			var newoption2 = "<option selected id=\""+id+"-timeBlock2"+"\">" + currOption + "</option>";
            	}else{
            		var newoption2 = "<option id=\""+id+"-timeBlock2"+"\">" + currOption + "</option>";
            	}
        		
        		$("#timeBlock1ETODropdown").append(newoption);
        		$("#timeBlock2ETODropdown").append(newoption2);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

function saveTemporaryOffering(){

	var arr = [];
	var id = $('#inputTempOfferingIDDump').val(); 
	arr = id.split('-'); // sa format natin, courseid-appenderID-<cell> = both for class and Id
//	console.log("idInput: " + id);
	
	var degreeProgram = $("#degreeProgramETO").val();
	var section = $("#sectionETO").val();
	//var courseCode = $("degreeProgramETO").text();
	var batch = $("#batchETO").val();
	var term = $("#termETO").val();
	var status = $("#statusETO").val();
	var remarks = $("#remarksETO").val();
	//var room = $("#roomETO").val();
	var concatId = arr[0] + "-" + arr[1]; //for checking kasi naka concat ung courseId natin
	
	$.each(tempCourseOfferings, function(i, currObject){
		
		if(currObject.courseId === concatId){
			//console.log("found");
			var arr2 = concatId.split('-'); //second parameter is the appender and the index
			currObject.degreeProgram = degreeProgram;
			//currObject.courseCode is just the same so no need to edit
			currObject.section = section;
			currObject.batch = batch;
			currObject.term = term;
			currObject.status = status;
			currObject.room = currObject.room;
			currObject.faculty = currObject.faculty;
			currObject.facultyId = currObject.facultyId;
			currObject.remarks = remarks;
			currObject.daysList1 = []; //if the user clicked save again, babalik lang din un mga nakatoggle
			currObject.daysList2 = [];//magstack ung days if di inempty before lagyan ulit
			currObject = saveTemporaryOfferingTimeSlots(currObject, currObject.daysList1, 1);
			currObject = saveTemporaryOfferingTimeSlots(currObject, currObject.daysList2, 2);
			tempCourseOfferings[i] = currObject;
		}
	});
	
	updateTemporaryOfferingList();
	emptyEditTemporaryOfferingModal();
}

function saveTemporaryOfferingTimeSlots(currObject, days, list){
	var timeSlot = "";
	
	if($('#toggle'+list+'METO').prop('checked')) days.push("M");
	if($('#toggle'+list+'TETO').prop('checked')) days.push("T");
	if($('#toggle'+list+'WETO').prop('checked')) days.push("W");
	if($('#toggle'+list+'HETO').prop('checked')) days.push("H");
	if($('#toggle'+list+'FETO').prop('checked')) days.push("F");
	if($('#toggle'+list+'SETO').prop('checked')) days.push("S");
	
	if(days.length != 0){
		if(list === 1){
			currObject.daysList1 = days;
			currObject.timeSlot1 = $('#timeBlock1ETODropdown :selected').text();
		}else if(list === 2){ 
			currObject.daysList2 = days;
			currObject.timeSlot2 = $('#timeBlock2ETODropdown :selected').text();
		}
		else{ 
			currObject.daysList1 = [];
			currObject.daysList2 = [];
			currObject.timeSlot1 = "none";
			currObject.timeSlot2 = "none";
		}
	}
	
	/*if((currObject.daysList1.length === 0 && currObject.daysList2.length === 0))  console.log("1");
	if((currObject.daysList1.length === 0 && (currObject.daysList2.length != 0 && (currObject.timeSlot2 === "" || currObject.timeSlot2 === "none")))) console.log("2"); 
	if((currObject.daysList1.length !=0 && (currObject.timeSlot1 === "" || currObject.timeSlot1 === "none"))) console.log("3");
	if((currObject.daysList2.length === 0 && (currObject.daysList1.length != 0 && (currObject.timeSlot1 === "" || currObject.timeSlot1 === "none")))) console.log("4");
	if((currObject.daysList2.length !=0 && (currObject.timeSlot2 === "" || currObject.timeSlot2 === "none"))) console.log("5");
	*/
	var hasNoSchedule = 0;
	//IF WALANG DAY AND TIME AND NAGROOM ASSIGN NA BEFORE, REMOVE ROOM CAUSE DAPAT MAY SCHEDULE BEFORE ROOM ASSIGN
	if((currObject.daysList1.length === 0 && currObject.daysList2.length === 0) ||
			(currObject.daysList1.length === 0 && (currObject.daysList2.length != 0 && (currObject.timeSlot2 === "" || currObject.timeSlot2 === "none"))) || 
			(currObject.daysList1.length !=0 && (currObject.timeSlot1 === "" || currObject.timeSlot1 === "none")) ||
			(currObject.daysList2.length === 0 && (currObject.daysList1.length != 0 && (currObject.timeSlot1 === "" || currObject.timeSlot1 === "none"))) || 
			(currObject.daysList2.length !=0 && (currObject.timeSlot2 === "" || currObject.timeSlot2 === "none")))
	{
		hasNoSchedule = 1;
	}
	
	if(hasNoSchedule === 1){
		currObject.room = "";//remove assigned room if any
		updateANORoomList(currObject.courseId);
		changeANOPanel('divANOSearchCoursesPanel', 'divANORoomAssignmentPanel');
	}
	
	return currObject;
}

function emptyEditTemporaryOfferingModal(){
	$("#degreeProgramETO").val("");
	$("#sectionETO").val("");
	$("#batchETO").val("");
	$("#termETO").val("");
	$("#statusETO").val("");
	$("#remarksETO").val("");
	$("#facultyETO").val("");
	initEditTemporaryOfferingTogglesAndDropdowns(1);
	initEditTemporaryOfferingTogglesAndDropdowns(2);
}

function emptyAddNewOfferingsModal(){
	$("#textStartYearANO").val("");
	$("#textEndYearANO").val("");
	$("#textTermANO").val("");
	$("#searchCourseANO").val("");
	$("#divANOSearchRoomRA").val("");
	
	$("#tableModalANOCoursesList tbody tr").remove();
	$("#tableModalANOFlowchartCoursesList tbody tr").remove();
	$("#tableModalANOOfferingList tbody tr").remove();
	
	appenderID = 0;
	tempCourseOfferings = [];
	roomAppenderID = 0;
	tempRooms = [];
}


function initEditTemporaryOfferingTogglesAndDropdowns(list){
	$('#toggle'+ list +'METO').bootstrapToggle('off');
	$('#toggle'+ list +'TETO').bootstrapToggle('off');
	$('#toggle'+ list +'WETO').bootstrapToggle('off');
	$('#toggle'+ list +'HETO').bootstrapToggle('off');
	$('#toggle'+ list +'FETO').bootstrapToggle('off');
	$('#toggle'+ list +'SETO').bootstrapToggle('off');
	
	$('#toggle'+ list +'METO').bootstrapToggle('enable');
	$('#toggle'+ list +'TETO').bootstrapToggle('enable');
	$('#toggle'+ list +'WETO').bootstrapToggle('enable');
	$('#toggle'+ list +'HETO').bootstrapToggle('enable');
	$('#toggle'+ list +'FETO').bootstrapToggle('enable');
	$('#toggle'+ list +'SETO').bootstrapToggle('enable');
	$('#timeBlock'+list+'ETODropdown').empty();
}


function saveRecentlyEditedTemporaryOfferingCell(id){

	var arr = [];
	arr = id.split('-'); // sa format natin, courseid-appenderID-<cell> = both for class and Id
	var degreeProgram = $('#'+arr[0]+"-"+arr[1]+"-degreeProgram").text();
	var section = $('#'+arr[0]+"-"+arr[1]+"-section").text();
	var courseCode = $('#'+arr[0]+"-"+arr[1]+"-courseCode").text();
	var batch = $('#'+arr[0]+"-"+arr[1]+"-batch").text();
	var term = /*$('#textTermANO').text();*/ globalTerm; //global
	var status = $('#'+arr[0]+"-"+arr[1]+"-status").text();
	var remarks = $('#'+arr[0]+"-"+arr[1]+"-remarks").text();
	var concatId = arr[0] + "-" + arr[1]; //for checking kasi naka concat ung courseId natin
	
	$.each(tempCourseOfferings, function(i, currObject){
		if(currObject.courseId === concatId){
			//console.log("found");
			currObject.degreeProgram = degreeProgram;
			currObject.courseCode = courseCode;
			currObject.section = section;
			currObject.batch = batch;
			currObject.term = term;
			currObject.status = status;
			currObject.remarks = remarks;
		}
	});
}

function addNewOfferingsToDB(){
	
	var count = 0;
	/*******************EMPTY LIST FIRST TO AVOID DUPLICATES*********************/
	$.ajax({
        type: 'POST',
        dataType: 'json',
        url: 'emptyNewOfferingList',
        async: false,
        success: function (data) {
        	//console.log(data);
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	/*******************EMPTY LIST FIRST TO AVOID DUPLICATES*********************/
	
	if(tempCourseOfferings.length != 0){
		var status = false;
		$.each(tempCourseOfferings, function(i, currOffering){
			//console.log(currOffering.courseCode);
			if(currOffering.daysList1.length === 0){
				currOffering.daysList1.push("ND"); //servlet wont read this kapag walang laman. filter nalang ung ND sa DAO
			}
			if(currOffering.daysList2.length === 0){
				currOffering.daysList2.push("ND");
			}
		
			var arr = [];
			arr = currOffering.courseId.split('-'); //arr[0] is the course id
			/*$('#saveOfferingStartYearDump').val(startYear);
			$('#saveOfferingEndYearDump').val(endYear);
			$('#saveOfferingTermDump').val(term);
			$('#saveOfferingDegreeProgramDump').val(currOffering.degreeProgram);
			$('#saveOfferingCourseIDDump').val(arr[0]);
			$('#saveOfferingCourseCodeDump').val(currOffering.courseCode);
			$('#saveOfferingSectionDump').val(currOffering.section);
			$('#saveOfferingBatchDump').val(currOffering.batch);
			$('#saveOfferingStatusDump').val(currOffering.status);
			$('#saveOfferingRemarksDump').val(currOffering.remarks);
			var days1="", days2="";
			$.each(currOffering.daysList1, function(j, currDay){
				days1 = days1 + currDay + "-"
			});
			$.each(currOffering.daysList2, function(k, currDay){
				days2 = days2 + currDay + "-"
			});
			console.log(days1 +"---"+days2);
			$('#saveOfferingDaysList1Dump').val(days1);
			$('#saveOfferingDaysList2Dump').val(days2);
			$('#saveOfferingTimeSlot1Dump').val(currOffering.timeSlot1);
			$('#saveOfferingTimeSlot2Dump').val(currOffering.timeSlot2);
			$('#saveOfferingForm').submit();*/
			
			$.ajax({
		        type: 'POST',
		        dataType: 'json',
		        url: 'addNewOfferingList',
		        data: {
		        	"startYear": globalStartYear,
		        	"endYear": globalEndYear,
					"term": globalTerm,
					"degreeProgram": currOffering.degreeProgram,
					"courseId": arr[0],
					"courseCode": currOffering.courseCode,
					"section": currOffering.section,
					"batch": currOffering.batch,
					"status": currOffering.status,
					"remarks": currOffering.remarks,
					"room": currOffering.room,
					"facultyId": currOffering.facultyId,
					"daysList1": currOffering.daysList1,
					"daysList2": currOffering.daysList2,
					"timeSlot1": currOffering.timeSlot1,
					"timeSlot2": currOffering.timeSlot2
		        },
		        async: false,
		        success: function (data) {
		        	console.log(data.response);
		        	status = false;
		        },
		    	error: function (data){
		    		console.log(data);
		    		status = false;
		    	}
		    });	
			status = true;
		});
		/*******************ADD ALL LIST VALUES TO DB*********************/
		$.ajax({
	        type: 'POST',
	        dataType: 'json',
	        url: 'saveNewOfferingList',
	        async: false, /*async false is Script stops and waits for the server to send back a reply before continuing. There are some situations where Synchronous Ajax is mandatory.*/
	        success: function (data) {
	        	console.log(data);
	        },
	    	error: function (data){
	    		console.log(data);
	    	}
	    });
		
	}
	
	updateViewOfferingsPanel();
	emptyAddNewOfferingsModal();
}

function initEditOfferingListModal(id){
	var arr = [];
	arr = id.split('-'); //arr[0] = start year, arr[1] = end year, arr[2] = term, arr[3] = extra word
	
	globalStartYear = arr[0];
	globalEndYear = arr[1];
	globalTerm = arr[2];
	
	if(globalStartYear.length!=0 && globalEndYear.length!=0 && globalTerm.length!=0){
		changeANOPanel('divANOSearchCoursesPanel', 'divANORoomAssignmentPanel');
		$('#setNewOfferingsModal').modal('hide');
		$('#addNewOfferingsModal').modal('show');
		$('#saveEditedOfferingsButtonANO').show();//cause we're editing the offerings in the DB.
		$('#saveNewOfferingsButtonANO').hide(); 
		$('#addNewOfferingsModal').removeData('modal').modal({
			keyboard: false,
			backdrop: 'static',
			toggle: 'modal',
			target: '#addNewOfferingsModal'
		});
		$('#addNewOfferingsModalTitle').text('Offerings for AY '+globalStartYear+'-'+globalEndYear+' Term '+ globalTerm);
		updateAddNewOfferingsDropdowns();
		
		/*****LOAD THE COURSES/OFFERINGS TO BE EDITED IN THE DB*****/
		
		if(isPure === 0){
			showLoadingModal();
		}
		
		$.ajax({
	        type: 'GET',
	        dataType: 'json',
	        cache: false,
	        url: 'getAllOfferingsWithoutFilters',
	        data: {
	        	"startYear": arr[0],
	        	"endYear": arr[1],
	        	"term": arr[2]
	        },
	        success: function (data) {
	        	hideLoadingModal();
	        	appenderID = 0; //setAppenderID to zero
	        	roomAppenderID = 0;
	        	tempCourseOfferings = [];
	        	tempRooms = [];
	        	
	        	$.each(data, function(i, currObject){
	        		var offeringId = currObject.offeringId;
	        		var degreeProgram = currObject.degreeProgram;
	        		var courseId = currObject.course.courseId + "-" + appenderID;
	        		var courseCode = currObject.course.courseCode;
	        		var section = currObject.section;
	        		var batch = ""; //kasi integer ung batch sa DB so minsan nagiging zero
	        		if(currObject.batch != 0){
	        			batch = currObject.batch;
	        		}
	        		var term = currObject.term;
	        		var status = currObject.status;
	        		var remarks = currObject.remarks;
	        		var days1 = [];
	        		var days2 = [];
	        		var timeSlot1 = "";
	        		var timeSlot2 = "";
	        		var room = "";
	        		var faculty = currObject.faculty.firstName + " " + currObject.faculty.lastName;
	        		var facultyId = currObject.faculty.facultyId;
	        		//console.log(faculty + "---> "+ facultyId);
	        		
	        		/****DAYS****/
	        		$.each(currObject.days, function(j, currDay){
	        			var startTime = ('0' + currDay.beginTime).slice(-4); 
	        			var endTime = ('0' + currDay.endTime).slice(-4); 
	        			//minus 4 sa slice meaning dapat 4 digit number. Kapag 3 lang ung nadetect, add zero infront. Otherwise, if 4 digit nadetect, do nothing.
	        			//https://stackoverflow.com/questions/8513032/less-than-10-add-0-to-number
	        			var tempTime = startTime + "-" + endTime;

	        			if(timeSlot1 != "" && timeSlot1 === tempTime){
	        				days1.push(currDay.classDay);
	        			}else if(timeSlot1 === "" && timeSlot2 != tempTime){
	        				timeSlot1 = tempTime;
	        				days1.push(currDay.classDay);
	        			}else if(timeSlot2 != "" && timeSlot2 === tempTime){
	        				days2.push(currDay.classDay);
	        			}else if(timeSlot2 === "" && timeSlot1 != tempTime){
	        				timeSlot2 = tempTime;
	        				days2.push(currDay.classDay);
	        			}else{
	        				alert("Error importing time of "+ courseCode + " " + section);
	        			}
	        			
	        			/****ROOM****/
	        			if(currDay.room.roomCode === "No Room") room+= "";
	    				else{
	    					room = currDay.room.roomCode;
	    					//console.log(currDay.room.id);
	    					var roomObject = new TempRoom(currDay.room.id, currDay.room.roomCode, courseId);
	    					tempRooms[roomAppenderID] = roomObject;
	    					roomAppenderID++;
	    				}
	        			/****ROOM****/
	        		});
	        		/****DAYS****/
	        		var offering = new TempOffering(degreeProgram, courseId, courseCode, section, batch, term, status, remarks);
	        		offering.daysList1 = [];
	        		offering.daysList2 = [];
	        		offering.daysList1 = days1.slice(0); //Array.prototype.slice returns a shallow copy of a portion of an array. Giving it 0 as the first parameter means you are returning a copy of all the elements (starting at index 0 that is)
	        		offering.daysList2 = days2.slice(0);
	        		offering.timeSlot1 = timeSlot1;
	        		offering.timeSlot2 = timeSlot2;
	        		offering.room = room; //not confuse room with roomObject
	        		offering.facultyId = facultyId;
	        		offering.faculty = faculty;
	        		
	        		tempCourseOfferings[appenderID] = offering;
	        		//console.log(tempCourseOfferings[appenderID].courseCode);
	            	appenderID++; //for next offering
	            	updateTemporaryOfferingList();
	        	});
	        },
	    	error: function (data){
	    		hideLoadingModal();
	    		console.log(data);
	    	}
	    });	
		/*****LOAD THE COURSES/OFFERINGS TO BE EDITED IN THE DB*****/
	}
}

function addEditedOfferingsToDB(){
	var arr = [];
	
	$('#viewOfferingsPanelAYDump').val(globalStartYear + "-" + globalEndYear + "-"+ globalTerm + "-delete");
	//console.log($('#viewOfferingsPanelAYDump').val());
	deleteOfferingList(); //para madelete muna ung offering list sa DB then add the whole thing again.
	$('#viewOfferingsPanelAYDump').val(''); //make sure empty or else baka may madelete.
	addNewOfferingsToDB(); //add na
}

function closeModal(id){
	$('#'+id).modal('hide');
	$('body').removeClass('modal-open');
	$('.modal-backdrop').remove();
}

function addAllTempOfferings(){
	var indexAddBtnCol = 2;
	
	$("#tableModalANOFlowchartCoursesList").find("tbody > tr").each(function(e){
		var addBtn = $(this).find("td").eq(indexAddBtnCol).find("a");
		
//		console.log("addBtn: " + addBtn.attr("id"));
		addCourseToTemporaryOfferingTable(addBtn.attr("id"));
	});
}

function removeAllTempOfferings(){
	$("#tableModalANOOfferingList tbody tr").remove();
	
	appenderID = 0;
	tempCourseOfferings = [];
	roomAppenderID = 0;
	tempRooms = [];
	
	updateTemporaryOfferingList();
}
/**************************ADD NEW OFFERINGS*******************************/
/**************************ROOM ASSIGNMENT IN ADD NEW OFFERING MODAL**********************************/
function changeANOPanel(show, hide){
	//console.log(show + "---" + hide);
	$('#' + show).addClass('activePanel');
	$('#' + show).removeClass('inactivePanel');
	$('#' + hide).addClass('inactivePanel');
	$('#' + hide).removeClass('activePanel');
	
	$('#assignANORoomOfferingIDDump').val("");
	$('#assignANORoomRoomIDDump').val("");
	$('#assignANORoomAYIDDump').val("");
	
	updateTemporaryOfferingList();
}

function assignRoomToAnOfferingANO(id){
	
	var AYId = globalStartYear + "-" + globalEndYear + "-" + globalTerm + "-" + globalBatch;
	
	var arr = [];
	arr = id.split('-'); // 0 - courseId, 1 - appenderId, 2 - room Id, 3 - room Code, 4 - extra word
	var tempCourseID = arr[0];
	var tempAppenderID = arr[1];
	var roomID = arr[2];
	var roomCode = arr[3];
	console.log(id);
	
	var room = new TempRoom(roomID, roomCode, (tempCourseID + "-" + tempAppenderID + "-courseId")); //roomId, concatId(courseId, appenderId, courseId)
	tempRooms[roomAppenderID] = room;
	roomAppenderID++;
	tempCourseOfferings[tempAppenderID].room = roomCode; //change tempCourseOfferings element
	
	updateTemporaryOfferingList();
	updateANORoomList(arr[0]+"-"+arr[1]+"-update"); //tempOffering = not DB version so in this case, course palang to so we pass courseID
	
}

function unassignRoomToAnOfferingANO(id){
		
	var arr = [];
	arr = id.split('-'); // 0 - courseId, 1 - appenderId, 2 extra word
	var tempCourseID = arr[0];
	var tempAppenderID = arr[1];
	
	var deleteIndex = 0, hasFound = 0;
	$.each(tempRooms, function(i, currRoom){
		var arr2 = [];
		arr2 = currRoom.courseId.split('-');
		if(tempCourseID === arr2[0] && tempAppenderID === arr2[1]){	
			tempCourseOfferings[tempAppenderID].room = "";
			hasFound = 1;
			deleteIndex = i; //roomAppenderId
		}
	});
	
	if(hasFound === 1){
		tempRooms.splice(deleteIndex, 1); //delete 1 element starting from index <deleteIndex>
		roomAppenderID = roomAppenderID - 1; //cause mababawasan so ung last appenderID is minus 1
		//console.log(tempRooms.length + "---"+ roomAppenderID);
		//console.log(tempCourseOfferings.length + " == "+ appenderID + " == " + deleteIndex);
		hasFound = 0;
		updateTemporaryOfferingList();
		updateANORoomList(arr[0]+"-"+arr[1]+"-update"); //tempOffering = not DB version so in this case, course palang to so we pass courseID
		changeANOPanel('divANOSearchCoursesPanel', 'divANORoomAssignmentPanel');
	}
}

function checkIfTimeSlotCollide(timeSlot1, timeSlot2){
	var arr = [], arr2 = [];
	arr = timeSlot1.split("-"); //arr[0] is the startTime, arr[1] is the endTime
	arr2 = timeSlot2.split("-"); //arr[0] is the startTime, arr[1] is the endTime
	
	var startTS1 = arr[0];
	var startTS2 = arr2[0];
	var endTS1 = arr[1];
	var endTS2 = arr2[1];
	
	if(startTS2 >= startTS1 && startTS2 < endTS1) //bawal may equal sa endtime kasi pwede ung first class 7-11 tapos ung next is 11-12
		return 1; //1 meaning collide
	else if(startTS2 <= startTS1 && endTS2 > startTS1 && endTS2 < endTS1)
		return 1; //1 meaning collide
	else if(startTS1 <= startTS2 && endTS1 > startTS2 && endTS1 < endTS2)
		return 1; //1 meaning collide
	else if(startTS2 >= startTS1 && startTS2 < endTS1 && endTS2 > endTS1)
		return 1; //1 meaning collide
	else if(startTS1 >= startTS2 && startTS1 < endTS2 && endTS1 > endTS2)
		return 1; //1 meaning collide
	else return 0
	
}

function updateANORoomList(tempOfferingId){
	
	var arr = [];
	arr = tempOfferingId.split('-'); //arr[0] would be courseID, [1], appender, [2] extraneous text
	var id = arr[0] +"-"+ arr[1];
	var concatId = globalStartYear + "-" + globalEndYear + "-" + globalTerm + "-" + globalBatch;
	
	$('#assignANORoomCurrentAYDump').val(concatId);
	$('#assignANORoomOfferingIDDump').val(tempOfferingId); //for search Listeners cause di nila alam ung ID
	$('#assignANORoomRoomIDDump').val(tempOfferingId); //for search Listeners cause di nila alam ung ID
	updateTemporaryOfferingList(); // to change the color of the row selected
	
	var selectedRoomType = $('#divANORoomTypeRADropdown :selected').text();
	var selectedBuilding = $('#divANOBuildingRADropdown :selected').text();
	var searchRoom = $('#divANOSearchRoomRA').val();
	
	populateANORoomAssignmentDropdown(id, selectedRoomType, selectedBuilding);
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getRoomListANOWithKey',
        data: {
        	//"offeringId": arr[0],
			"searchKeyword": searchRoom,
			"roomType": selectedRoomType,
			"building": selectedBuilding
        },
        success: function (data) {
        	
        	$("#tableANOModalRARoomList tbody tr").remove(); //removes all tr but not thead

        	$.each(data, function(i, currObject){
        		var rows = "";
        		var roomId = currObject.id;
        		var roomCode = currObject.roomCode;
        		var building = currObject.building.buildingName;
        		var roomType = currObject.roomType;
        		var roomCapacity = currObject.roomCapacity;
        		var concatId = id + "-" + roomId + "-" + roomCode;
        		
        		var foundMatch = 0;
        		var takenRooms = [];
        		
        		$.each(tempRooms, function(i, currRoom){
        			var arr2 = [];
        			arr2 = currRoom.courseId.split('-');
        			//console.log(arr[0]+"-"+arr[1]+"&&"+arr2[0]+"-"+arr2[1]);
        			if(arr[0] === arr2[0] && arr[1] === arr2[1] && tempCourseOfferings[arr[1]].room === roomCode){
        				foundMatch = 1;
        			}
        			
        			/*******************CHECK FOR ROOM COLLISION***********************/
        			var firstDaysList1 = [], firstDaysList2 = [], secondDaysList1 = [], secondDaysList2 = [];
        			var firstTimeSlot1, firstTimeSlot2, secondTimeSlot1, secondTimeSlot2;
        			
        			firstDaysList1 = tempCourseOfferings[arr[1]].daysList1.slice(0); //0 means copy thw whole array
        			firstDaysList2 = tempCourseOfferings[arr[1]].daysList2.slice(0);
        			secondDaysList1 = tempCourseOfferings[arr2[1]].daysList1.slice(0);
        			secondDaysList2 = tempCourseOfferings[arr2[1]].daysList2.slice(0);
        			firstTimeSlot1 = tempCourseOfferings[arr[1]].timeSlot1;
        			firstTimeSlot2 = tempCourseOfferings[arr[1]].timeSlot2;
        			secondTimeSlot1 = tempCourseOfferings[arr2[1]].timeSlot1;
        			secondTimeSlot2 = tempCourseOfferings[arr2[1]].timeSlot2;
        			
        			var foundCollision = 0
        			
        			//compare first dayslist first
        			if(firstDaysList1.length != 0 && secondDaysList1.length != 0){
	        			$.each(firstDaysList1, function(j, currDay){
	        				$.each(secondDaysList1, function(j, currDay2){
	            				if(currDay === currDay2 && checkIfTimeSlotCollide(firstTimeSlot1, secondTimeSlot1) === 1){
	            					foundCollision = 1
	            				}
	            			});
	        			});
        			}
        			//compare second dayslist
        			if(firstDaysList2.length != 0 && secondDaysList2.length != 0){
	        			$.each(firstDaysList2, function(k, currDay){
	        				$.each(secondDaysList2, function(k, currDay2){
	            				if(currDay === currDay2 && checkIfTimeSlotCollide(firstTimeSlot2, secondTimeSlot2) === 1){
	            					foundCollision = 1
	            				}
	            			});
	        			});
        			}
        			//compare first dayslist and second dayslist
        			if(firstDaysList1.length != 0 && secondDaysList2.length != 0){
	        			$.each(firstDaysList1, function(l, currDay){
	        				$.each(secondDaysList2, function(l, currDay2){
	            				if(currDay === currDay2 && checkIfTimeSlotCollide(firstTimeSlot1, secondTimeSlot2) === 1){
	            					foundCollision = 1
	            				}
	            			});
	        			});
        			}
        			//compare second dayslist and first dayslist
        			if(firstDaysList2.length != 0 && secondDaysList1.length != 0){
	        			$.each(firstDaysList2, function(m, currDay){
	        				$.each(secondDaysList1, function(m, currDay2){
	            				if(currDay === currDay2 && checkIfTimeSlotCollide(firstTimeSlot2, secondTimeSlot1) === 1){
	            					foundCollision = 1
	            				}
	            			});
	        			});
        			}
        			
        			if(foundCollision === 1){
        				takenRooms.push(currRoom);
        				//roomTaken = 1;
        				//assignedCourseAppender = arr2[1];
        				console.log(tempCourseOfferings[arr2[1]].courseCode);
        			}
        			
        			/*******************CHECK FOR ROOM COLLISION***********************/	
        		});
        		
        		var roomTaken = 0, assignedCourseAppenderId = -999;
        		//if nasa takenRooms that means taken ung current Room.
        		$.each(takenRooms, function(n, currRoom){
        			var arr2 = [];
        			arr2 = currRoom.courseId.split('-'); // arr2[0] - courseId, arr2[1] - appender, arr[3] & arr[4] - extra strings
        			console.log(currRoom.roomId === roomId);
        			if(currRoom.roomId === roomId){
        				roomTaken = 1;
        				assignedCourseAppenderId = arr2[1];
        			}
        		});
        		/*****************IF UNG CURRENT OFFERING UNG NAKAASSIGN SA ROOM NA YUN***************/
        		if(roomTaken == 1){
	        		var assignedCourseCode = tempCourseOfferings[assignedCourseAppenderId].courseCode;
					var assignedSection = tempCourseOfferings[assignedCourseAppenderId].section;
					
					var tempString = assignedCourseCode + " " + assignedSection;
					var tempString2 = tempCourseOfferings[arr[1]].courseCode + " " + tempCourseOfferings[arr[1]].section;
        		}
        		/*****************IF UNG CURRENT OFFERING UNG NAKAASSIGN SA ROOM NA YUN***************/
				
    			if(foundMatch === 0 && roomTaken === 0) 
    				rows += "<tr id=\"" + id + "-id\" class=\"clicked-room-inactive roomRow "+id+"-id\">";
    			else if((foundMatch === 1 && roomTaken === 0) || (foundMatch === 1 && roomTaken === 1)) //second argument is kasi kapag ung current course ung assigned dun sa room meaning no collision kasi nakaassign na siya dun
    				rows += "<tr id=\"" + id + "-id\" class=\"roomRow match-room-offering "+id+"-id\">";
    			else if(foundMatch === 0 && roomTaken === 1 && (tempString != tempString2))
    				rows += "<tr id=\"" + id + "-id\" class=\"roomRow room-collision "+id+"-id\">";
    			else{
    				rows += "<tr id=\"" + id + "-id\" class=\"clicked-room-inactive roomRow "+id+"-id\">";
    			}
    			
				rows+="<td>"+roomCode+"</td>";
    			
				rows+=  "<td>"+roomType+"</td>"+
						"<td>"+building+"</td>"+
						"<td>"+roomCapacity+"</td>";
				
				if(roomTaken === 1 && (tempString != tempString2)){
					rows+= "<td>"+assignedCourseCode + " " + assignedSection +"</td>";
				}else rows+= "<td><a class=\"btn btn-default\" type=\"button\" onclick=\"assignRoomToAnOfferingANO(this.id)\" id=\"" + concatId + "-assign\">Assign</a>" + "</td>";
				
				rows+= "<tr>";
				
	    		//console.log(rows);
	    		$(rows).appendTo("#tableANOModalRARoomList tbody");
				
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

function checkIfRoomAssignmentPossible(tempOfferingId){
	//offering must have a schedule otherwise can't room assign
	var arr = [];
	arr = tempOfferingId.split('-'); //arr[0] would be courseID, [1], appender, [2] extraneous text
	
	var tempOffering = tempCourseOfferings[arr[1]];
	
	var hasNoSchedule = 0;
	if((tempOffering.daysList1.length === 0 && tempOffering.daysList2.length === 0) || 
			(tempOffering.daysList1.length === 0 && (tempOffering.daysList2.length != 0 && (tempOffering.timeSlot2 === "" || tempOffering.timeSlot2 === "none"))) || 
			(tempOffering.daysList1.length !=0 && (tempOffering.timeSlot1 === "" || tempOffering.timeSlot1 === "none")) ||
			(tempOffering.daysList2.length === 0 && (tempOffering.daysList1.length != 0 && (tempOffering.timeSlot1 === "" || tempOffering.timeSlot1 === "none"))) || 
			(tempOffering.daysList2.length !=0 && (tempOffering.timeSlot2 === "" || tempOffering.timeSlot2 === "none")))
	{
		hasNoSchedule = 1;
	}
	
	if(hasNoSchedule === 0){
		changeANOPanel('divANORoomAssignmentPanel', 'divANOSearchCoursesPanel');
		updateANORoomList(tempOfferingId);
	}else{ //if hasNoSchedule === 1
		changeANOPanel('divANOSearchCoursesPanel', 'divANORoomAssignmentPanel');
		$('#errorNoScheduleModal').modal('show');
		$('#errorNoScheduleModal').removeData('modal').modal({
			keyboard: false,
			backdrop: 'static',
			toggle: 'modal',
			target: '#errorNoScheduleModal'
		});
	}
}

function populateANORoomAssignmentDropdown(id, selectedRoomType, selectedBuilding){
	//courseId is courseID of temp offering-appenderID-extratext
	//populate Room Type
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateRoomTypeDropdown',
        success: function (data) {
        	$("#divANORoomTypeRADropdown").empty(); //removes all options
        	$("#diVANORoomTypeRADropdown").removeAttr("onChange");
        	$("#divANORoomTypeRADropdown").attr("onChange", "return updateANORoomList(\""+id+"-type\")");
        	
        	if(selectedRoomType === "All"){
        		$("#divANORoomTypeRADropdown").prepend("<option selected onclick=\"updateANORoomList(this.id)\" id=\""+id+"-type"+"\">All</option>");
        	}else{
        		$("#divANORoomTypeRADropdown").prepend("<option onclick=\"updateANORoomList(this.id)\" id=\""+id+"-type"+"\">All</option>");
        	}
        	
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedRoomType === currOption){
        			var newoption = "<option selected onclick=\"updateANORoomList(this.id)\" id=\""+id+"-type"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"updateANORoomList(this.id)\" id=\""+id+"-type"+"\">" + currOption + "</option>";
            	}
        		
        		$("#divANORoomTypeRADropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	
	//populate Building
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateBuildingDropdown',
        success: function (data) {
        	$("#divANOBuildingRADropdown").empty(); //removes all options
        	$("#divANOBuildingRADropdown").removeAttr("onChange");
        	$("#divANOBuildingRADropdown").attr("onChange", "return updateANORoomList(\""+id+"-type\")");
        	
        	if(selectedBuilding === "All"){
        		$("#divANOBuildingRADropdown").prepend("<option selected onclick=\"updateANORoomList(this.id)\" id=\""+id+"-building"+"\">All</option>");
        	}else{
        		$("#divANOBuildingRADropdown").prepend("<option onclick=\"updateANORoomList(this.id)\" id=\""+id+"-building"+"\">All</option>");
        	}
        	
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedBuilding === currOption){
        			var newoption = "<option selected onclick=\"updateANORoomList(this.id)\" id=\""+id+"-building"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"updateANORoomList(this.id)\" id=\""+id+"-building"+"\">" + currOption + "</option>";
            	}
        		
        		$("#divANOBuildingRADropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

function clickOffering(id){
	//$('.clicked-offering-active').removeClass('success');
	$('.clicked-offering-active').addClass('clicked-offering-inactive');
	$('.clicked-offering-active').removeClass('clicked-offering-active');
	//$('.sideBar-cell-color').removeClass('sideBar-cell-color');

	$('.'+id).addClass('clicked-offering-active');
	$('.'+id).removeClass('clicked-offering-inactive');
	//$('.'+id).addClass('success');
	//$('#'+id).addClass('sideBar-cell-color');
	
	//once the user clicked on an offering, filters for rooms will now become enabled
	$('#buildingRADropdown').attr('disabled', false);
	$('#roomTypeRADropdown').attr('disabled', false);
	$('#searchRoomRA').prop('disabled', false);
	
	//set hidden input form for later use/for assigning
	$('#assignRoomOfferingIDDump').val(id);
	
	updateRoomList(id);
}

function clearRoomAssignmentANOPanel(){
	//this function empties the dropdown list again and sets "All" as the default selected option
	$("#divANORoomTypeRADropdown").empty(); //removes all options
	$("#divANORoomTypeRADropdown").removeAttr("onChange");
	//$("#divANORoomTypeRADropdown").attr("onChange", "return updateRoomAssignmentModal(\"\")");
	$("#divANORoomTypeRADropdown").prepend("<option selected>All</option>");
	
	$("#divANOBuildingRADropdown").empty(); //removes all options
	$("#divANOBuildingRADropdown").removeAttr("onChange");
	//$("#divANOBuildingRADropdown").attr("onChange", "return updateRoomAssignmentModal(\"\")");
	$("#divANOBuildingRADropdown").prepend("<option selected>All</option>");
	
	$("#tableANOModalRARoomList tbody tr").remove();
	
	$("#divANOSearchRoomRA").val("");
}

function assignThisRoomToCourse(id){
	var arr = [], arr2 = [];
	
	arr = id.split('-');
	var roomId = arr[0]; //roomID
	
	offeringId = $('#assignRoomOfferingIDDump').val();
	arr2 = offeringId.split('-');
	var offeringId = arr2[0]; //clicked OfferingID
	
	$.ajax({
        type: 'POST',
        dataType: 'json',
        cache: false,
        url: 'initiateRoomAssignment',
        data: {
        	"offeringId": offeringId,
			"roomId": roomId,
        },
        success: function (data) {
        	console.log($('#assignRoomCurrentAYDump').val());
        	updateRoomAssignmentModal($('#assignRoomCurrentAYDump').val());
        	clickOffering($('#assignRoomOfferingIDDump').val());
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

/**************************ROOM ASSIGNMENT IN ADD NEW OFFERING MODAL**********************************/
/****EVENT LISTENERS FOR SEARCH****/
$('#searchOfferingRA').keyup(function() {
	updateRoomAssignmentModal($('#assignRoomCurrentAYDump').val());
	$("#tableModalRARoomList tbody tr").remove();
});

$('#searchOfferingRA').on('input',function(e){
	updateRoomAssignmentModal($('#assignRoomCurrentAYDump').val());
	$("#tableModalRARoomList tbody tr").remove();
});

$('#searchRoomRA').keyup(function() {
	updateRoomList($('#assignRoomRoomIDDump').val());
});

$('#searchRoomRA').on('input',function(e){
	updateRoomList($('#assignRoomRoomIDDump').val());
});

$('#divANOSearchRoomRA').keyup(function() {
	updateANORoomList($('#assignANORoomOfferingIDDump').val());
});

$('#divANOSearchRoomRA').on('input',function(e){
	updateANORoomList($('#assignANORoomOfferingIDDump').val());
});

$('#searchOfferingVO').keyup(function() {
	if(isPure === 0){
		isPure = 1;
	}
	
	viewOfferingsInModal($('#viewOfferingsCurrentAYDump').val());
});

//$('#searchOfferingVO').on('input',function(e){
//	viewOfferingsInModal($('#viewOfferingsCurrentAYDump').val());
//});

$('#searchCourseANO').keyup(function() {
	updateAddNewOfferingsCourseList();
});

//$('#searchCourseANO').on('input',function(e){
//	updateAddNewOfferingsCourseList();
//});

$('#textStartYearANO').on('input', function(e){
	var endYear = parseInt($('#textStartYearANO').val()) + 1;
	$('#textEndYearANO').val(endYear);
})
/****EVENT LISTENERS FOR SEARCH****/
/******EVENT LISTENERS FOR TABLE******/

/******EVENT LISTENERS FOR TABLE******/
/*********THIS FIXES ANY PROBLEM RELATED TO PARALLAX NOT UPDATING AFTER SCROLL OR DOM UPDATES************/
function fixForParallax(){
	jQuery(window).trigger('resize').trigger('scroll');
}
/*********THESE FIXES ANY PROBLEM RELATED TO MODAL SCROLLBARS************/
$(document).on('show.bs.modal', '.modal', function () {
    var zIndex = 1040 + (10 * $('.modal:visible').length);
    $(this).css('z-index', zIndex);
    setTimeout(function() {
        $('.modal-backdrop').not('.modal-stack').css('z-index', zIndex - 1).addClass('modal-stack');
    }, 0);
});

$(document).on('hidden.bs.modal', '.modal', function () {
    $('.modal:visible').length && $(document.body).addClass('modal-open');
});

$(document).ready(function(){
    /*********THESE FIXES ANY PROBLEM RELATED TO MODAL SCROLLBARS************/
    $('.modal').on('show.bs.modal', function () {
      if ($(document).height() > $(window).height()) {
        // no-scroll
        $('body').addClass("modal-open-noscroll");
      }
      else { 
        $('body').removeClass("modal-open-noscroll");
      }
    })
    $('.modal').on('hide.bs.modal', function () {
        $('body').removeClass("modal-open-noscroll");
    })
    /*********THESE FIXES ANY PROBLEM RELATED TO MODAL SCROLLBARS************/
    $('#flowchartFiles').change(function(event, numFiles, label) {
    	var files = $(this)[0].files;
    	
    	if(files.length > 1){
    		$("#uploadText").val(files.length + " files selected");
    	} else {
    		$("#uploadText").val($('#flowchartFiles').val().replace(/C:\\fakepath\\/i, ''));
    	}
    });
})
 /*********THESE FIXES ANY PROBLEM RELATED TO MODAL SCROLLBARS************/
$(document).on('hidden.bs.modal', function () {
	$('body').addClass('modal-open');
});
/*********THESE FIXES ANY PROBLEM RELATED TO MODAL SCROLLBARS************/
/*********LOADING MODAL****************/
function showLoadingModal(){
	if(!$('#loadingModal').hasClass("in")){
		$('#loadingModal').modal({
		    show: true,
		    backdrop: 'static',
		    keyboard: false
		});
	}
}

function hideLoadingModal(){
	$("#loadingModal").modal("hide");
}
/*********LOADING MODAL****************/
/***************ROW ANIMATION***************************/
function animateColor(rowId){
//	console.log("rowId: " + rowId);
	$("#" + rowId).effect("highlight", {color: "#3ca53a"}, 1000);
}
/***************ROW ANIMATION***************************/
/***************EXPORT********************/
function clickExport(id){
	clickSideBar(id);
	makeActivePanel("#panelExport");
	updateExportAYDropDown();
	fixForParallax();
}

function updateExportAYDropDown(){
	var collegeId = $('#collegeIDDump').val();
	var deptId = $('#deptIDDump').val();
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateOfferingAYDropdown',
        data:{
        	"collegeId": collegeId,
        	"deptId": deptId
        },
        success: function (data) {
        	$("#exportPanelAYDropdown").empty(); //removes all options
        	
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(i === 0){
        			var newoption = "<option selected onclick=\"\" id=\"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"\" id=\"\">" + currOption + "</option>";
            	}
        		
        		$("#exportPanelAYDropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

function download(filename, text) {
	var element = document.createElement('a');
	element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(text));
	element.setAttribute('download', filename);
	
	element.style.display = 'none';
	document.body.appendChild(element);
	
	element.click();
	
	document.body.removeChild(element);
}

function exportOffering(){
	var collegeId = $('#collegeIDDump').val();
	var deptId = $('#deptIDDump').val();
	
	var tableName = "Offering";
	var academicYear = $("#exportPanelAYDropdown option:selected").text();
	var term = $('input[name=flowchartTerm]:checked', '#exportPanelTermRadioButtons').val()
	
//	console.log("tablename: " + tableName);
//	console.log("ay: " + academicYear);
//	console.log("term: " + term);
	
	showLoadingModal();
	
	$.ajax({
        type: 'POST',
        dataType: 'json',
        cache: false,
        url: 'exportOffering',
        data: {
        	"tableName": tableName,
			"academicYear": academicYear,
			"term" : term
        },
        success: function (data) {
        	hideLoadingModal();
        	var filename = "Loads-" + academicYear + "-" + term;
        	
        	download(filename, data.message);
        },
    	error: function (data){
        	hideLoadingModal();
    		console.log(data);
    	}
    });
}

/***************EXPORT********************/

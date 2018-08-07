/***ADD OFFERINGS GLOBAL VARIABLES***/
var hasInitializedEditableOfferingListenner = false;
var globalStartYear, globalEndYear, globalTerm, globalBatch, globalDegreeProgram, globalStudentCount, globalSectionNumber;
/***ADD OFFERINGS GLOBAL VARIABLES***/
/***GLOBAL VARIABLES FOR ADD NEW OFFERING FUNCTION***/
var tempCourseOfferings = [];
var tempFacultys = [];
var facultyAppenderID = 0;
var appenderID = 0;
var tempRooms = [];
var roomAppenderID = 0;
var selectedTemporaryOfferingID = "";
var selectedRoomID = "";
var clickedOfferingFA = ""
var globalAssignedOfferingFA = "";
var globalFacultyOfferingList = [];
/***GLOBAL VARIABLES FOR ADD NEW OFFERING FUNCTION***/
/******ADD NEW COURSE ***********/
var newCourseSuggestList = [];
/******ADD NEW COURSE***********/
/********LOADING MODAL VARIABLES********/
var isPure = 0;
/********LOADING MODAL VARIABLES********/

/*** Global Variables for some CVC Functions ***/
var glbStartYear, glbEndYear, glbTerm, glbFacultyId;

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

/**************************VIEW OFFERINGS WITH FACULTY ASSIGNMENT**********************************/
function clickViewOfferingsCVC(id){
	clickSideBar(id);
	makeActivePanel("#panelViewOfferingsCVC");
	
	updateViewOfferingsPanel();
}

function updateViewOfferingsPanel(){
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getAllAcademicYearsCVC',
        success: function (data) {
        	var headers = "";
        	//console.log(data);
        	$("#tableViewOfferingsCVC tbody tr").remove(); //removes all tr but not thead
        	
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
        				
		        		if(isPublished === "1"){
							//rows += "<td><a class=\"btn btn-default publish-active\" type=\"button\" onclick=\"publishAYTermOffering(this.id, 1)\"  id=\"" + concatId + "-publish\"><span><i class=\"fa fa-check\"></i></span></a> <a class=\"btn btn-default publish-inactive pushed-option\" type=\"button\" onclick=\"publishAYTermOffering(this.id, 0)\"  id=\"" + concatId + "-unpublish\" disabled><span><i class=\"fa fa-close\"></i></span></a></td>";
							
							rows += "<td><a class=\"btn btn-default\" type=\"button\" onclick=\"viewOfferingsInModalCVC(this.id)\" data-toggle=\"modal\" data-target=\"#viewOfferingsModal\" data-backdrop=\"static\" data-keyboard=\"false\" id=\"" + concatId + "-view\"><span><i class=\"fa fa-search\"></i></span></a> ";
							//rows+="<a class=\"btn btn-default edit-button\" type=\"button\" onclick=\"initEditDeloadOfferingListModal(this.id)\" id=\"" + concatId + "-edit\"><span><i class=\"fa fa-edit\"></i></span></a>";
							rows+="<a class=\"btn btn-default assign-button\" type=\"button\" onclick=\"initFacultyAssignmentModal(this.id)\" data-toggle=\"modal\" data-target=\"#facultyAssignmentModal\" data-backdrop=\"static\" data-keyboard=\"false\" id=\"" + concatId + "-facultyassign\"><span><i class=\"fa fa-star\"></i></span></a></td>";	
		        		}else{
							//rows += "<td><a class=\"btn btn-default publish-active pushed-option\" type=\"button\" onclick=\"publishAYTermOffering(this.id, 1)\"  id=\"" + concatId + "-publish\" disabled><span><i class=\"fa fa-check\"></i></span></a> <a class=\"btn btn-default publish-inactive\" type=\"button\" onclick=\"publishAYTermOffering(this.id, 0)\"  id=\"" + concatId + "-unpublish\"><span><i class=\"fa fa-close\"></i></span></a></td>";
							
							rows += "<td><a class=\"btn btn-default\" type=\"button\" onclick=\"viewOfferingsInModalCVC(this.id)\" data-toggle=\"modal\" data-target=\"#viewOfferingsModal\" data-backdrop=\"static\" data-keyboard=\"false\" id=\"" + concatId + "-view\"><span><i class=\"fa fa-search\"></i></span></a> ";
							//rows+="<a class=\"btn btn-default edit-button\" type=\"button\" id=\"" + concatId + "-edit\" disabled><span><i class=\"fa fa-edit\"></i></span></a>";
							rows+="<a class=\"btn btn-default assign-button\" type=\"button\" id=\"" + concatId + "-facultyassign\" disabled><span><i class=\"fa fa-star\"></i></span></a></td>";		
						}
        				//data backdrop static prevents exit from background while data-keyboard false prevents exit using keyboard keys
        				
        					rows+="</tr>";
        		$(rows).appendTo("#tableViewOfferingsCVC tbody");
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	fixForParallax();
}

function viewOfferingsInModalCVC(id){
	var arr = [], daysList = [];  
	
	arr = id.split('-');
	
	var searchKeyword = $('#CVCsearchOfferingVO').val();
	var selectedTimeBlock = $('#CVCtimeBlockVODropdown :selected').text();
	var selectedStatus = $('#CVCstatusVODropdown :selected').text();
	var selectedBatch = $('#CVCbatchVODropdown :selected').text();
	var selectedRoomType = $('#CVCroomTypeVODropdown :selected').text();
	var concatId = arr[0] +"-"+ arr[1] + "-" + arr[2];
	//console.log(id);
	
	if(isPure === 0){
		showLoadingModal();
	}
	
	$('#viewOfferingsCurrentAYDump').val(concatId); //for search Listeners cause di nila alam ung ID
	$('#viewOfferingsModalTitle').text("View A.Y " +arr[0]+ "-" + arr[1] + " Term " + arr[2] + " Offerings"); //change modal title
	
	if($('#CVCtoggleMVO').prop('checked')) daysList.push("M");
	if($('#CVCtoggleTVO').prop('checked')) daysList.push("T");
	if($('#CVCtoggleWVO').prop('checked')) daysList.push("W");
	if($('#CVCtoggleHVO').prop('checked')) daysList.push("H");
	if($('#CVCtoggleFVO').prop('checked')) daysList.push("F");
	if($('#CVCtoggleSVO').prop('checked')) daysList.push("S");
	
	daysList.push("none"); //para may value kahit naka untoggle lahat or else error na daysList[] not present
	
	populateViewOfferingsModalDropdownAndToggle(concatId, selectedRoomType, selectedTimeBlock, selectedBatch, selectedStatus); //concatId is startyear-endyear-term
	disableClassDayToggles(true);
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getAllOfferingsWithFiltersCVC',
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
        		var facultyName = currObject.faculty.user.firstName + " " + currObject.faculty.user.lastName;
        		
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
						
						if(currObject.course.courseType === "TL"){
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
						
				rows+= "</tr>";
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
        url: 'populateRoomTypeDropdownCVC',
        success: function (data) {
        	$("#CVCroomTypeVODropdown").empty(); //removes all options
        	$("#CVCroomTypeVODropdown").removeAttr("onChange");
        	$("#CVCroomTypeVODropdown").attr("onChange", "return viewOfferingsInModalCVC(\""+AYId+"-type\")");
        	
        	if(selectedRoomType === "All"){
        		$("#CVCroomTypeVODropdown").prepend("<option selected onclick=\"viewOfferingsInModalCVC(this.id)\" id=\""+AYId+"-type"+"\">All</option>");
        	}else{
        		$("#CVCroomTypeVODropdown").prepend("<option onclick=\"viewOfferingsInModalCVC(this.id)\" id=\""+AYId+"-type"+"\">All</option>");
        	}
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedRoomType === currOption){
        			var newoption = "<option selected onclick=\"viewOfferingsInModalCVC(this.id)\" id=\""+AYId+"-type"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"viewOfferingsInModalCVC(this.id)\" id=\""+AYId+"-type"+"\">" + currOption + "</option>";
            	}
        		
        		$("#CVCroomTypeVODropdown").append(newoption);
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
        url: 'populateTimeBlockDropdownCVC',
        data: {
        	"startYear": arr[0],
        	"endYear": arr[1],
        	"term": arr[2]
        },
        success: function (data) {
        	$("#CVCtimeBlockVODropdown").empty(); //removes all options
        	$("#CVCtimeBlockVODropdown").removeAttr("onChange");
        	$("#CVCtimeBlockVODropdown").attr("onChange", "return viewOfferingsInModalCVC(\""+AYId+"-type\")");
        	
        	if(selectedTimeBlock === "All"){
        		$("#CVCtimeBlockVODropdown").prepend("<option selected onclick=\"viewOfferingsInModalCVC(this.id)\" id=\""+AYId+"-type"+"\">All</option>");
        	}else{
        		$("#CVCtimeBlockVODropdown").prepend("<option onclick=\"viewOfferingsInModalCVC(this.id)\" id=\""+AYId+"-type"+"\">All</option>");
        	}
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedTimeBlock === currOption){
        			var newoption = "<option selected onclick=\"viewOfferingsInModalCVC(this.id)\" id=\""+AYId+"-type"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"viewOfferingsInModalCVC(this.id)\" id=\""+AYId+"-type"+"\">" + currOption + "</option>";
            	}
        		
        		$("#CVCtimeBlockVODropdown").append(newoption);
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
        url: 'populateBatchDropdownCVC',
        data: {
        	"startYear": arr[0],
        	"endYear": arr[1],
        	"term": arr[2]
        },
        success: function (data) {
        	$("#CVCbatchVODropdown").empty(); //removes all options
        	$("#CVCbatchVODropdown").removeAttr("onChange");
        	$("#CVCbatchVODropdown").attr("onChange", "return viewOfferingsInModalCVC(\""+AYId+"-type\")");
        	
        	if(selectedBatch === "All"){
        		$("#CVCbatchVODropdown").prepend("<option selected onclick=\"viewOfferingsInModalCVC(this.id)\" id=\""+AYId+"-type"+"\">All</option>");
        	}else{
        		$("#CVCbatchVODropdown").prepend("<option onclick=\"viewOfferingsInModalCVC(this.id)\" id=\""+AYId+"-type"+"\">All</option>");
        	}
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedBatch === currOption){
        			var newoption = "<option selected onclick=\"viewOfferingsInModalCVC(this.id)\" id=\""+AYId+"-type"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"viewOfferingsInModalCVC(this.id)\" id=\""+AYId+"-type"+"\">" + currOption + "</option>";
            	}
        		
        		$("#CVCbatchVODropdown").append(newoption);
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
        url: 'populateStatusDropdownCVC',
        data: {
        	"startYear": arr[0],
        	"endYear": arr[1],
        	"term": arr[2]
        },
        success: function (data) {
        	$("#CVCstatusVODropdown").empty(); //removes all options
        	$("#CVCstatusVODropdown").removeAttr("onChange");
        	$("#CVCstatusVODropdown").attr("onChange", "return viewOfferingsInModalCVC(\""+AYId+"-type\")");
        	
        	if(selectedStatus === "All"){
        		$("#CVCstatusVODropdown").prepend("<option selected onclick=\"viewOfferingsInModalCVC(this.id)\" id=\""+AYId+"-type"+"\">All</option>");
        	}else{
        		$("#CVCstatusVODropdown").prepend("<option onclick=\"viewOfferingsInModalCVC(this.id)\" id=\""+AYId+"-type"+"\">All</option>");
        	}
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedStatus === currOption){
        			var newoption = "<option selected onclick=\"viewOfferingsInModalCVC(this.id)\" id=\""+AYId+"-type"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"viewOfferingsInModalCVC(this.id)\" id=\""+AYId+"-type"+"\">" + currOption + "</option>";
            	}
        		
        		$("#CVCstatusVODropdown").append(newoption);
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
	$("#CVCroomTypeVODropdown").empty(); //removes all options
	$("#CVCroomTypeVODropdown").removeAttr("onChange");
	$("#CVCroomTypeVODropdown").attr("onChange", "return updateRoomAssignmentModal(\"\")");
	$("#CVCroomTypeVODropdown").prepend("<option selected>All</option>");
	
	$("#CVCbatchVODropdown").empty(); //removes all options
	$("#CVCbatchVODropdown").removeAttr("onChange");
	$("#CVCbatchVODropdown").attr("onChange", "return updateRoomAssignmentModal(\"\")");
	$("#CVCbatchVODropdown").prepend("<option selected>All</option>");
	
	$("#CVCstatusVODropdown").empty(); //removes all options
	$("#CVCstatusVODropdown").removeAttr("onChange");
	$("#CVCstatusVODropdown").attr("onChange", "return updateRoomAssignmentModal(\"\")");
	$("#CVCstatusVODropdown").prepend("<option selected>All</option>");
	
	$("#CVCtimeBlockVODropdown").empty(); //removes all options
	$("#CVCtimeBlockVODropdown").removeAttr("onChange");
	$("#CVCtimeBlockVODropdown").attr("onChange", "return updateRoomAssignmentModal(\"\")");
	$("#CVCtimeBlockVODropdown").prepend("<option selected>All</option>");
	
	$("#CVCsearchOfferingVO").val("");
	closeModal("viewOfferingsModal");
}

function disableClassDayToggles(state){ //true or false
	if(state === true){
		$('#CVCtoggleMVO').bootstrapToggle('disable');
		$('#CVCtoggleTVO').bootstrapToggle('disable');
		$('#CVCtoggleWVO').bootstrapToggle('disable');
		$('#CVCtoggleHVO').bootstrapToggle('disable');
		$('#CVCtoggleFVO').bootstrapToggle('disable');
		$('#CVCtoggleSVO').bootstrapToggle('disable');
	}else{
		$('#CVCtoggleMVO').bootstrapToggle('enable');
		$('#CVCtoggleTVO').bootstrapToggle('enable');
		$('#CVCtoggleWVO').bootstrapToggle('enable');
		$('#CVCtoggleHVO').bootstrapToggle('enable');
		$('#CVCtoggleFVO').bootstrapToggle('enable');
		$('#CVCtoggleSVO').bootstrapToggle('enable');
	}
}

/**************************VIEW OFFERINGS**********************************/
/**************************FACULTY ASSIGNMENT******************************/
function clearClickedOfferingAndFacultyList(){
	clickedOfferingFA = "";
	$("#tableModalFAFacultyList tbody tr").remove();
}

function clearAndRecommendFacultyList(){

	if(clickedOfferingFA != ""){
		$("#tableModalFAFacultyList tbody tr").remove();
		initiateFARecommendedFacultyList($('#assignFAFacultyOfferingIDDump').val());
	}
}

function initFacultyAssignmentModal(id){
	var arr = [];
	
	arr = id.split('-');
	
	/****************DISABLE THE FILTERS FIRST HANGGAT WALANG NACLICK NA COURSE*********************/
	$('#recommendFacultyButton').prop('disabled', true); 
	$('#facultyTypeFADropdown').prop('disabled', true);
	$('#collegeFADropdown').prop('disabled', true);
	$('#departmentFADropdown').prop('disabled', true);
	$('#searchFacultyFA').prop('disabled', true);
	/****************DISABLE THE FILTERS FIRST HANGGAT WALANG NACLICK NA COURSE*********************/
	
	var searchKeyword = $('#divFASearchFaculty').val();
	var selectedFacultyType = $('#divFAFacultyTypeDropdown :selected').text();
	
	var concatId = arr[0] +"-"+ arr[1] + "-" + arr[2];
	//console.log(id);
	$('#facultyAssignmentCurrentAYDump').val(concatId); //for search Listeners cause di nila alam ung ID
	$('#facultyAssignmentModalTitle').text("View A.Y " +arr[0]+ "-" + arr[1] + " Term " + arr[2] + " Offerings"); //change modal title
	
	globalStartYear = arr[0];
	globalEndYear = arr[1];
	globalTerm = arr[2];
	
	
	updateFacultyAssignmentOfferingList(id);
}

function updateFacultyAssignmentOfferingList(id){
	console.log("in update");
	var arr = [], daysList = [];  
	
	arr = id.split('-');
	
	if(isPure === 0 ){
		showLoadingModal();
	}
	
	var searchKeyword = $('#searchCourseFA').val();
	var selectedFacultyType = $('#divFAFacultyTypeDropdown :selected').text();
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getAllOfferingsWithoutFiltersCVC',
        data: {
        	"searchKeyword": searchKeyword,
        	"startYear": globalStartYear,
        	"endYear": globalEndYear,
        	"term": globalTerm
        },
        success: function (data) {
        	hideLoadingModal();
        	disableClassDayToggles(true);
        	$("#tableModalFAOfferingList tbody tr").remove(); //removes all tr but not thead

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
        		var facultyId = currObject.faculty.facultyId;
        		
        		if(clickedOfferingFA.length != 0){ //meron nanag naclick na Offering
        			/****************ENABLE THE FILTERS SINCE MAY NACLICK NA COURSE*********************/
        			$('#recommendFacultyButton').prop('disabled', false);
        			$('#facultyTypeFADropdown').prop('disabled', false);
        			$('#collegeFADropdown').prop('disabled', false);
        			$('#departmentFADropdown').prop('disabled', false);
//        			$('#searchFacultyFA').prop('disabled', false);
        			/****************ENABLE THE FILTERS SINCE MAY NACLICK NA COURSE*********************/
        		}
        		
    			if(offeringId === clickedOfferingFA){
    				rows += "<tr id=\"" +offeringId+"-"+facultyId+ "-id\" class=\"clicked-offering-active offeringRow "+id+"-id\">";
    			}else{
    				rows += "<tr id=\"" +offeringId+"-"+facultyId+ "-id\" onclick=\"checkIfFacultyAssignmentPossible(this.id)\" class=\"clicked-offering-inactive offeringRow "+id+"-id\">";
    			}
    			
				rows += "<td>"+degreeProgram+"</td>"+
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
						"<td>"+status+"</td>"+
						"<td>"+remarks+"</td>";
						//console.log(currObject.faculty.user.userId);
						if(currObject.faculty.userId != "11111111"){
							rows+= "<td>" + facultyName +"<br><a class=\"btn btn-default unassign-button\" type=\"button\" onclick=\"unassignThisFacultyToCourse(this.id)\" id=\""+offeringId+"-"+facultyId+"-facultyassign\"><span><i class=\"fa fa-times-circle\"></i></span></a></td>";
						}else{
							rows+="<td><a class=\"btn btn-default assign-button\" type=\"button\" id=\""+offeringId+"-"+facultyId+"-facultyassign\"><span><i class=\"fa fa-plus-circle\"></i></span></a></td>"
						}
						
						"<tr>";
	    		//console.log(rows);
	    		$(rows).appendTo("#tableModalFAOfferingList tbody");
        		
        	});
        },
    	error: function (data){
    		hideLoadingModal();
    		console.log(data);
    	}
    });
}

function checkIfFacultyAssignmentPossible(offeringId){
	if(isPure === 0 ){
		showLoadingModal();
	}
	console.log("check if possible");
	//isPure = 0;
	//offering must have a schedule otherwise can't room assign
	var indexDayCol = 4;
	var indexBeginTimeCol = 5;
	var indexEndTimeCol = 6;
	
	var dayColVal = "";
	var beginTimeColVal = "";
	var endTimeColVal = "";
	
	dayColVal = $("#" + offeringId).find("td").eq(indexDayCol).text();
	beginTimeColVal = $("#" + offeringId).find("td").eq(indexBeginTimeCol).text();
	endTimeColVal = $("#" + offeringId).find("td").eq(indexEndTimeCol).text();
	
	console.log("Pasok Men! " + offeringId + ":" + dayColVal + "|" + beginTimeColVal + "-" + endTimeColVal);
	
	if(dayColVal !== "" && (beginTimeColVal !== "" && beginTimeColVal !== "0") && (endTimeColVal !== "" && endTimeColVal !== "0")){
//		Implies selected offering has schedule
		console.log("Pasok Men! " + dayColVal + "|" + beginTimeColVal + "-" + endTimeColVal);
		initiateFARecommendedFacultyList(offeringId);
		$('#recommendFacultyButton').prop('disabled', false);
		$('#facultyTypeFADropdown').prop('disabled', false);
		$('#collegeFADropdown').prop('disabled', false);
		$('#departmentFADropdown').prop('disabled', false);
		$('#searchFacultyFA').prop('disabled', false);
	} else {
		$('#errorNoScheduleModal').modal('show');
		$('#errorNoScheduleModal').removeData('modal').modal({
			keyboard: false,
			backdrop: 'static',
			toggle: 'modal',
			target: '#errorNoScheduleModal'
		});
	}
	
//	console.log("td: " + $("#" + offeringId).find("td").eq(indexDayCol).text());
	
//	var arr = [];
//	arr = offeringId.split('-'); //arr[0] would be courseID, [1], appender, [2] extraneous text
//	clickedOfferingFA = arr[0];
//	
//	var hasSchedule = 0;
//	$.ajax({
//        type: 'GET',
//        dataType: 'json',
//        cache: false,
//        url: 'getOfferingWithScheduleCVC',
//        data: {
//        	"offeringId": arr[0]
//        },
//        success: function (data) {
//        	//console.log(data.days.length);
//        	if(data.days.length != 0){
//        		hasSchedule = 1; //meaning may sched
//        	}else hasSchedule = 0;
//        
//        	if(hasSchedule === 1){
//        		//$("#tableModalFAFacultyList tbody tr").remove();
//        		initiateFARecommendedFacultyList(offeringId);
//        	}else{ //if hasSchedule === 0
//        		$('#errorNoScheduleModal').modal('show');
//        		$('#errorNoScheduleModal').removeData('modal').modal({
//        			keyboard: false,
//        			backdrop: 'static',
//        			toggle: 'modal',
//        			target: '#errorNoScheduleModal'
//        		});
//        	}
//        },
//        error: function (data){
//            console.log(data);
//        }
//    });
}

function initiateFARecommendedFacultyList(tempOfferingId){
	console.log("initiate...");
	var arr = [];
	arr = tempOfferingId.split('-'); //arr[0] would be courseID, [1], appender, [2] extraneous text
	var id = arr[0] +"-"+ arr[1];
	
	populateFacultyAssignmentModalDropdown(id, "All", "All", "All");
	$("#tableModalFAFacultyList tbody tr").remove(); //removes all tr but not thead
	
	updateFARecommendedFacultyList(tempOfferingId);
}

function updateFARecommendedFacultyList(tempOfferingId){
	console.log("update");
	tempFacultys=[]; //clear temp array of faculty
	
	var arr = [];
	arr = tempOfferingId.split('-'); //arr[0] would be offeringID, [1], appender, [2] extraneous text
	var id = arr[0] +"-"+ arr[1];
	var concatId = globalStartYear + "-" + globalEndYear + "-" + globalTerm + "-" + globalBatch;
	
	$('#assignFAFacultyCurrentAYDump').val(concatId);
	$('#assignFAFacultyOfferingIDDump').val(tempOfferingId); //for search Listeners cause di nila alam ung ID
	$('#assignFAFacultyFacultyIDDump').val(tempOfferingId); //for search Listeners cause di nila alam ung ID
	updateFacultyAssignmentOfferingList(tempOfferingId); // to change the color of the row selected
	
	var selectedFacultyType = "All";
	var selectedCollege = "All";
	var selectedDepartment = "All";
	var searchFaculty = "";
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        url: 'getRecommendedFacultyListWithKeyCVC',
        cache: false,
        async: false,
        data: {
        	"offeringId": arr[0],
			"facultyType": selectedFacultyType,
			"department": selectedDepartment,
			"college": selectedCollege,
			"searchKeyword": searchFaculty,
			"startYear": globalStartYear,
			"endYear": globalEndYear,
			"term": globalTerm
        },
        success: function (data) {
    		hideLoadingModal();
        	$("#tableModalFAFacultyList tbody tr").remove(); //removes all tr but not thead

        	$.each(data, function(i, currObject){
        		var rows = "";
        		var id = currObject.user.userId;
        		var facultyId = currObject.facultyId;
        		var name = currObject.user.lastName + ", " + currObject.user.firstName;
        		var facultyType = currObject.facultyType;
        		var currentLoad = currObject.load.totalLoad;
        		if(!currentLoad) currentLoad = 0;
        		var adminLoad = currObject.load.adminLoad;
        		if(!adminLoad) adminLoad = 0;
        		var researchLoad = currObject.load.researchLoad;
        		if(!researchLoad) researchLoad = 0;
        		var teachingLoad = currObject.load.teachingLoad;
        		if(!teachingLoad) teachingLoad = 0;
        		var preparations = currObject.load.preparations;
        		if(!preparations) preparations = 0;

        	
        		/*******************CUSTOMIZE TEXT******************/
        		rows += "<tr id=\"" + facultyId + "-id\">";
        		
					rows += "<td>"+name+"</td>";
					/*facultyId+clickedOfferingFA+*/
					rows+=  "<td>"+facultyType+"</td>";
					
					if((facultyType === "FT" && currentLoad >= 15) || (facultyType === "PT" && currentLoad >= 15) || (facultyType === "HT" && currentLoad >= 15)){
						rows+= "<td><strong><p class=\"broken-rule-remark\">"+currentLoad+" (MAX)<p></strong></td>";
					}else if((facultyType === "FT" && currentLoad > 12) || (facultyType === "PT" && currentLoad > 9) || (facultyType === "HT" && currentLoad > 6)){
						rows+= "<td><strong><p class=\"broken-rule-remark\">"+currentLoad+" (OVERLOAD)<p></strong></td>";
					}else rows+= "<td>"+currentLoad+"</td>";
					
					rows+= "<td>"+adminLoad+"</td>"+
							"<td>"+researchLoad+"</td>"+
							"<td>"+teachingLoad+"</td>";
					
					if(preparations == 3){
						rows+= "<td><strong><p class=\"exact-rule-remark\">"+preparations+"<p></strong></td>";
					}else if(preparations >= 4){
						rows+= "<td><strong><p class=\"broken-rule-remark\">"+preparations+" (MAX)<p></strong></td>";
					}else rows+= "<td>"+preparations+"</td>";
				/*******************CUSTOMIZE TEXT******************/
					
					rows+=  "<td><a class=\"btn btn-default faculty-assign-button\" type=\"button\" onclick=\"assignThisFacultyToCourse(this.id)\" id=\"" + facultyId + "-assign\"><span><i class=\"fa fa-star\"></i></span></a></td>";
					
					rows+= "</tr>";

				$(rows).appendTo("#tableModalFAFacultyList tbody");
	    		
	    		var tempFac = TempFaculty(facultyId, currObject.user.userId, currObject.user.lastName, currObject.user.firstName, "Recommended");
	    		tempFacultys[facultyAppenderID] = tempFac;
	    		facultyAppenderID++;;
	    		
	    		//}
        	});
        	
        },
    	error: function (data){
    		hideLoadingModal();
    		console.log(data);
    	}
    });
	
	/****************************MAGPASOK NG NORMAL FACULTY BASED SA DEPARTMENT AT COLLEGE NG CLICKED OFFERING**************************/
	$.ajax({
        type: 'GET',
        dataType: 'json',
        url: 'getNormalFacultyListWithKeyCVC',
        cache: false,
        async: false,
        data: {
        	"offeringId": arr[0],
			"startYear": globalStartYear,
			"endYear": globalEndYear,
			"term": globalTerm
        },
        success: function (data) {
        	hideLoadingModal();
        	$.each(data, function(j, currObject){
        		var rows = "";
        		var id = currObject.user.userId;
        		var facultyId = currObject.facultyId;
        		var name = currObject.user.lastName + ", " + currObject.user.firstName;
        		var facultyType = currObject.facultyType;
        		var currentLoad = currObject.load.totalLoad;
        		if(!currentLoad) currentLoad = 0;
        		var adminLoad = currObject.load.adminLoad;
        		if(!adminLoad) adminLoad = 0;
        		var researchLoad = currObject.load.researchLoad;
        		if(!researchLoad) researchLoad = 0;
        		var teachingLoad = currObject.load.teachingLoad;
        		if(!teachingLoad) teachingLoad = 0;
        		var preparations = currObject.load.preparations;
        		if(!preparations) preparations = 0;

        		//if(roomCode != "No Room" || roomCode != "None"){ rows += "<tr id=\"" + id + "-id\" class=\"clicked-room-inactive roomRow "+id+"-id\">";
        		/*******************CUSTOMIZE TEXT******************/
	        		rows += "<tr id=\"" + facultyId + "-id\">";
					rows += "<td>"+name+clickedOfferingFA+"</td>";
	    			
					rows+=  "<td>"+facultyType+"</td>";
					
					if((facultyType === "FT" && currentLoad > 12) || (facultyType === "PT" && currentLoad > 9) || (facultyType === "HT" && currentLoad > 6)){
						rows+= "<td><strong><p class=\"broken-rule-remark\">"+currentLoad+" (OVERLOAD)<p></strong></td>";
					}else rows+= "<td>"+currentLoad+"</td>";
					
					rows+= "<td>"+adminLoad+"</td>"+
							"<td>"+researchLoad+"</td>"+
							"<td>"+teachingLoad+"</td>";
					
					if(preparations == 3){
						rows+= "<td><strong><p class=\"exact-rule-remark\">"+preparations+"<p></strong></td>";
					}else if(preparations >= 4){
						rows+= "<td><strong><p class=\"broken-rule-remark\">"+preparations+" (MAX)<p></strong></td>";
					}else rows+= "<td>"+preparations+"</td>";
				/*******************CUSTOMIZE TEXT******************/
					rows+= "<td><a class=\"btn btn-default\" type=\"button\" onclick=\"assignThisFacultyToCourse(this.id)\" id=\"" + facultyId + "-assign\"><span><i class=\"fa fa-star\"></i></span></a>" + "</td>"+
						"</tr>";
	    		//console.log(rows);
	    		$(rows).appendTo("#tableModalFAFacultyList tbody");
	    		
	    		var tempFac = TempFaculty(facultyId, currObject.user.userId, currObject.user.lastName, currObject.user.firstName, "Normal");
	    		tempFacultys[facultyAppenderID] = tempFac;
	    		facultyAppenderID++;;
	    		
        	});
        	
        },
    	error: function (data){
    		hideLoadingModal();
    		console.log(data);
    	}
    });
	/****************************MAGPASOK NG NORMAL FACULTY BASED SA DEPARTMENT AT COLLEGE NG CLICKED OFFERING**************************/
}

function updateFAFacultyList(tempOfferingId){ //FOR NORMAL SEARCHERS. WALANG RECOMMENDATIONS ALGORITHM
	tempFacultys=[]; //clear temp array of faculty
	
	var arr = [];
	arr = tempOfferingId.split('-'); //arr[0] would be courseID, [1], appender, [2] extraneous text
	var id = arr[0] +"-"+ arr[1];
	var concatId = globalStartYear + "-" + globalEndYear + "-" + globalTerm + "-" + globalBatch;
	
	$('#assignFAFacultyCurrentAYDump').val(concatId);
	$('#assignFAFacultyOfferingIDDump').val(tempOfferingId); //for search Listeners cause di nila alam ung ID
	$('#assignFAFacultyFacultyIDDump').val(tempOfferingId); //for search Listeners cause di nila alam ung ID
	updateFacultyAssignmentOfferingList(tempOfferingId); // to change the color of the row selected
	
	var selectedFacultyType = $('#facultyTypeFADropdown :selected').text();
	var selectedCollege = $('#collegeFADropdown :selected').text();
	var selectedDepartment = $('#departmentFADropdown :selected').text();
	var searchFaculty = $('#searchFacultyFA').val();
	
	populateFacultyAssignmentModalDropdown(id, selectedFacultyType, selectedCollege, selectedDepartment);
	
	console.log(selectedFacultyType +"--"+selectedCollege + "--" + selectedDepartment + "--" + searchFaculty);
	$.ajax({
        type: 'GET',
        dataType: 'json',
        url: 'getFacultyListWithKeyCVC',
        cache: false,
        data: {
        	"offeringId": arr[0],
			"facultyType": selectedFacultyType,
			"department": selectedDepartment,
			"college": selectedCollege,
			"searchKeyword": searchFaculty,
			"startYear": globalStartYear,
			"endYear": globalEndYear,
			"term": globalTerm
        },
        success: function (data) {

        	$("#tableModalFAFacultyList tbody tr").remove(); //removes all tr but not thead

        	$.each(data, function(i, currObject){
        		var rows = "";
        		var id = currObject.user.userId;
        		var facultyId = currObject.facultyId;
        		var name = currObject.user.lastName + ", " + currObject.user.firstName;
        		var facultyType = currObject.facultyType;
        		var currentLoad = currObject.load.totalLoad;
        		if(!currentLoad) currentLoad = 0;
        		var adminLoad = currObject.load.adminLoad;
        		if(!adminLoad) adminLoad = 0;
        		var researchLoad = currObject.load.researchLoad;
        		if(!researchLoad) researchLoad = 0;
        		var teachingLoad = currObject.load.teachingLoad;
        		if(!teachingLoad) teachingLoad = 0;
        		var preparations = currObject.load.preparations;
        		if(!preparations) preparations = 0;

        		//if(roomCode != "No Room" || roomCode != "None"){ rows += "<tr id=\"" + id + "-id\" class=\"clicked-room-inactive roomRow "+id+"-id\">";
        		/*******************CUSTOMIZE TEXT******************/
	        		rows += "<tr id=\"" + facultyId + "-id\">";
					rows += "<td>"+name+"</td>";
	    			
					rows+=  "<td>"+facultyType+"</td>";
					
					if((facultyType === "FT" && currentLoad > 12) || (facultyType === "PT" && currentLoad > 9) || (facultyType === "HT" && currentLoad > 6)){
						rows+= "<td><strong><p class=\"broken-rule-remark\">"+currentLoad+" (OVERLOAD)<p></strong></td>";
					}else rows+= "<td>"+currentLoad+"</td>";
					
					rows+= "<td>"+adminLoad+"</td>"+
							"<td>"+researchLoad+"</td>"+
							"<td>"+teachingLoad+"</td>";
					
					if(preparations == 3){
						rows+= "<td><strong><p class=\"exact-rule-remark\">"+preparations+"<p></strong></td>";
					}else if(preparations >= 4){
						rows+= "<td><strong><p class=\"broken-rule-remark\">"+preparations+" (MAX)<p></strong></td>";
					}else rows+= "<td>"+preparations+"</td>";
				/*******************CUSTOMIZE TEXT******************/
					rows+= "<td><a class=\"btn btn-default\" type=\"button\" onclick=\"assignThisFacultyToCourse(this.id)\" id=\"" + facultyId + "-assign\"><span><i class=\"fa fa-star\"></i></span></a></td>"+
						"</tr>";
	    		//console.log(rows);
	    		$(rows).appendTo("#tableModalFAFacultyList tbody");
	    		
	    		var tempFac = TempFaculty(facultyId, currObject.user.userId, currObject.user.lastName, currObject.user.firstName, "Recommended");
	    		tempFacultys[facultyAppenderID] = tempFac;
	    		facultyAppenderID++;;
	    		
	    		//}
        	});
        	
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

function assignThisFacultyToCourse(id){
	var arr = [], arr2 = [];
	
	arr = id.split('-');
	var facultyId = arr[0]; //facultyId
	
	var concatOfferingId = $('#assignFAFacultyOfferingIDDump').val();
	arr2 = concatOfferingId.split('-');
	var offeringId = arr2[0]; //clicked OfferingID
	
	$.ajax({
        type: 'POST',
        dataType: 'json',
        cache: false,
        url: 'initiateFacultyAssignmentCVC',
        data: {
        	"offeringId": offeringId,
			"facultyId": facultyId,
			"startYear": globalStartYear,
			"endYear": globalEndYear,
			"term": globalTerm
        },
        success: function (data) {
        	console.log(data);
        	if(data.response === "SUCCESS"){
	        	initFacultyAssignmentModal($('#assignFAFacultyCurrentAYDump').val());
	        	initiateFARecommendedFacultyList(concatOfferingId);
        	}else if(data.response === "OFFERING-COLLISION"){
        		$('#errorRuleBrokenModal').modal('show');
        		$('#errorRuleBrokenModalTitle').text('Schedule Collision');
        		$('#errorRuleBrokenMessage').text("Assigning this course to " + data.firstName + " " + data.lastName + " will collide with one of the " + data.firstName + "'s schedule. Therefore, this assignment will not be carried out.");
        		$('#errorRuleBrokenModal').removeData('modal').modal({
        			keyboard: false,
        			backdrop: 'static',
        			toggle: 'modal',
        			target: '#errorRuleBrokenModal'
        		});
        	}else if(data.response === "6-HOUR-THREAT"){
        		$('#errorRuleBrokenModal').modal('show');
        		$('#errorRuleBrokenModalTitle').text('6-Hour Rule');
        		$('#errorRuleBrokenMessage').text("Assigning this course to " + data.firstName + " " + data.lastName + " will violate the maximum 6 hours per day rule of the university. Therefore, this assignment will not be carried out.");
        		$('#errorRuleBrokenModal').removeData('modal').modal({
        			keyboard: false,
        			backdrop: 'static',
        			toggle: 'modal',
        			target: '#errorRuleBrokenModal'
        		});
        	}else if(data.response === "CONSECUTIVE-HOUR-THREAT"){
        		$('#errorRuleBrokenModal').modal('show');
        		$('#errorRuleBrokenModalTitle').text('4.5 Consecutive Hour Rule');
        		$('#errorRuleBrokenMessage').text("Assigning this course to " + data.firstName + " " + data.lastName + " will violate the maximum consecutive hours(4.5) per day rule of the university. Therefore, this assignment will not be carried out.");
        		$('#errorRuleBrokenModal').removeData('modal').modal({
        			keyboard: false,
        			backdrop: 'static',
        			toggle: 'modal',
        			target: '#errorRuleBrokenModal'
        		});
        	}else if(data.response === "MAX-PREPARATION"){
        		$('#errorRuleBrokenModal').modal('show');
        		$('#errorRuleBrokenModalTitle').text('Maximum Preparations');
        		$('#errorRuleBrokenMessage').text(data.firstName + " " + data.lastName + " has already reached the maximum allowed preparations.");
        		$('#errorRuleBrokenModal').removeData('modal').modal({
        			keyboard: false,
        			backdrop: 'static',
        			toggle: 'modal',
        			target: '#errorRuleBrokenModal'
        		});
        	}else if(data.response === "ALREADY-OVERLOAD"){
        		$('#errorRuleBrokenModal').modal('show');
        		$('#errorRuleBrokenModalTitle').text('Already Overload');
        		$('#errorRuleBrokenMessage').text(data.firstName + " " + data.lastName + " has already reached the maximum allowed loads for a " + data.facultyType + " faculty.");
        		$('#errorRuleBrokenModal').removeData('modal').modal({
        			keyboard: false,
        			backdrop: 'static',
        			toggle: 'modal',
        			target: '#confirmRuleBrokenModal'
        		});
        	}else if(data.response === "OVERLOAD-THREAT"){
        		initFacultyAssignmentModal($('#assignFAFacultyCurrentAYDump').val());
	        	initiateFARecommendedFacultyList(concatOfferingId);
        		$('#errorRuleBrokenModal').modal('show');
        		$('#errorRuleBrokenModalTitle').text('Overload Threat');
        		$('#errorRuleBrokenMessage').text(data.firstName + " " + data.lastName + " has already reached the maximum allowed loads for a " + data.facultyType + " faculty.");
        		$('#errorRuleBrokenModal').removeData('modal').modal({
        			keyboard: false,
        			backdrop: 'static',
        			toggle: 'modal',
        			target: '#errorRuleBrokenModal'
        		});
        	}else if(data.response === "MAX-LOAD-REACHED"){
        		$('#errorRuleBrokenModal').modal('show');
        		$('#errorRuleBrokenModalTitle').text('Maximum Loads Reached');
        		$('#errorRuleBrokenMessage').text(data.firstName + " " + data.lastName + " has already reached the maximum allowed loads. You cannot assign anymore.");
        		$('#errorRuleBrokenModal').removeData('modal').modal({
        			keyboard: false,
        			backdrop: 'static',
        			toggle: 'modal',
        			target: '#errorRuleBrokenModal'
        		});
        	}else{
        		initFacultyAssignmentModal($('#assignFAFacultyCurrentAYDump').val());
	        	initiateFARecommendedFacultyList(concatOfferingId);
        	}
        		
        	clickOffering($('#assignFAOfferingIDDump').val());
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

function unassignThisFacultyToCourse(id){
	var arr = [];
	
	arr = id.split('-'); //<offeringId-facultyId-<extraneous string>
	var facultyId = arr[1]; //facultyId
	
	var concatOfferingId = $('#assignFAFacultyOfferingIDDump').val();
	//arr2 = concatOfferingId.split('-');
	var offeringId = arr[0]; //clicked OfferingID
	//console.log(offeringId +"---"+ facultyId);
	$.ajax({
        type: 'POST',
        dataType: 'json',
        cache: false,
        url: 'removeLoadFromFacultyCVC',
        data: {
        	"offeringId": offeringId,
			"facultyId": facultyId,
			"startYear": globalStartYear,
			"endYear": globalEndYear,
			"term": globalTerm
        },
        success: function (data) {
        	//initFacultyAssignmentModal($('#assignFAFacultyCurrentAYDump').val());
        	initiateFARecommendedFacultyList(concatOfferingId);
        	clickOffering($('#assignFAOfferingIDDump').val());
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

function populateFacultyAssignmentModalDropdown(id, selectedFacultyType, selectedCollege, selectedDepartment){

	//populate Faculty Type
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateFacultyTypeDropdownCVC',
        success: function (data) {
        	$("#facultyTypeFADropdown").empty(); //removes all options
        	$("#facultyTypeFADropdown").removeAttr("onChange");
        	$("#facultyTypeFADropdown").attr("onChange", "return updateFAFacultyList(\""+id+"-type\")");
        	
        	if(selectedFacultyType === "All"){
        		$("#facultyTypeFADropdown").prepend("<option selected onclick=\"updateFAFacultyList(this.id)\" id=\""+id+"-type"+"\">All</option>");
        	}else{
        		$("#facultyTypeFADropdown").prepend("<option onclick=\"updateFAFacultyList(this.id)\" id=\""+id+"-type"+"\">All</option>");
        	}
        	
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedFacultyType === currOption){
        			var newoption = "<option selected onclick=\"updateFAFacultyList(this.id)\" id=\""+id+"-type"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"updateFAFacultyList(this.id)\" id=\""+id+"-type"+"\">" + currOption + "</option>";
            	}
        		
        		$("#facultyTypeFADropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	
	//populate College
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateCollegeDropdownCVC',
        success: function (data) {
        	$("#collegeFADropdown").empty(); //removes all options
        	$("#collegeFADropdown").removeAttr("onChange");
        	$("#collegeFADropdown").attr("onChange", "return updateFAFacultyList(\""+id+"-type\")");
        	
        	if(selectedCollege === "All"){
        		$("#collegeFADropdown").prepend("<option selected onclick=\"updateFAFacultyList(this.id)\" id=\""+id+"-college"+"\">All</option>");
        	}else{
        		$("#collegeFADropdown").prepend("<option onclick=\"updateFAFacultyList(this.id)\" id=\""+id+"-college"+"\">All</option>");
        	}
        	
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedCollege === currOption){
        			var newoption = "<option selected onclick=\"updateFAFacultyList(this.id)\" id=\""+id+"-college"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"updateFAFacultyList(this.id)\" id=\""+id+"-college"+"\">" + currOption + "</option>";
            	}
        		
        		$("#collegeFADropdown").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });

	//populate Department
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'populateDepartmentDropdownCVC',
        data:{
        	"selectedCollege": selectedCollege
        },
        success: function (data) {
        	$("#departmentFADropdown").empty(); //removes all options
        	$("#departmentFADropdown").removeAttr("onChange");
        	$("#departmentFADropdown").attr("onChange", "return updateFAFacultyList(\""+id+"-type\")");
        	
        	if(selectedDepartment === "All"){
        		$("#departmentFADropdown").prepend("<option selected onclick=\"updateFAFacultyList(this.id)\" id=\""+id+"-college"+"\">All</option>");
        	}else{
        		$("#departmentFADropdown").prepend("<option onclick=\"updateFAFacultyList(this.id)\" id=\""+id+"-college"+"\">All</option>");
        	}
        	
        	$.each(data, function(i, currOption){
        		var rows = "";
        		
        		if(selectedDepartment === currOption){
        			var newoption = "<option selected onclick=\"updateFAFacultyList(this.id)\" id=\""+id+"-college"+"\">" + currOption + "</option>";
            	}else{
            		var newoption = "<option onclick=\"updateFAFacultyList(this.id)\" id=\""+id+"-college"+"\">" + currOption + "</option>";
            	}
        		
        		$("#departmentFADropdown").append(newoption);
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
	///$('#buildingRADropdown').attr('disabled', false);
	///$('#roomTypeRADropdown').attr('disabled', false);
	///$('#searchRoomRA').prop('disabled', false);
	
	//set hidden input form for later use/for assigning
	///$('#assignRoomOfferingIDDump').val(id);
	
	///updateRoomList(id);
}

function exitFacultyAssignmentModal(){
	$("#searchFacultyFA").val("");
	
	$("#tableModalFAFacultyList tbody tr").remove();
	$("#tableModalFAOfferingList tbody tr").remove();
	
	clickedOfferingFA = "";
	
	if(isPure === 1){
		isPure = 0;
	}
}
/**************************FACULTY ASSIGNMENT******************************/
/**************************VIEW FACULTY LOAD INFO*************************/
function clickViewFacultyCVC(id){
	clickSideBar(id);
	makeActivePanel("#panelViewFacultyCVC");
	getViewFacultyAcademicYears();
}

function getViewFacultyAcademicYears(){
	populateAYDropdownViewFaculty();
}

function populateAYDropdownViewFaculty(){
	var collegeID = $('#collegeIDDump').val();
	var deptID = $('#deptIDDump').val();
	var selectedAY = $('#loadAYDropdownCVC :selected').text();
	//console.log(selectedAY);
	
	//$("#loadAYDropDownCVC").empty();
    //$("#loadAYDropDownCVC").append("<option selected> Select Term </option>");
    
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getAllAcademicYearsCVC',
        success: function (data) {
        	//console.log(data);
        	$("#loadAYDropdownCVC").empty(); //removes all options
        	
        	if(selectedAY === "All"){
        		$("#loadAYDropdownCVC").prepend("<option selected onclick=\"clearVFAFacultyList(this.id)\" id=\"0-0-0-college"+"\">All</option>");
        	}else{
        		$("#loadAYDropdownCVC").prepend("<option onclick=\"clearVFAFacultyList(this.id)\" id=\"0-0-0-college"+"\">All</option>");
        	}
        	
        	$.each(data, function(i, currObject){
        		$("#loadAYDropdownCVC").removeAttr("onChange");
            	$("#loadAYDropdownCVC").attr("onChange", "return updateVFAFacultyList()");
            	
        		var rows = "";
        		var id = currObject.id;
        		var AY = "A.Y " + currObject.startYear +"-"+ currObject.endYear + " Term "+ currObject.term;
        		var term = currObject.term;
        		var isPublished = currObject.isPublished;
        		var unPublishedCount = currObject.unPublishedCount;
        		var concatId = currObject.startYear +"-"+ currObject.endYear + "-" + currObject.term;
        		
        		//$("#loadAYDropdownCVC").removeAttr("onChange");
            	//$("#loadAYDropdownCVC").attr("onChange", "return updateVFAFacultyList()");
            	
        		if(selectedAY === AY){
        			//console.log("entered" + AY);
        			var newoption = "<option selected id=\""+concatId+"-college"+"\">" + AY + "</option>";
            	}else{
            		//console.log("HEY" + AY);
            		var newoption = "<option id=\""+concatId+"-college"+"\">" + AY + "</option>";
            	}
        		
        		$("#loadAYDropdownCVC").append(newoption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	fixForParallax();
}

function clearVFAFacultyList(id){
	$("#tableViewOfferingsCVC tbody tr").remove();
}

function setGlobalAY(id){
	var arr = [];
	arr = id.split("-"); // 0-startYear, 1-endYear, 2-term, 3-extra string
	
	globalStartYear = arr[0];
	globalEndYear = arr[1];
	globalTerm = arr[2];
}

function updateVFAFacultyList(){
	
	var id = $('#loadAYDropdownCVC :selected').attr("id");
	var arr = [];
	arr = id.split("-"); // 0-startYear, 1-endYear, 2-term, 3-extra string*/
	var collegeId = $('#collegeIDDump').val();
	var deptId = $('#deptIDDump').val();

	globalStartYear = arr[0];
	globalEndYear = arr[1];
	globalTerm = arr[2];
	//console.log(globalStartYear + "="+globalEndYear + "=" + globalTerm);
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getAllFacultyLoadWithFilters',
        data:{
        	"startYear": globalStartYear,
        	"endYear": globalEndYear,
        	"term": globalTerm,
        	"collegeId": collegeId,
        	"deptId": deptId
        },
        success: function (data) {
        	var headers = "";
        	//console.log(data);
        	$("#tableViewFacultyCVC tbody tr").remove(); //removes all tr but not thead
        	
        	$.each(data, function(i, currObject){
        		var rows = "";
        		var facultyId = currObject.facultyId;
        		var name = currObject.lastName +", "+ currObject.firstName;
        		var totalLoad = currObject.load.totalLoad;
        		var facultyType = currObject.facultyType;
        		var deloading = currObject.load.deloading;
        		var isOnLeave = currObject.load.isOnLeave;
        		var leaveType = currObject.load.leaveType;
        		var concatId = globalStartYear +"-"+ globalEndYear + "-" + globalTerm + "-" + facultyId;
        		
        		rows += "<tr id=\"" + facultyId + "-id\" class=\"AYRow\">"+
        				"<td>"+name+"</td>";
		        		/*"<td>"+totalLoad+"</td>"+
		        		"<td>"+deloading+"</td>"+
		        		"<td>"+facultyType+"</td>";*/
        		
        		if(facultyType === "FT"){
        			if(totalLoad == 12) rows += "<td><p class=\"orange-highlight\"><strong>"+totalLoad+" (MAX)</strong></p></td>";
        			else if(totalLoad > 12) rows += "<td><p class=\"red-highlight\"><strong>"+totalLoad+" (OVERLOAD)</strong></p></td>";
        			else rows += "<td>"+ totalLoad +"</td>";
        		}else if(facultyType === "PT"){
        			if(totalLoad == 9) rows += "<td><p class=\"orange-highlight\"><strong>"+totalLoad+" (MAX)</strong></p></td>";
        			else if(totalLoad > 9) rows += "<td><p class=\"red-highlight\"><strong>"+totalLoad+" (OVERLOAD)</strong></p></td>";
        			else rows += "<td>"+ totalLoad +"</td>";
        		}else if(facultyType === "HT"){
        			if(totalLoad == 6) rows += "<td><p class=\"orange-highlight\"><strong>"+totalLoad+" (MAX)</strong></p></td>";
        			else if(totalLoad > 6) rows += "<td><p class=\"red-highlight\"><strong>"+totalLoad+" (OVERLOAD)</strong></p></td>";
        			else rows += "<td>"+ totalLoad +"</td>";
        		}else rows += "<td>"+ totalLoad +"</td>";
        		
        		rows += "<td>"+ deloading +"</td>";
        		
	    		if(isOnLeave === "0"){					
					rows += "<td><a class=\"btn btn-default\" type=\"button\" onclick=\"viewLoadInfoInTableModalCVC(this.id)\" data-toggle=\"modal\" data-target=\"#viewFacultyLoadTableModal\" data-backdrop=\"static\" data-keyboard=\"false\" id=\"" + concatId + "-view\"><span><i class=\"fa fa-search\"></i></span></a> "+
					"<a class=\"btn btn-default\" type=\"button\" onclick=\"viewLoadInfoInGraphModalCVC(this.id)\" data-toggle=\"modal\" data-target=\"#viewFacultyLoadGraphModal\" data-backdrop=\"static\" data-keyboard=\"false\" id=\"" + concatId + "-view\"><span><i class=\"fa fa-table\"></i></span></a> ";
	    		}else{
	    			if(leaveType === "") rows += "<td><p class=\"red-highlight\"><strong> LEAVE </strong></p></td>";
	    			else rows += "<td><p class=\"red-highlight\"><strong>" + leaveType + "</strong></p></td>";
	    		}
				//data backdrop static prevents exit from background while data-keyboard false prevents exit using keyboard keys
				
				rows+="</tr>";
        		$(rows).appendTo("#tableViewFacultyCVC tbody");
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	fixForParallax();
}

function viewLoadInfoInGraphModalCVC(id){
	//alert(id);
	var arr = [];
	arr = id.split("-"); // 0-startYear, 1-endYear, 2-term, 3-facultyId, 4-extra string*/
	var startYear = arr[0];
	var endYear = arr[1];
	var term = arr[2];
	var facultyId = arr[3];
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        async: false,
        url: 'getAllFacultyLoadByTimeBlock',
        data:{
        	"startYear": startYear,
        	"endYear": endYear,
        	"term": term,
        	"facultyId": facultyId
        },
        success: function (data) {
        	var headers = "";
        	//console.log(data);
        	$("#tableModalViewFacultyLoad tbody tr").remove(); //removes all tr but not thead
        	
        	//console.log(data.length + "length");
        	$.each(data, function(i, currObject){
        		var rows = "";
        		var facultyId = currObject.faculty.facultyId;
        		var name = currObject.faculty.lastName +", "+ currObject.faculty.firstName;
        		var beginTime = currObject.beginTime;
        		var endTime = currObject.endTime;
        		var concatId = currObject.startYear +"-"+ currObject.endYear + "-" + currObject.term + "-" + facultyId;
        		
        		$('#viewFacultyLoadModalTitle').text(name + "'s loads for A.Y " + startYear + "-" + endYear + " Term " + term); //MODAL HEADER
        		
        		rows += "<tr id=\"" + concatId + "-id\" class=\"AYRow\">"+
        				"<td  class=\"time-cell-color\">"+ beginTime + "-" + endTime +"</td>";
        
        		if(currObject.hasMonday === true){ //monday. The reason why hind string ung true is because sa Java class natin, boolean ung data type ng hasMonday
        			rows += "<td class=\"load-cell-color\" align=\"center\"><strong>"+ currObject.monday.offering.course.courseCode + "</strong><br>" + currObject.monday.offering.section +"</td>";
        		}else rows += "<td> </td>";
        			
        		if(currObject.hasTuesday === true){ //tuesday
        			rows += "<td class=\"load-cell-color\" align=\"center\"><strong>"+ currObject.tuesday.offering.course.courseCode + "</strong><br>" + currObject.tuesday.offering.section +"</td>";
        		}else rows += "<td> </td>";
        			
        		if(currObject.hasWednesday === true){ //wednesday
        			rows += "<td class=\"load-cell-color\" align=\"center\"><strong>"+ currObject.wednesday.offering.course.courseCode + "</strong><br>" + currObject.wednesday.offering.section +"</td>";
        		}else rows += "<td> </td>";
        			
        		if(currObject.hasThursday === true){ //thursday
        			rows += "<td class=\"load-cell-color\" align=\"center\"><strong>"+ currObject.thursday.offering.course.courseCode + "</strong><br>" + currObject.thursday.offering.section +"</td>";
        		}else rows += "<td> </td>";
        			
        		if(currObject.hasFriday === true){ //friday
        			rows += "<td class=\"load-cell-color\" align=\"center\"><strong>"+ currObject.friday.offering.course.courseCode + "</strong><br>" + currObject.friday.offering.section +"</td>";
        		}else rows += "<td> </td>";
        			
        		if(currObject.hasSaturday === true){ //saturday
        			rows += "<td class=\"load-cell-color\" align=\"center\"><strong>"+ currObject.saturday.offering.course.courseCode + "</strong><br>" + currObject.saturday.offering.section +"</td>";
        		}else rows += "<td> </td>";
        		
        		rows+="</tr>";
        		$(rows).appendTo("#tableModalViewFacultyLoad tbody");
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	fixForParallax();
}

function viewLoadInfoInTableModalCVC(id){
	//alert(id);
	var arr = [];
	arr = id.split("-"); // 0-startYear, 1-endYear, 2-term, 3-facultyId, 4-extra string*/
	var startYear = arr[0];
	var endYear = arr[1];
	var term = arr[2];
	var facultyId = arr[3];
	globalStartYear = startYear;
	globalEndYear = endYear;
	globalTerm = term;
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getAllLoadsOfFaculty',
        data:{
        	"startYear": startYear,
        	"endYear": endYear,
        	"term": term,
        	"facultyId": facultyId
        },
        success: function (data) {
        	var headers = "";
        	//console.log(data);
        	$("#tableModalViewFacultyLoad2 tbody tr").remove(); //removes all tr but not thead
        	
        	$.each(data, function(i, currObject){
        		var rows = "";
        		var offeringId = currObject.offeringId;
        		var degreeProgram = currObject.degreeProgram;
        		var course = currObject.course.courseCode;
        		var section = currObject.section;
        		var batch = currObject.batch;
        		var status = currObject.status;
        		var remarks = currObject.remarks;
        		var facultyName = currObject.faculty.user.firstName + " " + currObject.faculty.user.lastName;
        		var concatId = currObject.startYear +"-"+ currObject.endYear + "-" + currObject.term + "-" + facultyId;
        		
        		$('#viewFacultyLoad2ModalTitle').text(facultyName + "'s loads for A.Y " + startYear + "-" + endYear + " Term " + term); //MODAL HEADER
        		
    			rows += "<tr id=\"" + concatId + "-id\" class=\"clicked-offering-inactive offeringRow "+id+"-id\">"+
						"<td>"+course+"</td>"+
						"<td>"+section+"</td>";
				
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
						"<td>"+status+"</td>"+
						"<td>"+remarks+"</td>";
						
				rows+= "<td>" +"<a class=\"btn btn-default trash-button\" type=\"button\" onclick=\"confirmUnassignClickedLoad(this.id)\" data-toggle=\"modal\" data-target=\"#unassignClickedLoadModal\" data-backdrop=\"static\" id=\"" + offeringId + "="+ facultyId + "=" + course + "=" + facultyName + "=" + section +"=delete\"><span><i class=\"fa fa-trash\"></i></span></a>" +"</td>";
						
				rows+= "</tr>";
        		$(rows).appendTo("#tableModalViewFacultyLoad2 tbody");
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
	fixForParallax();
}

function confirmUnassignClickedLoad(id){ //delete for view
	var arr = [];
	arr = id.split('='); //may extra word kaya dapat isplit
	//offeringID is arr[0], facultyId is arr[1], course is arr[2], facultyName is arr[3], section is arr[4], extra word is arr[5]
    //console.log(id);
	$('#viewFacultyLoadOfferingIDDump').val(id); //for saving

	$('#unassignClickedLoadModalTitle').text('Unassign ' + arr[2] + ' ' + arr[4]);
	$('#unassignClickedLoadMessage').text('Are you sure you want to unassign ' + arr[2] + ' ' + arr[4] + ' from ' + arr[3] + '?');
}

function unassignClickedLoad(){
	var arr = [];
	var id = $('#viewFacultyLoadOfferingIDDump').val();
	arr = id.split('='); //offeringID is arr[0], facultyId is arr[1], course is arr[2], facultyName is arr[3], section is arr[4], extra word is arr[5]
	 
	$.ajax({
        type: 'POST',
        dataType: 'json',
        async: false,
        url: 'removeLoadFromFacultyCVC',
        data: {
        	"offeringId" : arr[0],
        	"facultyId": arr[1],
        	"startYear": globalStartYear,
			"endYear": globalEndYear,
			"term": globalTerm
        },
        success: function (data) {
        	//console.log(data);
        	var AY = globalStartYear + "-" + globalEndYear + "-" + globalTerm + "-" + arr[1];
        	var concatId = AY+"-view";
        	viewLoadInfoInTableModalCVC(concatId); //update view
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

function updateViewFacultyInfoPanel(){
	
}
/**************************VIEW FACULTY LOAD INFO*************************/




















/**************************ADD NEW DELOAD OFFERINGS*******************************/

function initAddNewDeloadOfferingsModal(){
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

function updateAddNewDeloadOfferingsDropdowns(){
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
        				
        				rows += "<td>" + "<a class=\"btn btn-default add-button\" type=\"button\" onclick=\"addCourseToTemporaryOfferingTable(this.id)\" id=\"" + id + "-add\"><span><i class=\"fa fa-plus\"></i></span></a></td></tr>"
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
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getAllCoursesWithCourseKey',
        data:{
        	"searchKeyword": searchKeyword
        },
        success: function (data) {
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
        				
        				rows += "<td>" + "<a class=\"btn btn-default add-button\" type=\"button\" onclick=\"addCourseToTemporaryOfferingTable(this.id)\" id=\"" + id + "-add\"><span><i class=\"fa fa-plus\"></i></span></a></td></tr>"
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
	var arr = [];
	arr = courseID.split("-"); //arr[0] clickedCourseID;
	
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
        	
        	var offering = new TempOffering("", (data.courseId + "-" +appenderID), data.courseCode, "", "", "", "Regular", "");
        	
        	tempCourseOfferings[appenderID] = offering;
        	appenderID++;
        	//sortTempCourseOfferings();
        	updateTemporaryOfferingList();
        },
    	error: function (data){
    		console.log(data);
    	}
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
		var courseCode = currObject.courseCode;
		var section = currObject.section;
		var batch = currObject.batch;//$('#batchANODropdown').find(":selected").text();
		var term = currObject.term;
		var room = currObject.room; //roomCode 
		var status = currObject.status;
		var remarks = currObject.remarks;
		var faculty = currObject.faculty;
		
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
		
		rows += "<td class=\"editable-cell\" id=\""+id+"-batch\" contenteditable>"+batch+"</td>"+
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
	
	var concatId = arr[0]+"-"+arr[1]; //course code is arr[0], appenderID is arr[1]
	var hasFound = 0, deleteIndex = 0; //check if element to be deleted is found. If so, all succeedding elements will have their appenderID decreease by 1 to signify change in index
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
			deleteIndex = arr[1];
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
			currObject.faculty = faculty;
			currObject.facultyId = facultyId;
			currObject.status = status;
			currObject.room = currObject.room;
			currObject.remarks = remarks;
			currObject.daysList1 = []; //if the user clicked save again, babalik lang din un mga nakatoggle
			currObject.daysList2 = [];//magstack ung days if di inempty before lagyan ulit
			currObject = saveTemporaryOfferingTimeSlots(currObject, currObject.daysList1, 1);
			currObject = saveTemporaryOfferingTimeSlots(currObject, currObject.daysList2, 2);
			tempCourseOfferings[arr2[1]] = currObject;
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
					"facultyId": currObject.facultyId,
					"room": currOffering.room,
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
	        		var faculty = currObject.faculty.firstName + " " + currObject.faculty.lastName;
	        		console.log(faculty + " " + term);
	        		var status = currObject.status;
	        		var remarks = currObject.remarks;
	        		var days1 = [];
	        		var days2 = [];
	        		var timeSlot1 = "";
	        		var timeSlot2 = "";
	        		var room = "";
	        		
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
	        		offering.faculty = faculty;
	        		offering.daysList1 = [];
	        		offering.daysList2 = [];
	        		offering.daysList1 = days1.slice(0); //Array.prototype.slice returns a shallow copy of a portion of an array. Giving it 0 as the first parameter means you are returning a copy of all the elements (starting at index 0 that is)
	        		offering.daysList2 = days2.slice(0);
	        		offering.timeSlot1 = timeSlot1;
	        		offering.timeSlot2 = timeSlot2;
	        		offering.room = room; //not confuse room with roomObject

	        		tempCourseOfferings[appenderID] = offering;
	        		//console.log(tempCourseOfferings[appenderID].courseCode);
	            	appenderID++; //for next offering
	            	updateTemporaryOfferingList();
	        	});
	        },
	    	error: function (data){
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

function unassignRoomToAnOffering(id){
		
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
        		//if nasa takenRooms that means t6aken ung current Room.
        		$.each(takenRooms, function(n, currRoom){
        			var arr2 = [];
        			arr2 = currRoom.courseId.split('-'); // arr2[0] - courseId, arr2[1] - appender, arr[3] & arr[4] - extra strings
        			console.log(currRoom.roomId === roomId);
        			if(currRoom.roomId === roomId){
        				roomTaken = 1;
        				assignedCourseAppenderId = arr2[1];
        			}
        		});
        		
    			if(foundMatch === 0 && roomTaken === 0) 
    				rows += "<tr id=\"" + id + "-id\" class=\"clicked-room-inactive roomRow "+id+"-id\">";
    			else if((foundMatch === 1 && roomTaken === 0) || (foundMatch === 1 && roomTaken === 1)) //second argument is kasi kapag ung current course ung assigned dun sa room meaning no collision kasi nakaassign na siya dun
    				rows += "<tr id=\"" + id + "-id\" class=\"roomRow match-room-offering "+id+"-id\">";
    			else if(foundMatch === 0 && roomTaken === 1)
    				rows += "<tr id=\"" + id + "-id\" class=\"roomRow room-collision "+id+"-id\">";
    			else{
    				rows += "<tr id=\"" + id + "-id\" class=\"clicked-room-inactive roomRow "+id+"-id\">";
    			}
    			
				rows+="<td>"+roomCode+"</td>";
    			
				rows+=  "<td>"+roomType+"</td>"+
						"<td>"+building+"</td>"+
						"<td>"+roomCapacity+"</td>";
				
				if(roomTaken === 1){
					var assignedCourseCode = tempCourseOfferings[assignedCourseAppenderId].courseCode;
					var assignedSection = tempCourseOfferings[assignedCourseAppenderId].section;
					
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

/**************************ROOM ASSIGNMENT IN ADD NEW OFFERING MODAL**********************************/
/******ADD NEW FACULTY***********/
function clickAddNewFaculty(id){
    clickSideBar(id);
    makeActivePanel("#panelAddNewFaculty");
	fixForParallax();
}
/******ADD NEW FACULTY***********/
/******ADD NEW COURSE***********/
function clickAddNewCourse(id){
    clickSideBar(id);
    makeActivePanel("#panelAddNewCourse");
    initCourseSearchSuggestion();
    setDeptAddNewCourse();
	fixForParallax();
}

function setDeptAddNewCourse(){
	var cname = getCookie("Gamer");
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getSingleDepartment',
        data: {
			"userId": cname
        },
        success: function (data) {
        	console.log("Dept Loaded");
        	
        	$("#newCourseDept").html(data.message);
        	$("#newCourseDeptH").val(data.message);
        },
    	error: function (data){
    		console.log("ERROR");
    		console.log(data);
    	}
    });
	
}

function getCookie(cname){
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function initCourseSearchSuggestion(){
	if(newCourseSuggestList.length === 0){
		var cname = getCookie("Gamer");
		
		$.ajax({
	        type: 'GET',
	        dataType: 'json',
	        cache: false,
	        url: 'getAllCoursesSuggestion',
	        data: {
				"userId": cname
	        },
	        success: function (data) {
	        	console.log("Suggestion Loaded");
	        	
	        	$.each(data, function(i, currOption){
	        		newCourseSuggestList.push(currOption);
//	        		console.log(currOption);
	        	});
	        },
	    	error: function (data){
	    		console.log(data);
	    	}
	    });
	}
}

function updateCourseSearchSuggestion(){
	var cname = getCookie("Gamer");
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        cache: false,
        url: 'getAllCoursesSuggestion',
        data: {
			"userId": cname
        },
        success: function (data) {
        	console.log("Suggestion Updated");
        	newCourseSuggestList = [];
        	
        	$.each(data, function(i, currOption){
        		newCourseSuggestList.push(currOption);
        	});
        },
    	error: function (data){
    		console.log(data);
    	}
    });
}

function addNewRequisiteRow(){
	var inputBtnString = '<div class="input-group-btn" for="newCourseReq">';
	var finalString = "";
	var dropdownString = "";
	var reqBtnString = "";
	var listString = "";
	var hiddenString = "";
	var courseCodeString = "";
	var plusBtnString = "";
	var id = getRandomNumber();
	
	finalString += '<div class="input-group" id="' + id + '-group">';
	
	reqBtnString += '<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" id="' + id + '-btn">Pre-req <span class="caret"></span></button>';
	
	dropdownString += '<ul class="dropdown-menu newCourseReqTypeList">' +
					        '<li id="' + id + '-li1" onclick="changeNewCourseReqType(this.id)"><a>Pre-req</a></li>' +
//					        '<li id="' + id + '-li2" onclick="changeNewCourseReqType(this.id)"><a>Post-req</a></li>' +
					        '<li id="' + id + '-li3" onclick="changeNewCourseReqType(this.id)"><a>Equivalent</a></li>' +
					    '</ul>';
	
	hiddenString += '<input type="hidden" name="newCourseReqType[]" id="' + id + '-hid" value="Pre-req"/>';
	
	courseCodeString += '<input type="text" class="form-control newCourseReqInput" id="newCourseReq" name="newCourseReq[]" placeholder="Enter Course Code"/>';
	
	plusBtnString += '<button class="btn btn-default" onclick="removeRequisiteRow(this.id)" type="button" id="' + id + '"> - </button>';
	
	finalString += inputBtnString + reqBtnString + dropdownString + '</div>' + hiddenString + courseCodeString + inputBtnString + plusBtnString + '</div></div>';
	
	$("#newCourseReqList").append(finalString);
}

function changeNewCourseReqType(id){
	var value = $("#" + id).find("a").text();
//	alert(value);
	
	setNewCourseDropdownValue(id, value)
}

function setNewCourseDropdownValue(id, value){
	var parsedId = parseNewCourseId(id);
	
	$("#" + parsedId[0] + "-btn").html(value + ' <span class="caret"></span>');
	$("#" + parsedId[0] + "-hid").val(value);
//	alert($("#" + parsedId[0] + "-hid").val());
}

function removeRequisiteRow(id){
	$("#" + id + "-group").remove();
}

function parseNewCourseId(id){
	return id.split("-")
}
/******ADD NEW COURSE***********/
/******MATH***********/
function getRandomNumber(){
	return (Math.floor(Math.random() * 100000) + 10) + (Math.floor(Math.random() * 100000) + 10);
}
/******MATH***********/
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

$('#searchFacultyFA').keyup(function() {
	if(isPure === 0){
		isPure = 1;
	}
	
	if($('#divFASearchFaculty').val() === ""){
		initiateFARecommendedFacultyList($('#assignFAFacultyOfferingIDDump').val());
	}else updateFAFacultyList($('#assignFAFacultyOfferingIDDump').val());
});

/*$('#searchFacultyFA').on('input',function(e){
	if($('#divFASearchFaculty').val() === ""){
		initiateFARecommendedFacultyList($('#assignFAFacultyOfferingIDDump').val());
	}else updateFAFacultyList($('#assignFAFacultyOfferingIDDump').val());
});*/

$('#searchCourseFA').keyup(function() {
	//console.log("entered");
	updateFacultyAssignmentOfferingList(globalStartYear + "-" + globalEndYear + "-" + globalTerm + "-year");
});

/*$('#searchCourseFA').on('input',function(e){
	if($('#divFASearchFaculty').val() === ""){
		initiateFARecommendedFacultyList($('#assignFAFacultyOfferingIDDump').val());
	}else updateFAFacultyList($('#assignFAFacultyOfferingIDDump').val());
});*/

$('#CVCsearchOfferingVO').keyup(function() {
	viewOfferingsInModalCVC($('#viewOfferingsCurrentAYDump').val());
});

$('#CVCsearchOfferingVO').on('input',function(e){
	viewOfferingsInModalCVC($('#viewOfferingsCurrentAYDump').val());
});

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
    /*********FOR AUTOCOMPLETE ADD NEW COURSES*******************************/
    $("#newCourseReqList").on('focus', '.newCourseReqInput', function() {
    	$( ".newCourseReqInput" ).autocomplete({
    	  source: newCourseSuggestList
    	});
    } );
    /*********FOR AUTOCOMPLETE ADD NEW COURSES*******************************/
    /*********FOR SUBMITTING ADD NEW FACULTY FORM****************************/
    $('#addNewFacultyForm').submit(function(e){
		e.preventDefault;

		console.log("Submitting...");
		
		var userId = getCookie("Gamer");
		
		$("#addNewFacultyUser").val(userId);
		
		$.ajax({
	        type: 'POST',
	        dataType: 'json',
	        cache: false,
	        url: 'addNewFacultyToDB',
	        data: $('#addNewFacultyForm').serialize(),
	        success: function (data) {
	        	console.log("Success");
	        	$("#addNewFacultyModalHeader").text(data.message);
	        	$("#addNewFacultyMsgModal").modal("show");
	        },
	    	error: function (data){
	    		console.log(data);
	    	}
	    });
	});
    /*********FOR SUBMITTING ADD NEW FACULTY FORM****************************/
    /*********FOR SUBMITTING ADD NEW COURSE FORM*****************************/
    $('#addNewCourseForm').submit(function(e){
		e.preventDefault;

//		console.log("Submitting...");
		
		var userId = getCookie("Gamer");
		
		$("#addNewCourseUser").val(userId);
		
		$.ajax({
	        type: 'POST',
	        dataType: 'json',
	        cache: false,
	        url: 'addNewCourseToDB',
	        data: $('#addNewCourseForm').serialize(),
	        success: function (data) {
	        	console.log(data.message);
	        	updateCourseSearchSuggestion();
	        	$("#addNewCourseModalHeader").text(data.message);
	        	$("#addNewCourseMsgModal").modal("show");
//	        	for(var i = 0; i < newCourseSuggestList.length; i++){
//	        		console.log("suggestList: " + newCourseSuggestList[i]);
//	        	}
	        },
	    	error: function (data){
	    		console.log(data);
	    	}
	    });
		
	});
    /*********FOR SUBMITTING ADD NEW COURSE FORM*****************************/
    /*********FOR ADD NEW COURSE FORM ERROR**********************************/
    $("#addNewCourseForm").on('blur', '.filledOut', function(event) {
//    	console.log(event.target.id);
    	if($("#" + event.target.id).val().length === 0){
    		if($("#" + event.target.id + "Error").length === 0){
    			$("#" + event.target.id).get(0).setCustomValidity("WARNING! Please fill out this field!");
        		$("#" + event.target.id + "Group").addClass("has-error");
        		$("#" + event.target.id + "Help").append('<span class="help-block" id="' + event.target.id + 'Error">WARNING! Please fill out this field!</span>');
    		}
    	} else {
    		$("#" + event.target.id).get(0).setCustomValidity("");
    		$("#" + event.target.id + "Group").removeClass("has-error");
    		$("#" + event.target.id + "Error").remove();
    	}
    } );
    
    $("#addNewCourseForm").on('blur', '.mustSeven', function(event) {
    	if($("#" + event.target.id).val().length !== 7){
    		if($("#" + event.target.id + "Error").length === 0){
    			$("#" + event.target.id).get(0).setCustomValidity("Must contain exactly 7 characters!");
        		$("#" + event.target.id + "Group").addClass("has-error");
    		}
    	} else {
    		$("#" + event.target.id).get(0).setCustomValidity("");
    		$("#" + event.target.id + "Group").removeClass("has-error");
    	}
    } );
    
    $("#addNewCourseForm").on('keyup', '.mustSeven', function(event) {
    	if($("#" + event.target.id).val().length === 7){
    		$("#" + event.target.id).get(0).setCustomValidity("");
    		$("#" + event.target.id + "Group").removeClass("has-error");
    	} 
    } );
    
    /*********FOR ADD NEW COURSE FORM ERROR**********************************/
    /*********FOR ADD NEW FACULTY FORM ERROR*********************************/
    $("#addNewFacultyForm").on('blur', '.filledOut', function(event) {
//    	console.log(event.target.id);
    	if($("#" + event.target.id).val().length === 0){
    		if($("#" + event.target.id + "Error").length === 0){
    			$("#" + event.target.id).get(0).setCustomValidity("WARNING! Please fill out this field!");
        		$("#" + event.target.id + "Group").addClass("has-error");
        		$("#" + event.target.id + "Help").append('<span class="help-block" id="' + event.target.id + 'Error">WARNING! Please fill out this field!</span>');
    		}
    	} else {
    		$("#" + event.target.id).get(0).setCustomValidity("");
    		$("#" + event.target.id + "Group").removeClass("has-error");
    		$("#" + event.target.id + "Error").remove();
    	}
    } );
    
    $("#addNewFacultyForm").on('blur', '.passwordCheck', function() {
    	if($("#newFacultyConfirmPassword").val().length > 0){
    		if($("#newFacultyConfirmPassword").val() === $("#newFacultyPassword").val()){
        		$("#newFacultyConfirmPassword").get(0).setCustomValidity("");
        		$("#newFacultyConfirmPasswordGroup").removeClass("has-error");
        		$("#newFacultyConfirmPasswordHelpBlock").remove();
        	} else {
        		if($("#newFacultyConfirmPasswordHelpBlock").length === 0){
            		$("#newFacultyConfirmPassword").get(0).setCustomValidity("WARNING! Passwords do not match!");
            		$("#newFacultyConfirmPasswordGroup").addClass("has-error");
            		$("#newFacultyConfirmPasswordHelp").append('<span class="help-block" id="newFacultyConfirmPasswordHelpBlock">WARNING! Passwords do not match!</span>');
        		}
        	}
    	}
    } );
    /*********FOR ADD NEW FACULTY FORM ERROR*********************************/
    /*********FOR ADD NEW DELOADING SUBMIT*********************************/
    $("#addNewDeloadingForm").submit(function(e){
    	e.preventDefault();
    	var deloadingName = document.getElementById("addDeloadingName").value;
    	var deloadingCode = document.getElementById("addDeloadingCode").value;
    	var deloadingDD = document.getElementById("addDeloadingDropDown");
    	var deloadingType = deloadingDD.options[deloadingDD.selectedIndex].value;
    	var units = document.getElementById("addDeloadingUnits").value;
    	var description = document.getElementById("addDeloadingDescription").value;
    	var deptId = $('#deptIDDump').val(); 
    	
    	console.log("dname: " + deloadingName);
    	
    	$.ajax({
            type: 'POST',
            dataType: 'json',
            async: false,
            url: 'addNewDeloading',
            data: {
            	"deloadingName" : deloadingName,
            	"deloadingCode": deloadingCode,
            	"deloadingType": deloadingType,
    			"units": units,
    			"description": description,
    			"deptId": deptId
            },
            success: function (data) {
            	//console.log(data);
	        	$("#addNewDeloadingModalHeader").text(deloadingName + " was successfully added!");
	        	$("#addNewDeloadingMsgModal").modal("show");
            	console.log("Successfully added new deloading!");
            },
        	error: function (data){
        		console.log(data);
	        	$("#addNewDeloadingModalHeader").text("Error!" + deloadingName + " was added successfully!");
	        	$("#addNewDeloadingMsgModal").modal("show");
        	}
        });
    });
    /*********FOR ADD NEW DELOADING SUBMIT*********************************/
})
 /*********THESE FIXES ANY PROBLEM RELATED TO MODAL SCROLLBARS************/
$(document).on('hidden.bs.modal', function () {
$('body').addClass('modal-open');
});
/*********THESE FIXES ANY PROBLEM RELATED TO MODAL SCROLLBARS************/

/************************ DELOADING RELATED ******************************/

function updateFacultyLoadListInDeloading() {
	
	var startYear = glbStartYear;
	var endYear = glbEndYear;
	var term = glbTerm;
	
	var searchKeyword = $('#searchFacultyViaDeloading').val();
	
	
	if (searchKeyword.length === 0)
		searchKeyword = "";
	if (searchKeyword == null)
		searchKeyword = "";
	
	 var departmentId = $('#deptIDDump').val();
	
	$.ajax({
        type: 'GET',
        dataType: 'json',
        url: 'getFacultyListWithKeyViaDeloading',
        data: {
        	"searchKeyword": searchKeyword,
        	"departmentId": departmentId
        },
        success: function (data) {
            $("#tableFacultyLoadViaDeloading tbody tr").remove(); //removes all tr but not thead
            $.each(data, function(index, currObject){
                var row = "";
              //  var leaveType = '';

                
               
                row = "<tr>" ;
                row += "<td>" + currObject.firstName + " " + currObject.lastName + "</td>";
                //row += '<td>' + currObject.firstName + " " + '<br>' + currObject.lastName + '</td>';
                row += "<td>" + currObject.facultyType + "</td>";
                row += "<td> <a class=\"btn btn-success\" type=\"button\" onclick=\"viewDeloadingModal(this.id)\" id=\"" + currObject.facultyId + "\">Deloading</a>" + "</td>";
                //row += '<td>' + leaveType + '</td>';
                row += "</tr>";
                
                $("#tableFacultyLoadViaDeloading tbody").append(row);
            })
        },
        error: function (data){
            console.log(data);
        }
    });     
	
	
}

function clickDeloading(id){
    clickSideBar(id);
    //populateLoadAYViaDeloading();
    makeActivePanel("#panelViewDeloading");
    $("#tableFacultyLoadViaDeloading tbody tr").remove(); //removes all tr but not thead; for refresh or sudden click at sidebar
    getListFacultyLoadViaDeloading(); //populate faculty list table in deloading
}

function clickAddNewDeloading(id){
    clickSideBar(id);
    //populateLoadAYViaDeloading();
    makeActivePanel("#panelAddNewDeloading");
}

function getAYDeloading() { //gets AY then goes to faculty list deloading modal
	var startYear = $("#deloadingStartYear").val();
	var endYear = $("#deloadingEndYear").val();
	var term = $("#deloadingTerm").val();
	
	glbStartYear = startYear;
	glbEndYear = endYear;
	glbTerm = term;
	
	var AY = startYear + "-" + endYear + "-" + term;
	
	//console.log(AY);
	
	 $("#searchFacultyViaDeloading").val("");
	getListFacultyLoadViaDeloading(AY);

	
}

function populateLoadAYViaDeloading(){
    $("#loadAYDropDownDeloading").empty();
    $("#loadAYDropDownDeloading").append("<option selected> Select Term </option>");
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: 'getLoadTimeframeList',
        success: function (data) {
            $.each(data, function(index, currObject){
                var input = '<option value="' + currObject.startYear + '-' + currObject.endYear + '-' + currObject.term + '">' + 
                    currObject.startYear + '-' + currObject.endYear + ' T' + currObject.term + '</option>';
                $("#loadAYDropDownDeloading").append(input);
            })
        },
        error: function (data){
            console.log(data);
        }
    });
}

function populateDeloadingModal(){
    $("#AdminLoadDropDown").empty();
    $("#ResearchLoadDropDown").empty();
    $("#LeaveLoadDropDown").empty();
    $("#OtherLoadDropDown").empty();
    
    $("#AdminLoadDropDown").append("<option value = '0'> Select Deloading </option>");
    $("#ResearchLoadDropDown").append("<option value = '0'> Select Deloading </option>");
    $("#LeaveLoadDropDown").append("<option value = '0'> Select Deloading </option>");
    $("#OtherLoadDropDown").append("<option value = '0'> Select Deloading </option>");
    
    //populate dropdowns
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: 'getDeloadingList',
        success: function (data) {
            $.each(data, function(index, currObject){
                var input = '<option value="' + currObject.deloadingId +'">' + 
                    currObject.deloadingCode + '</option>';   
                
                if(currObject.deloadingType == "AL")
                	$("#AdminLoadDropDown").append(input);
                else if (currObject.deloadingType == "RL")
                	$("#ResearchLoadDropDown").append(input);
                else if (currObject.deloadingType == "LL")
                	$("#LeaveLoadDropDown").append(input);
                else if (currObject.deloadingType == "OL")
                	$("#OtherLoadDropDown").append(input);
            })
        },
        error: function (data){
            console.log(data);
        }
    });
    
    //var startYear = glbStartYear;
	//var endYear = glbEndYear;
	//var term = glbTerm;
	var facultyId = glbFacultyId;
	
    
    
    //populate deloading table
    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: 'getFacultyDeloadingAll',
        data: {
            "facultyId": facultyId,
        },
        success: function (data) {
        	$.each(data, function(index, currObject){
        	
        	var rows = "";
            
            rows += "<tr id=\"" + currObject.deloadofferId + "\">";
            rows += "<td>"+currObject.deloading.deloadingCode+"</td>";
            rows += "<td>"+currObject.deloading.deloadingType+"</td>";
            rows += "<td>"+currObject.deloading.deloadUnits+"</td>";
            rows += "<td>"+currObject.start_year+"</td>";
            rows += "<td>"+currObject.end_year+"</td>";
            rows += "<td>"+currObject.term+"</td>";
            
            rows += "<td> <a class=\"btn btn-danger\" type=\"button\" onclick=\"removeDeloading(this.id)\" id=\"" + currObject.deloadofferId + "\">Remove</a>" + "</td>";   
            rows+="<tr>";
            //console.log(rows);
            $(rows).appendTo("#tableDeloading tbody");
        	})
           
        },
        error: function (data){
            console.log(data);
        }
    });
}

function removeDeloading(deloadofferId) {
	var startYear = glbStartYear;
	var endYear = glbEndYear;
	var term = glbTerm;
	var facultyId = glbFacultyId;
	
	$.ajax({
		type : 'POST',
		dataType : 'json',
		cache : false,
		url : 'removeDeloading',
		data : {
			"deloadofferId" : deloadofferId
		},
		success : function() {
			//TODO: update the shown table/s
			updateFacultyDeloadingTable();
			console.log("Removing of deloading successful!");
		},
		error : function() {
			console.log();
		}
	});
}

function adminDeloading() {
	
	var adminDD = document.getElementById("AdminLoadDropDown");
	var deloadingId = adminDD.options[adminDD.selectedIndex].value;
	
	// glb = global variable, these variables was set before everything it got here
	var startYear = document.getElementById("deloadingStartYear").value;
	var endYear = document.getElementById("deloadingEndYear").value;
	//var term = glbTerm;
	var facultyId = glbFacultyId;
	
	var terms = [];
	// check "checked" checkboxes
	if (document.getElementById("deloadingTerm1").checked == true)
		terms.push("1");
	if (document.getElementById("deloadingTerm2").checked == true)
		terms.push("2");
	if (document.getElementById("deloadingTerm3").checked == true)
		terms.push("3");
		
	console.log("tenen: "  + facultyId + startYear + endYear + deloadingId);
	
	if (deloadingId == 0 || startYear == null || endYear == null || terms.length == 0 ) // none selected
		console.log("Select needed values!");
	else {
		for(i = 0; i < terms.length ; i++) {
			$.ajax({
				type : 'POST',
				dataType : 'json',
				cache : false,
				url : 'adminDeloading',
				data : {
					"facultyId" : facultyId,
					"startYear" : startYear,
					"endYear" : endYear,
					"term" : terms[i],
					"deloadingId" : deloadingId
				},
				success : function() {
					//TODO: update the shown table/s
					updateFacultyDeloadingTable();
					console.log("Admin deloading successful!");
				},
				error : function() {
					console.log("Error in admin deloading!");
				}
			});
		}
	}
	//updateFacultyDeloadingTable();
}

function researchDeloading() {
	
	var researchDD = document.getElementById("ResearchLoadDropDown");
	var deloadingId = researchDD.options[researchDD.selectedIndex].value;
	
	// glb = global variable, these variables was set before everything it got here
	var startYear = document.getElementById("deloadingStartYear").value;
	var endYear = document.getElementById("deloadingEndYear").value;
	//var term = glbTerm;
	var facultyId = glbFacultyId;
	
	var terms = [];
	// check "checked" checkboxes
	if (document.getElementById("deloadingTerm1").checked == true)
		terms.push("1");
	if (document.getElementById("deloadingTerm2").checked == true)
		terms.push("2");
	if (document.getElementById("deloadingTerm3").checked == true)
		terms.push("3");
		
	console.log("tenen: "  + facultyId + startYear + endYear + deloadingId);
	
	
	if (deloadingId == 0 || startYear == null || endYear == null || terms.length == 0 ) // none selected
		console.log("Select needed values!");
	else {
		
		for(i = 0; i < terms.length ; i++) {
		
		$.ajax({
			type : 'POST',
			dataType : 'json',
			cache : false,
			url : 'researchDeloading',
			data : {
				"facultyId" : facultyId,
				"startYear" : startYear,
				"endYear" : endYear,
				"term" : term,
				"deloadingId" : deloadingId
			},
			success : function() {
				//TODO: update the shown table/s
				updateFacultyDeloadingTable();
				console.log("Research deloading successful!");
			},
			error : function() {
				console.log("Error in research deloading!");
			}
		});
		
		}
	}
	//updateFacultyDeloadingTable();
}

function leaveDeloading() {
	
	var leaveDD = document.getElementById("LeaveLoadDropDown");
	var deloadingId = leaveDD.options[leaveDD.selectedIndex].value;
	
	// glb = global variable, these variables was set before everything it got here
	var startYear = document.getElementById("deloadingStartYear").value;
	var endYear = document.getElementById("deloadingEndYear").value;
	//var term = glbTerm;
	var facultyId = glbFacultyId;
	
	var terms = [];
	// check "checked" checkboxes
	if (document.getElementById("deloadingTerm1").checked == true)
		terms.push("1");
	if (document.getElementById("deloadingTerm2").checked == true)
		terms.push("2");
	if (document.getElementById("deloadingTerm3").checked == true)
		terms.push("3");
		
	//console.log("tenen: "  + facultyId + startYear + endYear + deloadingId);
	
	
	if (deloadingId == 0 || startYear == null || endYear == null || terms.length == 0 ) // none selected
		console.log("Select needed values!");
	else {
		
		for(i = 0; i < terms.length ; i++) {
		
			$.ajax({
				type : 'POST',
				dataType : 'json',
				cache : false,
				url : 'leaveDeloading',
				data : {
					"facultyId" : facultyId,
					"startYear" : startYear,
					"endYear" : endYear,
					"term" : term,
					"deloadingId" : deloadingId
				},
				success : function() {
					//TODO: update the shown table/s
					updateFacultyDeloadingTable();
					console.log("Research deloading successful!");
				},
				error : function() {
					console.log("Error in research deloading!");
				}
			});
		
		}
	}
	//updateFacultyDeloadingTable();
}

function updateFacultyDeloadingTable() {
	$("#tableDeloading tbody tr").remove(); 
    var facultyId = glbFacultyId;

	   $.ajax({
	        type: 'GET',
	        dataType: 'json',
	        url: 'getFacultyDeloadingAll',
	        data: {
	            "facultyId": facultyId,
	        },
	        success: function (data) {
	        	$.each(data, function(index, currObject){
	        	
	        	var rows = "";
	            
	            rows += "<tr id=\"" + currObject.deloadofferId + "\">";
	            rows += "<td>"+currObject.deloading.deloadingCode+"</td>";
	            rows += "<td>"+currObject.deloading.deloadingType+"</td>";
	            rows += "<td>"+currObject.deloading.deloadUnits+"</td>";
	            rows += "<td>"+currObject.start_year+"</td>";
	            rows += "<td>"+currObject.end_year+"</td>";
	            rows += "<td>"+currObject.term+"</td>";
	            
	            rows += "<td> <a class=\"btn btn-danger\" type=\"button\" onclick=\"removeDeloading(this.id)\" id=\"" + currObject.deloadofferId + "\">Remove</a>" + "</td>";   
	            rows+="<tr>";
	            //console.log(rows);
	            $(rows).appendTo("#tableDeloading tbody");
	        	})
	           
	        },
	        error: function (data){
	            console.log(data);
	        }
	    });
}

function getListFacultyLoadViaDeloading(){
    //console.log("get list faculty");
    
    //$("#searchFacultyViaDeloading").val(id); // for search faculty
    
   /* var arr = [];
    arr = id.split('-'); //arr[0] would be startyear, [1], endyear, [2] term
    var startYear = arr[0];
    var endYear = arr[1];
    var term = arr[2];
    
    // to easily get the term
    glbStartYear = startYear;
    glbEndYear = endYear;
    glbTerm = term;*/
    
    var departmentId = $('#deptIDDump').val();

    $.ajax({
        type: 'GET',
        dataType: 'json',
        url: 'getFacultyListSameDeparment',
        data: {
            "departmentId": departmentId // id contains start year, end year, and term
        },
        success: function (data) {
            $("#tableFacultyLoadViaDeloading tbody tr").remove(); //removes all tr but not thead
            $.each(data, function(index, currObject){
                var row = '';
              //  var leaveType = '';
                row = '<tr>';
                row += '<td>' + currObject.firstName + " " + currObject.lastName + '</td>';
                //row += '<td>' + currObject.firstName + " " + '<br>' + currObject.lastName + '</td>';
                row += '<td>' + currObject.facultyType + '</td>';
                row += "<td> <a class=\"btn btn-success\" type=\"button\" onclick=\"viewDeloadingModal(this.id)\" id=\"" + currObject.facultyId + "\">Deloading</a>" + "</td>";
                //row += '<td>' + leaveType + '</td>';
                row += '</tr>';
                
                $("#tableFacultyLoadViaDeloading tbody").append(row);
            })
            // show faculty list modal
           // viewFacultyListModal();
        },
        error: function (data){
            console.log(data);
        }
    });     
}

function viewDeloadingModal(facultyId) {
     glbFacultyId = facultyId;
     
     $("#tableDeloading tbody tr").remove(); 
	 populateDeloadingModal();
	 $('#deloadingModal').modal('show');
	
}

//$('#searchFacultyViaDeloading').keyup(function() {
//	updateFacultyLoadListInDeloading();
//});

$('#searchFacultyViaDeloading').on('input',function(e){
	updateFacultyLoadListInDeloading();
});

function viewFacultyListModal() {
	 $('#deloadingFacultyListModal').modal('show');
}

//function addNewDeloading() {
//	var deloadingName = document.getElementById("addDeloadingName").value;
//	var deloadingCode = document.getElementById("addDeloadingCode").value;
//	var deloadingDD = document.getElementById("addDeloadingDropDown");
//	var deloadingType = deloadingDD.options[deloadingDD.selectedIndex].value;
//	var units = document.getElementById("addDeloadingUnits").value;
//	var description = document.getElementById("addDeloadingDescription").value;
//	var deptId = $('#deptIDDump').val(); 
//	
//	console.log("dname: " + deloadingName);
//	
//	$.ajax({
//        type: 'POST',
//        dataType: 'json',
//        async: false,
//        url: 'addNewDeloading',
//        data: {
//        	"deloadingName" : deloadingName,
//        	"deloadingCode": deloadingCode,
//        	"deloadingType": deloadingType,
//			"units": units,
//			"description": description,
//			"deptId": deptId
//        },
//        success: function (data) {
//        	//console.log(data);
//        	// show success modal
//        	console.log("Successfully added new deloading!");
//        },
//    	error: function (data){
//    		console.log(data);
//    	}
//    });
//	
//	
//}
/**** DELOOOADING ***********/
/*********LOADING MODAL****************/
function showLoadingModal(){
	console.log("showing loading...");
	if(!($("#loadingModal").data('bs.modal') || {isShown: false}).isShown){
		$('#loadingModal').modal({backdrop: 'static', keyboard: false});
	}
}

function hideLoadingModal(){
	console.log("hiding loading...");
	if(($("#loadingModal").data('bs.modal') || {isShown: false}).isShown){
		$("#loadingModal").hide();
	}
}
/*********LOADING MODAL****************/
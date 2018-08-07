<form id="">
    <input type="hidden" class="addNewOfferingsOfferingIDDump" name="addNewOfferingsOfferingIDDump" id="addNewOfferingsOfferingIDDump" value="">
    <input type="hidden" class="addNewOfferingsCurrentAYDump" name="addNewOfferingsCurrentAYDump" id="addNewOfferingsCurrentAYDump" value="">
</form>
<form action="addNewOfferingList" method="post" class="form-box" role="form" id="saveOfferingForm">
    <input type="hidden" class="saveOfferingDump" name="saveOfferingStartYearDump" id="saveOfferingStartYearDump" value="">
    <input type="hidden" class="saveOfferingDump" name="saveOfferingEndYearDump" id="saveOfferingEndYearDump" value="">
    <input type="hidden" class="saveOfferingDump" name="saveOfferingTermDump" id="saveOfferingTermDump" value="">
    <input type="hidden" class="saveOfferingDump" name="saveOfferingDegreeProgramDump" id="saveOfferingDegreeProgramDump" value="">
    <input type="hidden" class="saveOfferingDump" name="saveOfferingCourseIDDump" id="saveOfferingCourseIDDump" value="">
    <input type="hidden" class="saveOfferingDump" name="saveOfferingCourseCodeDump" id="saveOfferingCourseCodeDump" value="">
    <input type="hidden" class="saveOfferingDump" name="saveOfferingSectionDump" id="saveOfferingSectionDump" value="">
    <input type="hidden" class="saveOfferingDump" name="saveOfferingBatchDump" id="saveOfferingBatchDump" value="">
    <input type="hidden" class="saveOfferingDump" name="saveOfferingStatusDump" id="saveOfferingStatusDump" value="">
    <input type="hidden" class="saveOfferingDump" name="saveOfferingRemarksDump" id="saveOfferingRemarksDump" value="">
    <input type="hidden" class="saveOfferingDump" name="saveOfferingRoomDump" id="saveOfferingRoomDump" value="">
    <input type="hidden" class="saveOfferingDump" name="saveOfferingDaysList1Dump" id="saveOfferingDaysList1Dump" value="">
    <input type="hidden" class="saveOfferingDump" name="saveOfferingDaysList2Dump" id="saveOfferingDaysList2Dump" value="">
    <input type="hidden" class="saveOfferingDump" name="saveOfferingTimeSlot1Dump" id="saveOfferingTimeSlot1Dump" value="">
    <input type="hidden" class="saveOfferingDump" name="saveOfferingTimeSlot2Dump" id="saveOfferingTimeSlot2Dump" value="">
</form>
<form id="">
    <input type="hidden" class="assignANORoomOfferingIDDump" name="assignRoomOfferingIDDump" id="assignANORoomOfferingIDDump" value="">
    <input type="hidden" class="assignANORoomRoomIDDump" name="assignRoomRoomIDDump" id="assignANORoomRoomIDDump" value="">
    <input type="hidden" class="assignANORoomCurrentAYDump" name="assignRoomCurrentAYDump" id="assignANORoomCurrentAYDump" value="">
</form>

<!-- Screen may move to the right or left after modal open up. Explanation: This is because your scrollbar disappears to prevent user scrolling on the main window -->
<div class="modal fade" id="setNewOfferingsModal" role="dialog" >
  <div class="modal-dialog">
    <div class="modal-content">
      
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="exitSetNewOfferingsModal()"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="setNewOfferingsModalTitle">New Offerings Information</h4>
      </div>
      
      	<div class="col-md-12">
      	<br>
      	<div class="modal-body col-md-12">
	      	<div class ="modal-body col-md-12 " id="divModalANOAYInfo">
	      		<form action="loginSubmit" class="form-box" role="form">
		      	<div class="col-md-4 form-group">
			       <label for="textStartYearANO">Start Year</label>
			       <input type="number" required class="form-control" id="textStartYearANO" placeholder="e.g. 2020">
			    </div>
			    <div class="col-md-4 form-group">
			       <label for="textEndYearANO">End Year</label>
			       <input type="number" required class="form-control" id="textEndYearANO" placeholder="e.g. 2021">
			    </div>
			    <div class="col-md-4 form-group">
			       <label for="textTermANO">Term</label>
			       <input type="number" required class="form-control" id="textTermANO" placeholder="e.g. 1">
			    </div>
			    </form>
		    </div>
		    <!--<div class ="modal-body col-md-12 " id="divModalANOStudentInfo">
		    	<div class="col-md-6 form-group">
			       <label for="textStudentCountANO"># of students</label>
			       <input type="number" required class="form-control" id="textStudentCountANO" placeholder="e.g. 100">
			    </div>
			    <div class="col-md-6 form-group">
			       <label for="textSectionNumberANO">Starting Section number</label>
			       <input type="number" required class="form-control" id="textSectionNumberANO" placeholder="e.g. 19(no need to put S19)">
			    </div>
			     <div class="col-md-6 form-group">
		          <a class="btn btn-default" type="button" onclick="importOfferingsFromDBANO()" id="importOfferingsFromDBANOButton"><span><i class="fa fa-reply"></i></span> Import</a>
			    </div> 
		    </div>	-->		    
		  </div>
		</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="exitSetNewOfferingsModal()">Close</button>
        <button type="button" class="btn btn-success" data-dismiss="modal" onclick="initAddNewOfferingsModal()">Add New Offerings</button>
      </div>
      
    </div>
  </div>
</div>

<!-- Screen may move to the right or left after modal open up. Explanation: This is because your scrollbar disappears to prevent user scrolling on the main window -->
<div class="modal fade" id="addNewOfferingsModal" role="dialog" >
  <div class="modal-dialog modal-xl">
    <div class="modal-content">
      
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="exitAddNewOfferingsModal()"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="addNewOfferingsModalTitle">Add New Offerings</h4>
      </div>
      
      	<div class="col-md-12">
      	<br>
      	
      	<!-- SEARCH FOR COURSES PANEL -->
      	<div class="modal-body col-md-12" id="divANOSearchCoursesPanel">
	      	<div class="modal-body col-md-7" id="divModalANOInfoandSearch">
			      				      
			      <div class="modal-body col-md-12" id="searchFlowchartModalBody">
			      	<div class ="col-md-12">
				        <div class="col-md-3 form-group">
				          <label for="flowchartDegreeProgramANODropdown">Degree Program</label>
				            <select class="form-control" id="flowchartDegreeProgramANODropdown" onChange="">
							    <option selected>All</option>
							</select>
			       		</div>
			       		<div class="col-md-2 form-group">
				          <label for="flowchartBatchANODropdown">Batch</label>
				            <select class="form-control" id="flowchartBatchANODropdown" onChange="">
							    <option selected>All</option>
							</select>
			       		</div>
			       		<div class="col-md-3 form-group">
				          <label for="flowchartAYANODropdown">Academic Year</label>
				            <select class="form-control" id="flowchartAYANODropdown" onChange="">
							    <option selected>All</option>
							</select>
			       		</div>
			       		<div class="col-md-3 form-group">
				          <label for="termANORadioButtons">Term</label>
				          <form id="termFormANO">
						    <label class="radio-inline"><input type="radio" checked name="termRadio" id="radio1ANO" value="1">1</label>
						    <label class="radio-inline"><input type="radio" name="termRadio" id="radio2ANO" value="2">2</label>
						    <label class="radio-inline"><input type="radio" name="termRadio" id="radio3ANO" value="3">3</label>
						  </form>
			       		</div>
			       		<div class="col-md-1 form-group">
		       				<br><a class="btn btn-default" type="button" onclick="updateAddNewOfferingsFlowchartCourseList()" id="searchFlowchartANOButton"><span><i class="fa fa-search"></i></span></a>
			       		</div>
		       		</div>
		       		<div class ="col-md-12">
			       		<div class="col-md-3 form-group">
		       				<br><a class="btn btn-default" type="button" onclick="addAllTempOfferings()" id="addAllFlowchartANOButton">Add All</a>
			       		</div>
			       		<div class="col-md-3 form-group">
		       				<br><a class="btn btn-default" type="button" onclick="removeAllTempOfferings()" id="removeAllFlowchartANOButton">Remove All</a>
			       		</div>
		       		</div>
		       		<div class="row col-md-12" id="divModalANOFlowchartCourses">
					      	<table id="tableModalANOFlowchartCoursesList" class="table table-striped table-hover header-fixed  header-fixed-flowchartcourseslistANOmodal">
					      	<thead id="tableModalANOFlowchartCoursesListHeaders">
						         <tr>
						           <th>Course</th>
						           <th>Name</th> 
						           <th>Add</th>
						         </tr>
					         </thead>
					         <tbody>
					         </tbody>
					      </table>
					 </div>
			      </div>
		      </div>
	      
		      <div class="modal-body col-md-5" id="searchCourseModalBody">
		      	<div class ="col-md-12">
			        <div class="col-md-12 form-group">
			          <label for="searchCourseANO">Search Course</label>
			            <input type="text" class="form-control" id="searchCourseANO" placeholder="Type something...">
		       		</div>
		       		<div class="col-md-12 form-group">
			          <div class="row" id="divModalANOCourses">
				      	<table id="tableModalANOCoursesList" class="table table-striped table-hover header-fixed  header-fixed-courseslistANOmodal">
				      	<thead id="tableModalANOCoursesListHeaders">
					         <tr>
					           <th>Course</th>
					           <th>Name</th> <!-- to force headers to have same width -->
					           <th>Add</th>
					         </tr>
				         </thead>
				         <tbody>
				         </tbody>
				      </table>
				      </div>
		       		</div>
	       		</div>
		      </div>
		  </div>
		  <!-- SEARCH FOR COURSES PANEL -->
		  <!-- ROOM ASSIGNMENT PANEL -->
		  <div class="col-md-12" id="divANORoomAssignmentPanel">
		    	<div class="modal-body" id="divANORoomAssignmentModalBody">
		      
		      	<div class="row col-md-12 form-group">
		          <a class="btn btn-default" type="button" onclick="changeANOPanel('divANOSearchCoursesPanel', 'divANORoomAssignmentPanel')" id="goBackToCourseListANOButton"><span><i class="fa fa-reply"></i></span> Go Back To Course List</a>
	       		</div>
		        <div class="col-md-4 form-group">
		        	<label for="searchRoom">Search Room</label>
		          	<input type="text" class="form-control" id="divANOSearchRoomRA" placeholder="Search Room...">
		        </div>
		        <div class="col-md-3 form-group">
		          <label for="roomType">Room Type</label>
		            <select class="form-control" id="divANORoomTypeRADropdown" onChange="">
					    <option selected>All</option>
					</select>
	       		</div>
		        <div class="col-md-5 form-group">
		          <label for="roomType">Building</label>
		            <select class="form-control" id="divANOBuildingRADropdown" onChange="">
					    <option selected>All</option>
					</select>
	       		</div>
		      </div>
		      
		      <div class="row" id="divANOModalRARooms">
		      	<table id="tableANOModalRARoomList" class="table table-striped table-hover header-fixed  header-fixed-ANOroomlistRAmodal">
		      	<thead id="tableANOModalRARoomListHeaders">
			         <tr>
			           <th>Room</th>
			           <th>Room Type</th>
			           <th>Building</th>
			           <th>Capacity</th>
			           <th>Assign</th>
			         </tr>
		         </thead>
		         <tbody>
		         </tbody>
		      </table>
		      </div>
		    </div>
		  <!-- ROOM ASSIGNMENT PANEL -->
		  
	      <div class="row col-md-12" id="divModalANOOfferings">
	      	<table id="tableModalANOOfferingList" class="table table-striped table-hover header-fixed  header-fixed-offerringlistANOmodal">
	      	<thead id="tableModalANOOfferingListHeaders">
		         <tr>
		           <th>Degree<br>Program</th>
		           <th> <br>Batch</th>
		           <th> <br>Course</th>
		           <th> <br>Section</th> <!-- to force headers to have same width -->
		           <th> <br>Timeslot</th>
		           <th> <br>Room</th>
		           <th> <br>Status</th>
		           <th> <br>Remarks</th>
		           <th>Edit/<br>Delete</th>
		         </tr>
	         </thead>
	         <tbody>
	         </tbody>
	      </table>
	      </div>
	    </div>
      
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="exitAddNewOfferingsModal()">Close</button>
        <button type="button" class="btn btn-success" data-dismiss="modal" onclick="addEditedOfferingsToDB()" id="saveEditedOfferingsButtonANO">Save Edited Offerings</button>
        <button type="button" class="btn btn-success" data-dismiss="modal" onclick="addNewOfferingsToDB()" id="saveNewOfferingsButtonANO">Save New Offerings</button>
      </div>
      
    </div>
  </div>
</div>

<!-- **********************************ERROR NO SCHEDULE MODAL********************************* -->
<div class="modal fade" id="errorNoScheduleModal" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="errorNoScheduleModalTitle">No Schedule Error</h4>
            </div>

            <!--  THERE SHOULD ONLY BE ONE MODAL BODY OTHERWISE DI GAGANA LAHAT(YOU CANT TYPE); -->
            <div class="modal-body" id="errorNoScheduleModalBody">
                <div class="row">
                	<div class="col-md-12">
                   		<label id="deleteClickedOfferingMessage">ERROR! Please set a schedule(complete with day and time) first before assigning a room.</label>
                   	</div>
                </div>
            </div>

            <div class="row">
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default okay-button" data-dismiss="modal">Okay</button>
            </div>

        </div>
    </div>
</div>
<!-- **********************************ERROR NO SCHEDULE MODAL********************************* -->
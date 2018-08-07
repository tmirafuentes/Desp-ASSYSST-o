<form id="">
    <input type="hidden" class="facultyAssignmentOfferingIDDump" name="facultyAssignmentOfferingIDDump" id="facultyAssignmentOfferingIDDump" value="">
    <input type="hidden" class="facultyAssignmentCurrentAYDump" name="facultyAssignmentCurrentAYDump" id="facultyAssignmentCurrentAYDump" value="">
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
    <input type="hidden" class="assignFAFacultyOfferingIDDump" name="assignFAFacultyOfferingIDDump" id="assignFAFacultyOfferingIDDump" value="">
    <input type="hidden" class="assignFAFacultyFacultyIDDump" name="assignFAFacultyFacultyIDDump" id="assignFAFacultyFacultyIDDump" value="">
    <input type="hidden" class="assignFAFacultyCurrentAYDump" name="assignFAFacultyCurrentAYDump" id="assignFAFacultyCurrentAYDump" value="">
</form>

<!-- Screen may move to the right or left after modal open up. Explanation: This is because your scrollbar disappears to prevent user scrolling on the main window -->
<div class="modal fade" id="facultyAssignmentModal" role="dialog" >
  <div class="modal-dialog modal-xl">
    <div class="modal-content">
      
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="exitFacultyAssignmentModal()"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="facultyAssignmentModalTitle">Faculty Assignment</h4>
      </div>
      
      	<div class="col-md-12">
      	<br>
      	
		
		  <div class="col-md-12" id="divANOFacultyAssignmentPanel">
		  <div class="col-md-12">
		    	<div class="modal-body" id="divANOFacultyAssignmentModalBody">
		      
		        <div class="col-md-4 form-group">
				 	<label for="searchFacultyFA">Search Faculty</label>
				   	<input type="text" class="form-control" id="searchFacultyFA" placeholder="Search Faculty...">
				</div>
				<div class="col-md-4 form-group">
				 	<label for="searchCourseFA">Search Course</label>
				   	<input type="text" class="form-control" id="searchCourseFA" placeholder="Search Course...">
				</div>
				<div class="col-md-4 form-group">
					<label for="searchFaculty">See Recommended Faculty</label>
				 	<a class="btn btn-default faculty-assign-button" type="button" onclick="clearAndRecommendFacultyList()" id="recommendFacultyButton"><span><i class="fa fa-users"></i></span> Click To View Recommended Faculty</a>
				</div>
		        <div class="col-md-4 form-group">
		          <label for="divFAFacultyTypeDropdown">Faculty Type</label>
		            <select class="form-control" id="facultyTypeFADropdown" onChange="">
					    <option selected>All</option>
					</select>
	       		</div> 
	       		<div class="col-md-4 form-group">
		          <label for="divFACollegeDropdown">College</label>
		            <select class="form-control" id="collegeFADropdown" onChange="">
					    <option selected>All</option>
					</select>
	       		</div> 
	       		<div class="col-md-4 form-group">
		          <label for="divFADepartmentDropdown">Department</label>
		            <select class="form-control" id="departmentFADropdown" onChange="">
					    <option selected>All</option>
					</select>
	       		</div> 
		      </div>
		      
		      <div class="row" id="divANOModalFAFaculty">
		      	<table id="tableModalFAFacultyList" class="table table-striped table-hover header-fixed  header-fixed-facultylistFAmodal">
		      	<thead id="tableModalFAFacultyListHeaders">
			         <tr>
			           <th>Faculty Name</th>
			           <th>Faculty Type</th>
			           <th>Current Load</th>
			           <th>Admin Load</th>
			           <th>Research Load</th>
			           <th>Teaching Load</th>
			           <th>Preparations</th>
			           <th>Assign</th>
			         </tr>
		         </thead>
		         <tbody>
		         </tbody>
		      </table>
		      </div>
		      </div>
		    </div>
		  <!-- FACULTY ASSIGNMENT PANEL -->
		  
	      <div class="row col-md-12" id="divModalFAOfferings">
	      	<table id="tableModalFAOfferingList" class="table table-striped table-hover header-fixed  header-fixed-offerringlistFAmodal">
	      	<thead id="tableModalFAOfferingListHeaders">
		         <tr>
		           <th>Degree<br>Program</th>
		           <th> <br>Course</th>
		           <th> <br>Section</th> <!-- to force headers to have same width -->
		           <th> <br>Batch</th> 
		           <th> <br>Day</th>
		           <th>Begin<br>Time</th>
		           <th>End<br>Time</th>
		           <th> <br>Room</th>
		           <th> <br>Status</th>
		           <th> <br>Remarks</th>
		           <th> <br>Faculty</th>
		           
		         </tr>
	         </thead>
	         <tbody>
	         </tbody>
	      </table>
	      </div>
	    </div>
      
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="exitFacultyAssignmentModal()">Close</button>
        <button type="button" class="btn btn-success" data-dismiss="modal" onclick="exitFacultyAssignmentModal()" id="saveNewFacultyAssignmebt">Save Faculty Assignment</button>
      </div>
      
    </div>
  </div>
</div>

<!-- **********************************ERROR RULE BROKEN MODAL********************************* -->
<div class="modal fade" id="errorRuleBrokenModal" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="errorRuleBrokenModalTitle">No Schedule Error</h4>
            </div>

            <!--  THERE SHOULD ONLY BE ONE MODAL BODY OTHERWISE DI GAGANA LAHAT(YOU CANT TYPE); -->
            <div class="modal-body" id="errorRuleBrokenModalBody">
                <div class="row">
                	<div class="col-md-12">
                   		<label id="errorRuleBrokenMessage">ERROR! Please set a schedule(complete with day and time) first before assigning a room.</label>
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
<!-- **********************************ERROR RULE BROKEN MODAL********************************* -->

<!-- **********************************CONFIRM RULE BROKEN MODAL********************************* -->
<div class="modal fade" id="confirmRuleBrokenModal" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="confirmRuleBrokenModalTitle">No Schedule Error</h4>
            </div>

            <!--  THERE SHOULD ONLY BE ONE MODAL BODY OTHERWISE DI GAGANA LAHAT(YOU CANT TYPE); -->
            <div class="modal-body" id="confirmRuleBrokenModalBody">
                <div class="row">
                	<div class="col-md-12">
                   		<label id="confirmRuleBrokenMessage">ERROR! Please set a schedule(complete with day and time) first before assigning a room.</label>
                   	</div>
                </div>
            </div>

            <div class="row">
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default unconfirm-button" data-dismiss="modal">No, I don't want to continue</button>
                <button type="button" class="btn btn-default confirm-button" data-dismiss="modal">Yes, I want to continue.</button>
            </div>

        </div>
    </div>
</div>
<!-- **********************************CONFIRM RULE BROKEN MODAL********************************* -->
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
                   		<label id="deleteClickedOfferingMessage">ERROR! Please set a schedule(complete with day and time) first before assigning a faculty.</label>
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
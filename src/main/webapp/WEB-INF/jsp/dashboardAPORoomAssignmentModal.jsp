<form id="">
    <input type="hidden" class="assignRoomOfferingIDDump" name="assignRoomOfferingIDDump" id="assignRoomOfferingIDDump" value="">
    <input type="hidden" class="assignRoomRoomIDDump" name="assignRoomRoomIDDump" id="assignRoomRoomIDDump" value="">
    <input type="hidden" class="assignRoomCurrentAYDump" name="assignRoomCurrentAYDump" id="assignRoomCurrentAYDump" value="">
</form>

<!-- Screen may move to the right or left after modal open up. Explanation: This is because your scrollbar disappears to prevent user scrolling on the main window -->
<div class="modal fade" id="roomAssignmentModal" role="dialog" >
  <div class="modal-dialog modal-xl">
    <div class="modal-content">
      
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="exitRoomAssignmentModal()"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="roomAssignmentModalTitle">Room Assignment</h4>
      </div>
      
      <div class="row" id="RAModalBody">
      	<div class="col-md-8">
	      <div class="modal-body" id="roomAssignmentModalBody">
	      
	        <div class="col-md-12 form-group">
	        	<label for="searchOffering">Search Course</label>
	          	<input type="text" class="form-control" id="searchOfferingRA" placeholder="Search Course...">
	        </div>
	        
	      </div>
	      
	      <div class="row" id="divModalRAOfferings">
	      	<table id="tableModalRAOfferingList" class="table table-striped table-hover header-fixed  header-fixed-offerringlistRAmodal">
	      	<thead id="tableModalRAOfferingListHeaders">
		         <tr>
		           <th>Degree<br>Program</th>
		           <th> <br>Course</th>
		           <th> <br>Section</th>
		           <th> <br>Day</th>
		           <th>Begin<br>Time</th>
		           <th>End<br>Time</th>
		           <th> <br>Room</th>
		         </tr>
	         </thead>
	         <tbody>
	         </tbody>
	      </table>
	      </div>
	    </div>
	    
	    <div class="col-md-4">
	    	<div class="modal-body" id="roomAssignmentModalBody">
	      
	        <div class="col-md-4 form-group">
	        	<label for="searchRoom">Search Room</label>
	          	<input type="text" class="form-control" id="searchRoomRA" placeholder="Search Room...">
	        </div>
	        <div class="col-md-8 form-group">
	          <label for="roomType">Room Type</label>
	            <select class="form-control" id="roomTypeRADropdown" onChange="">
				    <option selected>All</option>
				</select>
       		</div>
	        <div class="col-md-12 form-group">
	          <label for="roomType">Building</label>
	            <select class="form-control" id="buildingRADropdown" onChange="">
				    <option selected>All</option>
				</select>
       		</div>
	      </div>
	      
	      <div class="row" id="divModalRARooms">
	      	<table id="tableModalRARoomList" class="table table-striped table-hover header-fixed  header-fixed-roomlistRAmodal">
	      	<thead id="tableModalRARoomListHeaders">
		         <tr>
		           <th> <br>Room</th>
		           <th>Room<br>Type</th>
		           <th> <br>Building</th>
		           <th> <br>Capacity</th>
		           <th> <br>Assign</th>
		         </tr>
	         </thead>
	         <tbody>
	         </tbody>
	      </table>
	      </div>
	    </div>
      </div>
      
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="exitRoomAssignmentModal()">Close</button>
      </div>
      
    </div>
  </div>
</div>
<form id="">
    <input type="hidden" class="inputRoomTypeDump" name="inputRoomTypeDump" value="">
    <input type="hidden" class="inputRoomCapacityDump" name="inputRoomCapacityDump" value="">
</form>
<!-- Screen may move to the right or left after modal open up. Explanation: This is because your scrollbar disappears to prevent user scrolling on the main window -->
<div class="modal fade" id="viewRoomsModal" role="dialog" >
  <div class="modal-dialog">
    <div class="modal-content">
      
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="exitViewRoomsModal()"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="viewRoomsModalTitle">View Building's Rooms</h4>
      </div>
      
      <div class="modal-body" id="viewRoomsModalBody">
      
        <div class="col-md-8 form-group">
          <label for="roomType">Room Type</label>
            <select class="form-control" id="roomTypeDropdown" onChange="">
			    <option selected>All</option>
			</select>
        </div>
        
        <div class="col-md-4 form-group">
          <label for="roomCapacity">Room Capacity: </label>
            <select class="form-control" id="roomCapacityDropdown">
			    <option selected>All</option>
			</select>
        </div>
      </div>
      <div class="row" id="divModalBuildingRooms">
      	<table id="tableModalBuildingRooms" class="table table-striped table-hover header-fixed  header-fixed-viewbuildingsandroomsmodal">
      	<thead id="tableModalBuildingRoomsHeaders">
	         <tr>
	           <th>Room</th>
	           <th>Room Type</th>
	           <th>Room Capacity(max)</th>
	         </tr>
         </thead>
         <tbody>
         </tbody>
      </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="exitViewRoomsModal()">Close</button>
      </div>
      
    </div>
  </div>
</div>
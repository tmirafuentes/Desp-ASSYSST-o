<form id="">
    <input type="hidden" class="viewOfferingsOfferingIDDump" name="viewOfferingsOfferingIDDump" id="viewOfferingsOfferingIDDump" value="">
    <input type="hidden" class="viewOfferingsCurrentAYDump" name="viewOfferingsCurrentAYDump" id="viewOfferingsCurrentAYDump" value="">
</form>

<!-- Screen may move to the right or left after modal open up. Explanation: This is because your scrollbar disappears to prevent user scrolling on the main window -->
<div class="modal fade" id="viewOfferingsModal" role="dialog" >
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="exitViewOfferingsModal()"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="viewOfferingsModalTitle">View Offerings</h4>
      </div>
      
      	<div class="col-md-12">
	      <div class="modal-body" id="viewOfferingsModalBody">
	      	<div class ="col-md-12">
		        <div class="col-md-4 form-group">
		        	<label for="searchOfferingVO">Search Course</label>
		          	<input type="text" class="form-control" id="CVCsearchOfferingVO" placeholder="Search Course...">
		        </div>
		        <div class="col-md-3 form-group">
		          <label for="statusVODropdown">Status</label>
		            <select class="form-control" id="CVCstatusVODropdown" onChange="">
					    <option selected>All</option>
					</select>
	       		</div>
	       		<div class="col-md-3 form-group">
		          <label for="batchVODropdown">Batch</label>
		            <select class="form-control" id="CVCbatchVODropdown" onChange="">
					    <option selected>All</option>
					</select>
	       		</div>
       		</div>
       		<div class ="col-md-12">
       			<div class="col-md-3 form-group">
		          <label for="roomTypeVODropdown">Room Type</label>
		            <select class="form-control" id="CVCroomTypeVODropdown" onChange="">
					    <option selected>All</option>
					</select>
	       		</div>
		        <div class="col-md-3 form-group">
		          <label for="timeBlockVODropdown">Time Block</label>
		            <select class="form-control" id="CVCtimeBlockVODropdown" onChange="">
					    <option selected>All</option>
					</select>
	       		</div>
	       		<div class="col-md-6 form-group">
	       			<label for="classDayVO">Class Day </label>
	       			<br>
					<label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="M" data-off="M" id="CVCtoggleMVO" value="" checked></label>
					<label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="T" data-off="T" id="CVCtoggleTVO" value="" checked></label>
					<label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="W" data-off="W" id="CVCtoggleWVO" value="" checked></label>
					<label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="H" data-off="H" id="CVCtoggleHVO" value="" checked></label>
					<label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="F" data-off="F" id="CVCtoggleFVO" value="" checked></label>
					<label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="S" data-off="S" id="CVCtoggleSVO" value="" checked></label>
	       		</div>
       		</div>
	      </div>
	      
	      <div class="row" id="divModalVOOfferings">
	      	<table id="tableModalVOOfferingList" class="table table-striped table-hover header-fixed  header-fixed-offerringlistVOmodal">
	      	<thead id="tableModalVOOfferingListHeaders">
		         <tr>
		           <th>Degree<br>Program</th>
		           <th> <br>Course</th>
		           <th> <br>Section</th> <!-- to force headers to have same width -->
		           <th> <br>Batch</th>
		           <th> <br>Day</th>
		           <th>Begin<br>Time</th>
		           <th>End<br>Time</th>
		           <th> <br>Room</th>
		           <th> <br>Faculty</th>
		           <th> <br>Status</th>
		           <th colspan ="2"> <br>Remarks</th>
		           <th> <br>Delete</th>
		         </tr>
	         </thead>
	         <tbody>
	         </tbody>
	      </table>
	      </div>
	    </div>
      
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="exitViewOfferingsModal()">Close</button>
      </div>
      
    </div>
  </div>
</div>

<!-- **********************************DELETE OFFERING IN ADD OFFERRING MODAL********************************* -->
<div class="modal fade" id="deleteClickedOfferingModal" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="deleteClickedOfferingModalTitle">Delete</h4>
            </div>

            <!--  THERE SHOULD ONLY BE ONE MODAL BODY OTHERWISE DI GAGANA LAHAT(YOU CANT TYPE); -->
            <div class="modal-body" id="deleteClickedOfferingModalBody1">
                <div class="row">
                	<div class="col-md-12">
                   		<label id="deleteClickedOfferingMessage">Are you sure you want to delete this offering?</label>
                   	</div>
                </div>
            </div>

            <div class="row">
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-default trash-button" id="deleteClickedButton" data-dismiss="modal" onclick="deleteClickedOffering()"><span><i class="fa fa-trash"></i></span> Delete</button>
            </div>

        </div>
    </div>
</div>
<!-- **********************************DELETE OFFERING IN ADD OFFERRING MODAL********************************* -->
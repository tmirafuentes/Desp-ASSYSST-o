<form id="">
    <input type="hidden" class="viewFacultyLoadOfferingIDDump" name="viewFacultyLoadOfferingIDDump" id="viewFacultyLoadOfferingIDDump" value="">
    <input type="hidden" class="viewFacultyLoadCurrentAYDump" name="viewFacultyLoadCurrentAYDump" id="viewFacultyLoadCurrentAYDump" value="">
</form>

<!-- Screen may move to the right or left after modal open up. Explanation: This is because your scrollbar disappears to prevent user scrolling on the main window -->
<div class="modal fade" id="viewFacultyLoadGraphModal" role="dialog" >
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="viewFacultyLoadModalTitle">Professor's Load for AY 2012-2013 Term 3</h4>
      </div>
      
      	<div class="col-md-12">
	      
	      <div class="row" id="divModalViewFacultyLoad">
	      	<table id="tableModalViewFacultyLoad" class="table table-striped table-hover header-fixed  header-fixed-viewfacultyloadmodal">
	      	<thead id="tableModalViewFacultyLoadHeaders">
		         <tr>
		           <th align="center">Time</th>
		           <th align="center">Monday</th>
		           <th align="center">Tuesday</th> <!-- to force headers to have same width -->
		           <th align="center">Wednesday</th>
		           <th align="center">Thursday</th>
		           <th align="center">Friday</th>
		           <th align="center">Saturday</th>
		         </tr>
	         </thead>
	         <tbody>
	         </tbody>
	      </table>
	      </div>
	    </div>
      
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
      
    </div>
  </div>
</div>

<!-----------------------------------------------GRAPH ABOVE. TABLE BELOW------------------------------------------------------>
<form id="">
    <input type="hidden" class="viewFacultyLoadOfferingIDDump" name="viewFacultyLoadOfferingIDDump" id="viewFacultyLoadOfferingIDDump" value="">
    <input type="hidden" class="viewFacultyLoadCurrentAYDump" name="viewFacultyLoadCurrentAYDump" id="viewFacultyLoadCurrentAYDump" value="">
</form>

<!-- Screen may move to the right or left after modal open up. Explanation: This is because your scrollbar disappears to prevent user scrolling on the main window -->
<div class="modal fade" id="viewFacultyLoadTableModal" role="dialog" >
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="viewFacultyLoad2ModalTitle">Professor's Load for AY 2012-2013 Term 3</h4>
      </div>
      
      	<div class="col-md-12">
	      
	      <div class="row" id="divModalViewFacultyLoad2">
	      	<table id="tableModalViewFacultyLoad2" class="table table-striped table-hover header-fixed  header-fixed-viewfacultyload2modal">
	      	<thead id="tableModalViewFacultyLoad2Headers">
		         <tr>
		           <th align="center">Course</th>
		           <th align="center">Section</th> <!-- to force headers to have same width -->
		           <th align="center">Class Day</th>
		           <th align="center">Begin Time</th>
		           <th align="center">End Time</th>
		           <th align="center">Room</th>
		           <th align="center">Status</th>
		           <th align="center">Remarks</th>
		           <th align="center">Delete</th>
		         </tr>
	         </thead>
	         <tbody>
	         </tbody>
	      </table>
	      </div>
	    </div>
      
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="updateVFAFacultyList()">Close</button>
      </div>
      
    </div>
  </div>
</div>

<!-- **********************************DELETE LOAD MODAL********************************* -->
<div class="modal fade" id="unassignClickedLoadModal" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="unassignClickedLoadModalTitle">Delete</h4>
            </div>

            <!--  THERE SHOULD ONLY BE ONE MODAL BODY OTHERWISE DI GAGANA LAHAT(YOU CANT TYPE); -->
            <div class="modal-body" id="unassignClickedLoadModalBody1">
                <div class="row">
                	<div class="col-md-12">
                   		<label id="unassignClickedLoadMessage">Are you sure you want to delete this offering?</label>
                   	</div>
                </div>
            </div>

            <div class="row">
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-default trash-button" id="deleteClickedButton" data-dismiss="modal" onclick="unassignClickedLoad()"><span><i class="fa fa-trash"></i></span> Delete</button>
            </div>

        </div>
    </div>
</div>
<!-- **********************************DELETE LOAD MODAL********************************* -->
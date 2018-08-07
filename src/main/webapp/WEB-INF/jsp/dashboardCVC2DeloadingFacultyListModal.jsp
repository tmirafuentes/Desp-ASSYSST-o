
<!-- <label style="display:none" id="assignFacultyFacultyIdValue" value="">  </label>  -->



<!-- Screen may move to the right or left after modal open up. Explanation: This is because your scrollbar disappears to prevent user scrolling on the main window -->
<div class="modal fade" id="deloadingFacultyListModal" role="dialog" >
  <div class="modal-dialog-lg">
    <div class="modal-content">
      
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="updateFacultyLoadListInDeloading()"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="deloadingFacultyListModalTitle">Deloading - Faculty List</h4>
      </div>

			<div class="modal-body" id="facultyListDeloadingModalBody">
			
			<div class="col-md-7 form-group">
	        	<label for="searchFaculty">Search Faculty</label>
	          	<input type="text" class="form-control" id="searchFacultyViaDeloading" placeholder="Search Faculty...">
	       	    </div>
				<table id="tableFacultyLoadViaDeloading" class="table table-striped table-hover header-fixed header-fixed-deloadingfacultylisttable">  
					<thead id="tableFacultyLoadHeaders">
						<tr>
							<th>Name</th>
							<th>Faculty Type</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table> 

				
				

			</div>
			
				<div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" onclick="updateFacultyLoadListInDeloading()">Close</button>
     	 </div>



			
      
    </div>
  </div>
</div>
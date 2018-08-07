
<!-- <label style="display:none" id="assignFacultyFacultyIdValue" value="">  </label>  -->



<!-- Screen may move to the right or left after modal open up. Explanation: This is because your scrollbar disappears to prevent user scrolling on the main window -->
<div class="modal fade" id="deloadingModal" role="dialog" >
  <div class="modal-dialog-lg">
    <div class="modal-content">
      
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="updateFacultyLoadListInDeloading()"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="deloadingModalTitle">Faculty Deloading</h4>
      </div>

			<div class="modal-body" id="facultyDeloadingModalBody">
			
			<!--  Length of effectivity of the deloading -->
			<label class="control-label col-sm-1" for="lblDeloadingStartYear">Start Year: </label>	
				<div class="col-sm-1">
					<input type="text" class="form-control filledOut" id="deloadingStartYear"  placeholder=" " required/>
				</div>
		
		
		
			<label class="control-label col-sm-1" for="lblDeloadingStartYear">End Year: </label>	
				<div class="col-sm-1">
					<input type="text" class="form-control filledOut" id="deloadingEndYear"  placeholder=" " required/>
				</div>
			
     	
     	
     	<!-- Deloading term radio buttons -->
     	<div class="col-md-6 form-group">
     	<label class="control-label col-sm-4" for="lblDeloadingStartYear">Valid for Term/s: </label> 
     	
		<label class="container">Term 1
  			<input id ="deloadingTerm1" type="checkbox" value = "1">
		</label>
		
		<label class="container">Term 2
  			<input id ="deloadingTerm2" type="checkbox" value = "2">
		</label>
		
		<label class="container">Term 3
  			<input id ="deloadingTerm3" type="checkbox" value = "3">
		</label>
		</div>
		
		
		<br> <br>

				<div class="col-md-3 form-group">
					<label for="AdminLoadDropDown">Admin Load</label>
					<select class="form-control" id="AdminLoadDropDown">
						<option selected>Select</option>
					</select>
					<br>
					<button type="button" class="btn btn-default" onclick="adminDeloading()">Deload
						Faculty</button>
				</div>
				



				<div class="col-md-3 form-group">
					<label for="ResearchLoadDropDown">Research Load</label> 
					<select class="form-control" id="ResearchLoadDropDown">
						<option selected>All</option>
					</select>
					<br>
					<button type="button" class="btn btn-default" onclick="researchDeloading()">Deload
						Faculty</button>
				</div>
				
				<div class="col-md-3 form-group">
					<label for="LeaveLoadDropDown">Leave Load</label> 
					<select class="form-control" id="LeaveLoadDropDown">
						<option selected>All</option>
					</select>
					<br>
					<button type="button" class="btn btn-default" onclick="leaveDeloading()">Deload
						Faculty</button>
				</div>
				
				<div class="col-md-3 form-group">
					<label for="OtherLoadDropDown">Other Load</label> 
					<select class="form-control" id="OtherLoadDropDown">
						<option selected>All</option>
					</select>
					<br>
					<button type="button" class="btn btn-default" onclick="otherDeloading()">Deload
						Faculty</button>
				</div>
				
				
				
				<br>
				
				<table id="tableDeloading" class="table table-striped table-hover header-fixed header-fixed-deloadingview">  
					<thead id="tableDeloadingHeaders">
						<tr>
							<th>Deloading Code</th>
							<th>Deloading Type</th>
							<th>Deloading Units</th>
							<th>Start Year</th>
							<th>End Year </th>
							<th>Term </th>
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
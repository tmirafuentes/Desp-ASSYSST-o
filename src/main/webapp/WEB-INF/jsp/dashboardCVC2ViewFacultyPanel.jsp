
<form id="" action="wiewFacultyCVCForm" method="GET">
    <input type="hidden" class="inputDumpID" name="inputDumpID" value="">
    <input type="hidden" class="viewFacultyCVCPanelAYDump" name="viewFacultyCVCPanelAYDump" value="" id="viewFacultyFAPanelAYDump">
</form>

<div class="col-md-9 inactivePanel" id="panelViewFacultyCVC">
<div id="subcontainer">
  <div class="panel panel-default">
    <div class="panel-heading clickedPanel-color">
      <h3 class="panel-title"><span><i class="fa fa-calendar-o"></i></span> View Faculty Load Information</h3>
    </div>
    <div class="panel-body">
    	<div class="row">
			<div class="col-md-3 form-group">
				<label for="loadAYDropdownCVC">Academic Year</label> 
				<select	class="form-control" id="loadAYDropdownCVC">
					<option selected>All</option>
				</select>
			</div>
			<div class="col-md-3 form-group">
			</div>
			<div class="col-md-3 form-group">
			</div>
			<div class="col-md-3 form-group">
			</div>
		</div>
      <br>
      <table id="tableViewFacultyCVC" class="table table-striped table-hover header-fixed header-fixed-viewfacultycvc">
      	<thead id="tableViewFacultyCVCHeaders">
	         <tr>
	           <th>Name</th>
	           <th>Total Load</th>
	           <th>Deloading</th>
	           <th>View Loads</th><!-- put space para di masira yung header na fixed -->
	         </tr>
         </thead>
         <tbody>
         </tbody>
      </table>
    </div>
    </div>
</div>
</div>

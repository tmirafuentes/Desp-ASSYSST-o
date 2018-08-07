<form id="" action="wiewOfferingsForm" method="GET">
    <input type="hidden" class="inputDumpID" name="inputDumpID" value="">
    <input type="hidden" class="flowchartsPanelAYDump" name="flowchartsPanelAYDump" value="" id="viewOfferingsPanelAYDump">
</form>

<div class="col-md-9 inactivePanel" id="panelFlowcharts">
<div id="subcontainer">
  	<div class="panel panel-default">
    <div class="panel-heading clickedPanel-color">
      <h3 class="panel-title"><span><i class="fa fa-calendar-o"></i></span> Flowcharts</h3>
    </div>
	    <div class="panel-body">
	    	<div class="row">
	    		<div class="col-md-12">
	    			<div class="col-md-3">
	    				<button class="btn btn-default" data-toggle="modal" data-target="#uploadFlowchartModal" data-backdrop="static" data-keyboard="false">Upload Flowchart</button>
	    			</div>
	    		</div>	    	
	    	</div>
	    	<br>
	      	<div class="row">
	          <!-- <div class="col-md-4">
	               <a class="btn btn-default" type="button" onclick="" data-toggle="modal" data-target="#setNewOfferingsModal" data-backdrop="static" data-keyboard="false" id="addNewOfferingsButton"><span><i class="fa fa-calendar-plus-o"></i></span> Add New Offerings</a>
	          </div> -->
	          <div class ="col-md-12">
		        <div class="col-md-3 form-group">
		          <label for="flowchartPanelDegreeProgramDropdown">Degree Program</label>
		            <select class="form-control" id="flowchartPanelDegreeProgramDropdown" onChange="">
					    <option selected>All</option>
					</select>
	       		</div>
	       		<div class="col-md-2 form-group">
		          <label for="flowchartPanelBatchDropdown">Batch</label>
		            <select class="form-control" id="flowchartPanelBatchDropdown" onChange="">
					    <option selected>All</option>
					</select>
	       		</div>
	       		<div class="col-md-3 form-group">
		          <label for="flowchartPanelAYDropdown">Academic Year</label>
		            <select class="form-control" id="flowchartPanelAYDropdown" onChange="">
					    <option selected>All</option>
					</select>
	       		</div>
	       		<div class="col-md-3 form-group">
		          <label for="flowchartPanelTermRadioButtons">Term</label>
		          <form id="flowchartPanelTermRadioButtons">
				    <label class="radio-inline"><input type="radio" checked name="flowchartTerm" id="flowchartPanelRadio1" value="1">1</label>
				    <label class="radio-inline"><input type="radio" name="flowchartTerm" id="flowchartPanelRadio2" value="2">2</label>
				    <label class="radio-inline"><input type="radio" name="flowchartTerm" id="flowchartPanelRadio3" value="3">3</label>
				  </form>
	       		</div>
	       		<div class="col-md-1 form-group">
       				<br><a class="btn btn-default" type="button" onclick="updateViewFlowchartTable()" id="viewFlowchartButton"><span><i class="fa fa-search"></i></span></a>
	       		</div>
       		</div>
	      </div>  
	      <br>
	      <table id="tableViewFlowchart" class="table table-striped table-hover header-fixed header-fixed-viewflowchart">
	      	<thead id="tableViewFlowchartHeaders">
		         <tr>
		           <th>Course Code</th>
		           <th>Course Name</th>
		           <th>Units</th>
		           <th>College</th>
		           <th>Department</th>
		         </tr>
	         </thead>
	         <tbody>
	         </tbody>
	      </table>
	    </div>
    </div>
</div>
</div>
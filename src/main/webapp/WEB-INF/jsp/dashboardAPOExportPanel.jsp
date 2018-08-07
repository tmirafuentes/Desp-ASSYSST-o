<div class="col-md-9 inactivePanel" id="panelExport">
	<div id="subcontainer">
	  	<div class="panel panel-default">
		    <div class="panel-heading clickedPanel-color">
		      <h3 class="panel-title"><span><i class="fa fa-calendar-o"></i></span> Export Offerings</h3>
		    </div>
		    <div class="panel-body">
		    	<div class="col-md-3 form-group">
		          <label for="exportPanelAYDropdown">Academic Year</label>
		            <select class="form-control" id="exportPanelAYDropdown" onChange="">
					    <option selected>All</option>
					</select>
	       		</div>
	       		<div class="col-md-3 form-group">
		          	<label for="exportPanelTermRadioButtons">Term</label>
		          	<form id="exportPanelTermRadioButtons">
				    	<label class="radio-inline"><input type="radio" checked name="flowchartTerm" id="flowchartPanelRadio1" value="1">1</label>
				    	<label class="radio-inline"><input type="radio" name="flowchartTerm" id="flowchartPanelRadio2" value="2">2</label>
				    	<label class="radio-inline"><input type="radio" name="flowchartTerm" id="flowchartPanelRadio3" value="3">3</label>
					</form>
	       		</div>
		    	<div class="col-md-3 form-group">
		    		<button type="button" id="exportBtn" onclick="exportOffering()" class="btn btn-default">Export Offerings</button>
		    	</div>
		    </div>
	    </div>
	</div>
</div>


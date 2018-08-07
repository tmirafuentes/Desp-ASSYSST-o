<form id="" action="wiewDeloadingsCVCForm" method="GET">
    <input type="hidden" class="inputDumpID" name="inputDumpID" value="">
    <input type="hidden" class="viewDeloadingsCVCPanelAYDump" name="viewDeloadingsCVCPanelAYDump" value="" id="viewDeloadingsPanelAYDump">
</form>

<div class="col-md-9 inactivePanel" id="panelViewDeloadingsCVC">
<div id="subcontainer">
  <div class="panel panel-default">
    <div class="panel-heading clickedPanel-color">
      <h3 class="panel-title"><span><i class="fa fa-calendar-o"></i></span> View Offerings</h3>
    </div>
    <div class="panel-body">
     <div class="row">
           <div class="col-md-4">
                <a class="btn btn-default" type="button" onclick="" data-toggle="modal" data-target="#setNewDeloadingsModal" data-backdrop="static" data-keyboard="false" id="addNewDeloadingsButton"><span><i class="fa fa-calendar-plus-o"></i></span> Add New Deloadings</a>
           </div>
      </div>  
      <br>
      <table id="tableDeloadingsCVC" class="table table-striped table-hover header-fixed header-fixed-deloadingscvc">
      	<thead id="tableDeloadingsCVCHeaders">
	         <tr>
	           <th>AY</th>
	           <th>Term</th>
	           <th>View - Edit - Delete</th><!-- put space para di masira yung header na fixed -->
	         </tr>
         </thead>
         <tbody>
         </tbody>
      </table>
    </div>
    </div>
</div>
</div>

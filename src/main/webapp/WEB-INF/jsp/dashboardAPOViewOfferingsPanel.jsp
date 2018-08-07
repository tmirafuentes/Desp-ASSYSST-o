<form id="" action="wiewOfferingsForm" method="GET">
    <input type="hidden" class="inputDumpID" name="inputDumpID" value="">
    <input type="hidden" class="viewOfferingsPanelAYDump" name="viewOfferingsPanelAYDump" value="" id="viewOfferingsPanelAYDump">
</form>

<div class="col-md-9 inactivePanel" id="panelViewOfferings">
<div id="subcontainer">
  <div class="panel panel-default">
    <div class="panel-heading clickedPanel-color">
      <h3 class="panel-title"><span><i class="fa fa-calendar-o"></i></span> View Offerings</h3>
    </div>
    <div class="panel-body">
     <div class="row">
           <div class="col-md-4">
                <a class="btn btn-default" type="button" onclick="" data-toggle="modal" data-target="#setNewOfferingsModal" data-backdrop="static" data-keyboard="false" id="addNewOfferingsButton"><span><i class="fa fa-calendar-plus-o"></i></span> Add New Offerings</a>
           </div>
      </div>  
      <br>
      <table id="tableViewOfferings" class="table table-striped table-hover header-fixed header-fixed-viewofferings">
      	<thead id="tableViewOfferingsHeaders">
	         <tr>
	           <th>AY</th>
	           <th>Term</th>
	           <th>Publish</th>
	           <th>View/Edit/Delete</th><!-- put space para di masira yung header na fixed -->
	         </tr>
         </thead>
         <tbody>
         </tbody>
      </table>
    </div>
    </div>
</div>
</div>

<!-- **********************************DELETE OFFERING LIST********************************* -->
<div class="modal fade" id="deleteClickedOfferingList" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="deleteClickedOfferingListTitle">Delete</h4>
            </div>

            <!--  THERE SHOULD ONLY BE ONE MODAL BODY OTHERWISE DI GAGANA LAHAT(YOU CANT TYPE); -->
            <div class="modal-body" id="deleteClickedOfferingModalBody1">
                <div class="row">
                	<div class="col-md-12">
                   		<label id="deleteClickedOfferingListMessage">Are you sure you want to delete this offering?</label>
                   	</div>
                </div>
            </div>

            <div class="row">
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-default trash-button" id="deleteClickedListButton" data-dismiss="modal" onclick="deleteOfferingList()"><span><i class="fa fa-trash"></i></span> Delete</button>
            </div>

        </div>
    </div>
</div>
<!-- **********************************DELETE OFFERING LIST********************************* -->
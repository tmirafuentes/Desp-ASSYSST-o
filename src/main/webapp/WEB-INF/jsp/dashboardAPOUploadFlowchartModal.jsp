<!-- Screen may move to the right or left after modal open up. Explanation: This is because your scrollbar disappears to prevent user scrolling on the main window -->
<div class="modal fade" id="uploadFlowchartModal" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick=""><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="viewRoomsModalTitle">Upload Flowchart</h4>
            </div>

            <div class="modal-body" id="uploadFlowchartModalBody">           
                <div class="panel-body">
                    <!-- Standard Form -->
                    <form action="uploadFlowchartFiles" method="POST" enctype="multipart/form-data" id="uploadFlowchartsForm">
						<div class="form-group">
							<div class="col-lg-6 col-sm-6 col-12">
					            <div class="input-group">
					                <label class="input-group-btn">
					                    <span class="btn btn-default">
					                        Browse&hellip; <input id="flowchartFiles" type="file" name="flowchartFiles[]" style="display:none" multiple/>
					                    </span>
					                </label>
					                <input type="text" class="form-control" id="uploadText" readonly>
					            </div>
					            <span class="help-block" id="upload">
					                
					            </span>
					        </div>
						</div>
                        
                        <div class="form-group">
                        	<button id="uploadFlowchartsBtn" class="btn btn-success" onclick="fileUpload(this.form, 'uploadFlowchartFiles', 'upload'); return false;" >Upload</button>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="">Close</button>
            </div>

        </div>
    </div>
</div>
<form id="">
    <input type="hidden" class="inputTempOfferingIDDump" id="inputTempOfferingIDDump" name="inputTempOfferingIDDump" value="">
</form>
<!-- **********************************EDIT OFFERING IN ADD OFFERRING MODAL********************************* -->
<div class="modal fade" id="editTemporaryOfferingModal" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="exitEditTemporaryOfferingModal()"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="editTemporaryOfferingModalTitle">Edit</h4>
            </div>

            <!--  THERE SHOULD ONLY BE ONE MODAL BODY OTHERWISE DI GAGANA LAHAT(YOU CANT TYPE); -->
            <div class="modal-body" id="editTemporaryOfferingModalBody1">
                <div class="row">
                    <div class="col-md-4 form-group">
                        <label for="degreeProgramETO">Degree Program</label>
                        <input type="text" required class="form-control" id="degreeProgramETO" placeholder="-">
                    </div>

                    <div class="col-md-2 form-group">
                        <label for="termETO">Term</label>
                        <input type="number" disabled class="form-control" id="termETO" placeholder="-">
                    </div>

                    <div class="col-md-2 form-group">
                        <label for="batchETO">Batch</label>
                        <input type="number" class="form-control" id="batchETO" placeholder="-">
                    </div>

                    <div class="col-md-4 form-group">
                        <label for="sectionETO">Section</label>
                        <input type="text" class="form-control" id="sectionETO" placeholder="-">
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-4 form-group">
                        <label for="statusETO">Status</label>
                        <input type="text" class="form-control" id="statusETO" placeholder="-">
                    </div>

                    <div class="col-md-8 form-group">
                        <label for="remarksETO">Remarks</label>
                        <input type="text" class="form-control" id="remarksETO" placeholder="-">
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-8 form-group">
                        <label for="facultyETO">Faculty</label>
                        <input type="text" disabled class="form-control" id="facultyETO" placeholder="No faculty">
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-8 form-group">
                        <label for="classDayVO">Day 1</label>
                        <br>
                        <label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="M" data-off="M" id="toggle1METO" value=""></label>
                        <label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="T" data-off="T" id="toggle1TETO" value=""></label>
                        <label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="W" data-off="W" id="toggle1WETO" value=""></label>
                        <label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="H" data-off="H" id="toggle1HETO" value=""></label>
                        <label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="F" data-off="F" id="toggle1FETO" value=""></label>
                        <label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="S" data-off="S" id="toggle1SETO" value=""></label>
                    </div>
                    <div class="col-md-4 form-group">
                        <label for="timeBlock1ETODropdown">Day 1 Time Block</label>
                        <select class="form-control" id="timeBlock1ETODropdown">
                            <option selected>All</option>
                        </select>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-8 form-group">
                        <label for="classDayVO">Day 2</label>
                        <br>
                        <label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="M" data-off="M" id="toggle2METO" value=""></label>
                        <label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="T" data-off="T" id="toggle2TETO" value=""></label>
                        <label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="W" data-off="W" id="toggle2WETO" value=""></label>
                        <label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="H" data-off="H" id="toggle2HETO" value=""></label>
                        <label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="F" data-off="F" id="toggle2FETO" value=""></label>
                        <label class="checkbox-inline"><input type="checkbox" data-toggle="toggle" data-size="mini" data-onstyle="success" data-style="ios" data-on="S" data-off="S" id="toggle2SETO" value=""></label>
                    </div>
                    <div class="col-md-4 form-group">
                        <label for="timeBlock2ETODropdown">Day 2 Time Block</label>
                        <select class="form-control" id="timeBlock2ETODropdown">
                            <option selected>All</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row">
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="exitEditTemporaryOfferingModal()">Close</button>
                <button type="button" class="btn btn-default save-button" id="saveTemporaryButton" data-dismiss="modal" onclick="saveTemporaryOffering()"><span><i class="fa fa-save"></i></span> Save</button>
                <button type="button" class="btn btn-default save-button" id="saveToDatabaseButton" data-dismiss="modal" onclick="saveOfferingToDatabase()"><span><i class="fa fa-database"></i></span> Save To Database</button>
            </div>

        </div>
    </div>
</div>
<!-- **********************************EDIT OFFERING IN ADD OFFERRING MODAL********************************* -->
<!-- **********************************DELETE OFFERING IN ADD OFFERRING MODAL********************************* -->
<div class="modal fade" id="deleteTemporaryOfferingModal" role="dialog" >
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="deleteTemporaryOfferingModalTitle">Delete</h4>
            </div>

            <!--  THERE SHOULD ONLY BE ONE MODAL BODY OTHERWISE DI GAGANA LAHAT(YOU CANT TYPE); -->
            <div class="modal-body" id="deleteTemporaryOfferingModalBody1">
                <div class="row">
                	<div class="col-md-12">
                   		<label id="deleteTemporaryOfferingMessage">Are you sure you want to delete this offering?</label>
                   	</div>
                </div>
            </div>

            <div class="row">
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button type="button" class="btn btn-default trash-button" id="deleteTemporaryButton" data-dismiss="modal" onclick="deleteTemporaryOffering()"><span><i class="fa fa-trash"></i></span> Delete</button>
            </div>

        </div>
    </div>
</div>
<!-- **********************************DELETE OFFERING IN ADD OFFERRING MODAL********************************* -->
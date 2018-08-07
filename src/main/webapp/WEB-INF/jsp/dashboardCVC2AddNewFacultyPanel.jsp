<div class="col-md-9 inactivePanel" id="panelAddNewFaculty">
    <div id="subcontainer">
        <div class="panel panel-default">
            <div class="panel-heading clickedPanel-color">
                <h3 class="panel-title"><span><i class="fa fa-cogs"></i></span> Add New Faculty </h3>
            </div>
            <div class="panel-body">
            	<div class="col-md-12">
                	<div class="row">
						<form class="form-horizontal" action="" id="addNewFacultyForm" onsubmit="return false;">
							<div class="form-group" id="newFacultyIDGroup">
								<label class="control-label col-sm-2" for="newFacultyID">Faculty ID: </label>
								
								<div class="col-sm-4" id="newFacultyIDHelp">
									<input type="number" class="form-control filledOut" id="newFacultyID" name="newFacultyID" placeholder="Enter Faculty Code"  pattern=".{7,7}" required/>
								</div>
							</div>
							
							<div class="form-group" id="newFacultyPasswordGroup">
								<label class="control-label col-sm-2" for="newFacultyPassword">Password: </label>
								
								<div class="col-sm-4" id="newFacultyPasswordHelp">
									<input type="password" class="form-control passwordCheck filledOut" id="newFacultyPassword" name="newFacultyPassword" placeholder="Enter Password" required/>
								</div>
							</div>
							
							<div class="form-group" id="newFacultyConfirmPasswordGroup">
								<label class="control-label col-sm-2" for="newFacultyConfirmPassword">Confirm Password: </label>
								
								<div class="col-sm-4" id="newFacultyConfirmPasswordHelp">
									<input type="password" class="form-control passwordCheck filledOut" id="newFacultyConfirmPassword" name="newFacultyConfirmPassword" placeholder="Enter Password" required/>
								</div>
							</div>
							
							<div class="form-group" id="newFacultyFirstNameGroup">
								<label class="control-label col-sm-2" for="newFacultyFirstName">First Name: </label>
								
								<div class="col-sm-4" id="newFacultyFirstNameHelp">
									<input type="text" class="form-control filledOut" id="newFacultyFirstName" name="newFacultyFirstName" placeholder="Enter First Name" required/>
								</div>
							</div>
							
							<div class="form-group" id="newFacultyMiddleNameGroup">
								<label class="control-label col-sm-2" for="newFacultyMiddleName">Middle Name: </label>
								
								<div class="col-sm-4" id="newFacultyMiddleNameHelp">
									<input type="text" class="form-control" id="newFacultyMiddleName" name="newFacultyMiddleName" placeholder="Enter Middle Name"/>
								</div>
							</div>
							
							<div class="form-group" id="newFacultyLastNameGroup">
								<label class="control-label col-sm-2" for="newFacultyLastName">Last Name: </label>
								
								<div class="col-sm-4" id="newFacultyLastNameHelp">
									<input type="text" class="form-control filledOut" id="newFacultyLastName" name="newFacultyLastName" placeholder="Enter Last Name" required/>
								</div>
							</div>
							
							<div class="form-group">
								<label class="control-label col-sm-2" for="newFacultyType">Faculty Type: </label>
								<div class="col-sm-4">
									<select class="form-control" id="newFacultyType" name="newFacultyType">
										<option value="Full-time">Full-time</option>
										<option value="Part-time">Part-time</option>
										<option value="Half-time">Half-time</option>
									</select>
								</div>
							</div>
							
							<div class="form-group" id="newFacultyYearStartedGroup">
								<label class="control-label col-sm-2" for="newFacultyYearStarted">Year Started: </label>
								
								<div class="col-sm-4" id="newFacultyYearStartedHelp">
									<input type="text" class="form-control filledOut" id="newFacultyYearStarted" name="newFacultyYearStarted" placeholder="Enter Year Started" required/>
								</div>
							</div>
							
							<br>
							<input type="hidden" id="addNewFacultyUser" name="userId">
							<div class="form-group">
								<div class="col-sm-2"></div>
								<div class="col-sm-4">
									<input type="submit" class="btn btn-success btn-lg" value="Add Faculty">
								</div>
							</div>
						</form>
	                </div>
	            </div>  
            </div>
        </div>
    </div>
</div>
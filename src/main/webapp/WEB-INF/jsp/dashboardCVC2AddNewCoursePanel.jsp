<div class="col-md-9 inactivePanel" id="panelAddNewCourse">
    <div id="subcontainer">
        <div class="panel panel-default">
            <div class="panel-heading clickedPanel-color">
                <h3 class="panel-title"><span><i class="fa fa-cogs"></i></span> Add New Course </h3>
            </div>
            <div class="panel-body">
            	<div class="col-md-12">
                	<div class="row">
						<form class="form-horizontal" action="" id="addNewCourseForm" onsubmit="return false;">
							<div class="form-group" id="newCourseCourseCodeGroup">
								<label class="control-label col-sm-2" for="newCourseCourseCode">Course Code: </label>
								
								<div class="col-sm-4" id="newCourseCourseCodeHelp">
									<input type="text" class="form-control filledOut mustSeven" id="newCourseCourseCode" name="newCourseCourseCode" placeholder="Enter Course Code" required/>
									<span class="help-block">* Must contain exactly 7 characters</span>
								</div>
							</div>
							
							<div class="form-group" id="newCourseCourseTitleGroup">
								<label class="control-label col-sm-2" for="newCourseCourseTitle">Course Title: </label>
								
								<div class="col-sm-4" id="newCourseCourseTitleHelp">
									<input type="text" class="form-control filledOut" id="newCourseCourseTitle" name="newCourseCourseTitle" placeholder="Enter Course Title" required/>
								</div>
							</div>
							
							<div class="form-group" id="newCourseCourseDescGroup">
								<label class="control-label col-sm-2" for="newCourseCourseDesc">Course Description: </label>
								
								<div class="col-sm-4" id="newCourseCourseDescHelp">
									<input type="text" class="form-control filledOut" id="newCourseCourseDesc" name="newCourseCourseDesc" placeholder="Enter Course Description" required/>
								</div>
							</div>
							
							<!--  
							
							<div class="form-group">
								<label class="control-label col-sm-2" for="newCourseEffectYear">Effectivity: </label>
								
								<div class="col-sm-4">
									<input type="number" min="1911" class="form-control" id="newCourseEffectYear" name="newCourseEffectYear" placeholder="Year" required title="Input must be numbers only" oninvalid="this.setCustomValidity('Please fill out with numbers')" oninput="setCustomValidity('')"/>
									<input type="number" min="1" max="3" class="form-control" id="newCourseEffectTerm" name="newCourseEffectTerm" placeholder="Term" required title="Input must be numbers only" oninvalid="this.setCustomValidity('Please fill out with numbers')" oninput="setCustomValidity('')"/>
								</div>
							</div>
							
							<div class="form-group">
								<label class="control-label col-sm-2" for="newCourseGrpLvl">Group Level: </label>
								<div class="col-sm-4">
									<select class="form-control" id="newCourseGrpLvl" name="newCourseGrpLvl">
										<option>Graduate</option>
										<option>Undergraduate</option>
									</select>
								</div>
							</div>
							
							<div class="form-group">
								<label class="control-label col-sm-2" for="newCourseDegLvl">Degree Level: </label>
								
								<div class="col-sm-4">
									<input type="text" class="form-control" id="newCourseDegLvl" name="newCourseDegLvl" placeholder="Enter Degree Level" required/>
								</div>
							</div>
							
							-->
							
							<div class="form-group">
								<label class="control-label col-sm-2" for="newCourseDept">Department: </label>
								<div class="col-sm-4">
									<div class="form-control" id="newCourseDept"></div>
									<input type="hidden" name="newCourseDept" id="newCourseDeptH">
								</div>
							</div>
							
							<!-- 
							
							<div class="form-group">
								<label class="control-label col-sm-2" for="newCourseCourseType">Course Type: </label>
								<div class="col-sm-4">
									<select class="form-control" id="newCourseCourseType" name="newCourseCourseType">
										<option value="Elective">Elective</option>
										<option value="Major">Major</option>
										<option value="Masters">Masters</option>
										<option value="Senior High School">Senior High School</option>
										<option value="Service Course">Service Course</option>
									</select>
								</div>
							</div>  
							
							<div class="form-group">
								<label class="control-label col-sm-2" for="newCourseStudUnits">Student Units: </label>
								
								<div class="col-sm-4">
									<input type="number" min="0" class="form-control" id="newCourseStudUnits" name="newCourseStudUnits" placeholder="Enter Student Units" required title="Input must be numbers only" oninvalid="this.setCustomValidity('Please fill out with numbers')" oninput="setCustomValidity('')"/>
								</div>
							</div>
							
							-->
							
							<div class="form-group" id="newCourseFacUnitsGroup">
								<label class="control-label col-sm-2" for="newCourseFacUnits">Faculty Units: </label>
								
								<div class="col-sm-4" id="newCourseFacUnitsHelp">
									<input type="number" min="0" step="0.5" class="form-control filledOut" id="newCourseFacUnits" name="newCourseFacUnits" placeholder="Enter Faculty Units" required title="Input must be numbers only" oninvalid="this.setCustomValidity('Please fill out with numbers')" oninput="setCustomValidity('')"/>
								</div>
							</div>
							
							<div class="form-group">
								<label class="control-label col-sm-2" for="newCoursePassGrade">Passing Grade: </label>
								
								<div class="col-sm-4">
									<select class="form-control" id="newCoursePassGrade" name="newCoursePassGrade">
										<option value="1.0">1.0</option>
										<option value="1.5">1.5</option>
										<option value="2.0">2.0</option>
										<option value="2.5">2.5</option>
										<option value="3.0">3.0</option>
										<option value="3.5">3.5</option>
									</select>
								</div>
							</div>
							
							<!--  
							
							<div class="form-group">
								<label class="control-label col-sm-2" for="newCoursePermPad">Permanent Pad: </label>

								<div class="col-sm-4">
									<input type="number" class="form-control" id="newCoursePermPad" name="newCoursePermPad" placeholder="Enter Permanent Pad" required title="Input must be numbers only" oninvalid="this.setCustomValidity('Please fill out with numbers')" oninput="setCustomValidity('')"/>
								</div>
							</div>
							
							<div class="form-group">
								<label class="control-label col-sm-2" for="newCourseNumTermDefer">No. of Terms to Defer: </label>

								<div class="col-sm-4">
									<input type="number" class="form-control" id="newCourseNumTermDefer" name="newCourseNumTermDefer" placeholder="Enter Number of Terms" required title="Input must be numbers only" oninvalid="this.setCustomValidity('Please fill out with numbers')" oninput="setCustomValidity('')"/>
								</div>
							</div>
							
							<div class="form-group">
								<label class="control-label col-sm-2" for="newCourseAllowWithdraw">Allow Withdrawal: </label>

								<div class="col-sm-1">
									<input type="checkbox" class="form-control" id="newCourseAllowWithdraw" name="newCourseAllowWithdraw" required/>
								</div>
							</div>
							
							<div class="form-group">
								<label class="control-label col-sm-2" for="newCourseAssessCode">Assess Code: </label>

								<div class="col-sm-4">
									<input type="text" class="form-control" id="newCourseAssessCode" name="newCourseAssessCode" placeholder="Enter Assess Code" required/>
								</div>
							</div>
							
							<div class="form-group">
								<label class="control-label col-sm-2" for="newCourseAssessComp">Assessment Computation: </label>

								<div class="col-sm-4">
									<input type="number" min="1" class="form-control" id="newCourseAssessComp" name="newCourseAssessComp" placeholder="Enter Assessment Computation" required title="Input must be numbers only" oninvalid="this.setCustomValidity('Please fill out with numbers')" oninput="setCustomValidity('')"/>
								</div>
							</div>
							
							-->
							
							<div class="form-group">
								<label class="control-label col-sm-2" for="newCourseReqList">Requisites: </label>

								<div class="col-sm-4" id="newCourseReqList">
									<div class="input-group">
										<div class="input-group-btn " for="newCourseReq">
											<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" id="0-btn">Pre-req <span class="caret"></span></button>
					                        <ul class="dropdown-menu newCourseReqTypeList">
					                            <li id="0-li1" onclick="changeNewCourseReqType(this.id)"><a>Pre-req</a></li>
					                            <!-- <li id="0-li2" onclick="changeNewCourseReqType(this.id)"><a>Post-req</a></li> -->
					                            <li id="0-li3" onclick="changeNewCourseReqType(this.id)"><a>Equivalent</a></li>
					                        </ul>
										</div>
										<input type="hidden" name="newCourseReqType[]" id="0-hid" value="Pre-req"/>
										<input type="text" class="form-control newCourseReqInput" id="newCourseReq" name="newCourseReq[]" placeholder="Enter Course Code"/>
										<div class="input-group-btn " for="newCourseReq">
											<button class="btn btn-default" onclick="addNewRequisiteRow()" type="button">+</button>
										</div>
									</div>
									
								</div>
							</div>
							<br>
							<input type="hidden" id="addNewCourseUser" name="userId">
							<div class="form-group">
								<div class="col-sm-2"></div>
								<div class="col-sm-4">
									<!-- <button type="button" onclick="submitAddNewCourse()" class="btn btn-success btn-lg">Add Course</button> --> 
									<!--  -->
									<input type="submit" class="btn btn-success btn-lg" value="Add Course">
								</div>
							</div>
						</form>
	                </div>  
	            </div>
            </div>
        </div>
    </div>
</div>
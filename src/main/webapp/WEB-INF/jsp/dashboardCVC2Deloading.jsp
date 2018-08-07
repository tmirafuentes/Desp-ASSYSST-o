<label style="display:none" id="FCViewStartYearValue" value=""> </label>
<label style="display:none" id="FCViewEndYearValue" value=""> </label>
<label style="display:none" id="FCViewTermValue" value=""> </label>


<div class="col-md-9 inactivePanel" id="panelViewDeloading">
	<div id="subcontainer">
		<div class="panel panel-default">
			<div class="panel-heading clickedPanel-color">
				<h3 class="panel-title">
					<span><i class="fa fa-user"></i></span> Deloading
				</h3>
			</div>
			
			<div class="panel-body">
                <div class="container">
                	<div class="row">
                	<form class="form-horizontal" action="" id="addNewFacultyForm" onsubmit="return false;">
      					<div class="form-group">
						<label class="control-label col-sm-2" for="lblDeloadingStartYear">Start Year: </label>	
							<div class="col-sm-4">
								<input type="text" class="form-control filledOut" id="deloadingStartYear" name="deloadingStartYear" placeholder="Enter Start Year..." required/>
							</div>
						</div>
						
					
						<div class="form-group">
						<label class="control-label col-sm-2" for="lblDeloadingEndYear">End Year: </label>	
							<div class="col-sm-4">
								<input type="text" class="form-control filledOut" id="deloadingEndYear" name="deloadingEndYear" placeholder="Enter End Year..." required />
							</div>
						</div>
					
					
					
						<div class="form-group">
						<label class="control-label col-sm-2" for="lblDeloadingTerm">Term: </label>	
							<div class="col-sm-4">
								<input type="text" class="form-control filledOut" id="deloadingTerm" name="deloadingTerm" placeholder="Enter Term..." required/>
							</div>
						</div>
							
				
			<div style="text-align:center">  
    			<button id="btnDeloadingAY"   onclick ="getAYDeloading()" class ="btn btn-success btn-lg"> Enter</button>
			</div>  
					
					<!-- <a class ="btn btn-default" type="button" onclick="getAYDeloading()"> Enter </a> -->
					</form>
						
						
	                </div>  
                </div>
            </div>
			
				
			</div>
		</div>
	</div>
</div>
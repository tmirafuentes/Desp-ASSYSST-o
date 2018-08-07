<div class="col-md-9 inactivePanel" id="panelAddNewDeloading"> 
	<div id="subcontainer">
		<div class="panel panel-default">
			<div class="panel-heading clickedPanel-color">
				<h3 class="panel-title">
					<span><i class="fa fa-user"></i></span> Add New Deloading
				</h3>
			</div>
			
			<div class="panel-body">
                <div class="container">
                	<div class="row">
                	<form class="form-horizontal" action="" id="addNewDeloadingForm" onsubmit="return false;">
                	
      					<div class="form-group">
						<label class="control-label col-sm-2" for="">Deloading Name: </label>	
							<div class="col-sm-4">
								<input type="text" class="form-control filledOut" id="addDeloadingName" placeholder="Enter Deloading Name..." required/>
							</div>
						</div>
						
						<div class="form-group">
						<label class="control-label col-sm-2" for="">Deloading Code: </label>	
							<div class="col-sm-4">
								<input type="text" class="form-control filledOut" id="addDeloadingCode" placeholder="Enter Deloading Code..." required/>
							</div>
						</div>
						
						<div class="form-group">
						<label class="control-label col-sm-2" for="">Type: </label>	
						<div class="col-xs-2">
     						<select class = "form-control" id="addDeloadingDropDown">
     							<option value="DL"> Admin </option>
     							<option value="RL"> Research </option>
     							<option value="LL"> Leave </option>
     							<option value="OL"> Other </option>
     						</select>
     						</div>
     					</div>
     					
     					
     					<div class="form-group">
						<label class="control-label col-sm-2" for="">Units: </label>	
							<div class="col-sm-1">
								<input type="number" min="0" step="0.5" class="form-control filledOut" id="addDeloadingUnits" placeholder="" required/>
							</div>
						</div>
												
						<div class="form-group">
						<label class="control-label col-sm-2" for="">Description: </label>	
							<div class="col-sm-5">
								<input type="text" class="form-control filledOut" id="addDeloadingDescription" placeholder="" required/>
							</div>
						</div>
				
						<div style="text-align:center">  
			    			<button id="btnDeloadingAY" class ="btn btn-success btn-lg"> Enter</button>
						</div>  
					
					<!-- <a class ="btn btn-default" type="button" onclick="getAYDeloading()"> Enter </a> -->
					</form>
						
						
	                </div>  
                </div>
            </div>
			
				
			</div>
		</div>
	</div>
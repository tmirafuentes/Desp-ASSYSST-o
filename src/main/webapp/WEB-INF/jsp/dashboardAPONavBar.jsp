<!-- NAVBAR -->
	<nav id="nav" class="navbar navbar-default">
      <div class="container">
        <div id="nav" class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="homeTraverse">ARROWSMITH</a>
        </div>
        
        <div id="navbar" class="collapse navbar-collapse">
          <!--<ul class="nav navbar-nav">-->
            <!--<li class="active" onclick="clickNavigation(this.id)" id="dashboardNav"><a href="#">Dashboard</a></li><!-- class="active" means eto yung current page-->
            <!--<li onclick="clickNavigation(this.id)" id="viewOfferingsNav"><a href="manageCourse">View Offerings</a></li>-->
            <!--<li onclick="clickNavigation(this.id)" id="addOfferingsNav"><a href="addCourse">Add New Offerings</a></li>-->
            <!--<li onclick="clickNavigation(this.id)" id="roomAssignNav"><a href="#">Room Assignment</a></li>-->
          <!--</ul>-->
          <ul class="nav navbar-nav navbar-right">
            <li onclick="clickNavigation(this)" id="profileNav"><a href=""><span><i class="fa fa-user"></i></span> ${user.firstName}</a></li>
            
             <li class="dropdown" onclick="clickNavigation(this.id)" id="dropdownNav">
		        <a class="dropdown-toggle" href="#" data-toggle="dropdown">
		        <i class="fa fa-bars" style="padding-top:3px;"> <span class="caret"></span></i> 
		        </a>
		        <ul class="dropdown-menu">
		          <li><a class="dropdown-item" href="#">Settings</a></li>
		          <li><a class="dropdown-item" href="logout">Logout</a></li>
		        </ul>
		     </li>
		    
            <!-- <li><a href="logout">Logout</a></li> -->
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
	<!-- NAVBAR -->
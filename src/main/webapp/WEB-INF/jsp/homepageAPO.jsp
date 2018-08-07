<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
    <head>
    	<META HTTP-EQUIV="refresh" CONTENT="<%= session.getMaxInactiveInterval() %>; URL=/logout">
        <title>APO Homepage</title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
		<c:url value="/css/homepageAPOStyle.css" var="jstlCss" />
		<link type="text/css" href="${jstlCss}" rel="stylesheet" >
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        
		<script>
			$(document).ready(function(){
				$(".bottomAligned").css("margin-top", ($(".wrapperCenter").height()-$(".bottomAligned").height())+"px");
			});
			
		</script>
    </head>
    <body>
        <nav class="navbar navbar-default">
		<div class="container-fluid">
	    <!-- Brand and toggle get grouped for better mobile display -->
	    <div class="navbar-header">
	        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
	            <span class="sr-only">Toggle navigation</span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	        </button>
	        <a class="navbar-brand" onclick="openNav()">Arrowsmith</a>
	    </div>
	
		    <!-- Collect the nav links, forms, and other content for toggling -->
		    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		        <ul class="nav navbar-nav navbar-right">
		            <li><a href= "homeTraverse">Home</a></li>
		            <li><a href= "#">Profile</a></li>
		            <li><a href= "logout">Logout</a>
	        </ul>
	    	</div><!-- /.navbar-collapse -->
			</div><!-- /.container-fluid -->
		</nav>
        
        <div id="sideBarNav" class="sidenav">
		  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
		  <a href="manageCourse">View Offerings</a>
		  <a href="addCourse">Add Course Offerings</a>
		  <a href="roomAssign">Room Assignment</a>
		  <a href="publishButton">Publish</a>
		</div>
        <span >open</span>
        
        <div id = "main">
        <div class = "container">            
            <div id="apo-functionalities-div" class="row">
				<div id="view-offerings-div" class="col-sm-3 well wrapperCenter">
				    <a href="manageCourse"><button id="viewOfferingButton" type="button" class="btn btn-default bottomAligned">View Offerings</button></a>
				</div>
				<div id="add-course-div" class="col-sm-3 well wrapperCenter">
				    <a href="addCourse"><button id="addCourseButton" type="button" class="btn btn-default bottomAligned">Add Course</button></a>
				</div>
				<div id="room-assignment-div" class = "col-sm-3 well wrapperCenter">
				    <a href="roomAssign"><button id="roomAssignButton" type="button" class="btn btn-default bottomAligned">Room Assignment</button></a>
				</div>
				<div id="publish-div" class = "col-sm-3 well wrapperCenter">
				    <button id="publishButton" type="button" class="btn btn-default bottomAligned">Publish</button>
				</div>
            </div>
        </div>
		</div>
		
        <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
        <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.js"></script> <!-- should be first because angularjs properties would not be loaded if this is below -->
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-route.js"></script>
        <!--<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.7/angular-resource.min.js"></script>-->

        <!-- AngularJS Controller scripts -->
        <script type="text/javascript" src="resources/javascript/loginController.js"></script>

        <jsp:include page="footer.jsp"/>
        
        <script>
        function openNav() {
            document.getElementById("sideBarNav").style.width = "250px";
            document.getElementById("container").style.marginLeft = "250px";
        }

        /* Set the width of the side navigation to 0 and the left margin of the page content to 0 */
        function closeNav() {
            document.getElementById("sideBarNav").style.width = "0";
            document.getElementById("container").style.marginLeft = "0";
        }
        </script>
    </body>
</html>
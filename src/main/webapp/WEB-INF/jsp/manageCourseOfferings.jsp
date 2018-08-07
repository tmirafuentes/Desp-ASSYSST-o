<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link href="/css/bootstrap.min.css" rel="stylesheet">

        <style>
            #dividerOnRight{
                border-right: 1px solid #bbb;
            }		
        </style>

        <title>Manage Course Offerings</title>
    </head>
    <body>	
        <jsp:include page="header.jsp"/>
        <script type="text/javascript">
            var idleTime = 0;
            var mousemovingTime = 0;

            $(document).ready(function () {
                //Increment the idle time counter every minute.
                var idleInterval = setInterval(timerIncrement, 10); // 1 minute

                //Zero the idle timer on mouse movement.
                $(this).mousemove(function (e) {
                    idleTime = 0;
                });
                $(this).keypress(function (e) {
                    idleTime = 0;
                });
            });

            function timerIncrement() {
                //Counts every 0.01s
                idleTime = idleTime + 0.01;

                //console.log(idleTime);
            }


            function printMousePos(event) {
                console.log("[P] POINT - " + mousemovingTime);
                mousemovingTime = 0;

                console.log("[B] CLICKED - clientX: " + event.clientX +
                        " - clientY: " + event.clientY + " : " + event.target + " : " + event.target.tagName + " : " + event.target.getAttribute("klmname"));
            }

            var mouseMoving = function () {
                mousemovingTime++;
                // console.log("moving");
            };

            document.onkeypress = function (evt) {
                evt = evt || window.event;
                var charCode = evt.keyCode || evt.which;
                var charStr = String.fromCharCode(charCode);

                console.log("[K] KEYSTROKE - " + charStr);
            };


            document.addEventListener("click", printMousePos);
            document.addEventListener('mousemove', mouseMoving, false);
        </script>
		
        <div class="container">
            <div class="row">
                <div class="col-sm-3" id="dividerOnRight"> 
                    <a href="#" data-toggle="modal" data-target="#squarespaceModal"><h2>Import Course</h2></a>
                </div>
                <div class="col-sm-3" id="dividerOnRight"> 
                    <a href="#" data-toggle="modal" data-target=""><h2>Modify Course</h2></a>
                </div>
                <div class="col-sm-6">
                    <a href="#"><h2>Remove Course</h2></a>
                </div>
                <div class="col-sm-3"></div>
            </div>
        </div>	
        
        <br>
        <div class="container">
            <div class="row">
                <div class="col-sm-3">
                	<form id="searchForm" action="findCourses" method="POST">
                		<input id="searchVar" name="searchVar" type="hidden">
                	</form>
                	<div class="dropdown">
						<button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">${timeframeButton } <span class="caret"></span></button>
						<ul class="dropdown-menu">
							<c:forEach items="${timeframeList }" var="timeframe">
								<li><a onclick="$('#searchVar').val('${timeframe}'); $('#searchForm').submit();"><c:out value="${timeframe }"></c:out></a></li>
							</c:forEach>
						</ul>
					</div>
                </div>
                <div class="col-sm-6"></div>
                <div class="col-sm-3"><input type="text" class="form-control" id="search" placeholder="Search" klmname = "searchForm"></div>
            </div>
            <hr>
            <div class="table-responsive">          
                <table class="table" name = "table1">
                    <thead>
                        <tr>
                            <th>Course Code</th>
                            <th>Section</th>
                            <th>Day</th>
                            <th>Time Start</th>
                            <th>Time End</th>
                            <th>Room</th>
                        </tr>
                    </thead>
                    <tbody>
                    	<c:forEach items="${offeringList }" var="offering">
                    		<c:forEach items="${offering.days }" var="day">
		                        <tr class="success">
		                            <td><a href="#"><c:out value="${offering.course.courseCode }"/></a></td>
		                            <td><c:out value="${offering.section }"/></td>
		                            <td><c:out value="${day.classDay }"/></td>
		                            <td><c:out value="${day.beginTime }"/></td>
		                            <td><c:out value="${day.endTime }"/></td>
		                            <td><c:out value="${day.room.roomCode }"/></td>
		                        </tr>
	                        </c:forEach>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="squarespaceModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
                        <h3 class="modal-title" id="lineModalLabel">Import from Flowchart</h3>
                    </div>
                    <div class="modal-body">
                        <!-- content goes here -->
                        <form>
                            <div class="form-group">
                                <label for="exampleInputEmail1">Email address</label>
                                <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email">
                            </div>
                            <div class="form-group">
                                <label for="exampleInputPassword1">Password</label>
                                <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
                            </div>
                            <div class="form-group">
                                <label for="exampleInputFile">File input</label>
                                <input type="file" id="exampleInputFile">
                                <p class="help-block">Example block-level help text here.</p>
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox"> Check me out
                                </label>
                            </div>
                            <button type="submit" class="btn btn-default">Submit</button>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <div class="btn-group btn-group-justified" role="group" aria-label="group button">
                            <div class="btn-group" role="group">
                                <button type="button" class="btn btn-danger" data-dismiss="modal"  role="button">Close</button>
                            </div>
                            <div class="btn-group btn-delete hidden" role="group">
                                <button type="button" id="delImage" class="btn btn-danger" data-dismiss="modal"  role="button">Cancel</button>
                            </div>
                            <div class="btn-group" role="group">
                                <button type="button" id="saveImage" class="btn btn-success btn-hover-green" data-action="save" role="button">Import</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <script>
			// Get the modal
			//var courseSelectModal = document.getElementById('courseSelectModal');
			
			//courseSelectModal.style.display = "block";
			//$("#courseSelectModal").modal({backdrop: 'static', keyboard: false});
        </script>

        <jsp:include page="footer.jsp"/>
    </body>
</html>
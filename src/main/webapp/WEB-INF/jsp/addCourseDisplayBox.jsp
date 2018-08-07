<h2>Courses to be added for Term 3, S.Y. 2013-2014</h2>
<div style="padding: 10px; background-color: #a7f5ab; width: 1020px; min-height: 300px; margin: auto; border: 1px solid black; overflow-y: scroll;">
	<c:forEach items="${previewCourseList }" var="previewCourse" varStatus="innerLoop">
		<!---templatePerCourse-->
	    <div style="overflow: hidden;">
	        <div style="float: left; margin-left: 10px">
	            <b id="${previewCourse.courseId }-${innerLoop.count}"><c:out value="${previewCourse.courseCode }"></c:out></b>
	            <button type="button" class="btn btn-default">S11A</button>
	        </div>
	    </div>
	    
	    <c:choose>
	    	<c:when test="${prevId != previewCourse.courseId}">
	    		<hr style="border-color: black;"><br>
	    	</c:when>
	    </c:choose>
	    
	    <c:set var = "prevId" scope = "session" value = "${previewCourse.courseId}"/>
	    <!--endtemplate-->
	</c:forEach>
    
</div>
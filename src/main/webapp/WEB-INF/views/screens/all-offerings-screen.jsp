<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <c:url value="/images/black-icons/sort-general.png" var="sortArrow" />
    <!-- All Offerings Container -->
    <section id="all-offerings-container">
        <p class="datatables-title">
            <c:choose>
                <c:when test="${userType == 'FACULTY'}">
                    Faculty Load for ${termString}
                </c:when>
                <c:otherwise>
                    Course Offerings for ${termString}
                </c:otherwise>
            </c:choose>
        </p>
        <div id="all-offerings-box">
            <table id="all-offerings-table" class="offerings-table hover row-border order-column">
                <thead>
                    <tr>
                        <th></th>
                        <th>Course</th>
                        <th>Section</th>
                        <th>Days</th>
                        <th>Timeslot</th>
                        <th>Room</th>
                        <th>Faculty</th>
                        <th><!--<img src="/images/green-icons/refresh-button.png" id="all-offerings-refresh" />--></th>
                    </tr>
                </thead>
                <tbody>

                </tbody>
            </table>

            <c:if test="${userType == 'FACULTY'}">
                <div id="teaching-load-compare-box">
                    <label for="teaching-load-compare-dropdown">Compare Teaching Load with: </label>
                    <select id="teaching-load-compare-dropdown">
                        <option value="-">Select faculty</option>
                    </select>
                </div>

                <table id="compare-load-table" class="offerings-table hover row-border order-column">
                    <thead>
                    <tr>
                        <th></th>
                        <th>Course</th>
                        <th>Section</th>
                        <th>Days</th>
                        <th>Timeslot</th>
                        <th>Room</th>
                        <th>Faculty</th>
                        <th><!--<img src="/images/green-icons/refresh-button.png" id="all-offerings-refresh" />--></th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </c:if>
        </div>
    </section>
</html>
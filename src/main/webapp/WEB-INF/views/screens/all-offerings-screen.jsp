<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <c:url value="/images/black-icons/sort-general.png" var="sortArrow" />
    <!-- All Offerings Container -->
    <section id="all-offerings-container">
        <div id="all-offerings-box">
            <ul id="all-offerings-header" class="all-offerings-row">
                <li>Course <img src="${sortArrow}" class="all-offerings-sort" /></li>
                <li>Section <img src="${sortArrow}" class="all-offerings-sort" /></li>
                <li>Day(s) <img src="${sortArrow}" class="all-offerings-sort" /></li>
                <li>Timeslot <img src="${sortArrow}" class="all-offerings-sort" /></li>
                <li>Room <img src="${sortArrow}" class="all-offerings-sort" /></li>
                <li>Faculty <img src="${sortArrow}" class="all-offerings-sort" /></li>
                <li><img src="/images/green-icons/refresh-button.png" class="all-offerings-row-img" id="all-offerings-refresh" /></li>
            </ul>
            <hr id="all-offerings-header-border" class="section-header-border" />
        </div>
    </section>
</html>
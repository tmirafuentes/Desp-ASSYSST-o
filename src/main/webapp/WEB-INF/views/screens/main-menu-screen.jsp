<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <!-- Menu Container -->
    <section id="menu-container">
        <div id="menu-box">
            <p class="section-header-text" id="menu-current-term">${termString}</p>
            <!-- Main Menu -->
            <ul class="menu-subset-list" id="menu-list-main">
                <a href="/"><li <c:if test="${context == 'workspace'}"> class="menu-selected-page" </c:if>>Workspace</li></a>
                <a href="/concerns"><li <c:if test="${context == 'concerns'}"> class="menu-selected-page" </c:if>>Concerns</li></a>
                <a href="/history"><li <c:if test="${context == 'history'}"> class="menu-selected-page" </c:if>>History</li></a>
            </ul>
            <!-- Details Menu -->
            <ul class="menu-subset-list" id="menu-list-manage">
                <li class="menu-subset-list-header">Profiles</li>
                <a href="/courses"><li class="menu-subset-list-item <c:if test="${context == 'courses'}"> menu-selected-page </c:if>">Courses</li></a>
                <a href="/faculty"><li class="menu-subset-list-item <c:if test="${context == 'faculty'}"> menu-selected-page </c:if>">Faculty</li></a>
            </ul>
            <!-- Actions Menu -->
            <ul class="menu-subset-list" id="menu-list-actions">
                <li class="menu-subset-list-header">User Actions</li>
                <a href="#"><li class="menu-subset-list-item">Start New Term</li></a>
            </ul>
        </div>
    </section>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <header id="workspace-header">
        <div class="workspace-title">
            <img src="/images/dlsu-logo.png" id="workspace-logo-img" alt="DLSU Logo" />
            <a href="/" id="workspace-menu-title-link">
                <h3 id="workspace-menu-title">
                    <c:choose>
                        <c:when test="${userType == 'APO'}">
                            APO Workspace
                        </c:when>
                        <c:when test="${userType == 'CVC'}">
                            Chairs Workspace
                        </c:when>
                        <c:when test="${userType == 'FACULTY'}">
                            Faculty Home
                        </c:when>
                    </c:choose>
                </h3>
            </a>
        </div>
        <div class="workspace-navigation">
            <ul class="workspace-navigation-list">
                <a href="/"><li <c:if test="${context == 'workspace'}"> class="workspace-navigation-selected" </c:if>>Home</li></a>
                <a href="/concerns"><li <c:if test="${context == 'concerns'}"> class="workspace-navigation-selected" </c:if>>Concerns</li></a>
                <a href="/faculty"><li <c:if test="${context == 'faculty'}"> class="workspace-navigation-selected" </c:if>>Faculty</li></a>
                <a href="/courses"><li <c:if test="${context == 'courses'}"> class="workspace-navigation-selected" </c:if>>Courses</li></a>
            </ul>
        </div>
        <div class="workspace-user">
            <img src="/images/white-icons/user-avatar.png" class="workspace-user-avatar workspace-user-icons" />
            <div id="header-dropdown-user" class="header-dropdown-content">
                <div class="header-dropdown-arrow" id="dropdown-arrow-user"></div>
                <ul class="header-dropdown-content-list">
                    <li>Signed in as<br><span class="user-name">${currentUser}</span></li>
                    <hr />
                    <a href="/profile"><li>Edit Profile</li></a>
                    <hr />
                    <a href="/logout"><li>Sign out</li></a>
                </ul>
            </div>
            <img src="/images/white-icons/concerns-inbox.png" class="workspace-user-inbox workspace-user-icons" />
            <div class="header-concerns-notif">1</div>
            <div id="header-dropdown-concerns" class="header-dropdown-content">
                <div class="header-dropdown-arrow" id="dropdown-arrow-concerns"></div>
                <ul class="header-dropdown-content-list">
                    <li id="concerns-dropdown-header">Recent Concerns</li>
                    <hr id="concerns-dropdown-header-border" />
                    <a id="dropdown-concerns-mark-all"><li>Mark All as Acknowledged</li></a>
                </ul>
            </div>
        </div>
    </header>
</html>
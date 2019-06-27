<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <header id="workspace-header">
        <img src="/images/dlsu-logo.png" id="workspace-logo-img" alt="DLSU Logo" />
        <a href="/" id="workspace-menu-title-link">
            <h3 id="workspace-menu-title">
                <c:choose>
                    <c:when test="${userType == 'APO'}">
                        APO
                    </c:when>
                    <c:when test="${userType == 'CVC'}">
                        Chairs
                    </c:when>
                </c:choose>
                Workspace
            </h3>
        </a>
        <!--
        <div class="workspace-search">
            <input type="text" placeholder="Search for a course offering..." class="workspace-search-textfield" name="workspace-search-textfield" />
            <button type="button" class="workspace-search-button">
                <img src="/images/white-icons/search.png" class="workspace-search-image" />
            </button>
        </div>-->
        <div class="workspace-user">
            <img src="/images/black-icons/user-avatar.png" class="workspace-user-avatar workspace-user-icons" />
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
            <img src="/images/black-icons/concerns-inbox.png" class="workspace-user-inbox workspace-user-icons" />
            <div id="header-dropdown-concerns" class="header-dropdown-content">
                <div class="header-dropdown-arrow" id="dropdown-arrow-concerns"></div>
                <ul class="header-dropdown-content-list">
                    <li id="concerns-dropdown-header">Recent Concerns</li>
                    <hr id="concerns-dropdown-header-border" />
                    <!--
                    <li class="concerns-dropdown-unmarked">
                        <p class="dropdown-concerns-header">CCPROG1 S17</p>
                        <p class="dropdown-concerns-time">8:50 PM</p>
                        <p class="dropdown-concerns-content">When I was a boy, I love Kazuchika Okada with all my heart and with all my soul. But Naito will always be my stardust. </p>
                    </li>
                    <hr />
                    <li>
                        <p class="dropdown-concerns-header">CCPROG1 S17</p>
                        <p class="dropdown-concerns-time">8:50 PM</p>
                        <p class="dropdown-concerns-content">Hello, I have a concern for your Miss Hazel. When I was...</p>
                    </li>
                    <hr />
                    <li class="concerns-dropdown-unmarked">
                        <p class="dropdown-concerns-header">CCPROG1 S17</p>
                        <p class="dropdown-concerns-time">8:50 PM</p>
                        <p class="dropdown-concerns-content">Hello, I have a concern for your Miss Hazel. When I was...</p>
                    </li>
                    <hr />
                    <li>
                        <p class="dropdown-concerns-header">CCPROG1 S17</p>
                        <p class="dropdown-concerns-time">8:50 PM</p>
                        <p class="dropdown-concerns-content">Hello, I have a concern for your Miss Hazel. When I was...</p>
                    </li>
                    <hr />
                    <li>
                        <p class="dropdown-concerns-header">CCPROG1 S17</p>
                        <p class="dropdown-concerns-time">8:50 PM</p>
                        <p class="dropdown-concerns-content">Hello, I have a concern for your Miss Hazel. When I was...</p>
                    </li>
                    <hr />-->
                    <a href="#" id="dropdown-concerns-mark-all"><li>Mark All as Acknowledged</li></a>
                    <hr />
                </ul>
            </div>
        </div>
    </header>
</html>
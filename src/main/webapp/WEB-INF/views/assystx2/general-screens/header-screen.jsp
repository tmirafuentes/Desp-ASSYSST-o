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
        <div class="workspace-search">
            <input type="text" placeholder="Search for a course offering..." class="workspace-search-textfield" name="workspace-search-textfield" />
            <button type="button" class="workspace-search-button">
                <img src="/images/white-icons/search.png" class="workspace-search-image" />
            </button>
        </div>
        <div class="workspace-user">
            <img src="/images/white-icons/user-avatar.png" class="workspace-user-avatar workspace-user-icons" />
            <div class="logout-dropdown">
                <a href="#">Logout</a>
            </div>
            <img src="/images/white-icons/concerns-inbox.png" class="workspace-user-inbox workspace-user-icons" />
        </div>
    </header>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
    <head>
        <title>Arrowsmith</title>

        <link rel="stylesheet" href="/css/bootstrap.min.css">
        <link href="/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link type="text/css" href="/css/indexStyle.css" rel="stylesheet" >

    </head>
    <body>

        <div class="container" ng-app="loginApp" ng-controller="LogInController">

            <div class="login-box">
            	<br>
                <div><center><font color="white"> <strong>${msg }</strong> </font></center></div>
                <div class="greeting">Welcome to Arrowsmith!</div>
                <form action="loginSubmit" method="post" class="form-box" role="form">

                    <div class="label-textbox-parent-div">
                        <div class="label-div"><label for="idnumber">ID Number:</label></div>
                        <div class="input-div"><input type="text" name="idnumber" id="idnumber" ng-model="user.idnumber" class="input-component" required/><br></div>
                    </div>

                    <div class="label-textbox-parent-div">
                        <div class="label-div"><label for="password">Password:</label></div>
                        <div class="input-div"><input type="password" name="password" id="password" ng-model="user.password" class="input-component" required/><br></div>
                    </div>

                    <div class="space-divider"></div>
	
					<!-- 
                    <div class="remember-me-forgot-password-parent-div">
                        <div class = "remember-me-div"><input type="checkbox" name="rememberme" class="remember-me"><span>Remember me</span></div>
                        <div class = "forgot-password-div"><a href="#" class="forgot-password">Forgot Password?</a></div>
                    </div>
                    -->

                    <div class="space-divider"></div>
					<br>
					<br>
                    <!-- <input type="submit" name="submit" value="Login" class="submit-button" ng-click="login()"> -->
                    <center><button name="submit" value="Login" class="submit-button" ng-click="login()">Login</button></center>
                    <!-- <img ng-if="dataLoading" src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" /> -->

                </form>
            </div>
        </div>

        <script type="text/javascript" src="/js/jquery.min.js"></script>
        <script type="text/javascript" src="/js/bootstrap.min.js"></script>
        <!--<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.7/angular-resource.min.js"></script>-->


    </body>
</html>
<%@ page import="org.springframework.security.web.WebAttributes" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" charset="UTF-8">

        <title>ASSYSTX</title>

        <c:url value="/css/component-styles/signin-style.css" var="signinStyle" />
        <link rel="stylesheet" type="text/css" href="${signinStyle}" />
    </head>
    <body>
        <div class="signin-wrapper">
            <div class="signin-assystx-intro signin-containers">
                <h1 id="signin-intro-title">ASSYSTX</h1>
                <p id="signin-intro-description">
                    ASSYSTX is the course scheduling and faculty load assignment
                    system for the College of Computer Studies. This is for exclusive
                    use of its faculty and Academic Programming Officer. Please do not
                    hack us huhu.
                    <br><br>
                    This is an undergraduate thesis by Roi Ante, Troy Mirafuentes,
                    and Gavin Sanchez as advised by Mr. Jordan Deja.
                </p>
            </div>
            <div class="signin-assystx-login signin-containers">
                <form method="POST" action="/signin">
                    <label for="signin-username">ID Number</label>
                    <input type="text" placeholder="Enter DLSU ID" id="signin-username" name="username" value="22131451" />

                    <label for="signin-password">Password</label>
                    <input type="password" placeholder="iLoveCCS" id="signin-password" name="password" value="iLoveCCS" />

                    <input type="text" id="signin-error" name="error" value="${error}" disabled />

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <button type="submit" id="signin-submit-button">Sign in to ASSYSTX</button>
                </form>
            </div>
        </div>
    </body>
</html>
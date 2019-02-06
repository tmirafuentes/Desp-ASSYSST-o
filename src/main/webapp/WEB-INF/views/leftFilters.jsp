<%--
  Created by IntelliJ IDEA.
  User: Troy Mirafuentes
  Date: 2/1/2019
  Time: 1:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:url value="/css/assystxStyle.css" var="jstlCss" />
    <link rel="stylesheet" type="text/css" href="${jstlCss}">
    <meta charset="UTF-8">
</head>
<body>
<!-- Filters -->
<table id = "left_side_content">
    <tr class = "left_side_rows">
        <td class ="left_side_rows_content">
            <p class = "left_side_text"> View Offerings </p>
            <select class = "left_side_select" id="select_view_offerings">
                <option>All</option>
            </select>
        </td>
    </tr>
    <tr class = "left_side_rows">
        <td class ="left_side_rows_content">
            <p class = "left_side_text"> Search Course </p>
            <form id ="form_search_class">
                <input class = "left_side_select" id="input_search_course"placeholder = "Search Course...">
                <button id = "submit_left_side_search"><i class="fas fa-search"></i></button>
            </form>
        </td>
    </tr>
    <tr class = "left_side_rows">
        <td class ="left_side_rows_content">
            <p class = "left_side_text"> Class Type </p>
            <select class = "left_side_select" id="select_left_class_type"> </select>
        </td>
    </tr>
    <tr class = "left_side_rows">
        <td class ="left_side_rows_content">
            <p class = "left_side_text"> Room Type </p>
            <select class = "left_side_select" id="select_room_type"> </select>
        </td>
    </tr>
    <tr class = "left_side_rows">
        <td class ="left_side_rows_content">
            <p class = "left_side_text"> Time Block </p>
            <select class = "left_side_select" id="select_left_timeblock"> </select>
        </td>
    </tr>
    <tr class = "left_side_rows">
        <td class ="left_side_rows_content">
            <p class = "left_side_text"> Class Days </p>
        </td>
    </tr>
    <tr class = "left_side_rows">
        <td>
            <button class = "class_days" id="class_m"> M </button>
            <button class = "class_days" id="class_t"> T </button>
            <button class = "class_days" id="class_w"> W </button>
            <button class = "class_days" id="class_h"> H </button>
            <button class = "class_days" id="class_f"> F </button>
            <button class = "class_days" id="class_s"> S </button>
        </td>
    </tr>
</table>
</body>
</html>

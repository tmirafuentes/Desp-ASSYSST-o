<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <section id="filters-container">
        <div id="filters-box">
            <p class="section-header-text">Filter Offerings By</p>
            <hr class="section-header-border" />
            <ul class="filters-checkbox-row">
                <li>
                    <input type="checkbox" id="filters-no-room-assigned" name="no-room-filter" />
                    <label for="filters-no-room-assigned">No Room Assigned</label>
                </li>
            </ul>
            <ul class="filters-checkbox-row">
                <li>
                    <input type="checkbox" id="filters-no-faculty-assigned" />
                </li>
                <li><label for="filters-no-faculty-assigned">No Faculty Assigned</label></li>
            </ul>
            <ul class="filters-dropdown-row">
                <li>
                    <select id="filters-timeblock">
                        <option value="-">By Timeblock</option>
                    </select>
                </li>
            </ul>
            <ul class="filters-dropdown-row">
                <li>
                    <select id="filters-course">
                        <option value="-">By Course</option>
                    </select>
                </li>
            </ul>
        </div>
    </section>
</html>
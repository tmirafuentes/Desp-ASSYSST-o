<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <!-- Filters Container -->
    <section id="filters-container">
        <div id="filters-box">
            <p class="section-header-text">Filter Offerings</p>
            <hr class="section-header-border" />
            <ul id="filters-list">
                <li>
                    <div class="filter-checkboxes">
                        <input type="checkbox" id="filters-no-room-assigned" />
                        <label for="filters-no-room-assigned">No Room</label>
                    </div>
                </li>
                <li>
                    <div class="filter-checkboxes">
                        <input type="checkbox" id="filters-no-faculty-assigned" />
                        <label for="filters-no-faculty-assigned">No Faculty</label>
                    </div>
                </li>
                <li>
                    <select id="filters-course" class="filters-dropdowns">
                        <option value="-">By Course</option>
                    </select>
                </li>
                <li>
                    <select id="filters-timeblock" class="filters-dropdowns">
                        <option value="-">By Time Slot</option>
                    </select>
                </li>
                <li>
                    <select id="filters-building" class="filters-dropdowns">
                        <option value="-">By Building</option>
                    </select>
                </li>
            </ul>
        </div>
    </section>
</html>
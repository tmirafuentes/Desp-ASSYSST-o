<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <!-- Filters Container -->
    <section id="filters-container">
        <div id="filters-box">
            <p class="section-header-text">Filter Offerings</p>
            <hr class="section-header-border" />
            <ul id="filters-list">
            <c:choose>
                <c:when test="${userType == 'APO'}">
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
                            <select id="filters-timeslot" class="filters-dropdowns">
                                <option value="-">By Time Slot</option>
                            </select>
                        </li>
                        <li>
                            <select id="filters-room" class="filters-dropdowns">
                                <option value="-">By Room</option>
                            </select>
                        </li>
                </c:when>
                <c:when test="${userType == 'CVC'}">
                        <li>
                            <div class="filter-checkboxes">
                                <input type="checkbox" id="filters-no-faculty-assigned" />
                                <label for="filters-no-faculty-assigned">No Faculty</label>
                            </div>
                        </li>
                        <li>
                        <div class="filter-checkboxes">
                            <input type="checkbox" id="filters-special-class" />
                            <label for="filters-special-class">Special Class</label>
                        </div>
                    </li>
                        <li>
                        <div class="filter-checkboxes">
                            <input type="checkbox" id="filters-service-course" />
                            <label for="filters-service-course">Service Course</label>
                        </div>
                    </li>
                        <li>
                        <div class="filter-checkboxes">
                            <input type="checkbox" id="filters-dissolved-offerings" />
                            <label for="filters-dissolved-offerings">Dissolved</label>
                        </div>
                    </li>
                        <li>
                        <select id="filters-faculty" class="filters-dropdowns">
                            <option value="-">By Faculty</option>
                        </select>
                    </li>
                </c:when>
            </c:choose>
            </ul>
        </div>
    </section>
</html>
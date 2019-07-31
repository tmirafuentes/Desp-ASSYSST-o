<%--
  Created by IntelliJ IDEA.
  User: Troy Mirafuentes
  Date: 6/27/2019
  Time: 1:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <!-- Modals -->
    <div id="all-modals">
        <!-- Form Modals -->

        <!-- Create New Offering Modal -->
        <div id="create-offering-modal" class="modal">
            <p class="section-header-text">Create New Course Offering</p>
            <hr class="section-header-border" />
            <table class="form-modal-tables">
                <tr>
                    <td><label for="create-offering-course">Course Code: </label></td>
                    <td><input type="text" id="create-offering-course" value=""/></td>
                </tr>
                <tr>
                    <td><label for="create-offering-section">Section: </label></td>
                    <td><input type="text" id="create-offering-section" value=""/></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <button type="button" id="create-offering-submit" class="form-modal-submit">Save Offering</button>
                        <button type="button" id="create-offering-cancel" class="form-modal-cancel">Cancel</button>
                    </td>
                </tr>
            </table>
        </div>

        <!-- Raise Concerns Modal -->
        <div id="raise-concerns-modal" class="modal">
            <p class="section-header-text">Raise Concerns</p>
            <hr class="section-header-border" />
            <table class="form-modal-tables">
                <tr>
                    <td><label for="raise-concerns-receiver">To: </label></td>
                    <td><input type="text" id="raise-concerns-receiver" value="" disabled /></td>
                </tr>
                <tr>
                    <td><label for="raise-concerns-offering">Course Offering: </label></td>
                    <td><input type="text" id="raise-concerns-offering" value="" disabled /></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <textarea id="raise-concerns-content" placeholder="Raise Concerns Here..."></textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <button type="button" id="raise-concerns-submit" class="form-modal-submit">Send Concern</button>
                        <button type="button" id="raise-concerns-cancel" class="form-modal-cancel">Cancel</button>
                    </td>
                </tr>
            </table>
        </div>

        <!-- Deload Faculty Modal -->
        <div id="deload-faculty-modal" class="modal form-modal">
            <p class="section-header-text">Deload Faculty</p>
            <hr class="section-header-border" id="deload-faculty-header-border" />
            <table class="form-modal-tables">
                <tr>
                    <td><label for="deload-faculty-name">Faculty: </label></td>
                    <td><input type="text" id="deload-faculty-name" value="Deja, Jordan" disabled /></td>
                </tr>
                <tr>
                    <td><label for="deload-faculty-type">Deloading Type: </label></td>
                    <td>
                        <select id="deload-faculty-type">
                            <option value="-" selected>Select type</option>
                            <option value="AL">Administrative Load</option>
                            <option value="RL">Research Load</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="deload-faculty-instance">Deloading Code: </label></td>
                    <td>
                        <select id="deload-faculty-instance">
                            <option value="-">Select instance</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="deload-faculty-units-deloaded">Units Deloaded: </label></td>
                    <td><input type="text" id="deload-faculty-units-deloaded" value="0.0 units" disabled /></td>
                </tr>
                <!-- Optional: Choose which teaching load to be removed -->
                <tr>
                    <td colspan="2">
                        <button type="button" id="deload-faculty-submit" class="form-modal-submit">Deload Faculty</button>
                        <button type="button" id="deload-faculty-cancel" class="form-modal-cancel">Cancel</button>
                    </td>
                </tr>
            </table>
        </div>

        <!-- Information Modals -->

        <!-- View Offering History Modal -->
        <div id="view-history-modal" class="modal">
            <p class="section-header-text">Offering History</p>
            <hr class="section-header-border" id="view-history-header-border" />
        </div>

        <!-- Course Profile More Details Modal -->
        <div id="view-course-details-modal" class="modal">
            <p class="section-header-text">Course Profile</p>
            <hr class="section-header-border" />

            <table class="form-modal-tables">
                <tr>
                    <td colspan="2">
                        <label for="view-course-details-desc">Course Description: </label>
                        <textarea id="view-course-details-desc" placeholder="Course Description"></textarea>
                    </td>
                </tr>
                <tr>
                    <td><label for="view-course-details-hours">No. of Hours: </label></td>
                    <td><input type="text" id="view-course-details-hours" value="3 Hours" disabled /></td>
                </tr>
                <tr>
                    <td><label for="view-course-details-room">Room Type: </label></td>
                    <td><input type="text" id="view-course-details-room" value="Lecture" disabled /></td>
                </tr>
                <tr>
                    <td><label for="view-course-details-dept">Department: </label></td>
                    <td><input type="text" id="view-course-details-dept" value="Software Technology" disabled /></td>
                </tr>
                <tr>
                    <td><label for="view-course-details-room">College: </label></td>
                    <td><input type="text" id="view-course-details-college" value="College of Computer Studies" disabled /></td>
                </tr>
            </table>
        </div>

        <!-- Faculty Load Modal -->
        <div id="view-faculty-load-modal" class="modal">
            <p class="section-header-text">Faculty Load</p>
            <hr class="section-header-border" />

            <table class="form-modal-tables" id="view-teaching-load-table">
                <thead>
                    <tr>
                        <th>Teaching Load</th>
                        <th>3.0</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><label>CCPROG1 S11</label></td>
                        <td><input type="text" class="view-faculty-load-instance" value="3.0" disabled /></td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</html>

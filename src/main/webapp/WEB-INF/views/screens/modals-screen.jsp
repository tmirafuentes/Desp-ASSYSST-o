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
        <!-- Create New Offering Modal -->
        <div id="create-offering-modal" class="modal">
            <p class="section-header-text">Create New Course Offering</p>
            <hr class="section-header-border" />
            <label for="create-offering-course">Course Code: </label>
            <input type="text" id="create-offering-course" value=""/><br>
            <label for="create-offering-section">Section: </label>
            <input type="text" id="create-offering-section" value=""/><br>
            <button type="button" id="create-offering-submit">Save Offering</button>
        </div>

        <!-- Raise Concerns Modal -->
        <div id="raise-concerns-modal" class="modal">
            <p class="section-header-text">Raise Concerns</p>
            <hr class="section-header-border" />
            <label for="raise-concerns-receiver">To: </label>
            <input type="text" id="raise-concerns-receiver" value="" disabled />
            <label for="raise-concerns-offering">Course Offering: </label>
            <input type="text" id="raise-concerns-offering" value="" disabled />
            <textarea id="raise-concerns-content" placeholder="Raise Concerns Here..."></textarea>
            <button type="button" id="raise-concerns-submit">Send Concern</button>
        </div>

        <!-- View Offering History Modal -->
        <div id="view-history-modal" class="modal">
            <p class="section-header-text">Offering History</p>
            <hr class="section-header-border" id="view-history-header-border" />
        </div>

        <!-- Deload Faculty Modal -->
        <div id="deload-faculty-modal" class="modal">
            <p class="section-header-text">Deload Faculty</p>
            <hr class="section-header-border" id="deload-faculty-header-border" />
            <table>
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
                    <td></td>
                    <td>
                        <button type="button" id="deload-faculty-submit">Deload</button>
                        <button type="button" id="deload-faculty-cancel">Cancel</button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</html>

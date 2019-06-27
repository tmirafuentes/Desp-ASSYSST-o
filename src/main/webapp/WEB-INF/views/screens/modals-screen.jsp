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
        <!-- Edit Section Modal -->
        <div id="edit-section-modal" class="modal">
            <p class="section-header-text">Edit Section</p>
            <hr class="section-header-border" />
            <input id="edit-section-offering" type="text" value="Offering: CCPROG1 S17" />
            <input id="edit-section-input" type="text" placeholder="New Section" />
            <button type="button" id="edit-section-submit">Save</button>
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
    </div>
</html>

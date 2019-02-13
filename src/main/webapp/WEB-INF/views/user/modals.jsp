<%--
  Created by IntelliJ IDEA.
  User: Troy Mirafuentes
  Date: 2/13/2019
  Time: 7:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <body>
        <!-- Modal for Assign Room -->
        <div class="divModals" id="modalAssignRoom">
            <table class="modal_header">
                <tr>
                    <th>Search</th>
                    <th margin-left="50px">Room Type</th>
                    <th>Building</th>
                </tr>
                <tr>
                    <td>
                        <input class = 'modal_search' id='input_search_room'>
                        <button id='button_search_room'><i class='fas fa-search'></i></button>
                    </td>
                    <td>
                        <select class = 'modal_select' id='select_room_type'></select>
                    </td>
                    <td>
                        <select class = 'modal_select' id='select_building'>
                            <option value='All'>All</option>
                            <c:forEach items='${allBuildings}' var='building'>
                                <option value='${building.bldgName}'><c:out value='${building.bldgName}' /></option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>
            <table id="modal_table_room">
                <tr>
                    <th>Room</th>
                    <th>Room Type</th>
                    <th>Building</th>
                    <th>Capacity</th>
                    <th>Assign</th>
                </tr>
                <tr>
                    <td>G201</td>
                    <td>Lecture</td>
                    <td>Gokongwei</td>
                    <td>45</td>
                    <td><button class = 'assign_modal_buttons'>Assign</button></td>
                </tr>
            </table>
            <table class="modal_footer">
                <tr>
                    <td><button id='room_done_button' class='modal_buttons'>Done</button></td>
                    <td><button id='room_cancel_button' class='modal_buttons'>Cancel</button></td>
                </tr>
            </table>
        </div>

        <!-- Modal for Concerns -->
        <div class="divModals" id="modalConcerns">

        </div>

        <!-- Modal for Add Course Offering -->
        <div class="divModals" id="modalAddOffering">

        </div>
    </body>
</html>

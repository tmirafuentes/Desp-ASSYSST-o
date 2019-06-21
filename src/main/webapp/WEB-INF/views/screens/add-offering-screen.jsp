<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <!-- Add Offering Container -->
    <section id="add-offering-container">
        <div id="add-offering-box">
            <p class="section-header-text">Create New Offering</p>
            <hr class="section-header-border" />
            <div id="add-offering-form">
                    <input type="text" id="add-offering-course" placeholder="Course Code" required="required" name="courseCode" />
                    <input type="text" id="add-offering-section" placeholder="Section" required="required" name="section" />
                    <!--<button type="submit" id="add-offering-room" alt="Room Modal">Assign Room</button>-->
                    <button type="button" id="add-offering-submit" alt="Creates New Offering">Save New Offering</button>
            </div>
        </div>
    </section>
</html>
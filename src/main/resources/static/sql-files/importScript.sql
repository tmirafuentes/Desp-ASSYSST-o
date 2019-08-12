/* Import Users */
insert into assystx.user(user_id, first_name, last_name, password, user_type, college_id, dept_id, username)
select user_id, first_name, last_name, user_password, user_type, college_id, dept_id, user_id
from arrowsmithvampire.users;

/* Import Building */ 
insert into assystx.building(bldg_id, bldg_code, bldg_name)
select building_id, building_code, building_name
from arrowsmithvampire.building;

/* Import College*/
insert into assystx.college(college_id, college_code, college_name)
select college_id, college_code, college_name
from arrowsmithvampire.college;

/* Import Constraints*/
insert into assystx.constraints(constraint_id, max_consecutive_hours, max_fulltime_load,
								max_hours_per_day, max_parttime_load, min_fulltime_load,
                                min_parttime_load, dept_id)
select constraint_id, MAX_CONSECUTIVE_HOURS, MAX_LOAD_FULLTIME, MAX_HOURS_PER_DAY,
		MAX_LOAD_PARTTIME, MIN_LOAD_FULLTIME, MIN_LOAD_PARTTIME, dept_id
from arrowsmithvampire.constraints;

/* Import Course */
insert into assystx.course(course_id, course_code, course_desc, course_name, units, college_id, dept_id)
select course_id, course_code, description, course_name, units, college_id, dept_id
from arrowsmithvampire.course;

/* Import Course Offering */
insert into assystx.course_offering(offering_id, startay, enday, max_enrolled, section, status, term, course_id, user_id)
select Offering.offering_id, Offering.start_year, Offering.end_year, 
		Offering.max_students_enrolled, Offering.section, Offering.offering_status, 
        Offering.term, Offering.course_id, Faculty.user_id
from arrowsmithvampire.offering Offering
inner join arrowsmithvampire.faculty Faculty
on Offering.faculty_id = Faculty.faculty_id;

/* Import Days */
insert into assystx.days(days_id, begin_time, class_day, end_time, offering_id, room_id)
select days_id, begin_time, class_day, end_time, offering_id, room_id
from arrowsmithvampire.days;

/* Import Degree Program */
insert into assystx.degree_program(degree_id, degree_code, degree_name, college_id, dept_id)
select degreeprogram_id, degreeprogram_code, degreeprogram_name, college_id, dept_id
from arrowsmithvampire.degreeprogram;

/* Import Deload Instance */
insert into assystx.deload_instance(deload_in_id, enday, remarks, startay, term, deload_id, user_id)
select deloadoffer_id, end_year, remarks, start_year, term, deloading_id, user_id
from arrowsmithvampire.deloadoffer DO
inner join arrowsmithvampire.faculty FA
on DO.faculty_id = FA.faculty_id;

/* Import Deloading */
insert into assystx.deloading(deload_id, deload_code, deload_name, deload_type, description, units, college_id, department_id)
select deloading_id, deloading_code, deloading_name, deloading_type, description, units, college_id, dept_id
from arrowsmithvampire.deloading;

/* Import Department */
insert into assystx.department(dept_id, dept_code, dept_name, college_id)
select dept_id, dept_code, dept_name, college_id
from arrowsmithvampire.department;

/* Import Faculty Load */
insert into assystx.faculty_load(load_id, admin_load, deloaded_load, enday, leave_type, nonacad_load,
								research_load, startay, teaching_load, term, total_load,
								college_id, dept_id, user_id)
select loads_id, admin_load, deloading, end_year, leave_type, non_acad_load,
		research_load, start_year, teaching_load, term, total_load,
        college_id, dept_id, user_id
from arrowsmithvampire.loads LO
inner join arrowsmithvampire.faculty FA
on LO.faculty_id = FA.faculty_id;

/* Import Room */
insert into assystx.room(room_id, room_capacity, room_type, building_id, room_code)
select room_id, room_capacity, room_type, building_id, room_code
from arrowsmithvampire.room;
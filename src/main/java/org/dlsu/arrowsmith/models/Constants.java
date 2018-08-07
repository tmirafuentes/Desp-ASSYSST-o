package org.dlsu.arrowsmith.models;

public class Constants {
	public static final String NO_ROOM_ID = "60";
	public static final String NO_PROFESSOR = "11111111";
	public static final String NO_FACULTY_ID = "1";
	public static final String NO_COLLEGE_ID = "9";
	public static final String NO_DEPARTMENT_ID = "42";
	public static final String NO_CLASSDAY_CONSTANT = "ND";
	
	public static final String MAX_PREPARATION = "4";
	public static final String MAX_LOAD_ALL = "15";
	public static final String MAX_LOAD_FULLTIME = "12";
	public static final String MAX_LOAD_PARTTIME = "9";
	public static final String MAX_LOAD_HALFTIME = "6";
	public static final String FOUR_FIVE_REPRESENTATION = "430"; //4 hours and a half(30 minutes) = 4.5 hours
	
    public static final String COURSE_TABLE = "course";
    public static final String COLLEGE_TABLE = "college";
    public static final String DEPARTMENT_TABLE = "department";
    public static final String FACULTY_TABLE = "faculty";
    public static final String USERS_TABLE = "users";
    public static final String LOADS_TABLE = "loads";
    public static final String OFFERING_TABLE = "offering";
    public static final String DAYS_TABLE = "days";
    public static final String BUILDING_TABLE = "building";
    public static final String ROOM_TABLE = "room";
    public static final String DEGREEPROGRAM_TABLE = "degreeprogram";
    public static final String BATCHINFO_TABLE = "batchinfo";
    public static final String FLOWCHART_TABLE = "flowcharts";
    public static final String FLOWCOURSES_TABLE = "flowcourses";
    public static final String EQUIVALENCE_TABLE = "equivalence";
    public static final String REQUISITE_TABLE = "requisite";
    
    public static final String COURSE_ID = "course_id";
    public static final String COURSE_COLLEGEID = "college_id";
    public static final String COURSE_DEPTID = "dept_id";
    public static final String COURSE_AREAID = "area_id";
    public static final String COURSE_CODE = "course_code";
    public static final String COURSE_NAME = "course_name";
    public static final String COURSE_UNITS = "units";
    public static final String COURSE_REMARKS = "remarks";
    public static final String COURSE_DESCRIPTION = "description";
    
    public static final String FACULTY_ID = "faculty_id";
    public static final String FACULTY_USERID = "user_id";
    public static final String FACULTY_YEARSTARTED = "year_started";
    public static final String FACULTY_TYPE = "faculty_type";
    
    public static final String USERS_ID = "user_id";
    public static final String USERS_COLLEGEID = "college_id";
    public static final String USERS_DEPTID = "dept_id";
    public static final String USERS_FIRSTNAME = "first_name";
    public static final String USERS_MIDDLENAME = "middle_name";
    public static final String USERS_LASTNAME = "last_name";
    public static final String USERS_TYPE = "user_type";
    public static final String USERS_PASSWORD = "user_password";
    
    public static final String COLLEGE_ID = "college_id";
    public static final String COLLEGE_CODE = "college_code";
    public static final String COLLEGE_NAME = "college_name";
    public static final String COLLEGE_ISOBSOLETE = "is_obsolete";
    
    public static final String DEPT_ID = "dept_id";
    public static final String DEPT_CODE = "dept_code";
    public static final String DEPT_NAME = "dept_name";
    public static final String DEPT_SIZE = "dept_size";
    public static final String DEPT_COLLEGEID = "college_id";
    public static final String DEPT_ISOBSOLETE = "is_obsolete";
    
    public static final String DAYS_ID = "days_id";
    public static final String DAYS_OFFERINGID = "offering_id";
    public static final String DAYS_ROOMID = "room_id";
    public static final String DAYS_STARTYEAR = "start_year";
    public static final String DAYS_ENDYEAR = "end_year";
    public static final String DAYS_TERM = "term";
    public static final String DAYS_CLASSDAY = "class_day";
    public static final String DAYS_BEGINTIME = "begin_time";
    public static final String DAYS_ENDTIME = "end_time";
    
    public static final String EQUIVALENCE_COURSEID = "course_id";
    public static final String EQUIVALENCE_ID = "equivalence_id";

    public static final String LOADS_ID = "loads_id";
    public static final String LOADS_FACULTYID = "faculty_id";
    public static final String LOADS_COLLEGEID = "college_id";
    public static final String LOADS_DEPTID = "dept_id";
    public static final String LOADS_STARTYEAR = "start_year";
    public static final String LOADS_ENDYEAR = "end_year";
    public static final String LOADS_TERM = "term";
    public static final String LOADS_ADMINLOAD = "admin_load";
    public static final String LOADS_RESEARCHLOAD = "research_load";
    public static final String LOADS_TEACHINGLOAD = "teaching_load";
    public static final String LOADS_NONACADLOAD = "non_acad_load";
    public static final String LOADS_DELOADING = "deloading";
    public static final String LOADS_TOTALLOAD = "total_load";
    public static final String LOADS_PREPARATIONS = "preparations";
    public static final String LOADS_HASRESEARCHLOAD = "has_research_load";
    public static final String LOADS_ISADMIN = "is_admin";
    public static final String LOADS_LEAVETYPE = "leave_type";
    public static final String LOADS_ISONLEAVE = "is_on_leave";
    public static final String LOADS_TIMESTAMP = "time_stamp";
    
    public static final String OFFERING_ID = "offering_id";
    public static final String OFFERING_COURSEID = "course_id";
    public static final String OFFERING_FACULTYID = "faculty_id";
    public static final String OFFERING_DEGREEPROGRAM = "degree_program";
    public static final String OFFERING_SECTION = "section";
    public static final String OFFERING_BATCH = "batch";
    public static final String OFFERING_TERM = "term";
    public static final String OFFERING_STARTYEAR = "start_year";
    public static final String OFFERING_ENDYEAR = "end_year";
    public static final String OFFERING_MAXSTUDENTSENROLLED = "max_students_enrolled";
    public static final String OFFERING_CURRSTUDENTSENROLLED = "curr_students_enrolled";
    public static final String OFFERING_ISNONACAD = "is_non_acad";
    public static final String OFFERING_ISSHS = "is_shs";
    public static final String OFFERING_ISSERVICECOURSE = "is_service_course";
    public static final String OFFERING_SERVICEDEPTID = "servicedept_id";
    public static final String OFFERING_ISMASTERS = "is_masters";
    public static final String OFFERING_ISELECTIVE = "is_elective";
    public static final String OFFERING_ELECTIVETYPE = "elective_type";
    public static final String OFFERING_REMARKS = "remarks";
    public static final String OFFERING_STATUS = "offering_status";
    public static final String OFFERING_LOCATION = "location";
    public static final String OFFERING_ITEOEVAL = "iteo_eval";
    public static final String OFFERING_ISPUBLISHED = "isPublished";
    
    public static final String BUILDING_ID = "building_id";
    public static final String BUILDING_NAME = "building_name";
    public static final String BUILDING_CODE = "building_code";
    
    public static final String ROOM_ID = "room_id";
    public static final String ROOM_CODE = "room_code";
    public static final String ROOM_LOCATION = "room_location";
    public static final String ROOM_TYPE = "room_type";
    public static final String ROOM_CAPACITY = "room_capacity";
    public static final String ROOM_BUILDINGID = "building_id";
    
    public static final String BATCHINFO_ID = "batchinfo_id";
    public static final String BATCHINFO_DEGREEPROGRAMID = "degreeprogram_id";
    public static final String BATCHINFO_BATCH = "batch";
    public static final String BATCHINFO_STUDENTCOUNT = "studentcount";
    
    public static final String DEGREEPROGRAM_ID = "degreeprogram_id";
    public static final String DEGREEPROGRAM_COLLEGEID = "college_id";
    public static final String DEGREEPROGRAM_DEPTID = "dept_id";
    public static final String DEGREEPROGRAM_CODE = "degreeprogram_code";
    public static final String DEGREEPROGRAM_NAME = "degreeprogram_name";
    
    public static final String FLOWCHART_ID = "flowchart_id";
    public static final String FLOWCHART_DEGREEPROGRAMID = "degreeprogram_id";
    public static final String FLOWCHART_BATCH = "batch";
    public static final String FLOWCHART_YEARLEVEL = "year_level";
    public static final String FLOWCHART_STARTYEAR = "start_year";
    public static final String FLOWCHART_ENDYEAR = "end_year";
    
    public static final String FLOWCOURSES_FLOWCHARTID = "flowchart_id";
    public static final String FLOWCOURSES_COURSEID = "course_id";
    public static final String FLOWCOURSES_TERM = "term";
    
    public static final String REQUISITE_COURSEID = "course_id";
    public static final String REQUISITE_REQCOURSEID = "req_course_id";
    public static final String REQUISITE_REQTYPE = "req_type";
}

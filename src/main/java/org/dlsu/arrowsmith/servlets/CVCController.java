package org.dlsu.arrowsmith.servlets;

import org.dlsu.arrowsmith.dao.*;
import org.dlsu.arrowsmith.models.*;
import org.dlsu.arrowsmith.utility.RandomString;
import org.dlsu.arrowsmith.utility.Scrambler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

@Controller
public class CVCController {
	private ArrayList<Faculty> listFaculty = new ArrayList();
	private ArrayList<Timeframe> listTimeframe = new ArrayList();
	private ArrayList<Offering> listOffering = new ArrayList();
	
	private ArrayList<Timeframe> timeframes = new ArrayList();
	private ArrayList<String> formatTimeframes = new ArrayList();
	private ArrayList<TempOffering> tempOfferingList = new ArrayList();
	private String timeframeButtonName = "Select Term";
	
	private ArrayList<Degreeprogram> degreeProgramList = new ArrayList();
	private ArrayList<String> degreeList = null;
	private ArrayList<String> batchList = null;
	private ArrayList<String> yearList = null;
	
	private ArrayList<Course> previewCourseList = new ArrayList();
	
	/********************ACADEMIC YEARS/FLOWCHARTS RELATED METHODS***************/
	@RequestMapping(value="/getAllAcademicYearsCVC", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<AcademicYear> getAllAcademicYearsCVC() {
		ArrayList<AcademicYear> AYList = new ArrayList<AcademicYear>();
		
		try {
			AYList = AcademicYearDAO.getAllAY();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return AYList;
	}
	/********************ACADEMIC YEARS/FLOWCHARTS RELATED METHODS***************/
	/********************OFFERING RELATED METHODS**************************/
	@RequestMapping(value="/getOfferingListWithKeyCVC", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Offering> getOfferingListWithKeyCVC(
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term,
			@RequestParam("searchKeyword") String searchKeyword) {
		ArrayList<Offering> offeringList = new ArrayList<Offering>();
		
		try {
			offeringList = OfferingDAO.getListOfferingsByTermWithCourseKey(startYear, endYear, term, searchKeyword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return offeringList;
	}
	
	@RequestMapping(value="/getAllOfferingsWithFiltersCVC", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Offering> getAllOfferingsWithFiltersCVC(
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term,
			@RequestParam("daysList[]") String[] daysList,
			@RequestParam("selectedTimeBlock") String selectedTimeBlock,
			@RequestParam("selectedBatch") String selectedBatch,
			@RequestParam("selectedStatus") String selectedStatus,
			@RequestParam("selectedRoomType") String selectedRoomType,
			@RequestParam("searchKeyword") String searchKeyword) {
		
		ArrayList<Offering> offeringList = new ArrayList<Offering>();
		/*System.out.println(daysList.length + "-" + selectedTimeBlock + "-" + selectedRoomType
				+ "-" + selectedBatch + "-" + selectedStatus
				+ "-" + endYear + "-" + startYear+ "-" + term + "-" + daysList[2]
						+ "-" + daysList[4] + "-" + searchKeyword);*/
		try {
			offeringList = OfferingDAO.getListOfferingsByTermWithFilters(startYear, endYear, term, 
					daysList, selectedTimeBlock, selectedBatch, selectedStatus, selectedRoomType, searchKeyword);
			//System.out.println(offeringList.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return offeringList;
	}
	
	@RequestMapping(value="/getAllOfferingsWithoutFiltersCVC", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Offering> getAllOfferingsWithoutFiltersCVC(
			@RequestParam("searchKeyword") String searchKeyword,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term) {
		
		ArrayList<Offering> offeringList = new ArrayList<Offering>();
		
		try {
			offeringList = OfferingDAO.getListOfferingsByTermWithKey(startYear, endYear, term, searchKeyword);
			//System.out.println(offeringList.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return offeringList;
	}
	
	@RequestMapping(value="/populateBatchDropdownCVC", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateBatchDropdownCVC(
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term) {
		ArrayList<String> sList = new ArrayList<String>();
		
		try {
			sList = OfferingDAO.getAllUniqueBatch(startYear, endYear, term);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sList;
	}
	
	@RequestMapping(value="/populateStatusDropdownCVC", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateStatusDropdownCVC(
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term) {
		ArrayList<String> sList = new ArrayList<String>();
		
		try {
			sList = OfferingDAO.getAllUniqueStatus(startYear, endYear, term);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sList;
	}
	
	@RequestMapping(value="/populateTimeBlockDropdownCVC", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateTimeBlockDropdownCVC(
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term) {
		ArrayList<String> sList = new ArrayList<String>();
		
		try {
			sList = DaysDAO.getAllUniqueTimeBlock(startYear, endYear, term);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sList;
	}
	
	@RequestMapping(value="/populateRoomTypeDropdownCVC", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateRoomTypeDropdownCVC() {
		ArrayList<String> typeList = new ArrayList<String>();
		
		try {
			typeList = RoomDAO.getAllUniqueRoomType();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return typeList;
	}
	
	@RequestMapping(value="/getOfferingWithScheduleCVC", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    Offering getOfferingWithScheduleCVC(
			@RequestParam("offeringId") String offeringId) {
		Offering offering = new Offering();
		
		try {
			offering = OfferingDAO.getOfferingByID(offeringId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return offering;
	}
	
	@RequestMapping(value="/getOfferingListOfFacultyCVC", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Offering> getOfferingListOfFacultyCVC(
			@RequestParam("facultyId") String facultyId,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term) {
		
		ArrayList<Offering> offeringList = new ArrayList<Offering>();
		
		try {
			offeringList = OfferingDAO.getListOfferingsByFaculty(facultyId, startYear, endYear, term);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return offeringList;
	}
	/********************OFFERING RELATED METHODS**************************/
	/*********************FACULTY RELATED METHODS****************************/
	@RequestMapping(value="/getNormalFacultyListWithKeyCVC", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Faculty> getNormalFacultyListWithKeyCVC(
			@RequestParam("offeringId") String offeringId,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term) {
		
		ArrayList<Faculty> facultyList = new ArrayList<Faculty>();
		Offering offering = new Offering();
		Department dept = new Department();
		College college = new College();
		Course course = new Course();
		
		try {
			offering = OfferingDAO.getOfferingByID(offeringId);
			course = CourseDAO.getCourseByID(offering.getCourseId());
			dept = DepartmentDAO.getDepartmentByID(course.getDepartment().getId());
			college = CollegeDAO.getCollegeByID(course.getCollege().getId());
			
			facultyList = FacultyDAO.getAllFacultyWithSameCollegeAsOffering(course, college, startYear, endYear, term);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return facultyList;
	}
	
	@RequestMapping(value="/getFacultyListWithKeyCVC", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Faculty> getFacultyListWithKeyCVC(
			@RequestParam("offeringId") String offeringId,
			@RequestParam("facultyType") String facultyType,
			@RequestParam("department") String department,
			@RequestParam("college") String college,
			@RequestParam("searchKeyword") String searchKeyword,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term) {
		
		ArrayList<Faculty> facultyList = new ArrayList<Faculty>();
		Offering o = new Offering();
		Department d = new Department();
		College c = new College();
		int tempDeptId = -999;
		int tempCollegeId = -999;
		String newFacultyType = facultyType;
//		String newFacultyType;
		
//		if(facultyType.equalsIgnoreCase("Full Time")) newFacultyType = "FT";
//		else if(facultyType.equalsIgnoreCase("Part Time")) newFacultyType = "PT";
//		else if(facultyType.equalsIgnoreCase("Half Time")) newFacultyType = "HT";
//		else  newFacultyType = "FT";
		
		try {
			o = OfferingDAO.getOfferingByID(offeringId);
			
			if(!department.equalsIgnoreCase("All")){//if not "All" then set, else use default value
				d = DepartmentDAO.getDepartmentByName(department);
				tempDeptId = Integer.parseInt(d.getId());
			}
			
			if(!college.equalsIgnoreCase("All")){//if not "All" then set, else use default value
				c = CollegeDAO.getCollegeByName(college);
				//System.out.println("COLLEGE: "+ c.getCollegeName());
				tempCollegeId = Integer.parseInt(c.getId());
			}

			System.out.println(tempCollegeId + "--" + tempDeptId + "--" +searchKeyword + "--" +facultyType);
			facultyList = FacultyDAO.getAllFacultyWithSearchKey(o, newFacultyType, tempDeptId+"", tempCollegeId+"", searchKeyword, startYear, endYear, term);

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return facultyList;
	}
	
	@RequestMapping(value="/getRecommendedFacultyListWithKeyCVC", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Faculty> getRecommendedFacultyListWithKeyCVC(
			@RequestParam("offeringId") String offeringId,
			@RequestParam("facultyType") String facultyType,
			@RequestParam("department") String department,
			@RequestParam("college") String college,
			@RequestParam("searchKeyword") String searchKeyword,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term) {
		
		ArrayList<Faculty> facultyList = new ArrayList<Faculty>();
		Offering o = new Offering();
		int tempDeptId = -999;
		int tempCollegeId = -999;
		String newFacultyType = facultyType;
		
		try {
			o = OfferingDAO.getOfferingByID(offeringId);
			
			facultyList = FacultyDAO.getAllFacultyWithRecommendations(o, newFacultyType, tempDeptId+"", tempCollegeId+"", "", startYear, endYear, term);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return facultyList;
	}
	
	@RequestMapping(value="/populateFacultyTypeDropdownCVC", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateFacultyTypeDropdownCVC() {
		ArrayList<String> typeList = new ArrayList<String>();
		
		try {
			typeList = FacultyDAO.getAllUniqueFacultyType();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return typeList;
	}
	
	@RequestMapping(value = "/initiateFacultyAssignmentCVC", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
	public @ResponseBody
    StringResponse initiateFacultyAssignmentCVC(
			@RequestParam("offeringId") String offeringId,
			@RequestParam("facultyId") String facultyId,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term) throws SQLException {

		StringResponse s = new StringResponse("SUCCESS");
		float units = 0;
		String loadsId = "", currentloadsId ="";
		units = OfferingDAO.getNumberOfUnits(offeringId);
		
		if (!(FacultyDAO.checkIfSameFaculty(facultyId, offeringId))) {

			// check if load id is existing, if not create a new load id for this specific faculty
			try {
				if (FacultyDAO.checkIfHasLoadId(facultyId, startYear, endYear, term)) {
					try {
						// remove faculty from course if any (make faculty id = 1)
						String currentFacultyId = OfferingDAO.getFacultyIdUsingOfferingId(offeringId);
						System.out.println("Current faculty id: " + currentFacultyId);
						if(!currentFacultyId.equals("1")) { // 1 - no professor, no need to decrement
							OfferingDAO.removeFacultyFromOffering(offeringId); // prompt in the future if sure the user really wants to change
							currentloadsId = LoadDAO.getLoadsId(currentFacultyId, startYear, endYear, term);
							FacultyDAO.decrementLoad(currentloadsId, units);
						}
						/****************************LOAD NECESSARY INFO******************************/
				        ArrayList<Offering> initialOfferingList = OfferingDAO.getListOfferingsByFaculty(facultyId, startYear, endYear, term);
				        Offering o = OfferingDAO.getOfferingByID(offeringId);
				        Load l = LoadDAO.getLoadByID(facultyId, startYear, endYear, term);
				        Faculty f = FacultyDAO.getFacultyByID(facultyId, startYear, endYear, term);
				        /****************************LOAD NECESSARY INFO******************************/
				       
				        /****************************DETERMINE MAX LOADS PER FACULTY TYPE******************************/
				        float maxUnits = 12;
				        
				        if(f.getFacultyType().equalsIgnoreCase("FT")) maxUnits = 12;
				        else if(f.getFacultyType().equalsIgnoreCase("PT")) maxUnits = 9;
				        else if(f.getFacultyType().equalsIgnoreCase("HT")) maxUnits = 6;
				        /****************************DETERMINE MAX LOADS PER FACULTY TYPE******************************/
				        /****************************FACULTY ASSIGNMENT PROPER******************************/
				        Boolean hasOfferingCollision = checkOfferingCollision(o, initialOfferingList);
				        Boolean hasViolatedSixHoursPerDay = checkIfSixHoursRuleViolated(o, initialOfferingList);
				        Boolean hasViolatedConsecutiveHours = checkIfConsecutiveHoursRuleViolated(o, initialOfferingList);
				        
				        if(hasOfferingCollision == false){ //meaning no collision
					        if(hasViolatedSixHoursPerDay == false){ //meaning di naviolate
					        	if(hasViolatedConsecutiveHours == false){ //meaning di naviolate
									if(Integer.parseInt(l.getPreparations()) < 4){ //CHECK IF REACHED MAX PREPARATIONS
									//first step of recommend algo
										
										/****************************CHECK IF TOTAL LOAD +UNITS OF COURSE OT BE ADDED IS GREATER THAN MAX UNITS******************************/
										if((Float.parseFloat(l.getTotalLoad()) + Float.parseFloat(o.getCourse().getUnits())) <=  Float.parseFloat(Constants.MAX_LOAD_ALL)){
												/******************************OVERLOAD NOTICE*************************/
													if(((Float.parseFloat(l.getTotalLoad()) < maxUnits) && ((Float.parseFloat(l.getTotalLoad()) + Float.parseFloat(o.getCourse().getUnits())) >=  maxUnits))){ //MAX LOADS 2: ADDING THE SUBJECT WILL MAKE THE FACULTY OVERLOAD
														s = new StringResponse("OVERLOAD-THREAT");
														
														s.setLastName(f.getLastName());
														s.setFirstName(f.getFirstName());
														
														if(f.getFacultyType().equalsIgnoreCase("FT")){
															s.setFacultyType("Full Time");
															s.setMaxLoad(Float.parseFloat(Constants.MAX_LOAD_FULLTIME));
														}else if(f.getFacultyType().equalsIgnoreCase("PT")){
															s.setFacultyType("Part Time");
															s.setMaxLoad(Float.parseFloat(Constants.MAX_LOAD_PARTTIME));
														}if(f.getFacultyType().equalsIgnoreCase("HT")){
															s.setFacultyType("Half Time");
															s.setMaxLoad(Float.parseFloat(Constants.MAX_LOAD_HALFTIME));
														}else{
															s.setFacultyType("Full Time");
															s.setMaxLoad(Float.parseFloat(Constants.MAX_LOAD_FULLTIME));
														}
													}else s = new StringResponse("SUCCESS");
												/******************************OVERLOAD NOTICE*************************/
													
												FacultyDAO.assignFacultyToOffering(offeringId, facultyId); // assign the faculty id to offering id
												loadsId = LoadDAO.getLoadsId(facultyId, startYear, endYear, term);
												FacultyDAO.incrementLoad(loadsId, units);
												
												/****************************UPDATE PREPARATION COUNT******************************/
												int foundEquivalent = 0;
												
										        for(int j = 0; j < initialOfferingList.size(); j++){
													if(!initialOfferingList.get(j).getCourseId().equalsIgnoreCase(o.getCourseId())){ //if same kasi ng courseID/code/name dnadagdagan parin
											        	if(EquivalenceDAO.checkIfTwoCoursesAreEquivalent(initialOfferingList.get(j).getCourseId()+"", o.getCourseId()+"") == 1){
											        		foundEquivalent = 1; //para wag dagadagan pag equivalent
											        	}
													}else{
														foundEquivalent = 1; //para wag dagdagan pag same
													}
										        }
										        
										        if(foundEquivalent == 0) LoadDAO.updateFacultyPreparationCount(facultyId, startYear, endYear, term);
										        /****************************UPDATE PREPARATION COUNT******************************/
												
										}else if((Float.parseFloat(l.getTotalLoad()) + Float.parseFloat(o.getCourse().getUnits())) >  Float.parseFloat(Constants.MAX_LOAD_ALL)){ //MAX LOADS 3: nareach na ung max
											s = new StringResponse("MAX-LOAD-REACHED");
											
											s.setLastName(f.getLastName());
											s.setFirstName(f.getFirstName());
											
										}else if((Float.parseFloat(l.getTotalLoad()) >  maxUnits) && (Float.parseFloat(l.getTotalLoad()) > 15)){ //MAX LOADS 1: HAS REACHED MAX LOAD CHECKER
											s = new StringResponse("ALREADY-OVERLOAD");
											
											s.setLastName(f.getLastName());
											s.setFirstName(f.getFirstName());
											
											if(f.getFacultyType().equalsIgnoreCase("FT")){
												s.setFacultyType("Full Time");
												s.setMaxLoad(Float.parseFloat(Constants.MAX_LOAD_FULLTIME));
											}else if(f.getFacultyType().equalsIgnoreCase("PT")){
												s.setFacultyType("Part Time");
												s.setMaxLoad(Float.parseFloat(Constants.MAX_LOAD_PARTTIME));
											}if(f.getFacultyType().equalsIgnoreCase("HT")){
												s.setFacultyType("Half Time");
												s.setMaxLoad(Float.parseFloat(Constants.MAX_LOAD_HALFTIME));
											}else{
												s.setFacultyType("Full Time");
												s.setMaxLoad(Float.parseFloat(Constants.MAX_LOAD_FULLTIME));
											}
										}//overload threat
									}else{ //PREPARATION CHECKER
										s = new StringResponse("MAX-PREPARATION");
										s.setFacultyType(f.getFacultyType());
										s.setLastName(f.getLastName());
										s.setFirstName(f.getFirstName());
										s.setMaxPreparation(Integer.parseInt(Constants.MAX_PREPARATION));
									}
					        	}else{ //CONSECUTIVE HOURS RULE CHECKER
									s = new StringResponse("CONSECUTIVE-HOUR-THREAT");
									s.setFacultyType(f.getFacultyType());
									s.setLastName(f.getLastName());
									s.setFirstName(f.getFirstName());
									s.setMaxPreparation(Integer.parseInt(Constants.MAX_PREPARATION));
								}
							}else{ //MAX 6 HOURS PER DAY VIOLATED
								s = new StringResponse("6-HOUR-THREAT");
								
								s.setLastName(f.getLastName());
								s.setFirstName(f.getFirstName());
							}
						}else{ //OFFERING COLLISION
							s = new StringResponse("OFFERING-COLLISION");
							
							s.setLastName(f.getLastName());
							s.setFirstName(f.getFirstName());
						}
						/****************************FACULTY ASSIGNMENT PROPER******************************/
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						
					}
				} else { // create new loads id
					LoadDAO.addNewLoadsId(facultyId, startYear, endYear, term);
					
					// remove faculty from course if any (make faculty id = 1)
					String currentFacultyId = OfferingDAO.getFacultyIdUsingOfferingId(offeringId);
					if(!currentFacultyId.equals("1")) { // 1 - no professor, no need to decrement
						currentloadsId = LoadDAO.getLoadsId(currentFacultyId, startYear, endYear, term);		
						OfferingDAO.removeFacultyFromOffering(offeringId);
						FacultyDAO.decrementLoad(currentloadsId, units);
					}
					
					// faculty assignment proper
					loadsId = LoadDAO.getLoadsId(facultyId, startYear, endYear, term);
					FacultyDAO.assignFacultyToOffering(offeringId, facultyId); // assign the faculty id to offering id
					FacultyDAO.incrementLoad(loadsId, units);
					LoadDAO.updateFacultyPreparationCount(facultyId, startYear, endYear, term); //UPDATE PREPARATION COUNT
					System.out.println("Finished adding units to faculty!");
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				
			}

		} else // the faculty is already assigned to the offering
			System.out.println("The faculty is already assigned to this offering!");

		return s; //StringResponse
	}

	@RequestMapping(value="/removeLoadFromFacultyCVC",method= RequestMethod.POST, produces ="application/json")
	public @ResponseBody
    StringResponse removeLoadFromFacultyCVC(
			@RequestParam("offeringId") String offeringId,
			@RequestParam("facultyId") String facultyId,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term){
		
		StringResponse sr = new StringResponse("success");
		String loadsId ="";
		float units = 0;
		
		try {
			Offering o = OfferingDAO.getOfferingByID(offeringId);
			ArrayList<Offering> initialOfferingList = OfferingDAO.getListOfferingsByFaculty(facultyId, startYear, endYear, term);
			
			/****************************DECREASE PREPARATION COUNT******************************/
			int foundEquivalent = 0;
			
	        for(int j = 0; j < initialOfferingList.size(); j++){
	        	if(initialOfferingList.get(j).getCourseId().equalsIgnoreCase(o.getCourseId())){ //if same kasi ng courseID/code/name binabawan parin
		        	if(EquivalenceDAO.checkIfTwoCoursesAreEquivalent(initialOfferingList.get(j).getCourseId()+"", o.getCourseId()+"") == 1){
		        		foundEquivalent = 1; //to not decrease since may equivalent
		        	}
	        	}else{
	        		foundEquivalent = 1; // to not decrease since may kasame na course
	        	}
	        }
	        
	        if(foundEquivalent == 0) LoadDAO.decreaseFacultyPreparationCount(facultyId, startYear, endYear, term);
	        /****************************DECREASE PREPARATION COUNT******************************/
	        
	        OfferingDAO.removeFacultyFromOffering(offeringId);
			loadsId = LoadDAO.getLoadsId(facultyId, startYear, endYear, term);
			units = OfferingDAO.getNumberOfUnits(offeringId);
			FacultyDAO.decrementLoad(loadsId, units);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sr;
	}
	
	@RequestMapping(value="/populateCollegeDropdownCVC", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateCollegesDropdown() {
		ArrayList<String> typeList = new ArrayList<String>();
		
		try {
			typeList = CollegeDAO.getAllUniqueColleges();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return typeList;
	}
	
	@RequestMapping(value="/populateDepartmentDropdownCVC", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateDepartmentsDropdown(
			@RequestParam("selectedCollege") String selectedCollege) {
		ArrayList<String> typeList = new ArrayList<String>();
		
		try {
			typeList = DepartmentDAO.getAllUniqueDepartmentsOfCollege(selectedCollege);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return typeList;
	}

	/*********************FACULTY RELATED METHODS****************************/
	
	public boolean checkOfferingCollision(Offering o, ArrayList<Offering> offeringList){
		boolean hasViolatedRule = false;
		
		for(int i = 0; i < o.getDays().size(); i++){
			Days day1 = o.getDays().get(i); //get single day from offering to be added
			Integer sumOfHours = getSumOfHours(Integer.parseInt(day1.getBeginTime()), Integer.parseInt(day1.getEndTime())); //add ung hours ng offering to be added
			Integer hoursDifference = sumOfHours / 100;
			Integer minutesDifference = sumOfHours % 100;
			
			for(int j = 0; j < offeringList.size(); j++){
				ArrayList<Days> daysList = offeringList.get(j).getDays(); //days of the offering in DB
				
				//System.out.println("SIZEs: " + daysList.size());
				for(int k = 0; k < daysList.size(); k++){
					Days day2 = daysList.get(k); //get single day from offering in DB
					//System.out.println(sumOfHours + " SUM FIRST HOURS");
					if(day1.getClassDay().equalsIgnoreCase(day2.getClassDay())){
						
						if((Integer.parseInt(day1.getBeginTime()) >=  Integer.parseInt(day2.getBeginTime())) && (Integer.parseInt(day1.getEndTime()) <=  Integer.parseInt(day2.getEndTime()))){
							return true; // sched is between
						}else if((Integer.parseInt(day1.getBeginTime()) >=  Integer.parseInt(day2.getBeginTime())) && (Integer.parseInt(day1.getEndTime()) >  Integer.parseInt(day2.getEndTime()))){
							if((Integer.parseInt(day1.getBeginTime()) <  Integer.parseInt(day2.getEndTime()))){
								return true;
							}
						}else if((Integer.parseInt(day1.getBeginTime()) <  Integer.parseInt(day2.getBeginTime())) && (Integer.parseInt(day1.getEndTime()) <=  Integer.parseInt(day2.getEndTime()))){
							if((Integer.parseInt(day1.getEndTime()) >  Integer.parseInt(day2.getBeginTime()))){
								return true;
							}
						}else if((Integer.parseInt(day1.getBeginTime()) <  Integer.parseInt(day2.getBeginTime())) && (Integer.parseInt(day1.getEndTime()) >  Integer.parseInt(day2.getEndTime()))){
							return true;
						}
					}
				}
			}
		}
		
		return hasViolatedRule;
	}
	
	public boolean checkIfSixHoursRuleViolated(Offering o, ArrayList<Offering> offeringList){
		boolean hasViolatedRule = false;
		
		for(int i = 0; i < o.getDays().size(); i++){
			Days day1 = o.getDays().get(i); //get single day from offering to be added
			Integer sumOfHours = getSumOfHours(Integer.parseInt(day1.getBeginTime()), Integer.parseInt(day1.getEndTime())); //add ung hours ng offering to be added
			Integer hoursDifference = sumOfHours / 100;
			Integer minutesDifference = sumOfHours % 100;
			
			for(int j = 0; j < offeringList.size(); j++){
				ArrayList<Days> daysList = offeringList.get(j).getDays(); //days of the offering in DB
				
				//System.out.println("SIZEs: " + daysList.size());
				for(int k = 0; k < daysList.size(); k++){
					Days day2 = daysList.get(k); //get single day from offering in DB
					//System.out.println(sumOfHours + " SUM FIRST HOURS");
					if(day1.getClassDay().equalsIgnoreCase(day2.getClassDay())){
						//System.out.println(day2.getClassDay() + " " + day2.getBeginTime() + " " + day2.getEndTime());
						//System.out.println(sumOfHours + " SUM INITIAL HOURS");
						sumOfHours = getSumOfHours(Integer.parseInt(day2.getBeginTime()), Integer.parseInt(day2.getEndTime())); //add ung hours ng offering na nasa DB
						System.out.println(sumOfHours + " SUM AFTER HOURS");
						hoursDifference += sumOfHours / 100;
						minutesDifference += sumOfHours % 100;
						
						
						if(minutesDifference > 60){
							hoursDifference += (minutesDifference/60); 
							minutesDifference -= 60;
						}
						System.out.println(hoursDifference + " ==== " + minutesDifference);
						System.out.println(hoursDifference + "DIFF HOURS");
						
						if(hoursDifference > 6){ //if more than 6 hours na ung cours ena to. maviviolate na nya ung 6 hours
							hasViolatedRule = true;
							//optional return below
							return true;
						}
					}
				}
			}
		}
		
		return hasViolatedRule;
	}
	
	public boolean checkIfConsecutiveHoursRuleViolated(Offering o, ArrayList<Offering> offeringList){
		boolean hasViolatedRule = false;
		
		for(int i = 0; i < o.getDays().size(); i++){
			Days day1 = o.getDays().get(i); //get single day from offering to be added
			Integer sumOfHours = getSumOfHours(Integer.parseInt(day1.getBeginTime()), Integer.parseInt(day1.getEndTime())); //add ung hours ng offering to be added
			Integer hoursDifference = sumOfHours / 100;
			Integer minutesDifference = sumOfHours % 100;
			ArrayList<Days> previousSchedList = new ArrayList<Days>();
			ArrayList<Days> nextSchedList = new ArrayList<Days>();
			Boolean hasPreviousSched = false, hasNextSched = false, hasPreviousPreviousSched = false, hasNextNextSched = false;
			Days previousSched = new Days("","","");
			Days nextSched = new Days("","","");
			Days previousPreviousSched = new Days("","","");
			Days nextNextSched = new Days("","","");
			Integer consecutiveCounterForTheDay = 0; //tracks the number of consecutive
			
			for(int j = 0; j < offeringList.size(); j++){
				ArrayList<Days> daysList = offeringList.get(j).getDays(); //days of the offering in DB
				//System.out.println("-------->"+offeringList.size());
				//System.out.println("SIZEs: " + daysList.size());
				for(int k = 0; k < daysList.size(); k++){
					Days day2 = daysList.get(k); //get single day from offering in DB
					//System.out.println(sumOfHours + " SUM FIRST HOURS");
					
					//System.out.println("***"+day2.getBeginTime());
					if(day1.getClassDay().equalsIgnoreCase(day2.getClassDay())){
						
						/****************SORT TO APPROPRIATE ARRAYLISTS*********************/
						if((Integer.parseInt(day1.getBeginTime()) > Integer.parseInt(day2.getBeginTime())) && (Integer.parseInt(day1.getEndTime()) >= Integer.parseInt(day2.getBeginTime()))){
							//if day 1 sched is below the sched in faculty's offering list
							previousSchedList.add(day2); //kasi nasa baba ung iaadd na offering
							
							if(hasPreviousSched){ //if true meaning may nakalagay na sa previousSched na variable. Else, first time siya lalagyan
								//pag pumasok dito meron nang previous sched. 
								//Now, compare the previous at ung current Day2(another sched sa list ng faculty) kung sino mas malapit sa sched ng offering na iaadd
								
								if(checkIfPreviousSchedNeedsToChange(previousSched, day2)){
									
									if(hasPreviousPreviousSched){
										if(checkIfPreviousSchedNeedsToChange(previousPreviousSched, previousSched)){
											previousPreviousSched = new Days(previousSched.getClassDay(), previousSched.getBeginTime(), previousSched.getEndTime());
											hasPreviousPreviousSched = true;
										}
									}else{
										previousPreviousSched = new Days(previousSched.getClassDay(), previousSched.getBeginTime(), previousSched.getEndTime());
										hasPreviousPreviousSched = true;
									}
									
									previousSched = new Days(day2.getClassDay(), day2.getBeginTime(), day2.getEndTime()); //pag sa una to ung previousPrevious sched magiging same
								}else{
									if(hasPreviousPreviousSched){
										if(checkIfPreviousSchedNeedsToChange(previousPreviousSched, day2)){
											previousPreviousSched = new Days(day2.getClassDay(), day2.getBeginTime(), day2.getEndTime());
											hasPreviousPreviousSched = true;
										}
									}else{
										previousPreviousSched = new Days(day2.getClassDay(), day2.getBeginTime(), day2.getEndTime());
										hasPreviousPreviousSched = true;
									}
								}
								
							}else{
								previousSched = new Days(day2.getClassDay(), day2.getBeginTime(), day2.getEndTime());
								hasPreviousSched = true;
							}
						
						}else if((Integer.parseInt(day1.getBeginTime()) <= Integer.parseInt(day2.getEndTime())) && (Integer.parseInt(day1.getEndTime()) < Integer.parseInt(day2.getEndTime()))){
							//if day 1 sched is after the sched in the faculty's offering list
							nextSchedList.add(day2); //kasi nasa baba ung iaadd na offering
							
							if(hasNextSched){ //if true meaning may nakalagay na sa previousSched na variable. Else, first time siya lalagyan
								//pag pumasok dito meron nang previous sched. 
								//Now, compare the previous at ung current Day2(another sched sa list ng faculty) kung sino mas malapit sa sched ng offering na iaadd
								
								if(checkIfNextSchedNeedsToChange(nextSched, day2)){
									
									if(hasNextNextSched){
										if(checkIfNextSchedNeedsToChange(nextNextSched, nextSched)){
											nextNextSched = new Days(nextSched.getClassDay(), nextSched.getBeginTime(), nextSched.getEndTime());
											hasNextNextSched = true;
										}
									}else{
										nextNextSched = new Days(nextSched.getClassDay(), nextSched.getBeginTime(), nextSched.getEndTime());
										hasNextNextSched = true;
									}
									
									nextSched = new Days(day2.getClassDay(), day2.getBeginTime(), day2.getEndTime()); //pag sa una to ung previousPrevious sched magiging same
								}else{
									if(hasNextNextSched){
										if(checkIfNextSchedNeedsToChange(nextNextSched, day2)){
											nextNextSched = new Days(day2.getClassDay(), day2.getBeginTime(), day2.getEndTime());
											hasNextNextSched = true;
										}
									}else{
										nextNextSched = new Days(day2.getClassDay(), day2.getBeginTime(), day2.getEndTime());
										hasNextNextSched = true;
									}
								}
								
							}else{
								nextSched = new Days(day2.getClassDay(), day2.getBeginTime(), day2.getEndTime());
								hasNextSched = true;
							}
						}
						/****************SORT TO APPROPRIATE ARRAYLISTS*********************/
						/****************CHECK IF RULE VIOLATION*********************/
						Integer timeDifference = 0;
						Integer previousSchedSum = 0, nextSchedSum = 0, previousPreviousSchedSum = 0, nextNextSchedSum = 0;
						if(hasPreviousSched) previousSchedSum = getSumOfHours(Integer.parseInt(previousSched.getBeginTime()), Integer.parseInt(previousSched.getEndTime()));
						if(hasNextSched) nextSchedSum = getSumOfHours(Integer.parseInt(nextSched.getBeginTime()), Integer.parseInt(nextSched.getEndTime()));
						Integer offeringSum = getSumOfHours(Integer.parseInt(day1.getBeginTime()), Integer.parseInt(day1.getEndTime()));
						if(hasPreviousPreviousSched) previousPreviousSchedSum = getSumOfHours(Integer.parseInt(previousPreviousSched.getBeginTime()), Integer.parseInt(previousPreviousSched.getEndTime()));
						if(hasNextNextSched) nextNextSchedSum = getSumOfHours(Integer.parseInt(nextNextSched.getBeginTime()), Integer.parseInt(nextNextSched.getEndTime()));
						
						//System.out.println(day1.getClassDay() + "--" + hasPreviousPreviousSched + "--"+ hasPreviousSched + "--" + hasNextSched + "--" + hasNextNextSched);
						//System.out.println(day1.getClassDay() + "--" + previousPreviousSched.getBeginTime() + "--"+ previousSched.getBeginTime() + "--" + nextSched.getBeginTime() + "--" + nextNextSched.getBeginTime());
						
						if(hasPreviousSched && hasNextSched){ //will be inserting sched in the middle
							if(getTimeIntervalInBetween(previousSched, day1) < 45 && getTimeIntervalInBetween(day1, nextSched) < 45){ 
								if(getTimeEquivalentInHours(previousSchedSum + nextSchedSum + offeringSum) > 4.5){
									return true; 
								}
							}else{
								if(hasPreviousPreviousSched){
									if(getTimeIntervalInBetween(previousPreviousSched, previousSched) < 45 && getTimeIntervalInBetween(previousSched, day1) < 45){
										if(getTimeEquivalentInHours(previousPreviousSchedSum + previousSchedSum + offeringSum) > 4.5){
											return true; 
										}else if(getTimeIntervalInBetween(day1, nextSched) < 45 && getTimeEquivalentInHours(previousPreviousSchedSum + previousSchedSum + offeringSum + nextSchedSum) > 4.5){
											return true;
										}
									}
								}else if(hasNextNextSched){
									if(getTimeIntervalInBetween(day1, nextSched) < 45 && getTimeIntervalInBetween(nextSched, nextNextSched) < 45){
										if(getTimeEquivalentInHours(offeringSum + nextSchedSum + nextNextSchedSum) > 4.5){
											return true; 
										}else if(getTimeIntervalInBetween(previousSched, day1) < 45 && getTimeEquivalentInHours(previousSchedSum + offeringSum + nextSchedSum + nextNextSchedSum) > 4.5){
											return true;
										}
									}
								}
							}
						}else if(hasPreviousSched && !hasNextSched){ //will be at the later part of the day
							if(hasPreviousPreviousSched){
								if(getTimeIntervalInBetween(previousPreviousSched, previousSched) < 45 && getTimeIntervalInBetween(previousSched, day1) < 45){ 
									if(getTimeEquivalentInHours(previousPreviousSchedSum + previousSchedSum + offeringSum) > 4.5){
										return true; 
									}
								}
							}
						}else if(!hasPreviousSched && hasNextSched){ //will be at the earlier part of the day
							if(hasNextNextSched){
								if(getTimeIntervalInBetween(day1, nextSched) < 45 && getTimeIntervalInBetween(nextSched, nextNextSched) < 45){ 
									if(getTimeEquivalentInHours(offeringSum + nextSchedSum + nextNextSchedSum) > 4.5){
										return true; 
									}
								}
							}
						}
						/****************CHECK IF RULE VIOLATION*********************/
					}
					
				}
			}
		}
		return hasViolatedRule;
	}
	
	public boolean checkIfPreviousSchedNeedsToChange(Days sched1, Days sched2){
		
		if(Integer.parseInt(sched1.getEndTime()) < Integer.parseInt(sched2.getEndTime())){ //current Sched is nearer
			//sched1 = new Days(sched2.getClassDay(), sched2.getBeginTime(), sched2.getEndTime());
			return true;
		}else if(Integer.parseInt(sched1.getEndTime()) == Integer.parseInt(sched2.getEndTime())){ //same sila ng end time
			//if same ng end time, calculate sino mas malayo na startTime tapos ung biggest kunin para mas malaki ung timeDifference. Mas malalaman if naviolate ung 4.5 rule
			if(Integer.parseInt(sched1.getBeginTime()) < Integer.parseInt(sched2.getBeginTime())){ //previous Sched is much earlier but same end Time. Viable
				// dont change since previous sched is still previous sched
			}else if(Integer.parseInt(sched1.getBeginTime()) > Integer.parseInt(sched2.getBeginTime())){ //day2 Sched is much earlier but same end Time. Viable
				//sched1 = new Days(sched2.getClassDay(), sched2.getBeginTime(), sched2.getEndTime());
				return true;
			}else{
				//same sila ng time. retain previous sched.
			}
		}else{
			//previous sched is much nearer
		}
		
		return false;
	}
	
	private boolean checkIfNextSchedNeedsToChange(Days sched1, Days sched2){
		
		if(Integer.parseInt(sched1.getBeginTime()) > Integer.parseInt(sched2.getBeginTime())){ //current Sched is nearer
			return true;//sched1 = new Days(sched2.getClassDay(), sched2.getBeginTime(), sched2.getEndTime());
		}else if(Integer.parseInt(sched1.getBeginTime()) == Integer.parseInt(sched2.getBeginTime())){ //same sila ng begin time
			//if same ng begin time, calculate sino mas malayo na endTime tapos ung biggest kunin para mas malaki ung timeDifference. Mas malalaman if naviolate ung 4.5 rule
			if(Integer.parseInt(sched1.getEndTime()) > Integer.parseInt(sched2.getEndTime())){ //next Sched ends much later than day2 even though same begin Time. Viable
				// dont change since previous sched is still previous sched
			}else if(Integer.parseInt(sched1.getEndTime()) < Integer.parseInt(sched2.getEndTime())){ //day2 Sched ends much later than nextSched even though same begin Time. Viable
				return true;//sched1 = new Days(sched2.getClassDay(), sched2.getBeginTime(), sched2.getEndTime());
			}else{
				//same sila ng time. retain next sched.
			}
		}else{
			//next sched is much nearer
		}
		
		return false;
	}
	
	public Integer getTimeIntervalInBetween(Days sched1, Days sched2){
		//System.out.println(Integer.parseInt(sched1.getEndTime()) + "--" + Integer.parseInt(sched2.getBeginTime()));
		
		return getSumOfHours(Integer.parseInt(sched1.getEndTime()), Integer.parseInt(sched2.getBeginTime()));
	}
	
	public Float getTimeEquivalentInHours(Integer sumOfHours){
		Float finalDifference = 0f;
		
		Integer hoursDifference = sumOfHours / 100;
		Integer minutesDifference = sumOfHours % 100;
		
		finalDifference = (float)hoursDifference + (float)minutesDifference;
		if(minutesDifference > 60){
			finalDifference += finalDifference + (float)((float)minutesDifference/60); 
			minutesDifference -= 60;
		}
		
		return finalDifference;
	}
	
	public Integer getSumOfHours(Integer startTime, Integer endTime){
    	int timeDifference = 0;
    	String firstTime = "00:00:00", secondTime = "00:00:00";
    	//Integer startTime = 730, endTime = 900;
    	long difference = 0;
    
    	//System.out.println(startTime);
		
		if(startTime / 100 >= 10) firstTime = (startTime / 100) + ":" + (startTime % 100) + ":00";
		else firstTime = "0" + (startTime / 100) + ":" + (startTime % 100) + ":00"; //need 0 sa harap pag single digit lang ung hour
		
		if(endTime / 100 >= 10) secondTime = (endTime / 100) + ":" + (endTime % 100) + ":00";
		else secondTime = "0" + (endTime / 100) + ":" + (endTime % 100) + ":00"; //need 0 sa harap pag single digit lang ung hour

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		
		try {
			Date date1 = format.parse(firstTime);
			Date date2 = format.parse(secondTime);
			difference = date2.getTime() - date1.getTime();
          
          	//System.out.println("Hour: " + (difference / (60 * 60 * 1000)*100));
          	//System.out.println("Minutes: " + (difference / (60 * 1000) % 60));
          	
          	timeDifference = (int)((int)difference / (60 * 60 * 1000) * 100) + (int)((int)difference / (60 * 1000) % 60);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Math.abs(timeDifference);
	}
	
	
	/**************************GET LIST OF FACULTY*******************************/
	@RequestMapping(value="/getFacultyList", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Faculty> getFacultyList(@RequestParam("term") String input) {
		String[] processed = input.split("-");
		String startYear = processed[0];
		String endYear = processed[1];
		String term = processed[2];
		
		try {
			listFaculty = FacultyDAO.getListFacultyByTerm(startYear, endYear, term);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listFaculty;
	}
	/**************************GET LIST OF FACULTY*******************************/
	/**************************GET LIST OF LOAD TIMEFRAME************************/
	@RequestMapping(value="/getLoadTimeframeList", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Timeframe> getLoadTimeframe() {
		if(listTimeframe.isEmpty()) {
			try {
				listTimeframe = LoadDAO.getLoadAY();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return listTimeframe;
	}
	
	/**************************GET LIST OF LOAD TIMEFRAME************************/

	/**************************GET LIST OF FACULTY LOAD*******************************/
	/* Get current load of a faculty for a specific term and school year */
	@RequestMapping(value="/getAllFacultyLoadWithFilters", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Faculty> getAllFacultyLoadWithFilters(
			@RequestParam("term") String term,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("collegeId") String collegeId,
			@RequestParam("deptId") String deptId) {
		
		ArrayList<Faculty> facultyList = new ArrayList<Faculty>();
		try {
			facultyList =  FacultyDAO.getAllFacultyLoadWithFilters(startYear, endYear, term, collegeId, deptId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return facultyList;
	}
	
	@RequestMapping(value="/getAllFacultyLoadByTimeBlock", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<LoadTimeblock> getAllFacultyLoadByTimeBlock(
			@RequestParam("term") String term,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("facultyId") String facultyId) {
		
		ArrayList<LoadTimeblock> timeblockList = new ArrayList<LoadTimeblock>();
		try {
			timeblockList =  DaysDAO.getUniqueTimeBlocksOfFaculty(startYear, endYear, term, facultyId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return timeblockList;
	}
	
	@RequestMapping(value="/getAllLoadsOfFaculty", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Offering> getAllLoadsOfFaculty(
			@RequestParam("facultyId") String facultyId,
			@RequestParam("term") String term,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear) {
		
		ArrayList<Offering> loadList = new ArrayList();
		try {
			loadList =  OfferingDAO.getListOfferingsByFaculty(facultyId, startYear, endYear, term);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return loadList;
		
	}
	
	@RequestMapping(value="/getCurrentFacultyLoad", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Offering> getCurrentFacultyLoad(
			@RequestParam("facultyId") int facultyId,
			@RequestParam("term") int term,
			@RequestParam("startYear") int startYear,
			@RequestParam("endYear") int endYear) {
		
		ArrayList<Offering> loadList = new ArrayList();
		try {
			loadList =  FacultyDAO.getCurrentFacultyLoad(facultyId, term, startYear, endYear);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return loadList;
		
	}
	
	@RequestMapping(value="/getCurrentFacultyLoadSpecific", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    Load getCurrentFacultyLoadSpecific(
			@RequestParam("facultyId") int facultyId,
			@RequestParam("startYear") int startYear,
			@RequestParam("endYear") int endYear,
			@RequestParam("term") int term) {
		
		Load facultyLoad = new Load();
		try {
			facultyLoad =  FacultyDAO.getCurrentFacultyLoadSpecific(facultyId, startYear, endYear, term);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return facultyLoad;
		
	}
	
	
	/* Get current course offerings for a term, this is arrange in faculty id ascending order (therefore, it shows the offerings with no faculty first) */
	@RequestMapping(value="/getCVCOfferings", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Offering> getCVCOfferings(@RequestParam("term") String input) {
		String[] processed = input.split("-");
		String startYear = processed[0];
		String endYear = processed[1];
		String term = processed[2];
		
		try {
			listOffering = OfferingDAO.getListOfferingsByTermASC(startYear, endYear, term);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return listOffering;
	}
	/**************************GET LIST OF FACULTY LOAD*******************************/
	/**************************ADD NEW COURSE*******************************/
	@RequestMapping(value="/getAllCoursesSuggestion", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> getAllCourseSuggestionByCollege(@RequestParam("userId") String userId) {
		ArrayList<String> courseList = new ArrayList();
		User user = null;
		
		try {
			user = UserDAO.getUserByID(userId);
			courseList = CourseDAO.getAllCourseSuggestionByCollege(user.getCollegeID());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return courseList;
	}
	
	@RequestMapping(value="/getSingleDepartment", method= RequestMethod.GET)
	public @ResponseBody
    Envelope getSingleDeptAddNewCourse(@RequestParam("userId") String userId) {
		Department dept = new Department();
		User user = null;
		
		try {
			user = UserDAO.getUserByID(userId);
			dept = DepartmentDAO.getDepartmentByID(user.getDeptID());
			
//			System.out.println(user.getDeptID());
//			System.out.println(dept.getDeptCode());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Envelope(dept.getDeptCode());
	}
	
	@RequestMapping(value="/addNewCourseToDB", method= RequestMethod.POST, produces="application/json")
	public @ResponseBody
    Envelope insertNewCourseIntoDB(@RequestParam Map<String, String> params,
                                   @RequestParam("newCourseReq[]") String[] newCourseReq,
                                   @RequestParam("newCourseReqType[]") String[] newCourseReqType) {

		College college = null;
		Department dept = null;
		User user = null;
		
		try {
			user = UserDAO.getUserByID(params.get("userId"));
			college = CollegeDAO.getCollegeByID(user.getCollegeID());
			dept = DepartmentDAO.getDepartmentByID(user.getDeptID());
			
			Course course = new Course(college.getId(), dept.getId(), "0", 
					params.get("newCourseCourseCode").toUpperCase(), params.get("newCourseCourseTitle"), 
					params.get("newCourseFacUnits"), "", params.get("newCourseCourseDesc"));
			
			CourseDAO.insertCourseToDB(course);
			
			for(int i = 0; i < newCourseReq.length; i++) {
				if(newCourseReqType[i].toUpperCase().equals("EQUIVALENT")) {
					String currCourseId = CourseDAO.getIDByCode(course.getCourseCode());
					String equivalentCourseId = CourseDAO.getIDByCode(newCourseReq[i].toUpperCase());
					
					EquivalenceDAO.insertEquivalenceIntoDB(currCourseId, equivalentCourseId);
					
					System.out.println("Equivalence added: " + course.getCourseCode() + "|" + newCourseReq[i]);
				} else if(newCourseReqType[i].toUpperCase().equals("PRE-REQ") || newCourseReqType[i].toUpperCase().equals("POST-REQ")) {
					String currCourseId = CourseDAO.getIDByCode(course.getCourseCode());
					String requisiteCourseId = CourseDAO.getIDByCode(newCourseReq[i].toUpperCase());
					
					RequisiteDAO.insertRequisiteIntoDB(currCourseId, requisiteCourseId, newCourseReqType[i]);
					
					System.out.println(newCourseReqType[i] + " added: " + course.getCourseCode() + "|" + newCourseReq[i]);
				}
			}
			
			System.out.println("Insertion Complete"); 
			
			return new Envelope(course.getCourseCode() + " was successfully added!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Envelope("Error! The faculty was not added successfully!");
	}
	/**************************ADD NEW COURSE*******************************/
	/**************************ADD NEW FACULTY******************************/
	@RequestMapping(value="/addNewFacultyToDB", method= RequestMethod.POST, produces="application/json")
	public @ResponseBody
    Envelope insertNewFacultyIntoDB(@RequestParam Map<String, String> params) {
		College college = null;
		Department dept = null;
		User user = null;
		User temp = null;
		Faculty newFaculty = null;
		String facultyType = "";
		String salt = new RandomString().nextString();
		String newPassword = Scrambler.getHashed512(params.get("newFacultyPassword"), salt) + "|" + salt;
		
		System.out.println("newPass: " +newPassword);
		
		try {
			user = UserDAO.getUserByID(params.get("userId"));
			college = CollegeDAO.getCollegeByID(user.getCollegeID());
			dept = DepartmentDAO.getDepartmentByID(user.getDeptID());
			
			System.out.println("College: " + college.getCollegeCode());
			System.out.println("Dept: " + dept.getDeptCode());
			
			temp = new User(params.get("newFacultyID"), user.getCollegeID(), user.getDeptID(), params.get("newFacultyFirstName"), 
					params.get("newFacultyMiddleName"), params.get("newFacultyLastName"), "Faculty",
					newPassword);
			
			switch(params.get("newFacultyType")) {
				case "Full-time": 
					facultyType = "FT";
					break;
				case "Part-time": 
					facultyType = "PT";
					break;
				case "Half-time": 
					facultyType = "HT";
					break;
			}
			
			newFaculty = new Faculty(temp, params.get("newFacultyYearStarted"), facultyType);
			
			FacultyDAO.insertFacultyToDB(newFaculty);
			
			System.out.println("Faculty insertion complete");
			
			return new Envelope(temp.getLastName() + ", " + temp.getFirstName() + " was successfully added!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new Envelope("Error! The faculty was not added successfully!");
	}
	/**************************ADD NEW FACULTY******************************/

	public static String removeSpaces(String s){
		s = s.replaceAll("\\s",""); 
		return s;
	}
	
	private class Envelope{
		String message;

		public Envelope() {
			this.message = "Something went wrong";
		}
		
		public Envelope(String msg) {
			this.message = msg;
		}
		
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
	
/********************** DELOADING *************************************/
	
	@RequestMapping(value="/getFacultyListWithKeyViaDeloading", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Faculty> getFacultyListWithKeyViaDeloading(
			@RequestParam("searchKeyword") String searchKeyword,
			@RequestParam("departmentId") String departmentId) {
		
		ArrayList<Faculty> facultyList = new ArrayList<Faculty>();
		//TODO: Make facultyDAO get all same department w/ search

		try {
			
			facultyList = FacultyDAO.getAllFacultyFromSameDepartmentWithKey(departmentId, searchKeyword);
			
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return facultyList;
	}
	
	@RequestMapping(value="/getDeloadingList", method= RequestMethod.GET, produces ="application/json")
	public @ResponseBody
    ArrayList<Deloading> getDeloadingList() {
		
		ArrayList<Deloading> deloadingList = new ArrayList<Deloading>();
		
		try {
			deloadingList = DeloadingDAO.getDeloadingList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(deloadingList.size());
		return deloadingList;
		
	}
	
	@RequestMapping(value="/getFacultyDeloading", method={RequestMethod.GET}, produces ="application/json")
	public @ResponseBody
    ArrayList<DeloadOffer> getFacultyDeloading(
			@RequestParam("facultyId") String facultyId,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term)
		 {
		
		ArrayList<DeloadOffer> facultyDeloadingList = new ArrayList<DeloadOffer>();
		
		try {
			facultyDeloadingList = DeloadingDAO.getFacultyDeloadingList(facultyId, startYear, endYear, term);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return facultyDeloadingList;
	}
	
	@RequestMapping(value="/getFacultyDeloadingAll", method={RequestMethod.GET}, produces ="application/json")
	public @ResponseBody
    ArrayList<DeloadOffer> getFacultyDeloading(
			@RequestParam("facultyId") String facultyId)
		 {
		
		ArrayList<DeloadOffer> facultyDeloadingList = new ArrayList<DeloadOffer>();
		
		try {
			facultyDeloadingList = DeloadingDAO.getFacultyDeloadingListAll(facultyId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return facultyDeloadingList;
	}
	
	@RequestMapping(value="/adminDeloading", method={RequestMethod.POST}, produces ="application/json")
	public @ResponseBody
    StringResponse adminDeloading(
			@RequestParam("facultyId") String facultyId,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term,
			@RequestParam("deloadingId") String deloadingId) {
		
		StringResponse sr = new StringResponse("success");
		
		//TODO: insert/update deloadoffer and loads table
		try {
			DeloadingDAO.adminDeloading(facultyId, startYear, endYear, term, deloadingId);
			LoadDAO.updateAdminLoad(facultyId, startYear, endYear, term, deloadingId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sr;

	}
	
	@RequestMapping(value="/researchDeloading", method={RequestMethod.POST}, produces ="application/json")
	public @ResponseBody
    StringResponse researchDeloading(
			@RequestParam("facultyId") String facultyId,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term,
			@RequestParam("deloadingId") String deloadingId) {
		
		StringResponse sr = new StringResponse("success");
		
		//TODO: insert/update deloadoffer and loads table
		try {
			DeloadingDAO.researchDeloading(facultyId, startYear, endYear, term, deloadingId);
			LoadDAO.updateResearchLoad(facultyId, startYear, endYear, term, deloadingId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sr;

	}
	
	@RequestMapping(value="/leaveDeloading", method={RequestMethod.POST}, produces ="application/json")
	public @ResponseBody
    StringResponse leaveDeloading(
			@RequestParam("facultyId") String facultyId,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term,
			@RequestParam("deloadingId") String deloadingId) {
		
		StringResponse sr = new StringResponse("success");
		
		//TODO: insert/update deloadoffer and loads table
		try {
			DeloadingDAO.leaveDeloading(facultyId, startYear, endYear, term, deloadingId);
			LoadDAO.updateLeaveLoad(facultyId, startYear, endYear, term, deloadingId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sr;

	}
	
	@RequestMapping(value="/otherDeloading", method={RequestMethod.POST}, produces ="application/json")
	public @ResponseBody
    StringResponse otherDeloading(
			@RequestParam("facultyId") String facultyId,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term,
			@RequestParam("deloadingId") String deloadingId) {
		
		StringResponse sr = new StringResponse("success");
		
		//TODO: insert/update deloadoffer and loads table
		try {
			DeloadingDAO.otherDeloading(facultyId, startYear, endYear, term, deloadingId);
			LoadDAO.updateOtherLoad(facultyId, startYear, endYear, term, deloadingId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sr;

	}
	
	@RequestMapping(value="/removeDeloading",method= RequestMethod.POST, produces ="application/json")
	public @ResponseBody
    StringResponse removeDeloadingFromFaculty(
			@RequestParam("deloadofferId") String deloadofferId){
		
		StringResponse sr = new StringResponse("success");
		
		DeloadOffer deloadoffering = new DeloadOffer();

		
		try {
			deloadoffering = DeloadingDAO.getDeloadOfferingByIdOnly(deloadofferId);
			int units = Integer.parseInt(deloadoffering.getDeloading().getDeloadUnits());
			String facultyId = deloadoffering.getFacultyId();
			String startYear = deloadoffering.getStart_year();
			String endYear = deloadoffering.getEnd_year();
			String term = deloadoffering.getTerm();			
			
			LoadDAO.decrementDeloading(facultyId, startYear, endYear, term, units);
			LoadDAO.removeDeloading(facultyId, startYear, endYear, term, deloadofferId);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sr;
	}
	
	@RequestMapping(value="/addNewDeloading", method={RequestMethod.POST}, produces ="application/json")
	public @ResponseBody
    StringResponse addNewDeloading(
			@RequestParam("deloadingName") String deloadingName,
			@RequestParam("deloadingCode") String deloadingCode,
			@RequestParam("deloadingType") String deloadingType,
			@RequestParam("units") String units,
			@RequestParam("description") String description,
			@RequestParam("deptId") String deptId)
	{
		
		StringResponse sr = new StringResponse("success");
		
		//TODO: insert/update deloadoffer and loads table
		try {
			DeloadingDAO.addNewDeloading(deloadingName, deloadingCode, deloadingType, units, description, deptId);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sr;

	}
	
	@RequestMapping(value="/getFacultyListSameDeparment", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Faculty> getFacultyListSameDepartment(@RequestParam("departmentId") String departmentId) {
		
		try {
			listFaculty = FacultyDAO.getAllFacultyFromSameDepartment(departmentId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listFaculty;
	}
	
	// DELOADING 
}

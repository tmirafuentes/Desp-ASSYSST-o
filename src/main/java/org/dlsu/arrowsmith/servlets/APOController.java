package org.dlsu.arrowsmith.servlets;

import org.dlsu.arrowsmith.models.*;
import org.dlsu.arrowsmith.dao.*;
import org.dlsu.arrowsmith.utility.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

@Controller
public class APOController {
	private ArrayList<Timeframe> timeframes = new ArrayList();
	private ArrayList<String> formatTimeframes = new ArrayList();
	private ArrayList<TempOffering> tempOfferingList = new ArrayList();
	private String timeframeButtonName = "Select Term";
	
	private ArrayList<Degreeprogram> degreeProgramList = new ArrayList();
	private ArrayList<String> degreeList = null;
	private ArrayList<String> batchList = null;
	private ArrayList<String> yearList = null;
	
	private ArrayList<Course> previewCourseList = new ArrayList();
	
	@ModelAttribute
	public void setTimeframeDropDown(Model mav) {
		if(timeframes.isEmpty()) {
			try {
				timeframes = TimeframeDAO.getDistinctTimeframes();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println("Getting Timeframes");
		}
		
		if(formatTimeframes.isEmpty()) {
			if(timeframes != null) {
				for (Timeframe timeframe : timeframes) {
					formatTimeframes.add(timeframe.makeString());
				}
			}
//			System.out.println("Formatting Timeframes");
		}
		
		try {
			if(degreeList == null) {
				degreeList = DegreeprogramDAO.getDegreeList();
			}
			
			if(batchList == null) {
				batchList = FlowchartDAO.getBatchList();
			}
			
			if (yearList == null) {
				yearList = FlowchartDAO.getYearList();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (degreeList != null && degreeProgramList.isEmpty()) {
			for (String string : degreeList) {
				try {
					degreeProgramList.add(DegreeprogramDAO.getCourseByID(string));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		mav.addAttribute("timeframeList", formatTimeframes);
		mav.addAttribute("timeframeButton", timeframeButtonName);
		mav.addAttribute("degreeList", degreeProgramList);
		mav.addAttribute("batchList", batchList);
		mav.addAttribute("yearList", yearList);
	}
	
	@RequestMapping(value="/addCourse", method= RequestMethod.GET)
	public ModelAndView addCoursePage() {
		//System.out.println("-----REFRESHING PREVIEW COURSE LIST-----");
		previewCourseList = new ArrayList();
		ModelAndView mav = new ModelAndView("addCourses");
		
		return mav;
	}
	
	@RequestMapping(value="/roomAssign", method= RequestMethod.GET)
	public ModelAndView assignRoomPage() {
		ArrayList<Offering> offerings = new ArrayList();
		
		try {
			offerings = OfferingDAO.getListOfferingsByTerm("2016", "2017", "2");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ModelAndView mav = new ModelAndView("roomAssignment");
		mav.addObject("startYear", "2016");
		mav.addObject("endYear", "2017");
		mav.addObject("term", "2");
		mav.addObject("offeringList", offerings);
		
		//System.out.println("searchVar: " + searchVar);
		return mav;
	}
	
	@RequestMapping(value="/manageCourse", method= RequestMethod.GET)
	public ModelAndView manageCoursesPage() {
		ModelAndView mav = new ModelAndView("manageCourseOfferings");
		
		return mav;
	}
	
	@RequestMapping(value="/findCourses", method= RequestMethod.POST)
	public ModelAndView getListCourses(@RequestParam("searchVar") String searchVar) {
		ArrayList<Offering> offerings = new ArrayList();
		ArrayList<String> searchConfig = formatSearchVar(searchVar);
		
		try {
			offerings = OfferingDAO.getListOfferingsByTerm(searchConfig.get(0), searchConfig.get(1), searchConfig.get(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ModelAndView mav = new ModelAndView("manageCourseOfferings");
		mav.addObject("timeframeButton", searchVar);
//		timeframeButtonName = searchVar;
		mav.addObject("offeringList", offerings);
		
		//System.out.println("searchVar: " + searchVar);
		return mav;
	}
	
	@RequestMapping(value="/filterCourses", method= RequestMethod.POST)
	public ModelAndView filterCourses(@RequestParam Map<String, String> reqPar) {
		String degree = reqPar.get("degSelect");
		String batch = reqPar.get("batSelect");
		String yearLevel = reqPar.get("yearSelect");
		String term = reqPar.get("termSelect");
		ArrayList<Course> courseList = new ArrayList();
		ArrayList<String> courseIdList = new ArrayList();
		ArrayList<String> formattedYear = splitByDash(yearLevel);
		
		try {
			courseIdList = SpecialDAO.getCourseByID(degree, batch, formattedYear.get(0), formattedYear.get(1), term);
			
			if(!courseIdList.isEmpty()) {
				for (String string : courseIdList) {
					courseList.add(CourseDAO.getCourseByID(string));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ModelAndView mav = new ModelAndView("addCourses");
		mav.addObject("courseList", courseList);
		
		return mav;
	}
	
	@RequestMapping(value="/getCourseById", method= RequestMethod.POST, produces="application/json")
	public @ResponseBody
    ArrayList<Course> getSingleCourse(@RequestParam("courseId") String id) {
		String courseId = splitByDash(id).get(0);
		
		//System.out.println("courseId: " + courseId);
		
		try {
			previewCourseList.add(CourseDAO.getCourseByID(courseId));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return previewCourseList;
	}
	
	@RequestMapping(value="/displayCourseRoomAssign", method= RequestMethod.POST)
	public ModelAndView roomAssignmentClick(
			@RequestParam("inputDumpID") String ID,
			@RequestParam("inputDumpStartYear") String startYear,
			@RequestParam("inputDumpEndYear") String endYear,
			@RequestParam("inputDumpTerm") String term) {
		ArrayList<Offering> offerings = new ArrayList();
		Offering clickedOffering = new Offering();
		
		try {
			clickedOffering = OfferingDAO.getOfferingByID(ID);
			offerings = OfferingDAO.getListOfferingsByTerm(startYear, endYear, term);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ModelAndView mav = new ModelAndView("roomAssignment");
		mav.addObject("startYear", startYear);
		mav.addObject("endYear", endYear);
		mav.addObject("term", term);
		mav.addObject("clickedID", clickedOffering.getOfferingId());
		//mav.addObject("timeframeButton", searchVar);
//		timeframeButtonName = searchVar;
		mav.addObject("clickedOffering", clickedOffering);
		mav.addObject("offeringList", offerings);
		
		//System.out.println("searchVar: " + searchVar);
		return mav;
	}
	
	@RequestMapping(value="/addSections", method= RequestMethod.POST)
	public void getSections(@RequestParam Map<String, String> sectionList) {
		ArrayList<String> res = new ArrayList();
		Set<String> keys = sectionList.keySet();
		
		for (String string : keys) {
			//System.out.println("val: " + sectionList.get(string) + " key: " + string);
		}
		
		for (Course course : previewCourseList) {
			res.add(course.getCourseCode() + "-" + sectionList.get(course.getCourseCode()));
		}
		
		for (String string : res) {
			//System.out.println("section: " + string);
		}
	}
	
	private ArrayList<String> formatSearchVar(String searchVar) {
		ArrayList<String> res = new ArrayList();
		String[] clean = searchVar.split(" ");//contains AY, 20xx-20yy, and TZ 
		String term = clean[2].substring(1);//gets TZ
		
		clean = clean[1].split("-");//Splits 20xx and 20yy. AY should be gone
		
		res.add(clean[0]);
//		System.out.println("clean[0]: " + clean[0]);
		res.add(clean[1]);
//		System.out.println("clean[1]: " + clean[1]);
		res.add(term);
//		System.out.println("term: " + term);
		
		return res;
	}

	private ArrayList<String> splitByDash(String dashMid){
		ArrayList<String> res = new ArrayList();
		List<String> temp = Arrays.asList(dashMid.split("-"));
		
		res.addAll(temp);
		
		return res;
	}
	
	public String getTimeframeButtonName() {
		return timeframeButtonName;
	}

	public void setTimeframeButtonName(String timeframeButtonName) {
		this.timeframeButtonName = timeframeButtonName;
	}
	
	/********************BUILDING RELATED METHODS*************************/
	@RequestMapping(value="/getBuildingsAndRooms", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Building> getBuildingsAndRooms() {
		ArrayList<Building> buildingList = new ArrayList<Building>();
		
		try {
			buildingList = BuildingDAO.getAllBuildingsWithRoomCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return buildingList;
	}
	
	@RequestMapping(value="/getBuildingNameModal", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    StringResponse getBuildingsAndRooms(@RequestParam("buildingId") String buildingId) {
		Building b = new Building();
		StringResponse sr = new StringResponse("");
		try {
			b = BuildingDAO.getBuildingByID(buildingId);
			sr = new StringResponse(b.getBuildingName());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sr;
	}
	
	@RequestMapping(value="/populateBuildingDropdown", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateBuildingDropdown() {
		ArrayList<String> typeList = new ArrayList<String>();
		
		try {
			typeList = BuildingDAO.getAllBuildingNames();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return typeList;
	}
	/********************BUILDING RELATED METHODS*************************/
	/********************ROOM RELATED METHODS*************************/
	@RequestMapping(value="/getRoomsOfBuilding", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Room> getRoomsOfBuilding(
			@RequestParam("buildingId") String buildingId,
			@RequestParam("roomType") String roomType,
			@RequestParam("roomCapacity") String roomCapacity) {
		ArrayList<Room> roomList = new ArrayList<Room>();
		
		try {
			roomList = RoomDAO.getAllRoomsOfBuilding(buildingId, roomType, roomCapacity);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return roomList;
	}
	
	@RequestMapping(value="/populateRoomCapacityDropdown", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateRoomCapacityDropdown() {
		ArrayList<String> capacityList = new ArrayList<String>();
		
		try {
			capacityList = RoomDAO.getAllUniqueRoomCapacity();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return capacityList;
	}
	
	@RequestMapping(value="/populateRoomTypeDropdown", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateRoomTypeDropdown() {
		ArrayList<String> typeList = new ArrayList<String>();
		
		try {
			typeList = RoomDAO.getAllUniqueRoomType();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return typeList;
	}
	
	@RequestMapping(value="/getRoomListWithKey", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Room> getRoomListWithKey(
			@RequestParam("offeringId") String offeringId,
			@RequestParam("searchKeyword") String searchKeyword,
			@RequestParam("roomType") String roomType,
			@RequestParam("building") String building) {
		ArrayList<Room> roomList = new ArrayList<Room>();
		Offering o = new Offering();
		Building b = new Building();
		int tempId = -999;
		
		try {
			o = OfferingDAO.getOfferingByID(offeringId);
			if(building.equalsIgnoreCase("All")){
				tempId = -999;
			}else{
				b = BuildingDAO.getBuildingByName(building);
				tempId = Integer.parseInt(b.getId());
			}
			
			roomList = RoomDAO.getAllRoomsWithSearchKey(o, tempId, roomType, searchKeyword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return roomList;
	}
	
	@RequestMapping(value="/getRoomListANOWithKey", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Room> getRoomListANOWithKey(
			//@RequestParam("offeringId") String offeringId,
			@RequestParam("searchKeyword") String searchKeyword,
			@RequestParam("roomType") String roomType,
			@RequestParam("building") String building) {
		ArrayList<Room> roomList = new ArrayList<Room>();
		Offering o = new Offering();
		Building b = new Building();
		int tempId = -999;
		
		try {
			//o = OfferingDAO.getOfferingByID(offeringId);
			if(building.equalsIgnoreCase("All")){
				tempId = -999;
			}else{
				b = BuildingDAO.getBuildingByName(building);
				tempId = Integer.parseInt(b.getId());
			}
			
			roomList = RoomDAO.getAllRoomsANOWithSearchKey(tempId, roomType, searchKeyword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return roomList;
	}
	
	@RequestMapping(value="/initiateRoomAssignment", method= RequestMethod.POST, produces="application/json")
	public @ResponseBody
    StringResponse initiateRoomAssignment(
			@RequestParam("offeringId") String offeringId,
			@RequestParam("roomId") String roomId){
		
		StringResponse sr = new StringResponse("success");
		
		try {
			DaysDAO.assignRoomToOffering(offeringId, roomId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sr;
	}
	/********************ROOM RELATED METHODS*************************/
	/********************ACADEMIC YEARS/FLOWCHARTS RELATED METHODS***************/
	@RequestMapping(value="/getAllAcademicYears", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<AcademicYear> getAllAcademicYears() {
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
	@RequestMapping(value="/getOfferingListWithKey", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Offering> getOfferingListWithKey(
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
	
	@RequestMapping(value="/getAllOfferingsWithFilters", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Offering> getAllOfferingsWithFilters(
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
	
	@RequestMapping(value="/getAllOfferingsWithoutFilters", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Offering> getAllOfferingsWithoutFilters(
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term) {
		
		ArrayList<Offering> offeringList = new ArrayList<Offering>();
		
		try {
			offeringList = OfferingDAO.getListOfferingsByTerm(startYear, endYear, term);
			//System.out.println(offeringList.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return offeringList;
	}
	
	@RequestMapping(value="/publishAYTerm", method= RequestMethod.POST, produces="application/json")
	public @ResponseBody
    StringResponse publishAYTerm(
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term,
			@RequestParam("state") String state){
		
		StringResponse sr = new StringResponse("success");
		
		try {
			OfferingDAO.publishOfferings(startYear, endYear, term, state);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sr;
	}
	
	@RequestMapping(value="/deleteAnOffering", method= RequestMethod.POST, produces="application/json")
	public @ResponseBody
    StringResponse deleteAnOffering(
			@RequestParam("offeringId") String offeringId){
		
		StringResponse sr = new StringResponse("success");
		try {
			OfferingDAO.deleteAnOffering(offeringId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sr;
	}
	
	@RequestMapping(value="/deleteAnOfferingList", method= RequestMethod.POST, produces="application/json")
	public @ResponseBody
    StringResponse deleteAnOfferingList(
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term){
		
		StringResponse sr = new StringResponse("success");
		try {
			OfferingDAO.deleteAnOfferingList(startYear, endYear, term);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sr;
	}
	
	@RequestMapping(value="/populateBatchDropdown", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateBatchDropdown(
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
	
	@RequestMapping(value="/populateOfferingAYDropdown", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateOfferingAYDropdown() {
		ArrayList<String> sList = new ArrayList<String>();
		
		try {
			sList = OfferingDAO.getAllUniqueAY();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sList;
	}
	
	@RequestMapping(value="/populateStatusDropdown", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateStatusDropdown(
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
	
	@RequestMapping(value="/populateTimeBlockDropdown", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateTimeBlockDropdown(
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
	
	@RequestMapping(value="/populateTimeBlockETODropdown", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateTimeBlockETODropdown() {
		ArrayList<String> sList = new ArrayList<String>();
		
		/*try {
			sList = DaysDAO.getAllUniqueTimeBlock("none", "none", "none");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/***** 1 HR & 30 MINS TIMEBLOCK *****/
		sList.add("0730-0900");
		sList.add("0915-1045");
		sList.add("1100-1230");
		sList.add("1245-1415");
		sList.add("1430-1600");
		sList.add("1615-1745");
		sList.add("1800-1930");
		sList.add("1945-2115");
		/***** 1 HR & 30 MINS TIMEBLOCK *****/
		/***** 2 HRS TIMEBLOCK *****/
		sList.add("0730-0930");
		sList.add("0800-1000");
		sList.add("1000-1200");
		sList.add("1300-1500");
		/***** 2 HRS TIMEBLOCK *****/
		/***** 3 HRS TIMEBLOCK *****/
		sList.add("0900-1200");
		sList.add("1300-1600");
		sList.add("1800-2100");
		/***** 3 HRS TIMEBLOCK *****/
		/***** UNUSUAL TIMESLOT*****/
		sList.add("0800-0900");
		sList.add("0800-0930");
		sList.add("0800-1100");
		sList.add("0900-1700");
		sList.add("0915-1230");
		sList.add("0940-1240");
		sList.add("0940-1110");
		sList.add("1100-1200");
		sList.add("1120-1220");
		sList.add("1120-1250");
		sList.add("1120-1320");
		sList.add("1300-1400");
		sList.add("1300-1430");
		sList.add("1430-1530");
		sList.add("1440-1610");
		sList.add("1500-1800");
		sList.add("1600-1900");
		sList.add("1620-1750");
		sList.add("1630-1730");
		sList.add("1800-1930");
		/***************************/
		
		return sList;
	}
	@RequestMapping(value="/emptyNewOfferingList", method= RequestMethod.POST, produces="application/json")
	public @ResponseBody
    StringResponse emptyNewOfferingList() throws SQLException{
		StringResponse sr = new StringResponse("delete");
	
		tempOfferingList.clear(); //remove the laman
		return sr;
	}
	
	@RequestMapping(value="/saveNewOfferingList", method= RequestMethod.POST, produces="application/json")
	public @ResponseBody
    StringResponse saveNewOfferingList() throws SQLException{
		StringResponse sr = new StringResponse("success");
		//System.out.println(tempOfferingList.size() + " SAVE SIZE");
		//TempOffering o = new TempOffering(startYear, endYear, term, degreeProgram, courseId, courseCode,section, batch, status, remarks,days1, days2, time1, time2);
		//tempOfferingList.add(o);
		
		for(int i = 0; i < tempOfferingList.size(); i++){
			TempOffering o = tempOfferingList.get(i);
			int j = OfferingDAO.addNewOfferingsToDB(o.getStartYear(), o.getEndYear(), o.getTerm(), o.getDegreeProgram(), o.getCourseId(), o.getCourseCode(), o.getSection(), o.getBatch(), o.getStatus(), o.getRemarks(), o.getRoom(), o.getFacultyId(), o.getDays1(), o.getDays2(), o.getTime1(), o.getTime2());
		}
		
		sr = new StringResponse(OfferingDAO.getLastRecordIndex()+"");
		//emptyNewOfferingList();
		return sr;
	}
	
	@RequestMapping(value="/addNewOfferingList", method= RequestMethod.POST, produces="application/json")
	public @ResponseBody
    StringResponse addNewOfferingList(
            @RequestParam("startYear") String startYear,
            @RequestParam("endYear") String endYear,
            @RequestParam("term") String term,
            @RequestParam("degreeProgram") String degreeProgram,
            @RequestParam("courseId") String courseId,
            @RequestParam("courseCode") String courseCode,
            @RequestParam("section") String section,
            @RequestParam("batch") String batch,
            @RequestParam("status") String status,
            @RequestParam("remarks") String remarks,
            @RequestParam("facultyId") String facultyId,
            @RequestParam("room") String room,
            @RequestParam("daysList1[]") String[] days1,
            @RequestParam("daysList2[]") String[] days2,
            @RequestParam("timeSlot1") String time1, //time1 and 2 is in starttime-endtime format
            @RequestParam("timeSlot2") String time2) throws SQLException{
		StringResponse sr = new StringResponse("success " + courseCode);
		
		TempOffering o = new TempOffering(startYear, endYear, term, degreeProgram, courseId, courseCode,section, batch, status, remarks, room, facultyId, days1, days2, time1, time2);
		tempOfferingList.add(o);
		//saveNewOfferingList();
		//int i = OfferingDAO.addNewOfferingsToDB(startYear, endYear, term, degreeProgram, courseId, courseCode, section, batch, status, remarks, days1, days2, time1, time2);
		//sr = new StringResponse(OfferingDAO.getLastRecordIndex()+"");
		//System.out.println(tempOfferingList.size() + " SIZE");
		return sr;
	}
	//if forms ung pag submit
	/*@RequestMapping(value="/addNewOfferingList", method=RequestMethod.POST, produces="application/json")
	public @ResponseBody StringResponse addNewOfferingList(@RequestParam Map<String, String> pathVars,
			HttpServletRequest request, HttpServletResponse response) throws SQLException{
		System.out.println("Look what you made me do\n");
		String startYear = pathVars.get("saveOfferingStartYearDump");
		String endYear = pathVars.get("saveOfferingEndYearDump");
		String term = pathVars.get("saveOfferingTermDump");
		String degreeProgram = pathVars.get("saveOfferingDegreeProgramDump");
		String courseId = pathVars.get("saveOfferingCourseIDDump");
		String courseCode = pathVars.get("saveOfferingCourseCodeDump");
		String section = pathVars.get("saveOfferingSectionDump");
		String batch = pathVars.get("saveOfferingBatchDump");
		String status = pathVars.get("saveOfferingStatusDump");
		String remarks = pathVars.get("saveOfferingRemarksDump");
		String days1[] = pathVars.get("saveOfferingDaysList1Dump").split("-"); //delimiter are {'-',';'};
		String days2[] = pathVars.get("saveOfferingDaysList2Dump").split("-"); //delimiter are {'-',';'};
		String time1 = pathVars.get("saveOfferingTimeSlot1Dump");
		String time2 = pathVars.get("saveOfferingTimeSlot2Dump");
		System.out.println("courseCode " + courseCode);
		System.out.println("size of days1 " + pathVars.get("saveOfferingDaysList1Dump"));
		System.out.println("size of days2 " + pathVars.get("saveOfferingDaysList2Dump"));
		StringResponse sr = new StringResponse("success " + courseCode);
		TempOffering o = new TempOffering(startYear, endYear, term, degreeProgram, courseId, courseCode,section, batch, status, remarks,days1, days2, time1, time2);
		tempOfferingList.add(o);
		//saveNewOfferingList();
		//int i = OfferingDAO.addNewOfferingsToDB(startYear, endYear, term, degreeProgram, courseId, courseCode, section, batch, status, remarks, days1, days2, time1, time2);
		//sr = new StringResponse(OfferingDAO.getLastRecordIndex()+"");
		System.out.println(tempOfferingList.size() + " SIZE");
		return sr;
	}*/
	/********************OFFERING RELATED METHODS**************************/
	/********************FLOWCHART RELATED METHODS************************/
	@RequestMapping(value="/populateFlowchartBatchDropdown", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateFlowchartBatchDropdown(
			@RequestParam("collegeId") String collegeId,
			@RequestParam("deptId") String deptId) {
		ArrayList<String> sList = new ArrayList<String>();
		
		try {
			sList = DegreeprogramDAO.getAllFlowchartBatch(collegeId, deptId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sList;
	}
	
	@RequestMapping(value="/populateFlowchartAYDropdown", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateFlowchartAYDropdown(
			@RequestParam("collegeId") String collegeId,
			@RequestParam("deptId") String deptId) {
		ArrayList<String> sList = new ArrayList<String>();
		
		try {
			sList = FlowchartDAO.getAllFlowchartAcademicYear(collegeId, deptId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sList;
	}
	
	@RequestMapping(value="/populateFlowchartDegreeProgramDropdown", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateFlowchartDegreeProgramDropdown(
			@RequestParam("collegeId") String collegeId,
			@RequestParam("deptId") String deptId) {
		ArrayList<String> sList = new ArrayList<String>();
		
		try {
			sList = DegreeprogramDAO.getAllFlowchartDegreeProgram(collegeId, deptId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sList;
	}
	/********************FLOWCHART RELATED METHODS************************/
	/*********************COURSE RELATED METHODS**************************/
	@RequestMapping(value="/getAllCoursesWithCourseKey", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Course> getAllCoursesWithCourseKey(
			@RequestParam("searchKeyword") String key) {
		ArrayList<Course> courseList = new ArrayList<Course>();
		
		try {
			if(!key.isEmpty() && !removeSpaces(key).isEmpty()){
				courseList = CourseDAO.getAllCoursesWithKey(key);
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return courseList;
	}
	@RequestMapping(value="/getAllFlowchartCourses", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Course> getAllFlowchartCoursesList(
			@RequestParam("selectedDegreeProgram") String degreeProgram,
			@RequestParam("selectedBatch") String batch,
			@RequestParam("selectedTerm") String term,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear) {
		ArrayList<Course> courseList = new ArrayList<Course>();
		
		try {
			courseList = CourseDAO.getAllFlowchartCourses(degreeProgram, batch, term, startYear, endYear);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return courseList;
	}
	
	@RequestMapping(value="/getCourseWithID", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    Course getCourseWithID(
			@RequestParam("courseId") String ID) {
		Course c = new Course();
		
		try {
			c = CourseDAO.getCourseByID(ID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c;
	}
	/*********************COURSE RELATED METHODS**************************/
	/*********************FACULTY RELATED METHODS****************************/
	@RequestMapping(value="/getFacultyListWithKey", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<Faculty> getFacultyListWithKey(
			@RequestParam("offeringId") String offeringId,
			@RequestParam("facultyType") String facultyType,
			@RequestParam("department") String department,
			@RequestParam("college") String college,
			@RequestParam("searchKeyword") String searchKeyword) {
		
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

			if(removeSpaces(searchKeyword).equalsIgnoreCase("") && tempCollegeId == -999 && tempDeptId == -999){
				String tempKey = "";
				facultyList = FacultyDAO.getAllFacultyWithRecommendations(o, newFacultyType, tempDeptId+"", tempCollegeId+"", tempKey);
			}else{
				facultyList = FacultyDAO.getAllFacultyWithSearchKey(o, newFacultyType, tempDeptId+"", tempCollegeId+"", searchKeyword);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return facultyList;
	}
	
	
	@RequestMapping(value="/populateFacultyTypeDropdown", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateFacultyTypeDropdown() {
		ArrayList<String> typeList = new ArrayList<String>();
		
		try {
			typeList = FacultyDAO.getAllUniqueFacultyType();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return typeList;
	}
	
	@RequestMapping(value = "/initiateFacultyAssignment", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json")
	public @ResponseBody
    StringResponse initiateFacultyAssignment(
			@RequestParam("offeringId") String offeringId,
			@RequestParam("facultyId") String facultyId,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term) throws SQLException {

		StringResponse sr = new StringResponse("success");
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

						// faculty assignment proper
						System.out.println("New faculty id: " + facultyId);
						FacultyDAO.assignFacultyToOffering(offeringId, facultyId); // assign the faculty id to offering id
						loadsId = LoadDAO.getLoadsId(facultyId, startYear, endYear, term);
						FacultyDAO.incrementLoad(loadsId, units);
						System.out.println("Finished adding units to faculty!");

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
					System.out.println("Finished adding units to faculty!");
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else // the faculty is already assigned to the offering
			System.out.println("The faculty is already assigned to this offering!");

		return sr;
	}

	@RequestMapping(value="/removeLoadFromFaculty",method= RequestMethod.POST, produces ="application/json")
	public @ResponseBody
    StringResponse removeLoadFromFaculty(
			@RequestParam("offeringId") String offeringId,
			@RequestParam("facultyId") String facultyId,
			@RequestParam("startYear") String startYear,
			@RequestParam("endYear") String endYear,
			@RequestParam("term") String term){
		
		StringResponse sr = new StringResponse("success");
		String loadsId ="";
		float units = 0;
		
		try {
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


	/*********************FACULTY RELATED METHODS****************************/
	/*********************DEPARTMENT AND COLLEGE RELATED METHODS**********************/
	@RequestMapping(value="/populateCollegesDropdown", method= RequestMethod.GET, produces="application/json")
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
	
	@RequestMapping(value="/populateDepartmentsDropdown", method= RequestMethod.GET, produces="application/json")
	public @ResponseBody
    ArrayList<String> populateDepartmentsDropdown() {
		ArrayList<String> typeList = new ArrayList<String>();
		
		try {
			typeList = DepartmentDAO.getAllUniqueDepartments();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return typeList;
	}
	
	public static String removeSpaces(String s){
		s = s.replaceAll("\\s",""); 
		return s;
	}
	/*********************DEPARTMENT AND COLLEGE RELATED METHODS**********************/
	/*********************UPLOAD FLOWCHARTS**********************/
	@RequestMapping(value="/uploadFlowchartFiles", method= RequestMethod.POST)
	public void uploadFlowchartFiles(@RequestParam("flowchartFiles[]") List<CommonsMultipartFile> files, HttpSession session) {
		String UPLOAD_DIRECTORY = "flowcharts";
//		ServletContext context = session.getServletContext();  
//	    String path = context.getRealPath(UPLOAD_DIRECTORY);  
	    
	    for (MultipartFile file : files) {
	    	String filename = file.getOriginalFilename();  
    
		    File dir = new File(UPLOAD_DIRECTORY);
		    
		    if(!dir.exists()) {
		    	dir.mkdirs();
		    }		  
		    
		    File serverFile = new File(UPLOAD_DIRECTORY + File.separator + filename);
		    
		    BufferedOutputStream stream;
			try {
				byte[] bytes = file.getBytes();  
				stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				
			    stream.write(bytes);  
			    stream.flush();  
				
				Flowchart temp = TSVParser.readFlowchartFile(serverFile.getAbsolutePath());
				FlowchartDAO.insertFlowchart(temp);
				
			    stream.close();  
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
	    
	    
	    
	    System.out.println("File Upload Finished");
	}
	/*********************UPLOAD FLOWCHARTS**********************/
	/*********************EXPORT*****************/
	@RequestMapping(value="/exportOffering", method= RequestMethod.POST)
	public @ResponseBody
    Envelope exportOffering(@RequestParam Map<String, String> param) {
		String[] academicYear = param.get("academicYear").split("-");
//		System.out.println("start|end: " + academicYear[0] + "|" + academicYear[1]);
		String[][] res = ExportHandler.exportTable(param.get("tableName"), academicYear[0], academicYear[1], param.get("term")).toArray(new String[0][0]);
		StringBuilder sb = new StringBuilder();
		
		for (String[] strings : res) {
			for (int i = 0; i < strings.length; i++) {
				sb.append(strings[i]);
				sb.append("\t");
			}
			sb.append("\n");
//			System.out.println("Res: " + strings[1]);
//			System.out.println("Res2: " + sb.toString());
		}
		
		return new Envelope(sb.toString());
	}
	/*********************EXPORT*****************/
	/***********ENVELOPE CLASS***********/
	//Since SpringMVC will not bother to send String, this will be an alternative
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
	/***********ENVELOPE CLASS***********/
}

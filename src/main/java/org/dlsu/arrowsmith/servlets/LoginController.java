package org.dlsu.arrowsmith.servlets;

import org.dlsu.arrowsmith.dao.UserDAO;
import org.dlsu.arrowsmith.models.User;
import org.dlsu.arrowsmith.repositories.UserRepository;
import org.dlsu.arrowsmith.utility.Scrambler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Map;

@Controller
@SessionAttributes("user")
public class LoginController {
	
	@ModelAttribute
	public void setUsernameNav(@ModelAttribute("user") User user, Model mav) {
		mav.addAttribute("user", user);
	}

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value={"/", "/index"}, method=RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("index");

		return mav;
	}
	
	@RequestMapping(value="/login", method= RequestMethod.GET)
	public ModelAndView getLoginForm() {
		ModelAndView mav = new ModelAndView("index");
		
		return mav;
	}
	
	@RequestMapping(value="/loginSubmit", method= RequestMethod.POST)
	public ModelAndView receiveLoginForm(@RequestParam Map<String, String> pathVars,
                                         @CookieValue(value="Gamer", defaultValue="Samurai Afro") String user,
                                         @CookieValue(value="Code", defaultValue="John Cena") String pass,
                                         @CookieValue(value="Class", defaultValue="Mage") String type,
                                         HttpServletRequest request, HttpServletResponse response) throws SQLException{
		
		long ID = Long.parseLong(pathVars.get("idnumber"));
		String password = pathVars.get("password");
		Cookie toBake;
		HttpSession session = request.getSession();
		
		User u = userRepository.findByUserId(ID);
		
		if(u.getUser_id() == 0){
			ModelAndView mav = new ModelAndView("index");
			mav.addObject("msg", "USER DOES NOT EXIST");
			return mav; 
		}else{
			if(u.getUser_id() == ID && Scrambler.isSamePassword(u.getPassword(), password)){
				ModelAndView mav = null;
				
				toBake = new Cookie("Gamer", Long.toString(ID));//same shit as before
				toBake.setMaxAge(9999); //in secs
				
				response.addCookie(toBake);
				
				toBake = new Cookie("Code", password);//create cookie with key/name of "Code"; parang hashmap kc kaya may key/name
				toBake.setMaxAge(9999); //in secs; after 30 secs cookie DIES!!!
				
				response.addCookie(toBake);//to seal the deal... para ma dag dag sa session? browser? ang cookie
				
				toBake = new Cookie("Class", u.getUser_type());
				toBake.setMaxAge(9999);
				
				response.addCookie(toBake);//to seal the deal... para ma dag dag sa session and cookie
				
				session.setAttribute("user", u);
				
				if(u.getUser_type().equals("Academic Programming Officer")) {
					mav = new ModelAndView("dashboardAPO");
					mav.addObject(u);
				}else if(u.getUser_type().equals("Chair") || u.getUser_type().equals("Vice Chair")) {
					mav = new ModelAndView("dashboardCVC2");
					mav.addObject(u);
				}
				
				return mav; 
			}else{
				ModelAndView mav = new ModelAndView("index");
				mav.addObject("msg", "WRONG USERNAME OR PASSWORD! <br> PLEASE TRY AGAIN");
				return mav; 
			}
		}
		
	}
	
	@RequestMapping(value="/homeTraverse", method= RequestMethod.GET)
	public ModelAndView homeTraverse(@CookieValue(value="Gamer", defaultValue="Samurai Afro") String user, @CookieValue(value="Code", defaultValue="John Cena") String pass, @CookieValue(value="Class", defaultValue="Mage") String type, HttpServletResponse response) {
		ModelAndView mav = null;
		Cookie toBake = null;
		
		if(user.equals("Samurai Afro") || pass.equals("John Cena") || type.equals("Mage")) {
			mav = new ModelAndView("index");
			
			return mav;
		} else if(type.equals("Academic Programming Officer")){
			mav = new ModelAndView("dashboardAPO");
		}
		
		toBake = new Cookie("Gamer", user);
		toBake.setMaxAge(9999); 
		
		response.addCookie(toBake);
		
		toBake = new Cookie("Code", pass);
		toBake.setMaxAge(9999); 
		
		response.addCookie(toBake);
		
		toBake = new Cookie("Class", type);
		toBake.setMaxAge(9999); 
		
		response.addCookie(toBake);
		
		return mav;
	}
}

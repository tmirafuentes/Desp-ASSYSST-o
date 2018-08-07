package org.dlsu.arrowsmith.servlets;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LogoutController {
	
	@RequestMapping(value="/logout", method= RequestMethod.GET)
	public ModelAndView getAdmissionForm(@CookieValue(value="Gamer", defaultValue="Ass Afro") String user, @CookieValue(value="Code", defaultValue="John Cena") String pass, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("index");

		Cookie toBake = new Cookie("Gamer", "hello");
		toBake.setMaxAge(0);
		
		response.addCookie(toBake);
		
		toBake = new Cookie("Code", "there");
		toBake.setMaxAge(0);
		
		response.addCookie(toBake);
		
		return mav;
	}
}

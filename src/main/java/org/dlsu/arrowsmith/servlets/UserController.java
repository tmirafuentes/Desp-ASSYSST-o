package org.dlsu.arrowsmith.servlets;

import org.dlsu.arrowsmith.classes.main.User;
import org.dlsu.arrowsmith.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    /*** Services ***/
    @Autowired
    private UserService userService;

    /***
     *
     *  URL MAPPING
     *
     */

    /* Edit Account Details */
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String editAccountPage(Model model) {
        User currentUser = userService.retrieveUser();
        model.addAttribute("userForm", currentUser);

        return "user/account";
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public String editAccountSubmit(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {

        /* If error, redirect */
        if (bindingResult.hasErrors()) {
            //model.addAttribute("error", messages.getMessage("message.accountUpdate", null, null));
            model.addAttribute("userForm", userForm);
            return "user/account";
        }

        userForm.setUserId(userService.retrieveUser().getUserId());

        /* Save changes */
        userService.updateUser(userForm);
        return "redirect:/account";
    }
}

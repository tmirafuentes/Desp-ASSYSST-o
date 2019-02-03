package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.Role;
import org.dlsu.arrowsmith.classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DataService implements ApplicationRunner {
    @Autowired
    private UserService userService;

    public void run(ApplicationArguments args) {
        /* Add Roles */
        //Role cvcRole = new Role();
        //cvcRole.setRoleName("ROLE_CVC");
        //userService.saveRole(cvcRole);

        /* Initialize Admin */
    }
}

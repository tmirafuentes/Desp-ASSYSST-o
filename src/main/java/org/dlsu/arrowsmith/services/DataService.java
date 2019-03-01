package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.Role;
import org.dlsu.arrowsmith.classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataService implements ApplicationRunner {
    @Autowired
    private UserService userService;

    public void run(ApplicationArguments args) {
        /* Add Roles
        Role cvcRole = new Role();
        cvcRole.setRoleName("ROLE_CVC");
        userService.saveRole(cvcRole);

        Role apoRole = new Role();
        apoRole.setRoleName("ROLE_APO");
        userService.saveRole(apoRole);

         Sample User Doc Raffy
        User sampleUser = userService.findUserByUsername("22742131");
        HashSet<Role> sampleRoles = new HashSet<Role>();
        sampleRoles.add(userService.findRoleByName("ROLE_CVC"));
        sampleUser.setRoles(sampleRoles);

        userService.updateUser(sampleUser); */

        /*User sampleUser = userService.findUserByUsername("22131451");
        HashSet<Role> sampleRoles = new HashSet<Role>();
        sampleRoles.add(userService.findRoleByName("ROLE_APO"));
        sampleUser.setRoles(sampleRoles);

        userService.updateUser(sampleUser);*/

        /* Initialize Days */

    }
}

package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.Role;
import org.dlsu.arrowsmith.classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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

        /* Add Roles to the rest */
        Role cvcRole = userService.findRoleByName("ROLE_CVC");
        Role apoRole = userService.findRoleByName("ROLE_APO");
        Role facRole = userService.findRoleByName("ROLE_FACULTY");

        /* For APO
        Iterator allAPOs = userService.findAllUsersByUserType("Academic Programming Officer");
        while (allAPOs.hasNext())
        {
            User currAPO = (User)allAPOs.next();
            System.out.println("APO = " + currAPO.getFirstName() + " " + currAPO.getLastName());
            ArrayList<Role> roles = new ArrayList<Role>();
            roles.add(apoRole);
            currAPO.setPassword("iLoveCCS");
            userService.createNewUser(currAPO, roles);

            System.out.println("Hello nig");
        } */

        /* For CVC
        Iterator allChairs = userService.findAllUsersByUserType("Chair");
        while (allChairs.hasNext())
        {
            User currChair = (User)allChairs.next();
            System.out.println("Chair = " + currChair.getFirstName() + " " + currChair.getLastName());
            ArrayList<Role> roles = new ArrayList<Role>();
            roles.add(cvcRole);
            currChair.setPassword("iLoveCCS");
            userService.createNewUser(currChair, roles);
        } */

        /* For FACULTY
        Iterator allFaculty = userService.findAllUsersByUserType("Faculty");
        while (allFaculty.hasNext())
        {
            User currFaculty = (User)allFaculty.next();
            System.out.println("Faculty = " + currFaculty.getFirstName() + " " + currFaculty.getLastName());
            ArrayList<Role> roles = new ArrayList<Role>();
            roles.add(facRole);
            currFaculty.setPassword("iLoveCCS");
            userService.createNewUser(currFaculty, roles);
        } */
    }
}

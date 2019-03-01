package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.Role;
import org.dlsu.arrowsmith.classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("Logged in = " + s);

        User user = userService.findUserByIDNumber(Long.valueOf(s));
        if (user == null)
            throw new UsernameNotFoundException(s);

        System.out.println("Logged in 2 = " + user.getUsername());

        HashSet<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles())
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}

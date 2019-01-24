package org.dlsu.arrowsmith.services;

import org.dlsu.arrowsmith.classes.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

public class UserDetailsServiceImp implements UserDetailsService {
    /* External Services */
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    private MessageSource messages;

    /* Functions */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /* Try retrieving a user */
        User user = userService.findUserByIDNumber(Long.parseLong(username));
        if (user == null)
            throw new UsernameNotFoundException("Wrong username or password");

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        //for (Role role : user.getRoles())
            //grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));

        return new org.springframework.security.core.userdetails.User(String.valueOf(user.getUser_id()), user.getPassword(), true,
                true, true, true, grantedAuthorities);
    }
}

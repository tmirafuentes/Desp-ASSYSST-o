package org.dlsu.arrowsmith.security;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
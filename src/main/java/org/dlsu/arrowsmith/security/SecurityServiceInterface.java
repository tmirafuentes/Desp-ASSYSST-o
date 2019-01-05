package org.dlsu.arrowsmith.security;

public interface SecurityServiceInterface {
    String findLoggedInUsername();

    void autologin(String username, String password);
}

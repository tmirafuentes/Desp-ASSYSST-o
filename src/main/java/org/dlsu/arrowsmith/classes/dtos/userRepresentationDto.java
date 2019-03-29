package org.dlsu.arrowsmith.classes.dtos;

public class userRepresentationDto {
    private String userColor;
    private char userCharacter;

    public String getUserColor() {
        return userColor;
    }

    public void setUserColor(String userColor) {
        this.userColor = userColor;
    }

    public char getUserCharacter() {
        return userCharacter;
    }

    public void setUserCharacter(char userCharacter) {
        this.userCharacter = userCharacter;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;
}

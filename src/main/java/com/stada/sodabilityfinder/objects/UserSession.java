package com.stada.sodabilityfinder.objects;

public class UserSession {
    private static UserSession instance;

    private User user;

    private UserSession() {
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static UserSession getInstance() {
        if(instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void cleanUserSession() {
        user = null;
        instance = null;
    }
}
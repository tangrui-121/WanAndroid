package com.example.library.okhttp;

/**
 * Created by Mr'Tang on 2018/6/1.
 */

public class UserEvent {
    private String name;
    private String password;

    public UserEvent() {
    }

    public UserEvent(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

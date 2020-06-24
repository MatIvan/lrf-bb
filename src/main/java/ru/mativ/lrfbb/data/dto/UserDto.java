package ru.mativ.lrfbb.data.dto;

public class UserDto {

    private String login;
    private String name;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
        return "UserDto [login=" + login + ", name=" + name + ", password=" + password + "]";
    }

}

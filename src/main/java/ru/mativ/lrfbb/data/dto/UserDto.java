package ru.mativ.lrfbb.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import ru.mativ.lrfbb.data.entity.UserEntity;

public class UserDto {

    private String login;
    private String name;
    private String password;
    private List<RoleDto> roles;

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

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserDto [login=" + login + ", name=" + name + ", password=***" + ", roles=" + roles + "]";
    }

    public static UserDto make(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setLogin(userEntity.getLogin());
        userDto.setName(userEntity.getName());
        userDto.setPassword(userEntity.getPassword());

        List<RoleDto> rolesDto = userEntity
                .getRoles()
                .stream()
                .map(role -> RoleDto.make(role))
                .collect(Collectors.toList());

        userDto.setRoles(rolesDto);
        return userDto;
    }
}

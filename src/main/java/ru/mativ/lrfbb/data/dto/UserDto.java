package ru.mativ.lrfbb.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import ru.mativ.lrfbb.data.entity.UserEntity;

public class UserDto {

    private Integer id;
    private String login;
    private String name;
    private String password;
    private List<RoleDto> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public boolean hasRole(RoleDto role) {
        return roles == null ? false : roles.contains(role);
    }

    @Override
    public String toString() {
        return "UserDto [id=" + id + ", login=" + login + ", name=" + name + ", password=" + password + ", roles.size=" + roles.size() + "]";
    }

    public static UserDto make(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
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

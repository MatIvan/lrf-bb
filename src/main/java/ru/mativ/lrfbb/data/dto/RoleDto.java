package ru.mativ.lrfbb.data.dto;

import ru.mativ.lrfbb.data.entity.RoleEntity;

public class RoleDto {

    private String name;

    public RoleDto() {
    }

    public RoleDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoleDto [name=" + name + "]";
    }

    public static RoleDto make(RoleEntity roleEntity) {
        return new RoleDto(roleEntity.getName());
    }
}

package ru.mativ.lrfbb.data.dto;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RoleDto)) {
            return false;
        }
        RoleDto other = (RoleDto) obj;
        return Objects.equals(name, other.name);
    }

}

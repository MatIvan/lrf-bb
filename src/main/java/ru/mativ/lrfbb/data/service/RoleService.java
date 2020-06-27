package ru.mativ.lrfbb.data.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ru.mativ.lrfbb.data.dto.RoleDto;
import ru.mativ.lrfbb.data.entity.RoleEntity;
import ru.mativ.lrfbb.data.repository.RoleRepository;

@Service
public class RoleService {

    @Value("${web.user.role:USER}")
    private String defaultUserRole;

    @Value("${web.manager.role:MANAGER}")
    private String defaultManagerRole;

    @Autowired
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private RoleEntity getOrCreateRole(String roleName) {
        RoleEntity role = findByName(roleName);
        if (role != null) {
            return role;
        }
        return save(role);
    }

    public RoleEntity save(RoleEntity roleEntity) {
        return roleRepository.save(roleEntity);
    }

    public RoleEntity findByName(String name) {
        return roleRepository.findByName(name);
    }

    public RoleEntity getDefaultUserRole() {
        return getOrCreateRole(defaultUserRole);
    }

    public RoleEntity getDefaultManagerRole() {
        return getOrCreateRole(defaultManagerRole);
    }

    public List<RoleDto> getAllDto() {
        List<RoleEntity> roles = roleRepository.findAll();
        return roles
                .stream()
                .map(role -> RoleDto.make(role))
                .collect(Collectors.toList());
    }
}

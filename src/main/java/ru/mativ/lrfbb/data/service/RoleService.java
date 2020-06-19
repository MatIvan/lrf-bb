package ru.mativ.lrfbb.data.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.mativ.lrfbb.data.entity.RoleEntity;
import ru.mativ.lrfbb.data.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleEntity save(RoleEntity roleEntity) {
        return roleRepository.save(roleEntity);
    }

    public RoleEntity findByName(String name) {
        return roleRepository.findByName(name);
    }

    public Collection<RoleEntity> findListByName(Collection<String> names) {
        return roleRepository.findAllByNameIn(names);
    }

    public Collection<String> getNames(Collection<RoleEntity> roles) {
        Collection<String> result = new ArrayList<>();
        if (roles == null || roles.isEmpty()) {
            return result;
        }
        roles.stream().map(role -> result.add(role.getName()));
        return result;
    }

    public Collection<RoleEntity> createRoles(Collection<String> names) {
        Collection<RoleEntity> roles = new ArrayList<>();
        names.forEach(name -> {
            RoleEntity newRole = new RoleEntity();
            newRole.setName(name);
            roles.add(newRole);
        });
        return roleRepository.saveAll(roles);
    }
}

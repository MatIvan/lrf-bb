package ru.mativ.lrfbb.data;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import ru.mativ.lrfbb.data.entity.RoleEntity;
import ru.mativ.lrfbb.data.entity.UserEntity;
import ru.mativ.lrfbb.data.service.RoleService;
import ru.mativ.lrfbb.data.service.UserService;

@Component
public class ManagerUserUtil {

    @Value("${web.manager.login:manager}")
    private String managerUserLogin;

    @Value("${web.manager.password:manager}")
    private String managerUserPassword;

    @Value("${web.manager.name:manager}")
    private String managerUserName;

    @Value("${web.manager.roles:MANAGER, USER}")
    private Collection<String> managerUserRoles;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    public UserEntity getOrCreateManagerUser() {
        UserEntity user = userService.findByLogin(managerUserName);
        if (user != null) {
            return user;
        }

        Collection<RoleEntity> managerRoles = getOrCreateManagerRole();

        UserEntity managerUser = new UserEntity();
        managerUser.setLogin(managerUserLogin);
        managerUser.setName(managerUserName);
        managerUser.setPassword(passwordEncoder.encode(managerUserPassword));
        managerRoles.forEach(role -> {
            managerUser.addRole(role);
        });

        return userService.save(managerUser);
    }

    private Collection<RoleEntity> getOrCreateManagerRole() {
        Collection<RoleEntity> result = roleService.findListByName(managerUserRoles);
        if (result.size() == managerUserRoles.size()) {
            return result;
        }

        Collection<String> roles = new ArrayList<>(managerUserRoles);
        Collection<String> existRoles = roleService.getNames(result);
        roles.removeAll(existRoles);

        return roleService.createRoles(roles);
    }
}

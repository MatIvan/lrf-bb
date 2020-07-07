package ru.mativ.lrfbb.data.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ru.mativ.lrfbb.data.dto.UserDto;
import ru.mativ.lrfbb.data.dto.UserValidator;
import ru.mativ.lrfbb.data.entity.RoleEntity;
import ru.mativ.lrfbb.data.entity.UserEntity;
import ru.mativ.lrfbb.data.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Value("${web.manager.login:manager}")
    private String managerUserLogin;

    @Value("${web.manager.password:manager}")
    private String managerUserPassword;

    @Value("${web.manager.name:manager}")
    private String managerUserName;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserEntity findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public UserEntity findById(Integer userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity userEntity = this.findByLogin(login);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Invalid login."); //TODO magic message
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                userEntity.getLogin(),
                userEntity.getPassword(),
                roleToAuthorities(userEntity.getRoles()));

        return userDetails;
    }

    private Collection<? extends GrantedAuthority> roleToAuthorities(Collection<RoleEntity> roles) {
        final String rolePrefix = "ROLE_";
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(rolePrefix + role.getName()))
                .collect(Collectors.toList());
    }

    public UserEntity newUser(UserDto userDto) {
        UserEntity newUser = new UserEntity();
        newUser.setLogin(userDto.getLogin());
        newUser.setName(userDto.getName());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        newUser.addRole(roleService.getDefaultUserRole()); //new user have only one (default) role.

        return save(newUser);
    }

    public UserEntity checkManagerUser() {
        UserEntity user = findByLogin(managerUserName);
        if (user != null) {
            return user;
        }

        UserEntity managerUser = new UserEntity();
        managerUser.setLogin(managerUserLogin);
        managerUser.setName(managerUserName);
        managerUser.setPassword(passwordEncoder.encode(managerUserPassword));
        managerUser.addRole(roleService.getDefaultUserRole());
        managerUser.addRole(roleService.getDefaultManagerRole());

        return save(managerUser);
    }

    public List<UserDto> getAllDto() {
        List<UserEntity> users = userRepository.findAll();
        return users
                .stream()
                .map(user -> UserDto.make(user))
                .collect(Collectors.toList());
    }

    public UserDto save(UserDto userDto) {
        UserEntity userEntity = findById(userDto.getId());
        if (userEntity == null) {
            throw new UsernameNotFoundException("Can't find user by id=" + userDto.getId());
        }
        userEntity.setLogin(userDto.getLogin());
        userEntity.setName(userDto.getName());

        if (UserValidator.validPassword(userDto)) {
            userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        userEntity.clearRoles();
        List<RoleEntity> roles = roleService.findListByDto(userDto.getRoles());
        roles.forEach(role -> userEntity.addRole(role));

        UserEntity saved = userRepository.save(userEntity);
        UserDto result = UserDto.make(saved);
        return result;
    }
}

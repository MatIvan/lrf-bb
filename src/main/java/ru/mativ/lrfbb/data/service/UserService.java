package ru.mativ.lrfbb.data.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.mativ.lrfbb.data.entity.RoleEntity;
import ru.mativ.lrfbb.data.entity.UserEntity;
import ru.mativ.lrfbb.data.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //    @Autowired
    //    private BCryptPasswordEncoder passwordEncoder;
    //    passwordEncoder.encode(managerUserPassword)

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserEntity findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity userEntity = this.findByLogin(login);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Invalid login.");
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                userEntity.getLogin(),
                userEntity.getPassword(),
                roleToAuthorities(userEntity.getRoles()));

        System.out.println("### loadUserByUsername");
        System.out.println("### username: " + userDetails.getUsername());
        System.out.println("### roles: " + userDetails.getAuthorities());

        return userDetails;
    }

    private Collection<? extends GrantedAuthority> roleToAuthorities(Collection<RoleEntity> roles) {
        final String rolePrefix = "ROLE_";
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(rolePrefix + role.getName()))
                .collect(Collectors.toList());
    }

}

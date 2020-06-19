package ru.mativ.lrfbb.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mativ.lrfbb.data.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByLogin(String login);

}

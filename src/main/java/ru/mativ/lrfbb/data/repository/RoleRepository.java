package ru.mativ.lrfbb.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mativ.lrfbb.data.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    RoleEntity findByName(String name);

    List<RoleEntity> findAllByNameIn(Iterable<String> names);
}

package ru.mativ.lrfbb.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.mativ.lrfbb.data.entity.ReportEntity;

public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {

    @Query("SELECT r "
            + "FROM ReportEntity r "
            + "JOIN FETCH r.user "
            + "WHERE r.user.id = :userId")
    List<ReportEntity> finadAllByUser(@Param("userId") int userId);

}

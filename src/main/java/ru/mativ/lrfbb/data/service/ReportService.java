package ru.mativ.lrfbb.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.mativ.lrfbb.data.entity.ReportEntity;
import ru.mativ.lrfbb.data.entity.UserEntity;
import ru.mativ.lrfbb.data.repository.ReportRepository;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public ReportEntity save(ReportEntity reportEntity) {
        return reportRepository.save(reportEntity);
    }

    public List<ReportEntity> getAllForUser(UserEntity user) {
        return reportRepository.finadAllByUser(user.getId());
    }

}

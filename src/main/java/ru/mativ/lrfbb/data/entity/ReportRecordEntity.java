package ru.mativ.lrfbb.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "report_records")
public class ReportRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "note")
    private String note;

    @Column(name = "comment")
    private String comment;

    @Column(name = "houres")
    private int houres;

    @ManyToOne(fetch = FetchType.LAZY)
    private ReportEntity report;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getHoures() {
        return houres;
    }

    public void setHoures(int houres) {
        this.houres = houres;
    }

    public ReportEntity getReport() {
        return report;
    }

    public void setReport(ReportEntity report) {
        this.report = report;
    }

    @Override
    public String toString() {
        return "ReportRecordEntity [id=" + id + ", note=" + note + ", comment=" + comment + ", houres=" + houres + ", report_id=" + report.getId() + "]";
    }

}

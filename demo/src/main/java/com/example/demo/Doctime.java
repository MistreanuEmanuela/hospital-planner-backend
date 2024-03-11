package com.example.demo;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "doctime")
public class Doctime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_doctor")
    private Doctor doctor;

    @Column(name = "hour_start")
    private LocalTime hourStart;

    @Column(name = "hour_end")
    private LocalTime hourEnd;
    @Column(name = "datee")
    private LocalDate date;



    public Doctime(LocalDate date) {
        this.date = date;
    }

    public Doctime(LocalTime hourStart, LocalTime hourEnd, LocalDate date, Long id, Doctor doctor) {
        this.id = id;
        this.doctor = doctor;
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setHourStart(LocalTime hourStart) {
        this.hourStart = hourStart;
    }

    public void setHourEnd(LocalTime hourEnd) {
        this.hourEnd = hourEnd;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalTime getHourStart() {
        return hourStart;
    }

    public LocalTime getHourEnd() {
        return hourEnd;
    }

    public LocalDate getDate() {
        return date;
    }

    public Doctime() {
    }

    public Doctime(Doctor doctor, LocalTime hourStart, LocalTime hourEnd, LocalDate date) {
        this.doctor = doctor;
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
        this.date = date;
    }
}

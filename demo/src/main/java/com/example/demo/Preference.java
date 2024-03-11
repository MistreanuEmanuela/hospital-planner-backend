package com.example.demo;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "preferences")
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "id_doctor")
    private Doctor doctor;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "hour_start")
    private LocalTime hourStart;

    @Column(name = "hour_end")
    private LocalTime hourEnd;

    public Preference() {
    }

    public Preference(Long id, Patient patient, Doctor doctor, LocalDate date, LocalTime hourStart, LocalTime hourEnd) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setHourStart(LocalTime hourStart) {
        this.hourStart = hourStart;
    }

    public void setHourEnd(LocalTime hourEnd) {
        this.hourEnd = hourEnd;
    }

    public Long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHourStart() {
        return hourStart;
    }

    public LocalTime getHourEnd() {
        return hourEnd;
    }

    @Override
    public String toString() {
        return "Preference{" +
                "id=" + id +
                ", patient=" + patient +
                ", doctor=" + doctor +
                ", date=" + date +
                ", hourStart=" + hourStart +
                ", hourEnd=" + hourEnd +
                '}';
    }
}
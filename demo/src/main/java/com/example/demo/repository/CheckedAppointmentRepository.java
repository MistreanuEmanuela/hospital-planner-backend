package com.example.demo.repository;

import com.example.demo.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CheckedAppointmentRepository extends JpaRepository<CheckedAppointment, Long> {
    List<CheckedAppointment> getByPatient_CnpAndDateAfter(String patientCNP, LocalDate date);
   ;
    default void deleteByPatientId(Long patientId)
    {
        List<CheckedAppointment> checkedAppointments=findAll();
        for (CheckedAppointment checkedAppointment : checkedAppointments) {
            if(checkedAppointment.getPatient().getId()==patientId)
                delete(checkedAppointment);
        }
    }
    private boolean isIntervalOverlapping(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }
    List<CheckedAppointment> findByDoctorIdAndDate(Long doctorId, LocalDate date);
    default void deleteByDr(Long doctorId, LocalTime startHour, LocalTime endHour, LocalDate date) {
        List<CheckedAppointment> checkedAppointments = findByDoctorIdAndDate(doctorId, date);
        for (CheckedAppointment checkedAppointment : checkedAppointments) {
            if (isIntervalOverlapping(checkedAppointment.getHourStart(), checkedAppointment.getHourEnd(), startHour, endHour)) {
                delete(checkedAppointment);
            }
        }
    }
    default void create(int patientId, LocalTime startHour, LocalTime endHour, Long doctorId, LocalDate date, DoctimeRepository doctimeRepository, DoctorRepository doctorRepository)
    {
        doctimeRepository.verified(doctorId, date,doctorRepository);
        int overlap = doctimeRepository.verifiedInterval(startHour, endHour, doctorId, date);
        System.out.println(overlap);
        if (overlap == 1) {
            CheckedAppointment checkedAppointment=new CheckedAppointment();
            checkedAppointment.setDate(date);
            Doctor d=new Doctor();
            d.setId(doctorId);
            checkedAppointment.setDoctor(d);
            Patient p=new Patient();
            p.setId(patientId);
            checkedAppointment.setPatient(p);
            checkedAppointment.setHourStart(startHour);
            checkedAppointment.setHourEnd(endHour);
            save(checkedAppointment);
        }
    }
    default void deleteByPatient_Id(Long patientId) {
        List<CheckedAppointment> checkedAppointments = findAll();
        for (CheckedAppointment checkedAppointment : checkedAppointments) {
            if (checkedAppointment.getPatient().getId() == patientId)
                delete(checkedAppointment);
        }
    }
    List<CheckedAppointment> findByPatient_Id(Long patientId);

}

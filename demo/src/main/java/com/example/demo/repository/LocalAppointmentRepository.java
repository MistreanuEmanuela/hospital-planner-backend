package com.example.demo.repository;


import com.example.demo.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.SQLOutput;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public interface LocalAppointmentRepository extends JpaRepository<LocalAppointment, Long> {
    List<LocalAppointment> getByPatient_CnpAndDateAfter(String patientCNP, LocalDate date);
    private boolean isIntervalOverlapping(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }
    List<LocalAppointment> findByDoctorIdAndDate(Long doctorId, LocalDate date);
    default void deleteByDr(Long doctorId, LocalTime startHour, LocalTime endHour, LocalDate date) {
       List<LocalAppointment> localAppointments = findByDoctorIdAndDate(doctorId, date);
       for (LocalAppointment localAppointment : localAppointments) {
           if (isIntervalOverlapping(localAppointment.getHourStart(), localAppointment.getHourEnd(), startHour, endHour)) {
               delete(localAppointment);
           }
       }
   }

    default void generateAppointments(int patientId, LocalTime startHour, LocalTime endHour, Long doctorId, LocalDate date, DoctimeRepository doctimeRepository) {
        List<Doctime> doctorDoctimes = doctimeRepository.findByDateAndDoctor_Id(date, doctorId);

        for (Doctime doctime : doctorDoctimes) {
            LocalTime doctimeStartHour = doctime.getHourStart();
            LocalTime doctimeEndHour = doctime.getHourEnd();
            long durationMinutes = ChronoUnit.MINUTES.between(startHour, endHour);
            Duration availableDuration = Duration.between(doctimeStartHour, doctimeEndHour);
            Duration desiredDuration = Duration.between(startHour, endHour);
          //  System.out.println(availableDuration);
          //  System.out.println(desiredDuration);
            if (availableDuration.compareTo(desiredDuration) > 0) {
                LocalAppointment appointment = new LocalAppointment();
                Doctor doc=new Doctor();
                doc.setId(doctorId);
                Patient patient=new Patient();
                patient.setId(patientId);
                appointment.setDoctor(doc);
                appointment.setPatient(patient);
                appointment.setHourStart(doctimeStartHour);
                appointment.setHourEnd(doctimeStartHour.plusMinutes(durationMinutes));
               // System.out.println(startHour);
              //  System.out.println(endHour);
                //System.out.println(doctimeStartHour.plusHours(endHour.getHour() - startHour.getHour()));
                appointment.setDate(date);
                save(appointment);
            }
        }
    }
    default void deleteByPatientId(Long patientId) {
        List<LocalAppointment> localAppointments = findAll();
        for (LocalAppointment localAppointment : localAppointments) {
            if (localAppointment.getPatient().getId() == patientId)
                delete(localAppointment);
        }
    }
    List<LocalAppointment> findByPatient_Id(Long patientId);


}

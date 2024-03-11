package com.example.demo.repository;

import com.example.demo.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> getByPatient_CnpAndDateAfter(String patientCNP, LocalDate date);

default void insertAppointment(Appointment appointment, DoctimeRepository doctime, LocalAppointmentRepository localAppointmentRepository, CheckedAppointmentRepository checkedAppointmentRepository)
   {
       save(appointment);
       localAppointmentRepository.deleteByPatientId((long) appointment.getPatient().getId());
       checkedAppointmentRepository.deleteByPatientId((long) appointment.getPatient().getId());
       localAppointmentRepository.deleteByDr(appointment.getDoctor().getId() , appointment.getHourStart(), appointment.getHourEnd(),appointment.getDate());
       checkedAppointmentRepository.deleteByDr(appointment.getDoctor().getId() ,appointment.getHourStart(), appointment.getHourEnd(),appointment.getDate());
       LocalTime start=doctime.findDoctimeStart(appointment.getDoctor().getId(),appointment.getDate(),appointment.getHourStart(),appointment.getHourEnd());
       LocalTime end=doctime.findDoctimeEnd(appointment.getDoctor().getId(),appointment.getDate(),appointment.getHourStart(),appointment.getHourEnd());
       doctime.deletedoctime(appointment.getDoctor().getId(), appointment.getDate(), appointment.getHourStart(), appointment.getHourEnd());
       doctime.insertDoctime(appointment.getDoctor().getId(),appointment.getDate(),start, appointment.getHourStart());
       doctime.insertDoctime(appointment.getDoctor().getId(),appointment.getDate(),appointment.getHourEnd(), end);
   }
    default void insertAppointmentFirst(Appointment appointment, DoctimeRepository doctime, LocalAppointmentRepository localAppointmentRepository, CheckedAppointmentRepository checkedAppointmentRepository)
    {
        save(appointment);
        localAppointmentRepository.deleteByDr(appointment.getDoctor().getId() , appointment.getHourStart(), appointment.getHourEnd(),appointment.getDate());
        checkedAppointmentRepository.deleteByDr(appointment.getDoctor().getId() ,appointment.getHourStart(), appointment.getHourEnd(),appointment.getDate());
        LocalTime start=doctime.findDoctimeStart(appointment.getDoctor().getId(),appointment.getDate(),appointment.getHourStart(),appointment.getHourEnd());
        LocalTime end=doctime.findDoctimeEnd(appointment.getDoctor().getId(),appointment.getDate(),appointment.getHourStart(),appointment.getHourEnd());
        doctime.deletedoctime(appointment.getDoctor().getId(), appointment.getDate(), appointment.getHourStart(), appointment.getHourEnd());
        if(start != appointment.getHourStart()) {
            doctime.insertDoctime(appointment.getDoctor().getId(), appointment.getDate(), start, appointment.getHourStart());
        }
        if(appointment.getHourEnd()!=end) {
            doctime.insertDoctime(appointment.getDoctor().getId(), appointment.getDate(), appointment.getHourEnd(), end);
        }
    }
    List<Appointment> findByDoctorIdAndDateAfterOrderByDateAsc(Long doctorId, LocalDate date);

    default List<Appointment> getAppointmentsByDocWithRange(Long doctorId, int start, int end) {
        List<Appointment> allAppointments = findByDoctorIdAndDateAfterOrderByDateAsc(doctorId, LocalDate.now());
        int totalAppointments = allAppointments.size();
        int startIndex = Math.min(start, totalAppointments);
        int endIndex = Math.min(end, totalAppointments);
        System.out.println(start);
        System.out.println(end);
        if (startIndex <= endIndex) {
            return allAppointments.subList(startIndex - 1, endIndex);
        }
        return List.of();
    }
}

package com.example.demo.repository;
import com.example.demo.Appointment;
import com.example.demo.Doctor;
import com.example.demo.Patient;
import com.example.demo.Preference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {

    default int verifiedFirstPref(int patientId, LocalTime startHour, LocalTime endHour, Long doctorId, LocalDate date, DoctimeRepository doctime,DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, LocalAppointmentRepository localAppointmentRepository, CheckedAppointmentRepository checkedAppointmentRepository) {
       // System.out.println(doctorId);
        doctime.verified(doctorId, date,doctorRepository);
        int overlap = doctime.verifiedInterval(startHour, endHour, doctorId, date);
        if (overlap == 1) {
            Appointment appointment = new Appointment();
            Doctor doctor=new Doctor();
            doctor.setId(doctorId);
            appointment.setDoctor(doctor);
            Patient patient=new Patient();
            patient.setId(patientId);
            appointment.setPatient(patient);
            appointment.setHourStart(startHour);
            appointment.setHourEnd(endHour);
            appointment.setDate(date);
            appointmentRepository.insertAppointmentFirst(appointment,doctime, localAppointmentRepository, checkedAppointmentRepository);
            return 1;
        }
        return 0;
    }
    default void generate(int patientId, LocalTime startHour, LocalTime endHour, Long doctorId, LocalDate date, DoctimeRepository doctime,DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, LocalAppointmentRepository localAppointmentRepository, CheckedAppointmentRepository checkedAppointmentRepository) {
        localAppointmentRepository.generateAppointments(patientId,startHour,endHour,doctorId,date,doctime);
    }
}

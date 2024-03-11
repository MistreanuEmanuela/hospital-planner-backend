package com.example.demo.repository;


import com.example.demo.Appointment;
import com.example.demo.Doctime;

import com.example.demo.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.print.Doc;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface DoctimeRepository extends JpaRepository<Doctime, Long> {

    List<Doctime> findByDateAndDoctor_Id(LocalDate date, Long doctorId);
    default LocalTime findDoctimeStart(long doctorId, LocalDate date, LocalTime startHour, LocalTime endHour) {
        List<Doctime> doctimes = findByDateAndDoctor_Id( date,doctorId);
        for (Doctime doctime : doctimes) {
            if (doctime.getHourStart().compareTo(startHour) <= 0 && doctime.getHourEnd().compareTo(endHour) >= 0) {
                return doctime.getHourStart();
            }
        }
        return null;
    }
    default LocalTime findDoctimeEnd(long doctorId, LocalDate date, LocalTime startHour, LocalTime endHour) {
        List<Doctime> doctimes =  findByDateAndDoctor_Id( date,doctorId);
        for (Doctime doctime : doctimes) {
            if (doctime.getHourStart().compareTo(startHour) <= 0 && doctime.getHourEnd().compareTo(endHour) >= 0) {
                return doctime.getHourEnd();
            }
        }
        return null;
    }
    void deleteByDoctorIdAndDate(Long doctorId, LocalDate date);

    default void deletedoctime(Long doctorId, LocalDate date, LocalTime startHour, LocalTime endHour) {
        List<Doctime> doctimesToDelete = findByDoctorIdAndDate(doctorId, date);
        doctimesToDelete.removeIf(d -> d.getHourStart().isAfter(endHour) || d.getHourEnd().isBefore(startHour));
        doctimesToDelete.forEach(this::delete);
    }

    List<Doctime> findByDoctorIdAndDate(Long doctorId, LocalDate date);


    default void insertDoctime(Long doctorId, LocalDate date, LocalTime startHour, LocalTime endHour)
    {
        Doctor d=new Doctor();
        d.setId(doctorId);
        Doctime doctime=new Doctime( d , startHour,endHour,date);
        this.save(doctime);
    }


    default void verified(Long doctorId, LocalDate date, DoctorRepository doctorRepository) {
        List<Doctime> hasDoctime =  findByDateAndDoctor_Id(date, doctorId);
        //System.out.println(hasDoctime.get(0).getId());
        if (hasDoctime.isEmpty()) {
            System.out.println("hello");
            Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
            if (doctor != null) {
                System.out.println("altceva");
                LocalTime start = doctor.getHourStartProgram();
                System.out.print(start);
                LocalTime end = doctor.getHourEndProgram();
                Doctime newDoctime = new Doctime(doctor, start, end, date);
                System.out.print(newDoctime.getDoctor().getId() +" "+  newDoctime.getHourStart() + newDoctime.getHourEnd()+ newDoctime.getDate());
                save(newDoctime);
            }
        }
    }

    default int verifiedInterval(LocalTime startHour, LocalTime endHour, Long doctorId, LocalDate date) {
        boolean hasOverlap = existsByDoctorIdAndDateAndTimeRange(doctorId, date, startHour, endHour);
        System.out.println(hasOverlap);
        if (hasOverlap) {
            return 1;
        }
        else {
            return 0;
        }
    }

    default boolean existsByDoctorIdAndDateAndTimeRange(Long doctorId, LocalDate date, LocalTime startHour, LocalTime endHour) {
        List<Doctime> doctorDoctimes = findByDoctorIdAndDate( doctorId, date);
        for (Doctime doctime : doctorDoctimes) {
            LocalTime doctimeStartHour = doctime.getHourStart();
            LocalTime doctimeEndHour = doctime.getHourEnd();
            System.out.println(doctimeStartHour);
            System.out.println(doctimeEndHour);
            System.out.println(startHour);
            System.out.println(endHour);
            if (doctimeStartHour.isBefore(startHour) && doctimeEndHour.isAfter(endHour)) {
                return true;
            }
        }

        return false;
    }

    List<Doctime> findByDoctorId(int doctorId);
}


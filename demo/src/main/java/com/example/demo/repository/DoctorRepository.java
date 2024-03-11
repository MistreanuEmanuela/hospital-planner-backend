package com.example.demo.repository;

import com.example.demo.Appointment;
import com.example.demo.Cabinet;
import com.example.demo.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor> findByCabinetId(Long cabinetId);

    Doctor findByUsernameAndPassword(String username, String password);
}

package com.example.demo.controllers;

import com.example.demo.Cabinet;
import com.example.demo.Doctor;
import com.example.demo.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorRepository.save(doctor);
    }
    @DeleteMapping("/{id}")
    public void deleteDoctorById(@PathVariable Long id) {
        doctorRepository.deleteById(id);
    }
    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    @GetMapping("/cabinet/{id}")
    public List<Doctor> getDoctorsByCabinetId(@PathVariable Long id) {
        return doctorRepository.findByCabinetId(id);
    }
    @GetMapping
    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }
    @GetMapping("/user/{username}/{password}")
    public Doctor findByUsername(@PathVariable String username,@PathVariable String password ) {
        return doctorRepository.findByUsernameAndPassword(username,password);
    }
}
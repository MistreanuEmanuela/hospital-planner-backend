package com.example.demo.controllers;

import com.example.demo.Appointment;
import com.example.demo.CheckedAppointment;

import com.example.demo.LocalAppointment;
import com.example.demo.repository.CheckedAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/chekedappointments")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})

public class CheckedAppointmentController {

    @Autowired
    private CheckedAppointmentRepository checkedAppointmentRepository;

    @PostMapping
    public CheckedAppointment createLocalAppointment(@RequestBody CheckedAppointment checkedAppointment) {
        return  checkedAppointmentRepository.save(checkedAppointment);
    }

    @GetMapping("/{id}")
    public CheckedAppointment getLocalAppointmentById(@PathVariable Long id) {
        return checkedAppointmentRepository.findById(id).orElse(null);
    }
    @GetMapping("/cnp/{cnp}")
    public List<CheckedAppointment> getByCnp(@PathVariable String cnp) {
        LocalDate currentDate = LocalDate.now();
        return checkedAppointmentRepository.getByPatient_CnpAndDateAfter(cnp,currentDate);
    }
    @DeleteMapping("/patient/{patientId}")
    public void deleteLocalAppointmentsByPatientId(@PathVariable Long patientId) {
        List<CheckedAppointment> appointments = checkedAppointmentRepository.findByPatient_Id(patientId);
        checkedAppointmentRepository.deleteAll(appointments);
    }
}
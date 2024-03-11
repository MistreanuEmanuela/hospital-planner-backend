package com.example.demo.controllers;

import com.example.demo.Appointment;
import com.example.demo.LocalAppointment;
import com.example.demo.repository.LocalAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/local-appointments")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class LocalAppointmentController {

    @Autowired
    private LocalAppointmentRepository localAppointmentRepository;

    @PostMapping
    public LocalAppointment createLocalAppointment(@RequestBody LocalAppointment localAppointment) {
        return localAppointmentRepository.save(localAppointment);
    }
    @DeleteMapping("/patient/{patientId}")
    public void deleteLocalAppointmentsByPatientId(@PathVariable Long patientId) {
        List<LocalAppointment> appointments = localAppointmentRepository.findByPatient_Id(patientId);
        localAppointmentRepository.deleteAll(appointments);
    }
    @GetMapping("/{id}")
    public LocalAppointment getLocalAppointmentById(@PathVariable Long id) {
        return localAppointmentRepository.findById(id).orElse(null);
    }

    @GetMapping("/cnp/{cnp}")
    public List<LocalAppointment> getByCnp(@PathVariable String cnp) {
        LocalDate currentDate = LocalDate.now();
        return localAppointmentRepository.getByPatient_CnpAndDateAfter(cnp,currentDate);
    }
}
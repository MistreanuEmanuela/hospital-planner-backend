package com.example.demo.controllers;

import com.example.demo.Patient;
import com.example.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        if ( patientRepository.findByCnp(patient.getCnp())!=null) {
            return null;
        }
       return patientRepository.save(patient);
    }
    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    @GetMapping("/cnp/{cnp}")
    public Patient getPatientByCNP(@PathVariable String cnp) {
        return patientRepository.findByCnp(cnp);
    }
}

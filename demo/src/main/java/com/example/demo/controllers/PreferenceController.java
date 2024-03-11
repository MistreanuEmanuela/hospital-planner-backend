package com.example.demo.controllers;

import com.example.demo.Preference;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequestMapping("/preferences")
public class PreferenceController {

    @Autowired
    private PreferenceRepository preferenceRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private LocalAppointmentRepository localAppointmentRepository;
    @Autowired
    private CheckedAppointmentRepository checkedAppointmentRepository;
    @Autowired
    private DoctimeRepository doctime;
    @Autowired
    private DoctorRepository doctorRepository;

    @PostMapping
    public ResponseEntity<String> createPreference(@RequestBody List<Preference> preference) {
        for (int index = 0; index < preference.size(); index++) {
            System.out.println(preference);
            System.out.print(preference.get(index).getDoctor().getId());
            if (index == 0) {
                System.out.println(preference.get((0)));
                if (preferenceRepository.verifiedFirstPref(preference.get(0).getPatient().getId(),
                        preference.get(0).getHourStart(), preference.get(0).getHourEnd(), preference.get(0).getDoctor().getId(),
                        preference.get(0).getDate(), doctime, doctorRepository, appointmentRepository, localAppointmentRepository, checkedAppointmentRepository) == 1) {

                }
                 else {
                    preferenceRepository.generate(preference.get(index).getPatient().getId(),
                            preference.get(index).getHourStart(), preference.get(index).getHourEnd(), preference.get(index).getDoctor().getId(),
                            preference.get(index).getDate(), doctime, doctorRepository, appointmentRepository, localAppointmentRepository, checkedAppointmentRepository);
                }
            }
             else
                {
                    checkedAppointmentRepository.create(preference.get(index).getPatient().getId(),
                            preference.get(index).getHourStart(), preference.get(index).getHourEnd(), preference.get(index).getDoctor().getId(),
                            preference.get(index).getDate(), doctime, doctorRepository);
                }
            }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    @GetMapping("/{id}")
    public Preference getPreferenceById(@PathVariable Long id) {
        return preferenceRepository.findById(id).orElse(null);
    }



}
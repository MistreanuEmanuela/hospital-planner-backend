package com.example.demo.controllers;

import com.example.demo.Appointment;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.CheckedAppointmentRepository;
import com.example.demo.repository.DoctimeRepository;
import com.example.demo.repository.LocalAppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequestMapping("/appointments")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RestController
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private LocalAppointmentRepository localAppointmentRepository;
    @Autowired
    private CheckedAppointmentRepository checkedAppointmentRepository;
    @Autowired
    private DoctimeRepository doctime;

    @PostMapping
    public ResponseEntity<String> createAppointment(@RequestBody Appointment appointment) {
       // System.out.println("aici");
        appointmentRepository.insertAppointment(appointment, doctime,localAppointmentRepository,checkedAppointmentRepository);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointmentById(@PathVariable Long id) {
        Optional<Appointment> appointment=appointmentRepository.findById(id);
        doctime.insertDoctime(appointment.get().getDoctor().getId(), appointment.get().getDate(),
                appointment.get().getHourStart(), appointment.get().getHourEnd());
        appointmentRepository.deleteById(id);
        return ResponseEntity.ok("Appointment deleted successfully");
    }
    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    @GetMapping("/cnp/{cnp}")
    public List<Appointment> getByCnp(@PathVariable String cnp) {
        LocalDate currentDate = LocalDate.now();
        return appointmentRepository.getByPatient_CnpAndDateAfter(cnp, currentDate);
    }

    @GetMapping("/doc/{id}/{start}/{end}")
    public List<Appointment> getByDoc(@PathVariable Long id,@PathVariable int start, @PathVariable int end) {
        return appointmentRepository.getAppointmentsByDocWithRange(id,start,end);
    }
}
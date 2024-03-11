package com.example.demo.controllers;

import com.example.demo.Appointment;
import com.example.demo.Doctime;
import com.example.demo.repository.DoctimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/doctime")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})

public class DoctimeController {

    @Autowired
    private DoctimeRepository doctimeRepository;

    @PostMapping
    public Doctime createDoctime(@RequestBody Doctime doctime) {
        return doctimeRepository.save(doctime);
    }

    @GetMapping("/{id}")
    public Doctime getDoctimeById(@PathVariable Long id) {
        return doctimeRepository.findById(id).orElse(null);
    }

    @GetMapping("/{id}/{date}")
    public List<Doctime> getByDate(@PathVariable Long id ,@PathVariable LocalDate date) {
        return doctimeRepository.findByDateAndDoctor_Id(date, id);
    }
}
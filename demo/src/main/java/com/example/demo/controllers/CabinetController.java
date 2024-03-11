package com.example.demo.controllers;


import com.example.demo.Cabinet;
import com.example.demo.repository.CabinetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})

@RequestMapping("/cabinets")
public class CabinetController {

    @Autowired
    private CabinetRepository cabinetRepository;

    @PostMapping
    public Cabinet createCabinet(@RequestBody Cabinet cabinet) {
        return cabinetRepository.save(cabinet);
    }
    @DeleteMapping("/{id}")
    public void deleteCabinetById(@PathVariable Long id) {
        cabinetRepository.deleteById(id);
    }
    @GetMapping("/{id}")
    public Cabinet getCabinetById(@PathVariable Long id) {
        return cabinetRepository.findById(id).orElse(null);
    }
    @GetMapping
    public List<Cabinet> getAll() {
        return cabinetRepository.findAll();
    }
}
package com.example.demo;

import jakarta.persistence.*;

import java.time.LocalTime;
@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "genre")
    private String genre;

    @Column(name = "type")
    private String type;

    @Column(name = "hour_start_program")
    private LocalTime hourStartProgram;

    @Column(name = "hour_end_program")
    private LocalTime hourEndProgram;

    @ManyToOne
    @JoinColumn(name = "id_cabinet")
    private Cabinet cabinet;

    @Column(name = "username")
    private String username;

    @Column(name = "passwrd")
    private String password;



    public Doctor() {
    }

    public Doctor(String firstName, String lastName, String address, String genre, String type,
                  LocalTime hourStartProgram, LocalTime hourEndProgram, Cabinet cabinet,
                  String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.genre = genre;
        this.type = type;
        this.hourStartProgram = hourStartProgram;
        this.hourEndProgram = hourEndProgram;
        this.cabinet = cabinet;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getGenre() {
        return genre;
    }

    public String getType() {
        return type;
    }

    public LocalTime getHourStartProgram() {
        return hourStartProgram;
    }

    public LocalTime getHourEndProgram() {
        return hourEndProgram;
    }

    public Cabinet getCabinet() {
        return cabinet;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHourStartProgram(LocalTime hourStartProgram) {
        this.hourStartProgram = hourStartProgram;
    }

    public void setHourEndProgram(LocalTime hourEndProgram) {
        this.hourEndProgram = hourEndProgram;
    }

    public void setCabinet(Cabinet cabinet) {
        this.cabinet = cabinet;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Doctor(Long id, String firstName, String lastName, String address, String genre, String type, LocalTime hourStartProgram, LocalTime hourEndProgram, Cabinet cabinet, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.genre = genre;
        this.type = type;
        this.hourStartProgram = hourStartProgram;
        this.hourEndProgram = hourEndProgram;
        this.cabinet = cabinet;
        this.username = username;
        this.password = password;
    }
}

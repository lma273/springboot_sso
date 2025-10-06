package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String name;

    @Column(name = "auth0Id")
    private String auth0Id;


    public User() {}

    public User(String email, String name, String auth0Id) {
        this.email = email;
        this.name = name;
        this.auth0Id = auth0Id;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getAuth0Id() { return auth0Id; }

    public void setId(Long id) { this.id = id; }
    public void setEmail(String email) { this.email = email; }
    public void setName(String name) { this.name = name; }
    public void setAuth0Id(String auth0Id) { this.auth0Id = auth0Id; }
}

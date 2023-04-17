package com.adrijan.hrplatform.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private LocalDate dob;

    @Column
    private String phone;

    @Column
    private String email;

    //@OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(targetEntity = Skill.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    private List<Skill> skills = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addSkills(Skill s) {
        this.skills.add(s);
    }

    public Optional<Skill> getSkillById(Long skillId) {
        return this.skills.stream()
                .filter(skill -> skill.getId().equals(skillId))
                .findFirst();
    }
}



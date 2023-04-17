package com.adrijan.hrplatform.services;

import com.adrijan.hrplatform.models.Candidate;

import java.util.List;
import java.util.Optional;

public interface CandidateService {

    public List<Candidate> findByNameIgnoreCase(String name);

    public List<Candidate> findBySkillsNameIn(List<String> skills);

    public List<Candidate> findAll();


    public Optional<Candidate> findById(Long id);

    public void save(Candidate candidate);

    public void delete(Candidate candidate);

}

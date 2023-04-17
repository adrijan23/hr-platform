package com.adrijan.hrplatform.services;

import com.adrijan.hrplatform.models.Candidate;
import com.adrijan.hrplatform.repo.CandidateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateService{

    @Autowired
    private CandidateRepo candidateRepo;

    public List<Candidate> findByNameIgnoreCase(String name) {
        return candidateRepo.findByNameIgnoreCase(name);
    }

    public List<Candidate> findBySkillsNameIn(List<String> skills){
        return candidateRepo.findBySkillsNameIn(skills);
    }

    public List<Candidate> findAll(){
        return candidateRepo.findAll();
    }

    public Optional<Candidate> findById(Long id){
        return candidateRepo.findById(id);
    }

    public void save(Candidate candidate){
        candidateRepo.save(candidate);
    }

    public void delete(Candidate candidate){
        candidateRepo.delete(candidate);
    }

}

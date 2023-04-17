package com.adrijan.hrplatform.repo;

import com.adrijan.hrplatform.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CandidateRepo extends JpaRepository<Candidate, Long> {
    List<Candidate> findByNameIgnoreCase(String name);
    List<Candidate> findBySkillsNameIn(List<String> skills);

}

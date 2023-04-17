package com.adrijan.hrplatform.controller;

import com.adrijan.hrplatform.models.Candidate;
import com.adrijan.hrplatform.models.CandidateResponse;
import com.adrijan.hrplatform.models.Skill;
import com.adrijan.hrplatform.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CandidateController {

    @Autowired
    private CandidateService candidateService;


    //returns list of all candidates that are currently added
//    @GetMapping(value = "/candidates")
//    public List<Candidate> getCandidates(){
//        return candidateRepo.findAll();
//    }

    @GetMapping(value = "/candidates")
    public ResponseEntity<List<Candidate>> getCandidates(){
        return new ResponseEntity<>( candidateService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/candidates/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        Optional<Candidate> optionalCandidate = candidateService.findById(id);
        if (optionalCandidate.isPresent()) {
            Candidate candidate = optionalCandidate.get();
            return new ResponseEntity<>(candidate, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //adds new candidate
    @PostMapping("/candidates/add")
    public ResponseEntity<CandidateResponse> addCandidate(@RequestBody Candidate candidate) {
        try {
            candidateService.save(candidate);
            CandidateResponse response = new CandidateResponse("Candidate created succesfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            CandidateResponse response = new CandidateResponse("Error adding candidate");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //updates existing candidate
    @PutMapping(value = "/candidates/update/{id}")
    public ResponseEntity<CandidateResponse> updateCandidate(@PathVariable long id, @RequestBody Candidate updatedCandidate) {
        try {
            Candidate candidate = candidateService.findById(id).orElse(null);
            if (candidate != null) {
                candidate.setName(updatedCandidate.getName());

                candidate.setPhone(updatedCandidate.getPhone());
                candidate.setEmail(updatedCandidate.getEmail());

                // Get the existing candidate's skills
                List<Skill> existingSkills = candidate.getSkills();

                // Get the updated skills from the updatedCandidate object
                List<Skill> updatedSkills = updatedCandidate.getSkills();

                // Update the existing candidate's skills with the updated skills
                if (updatedSkills != null) {
                    existingSkills.clear();
                    existingSkills.addAll(updatedSkills);
                }

                candidateService.save(candidate);
                return new ResponseEntity<>(new CandidateResponse("Candidate updated succesfully"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new CandidateResponse("Error! Couldn't update this candidate.  Candidate doesn't exist"), HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e) {
            return new ResponseEntity<>(new CandidateResponse("Error! Couldn't update this candidate."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/candidates/delete/{id}")
    public ResponseEntity<CandidateResponse> removeCandidate(@PathVariable long id) {
        Optional<Candidate> optionalCandidate = candidateService.findById(id);
        if (optionalCandidate.isPresent()) {
            // Candidate exists
            Candidate candidate = optionalCandidate.get();

            //deleting candidate
            candidateService.delete(candidate);

            CandidateResponse response = new CandidateResponse("Candidate successfully deleted.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Candidate does not exist
            CandidateResponse response = new CandidateResponse("Candidate not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    //updates skills for a candidate with specific ID
    @PostMapping(value = "/candidates/{id}/add-skills")
    public ResponseEntity<CandidateResponse> addSkillsToCandidate(@PathVariable Long id, @RequestBody List<Skill> skills) {
        Optional<Candidate> optionalCandidate = candidateService.findById(id);
        if (optionalCandidate.isPresent()) {
            // Candidate exists
            Candidate candidate = optionalCandidate.get();

            //adding new skills
            candidate.getSkills().addAll(skills);
            candidateService.save(candidate);

            CandidateResponse response = new CandidateResponse("Candidate skills successfully updated.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // Candidate does not exist
            CandidateResponse response = new CandidateResponse("Candidate not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    //removes skill from a candidate based on candidate ID and skill ID
    @DeleteMapping(value = "/candidates/{candidateId}/skills/{skillId}")
    public ResponseEntity<CandidateResponse> removeSkillFromCandidate(@PathVariable Long candidateId, @PathVariable Long skillId) {
        Optional<Candidate> optionalCandidate = candidateService.findById(candidateId);

        if (optionalCandidate.isPresent()) {
            Candidate candidate = optionalCandidate.get();
            Optional<Skill> optionalSkill = candidate.getSkillById(skillId);

            if (optionalSkill.isPresent()) {
                candidate.getSkills().remove(optionalSkill.get());
                candidateService.save(candidate);

                return new ResponseEntity<>(new CandidateResponse("Skill successfully removed from candidate"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new CandidateResponse("Skill not found for this candidate"), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(new CandidateResponse("Candidate not found"), HttpStatus.NOT_FOUND);
        }
    }

    //searches for candidates with given name and returns a list
    @GetMapping(value = "/candidates/search")
    public ResponseEntity<List<Candidate>> searchCandidatesByName(@RequestParam String name){
        return new ResponseEntity<>(candidateService.findByNameIgnoreCase(name), HttpStatus.OK);

    }


    //returns a list of candidates who have at least one of the given skills
    @GetMapping("/candidates/search-by-skill")
    public ResponseEntity<List<Candidate>> searchCandidatesBySkill(@RequestParam List<String> skills) {

        List<Candidate> candidates = candidateService.findBySkillsNameIn(skills);
        return new ResponseEntity<>(candidates, HttpStatus.OK);
    }


}

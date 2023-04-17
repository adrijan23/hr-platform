package com.adrijan.hrplatform.controller;

import com.adrijan.hrplatform.models.Skill;
import com.adrijan.hrplatform.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class SkillContoller {

    @Autowired
    private SkillService skillService;

    //returns a list of all skills added
    @GetMapping(value = "/skills")
    public List<Skill> getSkills(){
        return skillService.findAll();
    }

    //adds new skills but not mapped to any of candidates
    @PostMapping(value = "/skills/add")
    public String addSkill(@RequestBody Skill skill){
        skillService.save(skill);
        return "Skill " + skill.getName() + " added.";
    }

    //removes skill based on ID given, it will also be removed from candidate that it is mapped to it
    @DeleteMapping(value = "/skills/delete/{id}")
    public ResponseEntity<String> removeSkill(@PathVariable long id){
        Optional<Skill> optionalSkill = skillService.findById(id);
        if (optionalSkill.isPresent()) {
            // Skill exists
            Skill skill = optionalSkill.get();

            //deleting skill
            skillService.delete(skill);

            return new ResponseEntity<>("Skill with ID: " + id + " removed.", HttpStatus.OK);
        } else {
            // Skill does not exist
            return new ResponseEntity<>("Skill not found", HttpStatus.NOT_FOUND);
        }
//        Skill removeSkill = skillRepo.findById(id).get();
//        skillRepo.delete(removeSkill);
//        return "Skill with id " + removeSkill.getId() + " deleted.";
    }
}

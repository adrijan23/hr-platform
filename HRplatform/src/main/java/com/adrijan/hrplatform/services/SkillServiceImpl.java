package com.adrijan.hrplatform.services;

import com.adrijan.hrplatform.models.Skill;
import com.adrijan.hrplatform.repo.SkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService{

    @Autowired
    private SkillRepo skillRepo;

    public List<Skill> findAll(){
        return skillRepo.findAll();
    }

    public Optional<Skill> findById(Long id){
        return skillRepo.findById(id);
    }

    public void save(Skill skill){
        skillRepo.save(skill);
    }
    public void delete(Skill skill){
        skillRepo.delete(skill);
    }
}

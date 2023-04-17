package com.adrijan.hrplatform.services;

import com.adrijan.hrplatform.models.Skill;

import java.util.List;
import java.util.Optional;

public interface SkillService {


    public List<Skill> findAll();

    public Optional<Skill> findById(Long id);

    public void save(Skill skill);

    public void delete(Skill skill);
}

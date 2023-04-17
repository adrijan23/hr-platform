package com.adrijan.hrplatform.repo;

import com.adrijan.hrplatform.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepo extends JpaRepository<Skill, Long> {
}

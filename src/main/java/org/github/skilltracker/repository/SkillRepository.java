package org.github.skilltracker.repository;


import org.github.skilltracker.model.Skill;
import org.springframework.data.repository.CrudRepository;

public interface SkillRepository extends CrudRepository<Skill, Long> {
}

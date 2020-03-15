package org.github.skilltracker.repository;


import org.github.skilltracker.model.Skill;
import org.github.skilltracker.model.SkillLevelEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends CrudRepository<Skill, Long> {

    List<Skill> findByDescription(String description);

    Optional<Skill> findByNameAndLevel(String name, SkillLevelEnum level);
}

package org.github.skilltracker.controller;

import org.github.skilltracker.model.Skill;
import org.github.skilltracker.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SkillController {

    private SkillRepository repository;

    public SkillController(@Autowired SkillRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/skills", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Skill> getAllSkills() {
        return (List<Skill>) repository.findAll();
    }

    @PutMapping(value = "/skill/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Skill putSkill(@RequestBody Skill newSkill, @PathVariable Long id) {
        return repository.findById(id)
                .map(skill -> {
                    skill.setName(newSkill.getName());
                    skill.setDescription(newSkill.getDescription());
                    skill.setLevel(newSkill.getLevel());
                    return repository.save(skill);
                })
                .orElseGet(() -> repository.save(newSkill));
    }

    @PostMapping(value = "/skill", produces = MediaType.APPLICATION_JSON_VALUE)
    public Skill postSkill(@RequestBody Skill newSkill) {
        return repository.save(newSkill);
    }

    @DeleteMapping("/skill/{id}")
    public void deleteSkill(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

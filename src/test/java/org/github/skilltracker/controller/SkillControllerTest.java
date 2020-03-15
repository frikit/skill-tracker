package org.github.skilltracker.controller;

import org.github.skilltracker.model.Skill;
import org.github.skilltracker.model.SkillLevelEnum;
import org.github.skilltracker.repository.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SkillControllerTest {

    @Autowired
    SkillRepository skillRepository;
    @Autowired
    SkillController skillController;

    Skill communication = new Skill("Communication", "Test", SkillLevelEnum.EXPERT);
    Skill communication2 = new Skill("Communication", "Test", SkillLevelEnum.AWARENESS);
    Skill communication3 = new Skill("Communication", "Test", SkillLevelEnum.WORKING);

    @BeforeEach
    public void beforeEach() {
        cleanupDatabase();

        //setup
        skillRepository.save(communication);
        skillRepository.save(communication2);
    }

    private void cleanupDatabase() {
        //cleanup
        //skills
        List<Skill> skillCleanup = skillRepository.findByDescription(communication.getDescription());
        skillCleanup.forEach(skillClean -> skillRepository.deleteById(skillClean.getId()));
    }

    @Test
    public void testGetAllSkills() {
        List<Skill> employees = skillController.getAllSkills();
        Skill employeeRetrieve = employees.stream()
                .filter(element -> element.getName().equals(communication.getName()))
                .findFirst()
                .orElse(null);

        assertNotNull(employeeRetrieve);
        assertSame(employeeRetrieve.getName(), communication.getName());
        assertSame(employeeRetrieve.getDescription(), communication.getDescription());
        assertSame(employeeRetrieve.getLevel(), communication.getLevel());
    }

    @Test
    public void testPostSkill() {
        Skill employeeRetrieve = skillController.postSkill(communication);

        assertNotNull(employeeRetrieve);
        assertSame(employeeRetrieve.getName(), communication.getName());
        assertSame(employeeRetrieve.getDescription(), communication.getDescription());
        assertSame(employeeRetrieve.getLevel(), communication.getLevel());
    }

    @Test
    public void testPutSkill() {
        Skill employeeRetrieve = skillController.putSkill(communication2, communication2.getId());

        assertNotNull(employeeRetrieve);
        assertSame(employeeRetrieve.getName(), communication2.getName());
        assertSame(employeeRetrieve.getDescription(), communication2.getDescription());
        assertSame(employeeRetrieve.getLevel(), communication2.getLevel());
    }

    @Test
    public void testPutUpdateSkill() {
        List<Skill> employees = skillController.getAllSkills();
        Skill employeeRetrieve = employees.stream()
                .filter(element -> element.getName().equals(communication.getName()))
                .findFirst()
                .orElse(null);

        if (employeeRetrieve == null) throw new RuntimeException("Should be in DB");
        communication3.setId(employeeRetrieve.getId());

        employeeRetrieve = skillController.putSkill(communication3, communication3.getId());

        assertNotNull(employeeRetrieve);
        assertSame(employeeRetrieve.getName(), communication3.getName());
        assertSame(employeeRetrieve.getDescription(), communication3.getDescription());
        assertSame(employeeRetrieve.getLevel(), communication3.getLevel());
    }

    @Test
    public void testDeleteSkill() {
        List<Long> before = skillController.getAllSkills().stream()
                .filter(element -> element.getName().equals(communication.getName()))
                .map(Skill::getId)
                .collect(Collectors.toList());

        assertFalse(before.isEmpty());

        before.forEach(skillController::deleteSkill);

        List<Long> after = skillController.getAllSkills().stream()
                .filter(element -> element.getName().equals(communication.getName()))
                .map(Skill::getId)
                .collect(Collectors.toList());

        assertTrue(after.isEmpty());
    }

}

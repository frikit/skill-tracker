package org.github.skilltracker.controller;

import org.github.skilltracker.model.Employee;
import org.github.skilltracker.model.Skill;
import org.github.skilltracker.model.SkillLevelEnum;
import org.github.skilltracker.repository.EmployeeRepository;
import org.github.skilltracker.repository.SkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerTest {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    SkillRepository skillRepository;
    @Autowired
    EmployeeController employeeController;
    @Autowired
    SkillController skillController;

    Skill communication = new Skill("Communication", "Test", SkillLevelEnum.EXPERT);
    Skill communication2 = new Skill("Communication", "Test", SkillLevelEnum.AWARENESS);
    Set<Skill> skills = new HashSet<>();
    Set<Skill> skills2 = new HashSet<>();
    Employee employee;
    Employee employee2;

    @BeforeEach
    public void beforeEach() {
        cleanupDatabase();

        //setup
        skillRepository.save(communication);
        skillRepository.save(communication2);

        skills.add(communication);
        employee = new Employee("Test", "Test", "test1", skills);

        skills2.add(communication2);
        employee2 = new Employee("Test", "Test", "test2", skills2);
        employee2.setId(99L);

        employeeRepository.save(employee);
    }

    private void cleanupDatabase() {
        //cleanup
        //employees
        List<Employee> emp = employeeRepository.findByFirstName("Test");
        emp.forEach(empl -> empl.getSkill().forEach(skill -> {
            Optional<Skill> skillEmp = skillRepository.findByNameAndLevel(skill.getName(), skill.getLevel());
            skillEmp.ifPresent(value -> skillRepository.deleteById(value.getId()));
        }));
        emp.forEach(empl -> employeeRepository.deleteById(empl.getId()));

        //skills
        List<Skill> skillCleanup = skillRepository.findByDescription(communication.getDescription());
        skillCleanup.forEach(skillClean -> skillRepository.deleteById(skillClean.getId()));
    }

    @Test
    public void testGetAllEmployees() {
        List<Employee> employees = employeeController.getAllEmployees();
        Employee employeeRetrieve = employees.stream()
                .filter(element -> element.getFirstName().equals(employee.getFirstName()))
                .findFirst()
                .orElse(null);

        assertNotNull(employeeRetrieve);
        assertSame(employeeRetrieve.getFirstName(), employee.getFirstName());
        assertSame(employeeRetrieve.getLastName(), employee.getLastName());
        assertEquals(employeeRetrieve.getSkill().iterator().next(), employee.getSkill().iterator().next());
    }

    @Test
    public void testPostEmployee() {
        Employee employeeRetrieve = employeeController.postEmployee(employee);

        assertNotNull(employeeRetrieve);
        assertSame(employeeRetrieve.getFirstName(), employee.getFirstName());
        assertSame(employeeRetrieve.getLastName(), employee.getLastName());
        assertEquals(employeeRetrieve.getSkill().iterator().next(), employee.getSkill().iterator().next());
    }

    @Test
    public void testPutEmployee() {
        Employee employeeRetrieve = employeeController.putEmployee(employee2, employee2.getId());

        assertNotNull(employeeRetrieve);
        assertEquals(employeeRetrieve.getFirstName(), employee2.getFirstName());
        assertEquals(employeeRetrieve.getLastName(), employee2.getLastName());
        assertEquals(employeeRetrieve.getUsername(), employee2.getUsername());
        assertEquals(employeeRetrieve.getSkill(), employee2.getSkill());
    }

    @Test
    public void testPutUpdateSkill() {
        List<Employee> employees = employeeController.getAllEmployees();
        Employee employeeRetrieve = employees.stream()
                .filter(element -> element.getFirstName().equals(employee.getFirstName()))
                .findFirst()
                .orElse(null);

        if (employeeRetrieve == null) throw new RuntimeException("Should be in DB");
        employee2.setId(employeeRetrieve.getId());

        employeeRetrieve = employeeController.putEmployee(employee2, employee2.getId());

        assertNotNull(employeeRetrieve);
        assertEquals(employeeRetrieve.getFirstName(), employee2.getFirstName());
        assertEquals(employeeRetrieve.getLastName(), employee2.getLastName());
        assertEquals(employeeRetrieve.getUsername(), employee2.getUsername());
        assertEquals(employeeRetrieve.getSkill(), employee2.getSkill());
    }

    @Test
    public void testDeleteSkill() {
        List<Long> before = employeeController.getAllEmployees().stream()
                .filter(element -> element.getFirstName().equals(employee.getFirstName()))
                .map(Employee::getId)
                .collect(Collectors.toList());

        assertFalse(before.isEmpty());

        before.forEach(employeeController::deleteEmployee);

        List<Long> after = employeeController.getAllEmployees().stream()
                .filter(element -> element.getFirstName().equals(employee.getFirstName()))
                .map(Employee::getId)
                .collect(Collectors.toList());

        assertTrue(after.isEmpty());
    }

    @Test
    public void testTwoEmployeeTheSameSkillDeleteSecond() {
        //setup
        employee2.setSkill(skills);
        employeeRepository.save(employee2);

        List<Long> beforeEmp = employeeController.getAllEmployees().stream()
                .filter(element -> element.getFirstName().equals(employee2.getFirstName()))
                .map(Employee::getId)
                .collect(Collectors.toList());

        List<Long> beforeSkills = skillController.getAllSkills().stream()
                .filter(element -> element.getName().equals(communication.getName()))
                .map(Skill::getId)
                .collect(Collectors.toList());

        assertEquals(2, beforeEmp.size());
        assertEquals(2, beforeSkills.size());

        employeeRepository.deleteById(beforeEmp.get(1));

        List<Long> afterEmp = employeeController.getAllEmployees().stream()
                .filter(element -> element.getFirstName().equals(employee2.getFirstName()))
                .map(Employee::getId)
                .collect(Collectors.toList());

        List<Long> afterSkills = skillController.getAllSkills().stream()
                .filter(element -> element.getName().equals(communication.getName()))
                .map(Skill::getId)
                .collect(Collectors.toList());

        assertEquals(1, afterEmp.size());
        assertEquals(2, afterSkills.size());
    }

}

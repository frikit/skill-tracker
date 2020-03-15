package org.github.skilltracker.loader;

import org.github.skilltracker.model.Employee;
import org.github.skilltracker.model.Skill;
import org.github.skilltracker.model.SkillLevelEnum;
import org.github.skilltracker.repository.EmployeeRepository;
import org.github.skilltracker.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataLoader implements ApplicationRunner {

    private EmployeeRepository employeeRepository;
    private SkillRepository skillRepository;

    @Autowired
    public DataLoader(
            EmployeeRepository employeeRepository,
            SkillRepository skillRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.skillRepository = skillRepository;
    }

    public Set<Skill> getSkills() {
        //random skills with random levels
        Skill cssExpert = new Skill("CSS", "styles", SkillLevelEnum.EXPERT);
        Skill cssAwareness = new Skill("CSS", "styles", SkillLevelEnum.AWARENESS);
        Skill html = new Skill("HTML", "markup", SkillLevelEnum.PRACTITIONER);
        Skill spring = new Skill("Spring", "spring", SkillLevelEnum.WORKING);
        Skill hibernate = new Skill("hibernate", "db", SkillLevelEnum.EXPERT);
        Skill jpa = new Skill("JPA", "jpa", SkillLevelEnum.AWARENESS);
        Skill java = new Skill("Java", "java 1.8", SkillLevelEnum.PRACTITIONER);
        Skill kotlin = new Skill("Kotlin", "jetbrains kotlin", SkillLevelEnum.WORKING);
        Skill lead = new Skill("lead", "mentoring", SkillLevelEnum.EXPERT);

        return new HashSet<>(Arrays.asList(cssExpert, cssAwareness, html, spring, hibernate, jpa, java, kotlin, lead));
    }

    public Set<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Employee username = new Employee("Firstname" + i, "Lastname" + i, "username" + i);
            employees.add(username);
        }

        return new HashSet<>(employees);
    }

    public void run(ApplicationArguments args) {
        //skills
        Set<Skill> skills = getSkills();
        skills.forEach(skillRepository::save);

        //employees
        Set<Employee> employees = getEmployees();
        employees.forEach(emp -> employeeRepository.save(emp));
    }
}

package org.github.skilltracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "level"})
)
public class Skill {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private SkillLevelEnum level;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Employee_Skill",
            joinColumns = @JoinColumn(name = "Skill_id"),
            inverseJoinColumns = @JoinColumn(name = "Employee_id")
    )
    private Set<Employee> employees;

    protected Skill() {
    }

    public Skill(String name, String description, SkillLevelEnum level) {
        this.name = name;
        this.description = description;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SkillLevelEnum getLevel() {
        return level;
    }

    public void setLevel(SkillLevelEnum level) {
        this.level = level;
    }

    @JsonIgnore
    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", level=" + level +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Skill skill = (Skill) o;

        if (getId() != null ? !getId().equals(skill.getId()) : skill.getId() != null) return false;
        if (getName() != null ? !getName().equals(skill.getName()) : skill.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(skill.getDescription()) : skill.getDescription() != null)
            return false;
        return getLevel() == skill.getLevel();
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getLevel() != null ? getLevel().hashCode() : 0);
        return result;
    }
}


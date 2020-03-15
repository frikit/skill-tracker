package org.github.skilltracker.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"username"})
)
public class Employee {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String username;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Employee_Skill",
            joinColumns = @JoinColumn(name = "Employee_id"),
            inverseJoinColumns = @JoinColumn(name = "Skill_id")
    )
    private Set<Skill> skill;

    protected Employee() {
    }

    public Employee(String firstName, String lastName, String username, Set<Skill> skill) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.skill = skill;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Skill> getSkill() {
        return skill;
    }

    public void setSkill(Set<Skill> skill) {
        this.skill = skill;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", skill=" + skill +
                '}';
    }
}

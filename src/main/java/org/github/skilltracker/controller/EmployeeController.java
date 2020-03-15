package org.github.skilltracker.controller;

import org.github.skilltracker.exceptions.ResourceNotFoundException;
import org.github.skilltracker.model.Employee;
import org.github.skilltracker.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private EmployeeRepository repository;

    @Autowired
    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/employees", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAllEmployees() {
        return (List<Employee>) repository.findAll();
    }

    @GetMapping(value = "/employees/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getEmployee(@PathVariable String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Cant find user with username '" + username + "'"));
    }

    @PutMapping(value = "/employee/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee putEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        return repository.findById(id)
                .map(employee -> {
                    employee.setFirstName(newEmployee.getFirstName());
                    employee.setLastName(newEmployee.getLastName());
                    employee.setSkill(newEmployee.getSkill());
                    employee.setUsername(newEmployee.getUsername());
                    //TODO before insert employee check if skill exist
                    //TODO and insert(hibernate level java level code?) or fail(as right now?)
                    return repository.save(employee);
                })
                .orElseGet(() -> repository.save(newEmployee));
    }

    @PostMapping(value = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee postEmployee(@RequestBody Employee newEmployee) {
        return repository.save(newEmployee);
    }

    @DeleteMapping("/employee/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

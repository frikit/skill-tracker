package org.github.skilltracker.loader;

import org.github.skilltracker.model.Employee;
import org.github.skilltracker.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    private EmployeeRepository employeeRepository;

    @Autowired
    public DataLoader(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
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
        Set<Employee> employees = getEmployees();
        employees.forEach(emp -> employeeRepository.save(emp));
    }
}

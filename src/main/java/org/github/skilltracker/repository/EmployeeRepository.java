package org.github.skilltracker.repository;

import org.github.skilltracker.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Optional<Employee> findByUsername(String username);

    List<Employee> findByFirstName(String name);

}

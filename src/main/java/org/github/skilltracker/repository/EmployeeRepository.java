package org.github.skilltracker.repository;

import org.github.skilltracker.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}

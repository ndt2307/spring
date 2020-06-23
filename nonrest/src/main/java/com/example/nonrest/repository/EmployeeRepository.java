package com.example.nonrest.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.nonrest.entity.Employee;

@Qualifier("EmployeeRepository")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}

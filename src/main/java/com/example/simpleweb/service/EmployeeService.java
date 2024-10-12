package com.example.simpleweb.service;

import com.example.simpleweb.dto.EmployeeDto;
import com.example.simpleweb.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(int theId);

    Employee save(Employee theEmployee);

    List<Employee> saveEmployees(List<Employee> employees);

    void deleteById(int theId);

    Employee convertToEmployee(EmployeeDto employeeDto);

}

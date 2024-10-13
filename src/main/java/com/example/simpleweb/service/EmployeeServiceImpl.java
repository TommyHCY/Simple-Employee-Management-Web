package com.example.simpleweb.service;

import com.example.simpleweb.dao.EmployeeRepository;
import com.example.simpleweb.dto.EmployeeDto;
import com.example.simpleweb.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int theId) {
        return employeeRepository.findById(theId).orElse(null);
    }

    @Override
    public Employee save(Employee theEmployee) {
        return employeeRepository.save(theEmployee);
    }

    @Override
    public List<Employee> saveEmployees(List<Employee> employees) {
        return employeeRepository.saveAll(employees);
    }

    @Override
    public void deleteById(int theId) {
        employeeRepository.deleteById(theId);
    }

    @Override
    public Employee convertToEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setId(0);
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setRole(employeeDto.getRole());
        return employee;
    }
}

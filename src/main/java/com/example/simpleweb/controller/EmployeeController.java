package com.example.simpleweb.controller;

import com.example.simpleweb.entity.Employee;
import com.example.simpleweb.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {

        Employee employee = employeeService.findById(employeeId);

        if (employee == null) {
            throw new RuntimeException("Employee isn't found: " + employeeId);
        }
        return employee;
    }

    @PostMapping("/employees/add")
    public Employee addEmployees(@RequestBody Employee employee) {

        employee.setId(0);

        Employee dbEmployee = employeeService.save(employee);

        return dbEmployee;
    }

    @PostMapping("/employees/addList")
    public List<Employee> addListEmployees(@RequestBody List<Employee> employees) {

        List<Employee> dbEmployees = employeeService.saveEmployees(employees);

        return dbEmployees;
    }

    @PutMapping("/employees/update")
    public Employee updateEmployee (@RequestBody Employee employee) {

        Employee existingEmployee = employeeService.findById(employee.getId());

        if (existingEmployee == null ) {

            throw new RuntimeException("Doesn't find id: " + employee.getId());
        }

        Employee dbEmployee = employeeService.save(employee);

        return dbEmployee;
    }

    @DeleteMapping("/employees/delete/{employeeId}")
    public String deleteEmployee (@PathVariable int employeeId) {

        Employee tempEmployee = employeeService.findById(employeeId);

        if (tempEmployee == null) {

            throw new RuntimeException("Employee isn't found: " + employeeId);

        }

        employeeService.deleteById(employeeId);

        return "Delete employee id: " + employeeId;
    }
}

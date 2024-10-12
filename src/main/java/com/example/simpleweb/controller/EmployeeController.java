package com.example.simpleweb.controller;

import com.example.simpleweb.dto.EmployeeDto;
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
    public Employee getEmployee(@PathVariable int employeeId) throws Exception {

        try {
            Employee employee = employeeService.findById(employeeId);

            return employee;

        } catch (Exception e) {

            throw new Exception(e);
        }
    }

    @PostMapping("/employees/add")
    public Employee addEmployees(@RequestBody EmployeeDto employeeDto) {

        return employeeService.save(employeeService.setIdZero(employeeDto));
    }

    @PostMapping("/employees/addList")
    public List<Employee> addListEmployees(@RequestBody List<EmployeeDto> employeesdto) {

        List<Employee> employees = employeesdto.stream().map(employeeDto -> {
            Employee employee = employeeService.setIdZero(employeeDto);
            return employee;
        }).toList();

        return employeeService.saveEmployees(employees);
    }

    @PutMapping("/employees/update")
    public Employee updateEmployee (@RequestBody Employee employee) throws Exception {

        try {
            Employee existingEmployee = employeeService.findById(employee.getId());

            Employee dbEmployee = null;

            if (existingEmployee != null) {

                dbEmployee = employeeService.save(employee);
            }

            return dbEmployee;

        } catch (Exception e) {

            throw new Exception(e);
        }
    }

    @DeleteMapping("/employees/delete/{employeeId}")
    public String deleteEmployee (@PathVariable int employeeId) throws Exception {

        try {
            Employee tempEmployee = employeeService.findById(employeeId);

            employeeService.deleteById(employeeId);

            return "Delete employee id: " + employeeId;

        } catch (Exception e) {

            throw new Exception(e);
        }
    }
}

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
        try {
            Employee employee = employeeService.findById(employeeId);

            return employee;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/employees/add")
    public Employee addEmployees(@RequestBody Employee employee) {

//        萬一在 JSON 中傳遞 id ...將 id 設置為 0
//        這是為了強制保存新employee ...而不是更新
        employee.setId(0);

        Employee dbEmployee = employeeService.save(employee);

        return dbEmployee;
    }

    @PostMapping("/employees/addList")
    public List<Employee> addListEmployees(@RequestBody List<Employee> employees) {

        employees.forEach(employee -> employee.setId(0));

        List<Employee> dbEmployees = employeeService.saveEmployees(employees);

        return dbEmployees;
    }

    @PutMapping("/employees/update")
    public Employee updateEmployee (@RequestBody Employee employee) {
        try {
            Employee existingEmployee = employeeService.findById(employee.getId());

            Employee dbEmployee = employeeService.save(employee);

            return dbEmployee;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/employees/delete/{employeeId}")
    public String deleteEmployee (@PathVariable int employeeId) {

        try {
            Employee tempEmployee = employeeService.findById(employeeId);

            employeeService.deleteById(employeeId);

            return "Delete employee id: " + employeeId;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}

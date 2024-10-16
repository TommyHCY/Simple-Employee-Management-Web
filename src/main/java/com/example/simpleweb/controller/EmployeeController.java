package com.example.simpleweb.controller;

import com.example.simpleweb.dto.EmployeeDto;
import com.example.simpleweb.entity.Employee;
import com.example.simpleweb.service.EmployeeService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Validated
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAll());
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> getEmployee(@PathVariable @Positive int employeeId) throws Exception {

        try {
            Employee employee = employeeService.findById(employeeId);

            if (employee == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.OK).body(employee);

        } catch (Exception e) {

            throw new Exception(e);
        }
    }

    @PostMapping("/employees/add")
    public ResponseEntity<String> addEmployees(@Valid @RequestBody EmployeeDto employeeDto) {

        employeeService.save(employeeService.convertToEmployee(employeeDto));

        return ResponseEntity.status(HttpStatus.OK).body("added success.");
    }

    @PostMapping("/employees/addList")
    public ResponseEntity<String> addListEmployees(@Valid @RequestBody List<EmployeeDto> employeesdto) {

//        List<Employee> employees = employeesdto.stream().map(employeeDto -> {
//            Employee employee = employeeService.convertToEmployee(employeeDto);
//            return employee;
//        }).toList();
        employeeService.saveEmployees(employeesdto.stream()
                .map(employeeDto -> {
                    Employee employee = employeeService.convertToEmployee(employeeDto);
                    return employee;
                }).toList());

        return ResponseEntity.status(HttpStatus.OK).body("added success.");
    }

    @PutMapping("/employees/update/{employeeid}")
    public ResponseEntity<Employee> updateEmployee (@Valid @RequestBody EmployeeDto employeeDto,@Positive @PathVariable int employeeid)throws Exception {

        try {

            Employee existingEmployee = employeeService.findById(employeeid);

            if (existingEmployee == null) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Employee employee = employeeService.convertToEmployee(employeeDto);

            employee.setId(employeeid);

            Employee updateEmployee = employeeService.save(employee);

            return ResponseEntity.status(HttpStatus.OK).body(updateEmployee);

        } catch (Exception e) {

            throw new Exception(e);
        }
    }

    @DeleteMapping("/employees/delete/{employeeId}")
    public ResponseEntity<String> deleteEmployee (@PathVariable @Positive int employeeId) throws Exception {

        try {
            Employee tempEmployee = employeeService.findById(employeeId);

            if (tempEmployee == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            employeeService.deleteById(employeeId);

            return ResponseEntity.status(HttpStatus.OK).body("Delete employee id: " + employeeId);

        } catch (Exception e) {

            throw new Exception(e);
        }
    }
}

package com.example.simpleweb.controller;

import com.example.simpleweb.dao.EmployeeRepository;
import com.example.simpleweb.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/employees")
public class EmployeePageController {

    private EmployeeRepository employeeService;

    //    since only one constructor, @Autowired is optional
//    Constructor injection
    @Autowired
    public EmployeePageController(EmployeeRepository theEmployeeService) {
        employeeService = theEmployeeService;
    }

    //    add mapping for "/list"
    @GetMapping("/list")
    public String listEmployees(Model theModel) {

//        get the employees from db
        List<Employee> theEmployees = employeeService.findAll();

//        add to the spring model
        theModel.addAttribute("employees",theEmployees);

//        we need to create a Thymeleaf page list-employees
        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

//        create model attribute to bind form data
        Employee theEmployee = new Employee();

        theModel.addAttribute("employee", theEmployee);

        return "employees/employee-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {

//        get the employee from the service
        Optional<Employee> employee = employeeService.findById(theId);

//        set employee in the model to prepopulate the form
        theModel.addAttribute("employee", employee);

//        send over to our form
        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {

//        save the employee
        employeeService.save(theEmployee);

//        use a redirect to prevent duplicate submission
        return "redirect:/employees/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId")int theId) {

//        delete the employee
        employeeService.deleteById(theId);

//        redirect to the /employees/list
        return "redirect:/employees/list";
    }
}
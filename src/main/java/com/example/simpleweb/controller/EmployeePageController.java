package com.example.simpleweb.controller;

import com.example.simpleweb.entity.Employee;
import com.example.simpleweb.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeePageController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeePageController(EmployeeService theEmployeeService) {
        employeeService = theEmployeeService;
    }

    //    add mapping for "/list"
    @GetMapping("/list")
    public String listEmployees(Model theModel) {

//        get the employees from db
        List<Employee> theEmployees = employeeService.findAll();

//        add to the spring model
        theModel.addAttribute("employees",theEmployees);

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
        Employee employee = employeeService.findById(theId);

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

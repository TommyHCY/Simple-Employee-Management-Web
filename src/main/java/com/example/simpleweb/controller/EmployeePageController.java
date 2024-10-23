package com.example.simpleweb.controller;

import com.example.simpleweb.dto.EmployeeDto;
import com.example.simpleweb.entity.Employee;
import com.example.simpleweb.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        List<Employee> employeeList = employeeService.findAll();

        List<EmployeeDto> theEmployeeDtos = employeeList.stream()
                .map(employee -> employeeService.convertToEmployeeDto(employee))
                .toList();

        List<Integer> employeeIds = employeeList.stream()
                .map(Employee::getId)
                .toList();

//        add to the spring model
        theModel.addAttribute("employees",theEmployeeDtos);
        theModel.addAttribute("employeeIds",employeeIds);

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
    public String showFormForUpdate(@Positive(message = "必須大於0") @RequestParam("employeeId") int theId,
                                    Model theModel) {

//        get the employee from the service
        Employee employee = employeeService.findById(theId);

//        set employee in the model to prepopulate the form
        theModel.addAttribute("employee", employee);
        theModel.addAttribute("employeeId", theId);

//        send over to our form
        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@RequestParam(value = "id", required = false)
                                   @Positive(message = "必須大於0") Integer id,
                               @Valid @ModelAttribute("employee") EmployeeDto theEmployeeDto,
                               BindingResult bindingResult,
                               Model theModel) {

        if (bindingResult.hasErrors()) {
            theModel.addAttribute("employee", theEmployeeDto);
            theModel.addAttribute("employeeId", id);
            theModel.addAttribute("errors",bindingResult.getAllErrors());
            return "employees/employee-form";
            }

        if (id != null) {
            Employee existEmployee = employeeService.findById(id);
            if (existEmployee != null) {
                existEmployee = employeeService.convertToEmployee(theEmployeeDto);
                existEmployee.setId(id);
                employeeService.save(existEmployee);
            }else {
                return "redirect:/employees/list?error=EmployeeNotFound";
            }
        }else  {
            employeeService.save(employeeService.convertToEmployee(theEmployeeDto));
        }

//        use a redirect to prevent duplicate submission
        return "redirect:/employees/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") @Positive(message = "必須大於0") int theId) {

//        delete the employee
        employeeService.deleteById(theId);

//        redirect to the /employees/list
        return "redirect:/employees/list";
    }
}

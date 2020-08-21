package com.example.springbootrestunittesting.controller;

import com.example.springbootrestunittesting.entity.Employee;
import com.example.springbootrestunittesting.service.inter.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/employees")
public class EmployeeRestController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    public List<Employee> getEmployees(){
        List<Employee> allEmployees = employeeService.getAllEmployees();
        return allEmployees;
    }
}

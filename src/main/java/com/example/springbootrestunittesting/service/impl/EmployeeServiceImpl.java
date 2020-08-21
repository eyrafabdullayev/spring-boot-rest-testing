package com.example.springbootrestunittesting.service.impl;

import com.example.springbootrestunittesting.entity.Employee;
import com.example.springbootrestunittesting.repository.EmployeeRepository;
import com.example.springbootrestunittesting.service.inter.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.getEmployees();
    }
}

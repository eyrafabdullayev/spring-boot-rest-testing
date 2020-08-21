package com.example.springbootrestunittesting;

import com.example.springbootrestunittesting.entity.Employee;
import com.example.springbootrestunittesting.repository.EmployeeRepository;
import com.example.springbootrestunittesting.service.impl.EmployeeServiceImpl;
import com.example.springbootrestunittesting.service.inter.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application.properties")
public class EmployeeRestControllerIntegrationTesting {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeService employeeService;

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public EmployeeService employeeService() {
            return new EmployeeServiceImpl();
        }
    }

    @MockBean
    public EmployeeRepository employeeRepository;

    @Test
    public void givenEmployees_whenGetEmployees_henStatus200() throws Exception {

        Employee employee = new Employee("James");

        List<Employee> employees = Arrays.asList(employee);

        given(employeeService.getAllEmployees()).willReturn(employees);

        mvc.perform(get("/api/v1/employees")
           .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content()
           .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}

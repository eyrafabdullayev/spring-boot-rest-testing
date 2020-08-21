# spring-boot-rest-testing

### Unit Testing With @WebMvcTest

Our Controller depends on Service layer, so we are going to include only one method.

``` java
	@RestController
	@RequestMapping("/api")
	public class EmployeeRestController {
	 
	    @Autowired
	    private EmployeeService employeeService;
	 
	    @GetMapping("/employees")
	    public List<Employee> getAllEmployees() {
	        return employeeService.getAllEmployees();
	    }
	}

```

We have to mock Service layer for out unit tests.

``` java
@RunWith(SpringRunner.class)
	@WebMvcTest(EmployeeRestController.class)
	public class EmployeeRestControllerIntegrationTest {
	 
	    @Autowired
	    private MockMvc mvc;
	 
	    @MockBean
	    private EmployeeService service;
	 
	    // write test cases here
	}

```

Using @WebMvcTest annotation we will control Controllers. It will auto-configure the Spring MVC infrastructure for our unit tests.

``` java
@Test
	public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
	  throws Exception {
	    
	    Employee james = new Employee("james");
	 
	    List<Employee> allEmployees = Arrays.asList(james);
	 
	    given(service.getAllEmployees()).willReturn(allEmployees);
	 
	    mvc.perform(get("/api/employees")
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$", hasSize(1)))
	      .andExpect(jsonPath("$[0].name", is(james.getName())));
	}

```

###  Integration Testing With @SpringBootTest

The integration tests need to start up a container to execute the test cases. Hence, some additional setup is required for this:

``` java

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

```

The @SpringBootTest annotation can be used when we need to bootstrap the entire container. 

> Property file loaded with @TestPropertySource will override the existing application.properties file.

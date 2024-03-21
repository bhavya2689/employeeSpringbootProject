package org.example.employeespringbootproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.employeespringbootproject.controller.EmployeeController;
import org.example.employeespringbootproject.model.EmployeeModel;
import org.example.employeespringbootproject.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import com.fasterxml.jackson.datatype.jsr310.*;




import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .build();
    }



    @Test
    public void getAllEmployeesTest() throws Exception {

        List<EmployeeModel> employees = Arrays.asList(
                new EmployeeModel(1L, "Bhavya", "Thota", "bhavya.thota@example.com", "123-456-7890", LocalDate.now(), "Developer", "IT", "New York", LocalDate.now(), "Manager A"),
                new EmployeeModel(2L, "John", "Doe", "john.doe@example.com", "098-765-4321", LocalDate.now(), "Tester", "QA", "New York", LocalDate.now(), "Manager B")
        );

        given(employeeService.getAllEmployees()).willReturn(employees);


        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("Bhavya")))
                .andExpect(jsonPath("$[1].firstName", is("John")));
    }

    @Test
    public void getEmployeeByIdTest() throws Exception {

        Long employeeId = 1L;
        EmployeeModel employee = new EmployeeModel();
        employee.setEmployeeId(employeeId);
        employee.setFirstName("Bhavya");
        employee.setLastName("Thota");
        employee.setEmailAddress("bhavya.thota@example.com");
        employee.setDepartment("software engineering");
        employee.setBirthDate(LocalDate.now());
        employee.setLocation("somewhere");
        employee.setJobTitle("sd");
        employee.setPhoneNumber("123456789");
        employee.setStartDate(LocalDate.now());
        employee.setManagerReporting("test");

        given(employeeService.getEmployeeById(employeeId)).willReturn(employee);


        mockMvc.perform(get("/employees/{employeeId}", employeeId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())));
    }

    @Test
    public void createEmployeeTest() throws Exception {

        EmployeeModel newEmployee = new EmployeeModel(null, "Alice", "Johnson", "alice.johnson@example.com", "234-567-8901", LocalDate.now(), "Designer", "Arts", "Chicago", LocalDate.now(), "Manager C");
        EmployeeModel savedEmployee = new EmployeeModel(3L, "Alice", "Johnson", "alice.johnson@example.com", "234-567-8901", LocalDate.now(), "Designer", "Arts", "Chicago", LocalDate.now(), "Manager C");
        given(employeeService.createEmployee(any(EmployeeModel.class))).willReturn(savedEmployee);


        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEmployee)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.employeeId", is(3)))
                .andExpect(jsonPath("$.firstName", is("Alice")));
    }

    @Test
    public void updateEmployeeTest() throws Exception {

        Long employeeId = 1L;
        EmployeeModel updateInfo = new EmployeeModel(employeeId, "Bhavya", "Thota", "update.bhavya.thota@example.com", "123-456-7890", LocalDate.now(), "Lead Developer", "IT", "New York", LocalDate.now(), "Manager A");
        given(employeeService.updateEmployee(any(EmployeeModel.class))).willReturn(updateInfo);


        mockMvc.perform(put("/employees/{employeeId}", employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateInfo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.emailAddress", is("update.bhavya.thota@example.com")))
                .andExpect(jsonPath("$.jobTitle", is("Lead Developer")));
    }

    @Test
    public void deleteEmployeeTest() throws Exception {

        Long employeeId = 1L;
        doNothing().when(employeeService).deleteEmployee(employeeId);


        mockMvc.perform(delete("/employees/{employeeId}", employeeId))
                .andExpect(status().isNoContent());

        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }
}

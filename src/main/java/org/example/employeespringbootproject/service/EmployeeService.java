package org.example.employeespringbootproject.service;

import org.example.employeespringbootproject.model.EmployeeModel;
import org.example.employeespringbootproject.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<EmployeeModel> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public EmployeeModel getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Employee not found with id: " + id));
    }

    public EmployeeModel createEmployee(EmployeeModel employee) {
        return employeeRepository.save(employee);
    }

    public EmployeeModel updateEmployee(EmployeeModel employeeDetails) {
        EmployeeModel employee = employeeRepository.findById(employeeDetails.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeDetails.getEmployeeId()));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailAddress(employeeDetails.getEmailAddress());
        employee.setPhoneNumber(employeeDetails.getPhoneNumber());
        employee.setBirthDate(employeeDetails.getBirthDate());
        employee.setJobTitle(employeeDetails.getJobTitle());
        employee.setDepartment(employeeDetails.getDepartment());
        employee.setLocation(employeeDetails.getLocation());
        employee.setStartDate(employeeDetails.getStartDate());
        employee.setEmployeeId(employeeDetails.getEmployeeId());
        employee.setManagerReporting(employeeDetails.getManagerReporting());

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        try {
            employeeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException("Employee not found with id: " + id);
        }
    }


}
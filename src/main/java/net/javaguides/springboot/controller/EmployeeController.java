package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.properties.EmployeeProperties;
import net.javaguides.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeProperties employeeProperties) {
        Employee employee = new Employee();
        mapEmployeePropertiesToEmployee(employeeProperties, employee);
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody EmployeeProperties employeeProperties) {
        Employee employeeToUpdate = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        mapEmployeePropertiesToEmployee(employeeProperties, employeeToUpdate);

        employeeService.updateEmployee(employeeToUpdate);

        return ResponseEntity.ok(employeeToUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }

    private void mapEmployeePropertiesToEmployee(EmployeeProperties employeeProperties, Employee employee) {
        employee.setFirstName(employeeProperties.getFirstName());
        employee.setLastName(employeeProperties.getLastName());
        employee.setEmailId(employeeProperties.getEmailId());
        employee.setDateOfBirth(employeeProperties.getDateOfBirth());
    }
}

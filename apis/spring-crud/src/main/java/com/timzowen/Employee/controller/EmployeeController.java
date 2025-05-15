package com.timzowen.Employee.controller;

import com.timzowen.Employee.dto.EmployeeDto;
import com.timzowen.Employee.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    /**
     * 1 -> Return type - Response entity of type object DTO
     * 2 -> save the data using service class
     * 3 -> return a status code [CREATED] and saved object
     */
    @PostMapping("/create-employee")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto savedEmployee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    /**
     *
     * @param employeeId should match the path variable name
     * @return the DTO object
     * returns new ResponseEntity<>(employeeDto, HttpStatus. OK); is the same as the return
     * since findById returns an optional entity
     * is using different names for id and path variable make sure to include pathV("id")
     */
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> fetchEmployeeById(@PathVariable Long employeeId){
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employeeDto);

    }

}

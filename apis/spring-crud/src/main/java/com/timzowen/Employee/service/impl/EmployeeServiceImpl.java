package com.timzowen.Employee.service.impl;

import com.timzowen.Employee.dto.EmployeeDto;
import com.timzowen.Employee.entity.Employee;
import com.timzowen.Employee.exception.ResourceNotFoundException;
import com.timzowen.Employee.mapper.EmployeeMapper;
import com.timzowen.Employee.repository.EmployeeRepository;
import com.timzowen.Employee.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

// Step 001 --> lombok for constructor injection & making it a service class
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    // Step 002 --> Inject the repository into service
    private EmployeeRepository employeeRepository;

    /**
     * 1 -> implement method from employee entity service
     * 2 -> convert DTO to entity for persistence in the db using the mapper
     * 3 -> save the converted entity in to the db
     * 4 -> return the saved entity as a dto back for transit
     **/
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEmployeeEntity(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    /**
     * 1 -> create a resource not found exception class -> RuntimeException
     * 2 -> check for the entity availability in the db
     * 3 -> return a mapped entity into dto for transit
     * NB: -> orElse takes lambda
     */
    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Employee with id: %d not found",employeeId)));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }
}

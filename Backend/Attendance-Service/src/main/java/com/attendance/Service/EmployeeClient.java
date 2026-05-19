package com.attendance.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.attendance.DTO.EmployeeDto;

// This tells Feign to talk to the Employee Service running on port 8081
@FeignClient(url = "http://localhost:8081/employeeService", name = "Employee-Client")
public interface EmployeeClient {

    @GetMapping("/employees/{id}")
    EmployeeDto findEmployeeById(@PathVariable("id") Long employeeId);

}

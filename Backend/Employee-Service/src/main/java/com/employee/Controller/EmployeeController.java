package com.employee.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.employee.DTO.EmployeeRequestDTO;
import com.employee.DTO.EmployeeResponseDTO;
import com.employee.Service.EmployeeService;

@RestController
@RequestMapping("/employeeService")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 1. Save operation
    @PostMapping("/employees")
    public ResponseEntity<EmployeeResponseDTO> saveEmployee(
            @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        // RequestDTO lekar service ko bhej rahe hain aur ResponseDTO return kar rahe
        // hain
        EmployeeResponseDTO savedEmployee = employeeService.saveEmployee(employeeRequestDTO);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // 2. Read operation (Paginated)
    @GetMapping("/employees")
    public ResponseEntity<Page<EmployeeResponseDTO>> fetchEmployeeList(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {

        // Paginates and returns a chunk of ResponseDTOs
        Page<EmployeeResponseDTO> employees = employeeService.fetchEmployeeList(pageNumber, pageSize, sortBy);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // 3. Update operation (Partial update using PATCH)
    @PatchMapping("/employees/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO, // @Valid lagana achhi practice hai validation ke
                                                                       // liye
            @PathVariable("id") Long employeeId) {
        // Updated data RequestDTO me aayega aur result ResponseDTO me milega
        EmployeeResponseDTO updatedEmployee = employeeService.updateEmployee(employeeRequestDTO, employeeId);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    // 4. Delete operation
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") Long employeeId) {
        // Isme koi change nahi hoga kyunki yeh pehle bhi sirf ID le raha tha
        employeeService.deleteEmployeeById(employeeId);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }
}
package com.employee.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.employee.Entity.Employee;
import com.employee.DTO.EmployeeRequestDTO;
import com.employee.DTO.EmployeeResponseDTO;
import com.employee.Exception.ResourceNotFoundException;
import com.employee.Repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // 1. SAVE OPERATION
    @Override
    public EmployeeResponseDTO saveEmployee(EmployeeRequestDTO requestDto) {
        // RequestDTO se data nikal kar Entity me daala
        Employee employee = new Employee();
        employee.setName(requestDto.getName());
        employee.setEmail(requestDto.getEmail());
        employee.setPhone(requestDto.getPhone());
        employee.setGender(requestDto.getGender());
        employee.setAge(requestDto.getAge());
        employee.setCurrLocation(requestDto.getCurrLocation());

        // Entity ko save kiya
        Employee savedEmployee = employeeRepository.save(employee);

        // Saved Entity ko ResponseDTO me convert karke return kiya
        return convertToResponseDto(savedEmployee);
    }

    // 2. READ OPERATION (FETCH ALL WITH PAGINATION & SORTING)
    @Override
    public Page<EmployeeResponseDTO> fetchEmployeeList(int pageNumber, int pageSize, String sortBy) {
        // Pageable object banaya jisme page no, size aur sort ki details hain
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));

        // Repository se Page<Employee> nikala
        Page<Employee> employeePage = employeeRepository.findAll(pageable);

        // Page<Employee> ko map() method use karke Page<EmployeeResponseDTO> me convert
        // kiya
        return employeePage.map(this::convertToResponseDto);
    }

    // 3. UPDATE OPERATION
    @Override
    public EmployeeResponseDTO updateEmployee(EmployeeRequestDTO requestDto, Long empId) {
        // Database se purana data nikala
        Employee emp = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + empId));

        // Sirf wahi fields badli jo null nahi hain (Null Checks)
        if (requestDto.getName() != null) {
            emp.setName(requestDto.getName());
        }
        if (requestDto.getEmail() != null) {
            emp.setEmail(requestDto.getEmail());
        }
        if (requestDto.getPhone() != null) {
            emp.setPhone(requestDto.getPhone());
        }
        if (requestDto.getGender() != null) {
            emp.setGender(requestDto.getGender());
        }
        if (requestDto.getAge() > 0) { // int ke liye null check nahi, > 0 check karte hain
            emp.setAge(requestDto.getAge());
        }
        if (requestDto.getCurrLocation() != null) {
            emp.setCurrLocation(requestDto.getCurrLocation());
        }

        // Updated data save kiya
        Employee updatedEmployee = employeeRepository.save(emp);

        // ResponseDTO return kiya
        return convertToResponseDto(updatedEmployee);
    }

    // 4. DELETE OPERATION
    @Override
    public void deleteEmployeeById(Long empId) {
        if (!employeeRepository.existsById(empId)) {
            throw new ResourceNotFoundException("Employee not found with id: " + empId);
        }
        employeeRepository.deleteById(empId);
    }

    // 💡 Helper Method: Entity ko ResponseDTO me convert karne ke liye
    private EmployeeResponseDTO convertToResponseDto(Employee employee) {
        EmployeeResponseDTO responseDto = new EmployeeResponseDTO();
        responseDto.setId(employee.getId());
        responseDto.setName(employee.getName());
        responseDto.setEmail(employee.getEmail());
        responseDto.setPhone(employee.getPhone());
        responseDto.setGender(employee.getGender());
        responseDto.setAge(employee.getAge());
        responseDto.setCurrLocation(employee.getCurrLocation());
        return responseDto;
    }

    @Override
    public EmployeeResponseDTO fetchEmployeeById(Long empId) {
        Employee emp = employeeRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + empId));

        return convertToResponseDto(emp);

    }
}
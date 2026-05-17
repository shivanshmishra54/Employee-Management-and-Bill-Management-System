package com.employee.Service;

import org.springframework.data.domain.Page;

// Naye DTOs ko import karna zaroori hai
import com.employee.DTO.EmployeeRequestDTO;
import com.employee.DTO.EmployeeResponseDTO;

public interface EmployeeService {

    // 1. Save: RequestDTO lega aur ResponseDTO return karega
    EmployeeResponseDTO saveEmployee(EmployeeRequestDTO employeeRequestDTO);

    // 2. Fetch: Ab yeh Entities ki nahi, balki ResponseDTOs ka Page dega
    // (Pagination)
    Page<EmployeeResponseDTO> fetchEmployeeList(int pageNumber, int pageSize, String sortBy);

    // 3. Update: RequestDTO aur ID lega, badla hua data ResponseDTO me dega
    EmployeeResponseDTO updateEmployee(EmployeeRequestDTO employeeRequestDTO, Long empId);

    // 4. Delete: Isme koi change nahi aayega, kyunki yeh sirf ID leta hai aur kuch
    // return nahi karta
    void deleteEmployeeById(Long empId);
}
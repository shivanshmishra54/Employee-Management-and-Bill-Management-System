package com.employee.DTO;

import lombok.Data;

@Data
public class EmployeeResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String gender;
    private int age;
    private String currLocation;
}
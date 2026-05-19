package com.attendance.DTO;

import lombok.Data;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDto {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private String gender;

    private int age;

    private String currLocation;
}
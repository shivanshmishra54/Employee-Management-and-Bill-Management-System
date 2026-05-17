package com.employee.Entity;

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
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Employee_Name", nullable = false, length = 15)
    private String name;

    @Column(name = "Employee_Email", nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "Employee_PhoneNo")
    @Size(min = 10, max = 14)
    private String phone;

    @Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "Gender must be MALE, FEMALE, or OTHER")
    private String gender;

    @Min(value = 18, message = "Employee Must be at least 18 years old")
    private int age;

    private String currLocation;
}
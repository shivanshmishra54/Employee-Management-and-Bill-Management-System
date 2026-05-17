package com.employee.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeRequestDTO {

    @NotBlank(message = "Name khali nahi ho sakta")
    @Size(max = 15, message = "Name 15 characters se bada nahi hona chahiye")
    private String name;

    @NotBlank(message = "Email zaroori hai")
    @Email(message = "Sahi email format likhein")
    private String email;

    @Size(min = 10, max = 14, message = "Phone number 10 se 14 digits ka hona chahiye")
    private String phone;

    @Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "Gender must be MALE, FEMALE, or OTHER")
    private String gender;

    @Min(value = 18, message = "Employee Must be at least 18 years old")
    private int age;

    private String currLocation;
}
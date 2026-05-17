package com.attendance.DTO;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.attendance.Enums.AttendanceStatus;
import com.attendance.Enums.Shift;

import lombok.Data;

@Data
public class AttendanceRequestDto {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Check-in time is required")
    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    private String currLocation;

    @NotNull(message = "Status is required")
    private AttendanceStatus status;

    private Shift shift;

    private String remarks;
}

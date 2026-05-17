package com.attendance.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.attendance.Enums.AttendanceStatus;
import com.attendance.Enums.Shift;

import lombok.Data;

@Data
public class AttendanceResponseDto {
    private Long attendanceId;
    private Long employeeId;

    private LocalDate date;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    private String currLocation;
    private AttendanceStatus status;
    private Shift shift;
    private String remarks;

    // Backend calculate karke dega (CheckOutTime - CheckInTime)
    private double totalWorkingHours;
    private double overtime;
}

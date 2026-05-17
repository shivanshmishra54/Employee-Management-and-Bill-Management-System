package com.attendance.Service;

import java.time.LocalDate;
import java.util.List;

import com.attendance.DTO.AttendanceRequestDto;
import com.attendance.DTO.AttendanceResponseDto;

public interface AttendanceService {

    AttendanceResponseDto addAttendance(AttendanceRequestDto request);

    AttendanceResponseDto updateAttendance(Long attendanceId, AttendanceRequestDto request);

    List<AttendanceResponseDto> getAttendanceByEmployee(Long employeeId, LocalDate startDate, LocalDate endDate);

    List<AttendanceResponseDto> getAttendanceByDate(LocalDate date);

    void deleteAttendance(Long attendanceId);

}

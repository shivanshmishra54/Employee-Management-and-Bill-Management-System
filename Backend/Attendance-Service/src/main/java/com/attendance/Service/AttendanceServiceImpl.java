package com.attendance.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendance.DTO.AttendanceRequestDto;
import com.attendance.DTO.AttendanceResponseDto;
import com.attendance.DTO.EmployeeDto;
import com.attendance.Entity.Attendance;
import com.attendance.Repository.AttendanceRepository;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeClient employeeClient;

    private void calculateHoursAndOvertime(Attendance attendance) {
        if (attendance.getCheckInTime() != null && attendance.getCheckOutTime() != null) {
            Duration duration = Duration.between(attendance.getCheckInTime(), attendance.getCheckOutTime());
            double totalHours = duration.toMinutes() / 60.0;
            totalHours = Math.round(totalHours * 100.0) / 100.0;
            attendance.setTotalWorkingHours(totalHours);

            if (totalHours > 8.0) {
                attendance.setOvertime(Math.round((totalHours - 8.0) * 100.0) / 100.0);
            } else {
                attendance.setOvertime(0.0);
            }
        }
    }

    private AttendanceResponseDto mapToDto(Attendance attendance) {
        AttendanceResponseDto dto = new AttendanceResponseDto();
        dto.setAttendanceId(attendance.getAttendanceId());
        dto.setEmployeeId(attendance.getEmployeeId());
        dto.setDate(attendance.getDate());
        dto.setCheckInTime(attendance.getCheckInTime());
        dto.setCheckOutTime(attendance.getCheckOutTime());
        dto.setCurrLocation(attendance.getCurrLocation());
        dto.setStatus(attendance.getStatus());
        dto.setShift(attendance.getShift());
        dto.setRemarks(attendance.getRemarks());
        dto.setTotalWorkingHours(attendance.getTotalWorkingHours());
        dto.setOvertime(attendance.getOvertime());
        return dto;
    }

    @Override
    public AttendanceResponseDto addAttendance(AttendanceRequestDto request) {

        // STEP 1: Verify the employee exists using Feign Client
        EmployeeDto employee = employeeClient.findEmployeeById(request.getEmployeeId());
        System.out.println("Marking attendance for Employee: " + employee.getName());

        // STEP 2: Proceed with saving the attendance
        Attendance attendance = new Attendance();
        attendance.setEmployeeId(request.getEmployeeId());
        attendance.setDate(request.getDate());
        attendance.setCheckInTime(request.getCheckInTime());
        attendance.setCheckOutTime(request.getCheckOutTime());
        attendance.setCurrLocation(request.getCurrLocation());
        attendance.setStatus(request.getStatus());
        attendance.setShift(request.getShift());
        attendance.setRemarks(request.getRemarks());

        calculateHoursAndOvertime(attendance);

        Attendance savedAttendance = attendanceRepository.save(attendance);
        return mapToDto(savedAttendance);
    }

    @Override
    public AttendanceResponseDto updateAttendance(Long attendanceId, AttendanceRequestDto request) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(
                        () -> new com.attendance.Exception.ResourceNotFoundException("Attendance", "id", attendanceId));

        // Optional: If the request includes an Employee ID, verify it and update it
        if (request.getEmployeeId() != null) {
            EmployeeDto employee = employeeClient.findEmployeeById(request.getEmployeeId());
            attendance.setEmployeeId(request.getEmployeeId());
            System.out.println("Updating attendance for Employee: " + employee.getName());
        }

        attendance.setDate(request.getDate());
        attendance.setCheckInTime(request.getCheckInTime());
        attendance.setCheckOutTime(request.getCheckOutTime());
        attendance.setCurrLocation(request.getCurrLocation());
        attendance.setStatus(request.getStatus());
        attendance.setShift(request.getShift());
        attendance.setRemarks(request.getRemarks());

        calculateHoursAndOvertime(attendance);

        Attendance updatedAttendance = attendanceRepository.save(attendance);
        return mapToDto(updatedAttendance);
    }

    @Override
    public List<AttendanceResponseDto> getAttendanceByEmployee(Long employeeId, LocalDate startDate,
            LocalDate endDate) {
        List<Attendance> attendances = attendanceRepository.findByEmployeeIdAndDateBetween(employeeId, startDate,
                endDate);
        return attendances.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<AttendanceResponseDto> getAttendanceByDate(LocalDate date) {
        List<Attendance> attendances = attendanceRepository.findByDate(date);
        return attendances.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteAttendance(Long attendanceId) {
        attendanceRepository.deleteById(attendanceId);
    }
}

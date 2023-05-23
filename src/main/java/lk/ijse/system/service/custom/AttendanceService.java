package lk.ijse.system.service.custom;

import lk.ijse.system.dto.AttendanceDTO;
import lk.ijse.system.dto.SalaryDTO;
import lk.ijse.system.dto.employeeDTO;
import lk.ijse.system.service.SuperService;
import lk.ijse.system.service.exception.NotFoundException;

public interface AttendanceService extends SuperService {
    public SalaryDTO searchSalaryType(String type) throws NotFoundException;

    public AttendanceDTO searchAttendance(String Employee,String Month);
}

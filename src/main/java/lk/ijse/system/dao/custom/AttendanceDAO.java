package lk.ijse.system.dao.custom;

import lk.ijse.system.dao.CrudDAO;
import lk.ijse.system.dto.AttendanceDTO;
import lk.ijse.system.dto.SalaryDTO;
import lk.ijse.system.entity.Attendance;
import lk.ijse.system.entity.Salary;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AttendanceDAO extends CrudDAO<Attendance,String> {
    public Salary findSalaryType(String type);

    public Attendance findAttendance(String eid,String month) ;

    boolean existsbytype(String type);

}

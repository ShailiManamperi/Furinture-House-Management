package lk.ijse.system.dao.custom.impl;

import lk.ijse.system.dao.custom.AttendanceDAO;
import lk.ijse.system.dao.exception.ConstraintViolationException;
import lk.ijse.system.dao.util.DBUtil;
import lk.ijse.system.dto.AttendanceDTO;
import lk.ijse.system.dto.SalaryDTO;
import lk.ijse.system.entity.Attendance;
import lk.ijse.system.entity.Employee;
import lk.ijse.system.entity.Salary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AttendanceDAOImpl implements AttendanceDAO {
    private final Connection connection;

    public AttendanceDAOImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Attendance save(Attendance entity) throws ConstraintViolationException {
        return null;
    }

    @Override
    public Attendance update(Attendance entity) throws ConstraintViolationException {
        return null;
    }

    @Override
    public boolean deleteByPk(String pk) throws ConstraintViolationException {
        try {
            if(!DBUtil.executeUpdate("DELETE FROM attendance WHERE E_id=?",pk)) {
                return false;
               // throw new SQLException("Failed to delete the attendances");
            }
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
        return true;
    }

    @Override
    public List<Attendance> findAll() {
        return null;
    }

    @Override
    public Optional<Attendance> findByPk(String pk) {
        return Optional.empty();
    }

    @Override
    public boolean existByPk(String pk) {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM attendance WHERE E_id=?", pk);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public Salary findSalaryType(String type) {
        try {
            ResultSet rst = DBUtil.executeQuery("Select * from salary where type = ? ", type);
            if(rst.next()){
                return new Salary(
                        rst.getString(1),
                        rst.getDouble(2),
                        rst.getDouble(3),
                        rst.getDouble(4),
                        rst.getDouble(5),
                        rst.getDouble(6)
                );
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the salary details");
        }
    }

    @Override
    public Attendance findAttendance(String eid, String month)  {
        try{
            ResultSet rst = DBUtil.executeQuery("Select * from attendance Where E_id = ? && Month = ?", eid, month);
            if (rst.next()){
                return new Attendance(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getInt(3),
                        rst.getInt(4),
                        rst.getInt(5)
                );
            }
            return null;
        }catch (SQLException e){
            throw new RuntimeException ("Cannot find");
        }

    }

    @Override
    public boolean existsbytype(String type) {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM salary WHERE type=?", type);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

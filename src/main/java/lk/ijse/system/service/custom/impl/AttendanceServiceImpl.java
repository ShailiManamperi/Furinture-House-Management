package lk.ijse.system.service.custom.impl;

import lk.ijse.system.dao.DaoFactory;
import lk.ijse.system.dao.DaoTypes;
import lk.ijse.system.dao.custom.AttendanceDAO;
import lk.ijse.system.db.DBConnection;
import lk.ijse.system.dto.AttendanceDTO;
import lk.ijse.system.dto.SalaryDTO;
import lk.ijse.system.service.custom.AttendanceService;
import lk.ijse.system.service.exception.NotFoundException;
import lk.ijse.system.service.util.Converter;

import java.sql.Connection;

public class AttendanceServiceImpl implements AttendanceService {

    private final Connection connection;
    private final Converter converter;
    private final AttendanceDAO attendanceDAO;


    public AttendanceServiceImpl() {
        connection = DBConnection.getDbConnection().getConnection();
        converter = new Converter();
        attendanceDAO = DaoFactory.getInstance().getDAO(connection,DaoTypes.ATTEND);

    }
    @Override
    public SalaryDTO searchSalaryType(String type) throws NotFoundException {
        if (!attendanceDAO.existsbytype(type)){
            throw new NotFoundException("Salary type is Not Found!");
        }
        return converter.fromSalary(attendanceDAO.findSalaryType(type));
    }

    @Override
    public AttendanceDTO searchAttendance(String id, String Month) {
        if (!attendanceDAO.existByPk(id)){
            throw new NotFoundException("This employee is not fonud");
        }
        return converter.fromAttendance(attendanceDAO.findAttendance(id,Month));
    }
}

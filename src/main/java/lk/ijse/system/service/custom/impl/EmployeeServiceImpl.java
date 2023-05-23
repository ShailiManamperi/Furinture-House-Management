package lk.ijse.system.service.custom.impl;

import lk.ijse.system.dao.DaoFactory;
import lk.ijse.system.dao.DaoTypes;
import lk.ijse.system.dao.custom.AttendanceDAO;
import lk.ijse.system.dao.custom.employeeDAO;
import lk.ijse.system.db.DBConnection;
import lk.ijse.system.dto.employeeDTO;
import lk.ijse.system.entity.Employee;
import lk.ijse.system.service.custom.EmployeeService;
import lk.ijse.system.service.exception.DuplicateException;
import lk.ijse.system.service.exception.InUseException;
import lk.ijse.system.service.exception.NotFoundException;
import lk.ijse.system.service.util.Converter;

import java.sql.Connection;
import java.sql.SQLException;

public class EmployeeServiceImpl implements EmployeeService {

    private final Connection connection;
    private final Converter converter;
    private final employeeDAO emDAO;



    public EmployeeServiceImpl() {
        connection = DBConnection.getDbConnection().getConnection();
        emDAO = DaoFactory.getInstance().getDAO(connection, DaoTypes.EMPLOYEE);
        converter = new Converter();

    }

    @Override
    public employeeDTO saveEmployee(employeeDTO eDTO) throws DuplicateException {
        if (emDAO.existByPk(eDTO.getEid())) {
            throw new DuplicateException("This Employee id is already added!");
        } else {
            emDAO.save(converter.toEmployee(eDTO));
            return eDTO;
        }
    }

    @Override
    public employeeDTO updateEmployee(employeeDTO eDTO) throws NotFoundException {
        if (!emDAO.existByPk(eDTO.getEid())) {
            throw new NotFoundException("Member not found!");
        } else {
            emDAO.update(converter.toEmployee(eDTO));
            return eDTO;
        }
    }

    @Override
    public boolean deleteEmployee(String employeeId) throws NotFoundException, InUseException, SQLException, ClassNotFoundException {
        if (!emDAO.existByPk(employeeId)) {
            throw new NotFoundException("Member not found!");
        }
        return emDAO.deleteByPk(employeeId);
    }

    @Override
    public employeeDTO searchEmployee(String employeeId, String type) throws NotFoundException {
        if (!emDAO.existByPk(employeeId)){
            throw new NotFoundException("Employee is Not Found!");
        }
        return converter.fromEmployee(emDAO.findEmployee(employeeId,type));
    }

    @Override
    public String generateNewEmployeeId() throws SQLException {
        String newEmployeeId = emDAO.findNewEmployeeId();
        return newEmployeeId;
    }
}

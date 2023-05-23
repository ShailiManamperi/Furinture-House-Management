package lk.ijse.system.service.custom;

import lk.ijse.system.dto.employeeDTO;
import lk.ijse.system.service.SuperService;
import lk.ijse.system.service.exception.DuplicateException;
import lk.ijse.system.service.exception.InUseException;
import lk.ijse.system.service.exception.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeService extends SuperService {
    public employeeDTO saveEmployee(employeeDTO eDTO) throws DuplicateException;

    public employeeDTO updateEmployee(employeeDTO eDTO) throws NotFoundException;

    public boolean deleteEmployee(String employeeId) throws NotFoundException, InUseException, SQLException, ClassNotFoundException;

    public employeeDTO searchEmployee(String employeeId, String type) throws NotFoundException;
}

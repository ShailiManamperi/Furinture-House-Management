package lk.ijse.system.dao.custom;

import lk.ijse.system.dao.CrudDAO;
import lk.ijse.system.dto.employeeDTO;
import lk.ijse.system.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface employeeDAO extends CrudDAO<Employee,String> {
    public List<Employee> searchByText(String text);

    public Employee findEmployee(String id, String type);

}

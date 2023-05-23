package lk.ijse.system.dao.custom.impl;

import lk.ijse.system.dao.custom.employeeDAO;
import lk.ijse.system.dao.exception.ConstraintViolationException;
import lk.ijse.system.dao.util.DBUtil;
import lk.ijse.system.dto.employeeDTO;
import lk.ijse.system.entity.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class employeeDAOImpl implements employeeDAO {

    private final Connection connection;

    public employeeDAOImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Employee save(Employee entity) throws ConstraintViolationException {
        try {
            if(DBUtil.executeUpdate("INSERT INTO employee (E_id, Name, dob, address, job, contact, salary, Nic) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    entity.getEid(), entity.getName(), entity.getDob(), entity.getAddress(), entity.getJob(), entity.getContact(), entity.getSalary(), entity.getNic())){
                return entity;
            }
            throw new SQLException("Failed to save the employee");
        }catch (SQLException e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Employee update(Employee entity) throws ConstraintViolationException {
        try {
            String sql ="UPDATE employee SET Name =?, dob = ?, address = ?, job = ?, contact = ?, salary = ?, Nic = ? WHERE E_id =?";
            if(DBUtil.executeUpdate(sql,entity.getName(),entity.getDob(),entity.getAddress(),entity.getJob(),entity.getContact(),entity.getSalary(),entity.getNic(),entity.getEid())){
                return entity;
            }
            throw new SQLException("Failed to update the employee");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public boolean deleteByPk(String pk) throws ConstraintViolationException {
        try {
            if(!DBUtil.executeUpdate("DELETE FROM employee WHERE E_id=?",pk)){
                return false;
            }
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
        return true;
    }

    @Override
    public List<Employee> findAll() {
        try{
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM employee");
            return getEmployeeList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load the book");
        }
    }

    @Override
    public Optional<Employee> findByPk(String pk) {
        try{
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM emloyee WHERE E_id=?", pk);
            if(rst.next()){
                return Optional.of(new Employee(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getDouble(7),
                        rst.getString(8)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the employee details");
        }
    }

    @Override
    public boolean existByPk(String pk) {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM employee WHERE E_id=?", pk);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try {
            ResultSet rst  = DBUtil.executeQuery("SELECT COUNT(E_id) AS count FROM employee");
            rst.next();
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Employee> getEmployeeList(ResultSet rst)  {
        try {
            List<Employee> employeeList= new ArrayList<>();
            while (rst.next()){
                Employee e1 = new Employee(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getDouble(7),
                        rst.getString(8));
                employeeList.add(e1);
            }
            return employeeList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> searchByText(String text) {
        try{
            text="%"+text+"%";
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM employee WHERE E_id LIKE ? OR Name LIKE ? OR Nic LIKE ? or contact LIKE  ?", text, text, text, text);
            return getEmployeeList(rst);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee findEmployee(String id, String type) {
        try {
            ResultSet rst = DBUtil.executeQuery("Select * from employee where " + type + "= ?", id);
            if(rst.next()){
                return new Employee(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getDouble(7),
                        rst.getString(8));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the employee details");
        }
    }
}

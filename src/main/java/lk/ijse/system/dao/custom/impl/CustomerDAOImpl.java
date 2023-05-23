package lk.ijse.system.dao.custom.impl;

import lk.ijse.system.dao.custom.CustomerDAO;
import lk.ijse.system.dao.exception.ConstraintViolationException;
import lk.ijse.system.dao.util.DBUtil;
import lk.ijse.system.entity.BestCustomer;
import lk.ijse.system.entity.Customer;
import lk.ijse.system.entity.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerDAOImpl implements CustomerDAO {
    private final Connection connection;

    public CustomerDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Customer save(Customer entity) throws ConstraintViolationException {
        try {
            if(DBUtil.executeUpdate("INSERT INTO customer VALUES (?, ?, ?, ?, ?)",
                    entity.getCid(), entity.getName(), entity.getAddress(), entity.getNic(),entity.getContact())){
                return entity;
            }
            throw new SQLException("Failed to save the customer");
        }catch (SQLException e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Customer update(Customer entity) throws ConstraintViolationException {
        try {
            String sql ="UPDATE customer SET name = ?, address = ?, Nic = ?, contact = ? WHERE C_id = ?";
            if(DBUtil.executeUpdate(sql,entity.getName(),entity.getAddress(),entity.getNic(),entity.getContact(),entity.getCid())){
                return entity;
            }
            throw new SQLException("Failed to update the Customer");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public boolean deleteByPk(String pk) throws ConstraintViolationException {
        try {
            if(!DBUtil.executeUpdate("DELETE FROM customer WHERE C_id=?",pk)){
                return false;
            }
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
        return true;
    }

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Optional<Customer> findByPk(String pk) {
        try{
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM customer WHERE C _id = ?", pk);
            if(rst.next()){
                return Optional.of(new Customer(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the customer details");
        }
    }

    @Override
    public boolean existByPk(String pk) {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM customer WHERE C_id = ?", pk);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try {
            ResultSet rst  = DBUtil.executeQuery("SELECT COUNT(C_id) AS count FROM customer");
            rst.next();
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer findCustomer(String id, String type) {
        try {
            ResultSet rst = DBUtil.executeQuery("Select * from customer where " + type + "= ?", id);
            if(rst.next()){
                return new Customer(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the customer details");
        }
    }

    @Override
    public String findNewCustomerId() {
        String custid = null;
        try {
            String sql = "SELECT C_id FROM customer ORDER BY C_id DESC LIMIT 1";
            ResultSet result = DBUtil.executeQuery(sql);
            if (!result.next()) {
                custid = generateNextCustomerId(result.getString(null));
            }
            custid = generateNextCustomerId(result.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
           return custid;
    }

    @Override
    public BestCustomer findBestCustomer() {
        BestCustomer c1 = null;
        try {
            String sql = "SELECT C_id, num_orders FROM (SELECT customer.C_id, COUNT(orders.O_id) as num_orders\n" +
                    "FROM customer LEFT JOIN orders ON customer.C_id = orders.c_id\n" +
                    "GROUP BY customer.C_id) as order_counts WHERE num_orders = (SELECT MAX(num_orders)\n" +
                    "FROM (SELECT COUNT(orders.O_id) as num_orders FROM customer LEFT JOIN orders\n" +
                    "ON customer.C_id = orders.c_id GROUP BY customer.C_id) as order_counts);";
            ResultSet result = DBUtil.executeQuery(sql);
            if(result.next()){
                c1 =  new BestCustomer(
                        result.getString(1),
                        result.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c1;
    }

    private String generateNextCustomerId(String currentOrderId) {
        if (currentOrderId == null) {
            return "C000001";
        }else{
            String[] split = currentOrderId.split("C");
            int id = Integer.parseInt(split[1]);
            id++;
            String newId = String.format("C%06d", id);
            return newId;
        }
    }
}

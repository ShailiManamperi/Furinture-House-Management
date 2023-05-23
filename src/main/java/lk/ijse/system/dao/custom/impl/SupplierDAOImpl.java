package lk.ijse.system.dao.custom.impl;

import lk.ijse.system.dao.custom.SupplierDAO;
import lk.ijse.system.dao.exception.ConstraintViolationException;
import lk.ijse.system.dao.util.DBUtil;
import lk.ijse.system.entity.Employee;
import lk.ijse.system.entity.Item;
import lk.ijse.system.entity.Supplier;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SupplierDAOImpl implements SupplierDAO {
    private final Connection connection;

    public SupplierDAOImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public   Supplier save(Supplier entity) throws ConstraintViolationException {
        try {
            if(DBUtil.executeUpdate("INSERT INTO suppliers (sup_id, company, supplier_name, address, email, contact) VALUES (?,?,?,?,?,?);",
                    entity.getSid(),entity.getCompany(),entity.getS_name(),entity.getAddress(),entity.getEmail(),entity.getContact())){
                return entity;
            }
            throw new SQLException("Failed to save the items");
        }catch (SQLException e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Supplier update(Supplier entity) throws ConstraintViolationException {
        try {
            String sql ="UPDATE suppliers SET company =?, supplier_name =? ,address = ?, email = ?, contact = ? WHERE sup_id =?;";
            if(DBUtil.executeUpdate(sql,entity.getCompany(),entity.getS_name(),
                    entity.getAddress(),entity.getEmail(),entity.getContact(),entity.getSid())){
                return entity;
            }
            throw new SQLException("Failed to update the Item");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public boolean deleteByPk(String pk) throws ConstraintViolationException {
        try {
            if(!DBUtil.executeUpdate("DELETE FROM suppliers WHERE sup_id=?",pk)){
                return false;
            }
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
        return true;
    }

    @Override
    public List<Supplier> findAll() {
        try{
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM suppliers");
            return getSupplierList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load the book");
        }
    }

    private List<Supplier> getSupplierList(ResultSet rst) {
        try {
            List<Supplier> supplierList= new ArrayList<>();
            while (rst.next()){
                Supplier e1 = new Supplier(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6));
                supplierList.add(e1);
            }
            return supplierList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Supplier> findByPk(String pk) {
        try{
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM suppliers WHERE sup_id=?", pk);
            if(rst.next()){
                return Optional.of(new Supplier(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6)
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item details");
        }
    }

    @Override
    public boolean existByPk(String pk) {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM suppliers WHERE sup_id=?", pk);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try {
            ResultSet rst  = DBUtil.executeQuery("SELECT COUNT(sup_id) AS count FROM suppliers");
            rst.next();
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Supplier searchsupplier(String id) {
        try {
            ResultSet rst = DBUtil.executeQuery("Select * from suppliers where company= ?", id);
            if(rst.next()){
                return new Supplier(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the supplier details");
        }
    }

    @Override
    public List<Supplier> searchByText(String text) {
        try{
            text="%"+text+"%";
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM suppliers WHERE sup_id LIKE ? OR company LIKE ? OR supplier_name LIKE ? ",text, text, text);
            return getSupplierList(rst);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String findNewSupplierId() {
        String supplierid = null;
        try {
            String sql = "SELECT sup_id FROM suppliers ORDER BY sup_id DESC LIMIT 1";
            ResultSet result = DBUtil.executeQuery(sql);
            if (!result.next()) {
                supplierid = generateNextSuppierId(result.getString(null));
            }
            supplierid = generateNextSuppierId(result.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplierid;
    }

    private String generateNextSuppierId(String currentSupplierId) {
        if (currentSupplierId == null) {
            return "S0001";
        }else{
            String[] split = currentSupplierId.split("S");
            int id = Integer.parseInt(split[1]);
            id++;
            String newId = String.format("S%04d", id);
            return newId;
        }
    }
}

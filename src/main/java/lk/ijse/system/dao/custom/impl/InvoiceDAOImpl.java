package lk.ijse.system.dao.custom.impl;

import lk.ijse.system.dao.custom.InvoiceDAO;
import lk.ijse.system.dao.exception.ConstraintViolationException;
import lk.ijse.system.dao.util.DBUtil;
import lk.ijse.system.entity.Customer;
import lk.ijse.system.entity.Invoice;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class InvoiceDAOImpl implements InvoiceDAO {
    private final Connection connection;

    public InvoiceDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Invoice searchInvoice(String id) {
        try{
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM havepay_invoice WHERE invoice_id = ?", id);
            if(rst.next()){
                return new Invoice(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getDouble(4));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the customer details");
        }
    }

    @Override
    public long count() {
        try {
            ResultSet rst  = DBUtil.executeQuery("SELECT COUNT(invoice_id) AS count FROM havepay_invoice");
            rst.next();
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Invoice saveInvoice(Invoice entity) {
        try {
            if(DBUtil.executeUpdate("INSERT INTO invoice VALUES (?, ?, ?, ?)",
                    entity.getInvoice_id(),entity.getDate(),entity.getSup_id(),entity.getAmount())){
                return entity;
            }
            throw new SQLException("Failed to save the invoice");
        }catch (SQLException e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Invoice savehaveInvoice(Invoice entity) {
        try {
            if(DBUtil.executeUpdate("INSERT INTO havepay_invoice VALUES (?, ?, ?, ?)",
                    entity.getInvoice_id(),entity.getDate(),entity.getSup_id(),entity.getAmount())){
                return entity;
            }
            throw new SQLException("Failed to save the invoice");
        }catch (SQLException e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public boolean deleteInvoice(String id) {
        try {
            if(!DBUtil.executeUpdate("DELETE FROM havepay_invoice WHERE invoice_id=?",id)){
                return false;
            }
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
        return true;
    }
}

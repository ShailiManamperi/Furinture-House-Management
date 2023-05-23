package lk.ijse.system.service.custom.impl;

import lk.ijse.system.dao.DaoFactory;
import lk.ijse.system.dao.DaoTypes;
import lk.ijse.system.dao.custom.CustomerDAO;
import lk.ijse.system.db.DBConnection;
import lk.ijse.system.dto.BestCustomerDTO;
import lk.ijse.system.dto.CustomerDTO;
import lk.ijse.system.entity.Customer;
import lk.ijse.system.service.custom.CustomerService;
import lk.ijse.system.service.exception.DuplicateException;
import lk.ijse.system.service.exception.NotFoundException;
import lk.ijse.system.service.util.Converter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService {

    private final Converter converter;
    private final Connection connection;
    private final CustomerDAO customerDAO;

    public CustomerServiceImpl() {
        connection = DBConnection.getDbConnection().getConnection();
        converter = new Converter();
        customerDAO = DaoFactory.getInstance().getDAO(connection,DaoTypes.CUSTOMER);

    }
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        if (customerDAO.existByPk(customerDTO.getCid())) {
            throw new DuplicateException("This Customer id is already added!");
        } else {
            customerDAO.save(converter.toCustomer(customerDTO));
            return customerDTO;
        }
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        if (!customerDAO.existByPk(customerDTO.getCid())) {
            throw new NotFoundException("Customer not found!");
        } else {
            customerDAO.update(converter.toCustomer(customerDTO));
            return customerDTO;
        }
    }

    @Override
    public CustomerDTO searchCustomer(String id, String type) throws NotFoundException {
        return converter.fromCustomer(customerDAO.findCustomer(id,type));
    }

    @Override
    public boolean deleteCustomer(String id) throws NotFoundException {
        if (!customerDAO.existByPk(id)){
            throw new NotFoundException("This customer id is not found");
        }
        return customerDAO.deleteByPk(id);
    }

    @Override
    public String generateNewCustomerId() throws SQLException {
        String newCustomerId = customerDAO.findNewCustomerId();
        return newCustomerId;
    }

    @Override
    public boolean searchDuplicate(String id) throws NotFoundException {
        if (!customerDAO.existByPk(id)){
            return false;
        }
        return true;
    }

    @Override
    public Optional<Customer> findCustomer(String id) {
        if (!customerDAO.existByPk(id)){
            return Optional.empty();
        }
         return customerDAO.findByPk(id);
    }

    @Override
    public long countCustomer() {
        return customerDAO.count();
    }

    @Override
    public BestCustomerDTO findBestCustomer() {
        return converter.fromBestCustomer(customerDAO.findBestCustomer());
    }
}

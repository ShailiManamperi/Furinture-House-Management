package lk.ijse.system.service.custom;

import lk.ijse.system.dto.BestCustomerDTO;
import lk.ijse.system.dto.CustomerDTO;
import lk.ijse.system.entity.BestCustomer;
import lk.ijse.system.entity.Customer;
import lk.ijse.system.service.SuperService;
import lk.ijse.system.service.exception.DuplicateException;
import lk.ijse.system.service.exception.NotFoundException;

import java.sql.SQLException;
import java.util.Optional;

public interface CustomerService extends SuperService {
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) throws  DuplicateException;

    public CustomerDTO updateCustomer(CustomerDTO customerDTO) throws  NotFoundException;

    public CustomerDTO searchCustomer(String id,String type) throws NotFoundException;

    public boolean deleteCustomer(String id) throws  NotFoundException;

    public String generateNewCustomerId() throws SQLException;

    public boolean searchDuplicate(String id) throws NotFoundException;

    public Optional<Customer> findCustomer(String id);

    long countCustomer();

    BestCustomerDTO findBestCustomer();
}

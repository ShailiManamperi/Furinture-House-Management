package lk.ijse.system.dao.custom;

import lk.ijse.system.dao.CrudDAO;
import lk.ijse.system.dto.CustomerDTO;
import lk.ijse.system.entity.BestCustomer;
import lk.ijse.system.entity.Customer;
import lk.ijse.system.entity.Employee;

public interface CustomerDAO extends CrudDAO<Customer,String> {
    public Customer findCustomer(String id, String type);

    public String findNewCustomerId();

    public BestCustomer findBestCustomer();
}

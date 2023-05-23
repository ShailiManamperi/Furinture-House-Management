package lk.ijse.system.service.custom;

import javafx.collections.ObservableList;
import lk.ijse.system.dto.OrderDTO;
import lk.ijse.system.entity.Order;
import lk.ijse.system.service.SuperService;
import lk.ijse.system.service.exception.NotFoundException;

import java.sql.SQLException;

public interface OrderService extends SuperService {
    public ObservableList<Order> getAllOrders(String Cid) ;

    public String generateNewOrderId() throws NotFoundException;

    public Integer[] geOrderValueMonths() throws SQLException;

    public String findTodaySales();

    String findTodaySalesCount();

}

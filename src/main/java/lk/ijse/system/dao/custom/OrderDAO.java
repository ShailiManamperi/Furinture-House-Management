package lk.ijse.system.dao.custom;

import javafx.collections.ObservableList;
import lk.ijse.system.dao.CrudDAO;
import lk.ijse.system.dao.SuperDAO;
import lk.ijse.system.entity.Order;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Order,String> {
    public ObservableList<Order> getAllOrderByCId(String id);

    public String findNewOrderId();

    public Integer[] geOrderValueMonths() throws SQLException;

    public  String findTodaySales();

    String findTodaySaleCount();
}

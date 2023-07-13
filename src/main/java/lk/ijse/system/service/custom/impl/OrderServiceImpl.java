package lk.ijse.system.service.custom.impl;

import javafx.collections.ObservableList;
import lk.ijse.system.dao.DaoFactory;
import lk.ijse.system.dao.DaoTypes;
import lk.ijse.system.dao.custom.OrderDAO;
import lk.ijse.system.dao.custom.employeeDAO;
import lk.ijse.system.db.DBConnection;
import lk.ijse.system.dto.OrderDTO;
import lk.ijse.system.entity.Order;
import lk.ijse.system.service.custom.OrderService;
import lk.ijse.system.service.exception.NotFoundException;
import lk.ijse.system.service.util.Converter;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderServiceImpl implements OrderService {
    private final Connection connection;
    private final Converter converter;
    private final OrderDAO orderDAO;

    public OrderServiceImpl() {
        connection = DBConnection.getDbConnection().getConnection();
        orderDAO = DaoFactory.getInstance().getDAO(connection, DaoTypes.ORDER);
        converter = new Converter();
    }

    @Override
    public ObservableList<Order> getAllOrders(String Cid)  {
        return orderDAO.getAllOrderByCId(Cid);
    }

    @Override
    public String generateNewOrderId() throws NotFoundException {
        String newOrderId = orderDAO.findNewOrderId();
        return newOrderId;
    }

    @Override
    public Integer[] geOrderValueMonths() throws SQLException {
        return orderDAO.geOrderValueMonths();
    }

    @Override
    public String findTodaySales() {
        return orderDAO.findTodaySales();
    }

    @Override
    public String findTodaySalesCount() {
        return orderDAO.findTodaySaleCount();
    }
}

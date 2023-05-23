package lk.ijse.system.service.custom.impl;

import lk.ijse.system.dao.DaoFactory;
import lk.ijse.system.dao.DaoTypes;
import lk.ijse.system.dao.custom.DeliveryDAO;
import lk.ijse.system.dao.custom.ItemDAO;
import lk.ijse.system.dao.custom.OrderDAO;
import lk.ijse.system.db.DBConnection;
import lk.ijse.system.dto.DeliveryDTO;
import lk.ijse.system.dto.OrderDTO;
import lk.ijse.system.dto.PlaceOrderDTO;
import lk.ijse.system.entity.Order;
import lk.ijse.system.service.custom.PlaceOrderService;
import lk.ijse.system.service.util.Converter;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceOrderServiceImpl implements PlaceOrderService {
    private final Connection connection;
    private final Converter converter;
    private final OrderDAO orderDAO;
    private final ItemDAO itemDAO;
    private final DeliveryDAO deliveryDAO;

    public PlaceOrderServiceImpl() {
        connection = DBConnection.getDbConnection().getConnection();
        orderDAO = DaoFactory.getInstance().getDAO(connection, DaoTypes.ORDER);
        itemDAO = DaoFactory.getInstance().getDAO(connection,DaoTypes.ITEM);
        deliveryDAO = DaoFactory.getInstance().getDAO(connection,DaoTypes.DELIVERY);
        converter = new Converter();
    }
    @Override
    public boolean PlaceOrderWithDelivery(PlaceOrderDTO placeOrder, DeliveryDTO deliveryDTO) throws SQLException, ClassNotFoundException {
        try {
            DBConnection.getDbConnection().getConnection().setAutoCommit(false);
            Order order = converter.toOrder(new OrderDTO(placeOrder.getOid(), placeOrder.getDate(), placeOrder.getCid(), placeOrder.getStatus(), placeOrder.getPrice()));
            Order save = orderDAO.save(order);
            if (save != null) {
                boolean isUpdated = itemDAO.updateQty(placeOrder.getOrderDetails());
                if (isUpdated) {
                    boolean issaved = itemDAO.saveOrderDetails(placeOrder.getOrderDetails(), converter.toPlaceOrder(placeOrder));
                    if (issaved) {
                        boolean saveDelivery = deliveryDAO.saveHaveDelivery(converter.toDelivery(deliveryDTO));
                        if (saveDelivery) {
                            DBConnection.getDbConnection().getConnection().commit();
                            return true;
                        }
                    }
                }
            }
            DBConnection.getDbConnection().getConnection().rollback();
            return false;
        } finally {
            DBConnection.getDbConnection().getConnection().setAutoCommit(true);
        }
    }

    @Override
    public boolean PlaceOrderWithoutDelivery(PlaceOrderDTO placeOrder) throws SQLException, ClassNotFoundException {
        try {
            DBConnection.getDbConnection().getConnection().setAutoCommit(false);
            Order order = converter.toOrder(new OrderDTO(placeOrder.getOid(), placeOrder.getDate(), placeOrder.getCid(), placeOrder.getStatus(), placeOrder.getPrice()));
            Order save = orderDAO.save(order);
            if (save != null) {
                boolean isUpdated = itemDAO.updateQty(placeOrder.getOrderDetails());
                if (isUpdated) {
                    boolean issaved = itemDAO.saveOrderDetails(placeOrder.getOrderDetails(), converter.toPlaceOrder(placeOrder));
                    if (issaved) {
                        DBConnection.getDbConnection().getConnection().commit();
                    }
                }
            }
            DBConnection.getDbConnection().getConnection().rollback();
            return false;
        } finally {
            DBConnection.getDbConnection().getConnection().setAutoCommit(true);
        }
    }
}

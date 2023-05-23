package lk.ijse.system.service.custom.impl;

import lk.ijse.system.dao.DaoFactory;
import lk.ijse.system.dao.DaoTypes;
import lk.ijse.system.dao.custom.DeliveryDAO;
import lk.ijse.system.dao.custom.ItemDAO;
import lk.ijse.system.db.DBConnection;
import lk.ijse.system.service.custom.DeliveryService;
import lk.ijse.system.service.util.Converter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryServiceImpl implements DeliveryService {
    private final Converter converter;
    private final Connection connection;
    private final DeliveryDAO deliveryDAO;

    public DeliveryServiceImpl() {
        connection = DBConnection.getDbConnection().getConnection();
        converter = new Converter();
        deliveryDAO = DaoFactory.getInstance().getDAO(connection, DaoTypes.DELIVERY);

    }
    @Override
    public ArrayList<String> loadVehicleId() throws SQLException, ClassNotFoundException {
        ArrayList<String> vehicleidList = deliveryDAO.loadVehicleIds();
        return vehicleidList;
    }

    @Override
    public String findNewDeliveryId() {
        String newDeliveryId = deliveryDAO.generateNewDeliveryId();
        return newDeliveryId;
    }
}

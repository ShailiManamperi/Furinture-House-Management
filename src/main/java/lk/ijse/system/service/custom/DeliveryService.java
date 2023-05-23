package lk.ijse.system.service.custom;

import lk.ijse.system.service.SuperService;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DeliveryService extends SuperService {
    public ArrayList<String> loadVehicleId() throws SQLException, ClassNotFoundException;

    public String findNewDeliveryId();
}

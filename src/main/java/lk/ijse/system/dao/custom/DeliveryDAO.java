package lk.ijse.system.dao.custom;

import lk.ijse.system.dao.SuperDAO;
import lk.ijse.system.entity.Delivery;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DeliveryDAO extends SuperDAO {
    public ArrayList<String> loadVehicleIds() throws SQLException, ClassNotFoundException;

    public String generateNewDeliveryId();

    public boolean saveDelivery(Delivery de1) throws SQLException;

    boolean saveHaveDelivery(Delivery de1) throws SQLException;
}

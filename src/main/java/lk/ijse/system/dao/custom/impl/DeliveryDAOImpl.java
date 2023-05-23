package lk.ijse.system.dao.custom.impl;

import lk.ijse.system.dao.custom.DeliveryDAO;
import lk.ijse.system.dao.util.DBUtil;
import lk.ijse.system.entity.Delivery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveryDAOImpl implements DeliveryDAO {
    private final Connection connection;
    public DeliveryDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ArrayList<String> loadVehicleIds() throws SQLException{
        String sql = "SELECT V_id FROM vehical";

        ResultSet resultSet = DBUtil.executeQuery(sql);

        ArrayList<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    @Override
    public String generateNewDeliveryId() {
        String deliveryid = null;
        try {
            String sql = "SELECT did FROM havedelivery ORDER BY did DESC LIMIT 1";
            ResultSet result = DBUtil.executeQuery(sql);
            if (!result.next()) {
                deliveryid = generateNextdeliveryId(result.getString(null));
            }
            deliveryid = generateNextdeliveryId(result.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deliveryid;
    }

    private String generateNextdeliveryId(String currentDeliveryId) {
        if (currentDeliveryId == null) {
            return "D-00000001 ";
        }else{
            String[] split = currentDeliveryId.split("D-");
            int id = Integer.parseInt(split[1]);
            id++;
            String newId = String.format("D-%08d", id);
            return newId;
        }
    }

    @Override
    public boolean saveDelivery(Delivery de1) throws SQLException {
        String sql = "INSERT INTO delivery VALUES (?, ?,?,?,?,?)";
        return DBUtil.executeUpdate(sql,
                de1.getDid(),
                de1.getOid(),
                de1.getVid(),
                de1.getCid(),
                de1.getDistance(),
                de1.getAmount()
        );
    }

    @Override
    public boolean saveHaveDelivery(Delivery de1) throws SQLException {
        String sql = "INSERT INTO havedelivery VALUES(?, ? , ? ,?)";
        return DBUtil.executeUpdate(sql,
                de1.getDid(),de1.getOid(),de1.getDistance(),de1.getAmount());
    }
}

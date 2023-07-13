package lk.ijse.system.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.system.dao.custom.OrderDAO;
import lk.ijse.system.dao.exception.ConstraintViolationException;
import lk.ijse.system.dao.util.DBUtil;
import lk.ijse.system.entity.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class OrderDAOImpl implements OrderDAO {
    private final Connection connection;

    public OrderDAOImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Order save(Order entity) throws ConstraintViolationException {
        try {
            if(DBUtil.executeUpdate("INSERT INTO orders (O_id, date, c_id, delivery_status, price) VALUES (?,?,?,?,?);",
                    entity.getOid(),entity.getDate(),entity.getCid(),entity.getStatus(),entity.getPrice())){
                return entity;
            }
            throw new SQLException("Failed to save the order");
        }catch (SQLException e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Order update(Order entity) throws ConstraintViolationException {
        return null;
    }

    @Override
    public boolean deleteByPk(String pk) throws ConstraintViolationException {
        return false;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Optional<Order> findByPk(String pk) {
        return Optional.empty();
    }

    @Override
    public boolean existByPk(String pk) {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM orders WHERE O_id=?", pk);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public ObservableList<Order> getAllOrderByCId(String id) {
        try {
            ObservableList<Order> ob = FXCollections.observableArrayList();
            ResultSet rs = DBUtil.executeQuery("select o.date , o.O_id ,o.price ,o.delivery_status from customer c join orders o on c.C_id = o.c_id where c.C_id =?",id);
            while (rs.next()) {
                ob.add(new Order(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getString(4)));
            }
            System.out.println(ob);
            return ob;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public String findNewOrderId() {
        String itemid = null;
        try {
            String sql = "SELECT O_id FROM orders ORDER BY O_id DESC LIMIT 1";
            ResultSet result = DBUtil.executeQuery(sql);
            if (!result.next()) {
                itemid = generateNextOrderId(result.getString(null));
            }
            itemid = generateNextOrderId(result.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemid;
    }

    @Override
    public Integer[] geOrderValueMonths() throws SQLException {
        Integer[] data = new Integer[12];
        int jan = 0;
        int feb = 0;
        int mar = 0;
        int apr = 0;
        int may = 0;
        int jun = 0;
        int jul = 0;
        int aug = 0;
        int sep = 0;
        int oct = 0;
        int nov = 0;
        int dec = 0;
        String sql = "SELECT MONTH(date), COUNT(O_id) FROM orders GROUP BY MONTH(date) ";
        ResultSet resultSet = DBUtil.executeQuery(sql);

        while (resultSet.next()) {
            switch (resultSet.getString(1)) {
                case "1":
                    jan = Integer.parseInt(resultSet.getString(2));
                    break;
                case "2":
                    feb = Integer.parseInt(resultSet.getString(2));
                    break;
                case "3":
                    mar = Integer.parseInt(resultSet.getString(2));
                    break;
                case "4":
                    apr = Integer.parseInt(resultSet.getString(2));
                    break;
                case "5":
                    may = Integer.parseInt(resultSet.getString(2));
                    break;
                case "6":
                    jun = Integer.parseInt(resultSet.getString(2));
                    break;
                case "7":
                    jul = Integer.parseInt(resultSet.getString(2));
                    break;
                case "8":
                    aug = Integer.parseInt(resultSet.getString(2));
                    break;
                case "9":
                    sep = Integer.parseInt(resultSet.getString(2));
                    break;
                case "10":
                    oct = Integer.parseInt(resultSet.getString(2));
                    break;
                case "11":
                    nov = Integer.parseInt(resultSet.getString(2));
                    break;
                case "12":
                    dec = Integer.parseInt(resultSet.getString(2));
                    break;

            }

            data[0] = jan;
            data[1] = feb;
            data[2] = mar;
            data[3] = apr;
            data[4] = may;
            data[5] = jun;
            data[6] = jul;
            data[7] = aug;
            data[8] = sep;
            data[9] = oct;
            data[10] = nov;
            data[11] = dec;
        }
        return data;
    }

    @Override
    public String findTodaySales() {
        String sql = "SELECT SUM(price) FROM orders WHERE orderDate = ?";
        try {
            ResultSet resultSet = DBUtil.executeQuery(sql);
            if(resultSet.next()){
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "000.00";
    }

    @Override
    public String findTodaySaleCount() {
        String sql = "SELECT COUNT(orderId) FROM orders WHERE orderDate = ?";
        try {
            ResultSet resultSet = DBUtil.executeQuery(sql);
            if(resultSet.next()){
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "0";
    }

    private String generateNextOrderId(String currentOrderId) {
            if (currentOrderId == null) {
                return "O-0000000001 ";
            }else{
                String[] split = currentOrderId.split("O-");
                int id = Integer.parseInt(split[1]);
                id++;
                String newId = String.format("O-%010d", id);
                return newId;
            }

    }
}

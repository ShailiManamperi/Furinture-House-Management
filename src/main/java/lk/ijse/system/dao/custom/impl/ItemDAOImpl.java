package lk.ijse.system.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.system.dao.custom.ItemDAO;
import lk.ijse.system.dao.exception.ConstraintViolationException;
import lk.ijse.system.dao.util.DBUtil;
import lk.ijse.system.dto.BestItemDTO;
import lk.ijse.system.entity.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDAOImpl implements ItemDAO {
    private final Connection connection;

    public ItemDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Item save(Item entity) throws ConstraintViolationException {
        try {
            if(DBUtil.executeUpdate("INSERT INTO items (code, name, Type, get_price, sell_Price, qty, sup_id) VALUES (?,?,?,?,?,?,?)",
                    entity.getCode(),entity.getName(),entity.getType(),entity.getGet_price(),entity.getSell_price(),
                    entity.getQty(),entity.getSupid())){
                return entity;
            }
            throw new SQLException("Failed to save the items");
        }catch (SQLException e){
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public Item update(Item entity) throws ConstraintViolationException {
        try {
            String sql ="UPDATE items SET  name = ?, Type =?, get_price = ?, sell_Price = ?, qty = ?, sup_id = ? WHERE code=?;";
            if(DBUtil.executeUpdate(sql,entity.getName(),entity.getType(),entity.getGet_price(),entity.getSell_price(),
                    entity.getQty(),entity.getSupid(),entity.getCode())){
                return entity;
            }
            throw new SQLException("Failed to update the Item");
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public boolean deleteByPk(String pk) throws ConstraintViolationException {
        try {
            if(!DBUtil.executeUpdate("DELETE FROM items WHERE code=?",pk)){
                return false;
            }
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
        return true;
    }

    @Override
    public List<Item> findAll() {
        try{
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM item");
            return getItemsList(rst);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load the book");
        }
    }

    private List<Item> getItemsList(ResultSet rst) {
        try {
            List<Item> itemList= new ArrayList<>();
            while (rst.next()){
                Item e1 = new Item(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getDouble(4),
                        rst.getDouble(5),
                        rst.getInt(6),
                        rst.getString(7));
                itemList.add(e1);
            }
            return itemList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Item> findByPk(String pk) {
        try{
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM items WHERE code=?", pk);
            if(rst.next()){
                return Optional.of(new Item(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getDouble(4),
                        rst.getDouble(5),
                        rst.getInt(6),
                        rst.getString(7)
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item details");
        }
    }

    @Override
    public boolean existByPk(String pk) {
        try {
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM items WHERE code=?", pk);
            return rst.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        try {
            ResultSet rst  = DBUtil.executeQuery("SELECT COUNT(code) AS count FROM items");
            rst.next();
            return rst.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Item> searchByText(String text) {
        try{
            text="%"+text+"%";
            ResultSet rst = DBUtil.executeQuery("SELECT * FROM items WHERE code LIKE ? OR name LIKE ? ", text, text);
            return getItemsList(rst);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String findNewItemId() {
        String itemid = null;
        try {
            String sql = "SELECT code FROM items ORDER BY code DESC LIMIT 1";
            ResultSet result = DBUtil.executeQuery(sql);
            if (!result.next()) {
                itemid = generateNextItemId(result.getString(null));
            }
            itemid = generateNextItemId(result.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemid;
    }

    private String generateNextItemId(String currentItemId) {
        if (currentItemId == null) {
            return "K00001";
        }else{
            String[] split = currentItemId.split("K");
            int id = Integer.parseInt(split[1]);
            id++;
            String newId = String.format("K%05d", id);
            return newId;
        }
    }

    @Override
    public List<Item> getAllItems() {
        try {
            ObservableList<Item> ob = FXCollections.observableArrayList();
            ResultSet rs = DBUtil.executeQuery("SELECT * FROM items ");
            while (rs.next()) {
                ob.add(new Item(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getInt(6),
                        rs.getString(7)));
            }
            return ob;
        } catch (SQLException e) {
            throw new ConstraintViolationException(e);
        }
    }

    @Override
    public  boolean updateQty(ArrayList<CartDetail> cartDetails) throws SQLException {
        for (CartDetail cartDetail : cartDetails) {
            if (!updateQty(new Item(cartDetail.getQty(),cartDetail.getCode()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean updateLoadQty(ArrayList<Detail> details) throws SQLException {
        for (Detail detail : details){
            if (!updateLoadQty(new Item(detail.getQty(),detail.getCode()))){
                return false;
            }
        }
        return true;
    }

    private boolean updateLoadQty(Item item) throws SQLException {
        String sql ="UPDATE items SET qty = qty + ? WHERE code =?";
        return DBUtil.executeUpdate(sql,item.getQty(),item.getCode());
    }

    private  boolean updateQty(Item q1) throws SQLException {
        String sql = "UPDATE items SET qty = qty - ? WHERE code = ?";
        return DBUtil.executeUpdate(sql,
                q1.getQty(),q1.getCode());
    }

    @Override
    public  boolean saveOrderDetails(ArrayList<CartDetail> cartDetails, PlaceOrder placeOrder) throws SQLException, ClassNotFoundException {
        for (CartDetail cartDetail : cartDetails) {
            if (!save(cartDetail,placeOrder)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ArrayList<String> loadItemIds() throws SQLException {
        String sql = "SELECT code FROM items";

        ResultSet resultSet = DBUtil.executeQuery(sql);
        ArrayList<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    @Override
    public BestItem findBestItem() {
        BestItem b1 = null;
        try {
            String sql = "SELECT order_counts.code, order_counts.num_orders\n" +
                    "FROM (SELECT management.order_detail.code, SUM(qty) as num_orders\n" +
                    "FROM management.order_detail GROUP BY code) as order_counts\n" +
                    "WHERE order_counts.num_orders = (SELECT MAX(num_orders)\n" +
                    "FROM (SELECT SUM(qty) as num_orders\n" +
                    "FROM management.order_detail GROUP BY code) as subquery);";
            ResultSet result = DBUtil.executeQuery(sql);
            if(result.next()){
                b1 =  new BestItem(
                        result.getString(1),
                        result.getInt(2));
            }
            System.out.println(b1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b1;
    }

    private  boolean save(CartDetail cartDetail, PlaceOrder placeOrder) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO order_detail VALUES(?, ?, ?)";
        return DBUtil.executeUpdate(sql,placeOrder.getOid(),cartDetail.getCode(),cartDetail.getQty() );
    }

}

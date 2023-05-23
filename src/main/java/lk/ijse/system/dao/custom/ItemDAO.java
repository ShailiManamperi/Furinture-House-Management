package lk.ijse.system.dao.custom;

import lk.ijse.system.dao.CrudDAO;
import lk.ijse.system.dto.BestItemDTO;
import lk.ijse.system.entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface ItemDAO extends CrudDAO<Item,String> {
    public List<Item> searchByText(String text);

    public String findNewItemId();

    public List<Item> getAllItems();

    public  boolean updateQty(ArrayList<CartDetail> cartDetails) throws SQLException, ClassNotFoundException;

    boolean updateLoadQty(ArrayList<Detail> details) throws SQLException,ClassNotFoundException;

    public  boolean saveOrderDetails(ArrayList<CartDetail> cartDetails, PlaceOrder placeOrder) throws SQLException, ClassNotFoundException;

    public  ArrayList<String> loadItemIds() throws SQLException, ClassNotFoundException;

    public BestItem findBestItem();
}

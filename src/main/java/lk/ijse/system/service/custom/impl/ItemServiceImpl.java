package lk.ijse.system.service.custom.impl;

import lk.ijse.system.dao.DaoFactory;
import lk.ijse.system.dao.DaoTypes;
import lk.ijse.system.dao.custom.ItemDAO;
import lk.ijse.system.db.DBConnection;
import lk.ijse.system.dto.BestItemDTO;
import lk.ijse.system.dto.ItemDTO;
import lk.ijse.system.entity.BestItem;
import lk.ijse.system.entity.Item;
import lk.ijse.system.service.custom.ItemService;
import lk.ijse.system.service.exception.DuplicateException;
import lk.ijse.system.service.exception.NotFoundException;
import lk.ijse.system.service.util.Converter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemServiceImpl implements ItemService {
    private final Converter converter;
    private final Connection connection;
    private final ItemDAO itemDAO;

    public ItemServiceImpl() {
        connection = DBConnection.getDbConnection().getConnection();
        converter = new Converter();
        itemDAO = DaoFactory.getInstance().getDAO(connection, DaoTypes.ITEM);
    }

    @Override
    public ItemDTO saveItem(ItemDTO itemDTO) throws DuplicateException {
        if (itemDAO.existByPk(itemDTO.getCode())) {
            throw new DuplicateException("This Item id is already added!");
        } else {
            itemDAO.save(converter.toItem(itemDTO));
            return itemDTO;
        }
    }

    @Override
    public ItemDTO updatItem(ItemDTO itemDTO) throws NotFoundException {
        if (!itemDAO.existByPk(itemDTO.getCode())) {
            throw new NotFoundException("Item not found!");
        } else {
            itemDAO.update(converter.toItem(itemDTO));
            return itemDTO;
        }
    }

    @Override
    public List<ItemDTO> searchItemList(String text) throws NotFoundException {
        List<Item> itemList = itemDAO.searchByText(text);
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (int i = 0; i<itemList.size(); i++){
            Item item = itemList.get(i);
            itemDTOList.add(i,converter.fromItem(item));
        }
        return itemDTOList;
    }

    @Override
    public boolean deleteItem(String id) throws NotFoundException {
        if (!itemDAO.existByPk(id)){
            throw new NotFoundException("This item id is not found");
        }
        return itemDAO.deleteByPk(id);
    }

    @Override
    public String generateNewItemId() throws SQLException {
        String newCustomerId = itemDAO.findNewItemId();
        return newCustomerId;
    }

    @Override
    public boolean searchDuplicate(String id) throws NotFoundException {
        if (!itemDAO.existByPk(id)){
            return false;
        }
        return true;
    }

    @Override
    public List<ItemDTO> getAllItems() {
        List<Item> allItems = itemDAO.getAllItems();
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (int i = 0; i<allItems.size(); i++){
            Item item = allItems.get(i);
            itemDTOList.add(i,converter.fromItem(item));
        }
        return itemDTOList;
    }

    @Override
    public Optional<Item> searchItem(String id) {
        if (!itemDAO.existByPk(id)) {
            throw new NotFoundException("This item is not founded!");
        }
        return  itemDAO.findByPk(id);
    }

    @Override
    public ArrayList<String> loadItemId() throws SQLException, ClassNotFoundException {
        ArrayList<String> itemids = itemDAO.loadItemIds();
        return itemids;
    }

    @Override
    public BestItemDTO findBestItem() {
        return converter.fromBestItem(itemDAO.findBestItem());
    }
}

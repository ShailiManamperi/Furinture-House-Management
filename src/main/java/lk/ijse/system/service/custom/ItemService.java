package lk.ijse.system.service.custom;

import lk.ijse.system.dto.BestItemDTO;
import lk.ijse.system.dto.CustomerDTO;
import lk.ijse.system.dto.ItemDTO;
import lk.ijse.system.entity.BestItem;
import lk.ijse.system.entity.Item;
import lk.ijse.system.service.SuperService;
import lk.ijse.system.service.exception.DuplicateException;
import lk.ijse.system.service.exception.NotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ItemService extends SuperService {
    public ItemDTO saveItem(ItemDTO itemDTO) throws DuplicateException;

    public ItemDTO updatItem(ItemDTO itemDTO) throws NotFoundException;

    public List<ItemDTO> searchItemList(String text) throws NotFoundException;

    public boolean deleteItem(String id) throws  NotFoundException;

    public String generateNewItemId() throws SQLException;

    public boolean searchDuplicate(String id) throws NotFoundException;

    public List<ItemDTO> getAllItems();

    public Optional<Item> searchItem(String id);

    public ArrayList<String> loadItemId() throws SQLException, ClassNotFoundException;

    public BestItemDTO findBestItem();

}

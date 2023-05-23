package lk.ijse.system.dao.custom;

import lk.ijse.system.dao.CrudDAO;
import lk.ijse.system.entity.Item;
import lk.ijse.system.entity.Supplier;

import java.util.List;

public interface SupplierDAO extends CrudDAO<Supplier,String> {
    public Supplier searchsupplier(String id);

    public List<Supplier> searchByText(String text);

    public String findNewSupplierId();
}

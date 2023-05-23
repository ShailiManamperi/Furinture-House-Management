package lk.ijse.system.dao.custom;

import lk.ijse.system.dao.SuperDAO;
import lk.ijse.system.dao.exception.ConstraintViolationException;
import lk.ijse.system.entity.Supplier_oder;

public interface SupplierOrderDAO extends SuperDAO<Supplier_oder,String> {
    public String findNewLoadId();

    Supplier_oder saveOrder(Supplier_oder entity);

}

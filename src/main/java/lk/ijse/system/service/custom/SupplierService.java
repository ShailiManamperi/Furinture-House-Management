package lk.ijse.system.service.custom;

import lk.ijse.system.dto.ItemDTO;
import lk.ijse.system.dto.SupplierDTO;
import lk.ijse.system.entity.Item;
import lk.ijse.system.entity.Supplier;
import lk.ijse.system.service.SuperService;
import lk.ijse.system.service.exception.DuplicateException;
import lk.ijse.system.service.exception.NotFoundException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SupplierService extends SuperService {
    public Optional<Supplier> searchSupplierByID(String id);

    public SupplierDTO searchSupplier(String text);

    public SupplierDTO saveSupplier(SupplierDTO supplierDTO) throws DuplicateException;

    public SupplierDTO updatSupplier(SupplierDTO supplierDTO) throws NotFoundException;

    public List<SupplierDTO> searchSupplierList(String text) throws NotFoundException;

    public boolean deleteSupplier(String id) throws  NotFoundException;

    public String generateNewSupplierId() throws SQLException;

    public boolean searchDuplicate(String id) throws NotFoundException;

    public List<SupplierDTO> getAllSupplier();

    public Optional<SupplierDTO> searchSupplier1(String id);
}

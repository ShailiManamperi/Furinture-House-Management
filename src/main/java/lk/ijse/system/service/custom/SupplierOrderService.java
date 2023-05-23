package lk.ijse.system.service.custom;

import lk.ijse.system.dto.InvoiceDTO;
import lk.ijse.system.dto.ItemDTO;
import lk.ijse.system.dto.SupplierOrderDTO;
import lk.ijse.system.service.SuperService;
import lk.ijse.system.service.exception.DuplicateException;

import java.sql.SQLException;

public interface SupplierOrderService extends SuperService {
    public SupplierOrderDTO saveSupplierOrder(SupplierOrderDTO supplierOrderDTO) throws DuplicateException;

    public String generateNewId() throws SQLException;

    boolean PlaceLoad(SupplierOrderDTO supplierOrderDTO, InvoiceDTO invoiceDTO) throws ClassNotFoundException,SQLException;

}

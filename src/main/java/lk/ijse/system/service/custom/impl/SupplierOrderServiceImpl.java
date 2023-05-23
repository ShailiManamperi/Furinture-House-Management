package lk.ijse.system.service.custom.impl;

import lk.ijse.system.dao.DaoFactory;
import lk.ijse.system.dao.DaoTypes;
import lk.ijse.system.dao.custom.InvoiceDAO;
import lk.ijse.system.dao.custom.ItemDAO;
import lk.ijse.system.dao.custom.SupplierOrderDAO;
import lk.ijse.system.db.DBConnection;
import lk.ijse.system.dto.InvoiceDTO;
import lk.ijse.system.dto.SupplierOrderDTO;
import lk.ijse.system.entity.Invoice;
import lk.ijse.system.entity.Supplier_oder;
import lk.ijse.system.service.custom.SupplierOrderService;
import lk.ijse.system.service.exception.DuplicateException;
import lk.ijse.system.service.util.Converter;

import java.sql.Connection;
import java.sql.SQLException;

public class SupplierOrderServiceImpl implements SupplierOrderService {
    private final Converter converter;
    private final Connection connection;
    private final SupplierOrderDAO supplierOrderDAO;
    private final ItemDAO itemDAO;
    private final InvoiceDAO invoiceDAO;

    public SupplierOrderServiceImpl() {
        connection = DBConnection.getDbConnection().getConnection();
        converter = new Converter();
        supplierOrderDAO = DaoFactory.getInstance().getDAO(connection, DaoTypes.SUPPLIERORDER);
        itemDAO = DaoFactory.getInstance().getDAO(connection,DaoTypes.ITEM);
        invoiceDAO= DaoFactory.getInstance().getDAO(connection,DaoTypes.INVOICE);
    }

    @Override
    public SupplierOrderDTO saveSupplierOrder(SupplierOrderDTO supplierOrderDTO) throws DuplicateException {
        supplierOrderDAO.saveOrder(converter.toSupplierorder(supplierOrderDTO));
        return supplierOrderDTO;
    }

    @Override
    public String generateNewId() throws SQLException {
        String newLoadId = supplierOrderDAO.findNewLoadId();
        return newLoadId;
    }

    @Override
    public boolean PlaceLoad(SupplierOrderDTO supplierOrderDTO, InvoiceDTO invoiceDTO) throws ClassNotFoundException, SQLException {
        try {
            DBConnection.getDbConnection().getConnection().setAutoCommit(false);
            Supplier_oder supplier_oder = converter.toSupplierorder(supplierOrderDTO);
            Supplier_oder supplier_oder1 = supplierOrderDAO.saveOrder(supplier_oder);
            if (supplier_oder1 != null) {
                boolean updateLoadQty = itemDAO.updateLoadQty(supplier_oder.getDetails());
                if (updateLoadQty) {
                    Invoice invoice = converter.toInvoice(invoiceDTO);
                    String status = supplierOrderDTO.getStatus();
                    Invoice invoice1 = null;
                    Invoice invoice2 = null;
                    if (status.equals("Cash")){
                       invoice1 = invoiceDAO.saveInvoice(invoice);
                    }else {
                       invoice2 = invoiceDAO.savehaveInvoice(invoice);
                    }
                    if ((invoice1 != null) || (invoice2!= null)) {
                        DBConnection.getDbConnection().getConnection().commit();
                        return true;
                    }
                }
            }
            DBConnection.getDbConnection().getConnection().rollback();
            return false;
        } finally {
            DBConnection.getDbConnection().getConnection().setAutoCommit(true);
        }
    }
}

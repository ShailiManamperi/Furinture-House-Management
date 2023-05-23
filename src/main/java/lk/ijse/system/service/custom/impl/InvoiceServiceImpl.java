package lk.ijse.system.service.custom.impl;

import lk.ijse.system.dao.DaoFactory;
import lk.ijse.system.dao.DaoTypes;
import lk.ijse.system.dao.custom.CustomerDAO;
import lk.ijse.system.dao.custom.InvoiceDAO;
import lk.ijse.system.db.DBConnection;
import lk.ijse.system.dto.InvoiceDTO;
import lk.ijse.system.entity.Invoice;
import lk.ijse.system.service.custom.InvoiceService;
import lk.ijse.system.service.exception.DuplicateException;
import lk.ijse.system.service.exception.NotFoundException;
import lk.ijse.system.service.util.Converter;

import java.sql.Connection;

public class InvoiceServiceImpl implements InvoiceService {

    private final Converter converter;
    private final Connection connection;
    private final InvoiceDAO invoiceDAO;

    public InvoiceServiceImpl() {
        connection = DBConnection.getDbConnection().getConnection();
        converter = new Converter();
        invoiceDAO = DaoFactory.getInstance().getDAO(connection,DaoTypes.INVOICE);

    }

    @Override
    public InvoiceDTO savehaveInvoice(InvoiceDTO invoiceDTO) throws DuplicateException {
        Invoice invoice = invoiceDAO.savehaveInvoice(converter.toInvoice(invoiceDTO));
        if (invoice == null){
            throw new DuplicateException("This invoice cannot be added!");
        }
        return converter.fromInvoice(invoice);
    }

    @Override
    public InvoiceDTO saveInvoice(InvoiceDTO invoiceDTO) throws DuplicateException {
        Invoice invoice = invoiceDAO.saveInvoice(converter.toInvoice(invoiceDTO));
        if (invoice == null){
            throw new DuplicateException("This invoice cannot be added!");
        }
        return converter.fromInvoice(invoice);
    }

    @Override
    public boolean deleteInvoice(String id) throws NotFoundException {
        if (invoiceDAO.searchInvoice(id) == null){
            throw new NotFoundException("invoice is not found");
        }
        return invoiceDAO.deleteInvoice(id);
    }

    @Override
    public long countInvoice() {
        return invoiceDAO.count();
    }

    @Override
    public InvoiceDTO searchInvoie(String id) throws NotFoundException {
        if (invoiceDAO.searchInvoice(id) == null){
            throw new NotFoundException("this invoice is not found");
        }
        return converter.fromInvoice(invoiceDAO.searchInvoice(id));
    }
}

package lk.ijse.system.dao.custom;

import lk.ijse.system.dao.SuperDAO;
import lk.ijse.system.entity.Invoice;

public interface InvoiceDAO extends SuperDAO<Invoice,String> {
    Invoice saveInvoice(Invoice entity);

    Invoice savehaveInvoice(Invoice entity);

    Invoice searchInvoice(String id);

    long count();

    boolean deleteInvoice(String id);
}

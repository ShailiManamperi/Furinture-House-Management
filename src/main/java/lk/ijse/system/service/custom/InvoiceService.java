package lk.ijse.system.service.custom;

import lk.ijse.system.dto.InvoiceDTO;
import lk.ijse.system.service.SuperService;
import lk.ijse.system.service.exception.DuplicateException;
import lk.ijse.system.service.exception.NotFoundException;

public interface InvoiceService extends SuperService {
    InvoiceDTO savehaveInvoice(InvoiceDTO invoiceDTO) throws  DuplicateException;

    InvoiceDTO saveInvoice(InvoiceDTO invoiceDTO) throws  DuplicateException;

    boolean deleteInvoice(String id) throws NotFoundException;

    long countInvoice() ;

    InvoiceDTO searchInvoie(String id) throws NotFoundException;
}

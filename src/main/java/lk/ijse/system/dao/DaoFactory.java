package lk.ijse.system.dao;

import lk.ijse.system.dao.custom.impl.*;


import java.sql.Connection;

public class DaoFactory {

    private static DaoFactory daoFactory ;
    private DaoFactory() {
    }

    public static DaoFactory getInstance(){
        return daoFactory==null?(daoFactory=new DaoFactory()):daoFactory;
    }

    public <T extends SuperDAO> T getDAO(Connection connection, DaoTypes daoType) {
        switch (daoType){
            case EMPLOYEE:
                return (T)new employeeDAOImpl(connection);
            case ATTEND:
                return (T)new AttendanceDAOImpl(connection);
            case CUSTOMER:
                return (T)new CustomerDAOImpl(connection);
            case ORDER:
                return (T)new OrderDAOImpl(connection);
            case ITEM:
                return (T)new ItemDAOImpl(connection);
            case SUPPLIER:
                return (T) new SupplierDAOImpl(connection);
            case DELIVERY:
                return (T) new DeliveryDAOImpl(connection);
            case USER:
                return(T) new UserDAOImpl(connection);
            case SUPPLIERORDER:
                return (T) new SupplierOrderDAOImpl(connection);
            case INVOICE:
                return (T) new InvoiceDAOImpl(connection);
            default:
                return null;

        }

    }
}

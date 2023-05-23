package lk.ijse.system.service;

import lk.ijse.system.service.custom.impl.*;

public class ServiceFactory {

    private static ServiceFactory serviceFactory;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance(){
        return serviceFactory==null?(serviceFactory=new ServiceFactory()):serviceFactory;
    }

    public <T extends SuperService> T getService(ServiceTypes serviceTypes){
        switch (serviceTypes){
            case EMPLOYEE:
                return (T)new EmployeeServiceImpl();
            case ATTEND:
                return (T)new AttendanceServiceImpl();
            case CUSTOMER:
                return (T)new CustomerServiceImpl();
            case ORDER:
                return (T) new OrderServiceImpl();
            case ITEM:
                return (T) new ItemServiceImpl();
            case SUPPLIER:
                return (T) new SupplierServiceImpl();
            case DELIVERY:
                return (T) new DeliveryServiceImpl();
            case PLACEORDER:
                return (T)new PlaceOrderServiceImpl();
            case USER:
                return (T) new UserServiceImpl();
            case SUPPLIERORDER:
                return (T) new SupplierOrderServiceImpl();
            case INVOICE:
                return (T) new InvoiceServiceImpl();
            default:
                return null;
        }
    }

}

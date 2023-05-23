package lk.ijse.system.service.util;

import lk.ijse.system.dto.*;
import lk.ijse.system.entity.*;

public class Converter {
    public employeeDTO fromEmployee(Employee employee){
        return new employeeDTO(employee.getEid(),employee.getName(),employee.getDob(), employee.getAddress(), employee.getJob(), employee.getContact(), employee.getSalary(), employee.getNic());
    }

    public Employee toEmployee(employeeDTO eDTO){
        return new Employee(eDTO.getEid(),eDTO.getName(), eDTO.getDob(), eDTO.getAddress(), eDTO.getJob(), eDTO.getContact(), eDTO.getSalary(), eDTO.getNic());
    }

    public AttendanceDTO fromAttendance(Attendance attendance){
        return new AttendanceDTO(attendance.getEid(), attendance.getMonth(), attendance.getWorkdays(),attendance.getNdays(),attendance.getOdays());
    }

    public Attendance toAttendance(AttendanceDTO attendanceDTO){
        return new Attendance(attendanceDTO.getEid(),attendanceDTO.getMonth(),attendanceDTO.getDaycount(),attendanceDTO.getNdays(),attendanceDTO.getOdays());
    }

    public SalaryDTO fromSalary(Salary salary){
        return new SalaryDTO(salary.getType(), salary.getBra(), salary.getApbonus(),salary.getDcbonus(), salary.getNdot(), salary.getSdot());
    }

    public Salary toSalary(SalaryDTO salaryDTO){
        return new Salary(salaryDTO.getType(),salaryDTO.getBra(),salaryDTO.getApbonus(),salaryDTO.getDcbonus(),salaryDTO.getNdot(),salaryDTO.getSdot());
    }

    public CustomerDTO fromCustomer(Customer customer){
        return new CustomerDTO(customer.getCid(),customer.getName(),customer.getAddress(),customer.getNic(),customer.getContact());
    }

    public Customer toCustomer(CustomerDTO customerDTO){
        return new Customer(customerDTO.getCid(),customerDTO.getName(), customerDTO.getAddress(), customerDTO.getNic(), customerDTO.getContact());
    }

    public OrderDTO fromOrder(Order order){
        return new OrderDTO(order.getOid(),order.getDate(),order.getCid(), order.getStatus(), order.getPrice());
    }

    public Order toOrder(OrderDTO orderDTO){
        return new Order(orderDTO.getOid(),orderDTO.getDate(),orderDTO.getCid(), orderDTO.getStatus(),orderDTO.getPrice());
    }

    public PlaceOrderDTO fromPlaceOrder(PlaceOrder placeOrder){
        return new PlaceOrderDTO(placeOrder.getOid(),placeOrder.getDate(),placeOrder.getCid(),placeOrder.getStatus(),placeOrder.getPrice(),placeOrder.getOrderDetails());
    }

    public PlaceOrder toPlaceOrder(PlaceOrderDTO placeOrderDTO){
        return new PlaceOrder(placeOrderDTO.getOid(), placeOrderDTO.getDate(), placeOrderDTO.getCid(), placeOrderDTO.getStatus(), placeOrderDTO.getPrice(), placeOrderDTO.getOrderDetails());
    }

    public ItemDTO fromItem(Item item){
        return new ItemDTO(item.getCode(),item.getName(),item.getType(),item.getGet_price(),item.getSell_price(), item.getQty(), item.getSupid());
    }

    public Item toItem(ItemDTO itemDTO){
        return new Item(itemDTO.getCode(),itemDTO.getName(),itemDTO.getType(),itemDTO.getGet_price(),itemDTO.getSell_price(), itemDTO.getQty(), itemDTO.getSupid());
    }

    public SupplierDTO fromSupplier(Supplier supplier){
        return new SupplierDTO(supplier.getSid(),supplier.getCompany(),supplier.getS_name(),supplier.getAddress(),supplier.getEmail(),supplier.getContact());
    }

    public Supplier toSupplier(SupplierDTO supplierDTO){
        return new Supplier(supplierDTO.getSid(),supplierDTO.getCompany(),supplierDTO.getS_name(),supplierDTO.getAddress(),supplierDTO.getEmail(),supplierDTO.getContact());
    }

    public UserDTO fromUser(User user){
        return new UserDTO(user.getUsername(), user.getType(), user.getPassword(), user.getVerification() ,user.getHint());
    }

    public User toUser(UserDTO userDTO){
        return new User(userDTO.getUsername(), userDTO.getType(), userDTO.getPassword(), userDTO.getVerification() , userDTO.getHint());
    }

    public SupplierOrderDTO fromsupplierOrder(Supplier_oder supplier_oder){
        return new SupplierOrderDTO(supplier_oder.getSoi_id(),supplier_oder.getDate(),supplier_oder.getSup_id(),supplier_oder.getAmount(),supplier_oder.getStatus(),supplier_oder.getDetails());
    }

    public Supplier_oder toSupplierorder(SupplierOrderDTO supplierOrderDTO){
        return new Supplier_oder(supplierOrderDTO.getSoi_id(),supplierOrderDTO.getDate(),supplierOrderDTO.getSup_id(),supplierOrderDTO.getAmount(),supplierOrderDTO.getStatus(),supplierOrderDTO.getDetails());
    }

    public InvoiceDTO fromInvoice(Invoice invoice){
        return new InvoiceDTO(invoice.getInvoice_id(),invoice.getDate(),invoice.getSup_id(), invoice.getAmount());
    }

    public Invoice toInvoice(InvoiceDTO invoiceDTO){
        return new Invoice(invoiceDTO.getInvoice_id(),invoiceDTO.getDate(),invoiceDTO.getSup_id(),invoiceDTO.getAmount());
    }

    public DeliveryDTO fromDelivery(Delivery delivery){
        return new DeliveryDTO(delivery.getDid(),delivery.getOid(),delivery.getVid(),delivery.getCid(),delivery.getDistance(),delivery.getAmount());
    }

    public Delivery toDelivery(DeliveryDTO deliveryDTO){
        return new Delivery(deliveryDTO.getDid(),deliveryDTO.getOid(),deliveryDTO.getVid(),deliveryDTO.getCid(),deliveryDTO.getDistance(),deliveryDTO.getAmount());
    }

    public BestCustomerDTO fromBestCustomer(BestCustomer bestCustomer){
        return new BestCustomerDTO(bestCustomer.getCid(),bestCustomer.getCount());
    }

    public BestCustomer toBestCustomer(BestCustomerDTO bestCustomerDTO) {
        return new BestCustomer(bestCustomerDTO.getCid(), bestCustomerDTO.getCount());
    }

    public BestItemDTO fromBestItem(BestItem bestItem){
        return new BestItemDTO(bestItem.getCode(),bestItem.getCount());
    }

    public BestItem toBestItem(BestItemDTO bestItemDTO) {
        return new BestItem(bestItemDTO.getCode(), bestItemDTO.getCount());
    }
}

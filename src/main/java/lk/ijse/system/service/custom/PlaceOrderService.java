package lk.ijse.system.service.custom;

import lk.ijse.system.dto.DeliveryDTO;
import lk.ijse.system.dto.PlaceOrderDTO;
import lk.ijse.system.entity.PlaceOrder;
import lk.ijse.system.service.SuperService;

import java.sql.SQLException;

public interface PlaceOrderService extends SuperService {
    public boolean PlaceOrderWithDelivery(PlaceOrderDTO placeOrder, DeliveryDTO deliveryDTO) throws SQLException,ClassNotFoundException;

    public boolean PlaceOrderWithoutDelivery(PlaceOrderDTO placeOrder) throws SQLException,ClassNotFoundException;
}

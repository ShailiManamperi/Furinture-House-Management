package lk.ijse.system.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.system.dto.CustomerDTO;
import lk.ijse.system.dto.DeliveryDTO;
import lk.ijse.system.dto.UserDTO;
import lk.ijse.system.entity.Customer;
import lk.ijse.system.entity.PlaceOrder;
import lk.ijse.system.service.ServiceFactory;
import lk.ijse.system.service.ServiceTypes;
import lk.ijse.system.service.custom.CustomerService;
import lk.ijse.system.service.custom.DeliveryService;
import lk.ijse.system.service.custom.PlaceOrderService;
import lk.ijse.system.service.util.Converter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class DeliveryFrameController {
    public Label lbltitle;
    public ImageView imghome;
    public Label lblDate;
    public JFXTextField txtdeliveryid;
    public JFXTextField txtorderid;
    public JFXTextField txtcustomerid;
    public JFXTextField txtcustomername;
    public JFXTextField txtaddress;
    public JFXTextField txtdistance;
    public JFXTextField txtdtotal;
    public JFXTextField txtftotal;
    public JFXButton btncalculate;
    public JFXButton btnfinish;
    public JFXComboBox cmbvehicle;
    public JFXButton btnBill;
    PlaceOrder p1 = orderFrameComtroller.placeOrder;
    UserDTO u1 = SigninFrameController.u1;
    double delivery = 0.0;
    
    public CustomerService customerService;
    public DeliveryService deliveryService;
    public PlaceOrderService placeOrderService;

    public void initialize() throws SQLException, ClassNotFoundException {
        this.customerService = ServiceFactory.getInstance().getService(ServiceTypes.CUSTOMER);
        this.deliveryService = ServiceFactory.getInstance().getService(ServiceTypes.DELIVERY);
        this.placeOrderService = ServiceFactory.getInstance().getService(ServiceTypes.PLACEORDER);
        lblDate.setText(p1.getDate());
        txtorderid.setText(p1.getOid());
        txtcustomerid.setText(p1.getCid());
        CustomerDTO customerDTO = customerService.searchCustomer(p1.getCid(), "C_id");
        txtcustomername.setText(customerDTO.getName());
        txtaddress.setText(customerDTO.getAddress());
        loadDeliveryid();
        loadvehicalid();
        if (p1.getStatus().equals("NO")){
            txtdistance.setText("0");
            txtdtotal.setText("0.0");
            txtftotal.setText(String.valueOf(p1.getPrice()));
        }
    }

    private void loadvehicalid() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> itemIdList = deliveryService.loadVehicleId();
            for (String id : itemIdList) {
                observableList.add(id);
            }
            cmbvehicle.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDeliveryid() {
        String status = p1.getStatus();
        if (status.equals("NO")){
            txtdeliveryid.setText("-");
        }else {
            String newDeliveryId = deliveryService.findNewDeliveryId();
            txtdeliveryid.setText(newDeliveryId);
        }
    }

    public void calculateOnAction(ActionEvent actionEvent) {
        double calculate = calculate();
        txtdtotal.setText(String.valueOf(calculate));
        double price = p1.getPrice();
        double total = price + calculate;
        txtftotal.setText(String.valueOf(total));
    }

    private double calculate() {
        int distance = Integer.parseInt(txtdistance.getText());
        int dist = distance-5;
        double frist = 5 * 150.00;
        if (distance < 5){
            delivery = distance * 200;
        }else {
            if (dist >= 0 && dist <= 10 ){
                delivery = (dist * 250) + frist;
            }if (dist >=11 && dist <= 20){
                delivery = (dist * 350) + frist;
            }if (dist >= 21 && dist <= 30){
                delivery = (dist * 450) + frist;
            }if (dist >= 31 && dist <= 55){
                delivery = (dist * 550) + frist;
            }if (dist > 56){
                delivery = (dist * 650) + frist;
            }
        }
        return delivery;
    }

    public void ordersetupOnAction(ActionEvent actionEvent) {
        String did = txtdeliveryid.getText();
        String oid = txtorderid.getText();
        String vid = cmbvehicle.getSelectionModel().getSelectedItem().toString();
        String cid = txtcustomerid.getText();
        int distane = Integer.parseInt(txtdistance.getText());
        double amount = Double.parseDouble(txtdtotal.getText());
        DeliveryDTO deliveryDTO = new DeliveryDTO(did,oid,vid,cid,distane,amount);
        Converter converter= new Converter();

        if (distane == 0){
            try {
                boolean placeOrder = placeOrderService.PlaceOrderWithoutDelivery(converter.fromPlaceOrder(p1));
                if (placeOrder){
                    new Alert(Alert.AlertType.CONFIRMATION, "The Order is Placed!", ButtonType.OK, ButtonType.CLOSE).show();
                    }else {
                    new Alert(Alert.AlertType.ERROR,"The Order is Not Placd!",ButtonType.CLOSE,ButtonType.OK).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Sql Exception",ButtonType.CLOSE).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Classnot found Exception",ButtonType.CLOSE).show();
            }
        }else {
            try {
                boolean placeOrder = placeOrderService.PlaceOrderWithDelivery(converter.fromPlaceOrder(p1),deliveryDTO);
                if (placeOrder){
                    new Alert(Alert.AlertType.CONFIRMATION,"The Order is Placed!", ButtonType.OK).show();
                }else {
                    new Alert(Alert.AlertType.ERROR,"The Order is Not Placd!",ButtonType.CLOSE).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Sql Exception",ButtonType.CLOSE).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR,"Classnot found Exception",ButtonType.CLOSE).show();
            }
        }

        
    }

    private void generateBill() {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("Cashiername", u1.getUsername());
        hm.put("itemprice", String.valueOf(p1.getPrice()));
        hm.put("deliveryprice", String.valueOf(delivery));
        String total = String.valueOf(p1.getPrice() + delivery);
        hm.put("totalprice", total);
        hm.put("customername", txtcustomername.getText());
        hm.put("address", txtaddress.getText());

        try {
            JasperReport compileReport = JasperCompileManager.compileReport("C:\\Users\\Admin\\Desktop\\project by maven\\project\\src\\main\\resources\\Report\\customerbill.jrxml");
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, hm, new JREmptyDataSource());
            JasperViewer.viewReport(jasperPrint);
        } catch (JRException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    public void HomepageOnMOuseClicked(MouseEvent mouseEvent) {
    }

    public void displayBillOnAction(ActionEvent actionEvent) {
        generateBill();
    }
}

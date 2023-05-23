package lk.ijse.system.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.system.dtm.OrderTm;
import lk.ijse.system.dto.CustomerDTO;
import lk.ijse.system.entity.Order;
import lk.ijse.system.service.ServiceFactory;
import lk.ijse.system.service.ServiceTypes;
import lk.ijse.system.service.custom.CustomerService;
import lk.ijse.system.service.custom.OrderService;
import lk.ijse.system.service.exception.NotFoundException;
import lk.ijse.system.util.Navigation;
import lk.ijse.system.util.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerFrameController {
    public AnchorPane frame;
    public JFXTextField txtSearch;
    public ImageView imgsearch;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnClear;
    public JFXTextField txtcustid;
    public JFXTextField txtcustname;
    public JFXTextField txtaddress;
    public JFXTextField txtnic;
    public JFXTextField txtcontact;
    public JFXRadioButton rdbid;
    public JFXRadioButton rdbnic;
    public JFXRadioButton rdbcontact;
    public TableView tblorders;
    public TableColumn coldate;
    public TableColumn colstatus;
    public TableColumn coloid;
    public TableColumn colprice;
    public JFXButton btnNew;
    public Label lbltitle;

    public static CustomerDTO customerDTO;
    public CustomerService customerService;
    public OrderService orderService;

    public void initialize() throws IOException {
        setCellValueFactory();
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void setCellValueFactory() {
        coldate.setCellValueFactory(new PropertyValueFactory<OrderTm, String>("date"));
        coloid.setCellValueFactory(new PropertyValueFactory<OrderTm, String >("oid"));
        colprice.setCellValueFactory(new PropertyValueFactory<OrderTm, Double>("price"));
        colstatus.setCellValueFactory(new PropertyValueFactory<OrderTm , String>("status"));
    }

    public void searchOnAction(ActionEvent actionEvent) {
        String selectedtype = selectedtype();
        String search = txtSearch.getText();
        this.customerService = ServiceFactory.getInstance().getService(ServiceTypes.CUSTOMER);
        this.orderService = ServiceFactory.getInstance().getService(ServiceTypes.ORDER);
        customerDTO = customerService.searchCustomer(search, selectedtype);
        if (customerDTO == null){
            new Alert(Alert.AlertType.WARNING,"This type customer is not founed!");
        }else{
            txtcustid.setText(customerDTO.getCid());
            txtcustname.setText(customerDTO.getName());
            txtaddress.setText(customerDTO.getAddress());
            txtcontact.setText(customerDTO.getContact());
            txtnic.setText(customerDTO.getNic());
            ObservableList<Order> list = orderService.getAllOrders(customerDTO.getCid());
            if (list == null){
                Alert alert = new Alert(Alert.AlertType.NONE, "There is no Orders", ButtonType.CLOSE);
                alert.show();
            }else {
                List<OrderTm> orderTmList = list.stream().map(order ->
                        new OrderTm(order.getOid(), order.getDate(), order.getPrice(), order.getStatus())).collect(Collectors.toList());
                tblorders.setItems(FXCollections.observableArrayList(orderTmList));
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            }

        }
    }

    private String selectedtype() {
        String type = null;
        if (rdbid.isSelected()){
            type ="C_id";
        }if (rdbnic.isSelected()){
            type = "Nic";
        }if (rdbcontact.isSelected()){
            type = "contact";
        }
        return type;
    }

    public void addCustomerOnAction(ActionEvent actionEvent) {
        CustomerDTO customerDTO = makeObject();
        this.customerService = ServiceFactory.getInstance().getService(ServiceTypes.CUSTOMER);
        boolean searchDuplicate = customerService.searchDuplicate(txtcustid.getText());
        if (searchDuplicate){
            Alert alert = new Alert(Alert.AlertType.WARNING, "This Customer id is already added!");
            alert.show();
        }else{
            CustomerDTO customerDTO1 = customerService.saveCustomer(customerDTO);
            if (customerDTO1 != null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This Customer is Added Succesfully", ButtonType.OK);
                alert.show();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "This Customer is not added sucesccfully!", ButtonType.CLOSE);
                alert.show();
            }
        }

    }

    private CustomerDTO makeObject() {
        String custid = txtcustid.getText();
        String custname = txtcustname.getText();
        String address = txtaddress.getText();
        String contact = txtcontact.getText();
        String nic = txtnic.getText();
        CustomerDTO customerDTO = new CustomerDTO(custid,custname,address,nic,contact);
        return customerDTO;
    }

    public void UpdateCustomerOnAction(ActionEvent actionEvent) {
        this.customerService = ServiceFactory.getInstance().getService(ServiceTypes.CUSTOMER);
        String cid = txtcustid.getText();
        if (customerDTO.getCid().equals(cid)){
            CustomerDTO customerDTO = makeObject();
            CustomerDTO customerDTO1 = customerService.updateCustomer(customerDTO);
            if (customerDTO1 == null){
                new Alert(Alert.AlertType.ERROR,"This Customer is Not Update!",ButtonType.OK).show();
            }else {
                ClearCustomerOnAction(actionEvent);
                new Alert(Alert.AlertType.CONFIRMATION,"This customer Detail is Update Sucessfully!",ButtonType.CLOSE).show();
            }
        }else {
            txtcustid.setText(customerDTO.getCid());
            new Alert(Alert.AlertType.WARNING,"You Cannot Update Customer id!").show();
        }

    }

    public void DeleteCustomerOnAction(ActionEvent actionEvent) {
        this.customerService = ServiceFactory.getInstance().getService(ServiceTypes.CUSTOMER);
        String id = txtcustid.getText();
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure to delete this Customer ?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean deleteCustomer = customerService.deleteCustomer(id);
            if (deleteCustomer) {
                new Alert(Alert.AlertType.INFORMATION, "Customer delete successful").show();
                ClearCustomerOnAction(actionEvent);
            }
        }
    }

    public void ClearCustomerOnAction(ActionEvent actionEvent) {
        txtcustid.clear();
        txtcustname.clear();
        txtcontact.clear();
        txtaddress.clear();
        txtnic.clear();
        txtSearch.clear();
        for ( int i = 0; i<tblorders.getItems().size(); i++) {
            tblorders.getItems().clear();
        }
    }

    public void newCustomeridOnAction(ActionEvent actionEvent) {
        this.customerService = ServiceFactory.getInstance().getService(ServiceTypes.CUSTOMER);
        try {
            String customerId = customerService.generateNewCustomerId();
            txtcustid.setText(customerId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Cannot Generate a Customer id.", ButtonType.CLOSE);
        }

    }

    public void HomePageOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        frame.getChildren().clear();
        Navigation.navigate(Routes.AHOME,frame);
        //Stage window = (Stage) frame.getScene().getWindow();

        //window.setScene(new Scene(FXMLLoader.load(getClass().getResource("./view/AdminDashboardcontent.fxml"))));
    }
}

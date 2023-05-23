package lk.ijse.system.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import lk.ijse.system.dtm.PlaceOrderTM;
import lk.ijse.system.dto.CustomerDTO;
import lk.ijse.system.dto.PlaceOrderDTO;
import lk.ijse.system.dto.UserDTO;
import lk.ijse.system.entity.CartDetail;
import lk.ijse.system.entity.Item;
import lk.ijse.system.entity.PlaceOrder;
import lk.ijse.system.service.ServiceFactory;
import lk.ijse.system.service.ServiceTypes;
import lk.ijse.system.service.custom.CustomerService;
import lk.ijse.system.service.custom.ItemService;
import lk.ijse.system.service.custom.OrderService;
import lk.ijse.system.service.custom.PlaceOrderService;
import lk.ijse.system.service.util.Converter;
import lk.ijse.system.util.Navigation;
import lk.ijse.system.util.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class orderFrameComtroller {
    public AnchorPane frame;
    public Label lbltitle;
    public ImageView imghome;
    public Label lblDate;
    public Label lblorderid;
    public Label lblCustid;
    public Label lblCustName;
    public JFXTextField txtdesc;
    public JFXTextField txtprice;
    public JFXTextField txtqty;
    public JFXTextField txtqtyonhand;
    public JFXComboBox cmbitemid;
    public JFXButton btnaddcart;
    public TableView tbiorderlist;
    public TableColumn colid;
    public TableColumn coldesc;
    public TableColumn colqty;
    public TableColumn colprice;
    public TableColumn colTotal;
    public TableColumn coldelete;
    public JFXButton btnplaceorder;
    public JFXRadioButton rdbyes;
    public JFXRadioButton rdbno;
    public JFXButton btnsearch;
    public JFXTextField txtcustmer;
    public JFXComboBox cmbtype;
    public JFXButton btnNew;
    public Label lbltime;
    public JFXTextField txttotal;
    public JFXTextField txtdistance;
    public JFXButton btndelivery;

    UserDTO u1 = SigninFrameController.u1;

    private ObservableList<PlaceOrderTM> obList = FXCollections.observableArrayList();
    public static PlaceOrder placeOrder;
    public static CustomerDTO customerDTO;

    public OrderService orderService;
    public ItemService itemService;
    public CustomerService customerService;
    public PlaceOrderService placeOrderService;

    public void initialize() throws SQLException, ClassNotFoundException {
        this.orderService = ServiceFactory.getInstance().getService(ServiceTypes.ORDER);
        this.itemService = ServiceFactory.getInstance().getService(ServiceTypes.ITEM);
        this.customerService =ServiceFactory.getInstance().getService(ServiceTypes.CUSTOMER);
        this.placeOrderService = ServiceFactory.getInstance().getService(ServiceTypes.PLACEORDER);
        String[] type = {"Nic","contact"};
        ObservableList<String> list = FXCollections.observableArrayList(type);
        cmbtype.setItems(list);
        lblDate.setText(new SimpleDateFormat("20yy-MM-dd").format(new Date()));
        loaditemid();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> lbltime.setText(new SimpleDateFormat("HH:mm:ss  a").format(Calendar.getInstance().getTime()))),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        loadorderid();
        colid.setCellValueFactory(new PropertyValueFactory<PlaceOrderTM,String >("code"));
        coldesc.setCellValueFactory(new PropertyValueFactory<PlaceOrderTM,String>("description"));
        colqty.setCellValueFactory(new PropertyValueFactory<PlaceOrderTM,Integer>("qty"));
        colprice.setCellValueFactory(new PropertyValueFactory<PlaceOrderTM,Double>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<PlaceOrderTM,Double>("total"));
        coldelete.setCellValueFactory(new PropertyValueFactory<PlaceOrderTM,Button>("btndelete"));

    }

    private void loadorderid() {
        String itemId = orderService.generateNewOrderId();
        lblorderid.setText(itemId);
    }

    private void loaditemid() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> itemIdList = itemService.loadItemId();
            for (String id : itemIdList) {
                observableList.add(id);
            }
            cmbitemid.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void HomepageOnMOuseClicked(MouseEvent mouseEvent) {
    }

    public void itemDetailsonAction(ActionEvent actionEvent) {
        String itemid = cmbitemid.getSelectionModel().getSelectedItem().toString();
        Optional<Item> searchitem = itemService.searchItem(itemid);
        txtdesc.setText(searchitem.get().getName());
        txtprice.setText(String.valueOf(searchitem.get().getSell_price()));
        txtqtyonhand.setText(String.valueOf(searchitem.get().getQty()));
        txtqty.requestFocus();
    }

    public void addcartOnAction(ActionEvent actionEvent) {
        int q1 = Integer.parseInt(txtqtyonhand.getText());
        int q2 = Integer.parseInt(txtqty.getText());
        if (q1 >= q2){
            addCart();
        }else {
            new Alert(Alert.AlertType.WARNING, "there are no enough items to issue in the stock!", ButtonType.OK).show();
        }
    }

    private void addCart() {
        String code = String.valueOf(cmbitemid.getValue());
        int qty = Integer.parseInt(txtqty.getText());
        String desc = txtdesc.getText();
        double unitPrice = Double.parseDouble(txtprice.getText());
        double total = unitPrice * qty;
        Button btnDelete = new Button("Delete");

        txtqty.setText("");

        ObservableList items = tbiorderlist.getItems();
        if (!obList.isEmpty()) {
            L1:
            /* check same item has been in table. If so, update that row instead of adding new row to the table */
            for (int i = 0; i < items.size(); i++) {
                if (colid.getCellData(i).equals(code)) {
                    qty += (int) colqty.getCellData(i);
                    total = unitPrice * qty;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTotal(total);
                    tbiorderlist.refresh();
                    return;
                }
            }
        }
        /* set delete button to some action before it put on obList */
        btnDelete.setOnAction((e) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get().getText().equalsIgnoreCase("yes")) {
                PlaceOrderTM tm = (PlaceOrderTM) tbiorderlist.getSelectionModel().getSelectedItem();
                //tbiorderlist.getItems().removeAll(tm);
                System.out.println("Searching");
                for (int i = 0 ; i <tbiorderlist.getItems().size() ; i++ ){
                    PlaceOrderTM placeOrderTM = (PlaceOrderTM) tbiorderlist.getItems().get(i);
                    System.out.println(placeOrderTM.getCode()+" - "+tm.getCode());
                    if(placeOrderTM.getCode().equals(tm.getCode())){
                        tbiorderlist.getItems().remove(i);
                        System.out.println("Removed : "+i);
                    }
                }
                tbiorderlist.refresh();
                double tot = Double.parseDouble(txttotal.getText());
                double total1 = tm.getTotal();
                tot = tot - total1;
                txttotal.setText(String.valueOf(tot));
            }
        });

        obList.add(new PlaceOrderTM(code, desc, qty, unitPrice, total, btnDelete));
        tbiorderlist.setItems(obList);
        calculateNetTotal();
    }

    private void calculateNetTotal() {
        double total = 0.0;
        for (PlaceOrderTM cartDetail :obList){
            total+=cartDetail.getTotal();
        }
        txttotal.setText(String.valueOf(total));
    }

    public void searchCustomerOnAction(ActionEvent actionEvent) {
        String search = txtcustmer.getText();
        String type = cmbtype.getSelectionModel().getSelectedItem().toString();
        customerDTO = customerService.searchCustomer(search, type);
        lblCustName.setText(customerDTO.getName());
        lblCustid.setText(customerDTO.getCid());
    }

    public void newCustomerOnAction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(
                    new Scene(FXMLLoader.load(
                            Objects.requireNonNull(getClass().getResource(
                                    "/view/customerdetailframe.fxml")))));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void DeliveryFrameOnAction(ActionEvent actionEvent) throws IOException {
        String type = null;
        if (rdbyes.isSelected()){
            type = "YES";
        }if (rdbno.isSelected()){
            type = "NO";
        }
        if(type == null){
            new Alert(Alert.AlertType.WARNING, "Please select the delivery status!").show();
        }
        String oid = lblorderid.getText();
        String date = lblDate.getText();
        String cid = lblCustid.getText();
        double total = Double.parseDouble(txttotal.getText());

        ArrayList<CartDetail> cartDetails = new ArrayList<>();
        /* load all cart items' to orderDetails arrayList */
        for (int i = 0; i < tbiorderlist.getItems().size(); i++) {
            /* get each row details to (PlaceOrderTm)tm in each time and add them to the orderDetails */
            PlaceOrderTM tm = obList.get(i);
            cartDetails.add(new CartDetail(tm.getCode(), tm.getDescription(), tm.getQty(), tm.getUnitPrice(), tm.getTotal()));
        }
        placeOrder = new PlaceOrder(oid, date, cid, type, total, cartDetails);
        deliveryframe();
    }

    private void deliveryframe() {
        Stage stage = new Stage();
        try {
            stage.setScene(
                    new Scene(FXMLLoader.load(
                            Objects.requireNonNull(getClass().getResource(
                                    "/view/DeliveryFrame.fxml")))));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

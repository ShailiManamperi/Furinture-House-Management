package lk.ijse.system.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.system.dtm.detailTM;
import lk.ijse.system.dto.InvoiceDTO;
import lk.ijse.system.dto.SupplierDTO;
import lk.ijse.system.dto.SupplierOrderDTO;
import lk.ijse.system.entity.Detail;
import lk.ijse.system.entity.Invoice;
import lk.ijse.system.entity.Item;
import lk.ijse.system.entity.Supplier_oder;
import lk.ijse.system.service.ServiceFactory;
import lk.ijse.system.service.ServiceTypes;
import lk.ijse.system.service.custom.ItemService;
import lk.ijse.system.service.custom.SupplierOrderService;
import lk.ijse.system.service.custom.SupplierService;
import lk.ijse.system.service.util.Converter;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class NewItemLoadFrameContrller {
    public Label lbldate;
    public TextField lblLoadnum;
    public TextField lbsupplierid;
    public JFXComboBox cmbSupplier;
    public JFXComboBox cmbitemid;
    public TextField lbldesc;
    public TextField lblqty;
    public TextField lblprice;
    public JFXButton btnAdd;
    public TableView tblitems;
    public TableColumn colitemid;
    public TableColumn colqty;
    public TableColumn colprice;
    public JFXTextField lblTotal;
    public JFXTextField txtinvoice;
    public JFXComboBox cmbstatus;
    public JFXButton btnLoad;

    public ItemService itemService;
    public SupplierService supplierService;
    public SupplierOrderService supplierOrderService;

    Optional<Item> item;
    private Supplier_oder supplier_oder;
    private Invoice invoice;
    private ObservableList<detailTM> obList = FXCollections.observableArrayList();

    public void initialize() throws SQLException {
        String[] type = {"Cash","Invoice"};
        ObservableList<String> list = FXCollections.observableArrayList(type);
        cmbstatus.setItems(list);
        this.itemService = ServiceFactory.getInstance().getService(ServiceTypes.ITEM);
        this.supplierOrderService = ServiceFactory.getInstance().getService(ServiceTypes.SUPPLIERORDER);
        this.supplierService =ServiceFactory.getInstance().getService(ServiceTypes.SUPPLIER);
        loadDate();
        loadid();
        loaditemid();
        loadsuppliers();
        colitemid.setCellValueFactory(new PropertyValueFactory<detailTM,String >("code"));
        colqty.setCellValueFactory(new PropertyValueFactory<detailTM,Integer >("qty"));
        colprice.setCellValueFactory(new PropertyValueFactory<detailTM,Double >("price"));
    }

    private void loadsuppliers() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            List<SupplierDTO> allSupplier = supplierService.getAllSupplier();
            for (SupplierDTO name : allSupplier){
                observableList.add(name.getCompany());
            }
            cmbSupplier.setItems(observableList);
        } catch (Exception e){
            e.printStackTrace();
        }
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

    private void loadid() throws SQLException {
        String loadid = supplierOrderService.generateNewId();
        lblLoadnum.setText(loadid);
    }

    private void loadDate() {
        lbldate.setText(new SimpleDateFormat("20yy-MM-dd").format(new Date()));
    }

    public void SupplieridOnAction(ActionEvent actionEvent) {
        String Name = cmbSupplier.getSelectionModel().getSelectedItem().toString();
        SupplierDTO supplierDTO = supplierService.searchSupplier(Name);
        lbsupplierid.setText(supplierDTO.getSid());
    }

    public void ItemnameOnAtion(ActionEvent actionEvent) {
        String id = cmbitemid.getSelectionModel().getSelectedItem().toString();
        item = itemService.searchItem(id);
        lbldesc.setText(item.get().getName());
        lblqty.requestFocus();
    }

    public void AddItemOnAction(ActionEvent actionEvent) {
        String id = cmbitemid.getSelectionModel().getSelectedItem().toString();
        int qty = Integer.parseInt(lblqty.getText());
        double price = Double.parseDouble(lblprice.getText());
        lblqty.setText("");
        lblprice.setText("");

        ObservableList items = tblitems.getItems();
        if (!obList.isEmpty()) {
            L1:
            /* check same item has been in table. If so, update that row instead of adding new row to the table */
            for (int i = 0; i < items.size(); i++) {
                if (colitemid.getCellData(i).equals(id)) {
                    qty += (int) colqty.getCellData(i);
                    price = item.get().getGet_price() * qty;

                    obList.get(i).setQty(qty);
                    obList.get(i).setPrice(price);
                    tblitems.refresh();
                    return;
                }
            }
        }
        obList.add(new detailTM(id,qty,price));
        tblitems.setItems(obList);
        calculateNetTotal();
    }

    private void calculateNetTotal() {
        double total = 0.0;
        for (detailTM de :obList){
            total+=de.getPrice();
        }
        lblTotal.setText(String.valueOf(total));
    }

    public void loadStoreOnAction(ActionEvent actionEvent) {
        String status = cmbstatus.getSelectionModel().getSelectedItem().toString();
        System.out.println(status);
        if(status.equals(null)){
            new Alert(Alert.AlertType.WARNING, "Please select the status!").show();
        }
        String so_id = lblLoadnum.getText();
        String date = lbldate.getText();
        String supid = lbsupplierid.getText();
        double total = Double.parseDouble(lblTotal.getText());
        ArrayList<Detail> details = new ArrayList<>();
        /* load all cart items' to orderDetails arrayList */
        for (int i = 0; i < tblitems.getItems().size(); i++) {
            /* get each row details to (PlaceOrderTm)tm in each time and add them to the orderDetails */
            detailTM detailTM = obList.get(i);
            details.add(new Detail(so_id,detailTM.getCode(),detailTM.getQty(),detailTM.getPrice()));
        }
        supplier_oder = new Supplier_oder(so_id,date,supid,total,status,details);
        invoice = new Invoice(txtinvoice.getText(),date,supid,total);
        System.out.println(supplier_oder);
        System.out.println(invoice);
        Converter converter = new Converter();
        SupplierOrderDTO supplierOrderDTO = converter.fromsupplierOrder(supplier_oder);
        InvoiceDTO invoiceDTO = converter.fromInvoice(invoice);
//        SupplierOrderDTO supplierOrderDTO1 = supplierOrderService.saveSupplierOrder(supplierOrderDTO);
//        if (supplierOrderDTO1 != null){
//            System.out.println("Done");
//        }else{
//            System.out.println("Not Done");
//        }

        try {
            boolean placeLoad = supplierOrderService.PlaceLoad(supplierOrderDTO, invoiceDTO);
            if (placeLoad){
                new Alert(Alert.AlertType.CONFIRMATION,"Load is Added!",ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.ERROR,"CAnnot Added!",ButtonType.CLOSE).show();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void priceOnAction(ActionEvent actionEvent) {
        int qty = Integer.parseInt(lblqty.getText());
        double get_price = item.get().getGet_price();
        double price = qty * get_price;
        lblprice.setText(String.valueOf(price));
    }
}

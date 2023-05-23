package lk.ijse.system.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.system.dtm.ItemTM;
import lk.ijse.system.dto.ItemDTO;
import lk.ijse.system.dto.SupplierDTO;
import lk.ijse.system.entity.Item;
import lk.ijse.system.entity.Supplier;
import lk.ijse.system.service.ServiceFactory;
import lk.ijse.system.service.ServiceTypes;
import lk.ijse.system.service.custom.ItemService;
import lk.ijse.system.service.custom.SupplierService;
import lk.ijse.system.util.Navigation;
import lk.ijse.system.util.Routes;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemFrameController {
    public AnchorPane frame;
    public TableView tblItems;
    public TableColumn colId;
    public TableColumn colname;
    public TableColumn colQty;
    public JFXTextField txtid;
    public JFXTextField txtname;
    public JFXComboBox cmbtype;
    public JFXTextField txtsupplier;
    public JFXTextField txtprice;
    public JFXTextField txtprice1;
    public JFXTextField txtqty;
    public ImageView imghome;
    public JFXTextField txtSearch;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnClear;
    public JFXButton btnNew;

    public ItemService itemService;
    public SupplierService supplierService;
    public ItemTM selectedItem;
    public JFXButton btnnewload;

    public void initialize()  {
        String[] type = {"Melamine","Steel","Aliminium","Wooden","Plastic","Cussion","Metttress","Iron"};
        ObservableList<String> list = FXCollections.observableArrayList(type);
        cmbtype.setItems(list);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        colId.setCellValueFactory(new PropertyValueFactory<ItemTM, String>("code"));
        colname.setCellValueFactory(new PropertyValueFactory<ItemTM, String>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<ItemTM, Integer>("qty"));
        this.itemService = ServiceFactory.getInstance().getService(ServiceTypes.ITEM);
        this.supplierService =ServiceFactory.getInstance().getService(ServiceTypes.SUPPLIER);
        List<ItemDTO> allItems = itemService.getAllItems();

        List<ItemTM> itemTmList = allItems.stream().map(item ->
                new ItemTM(item.getCode(), item.getName(), item.getQty())).collect(Collectors.toList());
        tblItems.setItems(FXCollections.observableArrayList(itemTmList));

        txtSearch.textProperty().addListener((observableValue, pre, curr) -> {
            if (!Objects.equals(pre, curr)) {
                tblItems.getItems().clear();
                this.itemService = ServiceFactory.getInstance().getService(ServiceTypes.ITEM);
                List<ItemTM> searchResult = itemService.searchItemList(curr).stream().map(item ->
                        new ItemTM(item.getCode(), item.getName(), item.getQty())).collect(Collectors.toList());
                tblItems.setItems(FXCollections.observableArrayList(searchResult));
            }
        });

        tblItems.getSelectionModel().selectedItemProperty().addListener((observableValue, pre, curr) -> {
            if (curr != pre || curr != null) {
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            }
        });
    }

    public void itemsdetailOnMouseClikced(MouseEvent mouseEvent) {
        selectedItem = (ItemTM) tblItems.getSelectionModel().getSelectedItem();
        Optional<Item> item = itemService.searchItem(selectedItem.getCode());
        btnDelete.setDisable(false);
        btnUpdate.setDisable(false);
        txtid.setText(item.get().getCode());
        txtname.setText(item.get().getName());
        txtprice.setText(String.valueOf(item.get().getGet_price()));
        txtprice1.setText(String.valueOf(item.get().getSell_price()));
        txtqty.setText(String.valueOf(item.get().getQty()));
        String supid = item.get().getSupid();
        Optional<Supplier> supplier = supplierService.searchSupplierByID(supid);
        txtsupplier.setText(supplier.get().getCompany());
        cmbtype.setValue(item.get().getType());
    }

    public void addItemOnAction(ActionEvent actionEvent) {
        ItemDTO itemDTO = makeObject();
        boolean duplicate = itemService.searchDuplicate(itemDTO.getCode());
        if (duplicate){
            new Alert(Alert.AlertType.WARNING,"This Item id is already added!").show();
        }else {
            ItemDTO itemDTO1 = itemService.saveItem(itemDTO);
            if (itemDTO1 != null){
                tblItems.getItems().add(new ItemTM(itemDTO1.getCode(),itemDTO1.getName(),itemDTO1.getQty()));
                new Alert(Alert.AlertType.CONFIRMATION,"This item added successfully!").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"This item cannot added !").show();
            }
        }

    }

    private ItemDTO makeObject() {
        String id = txtid.getText();
        String name = txtname.getText();
        String supplier = txtsupplier.getText();
        SupplierDTO supplierDTO = supplierService.searchSupplier(supplier);
        double getPrice = Double.parseDouble(txtprice.getText());
        double sellPrice = Double.parseDouble(txtprice1.getText());
        int qty = Integer.parseInt(txtqty.getText());
        String type = cmbtype.getSelectionModel().getSelectedItem().toString();
        return new ItemDTO(id,name,type,getPrice,sellPrice,qty,supplierDTO.getSid());
    }

    public void UpdateItemOnAction(ActionEvent actionEvent) {
        ItemDTO itemDTO = makeObject();
        String text = txtid.getText();
        if (text.equals(selectedItem.getCode())){
            ItemDTO itemDTO1 = itemService.updatItem(itemDTO);
            if (itemDTO1 == null){
                new Alert(Alert.AlertType.ERROR,"This Customer is Not Update!",ButtonType.OK).show();
            }else {
                ClearItemOnAction(actionEvent);
                new Alert(Alert.AlertType.CONFIRMATION,"This customer Detail is Update Sucessfully!",ButtonType.CLOSE).show();
            }
        }else{
            txtid.setText(selectedItem.getCode());
            new Alert(Alert.AlertType.WARNING,"You cannot Update item id").show();
        }
    }

    public void DeleteItemOnAction(ActionEvent actionEvent) {
        String id = txtid.getText();
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure to delete this Item ?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean deleteCustomer = itemService.deleteItem(id);
            if (deleteCustomer) {
                tblItems.getItems().removeAll(tblItems.getSelectionModel().getSelectedItem());
                new Alert(Alert.AlertType.INFORMATION, "Item delete successful").show();
                ClearItemOnAction(actionEvent);
            }
        }
    }

    public void ClearItemOnAction(ActionEvent actionEvent) {
        txtid.clear();
        txtname.clear();
        txtprice.clear();
        txtsupplier.clear();
        txtqty.clear();
        txtprice1.clear();
        txtSearch.clear();
    }

    public void newItemIdOnAction(ActionEvent actionEvent) {
        try {
            String itemId = itemService.generateNewItemId();
            txtid.setText(itemId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Cannot Generate a Item id.", ButtonType.CLOSE).show();
        }
    }

    public void HomePageOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        Navigation.navigate(Routes.AHOME,frame);
    }

    public void addnewitemloadframeOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/newItemLoadFrame.fxml");
        AnchorPane container = FXMLLoader.load(resource);
        Stage stage = new Stage();
        stage.setScene(new Scene(container));
        stage.centerOnScreen();
        stage.show();
    }
}

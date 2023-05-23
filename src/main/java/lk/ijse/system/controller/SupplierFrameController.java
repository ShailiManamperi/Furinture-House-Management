package lk.ijse.system.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.system.dtm.ItemTM;
import lk.ijse.system.dtm.SupplierTM;
import lk.ijse.system.dto.SupplierDTO;
import lk.ijse.system.entity.Supplier;
import lk.ijse.system.service.ServiceFactory;
import lk.ijse.system.service.ServiceTypes;
import lk.ijse.system.service.custom.SupplierService;
import lk.ijse.system.util.Navigation;
import lk.ijse.system.util.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class SupplierFrameController {
    public AnchorPane frame;
    public ImageView imghome;
    public JFXButton btnAdd;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    public JFXButton btnClear;
    public JFXTextField txtid;
    public JFXTextField txtname;
    public JFXTextField txtcomapanyname;
    public JFXTextField txtaddress;
    public JFXTextField txtcontact;
    public JFXTextField txtemail;
    public TableView tblSupplier;
    public TableColumn colid;
    public TableColumn colCname;
    public TableColumn colcontact;
    public JFXTextField txtsearch;
    public JFXButton btnNew;

    public SupplierService supplierService;
    public SupplierTM supplierTM;

    public void initialize() throws IOException {
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);

        this.supplierService = ServiceFactory.getInstance().getService(ServiceTypes.SUPPLIER);

        colid.setCellValueFactory(new PropertyValueFactory<SupplierTM,String>("id"));
        colCname.setCellValueFactory(new PropertyValueFactory<SupplierTM,String>("Cname"));
        colcontact.setCellValueFactory(new PropertyValueFactory<SupplierTM,String>("Contact"));

        List<SupplierDTO> allSupplier = supplierService.getAllSupplier();
        List<SupplierTM> itemTmList = allSupplier.stream().map(supplierDTO ->
                new SupplierTM(supplierDTO.getSid(),supplierDTO.getS_name(), supplierDTO.getContact())).collect(Collectors.toList());
        tblSupplier.setItems(FXCollections.observableArrayList(itemTmList));

        txtsearch.textProperty().addListener((observableValue, pre, curr) -> {
            if (!Objects.equals(pre, curr)) {
                tblSupplier.getItems().clear();
                List<SupplierTM> searchResult = supplierService.searchSupplierList(curr).stream().map(supplier ->
                        new SupplierTM(supplier.getSid(),supplier.getS_name(),supplier.getContact())).collect(Collectors.toList());
                tblSupplier.setItems(FXCollections.observableArrayList(searchResult));
            }
        });

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observableValue, pre, curr) -> {
            if (curr != pre || curr != null) {
                btnUpdate.setDisable(false);
                btnDelete.setDisable(false);
            }
        });
    }

    public void HomePageOnMouseClicked(MouseEvent mouseEvent) throws IOException {
        //Navigation.navigate(Routes.AHOME,frame);
        System.out.println("DashBoard");
    }

    public void addSupplierOnAction(ActionEvent actionEvent) {
        SupplierDTO supplierDTO = makeObject();
        SupplierDTO supplierDTO1 = supplierService.saveSupplier(supplierDTO);
        if (supplierDTO1 == null){
            new Alert(Alert.AlertType.ERROR,"This Supplier is Not added!",ButtonType.CLOSE).show();
        }else{
            tblSupplier.getItems().add(new SupplierTM(supplierDTO1.getSid(),supplierDTO1.getS_name(),supplierDTO1.getContact()));
            new Alert(Alert.AlertType.CONFIRMATION,"This Supplier added sucessfuly", ButtonType.OK).show();
        }
    }

    private SupplierDTO makeObject() {
        String id = txtid.getText();
        String company = txtcomapanyname.getText();
        String snmae = txtname.getText();
        String address = txtaddress.getText();
        String conatct = txtcontact.getText();
        String email = txtemail.getText();

        return new SupplierDTO(id,company,snmae,address,email,conatct);
    }

    public void UpdateSupplierOnAction(ActionEvent actionEvent) {
        SupplierDTO supplierDTO = makeObject();
        if (supplierDTO.getSid().equals(supplierTM.getId())){
            SupplierDTO supplierDTO1 = supplierService.updatSupplier(supplierDTO);
            if (supplierDTO1 != null){
                new Alert(Alert.AlertType.CONFIRMATION,"This Supplier Updated sucessfully",ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.WARNING,"This Supplier not Updated",ButtonType.CLOSE).show();
            }
        }else{
            txtid.setText(supplierTM.getId());
            new Alert(Alert.AlertType.WARNING,"you cannot  update id",ButtonType.CLOSE).show();
        }
    }

    public void DeleteSuplierOnAction(ActionEvent actionEvent) {
        String id = txtid.getText();
        Alert alert = new Alert(Alert.AlertType.WARNING, "Are you sure to delete this Supplier ?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            boolean deleteCustomer = supplierService.deleteSupplier(id);
            if (deleteCustomer) {
                tblSupplier.getItems().removeAll(tblSupplier.getSelectionModel().getSelectedItem());
                new Alert(Alert.AlertType.INFORMATION, "Supplier delete successful").show();
                ClearSupplierOnAction(actionEvent);
            }
        }
    }

    public void ClearSupplierOnAction(ActionEvent actionEvent) {
        txtsearch.clear();
        txtid.clear();
        txtcomapanyname.clear();
        txtname.clear();
        txtemail.clear();
        txtaddress.clear();
        txtcontact.clear();
    }

    public void supplierOnMouseClicked(MouseEvent mouseEvent) {
        supplierTM = (SupplierTM) tblSupplier.getSelectionModel().getSelectedItem();
        Optional<Supplier> supplier = supplierService.searchSupplierByID(supplierTM.getId());
       // System.out.println(supplier.get().getSid());
        txtid.setText(supplier.get().getSid());
        txtcomapanyname.setText(supplier.get().getCompany());
        txtname.setText(supplier.get().getS_name());
        txtaddress.setText(supplier.get().getAddress());
        txtcontact.setText(supplier.get().getContact());
        txtemail.setText(supplier.get().getEmail());
    }

    public void newSupplierIdOnAction(ActionEvent actionEvent) {
        try {
            String itemId = supplierService.generateNewSupplierId();
            txtid.setText(itemId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Cannot Generate a Supplier id.", ButtonType.CLOSE);
        }
    }
}

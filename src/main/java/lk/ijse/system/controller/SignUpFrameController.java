package lk.ijse.system.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.system.dto.UserDTO;
import lk.ijse.system.service.ServiceFactory;
import lk.ijse.system.service.ServiceTypes;
import lk.ijse.system.service.custom.UserService;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpFrameController {
    public AnchorPane paane;
    public JFXTextField txtUsername;
    public JFXComboBox cmbType;
    public JFXPasswordField psfPassword;
    public JFXPasswordField psfRePassword;
    public JFXButton btnLogin;

    public UserService userService;

    public void initialize() throws IOException {
        this.userService = ServiceFactory.getInstance().getService(ServiceTypes.USER);
        cmbType.getItems().addAll("Admin", "Cashier");
        cmbType.setPromptText("Select Login Type");
    }

    public void signupOnAction(ActionEvent actionEvent) {
        String ps = psfPassword.getText();
        String rps = psfRePassword.getText();
        String uname = txtUsername.getText();
        String type = (String) cmbType.getValue();
        String verification = "No";
        if (ps.equals(rps)) {
            if (ps.length() > 8){
                UserDTO u1 = new UserDTO(uname,type,ps,verification);
                try {

                    boolean save = userService.save(u1);

                    if (save) {
                        new Alert(Alert.AlertType.CONFIRMATION, "New user Added!").show();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Something happened!").show();
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }else{
                psfPassword.setFocusColor(Paint.valueOf("Red"));
                psfPassword.requestFocus();

            }
        }else{
            new Alert(Alert.AlertType.WARNING, "re enter password is not match. tryagain!!").show();
            psfPassword.clear();
            psfRePassword.clear();
        }
    }
}
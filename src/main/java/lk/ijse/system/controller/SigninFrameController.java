package lk.ijse.system.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.system.dto.UserDTO;
import lk.ijse.system.entity.User;
import lk.ijse.system.service.ServiceFactory;
import lk.ijse.system.service.ServiceTypes;
import lk.ijse.system.service.custom.UserService;
import lk.ijse.system.util.Navigation;
import lk.ijse.system.util.Routes;

import java.io.IOException;
import java.sql.SQLException;

public class SigninFrameController {
    public AnchorPane pane;
    public JFXButton btnLogin;
    public JFXTextField txtusername;
    public JFXPasswordField tpspassword;
    public static UserDTO u1;

    public UserService userService;

    public void LoginOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        this.userService = ServiceFactory.getInstance().getService(ServiceTypes.USER);
        String uname = txtusername.getText();
        String password = tpspassword.getText();
        UserDTO search = userService.search(uname);

        String type = search.getType();
        String ps = search.getPassword();
        String verification = search.getVerification();
        if (password.equals(ps)){
            if(verification.equals("No")) {
                if (type.equals("Admin")) {
                    u1 = new UserDTO(search.getUsername(),type,search.getPassword(),"Yes");
                    boolean save = userService.update(u1);
                    if(save){
                        Navigation.navigate(Routes.ADMIN, pane);
                    }
                }
                if (type.equals("Cashier")) {
                    u1 = new UserDTO(search.getUsername(),type,search.getPassword(),"Yes");
                    boolean isUpdated = userService.update(u1);
                    if (isUpdated) {
                        Navigation.navigate(Routes.CASHIER, pane);
                    }
                } else {
                    System.out.println("Error 2");
                }
            }else{
                new Alert(Alert.AlertType.WARNING, " This user already login!").show();
            }
        }else {
            tpspassword.setUnFocusColor(Paint.valueOf("#FF0000"));
            new Alert(Alert.AlertType.ERROR,"Enter Password is wrong! please re-enter it").show();
        }
    }
}
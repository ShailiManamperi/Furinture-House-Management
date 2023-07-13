package lk.ijse.system.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.ijse.system.dto.UserDTO;
import lk.ijse.system.service.ServiceFactory;
import lk.ijse.system.service.ServiceTypes;
import lk.ijse.system.service.custom.UserService;
import lk.ijse.system.util.Navigation;
import lk.ijse.system.util.Routes;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;

public class LoginFormController {

    public AnchorPane pane;
    public Button btnLogIn;
    public Label lblforgetPassword;
    public Button btnRegister;
    public JFXTextField txtUserName1;
    public JFXTextField txtUserName;
    public JFXTextField txtHint;
    public JFXTextField txtName;
    public JFXPasswordField txtUserPassword;
    public JFXTextField txtpassowrd;
    public Label lblHint;
    public ImageView imgShow;
    public ImageView imghide;
    public JFXComboBox cmbType;
    public AnchorPane slider;
    public Button btncreate;
    public Button btnlogin;
    public Text txtwelcome;
    public Text txtgreet;
    public Text txtcreate;
    public Text txtlogin;
    private UserService userService;

    public static UserDTO u1;

    public void initialize(){
        setWelcome();
        cmbType.getItems().addAll("Admin", "Cashier");
        cmbType.setPromptText("Select Login Type");
        this.userService = ServiceFactory.getInstance().getService(ServiceTypes.USER);
        imghide.setVisible(false);
        txtpassowrd.setVisible(false);
        txtwelcome.setVisible(false);
        txtlogin.setVisible(false);
        btnlogin.setVisible(false);
    }

    public void SigninOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        String uname = txtUserName1.getText();
        String password = txtUserPassword.getText();
        UserDTO search = userService.search(uname);

        String type = search.getType();
        String ps = search.getPassword();
        String verification = search.getVerification();
        if (password.equals(ps)){
            if(verification.equals("No")) {
                if (type.equals("Admin")) {
                    u1 = new UserDTO(search.getUsername(),type,search.getPassword(),"Yes", search.getHint());
                    boolean save = userService.update(u1);
                    if(save){
                        Navigation.navigate(Routes.ADMIN, pane);
                    }
                }
                if (type.equals("Cashier")) {
                    u1 = new UserDTO(search.getUsername(),type,search.getPassword(),"Yes","1234");
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
            txtUserPassword.setUnFocusColor(Paint.valueOf("#FF0000"));
            new Alert(Alert.AlertType.ERROR,"Enter Password is wrong! please re-enter it").show();
        }
    }

    public void hintOnmouseClicked(MouseEvent mouseEvent) {
        String userName = txtUserName1.getText();
        try {
            UserDTO search = userService.search(userName);
            lblHint.setText(search.getHint());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void SignUpOnAction(ActionEvent actionEvent) {
        String ps = txtUserName.getText();
        String uname = txtName.getText();
        String hint = txtHint.getText();
        String type = (String) cmbType.getValue();
        String verification = "No";
            if (ps.length() > 8){
                UserDTO u1 = new UserDTO(uname,type,ps,verification,hint);
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
                txtUserName.setFocusColor(Paint.valueOf("Red"));
                txtUserName.requestFocus();

            }

    }

    public void ShowPasswordOnMouseClicked(MouseEvent mouseEvent) {
        String password = txtUserPassword.getText();
        txtpassowrd.setVisible(true);
        txtUserPassword.setVisible(false);
        txtpassowrd.setText(password);
        imghide.setVisible(true);
        imgShow.setVisible(false);
    }

    public void HidePasswordOnMouseClicked(MouseEvent mouseEvent) {
        String password = txtpassowrd.getText();
        txtUserPassword.setVisible(true);
        txtpassowrd.setVisible(false);
        txtUserPassword.setText(password);
        imgShow.setVisible(true);
        imghide.setVisible(false);
    }

    public void SignupframOnAction(ActionEvent actionEvent) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(1.5));
        slide.setNode(slider);

        slide.setToX(-410);
        slide.play();
        txtwelcome.setVisible(true);
        txtlogin.setVisible(true);
        btnlogin.setVisible(true);
        txtgreet.setVisible(false);
        txtcreate.setVisible(false);
        btncreate.setVisible(false);

    }

    public void SigninFrameOnAction(ActionEvent actionEvent) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(1.5));
        slide.setNode(slider);

        slide.setToX(0);
        slide.play();
        txtwelcome.setVisible(false);
        txtlogin.setVisible(false);
        btnlogin.setVisible(false);
        txtgreet.setVisible(true);
        txtcreate.setVisible(true);
        btncreate.setVisible(true);
    }

    public void setWelcome() {
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            if (currentTime.getHour() > 6 && currentTime.getHour() < 12) {
                txtgreet.setText("Good Morning ");
            } else if (currentTime.getHour() >= 12 && currentTime.getHour() < 16) {
                txtgreet.setText("Good AfterNoon");
            } else if (currentTime.getHour() >= 16 && currentTime.getHour() < 19) {
                txtgreet.setText("Good Evening");
            } else {
                txtgreet.setText("Good Night");
            }
        }), new KeyFrame(Duration.seconds(1)));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }
}

package lk.ijse.system.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoginFrameController {
    public Label lblTime;
    public Label lblDate;
    public JFXButton btnSignin;
    public JFXButton btnSignup;
    public AnchorPane subpane;
    public AnchorPane pane;
    public AnchorPane frame;

    public void initialize() throws IOException {
        setDateTime();
        Parent load = FXMLLoader.load(getClass().getResource("/view/SigninFrame.fxml"));
        subpane.getChildren().clear();
        subpane.getChildren().add(load);
    }

    private void setDateTime() {
        lblDate.setText(
                new SimpleDateFormat("EEE,dd - MM - 20yy")
                        .format(
                                new Date()
                        ));
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
                event -> lblTime.setText(new SimpleDateFormat("HH:mm:ss  a").format(Calendar.getInstance().getTime()))),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void SigninFrameOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/SigninFrame.fxml"));
        subpane.getChildren().clear();
        subpane.getChildren().add(load);
    }

    public void SignupFrameOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/SignupFrame.fxml"));
        subpane.getChildren().clear();
        subpane.getChildren().add(load);
    }
}
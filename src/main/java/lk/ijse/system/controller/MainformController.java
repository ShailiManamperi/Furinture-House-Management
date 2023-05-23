package lk.ijse.system.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.system.util.Navigation;
import lk.ijse.system.util.Routes;

import java.io.IOException;
import java.net.URL;

public class MainformController {
    public AnchorPane pane;
    public Label lblmain;
    public Label lblmain2;
    public Label lblcp;
    public Rectangle recbar;
    public Rectangle recLoad;
    public Label lblLoading;

    public void initialize(){
        Timeline timeline = new Timeline();

        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), actionEvent -> {
            lblLoading.setText("Initilizing Application.. ");
            recLoad.setWidth(recbar.getWidth()*0.3);
        });
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(1000),actionEvent -> {
            lblLoading.setText("Loading Internal Resources.. ");
            recLoad.setWidth(recbar.getWidth()*0.4);
        });
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(1500),actionEvent -> {
            lblLoading.setText("Loading Database..");
            recLoad.setWidth(recbar.getWidth()*0.5);
        });
        KeyFrame keyFrame3 = new KeyFrame(Duration.millis(2000),actionEvent -> {
            lblLoading.setText("Loading System Ui.. ");
            recLoad.setWidth(recbar.getWidth()*0.6);
        });
        KeyFrame keyFrame4 = new KeyFrame(Duration.millis(2500),actionEvent -> {
            lblLoading.setText("Loading Images.. ");
            recLoad.setWidth(recbar.getWidth()*0.7);
        });
        KeyFrame keyFrame5 = new KeyFrame(Duration.millis(3000),actionEvent -> {
            lblLoading.setText("Getting Started.. ");
            recLoad.setWidth(recbar.getWidth()*0.9);
        });
        KeyFrame keyFrame6 = new KeyFrame(Duration.millis(3500),actionEvent -> {
            lblLoading.setText("Welcome to System.");
            recLoad.setWidth(recbar.getWidth());
        });

        KeyFrame keyFrame7 = new KeyFrame(Duration.millis(4000),actionEvent -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/frame.fxml"));
                Stage window = (Stage) recbar.getScene().getWindow();
                window.hide();
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.centerOnScreen();
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        timeline.getKeyFrames().addAll(keyFrame,keyFrame1,keyFrame2,keyFrame3,keyFrame4,keyFrame5,keyFrame6,keyFrame7);
        timeline.play();
    }
}

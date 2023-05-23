package lk.ijse.system.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static AnchorPane pane;
    private static AnchorPane subpane;

    public static void navigate(Routes route, AnchorPane pane) throws IOException {
        Navigation.pane = pane;
        Navigation.pane.getChildren().clear();
        Stage window = (Stage) Navigation.pane.getScene().getWindow();

        switch (route) {
            case SUPPLIER:
                window.setTitle("Supplier Management");
                initUI("SupplierFrame.fxml");
                break;
            case EMPLOYEE:
                window.setTitle("Employee Management");
                initUI("employeeFrame.fxml");
                break;
            case CUSTOMER:
                window.setTitle("Customer Management");
                initUI("CustomerFrame.fxml");
                break;
            case VEHICLE:
                window.setTitle("Vehicle Management");
                initUI("vehicleFrame.fxml");
                break;
            case ITEMS:
                window.setTitle("Item Management");
                initUI("ItemFrame.fxml");
                break;
            case AHOME:
                window.setTitle("AdminDashbord");
                initUI("AdminDashboardcontent.fxml");
                break;
            case ORDER:
                window.setTitle("Order Management");
                initUI("orderFrame.fxml");
                break;
            case DETAIL:
                window.setTitle("Search Customer");
                initUI("customerdetailframe.fxml");
                break;
            case ADMIN:
                window.setTitle("Admin Dashboard");
                init("AdminFrame.fxml");
                window.centerOnScreen();
                break;
            case CASHIER:
                window.setTitle("Cashier DashBoard");
                init("CashierFrame.fxml");
                window.centerOnScreen();
                break;
            case DELIVERY:
                window.setTitle("Cashier Screen");
                initUI("DeliveryFrame.fxml");
                break;
            case SIGNIN:
                window.setTitle("Login form");
                initUI("frame.fxml");
                break;
        }
    }

    public static void initUI(String location) throws IOException {
        Navigation.pane.getChildren().add(FXMLLoader.load(Navigation.class.getResource("/view/" + location)));
    }

    public static void init(String location) throws IOException {
        Stage window = (Stage) pane.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(Navigation.class.getResource("/view/" + location))));
    }

}

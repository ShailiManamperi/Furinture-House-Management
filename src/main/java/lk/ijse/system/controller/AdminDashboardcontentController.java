package lk.ijse.system.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.system.dao.DaoFactory;
import lk.ijse.system.dto.BestCustomerDTO;
import lk.ijse.system.dto.BestItemDTO;
import lk.ijse.system.dto.CustomerDTO;
import lk.ijse.system.entity.BestCustomer;
import lk.ijse.system.entity.BestItem;
import lk.ijse.system.entity.Customer;
import lk.ijse.system.entity.Item;
import lk.ijse.system.service.ServiceFactory;
import lk.ijse.system.service.ServiceTypes;
import lk.ijse.system.service.custom.CustomerService;
import lk.ijse.system.service.custom.InvoiceService;
import lk.ijse.system.service.custom.ItemService;
import lk.ijse.system.service.custom.OrderService;
import org.bouncycastle.asn1.dvcs.ServiceType;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Optional;

public class AdminDashboardcontentController {
    public AnchorPane frame;
    public Label lblgreet;
    public Label lblCustname;
    public Label lblcustodercount;
    public Label lblItemname;
    public Label lblitemOrdercount;
    public Label lblprice;
    public Label lblordercount;
    public BarChart bargraph;

    private CustomerService customerService;
    private InvoiceService invoiceService;
    private OrderService orderService;
    private ItemService itemService;

    public void initialize() throws SQLException {
        this.customerService = ServiceFactory.getInstance().getService(ServiceTypes.CUSTOMER);
        this.invoiceService = ServiceFactory.getInstance().getService(ServiceTypes.INVOICE);
        this.orderService = ServiceFactory.getInstance().getService(ServiceTypes.ORDER);
        this.itemService = ServiceFactory.getInstance().getService(ServiceTypes.ITEM);
        setWelcome();
        loadbest();
        loadbarchart();
    }

    private void loadbarchart() throws SQLException {
        Integer[] data = orderService.geOrderValueMonths();
        XYChart.Series<String, Integer> series = new XYChart.Series();
        series.setName("No. of Order");
        series.getData().add(new XYChart.Data("JAN", data[0]));
        series.getData().add(new XYChart.Data("FEB", data[1]));
        series.getData().add(new XYChart.Data("MAR", data[2]));
        series.getData().add(new XYChart.Data("APR", data[3]));
        series.getData().add(new XYChart.Data("MAY", data[4]));
        series.getData().add(new XYChart.Data("JUN", data[5]));
        series.getData().add(new XYChart.Data("JUL", data[6]));
        series.getData().add(new XYChart.Data("AUG", data[7]));
        series.getData().add(new XYChart.Data("SEP", data[8]));
        series.getData().add(new XYChart.Data("OCT", data[9]));
        series.getData().add(new XYChart.Data("NOV", data[10]));
        series.getData().add(new XYChart.Data("DEC", data[11]));

        bargraph.getData().addAll(series);

    }

    private void loadbest() {
        BestCustomerDTO bestCustomer = customerService.findBestCustomer();
        CustomerDTO customerDTO = customerService.searchCustomer(bestCustomer.getCid(), "C_id");
        lblCustname.setText(customerDTO.getName());
        lblcustodercount.setText(String.valueOf(bestCustomer.getCount()));

        BestItemDTO bestItem = itemService.findBestItem();
        Optional<Item> item = itemService.searchItem(bestItem.getCode());
        lblItemname.setText(item.get().getName());
        lblitemOrdercount.setText(String.valueOf(bestItem.getCount()));

        String todaySales = orderService.findTodaySales();
        String todaySalesCount = orderService.findTodaySalesCount();
        lblprice.setText(todaySales);
        lblordercount.setText(todaySalesCount);

    }


    public void setWelcome() {
        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            if (currentTime.getHour() > 6 && currentTime.getHour() < 12) {
                lblgreet.setText("Good Morning Admin");
            } else if (currentTime.getHour() >= 12 && currentTime.getHour() < 16) {
                lblgreet.setText("Good AfterNoon Admin");
            } else if (currentTime.getHour() >= 16 && currentTime.getHour() < 19) {
                lblgreet.setText("Good Evening Admin");
            } else {
                lblgreet.setText("Good Night Admin");
            }
        }), new KeyFrame(Duration.seconds(1)));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

}

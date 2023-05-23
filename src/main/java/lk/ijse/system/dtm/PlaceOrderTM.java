package lk.ijse.system.dtm;

import javafx.scene.control.Button;

public class PlaceOrderTM {
    private String code;
    private String description;
    private int qty;
    private double unitPrice;
    private double total;
    private Button btndelete;

    public PlaceOrderTM(String code, String description, int qty, double unitPrice, double total, Button btndelete) {
        this.code = code;
        this.description = description;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.total = total;
        this.btndelete = btndelete;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Button getBtndelete() {
        return btndelete;
    }

    public void setBtndelete(Button btndelete) {
        this.btndelete = btndelete;
    }
}

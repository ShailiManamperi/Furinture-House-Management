package lk.ijse.system.entity;

import java.util.ArrayList;

public class PlaceOrder implements SuperEntity{
    private String oid;
    private String date ;
    private String cid;
    private String status;
    private double price;
    private ArrayList<CartDetail> orderDetails = new ArrayList<>();

    public PlaceOrder(String oid, String date, String cid, String status, double price, ArrayList<CartDetail> orderDetails) {
        this.oid = oid;
        this.date = date;
        this.cid = cid;
        this.status = status;
        this.price = price;
        this.orderDetails = orderDetails;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<CartDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<CartDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

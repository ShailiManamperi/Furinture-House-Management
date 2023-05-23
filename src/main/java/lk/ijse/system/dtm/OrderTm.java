package lk.ijse.system.dtm;

public class OrderTm {
    private String date;
    private String oid;
    private double price;
    private String status;

    public OrderTm(String date, String oid, double price, String status) {
        this.date = date;
        this.oid = oid;
        this.price = price;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

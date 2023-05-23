package lk.ijse.system.entity;

public class Detail implements SuperEntity{
    private String so_id;
    private String code;
    private int qty;
    private double price;

    public Detail(String so_id, String code, int qty, double price) {
        this.so_id = so_id;
        this.code = code;
        this.qty = qty;
        this.price = price;
    }

    public String getSo_id() {
        return so_id;
    }

    public void setSo_id(String so_id) {
        this.so_id = so_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

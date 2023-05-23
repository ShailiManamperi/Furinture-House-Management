package lk.ijse.system.dtm;

public class detailTM {
    private String code;
    private int qty;
    private double price;

    public detailTM(String code, int qty, double price) {
        this.code = code;
        this.qty = qty;
        this.price = price;
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

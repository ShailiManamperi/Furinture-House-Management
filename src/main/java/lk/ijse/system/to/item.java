package lk.ijse.system.to;

public class item {
    private String code;
    private String name;
    private String type;
    private double get_price;
    private double sell_price;
    private int qty;
    private String supid;

    public item() {
    }

    public item(String code, String name, String type, double get_price, double sell_price, int qty, String supid) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.get_price = get_price;
        this.sell_price = sell_price;
        this.qty = qty;
        this.supid = supid;
    }

    public item(int qty, String code) {
        this.qty=qty;
        this.code=code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getGet_price() {
        return get_price;
    }

    public void setGet_price(double get_price) {
        this.get_price = get_price;
    }

    public double getSell_price() {
        return sell_price;
    }

    public void setSell_price(double sell_price) {
        this.sell_price = sell_price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getSupid() {
        return supid;
    }

    public void setSupid(String supid) {
        this.supid = supid;
    }
}

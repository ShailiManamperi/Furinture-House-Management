package lk.ijse.system.dtm;

public class ItemTM {
    private String code;
    private String name;
    private int qty;

    public ItemTM(String code, String name, int qty) {
        this.code = code;
        this.name = name;
        this.qty = qty;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}

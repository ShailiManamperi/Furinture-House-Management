package lk.ijse.system.dto;

public class OrderDTO {
    private String oid;
    private String date;
    private String cid;
    private String status;
    private double price;

    public OrderDTO(String oid, String date, String cid, String status, double price) {
        this.oid = oid;
        this.date = date;
        this.cid = cid;
        this.status = status;
        this.price = price;
    }

    public OrderDTO() {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "oid='" + oid + '\'' +
                ", date='" + date + '\'' +
                ", cid='" + cid + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                '}';
    }
}

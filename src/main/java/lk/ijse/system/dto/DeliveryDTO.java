package lk.ijse.system.dto;

public class DeliveryDTO {
    private String did;
    private String oid;
    private String vid;
    private String cid;
    private int distance;
    private double amount;

    public DeliveryDTO(String did, String oid, String vid, String cid, int distance, double amount) {
        this.did = did;
        this.oid = oid;
        this.vid = vid;
        this.cid = cid;
        this.distance = distance;
        this.amount = amount;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "DeliveryDTO{" +
                "did='" + did + '\'' +
                ", oid='" + oid + '\'' +
                ", vid='" + vid + '\'' +
                ", cid='" + cid + '\'' +
                ", distance=" + distance +
                ", amount=" + amount +
                '}';
    }
}

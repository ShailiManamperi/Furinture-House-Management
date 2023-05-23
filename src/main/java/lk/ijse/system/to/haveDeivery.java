package lk.ijse.system.to;

public class haveDeivery {
    private String DId;
    private String Oid;
    private int distance;
    private double amount;

    public haveDeivery(String DId, String oid, int distance, double amount) {
        this.setDId(DId);
        setOid(oid);
        this.setDistance(distance);
        this.setAmount(amount);
    }

    public haveDeivery() {
    }

    public String getDId() {
        return DId;
    }

    public void setDId(String DId) {
        this.DId = DId;
    }

    public String getOid() {
        return Oid;
    }

    public void setOid(String oid) {
        Oid = oid;
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
}

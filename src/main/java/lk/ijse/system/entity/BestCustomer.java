package lk.ijse.system.entity;

public class BestCustomer {
    String cid;
    int count;

    public BestCustomer(String cid, int count) {
        this.cid = cid;
        this.count = count;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "BestCustomer{" +
                "cid='" + cid + '\'' +
                ", count=" + count +
                '}';
    }
}

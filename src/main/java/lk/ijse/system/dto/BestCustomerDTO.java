package lk.ijse.system.dto;

public class BestCustomerDTO {
    private String Cid;
    private int count;

    public BestCustomerDTO(String cid, int count) {
        Cid = cid;
        this.count = count;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

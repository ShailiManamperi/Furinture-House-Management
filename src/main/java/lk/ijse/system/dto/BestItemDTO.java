package lk.ijse.system.dto;

public class BestItemDTO {
    private String code;
    private int count;

    public BestItemDTO(String code, int count) {
        this.code = code;
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

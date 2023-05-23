package lk.ijse.system.entity;

public class BestItem {
    String code;
    int count;

    public BestItem(String code, int count) {
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

    @Override
    public String toString() {
        return "BestItem{" +
                "code='" + code + '\'' +
                ", count=" + count +
                '}';
    }
}

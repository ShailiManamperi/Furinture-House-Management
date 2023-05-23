package lk.ijse.system.to;

public class attendance {
    private String id ;
    private String month;
    private int workday;
    private int Nday;
    private int Otday;

    public attendance(String id, String month, int workday, int nday, int otday) {
        this.id = id;
        this.month = month;
        this.workday = workday;
        Nday = nday;
        Otday = otday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWorkday() {
        return workday;
    }

    public void setWorkday(int workday) {
        this.workday = workday;
    }

    public int getNday() {
        return Nday;
    }

    public void setNday(int nday) {
        Nday = nday;
    }

    public int getOtday() {
        return Otday;
    }

    public void setOtday(int otday) {
        Otday = otday;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}

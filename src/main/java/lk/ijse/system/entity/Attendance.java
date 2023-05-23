package lk.ijse.system.entity;

public class Attendance implements SuperEntity {
    private String eid;
    private String month;
    private int workdays;
    private int ndays;
    private int odays;

    public Attendance(String eid, String month, int workdays, int ndays, int odays) {
        this.eid = eid;
        this.month = month;
        this.workdays = workdays;
        this.ndays = ndays;
        this.odays = odays;
    }

    public Attendance() {
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getWorkdays() {
        return workdays;
    }

    public void setWorkdays(int workdays) {
        this.workdays = workdays;
    }

    public int getNdays() {
        return ndays;
    }

    public void setNdays(int ndays) {
        this.ndays = ndays;
    }

    public int getOdays() {
        return odays;
    }

    public void setOdays(int odays) {
        this.odays = odays;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "eid='" + eid + '\'' +
                ", month='" + month + '\'' +
                ", workdays=" + workdays +
                ", ndays=" + ndays +
                ", odays=" + odays +
                '}';
    }
}

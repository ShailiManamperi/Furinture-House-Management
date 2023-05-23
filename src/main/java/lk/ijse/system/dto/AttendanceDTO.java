package lk.ijse.system.dto;

public class AttendanceDTO {
    private String eid;
    private String month;
    private int daycount;
    private int ndays;
    private int odays;

    public AttendanceDTO(String eid, String month, int daycount, int ndays, int odays) {
        this.eid = eid;
        this.month = month;
        this.daycount = daycount;
        this.ndays = ndays;
        this.odays = odays;
    }

    public AttendanceDTO() {
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

    public int getDaycount() {
        return daycount;
    }

    public void setDaycount(int daycount) {
        this.daycount = daycount;
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
        return "AttendanceDTO{" +
                "eid='" + eid + '\'' +
                ", month='" + month + '\'' +
                ", daycount=" + daycount +
                ", ndays=" + ndays +
                ", odays=" + odays +
                '}';
    }
}

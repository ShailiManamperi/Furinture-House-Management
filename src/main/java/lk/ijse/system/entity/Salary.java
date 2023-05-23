package lk.ijse.system.entity;

public class Salary implements SuperEntity{
    private String type ;
    private double bra;
    private double apbonus;
    private double dcbonus;
    private double Ndot;
    private double Sdot;

    public Salary(String type, double bra, double apbonus, double dcbonus, double ndot, double sdot) {
        this.type = type;
        this.bra = bra;
        this.apbonus = apbonus;
        this.dcbonus = dcbonus;
        Ndot = ndot;
        Sdot = sdot;
    }

    public Salary() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBra() {
        return bra;
    }

    public void setBra(double bra) {
        this.bra = bra;
    }

    public double getApbonus() {
        return apbonus;
    }

    public void setApbonus(double apbonus) {
        this.apbonus = apbonus;
    }

    public double getDcbonus() {
        return dcbonus;
    }

    public void setDcbonus(double dcbonus) {
        this.dcbonus = dcbonus;
    }

    public double getNdot() {
        return Ndot;
    }

    public void setNdot(double ndot) {
        Ndot = ndot;
    }

    public double getSdot() {
        return Sdot;
    }

    public void setSdot(double sdot) {
        Sdot = sdot;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "type='" + type + '\'' +
                ", bra=" + bra +
                ", apbonus=" + apbonus +
                ", dcbonus=" + dcbonus +
                ", Ndot=" + Ndot +
                ", Sdot=" + Sdot +
                '}';
    }
}

package lk.ijse.system.dto;

public class employeeDTO {
    private String eid;
    private String name;
    private String dob;
    private String address;
    private String job;
    private String contact;
    private double salary;
    private String nic;

    public employeeDTO(String eid, String name, String dob, String address, String job, String contact, double salary, String nic) {
        this.eid = eid;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.job = job;
        this.contact = contact;
        this.salary = salary;
        this.nic = nic;
    }

    public employeeDTO() {
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    @Override
    public String toString() {
        return "employeeDTO{" +
                "eid='" + eid + '\'' +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", address='" + address + '\'' +
                ", job='" + job + '\'' +
                ", contact='" + contact + '\'' +
                ", salary=" + salary +
                ", nic='" + nic + '\'' +
                '}';
    }
}

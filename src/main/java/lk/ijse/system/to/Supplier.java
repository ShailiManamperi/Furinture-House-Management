package lk.ijse.system.to;

public class Supplier {
    private String sid;
    private String company;
    private String s_name;
    private String address;
    private String email;
    private String contact;

    public Supplier(String sid, String company, String s_name, String address, String email, String contact) {
        this.sid = sid;
        this.company = company;
        this.s_name = s_name;
        this.address = address;
        this.email = email;
        this.contact = contact;
    }

    public Supplier() {
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}

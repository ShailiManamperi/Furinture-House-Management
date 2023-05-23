package lk.ijse.system.to;

public class customer {
    private String cid;
    private String name;
    private String address;
    private String nic;
    private String contact;

    public customer(String cid, String name, String address, String nic, String contact) {
        this.cid = cid;
        this.name = name;
        this.address = address;
        this.nic = nic;
        this.contact = contact;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;}
}

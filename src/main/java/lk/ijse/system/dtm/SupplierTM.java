package lk.ijse.system.dtm;

public class SupplierTM {
    private String id;
    private String Cname;
    private String Contact;

    public SupplierTM(String id, String cname, String contact) {
        this.id = id;
        Cname = cname;
        Contact = contact;
    }

    public SupplierTM() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        Cname = cname;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }
}

package lk.ijse.system.to;

public class Vehicle {
    private String id;
    private String type;
    private String did;
    private String num_plte;

    public Vehicle() {
    }

    public Vehicle(String id, String type, String did, String num_plte) {
        this.setId(id);
        this.setType(type);
        this.setDid(did);
        this.setNum_plte(num_plte);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getNum_plte() {
        return num_plte;
    }

    public void setNum_plte(String num_plte) {
        this.num_plte = num_plte;
    }
}

package lk.ijse.system.dto;

public class UserDTO {
    private String username;
    private String type;
    private String password;
    private String verification;

    public UserDTO(String username, String type, String password, String verification) {
        this.username = username;
        this.type = type;
        this.password = password;
        this.verification = verification;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }
}

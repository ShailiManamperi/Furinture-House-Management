package lk.ijse.system.dto;

import lk.ijse.system.entity.Detail;

import java.util.ArrayList;

public class SupplierOrderDTO {
    private String soi_id;
    private String date;
    private String sup_id;
    private double amount;
    private String status;
    private ArrayList<Detail> details = new ArrayList<>();

    public SupplierOrderDTO(String soi_id, String date, String sup_id, double amount, String status, ArrayList<Detail> details) {
        this.soi_id = soi_id;
        this.date = date;
        this.sup_id = sup_id;
        this.amount = amount;
        this.status = status;
        this.details = details;
    }

    public String getSoi_id() {
        return soi_id;
    }

    public void setSoi_id(String soi_id) {
        this.soi_id = soi_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSup_id() {
        return sup_id;
    }

    public void setSup_id(String sup_id) {
        this.sup_id = sup_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Detail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<Detail> details) {
        this.details = details;
    }
}

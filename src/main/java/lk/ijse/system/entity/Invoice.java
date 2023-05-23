package lk.ijse.system.entity;

public class Invoice implements SuperEntity{
    private String invoice_id;
    private String date;
    private String sup_id;
    private double amount;

    public Invoice(String invoice_id, String date, String sup_id, double amount) {
        this.setInvoice_id(invoice_id);
        this.setDate(date);
        this.setSup_id(sup_id);
        this.setAmount(amount);
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
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
}

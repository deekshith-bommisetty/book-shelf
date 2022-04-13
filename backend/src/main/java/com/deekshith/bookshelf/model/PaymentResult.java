package com.deekshith.bookshelf.model;

public class PaymentResult {

    private String Id;

    private String status;

    private String update_time;

    private String email_address;

    public PaymentResult(String id, String status, String update_time, String email_address) {
        Id = id;
        this.status = status;
        this.update_time = update_time;
        this.email_address = email_address;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }
}

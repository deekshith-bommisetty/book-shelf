package com.deekshith.bookshelf.model;

import java.util.Date;

public class PaymentResult {

    private String userId;

    private String status;

    private Date update_time;

    private String email_address;

    public PaymentResult(String userId, String status, Date update_time, String email_address) {
        this.userId = userId;
        this.status = status;
        this.update_time = update_time;
        this.email_address = email_address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }
}

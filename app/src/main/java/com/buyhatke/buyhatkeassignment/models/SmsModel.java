package com.buyhatke.buyhatkeassignment.models;

/**
 * Created by ashishgupta on 02/07/16.
 */
public class SmsModel {
    private String address;
    private String id;
    private String body;
    private String dateSent;
    private String date;
    private String serviceCenter;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setServiceCenter(String serviceCenter) {
        this.serviceCenter = serviceCenter;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }

    public String getBody() {
        return body;
    }
}

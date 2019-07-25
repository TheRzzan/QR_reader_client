package com.morozov.qr_reader.utility;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ticket implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code_ticket")
    @Expose
    private String codeTicket;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("promo")
    @Expose
    private Boolean promo;
    @SerializedName("uni")
    @Expose
    private String uni;
    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("date_event")
    @Expose
    private Integer dateEvent;
    @SerializedName("mob_phone")
    @Expose
    private String mobPhone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodeTicket() {
        return codeTicket;
    }

    public void setCodeTicket(String codeTicket) {
        this.codeTicket = codeTicket;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getPromo() {
        return promo;
    }

    public void setPromo(Boolean promo) {
        this.promo = promo;
    }

    public String getUni() {
        return uni;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Integer getDateEvent() {
        return dateEvent;
    }

    public void setDateEvent(Integer dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getMobPhone() {
        return mobPhone;
    }

    public void setMobPhone(String mobPhone) {
        this.mobPhone = mobPhone;
    }

}

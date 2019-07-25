package com.morozov.qr_reader.utility;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("response")
    @Expose
    private String response;
    @SerializedName("content")
    @Expose
    private Ticket ticket;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Ticket getContent() {
        return ticket;
    }

    public void setContent(Ticket ticket) {
        this.ticket = ticket;
    }
}
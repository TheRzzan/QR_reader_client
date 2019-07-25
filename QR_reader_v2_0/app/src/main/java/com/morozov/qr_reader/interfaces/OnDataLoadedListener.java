package com.morozov.qr_reader.interfaces;

import com.morozov.qr_reader.utility.Ticket;

import java.io.Serializable;

public interface OnDataLoadedListener extends Serializable {
    void onDataLoaded(Ticket ticket);
}

package com.morozov.qr_reader.interfaces;

public interface DataLoader {
    void loadData(String token, OnDataLoadedListener listener);
}

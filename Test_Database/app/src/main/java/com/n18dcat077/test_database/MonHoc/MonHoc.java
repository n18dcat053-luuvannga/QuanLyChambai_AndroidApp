package com.n18dcat077.test_database.MonHoc;

import java.io.Serializable;

public class MonHoc implements Serializable {

    private String maMH, tenMH, ChiPhi;

    public MonHoc() {
    }

    public MonHoc(String maMH, String tenMH, String ChiPhi) {
        this.maMH = maMH;
        this.tenMH = tenMH;
        this.ChiPhi = ChiPhi;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    public String getChiPhi() {
        return ChiPhi;
    }

    public void setChiPhi(String ChiPhi) {
        this.ChiPhi = ChiPhi;
    }
}

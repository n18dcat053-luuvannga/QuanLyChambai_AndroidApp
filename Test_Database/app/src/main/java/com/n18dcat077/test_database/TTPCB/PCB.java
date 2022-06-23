package com.n18dcat077.test_database.TTPCB;

import java.io.Serializable;

public class PCB implements Serializable {

    private String maPhieu, maMH, soBai;
    int TongBai;

    public PCB() {
    }

    public PCB(String maMH, int tongBai) {
        this.maMH = maMH;
        TongBai = tongBai;
    }

    public PCB(String maPhieu, String maMH, String soBai) {
        this.maPhieu = maPhieu;
        this.maMH = maMH;
        this.soBai = soBai;
    }

    public int getTongBai() {
        return TongBai;
    }

    public void setTongBai(int tongBai) {
        TongBai = tongBai;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public String getSoBai() {
        return soBai;
    }

    public void setSoBai(String soBai) {
        this.soBai = soBai;
    }
}

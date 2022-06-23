package com.n18dcat077.test_database.GiaoVien;

import java.io.Serializable;

public class GiaoVien implements Serializable {
    private String id;
    private String hoTen;
    private String sdt;
    private byte[] hinhanh;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GiaoVien(String id, String hoTen, String sdt, byte[] hinhanh, String email) {
        this.id = id;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.hinhanh = hinhanh;
        this.email = email;
    }

    public GiaoVien() {
    }
}

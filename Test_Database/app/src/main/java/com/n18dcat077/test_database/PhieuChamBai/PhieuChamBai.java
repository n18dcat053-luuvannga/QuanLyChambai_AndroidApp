package com.n18dcat077.test_database.PhieuChamBai;

import java.io.Serializable;

public class PhieuChamBai implements Serializable {
    private String SoPhieu, NgayGiao, MaGV, count;

    public PhieuChamBai() {
    }

    public PhieuChamBai(String soPhieu, String ngayGiao, String maGV) {
        SoPhieu = soPhieu;
        NgayGiao = ngayGiao;
        MaGV = maGV;
    }

    public PhieuChamBai(String maGV, String count) {
        MaGV = maGV;
        this.count = count;
    }

    public String getSoPhieu() {
        return SoPhieu;
    }

    public void setSoPhieu(String soPhieu) {
        SoPhieu = soPhieu;
    }

    public String getNgayGiao() {
        return NgayGiao;
    }

    public void setNgayGiao(String ngayGiao) {
        NgayGiao = ngayGiao;
    }

    public String getMaGV() {
        return MaGV;
    }

    public void setMaGV(String maGV) {
        MaGV = maGV;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "PhieuChamBai{" +
                "SoPhieu='" + SoPhieu + '\'' +
                ", NgayGiao='" + NgayGiao + '\'' +
                ", MaGV='" + MaGV + '\'' +
                '}';
    }
}

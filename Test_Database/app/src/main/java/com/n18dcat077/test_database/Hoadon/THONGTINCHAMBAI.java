package com.n18dcat077.test_database.Hoadon;

import com.n18dcat077.test_database.MonHoc.MonHoc;
import com.n18dcat077.test_database.PhieuChamBai.PhieuChamBai;

public class THONGTINCHAMBAI {
    private String sobai;
    private MonHoc monHoc;
    private PhieuChamBai phieuChamBai;
    private String thanhtien;
    private int TongBai;

    public int getTongBai() {
        return TongBai;
    }

    public void setTongBai(int tongBai) {
        TongBai = tongBai;
    }

    public String getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(String thanhtien) {
        this.thanhtien = thanhtien;
    }

    public String getSobai() {
        return sobai;
    }

    public void setSobai(String sobai) {
        this.sobai = sobai;
    }

    public MonHoc getMonHoc() {
        return monHoc;
    }

    public void setMonHoc(MonHoc monHoc) {
        this.monHoc = monHoc;
    }

    public PhieuChamBai getPhieuChamBai() {
        return phieuChamBai;
    }

    public void setPhieuChamBai(PhieuChamBai phieuChamBai) {
        this.phieuChamBai = phieuChamBai;
    }

    public THONGTINCHAMBAI(MonHoc mh, int TongBai) {
        this.monHoc = mh;
        this.TongBai = TongBai;
    }

    public THONGTINCHAMBAI(String sobai, MonHoc monHoc, PhieuChamBai phieuChamBai, String thanhtien) {
        this.sobai = sobai;
        this.monHoc = monHoc;
        this.phieuChamBai = phieuChamBai;
        this.thanhtien = thanhtien;
    }

    public THONGTINCHAMBAI() {
    }
}

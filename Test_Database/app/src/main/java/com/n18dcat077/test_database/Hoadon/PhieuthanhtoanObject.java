package com.n18dcat077.test_database.Hoadon;

import com.n18dcat077.test_database.GiaoVien.GiaoVien;
import com.n18dcat077.test_database.MonHoc.MonHoc;
import com.n18dcat077.test_database.PhieuChamBai.PhieuChamBai;

import java.util.ArrayList;

public class PhieuthanhtoanObject {
    private GiaoVien gv;
    private MonHoc mh;
    private ArrayList<PhieuChamBai> list;

    public GiaoVien getGv() {
        return gv;
    }

    public void setGv(GiaoVien gv) {
        this.gv = gv;
    }
    public MonHoc getMh() {
        return mh;
    }

    public void setMh(MonHoc mh) {
        this.mh = mh;
    }

    public ArrayList<PhieuChamBai> getList() {
        return list;
    }

    public void setList(ArrayList<PhieuChamBai> list) {
        this.list = list;
    }

    public PhieuthanhtoanObject() {
    }

    public PhieuthanhtoanObject(GiaoVien gv, MonHoc mh, ArrayList<PhieuChamBai> list) {
        this.gv = gv;
        this.list = list;
        this.mh = mh;
    }
}

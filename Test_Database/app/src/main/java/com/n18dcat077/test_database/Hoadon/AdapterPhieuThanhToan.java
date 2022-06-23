package com.n18dcat077.test_database.Hoadon;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.n18dcat077.test_database.DatabaseQLCB;
import com.n18dcat077.test_database.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdapterPhieuThanhToan extends ArrayAdapter<THONGTINCHAMBAI>{


    ArrayList<THONGTINCHAMBAI> data;
    Context context;
    int resource;
    DatabaseQLCB databaseQLCB;
    public AdapterPhieuThanhToan(@NonNull Context context, int resource, @NonNull ArrayList<THONGTINCHAMBAI> data) {
        super(context, resource, data);
        this.context=context;
        this.resource=resource;
        this.data=data;
    }
    public static class ViewHolder{
        TextView soPhieu;
        TextView ngay;
        TextView monhoc;
        TextView sobai;
        TextView chiphi;
        TextView thanhtien;
    }
    @Override
    public int getCount() {
        return super.getCount();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        databaseQLCB = new DatabaseQLCB(context);
        View row = convertView;
        ViewHolder holder =null;
        if(row != null){
            holder = (ViewHolder) row.getTag();
        }else{
            holder = new ViewHolder();
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.phieuthanhtoan,parent,false);

            holder.soPhieu = (TextView) row.findViewById(R.id.soPhieuHD);
            holder.ngay = (TextView) row.findViewById(R.id.ngayGiaoHD);
            holder.monhoc = (TextView) row.findViewById(R.id.monhoc);
            holder.chiphi = (TextView) row.findViewById(R.id.chiphiHD);
            holder.sobai = (TextView) row.findViewById(R.id.sobaiHD);
            holder.thanhtien = (TextView) row.findViewById(R.id.thanhtien);
            row.setTag(holder);
        }
        THONGTINCHAMBAI p = data.get(position);
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            holder.soPhieu.setText(p.getPhieuChamBai().getSoPhieu());
            holder.ngay.setText(p.getPhieuChamBai().getNgayGiao());
            holder.monhoc.setText(p.getMonHoc().getTenMH());
            holder.chiphi.setText(formatter.format(Integer.valueOf(p.getMonHoc().getChiPhi())));
            holder.sobai.setText(p.getSobai());

//            long total = 0;
//            long chiphi = Long.valueOf(p.getMonHoc().getChiPhi());
//            long sobai = Long.valueOf(p.getSobai());
//            total = chiphi*sobai;
            holder.thanhtien.setText(formatter.format(Integer.valueOf(p.getThanhtien())));


        return row;
    }
}

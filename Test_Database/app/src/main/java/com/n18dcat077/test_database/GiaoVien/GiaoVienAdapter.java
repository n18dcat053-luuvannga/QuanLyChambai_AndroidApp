package com.n18dcat077.test_database.GiaoVien;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.n18dcat077.test_database.R;

import java.util.ArrayList;

public class GiaoVienAdapter extends ArrayAdapter<GiaoVien> implements Filterable{

    Context context;
    int resource;
    ArrayList<GiaoVien> data;
    ArrayList<GiaoVien> searchList;
    public GiaoViencClickListener giaoViencClickListener;

    public GiaoVienAdapter(@NonNull Context context, int resource, @NonNull ArrayList<GiaoVien>data,GiaoViencClickListener giaoViencClickListener) {
        super(context, resource, data);
        this.context=context;
        this.resource=resource;
        this.data=data;
        this.searchList=data;
        this.giaoViencClickListener=giaoViencClickListener;
    }
    public interface GiaoViencClickListener{
        void select(GiaoVien giaoVien);
    }
    public static class ViewHolder{
        TextView magv;
        TextView hoten;
        TextView sdt;
    }
    @Override
    public int getCount() {
//        return super.getCount();
        return data.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder =null;
        if(row != null){
            holder = (ViewHolder) row.getTag();
        }else{
            holder = new ViewHolder();
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(R.layout.giaovien_item,parent,false);

            holder.magv = (TextView) row.findViewById(R.id.magv);
            holder.hoten = (TextView) row.findViewById(R.id.hotengv);
            holder.sdt = (TextView) row.findViewById(R.id.sdt);
            row.setTag(holder);
        }
        GiaoVien gv = data.get(position);
        holder.magv.setText(gv.getId());
        holder.hoten.setText(gv.getHoTen());
        holder.sdt.setText(gv.getSdt());
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                giaoViencClickListener.select(gv);
            }
        });
        return row;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strsearch = constraint.toString();
                if(strsearch.isEmpty()){
                    data=searchList;
                }
                else{
                    ArrayList<GiaoVien> listGV = new ArrayList<>();
                    for(GiaoVien gv: searchList){
                        if(gv.getHoTen().toLowerCase().contains(strsearch.toLowerCase())){
                            listGV.add(gv);
                        }
                    }
                    data=listGV;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                filterResults.count=data.size();
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = (ArrayList<GiaoVien>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}

package com.n18dcat077.test_database.PhieuChamBai;

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

public class PhieuChamBaiAdapter extends ArrayAdapter<PhieuChamBai> implements Filterable {
    Context context;
    int resource;
    ArrayList<PhieuChamBai> data;
    ArrayList<PhieuChamBai> searchList;
    public PCBListener pcbListener;

    public PhieuChamBaiAdapter(@NonNull Context context, int resource, @NonNull ArrayList<PhieuChamBai> data,PCBListener pcbListener) {
        super(context, resource, data);
        this.context=context;
        this.resource=resource;
        this.data=data;
        this.pcbListener=pcbListener;
        this.searchList=data;
    }
    public interface PCBListener{
        void select(PhieuChamBai PCB);
    }

    @Override
    public int getCount() {
//        return super.getCount();
        return data.size();
    }


    public static class ViewHolder{
        TextView SoPhieu;
        TextView NgayGiao;
        TextView MaGV;
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
            row = inflater.inflate(R.layout.phieuchambai_item,parent,false);

            holder.SoPhieu = (TextView) row.findViewById(R.id.rowSoPhieu);
            holder.NgayGiao = (TextView) row.findViewById(R.id.rowNgayGiao);
            holder.MaGV = (TextView) row.findViewById(R.id.rowMaGV);
            row.setTag(holder);
        }
        PhieuChamBai pcb = data.get(position);
        holder.SoPhieu.setText(pcb.getSoPhieu());
        holder.NgayGiao.setText(pcb.getNgayGiao());
        holder.MaGV.setText(pcb.getMaGV());
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pcbListener.select(pcb);
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
                    ArrayList<PhieuChamBai> listpcb = new ArrayList<>();
                    for(PhieuChamBai pcb: searchList){
                        if(pcb.getSoPhieu().toLowerCase().contains(strsearch.toLowerCase())){
                            listpcb.add(pcb);
                        }
                    }
                    data=listpcb;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                filterResults.count=data.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                data = (ArrayList<PhieuChamBai>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}

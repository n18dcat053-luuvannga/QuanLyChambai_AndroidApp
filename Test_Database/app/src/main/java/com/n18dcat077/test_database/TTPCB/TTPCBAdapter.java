package com.n18dcat077.test_database.TTPCB;

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

public class TTPCBAdapter extends ArrayAdapter<PCB> implements Filterable {

    Context context;
    int resource;
    ArrayList<PCB> data;
    ArrayList<PCB> searchList;
    public TTPCBClickListener ttpcbClickListener;

    public TTPCBAdapter(@NonNull Context context, int resource, @NonNull ArrayList<PCB> data,TTPCBClickListener ttpcbClickListener) {
        super(context, resource, data);
        this.context=context;
        this.resource=resource;
        this.data=data;
        this.searchList=data;
        this.ttpcbClickListener=ttpcbClickListener;
    }

    public static class ViewHolder{
        TextView maphieu;
        TextView mamonhoc;
        TextView sobai;
    }
    public interface TTPCBClickListener{
        void select(PCB pcb);
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
            row = inflater.inflate(R.layout.thongtinpcb_item,parent,false);

            holder.maphieu = (TextView) row.findViewById(R.id.maphieu);
            holder.mamonhoc = (TextView) row.findViewById(R.id.mamh);
            holder.sobai = (TextView) row.findViewById(R.id.sobai);
            row.setTag(holder);
        }
        PCB pcb = data.get(position);
        holder.maphieu.setText(pcb.getMaPhieu());
        holder.mamonhoc.setText((pcb.getMaMH()));
        holder.sobai.setText(pcb.getSoBai());
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ttpcbClickListener.select(pcb);
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
                    ArrayList<PCB> listTTPCB = new ArrayList<>();
                    for(PCB mh: searchList){
                        if(mh.getMaPhieu().toLowerCase().contains(strsearch.toLowerCase())){
                            listTTPCB.add(mh);
                        }
                    }
                    data=listTTPCB;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                filterResults.count=data.size();
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = (ArrayList<PCB>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}

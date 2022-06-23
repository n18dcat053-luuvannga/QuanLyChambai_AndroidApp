package com.n18dcat077.test_database.MonHoc;

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

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MonHocAdapter extends ArrayAdapter<MonHoc> implements Filterable {

    Context context;
    int resource;
    ArrayList<MonHoc> data;
    ArrayList<MonHoc> searchList;
    public MonHocClickListener monHocClickListener;

    public MonHocAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MonHoc> data,MonHocClickListener monHocClickListener) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
        this.searchList = data;
        this.monHocClickListener=monHocClickListener;
    }
    public interface MonHocClickListener{
        void select(MonHoc monHoc);
    }

    public static class ViewHolder {
        TextView mamh;
        TextView tenmh;
        TextView chiphi;
    }

    @Override
    public int getCount() {

//       return super.getCount();
        return data.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        ViewHolder holder = null;
        if (row != null) {
            holder = (ViewHolder) row.getTag();
        } else {
            holder = new ViewHolder();
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.monhoc_item, parent, false);

            holder.mamh = (TextView) row.findViewById(R.id.mamh);
            holder.tenmh = (TextView) row.findViewById(R.id.tenmh);
            holder.chiphi = (TextView) row.findViewById(R.id.chiphi);
            row.setTag(holder);
        }
        MonHoc mh = data.get(position);
        holder.mamh.setText(mh.getMaMH());
        holder.tenmh.setText(mh.getTenMH());
        holder.chiphi.setText(formatter.format(Integer.parseInt(mh.getChiPhi())));
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monHocClickListener.select(mh);
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
                    ArrayList<MonHoc> listMH = new ArrayList<>();
                    for(MonHoc mh: searchList){
                        if(mh.getTenMH().toLowerCase().contains(strsearch.toLowerCase())){
                            listMH.add(mh);
                        }
                    }
                    data=listMH;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                filterResults.count=data.size();
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = (ArrayList<MonHoc>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}

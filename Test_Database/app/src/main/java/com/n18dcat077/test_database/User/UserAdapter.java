package com.n18dcat077.test_database.User;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.n18dcat077.test_database.R;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {

    Context context;
    int resource;
    ArrayList<User> data;

    public UserAdapter(@NonNull Context context, int resource, @NonNull ArrayList<User> data) {
        super(context, resource, data);
        this.context=context;
        this.resource=resource;
        this.data=data;
    }

    public static class ViewHolder{
        TextView username;
        TextView password;
    }
    @Override
    public int getCount() {
        return super.getCount();
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
            row = inflater.inflate(R.layout.user_item,parent,false);

            holder.username = (TextView) row.findViewById(R.id.username_item);
            holder.password = (TextView) row.findViewById(R.id.password_item);
            row.setTag(holder);
        }
        User us = data.get(position);
        holder.username.setText(us.getUsername());
        holder.password.setText(us.getPassword());
        return row;
    }
}

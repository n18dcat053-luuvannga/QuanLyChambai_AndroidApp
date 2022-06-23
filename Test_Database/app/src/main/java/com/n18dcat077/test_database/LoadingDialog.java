package com.n18dcat077.test_database;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialog {
    AlertDialog dialog;
    Activity activity;

    LoadingDialog(Activity myActivity){
        activity=myActivity;
    }

    void startLoadingDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.load_dialog,null));
        builder.setCancelable(true);
        dialog=builder.create();
        dialog.show();
    }
    void dismissDialog(){
        dialog.dismiss();
    }

}

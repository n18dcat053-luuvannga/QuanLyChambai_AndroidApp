package com.n18dcat077.test_database;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.n18dcat077.test_database.GiaoVien.ManagerGiaoVienActivity;
import com.n18dcat077.test_database.Hoadon.DsGiaoVien;
import com.n18dcat077.test_database.MonHoc.QuanLiMonHoc;
import com.n18dcat077.test_database.PhieuChamBai.PhieuChamBaiActivity;
import com.n18dcat077.test_database.TTPCB.QuanLiTTPCB;
import com.n18dcat077.test_database.User.UserActivity;

public class OldMainActivity extends AppCompatActivity {

    Button btnManager, btnManagerPCB, btnquanlimonhoc, btnThongKe,btnQLTTPCB, btnhoadon,btnQlUser;
    DatabaseQLCB db;
    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>Quản lí chấm bài</font>"));
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#83F7FF"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_new_24);
        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);
        setControl();
        setEvent();
    }

    @SuppressLint("ResourceType")
    public void setControl(){
        btnManager = findViewById(R.id.mangerGV);
        btnManagerPCB = findViewById(R.id.managerPCB);
        btnquanlimonhoc = findViewById(R.id.qlmonhocMain);
        btnThongKe = findViewById(R.id.btnThongKe);
        //spinner = findViewById(R.id.spinner);
        btnQLTTPCB=findViewById(R.id.btnQLTTPCB);
        btnhoadon = findViewById(R.id.btnhoadon);
        btnQlUser=findViewById(R.id.btnqlUser);
    }
    public void setEvent(){
        db = new DatabaseQLCB(this);
        //test.setText(x);
        btnManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ManagerGiaoVienActivity.class);
                startActivity(intent);
            }
        });

        btnManagerPCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PhieuChamBaiActivity.class);
                startActivity(intent);
            }
        });
        btnquanlimonhoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuanLiMonHoc.class);
                startActivity(intent);
            }
        });
        btnQLTTPCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QuanLiTTPCB.class);
                startActivity(intent);
            }
        });
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), chart.class);
                startActivity(intent);
            }
        });
        btnhoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DsGiaoVien.class);
                startActivity(intent);
            }
        });
        btnQlUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                startActivity(intent);
            }
        });
    }
}
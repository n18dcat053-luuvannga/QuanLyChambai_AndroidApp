package com.n18dcat077.test_database.User;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.n18dcat077.test_database.DatabaseQLCB;
import com.n18dcat077.test_database.HomeActivity;
import com.n18dcat077.test_database.R;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    DatabaseQLCB db;
    ListView listView;
    UserAdapter userAdapter;
    ArrayList<User> data;
    Button btnAdd;
    TextView username,password,header;
    ArrayList<String> activities = new ArrayList<>();
    BottomNavigationView botNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
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

//    private void initActivity(){
//        activities.add("Quản lí giáo viên");
//        activities.add("Quản lí môn học");
//        activities.add("Quản lí phiếu chấm bài");
//    }

    public void setControl(){
//        username = findViewById(R.id.txttaikhoan);
//        password = findViewById(R.id.txtmatkhau);
        //header = findViewById(R.id.showHeader);
        listView = findViewById(R.id.lvUser);
        btnAdd = findViewById(R.id.btnAddNV);

    }
    public void setEvent(){
//        botNav.setSelectedItemId(R.id.QLGV);
//        botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.QLPCBz:
//                        Intent intent = new Intent(getApplicationContext(), PhieuChamBaiActivity.class);
//                        startActivity(intent);
//                        overridePendingTransition(0, 0);
//                        return true;
//                    case R.id.QLMH:
//                        Intent intent2 = new Intent(getApplicationContext(), QuanLiMonHoc.class);
//                        startActivity(intent2);
//                        overridePendingTransition(0, 1);
//                        return true;
//                    case R.id.TTPCB:
//                        Intent intent1 = new Intent(getApplicationContext(), QuanLiTTPCB.class);
//                        startActivity(intent1);
//                        overridePendingTransition(0, 1);
//                        return true;
//                }
//                return false;
//            }
//        });

        db = new DatabaseQLCB(this);
        /*GiaoVien gv1 = new GiaoVien("GV02","Thanh Tu","0374576967");
        dbGiaoVien.insertGV(gv1);*/
        data = db.getListUser();
        userAdapter = new UserAdapter(this, R.layout.user_item,data);
        listView.setAdapter(userAdapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddUser.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.hold);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User us = data.get(i);
                Intent intent = new Intent(getApplicationContext(), EditUser.class);
                intent.putExtra("USERNAME",us.getUsername());
                intent.putExtra("PASSWORD",us.getPassword());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(myIntent);
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_bottom);

        return true;
    }
}
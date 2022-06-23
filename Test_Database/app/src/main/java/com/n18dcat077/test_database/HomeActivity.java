package com.n18dcat077.test_database;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.n18dcat077.test_database.GiaoVien.ManagerGiaoVienActivity;
import com.n18dcat077.test_database.Hoadon.DsGiaoVien;
import com.n18dcat077.test_database.MonHoc.QuanLiMonHoc;
import com.n18dcat077.test_database.PhieuChamBai.PhieuChamBaiActivity;
import com.n18dcat077.test_database.TTPCB.QuanLiTTPCB;
import com.n18dcat077.test_database.User.UserActivity;

public class HomeActivity extends AppCompatActivity {

    //Button btnManager, btnManagerPCB, btnquanlimonhoc, btnThongKe,btnQLTTPCB, btnhoadon,btnQlUser;
    DatabaseQLCB db;
    BottomNavigationView bottomNav;
    TextView welcome, desRole, labelGV, labelMH, labelPCB, labelTTPCB, labelHoaDon, labelChart;
//    ImageView userManager;
    AppCompatButton btnManager, btnManagerPCB, btnquanlimonhoc, btnThongKe, btnQLTTPCB, btnhoadon, btnLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>Quản lí chấm bài</font>"));
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#83F7FF"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
//        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_new_24);
//        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
//        actionBar.setHomeAsUpIndicator(upArrow);

        // showing the back button in action bar
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setLogo(R.drawable.icon_home_admin);
//        actionBar.setTitle("Dev2Qa.com - News");

        setControl();
        setEvent();
    }

    private void setControl() {
        welcome = (TextView) findViewById(R.id.welcome);
        desRole = (TextView) findViewById(R.id.desRole);
//        userManager = (ImageView) findViewById(R.id.userManager);
        btnManager = (AppCompatButton) findViewById(R.id.mangerGV);
        btnManagerPCB = (AppCompatButton) findViewById(R.id.managerPCB);
        btnquanlimonhoc = (AppCompatButton) findViewById(R.id.qlmonhocMain);
        btnThongKe = (AppCompatButton) findViewById(R.id.btnThongKe);
        btnQLTTPCB = (AppCompatButton) findViewById(R.id.btnQLTTPCB);
        btnhoadon = (AppCompatButton) findViewById(R.id.btnhoadon);
        btnLogOut = (AppCompatButton) findViewById(R.id.logOut);

        // declare
        labelGV = (TextView) findViewById(R.id.labelGV);
        labelMH = (TextView) findViewById(R.id.labelMH);
        labelPCB = (TextView) findViewById(R.id.labelPCB);
        labelTTPCB = (TextView) findViewById(R.id.labelTTPCB);
        labelHoaDon = (TextView) findViewById(R.id.labelHoaDon);
        labelChart = (TextView) findViewById(R.id.labelChart);
    }

    private void setEvent() {
        String session = "admin";
        if(session.equals("admin")) {
//            txtSession.setText(session);
            welcome.setText("Welcome back, " + session);

            btnManager.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ManagerGiaoVienActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_to_right, R.anim.hold);
                }
            });

            btnManagerPCB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), PhieuChamBaiActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_to_right, R.anim.hold);
                }
            });
            btnquanlimonhoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), QuanLiMonHoc.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_to_right, R.anim.hold);
                }
            });
            btnQLTTPCB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), QuanLiTTPCB.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_to_right, R.anim.hold);
                }
            });
            btnThongKe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), gotoChart.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_to_right, R.anim.hold);
                }
            });
            btnhoadon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), DsGiaoVien.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_to_right, R.anim.hold);
                }
            });
//            userManager.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(), UserActivity.class);
//                    startActivity(intent);
//                }
//            });
            btnLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.hold, R.anim.right_to_left);
                }
            });


        } else {
//            txtSession.setText("Bạn không có quyền !!!");
            welcome.setText("Welcome back, " + session);
//            btnGV.setEnabled(false);
            // HIDE BUTTON
//            btnGV.setVisibility(View.GONE);
//            btnMH.setVisibility(View.GONE);
//            btnPCB.setVisibility(View.GONE);
//            btnTTPCB.setVisibility(View.GONE);
//            btnHoaDon.setVisibility(View.GONE);
//            btnChart.setVisibility(View.GONE);
            // HIDE LABEL
//            labelGV.setText("");
//            labelMH.setText("");
//            labelPCB.setText("");
//            labelTTPCB.setText("");
//            labelHoaDon.setText("");
//            labelChart.setText("");

        }
    }



//        EditText txtSession = (EditText) findViewById(R.id.txtSession);
//        TextView welcome = (TextView) findViewById(R.id.welcome);
//        TextView desRole = (TextView) findViewById(R.id.desRole);
//        ImageView userManager = (ImageView) findViewById(R.id.userManager);
//        AppCompatButton btnManager = (AppCompatButton) findViewById(R.id.mangerGV);
//        AppCompatButton btnManagerPCB = (AppCompatButton) findViewById(R.id.managerPCB);
//        AppCompatButton btnquanlimonhoc = (AppCompatButton) findViewById(R.id.qlmonhocMain);
//        AppCompatButton btnThongKe = (AppCompatButton) findViewById(R.id.btnThongKe);
//        AppCompatButton btnQLTTPCB = (AppCompatButton) findViewById(R.id.btnQLTTPCB);
//        AppCompatButton btnhoadon = (AppCompatButton) findViewById(R.id.btnhoadon);
//        AppCompatButton btnLogOut = (AppCompatButton) findViewById(R.id.logOut);
//
//        // declare
//        TextView labelGV = (TextView) findViewById(R.id.labelGV);
//        TextView labelMH = (TextView) findViewById(R.id.labelMH);
//        TextView labelPCB = (TextView) findViewById(R.id.labelPCB);
//        TextView labelTTPCB = (TextView) findViewById(R.id.labelTTPCB);
//        TextView labelHoaDon = (TextView) findViewById(R.id.labelHoaDon);
//        TextView labelChart = (TextView) findViewById(R.id.labelChart);


//        Intent intent = getIntent();
//        String session = intent.getStringExtra("session");
            String session = "admin";

//        if(session.equals("admin")) {
////            txtSession.setText(session);
//            welcome.setText("Welcome back, " + session);
//
//            btnManager.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(), ManagerGiaoVienActivity.class);
//                    startActivity(intent);
//                }
//            });
//
//            btnManagerPCB.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(), PhieuChamBaiActivity.class);
//                    startActivity(intent);
//                }
//            });
//            btnquanlimonhoc.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(), QuanLiMonHoc.class);
//                    startActivity(intent);
//                }
//            });
//            btnQLTTPCB.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(getApplicationContext(), QuanLiTTPCB.class);
//                    startActivity(intent);
//                }
//            });
//            btnThongKe.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(), chart.class);
//                    startActivity(intent);
//                }
//            });
//            btnhoadon.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(), DsGiaoVien.class);
//                    startActivity(intent);
//                }
//            });
//            userManager.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(), UserActivity.class);
//                    startActivity(intent);
//                }
//            });
//            btnLogOut.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    startActivity(intent);
//                }
//            });
//
//
//        } else {
////            txtSession.setText("Bạn không có quyền !!!");
//            welcome.setText("Welcome back, " + session);
////            btnGV.setEnabled(false);
//            // HIDE BUTTON
////            btnGV.setVisibility(View.GONE);
////            btnMH.setVisibility(View.GONE);
////            btnPCB.setVisibility(View.GONE);
////            btnTTPCB.setVisibility(View.GONE);
////            btnHoaDon.setVisibility(View.GONE);
////            btnChart.setVisibility(View.GONE);
//            // HIDE LABEL
////            labelGV.setText("");
////            labelMH.setText("");
////            labelPCB.setText("");
////            labelTTPCB.setText("");
////            labelHoaDon.setText("");
////            labelChart.setText("");
//
//        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manager_user,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_managerUser:
                openSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSearch() {
        Intent intent = new Intent(getApplicationContext(),UserActivity.class);
        startActivity(intent);
    }


}
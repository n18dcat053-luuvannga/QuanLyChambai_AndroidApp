package com.n18dcat077.test_database.GiaoVien;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.github.mikephil.charting.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.n18dcat077.test_database.DatabaseQLCB;
import com.n18dcat077.test_database.Hoadon.DsGiaoVien;
import com.n18dcat077.test_database.HomeActivity;
import com.n18dcat077.test_database.MonHoc.QuanLiMonHoc;
import com.n18dcat077.test_database.PhieuChamBai.PhieuChamBaiActivity;
import com.n18dcat077.test_database.R;
import com.n18dcat077.test_database.TTPCB.QuanLiTTPCB;

import java.util.ArrayList;

public class ManagerGiaoVienActivity extends AppCompatActivity implements GiaoVienAdapter.GiaoViencClickListener {

    DatabaseQLCB dbGiaoVien;
    ListView listView;
    GiaoVienAdapter adapter;
    ArrayList<GiaoVien> data;
    Button btnAdd;
    TextView magv,hoten,sdt,header;
    ArrayList<String> activities = new ArrayList<>();
    BottomNavigationView botNav;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_giao_vien);
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

    private void initActivity(){
        activities.add("Quản lí giáo viên");
        activities.add("Quản lí môn học");
        activities.add("Quản lí phiếu chấm bài");
    }

    public void setControl(){
        magv = findViewById(R.id.magvShow);
        hoten = findViewById(R.id.hotenShow);
        sdt = findViewById(R.id.sdtShow);
        //header = findViewById(R.id.showHeader);
        listView = findViewById(R.id.list_listview);
        btnAdd = findViewById(R.id.btnThem);
        botNav = findViewById(R.id.botNavGV);

    }
    public void setEvent(){
        botNav.setSelectedItemId(R.id.QLGV);
        botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.QLPCBz:
                        Intent intent = new Intent(getApplicationContext(), PhieuChamBaiActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right_to_left, R.anim.hold);
                        return true;
                    case R.id.QLMH:
                        Intent intent2 = new Intent(getApplicationContext(), QuanLiMonHoc.class);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.right_to_left, R.anim.hold);
                        return true;
                    case R.id.TTPCB:
                        Intent intent1 = new Intent(getApplicationContext(), QuanLiTTPCB.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.right_to_left, R.anim.hold);
                        return true;
                    case R.id.XHD:
                        Intent intent3 = new Intent(getApplicationContext(), DsGiaoVien.class);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.right_to_left, R.anim.hold);
                        return true;
                }
                return false;
            }
        });

        dbGiaoVien = new DatabaseQLCB(this);
        /*GiaoVien gv1 = new GiaoVien("GV02","Thanh Tu","0374576967");
        dbGiaoVien.insertGV(gv1);*/
        data = dbGiaoVien.getListGiaoVien();
        adapter = new GiaoVienAdapter(this, R.layout.giaovien_item,data,this::select);
        listView.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddGiaoVienActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.hold);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GiaoVien gv = data.get(i);
                Intent intent = new Intent(getApplicationContext(),EditGiaoVienActivity.class);
                intent.putExtra("MAGV",gv.getId());
                intent.putExtra("HOTEN",gv.getHoTen());
                intent.putExtra("SDT",gv.getSdt());
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

    public static void menuIconColor(MenuItem menuItem, int color) {
        Drawable drawable = menuItem.getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        // change color for icon 0
        Drawable yourdrawable = menu.getItem(0).getIcon(); // change 0 with 1,2 ...
        yourdrawable.mutate();
        yourdrawable.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);

        //
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
    // thu thanh serch view trc khi thoat ung dung
    @Override
    public void onBackPressed() {
        if(!searchView.isIconified()){
            searchView.setIconified(true);

            return;
        }
        super.onBackPressed();
    }

    @Override
    public void select(GiaoVien giaoVien) {
        Intent intent = new Intent(getApplicationContext(),EditGiaoVienActivity.class);
        intent.putExtra("MAGV",giaoVien.getId());
        intent.putExtra("HOTEN",giaoVien.getHoTen());
        intent.putExtra("SDT",giaoVien.getSdt());
        startActivity(intent);
    }
}
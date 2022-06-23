package com.n18dcat077.test_database.PhieuChamBai;

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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.n18dcat077.test_database.DatabaseQLCB;
import com.n18dcat077.test_database.GiaoVien.ManagerGiaoVienActivity;
import com.n18dcat077.test_database.Hoadon.DsGiaoVien;
import com.n18dcat077.test_database.HomeActivity;
import com.n18dcat077.test_database.MonHoc.QuanLiMonHoc;
import com.n18dcat077.test_database.R;
import com.n18dcat077.test_database.TTPCB.QuanLiTTPCB;

import java.util.ArrayList;

public class PhieuChamBaiActivity extends AppCompatActivity implements PhieuChamBaiAdapter.PCBListener{

    DatabaseQLCB db;
    ListView listView;
    PhieuChamBaiAdapter adapterPCB;
    ArrayList<PhieuChamBai> data;
    Button btnAddPCB;
    private SearchView searchView;
    TextView SoPhieu, NgayGiao, MaGV;
    ArrayList<String> activities = new ArrayList<>();
    BottomNavigationView botNav;


    private void initActivity(){
        activities.add("Quản lí giáo viên");
        activities.add("Quản lí môn học");
        activities.add("Quản lí phiếu chấm bài");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_cham_bai);
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

    private void setControl() {
        SoPhieu = findViewById(R.id.txtSoPhieu);
        NgayGiao = findViewById(R.id.txtNgayGiao);
        MaGV = findViewById(R.id.txtMaGV);
        listView = findViewById(R.id.lvPCB);
        btnAddPCB = findViewById(R.id.btnAddPCB);
        botNav = findViewById(R.id.botNavPCB);
    }

    private void setEvent() {
        db = new DatabaseQLCB(this);
        botNav.setSelectedItemId(R.id.QLPCBz);
        botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.QLGV:
                        Intent intent = new Intent(getApplicationContext(), ManagerGiaoVienActivity.class);
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

        data = db.getListPCB();
        adapterPCB = new PhieuChamBaiAdapter(this, R.layout.phieuchambai_item,data,this::select);
        listView.setAdapter(adapterPCB);
        btnAddPCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPhieuChamBaiActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.hold);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PhieuChamBai pcb = data.get(i);
                Intent intent = new Intent(getApplicationContext(), EditPhieuChamBaiActivity.class);
                intent.putExtra("SOPHIEU",pcb.getSoPhieu());
                intent.putExtra("NGAYGIAO",pcb.getNgayGiao());
                intent.putExtra("MAGV",pcb.getMaGV());
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterPCB.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapterPCB.getFilter().filter(newText);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(myIntent);
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_bottom);
        return true;
    }

    @Override
    public void select(PhieuChamBai PCB) {
        Intent intent = new Intent(getApplicationContext(), EditPhieuChamBaiActivity.class);
        intent.putExtra("SOPHIEU",PCB.getSoPhieu());
        intent.putExtra("NGAYGIAO",PCB.getNgayGiao());
        intent.putExtra("MAGV",PCB.getMaGV());
        startActivity(intent);
    }
}
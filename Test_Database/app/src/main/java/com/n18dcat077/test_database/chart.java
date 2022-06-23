package com.n18dcat077.test_database;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.n18dcat077.test_database.GiaoVien.GiaoVien;
import com.n18dcat077.test_database.PhieuChamBai.PhieuChamBai;

import java.util.ArrayList;

public class chart extends AppCompatActivity {
    PieChart pieChart;
    DatabaseQLCB db;
    ArrayList<PhieuChamBai> data;
    ArrayList<PhieuChamBai> data2;
    GiaoVien gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
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
        pieChart = findViewById(R.id.pieChart);
    }

    private void setEvent() {
        setUpPieChart();
        loadPieChart();
    }


    private void setUpPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setCenterText("BẢNG CHI TIẾT CHẤM BÀI");
        pieChart.setCenterTextSize(30);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setTextSize(15f);
        l.setEnabled(true);
    }

    private void loadPieChart() {
        db = new DatabaseQLCB(this);
        data = db.getListPCB();
        data2 = db.countGV();

        int totalGV = 0;
        for(int i=0;i<data2.size();i++){
            totalGV += Integer.parseInt(data2.get(i).getCount());
        }
        ArrayList<PieEntry> entries = new ArrayList<>();
        for(int i = 0;i<data2.size();i++) {
//            gv = db.getEmployeeName(data2.get(i).getMaGV());
            String gv2 = db.getResult(data2.get(i).getMaGV());
            float percent = Integer.parseInt(data2.get(i).getCount()) * 100 / totalGV;
            PieEntry pieEntry = new PieEntry(percent, gv2);
            entries.add(pieEntry);
        }
        ArrayList<Integer> colors = new ArrayList<>();
        for(int color : ColorTemplate.MATERIAL_COLORS){
            colors.add(color);
        }
        for(int color : ColorTemplate.VORDIPLOM_COLORS){
            colors.add(color);
        }

        PieDataSet pieDataSet = new PieDataSet(entries, "Danh sách giáo viên");
        pieDataSet.setColors(colors);
        pieChart.setEntryLabelTextSize(15f);
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(15f);
        pieData.setValueTextColor(Color.BLACK);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), gotoChart.class);
        startActivity(myIntent);
        overridePendingTransition(R.anim.right_to_left, R.anim.fade_out2);
        return true;
    }
}
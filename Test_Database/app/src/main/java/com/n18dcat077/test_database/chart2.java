package com.n18dcat077.test_database;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.n18dcat077.test_database.MonHoc.MonHoc;
import com.n18dcat077.test_database.PhieuChamBai.PhieuChamBai;
import com.n18dcat077.test_database.TTPCB.PCB;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class chart2 extends AppCompatActivity {
    BarChart barChart;
    DatabaseQLCB db;
    ArrayList<PCB> data;
    ArrayList<PCB> data2;
    MonHoc mh;
    TextView txt;
    PhieuChamBai pcb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart2);
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
        barChart = findViewById(R.id.chart2);
        txt = findViewById(R.id.txtChart2);
    }

    private void setEvent() {
        setupBarChart();
        loadBarChart();
    }

    private void setupBarChart() {
        barChart.setDrawBarShadow(true);
        barChart.setDrawValueAboveBar(true);
        barChart.getDescription().setEnabled(false);
    }
    private ArrayList<BarEntry> dataValues() {
        ArrayList<BarEntry> data2 = new ArrayList<BarEntry>();
        db = new DatabaseQLCB(this);
        data = db.getListTTPCB2();
        int z = 0;
        int k =0, l = 0;
            for(int i = 0; i < data.size(); i++) {
                z = data.get(i).getTongBai();
                data2.add(new BarEntry(i, z));
            }
        return data2;
    }

    public List<String> getAreaCount() {
        db = new DatabaseQLCB(this);
        data2 = db.getListTTPCB2();

        List<String> label = new ArrayList<>();
        for (int i = 0; i < data2.size(); i++)
            label.add(data2.get(i).getMaMH());
        label = label.stream().distinct().collect(Collectors.toList());
        return label;
    }
    int[] ColorArray = new int[]{Color.BLUE, Color.RED, Color.GREEN, Color.GRAY, Color.BLACK};
    private void loadBarChart() {
        db = new DatabaseQLCB(this);
        data = db.getListTTPCB2();

        BarDataSet barDataSet = new BarDataSet(dataValues(), "Danh sách số bài");
        barDataSet.setColors(ColorArray);
        Legend l = barChart.getLegend();
        LegendEntry[] legendEntries = new LegendEntry[data.size()];
        for(int i = 0; i<legendEntries.length; i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = ColorArray[i];
            entry.label = getAreaCount().get(i);
            legendEntries[i] = entry;
        }
        l.setTextSize(14f);
        l.setCustom(legendEntries);

        BarData barData = new BarData();
        barData.setBarWidth(0.5f);
        barData.addDataSet(barDataSet);
        barDataSet.setValueTextSize(14f);

        barChart.setData(barData);
        barChart.invalidate();
        barChart.setScrollBarSize(14);
        barChart.setDrawBarShadow(false);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        barChart.setNoDataText("Test");

        XAxis xAxis = barChart.getXAxis();
        YAxis yAxisL = barChart.getAxisLeft();
        YAxis yAxisR = barChart.getAxisRight();
        yAxisR.setDrawLabels(false);
        yAxisL.setTextSize(14f);
        xAxis.setDrawGridLines(false);
        barChart.getXAxis().setDrawLabels(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), gotoChart.class);
        startActivity(myIntent);
        overridePendingTransition(R.anim.right_to_left, R.anim.fade_out2);
        return true;
    }
}
package com.n18dcat077.test_database.Hoadon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.n18dcat077.test_database.DatabaseQLCB;
import com.n18dcat077.test_database.MonHoc.MonHoc;
import com.n18dcat077.test_database.PhieuChamBai.PhieuChamBai;
import com.n18dcat077.test_database.PhieuChamBai.PhieuChamBaiAdapter;
import com.n18dcat077.test_database.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PhieuThanhToan extends AppCompatActivity {
    DatabaseQLCB db;
    ListView listView;
    ArrayList<MonHoc> data;
    ArrayList<PhieuChamBai> p;
    Button btnback, btnxuat;
    TextView SoPhieuHD, NgayGiaoHD, Thanhtien;
    TextView magv,hoten,sdt, tenmh, sobai, chiphi, mamh;
    PhieuChamBaiAdapter adapterPCB;
    AdapterPhieuThanhToan adapter;
    ArrayList<THONGTINCHAMBAI> listThongTin;
    String id;
    String path = "";
    private LinearLayout linear;
    private Bitmap bitmap;
    private String stringFilePath;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_thanh_toan);
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

    private Bitmap LoadBitmap(View v, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    private void createPdf() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

//        write the document content
//        String targetPdf = "page.pdf";
//        File filePath;
//        filePath = new File(targetPdf);
        try {
            stringFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/Hoadon_"+path+".pdf";
            file = new File(stringFilePath);
            document.writeTo(new FileOutputStream(file));
            //Toast.makeText(this, "Something wrong: " + path, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }////////////////////

        // close the document
        document.close();
        Toast.makeText(this, "successfully pdf created", Toast.LENGTH_SHORT).show();
    }

    public void setControl(){

        magv = findViewById(R.id.magvhd);
        hoten = findViewById(R.id.hotengvhd);
        sdt = findViewById(R.id.sdtgvhd);
        listView = findViewById(R.id.lvHD);
        SoPhieuHD = findViewById(R.id.soPhieuHD);
        NgayGiaoHD = findViewById(R.id.ngayGiaoHD);
        tenmh = findViewById(R.id.monhoc);
        mamh = findViewById(R.id.mamh);
        sobai = findViewById(R.id.sobaiHD);
        chiphi = findViewById(R.id.chiphiHD);
        Thanhtien = findViewById(R.id.thanhtien);
        btnback = findViewById(R.id.btnback);
        linear = findViewById(R.id.lineard);
        btnxuat = findViewById(R.id.btnin);

        Intent intent = getIntent();
        magv.setText(intent.getStringExtra("MAGV"));
        hoten.setText(intent.getStringExtra("HOTENGV"));
        sdt.setText(intent.getStringExtra("SODT"));
        id = magv.getText().toString();
//        Toast.makeText(PhieuThanhToan.this,id,Toast.LENGTH_SHORT).show();

    }
    public void setEvent() {

//        data = db.getPCB1GV(magv.getText().toString());
//        adapterPCB = new PhieuChamBaiAdapter(this,R.layout.phieuthanhtoan,p);
//        listView.setAdapter(adapterPCB);
//        GiaoVien gv = db.getGiaoVienById(id);
//         p = db.getPCB1GV(id);
//        PhieuthanhtoanObject ph = new PhieuthanhtoanObject();
//        ph.setList(p);
//        ph.setGv(gv);
        db = new DatabaseQLCB(this);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        listThongTin = db.getPCB1GV1(id);
        path = id;
        adapter = new AdapterPhieuThanhToan(this, R.layout.phieuthanhtoan,listThongTin);
        listView.setAdapter(adapter);
        long total =0;
        for (int i=0; i<listThongTin.size(); i++){
            total+= Long.valueOf(listThongTin.get(i).getThanhtien());
        }
        Thanhtien.setText(formatter.format(total));
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DsGiaoVien.class);
                startActivity(intent);
            }
        });
        btnxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("size", "" + linear.getWidth() + " " + linear.getWidth());
                bitmap = LoadBitmap(linear, linear.getWidth(), linear.getHeight());
                createPdf();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), DsGiaoVien.class);
        startActivity(myIntent);
        return true;
    }

}

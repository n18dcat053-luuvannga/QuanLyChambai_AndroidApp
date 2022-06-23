package com.n18dcat077.test_database.MonHoc;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.n18dcat077.test_database.DatabaseQLCB;
import com.n18dcat077.test_database.R;
import com.n18dcat077.test_database.validate;

import java.util.ArrayList;

public class ThemMonHoc extends AppCompatActivity {

    TextInputLayout txtmaMH,txttenMH, txtChiphi;
    Button btnThem, btnQuaylai;
    DatabaseQLCB dbMonHoc;
    Button btnClose, btnCloseE;
    TextView txtSuccess, txtError;
    ImageView imgSuccess, imgError;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_mon_hoc);
        setControl();
        setEvent();
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
    }

    public void setControl(){
        txtmaMH = findViewById(R.id.txtmamonhoc);
        txttenMH = findViewById(R.id.txttenmon);
        txtChiphi = findViewById(R.id.txtchiphi);
        btnThem = findViewById(R.id.btnAdd);
        btnQuaylai = findViewById(R.id.btnBack);
        dialog = new Dialog(this);
        btnClose = dialog.findViewById(R.id.btnClose);
        imgSuccess = dialog.findViewById(R.id.imageSuccess);
        txtSuccess = (TextView) dialog.findViewById(R.id.txtSuccess);

        btnCloseE = dialog.findViewById(R.id.btnCloseE);
        imgError = dialog.findViewById(R.id.imageError);
        txtError = (TextView) dialog.findViewById(R.id.txtError);
    }
    public void setEvent(){
        dbMonHoc= new DatabaseQLCB(this);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList <MonHoc> ds = new ArrayList<>(dbMonHoc.danhsachID());
                String inputmaMH = txtmaMH.getEditText().getText().toString().trim().toUpperCase();
                String inputtenMH=txttenMH.getEditText().getText().toString().trim();
                String inputChiPhi=txtChiphi.getEditText().getText().toString().trim();
                if(inputmaMH.equals("") || inputtenMH.equals("") || inputChiPhi.equals("") ){
                    showDialogError("không để thông tin trống");
                    btnCloseE.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });
                    return;
                }
                else if(validate.validateLetters(inputtenMH)==false){
                    showDialogError("tên môn học không hợp lệ");
                    btnCloseE.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });
                    return;
                }
                else if(validate.isNumber(inputChiPhi)==false){
                    showDialogError("không nhập chi phí bằng chữ và chi phí không chứa khoản cách!");
                    btnCloseE.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });
                    return;
                }
                else if(validate.KiemTraChiPhi(inputChiPhi)==false){
                            showDialogError("Chi Phí không hợp lệ!, chi phí thấp nhất là 10000 va lớn nhất la 50000");
                            btnCloseE.setOnClickListener(new View.OnClickListener() {
                                @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });                    return;
                }
                for(MonHoc mh : ds ){
                    if(inputmaMH.equals(mh.getMaMH())){
                        showDialogError("mã môn học "+inputmaMH+" đã tồn tại!");
                        btnCloseE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                        return;}
                }
                MonHoc monHoc=getMonHoc();
                dbMonHoc.ThemDL(monHoc);
                showDialogSuccess("Thêm môn học thành công!");
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), QuanLiMonHoc.class);
                        startActivity(intent);
                    }
                });
            }
            private MonHoc getMonHoc() {
                MonHoc monHoc = new MonHoc();
                monHoc.setMaMH(txtmaMH.getEditText().getText().toString().trim().toUpperCase());
                monHoc.setTenMH(validate.chuanHoa(txttenMH.getEditText().getText().toString().trim().toUpperCase()));
                monHoc.setChiPhi(txtChiphi.getEditText().getText().toString().trim());
                return monHoc;
            }
        });
        btnQuaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),QuanLiMonHoc.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), QuanLiMonHoc.class);
        startActivity(myIntent);
        return true;
    }
    private void showDialogSuccess(String success) {
        dialog.setContentView(R.layout.custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        imgSuccess = dialog.findViewById(R.id.imageSuccess);
        btnClose = dialog.findViewById(R.id.btnClose);
        txtSuccess = dialog.findViewById(R.id.txtSuccess);
        txtSuccess.setText(success);
        dialog.show();
    }

    private void showDialogError(String error) {
        dialog.setContentView(R.layout.error_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        imgError = dialog.findViewById(R.id.imageError);
        btnCloseE = dialog.findViewById(R.id.btnCloseE);
        txtError = dialog.findViewById(R.id.txtError);
        txtError.setText(error);
        dialog.show();
    }
}
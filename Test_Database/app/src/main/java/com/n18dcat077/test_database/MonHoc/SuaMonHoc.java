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
import com.n18dcat077.test_database.TTPCB.PCB;
import com.n18dcat077.test_database.validate;

import java.util.ArrayList;

public class SuaMonHoc extends AppCompatActivity {

    TextInputLayout txtmaMH,txttenMH,txtChiPhi;
    Button btnSua, btnXoa, btnBack;
    DatabaseQLCB dbMonHoc;
    MonHoc monHoc;
    Button btnClose, btnCloseE, btnYes, btnNo;
    TextView txtSuccess, txtError, txtWarning;
    ImageView imgSuccess, imgError, imgWarning;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_mon_hoc);
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
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            monHoc = (MonHoc) intent.getSerializableExtra("data");
            txtmaMH.getEditText().setText(monHoc.getMaMH());
            txttenMH.getEditText().setText(monHoc.getTenMH());
            txtChiPhi.getEditText().setText(monHoc.getChiPhi());

        }
    }
    public void setControl(){
        txtmaMH = findViewById(R.id.editmaMH);
        txttenMH = findViewById(R.id.edittenMH);
        txtChiPhi = findViewById(R.id.editChiPhi);
        btnSua = findViewById(R.id.btnEdit);
        btnXoa = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBack);
        dialog = new Dialog(this);
        btnClose = dialog.findViewById(R.id.btnClose);
        imgSuccess = dialog.findViewById(R.id.imageSuccess);
        txtSuccess = (TextView) dialog.findViewById(R.id.txtSuccess);

        btnCloseE = dialog.findViewById(R.id.btnCloseE);
        imgError = dialog.findViewById(R.id.imageError);
        txtError = (TextView) dialog.findViewById(R.id.txtError);

        Intent intent = getIntent();
        txtmaMH.getEditText().setText(intent.getStringExtra("MAMH"));
        txttenMH.getEditText().setText(intent.getStringExtra("TENMH"));
        txtChiPhi.getEditText().setText(intent.getStringExtra("CHIPHI"));
        txtWarning = dialog.findViewById(R.id.txtWarning);
        imgWarning = dialog.findViewById(R.id.imgWarning);
        btnYes = dialog.findViewById(R.id.btnYes);
        btnNo = dialog.findViewById(R.id.btnNo);
    }

    private void setEvent() {
        dbMonHoc = new DatabaseQLCB(this);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputChiPhi=txtChiPhi.getEditText().getText().toString().trim();
                String inputmaMH = txtmaMH.getEditText().getText().toString().trim();
                String inputtenMH = txttenMH.getEditText().getText().toString().trim();
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
                    showDialogError("Chi Phí không hợp lệ!, chi phí thấp nhất là 10000");
                    btnCloseE.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });                    return;
                }
                dbMonHoc.SuaDL(getMonHoc());
                showDialogSuccess("Sửa môn học thành công!");
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), QuanLiMonHoc.class);
                        startActivity(intent);
                    }
                });
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWarningDialog("Bạn có chắc chắn muốn xoá không?");
                String inputmaMH=txtmaMH.getEditText().getText().toString();
                ArrayList<PCB> list=dbMonHoc.getListidMonHocInTTPCB(inputmaMH);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(list.size()>0){
                            showDialogError("Không được phép xóa");
                            btnCloseE.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.cancel();
                                }
                            });
                        }else {
                            if(dbMonHoc.XoaDL(getMonHoc()) == true){
                                showDialogSuccess("Xóa thành công");
                                btnClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getApplicationContext(), QuanLiMonHoc.class);
                                        startActivity(intent);
                                    }
                                });
                            }else{
                                showDialogError("Xóa thất bại");
                                btnCloseE.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.cancel();
                                    }
                                });
                            }

                        }
                    }
                });
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
//                if(list.size()>0){
//                    showDialogError("Không được phép xóa");
//                    btnCloseE.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dialog.cancel();
//                        }
//                    });
//                }else {
//                        if(dbMonHoc.XoaDL(getMonHoc()) == true){
//                            showDialogSuccess("Xóa thành công");
//                            btnClose.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    Intent intent = new Intent(getApplicationContext(), QuanLiMonHoc.class);
//                                    startActivity(intent);
//                                }
//                            });
//                        }else{
//                            showDialogError("Xóa thất bại");
//                            btnCloseE.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    dialog.cancel();
//                                }
//                            });
//                        }
//
//                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),QuanLiMonHoc.class);
                startActivity(intent);
            }
        });
    }
    private MonHoc getMonHoc() {
        MonHoc monHoc = new MonHoc();
        monHoc.setMaMH(txtmaMH.getEditText().getText().toString().trim().toUpperCase());
        monHoc.setTenMH(validate.chuanHoa(txttenMH.getEditText().getText().toString().trim().toUpperCase()));
        monHoc.setChiPhi(txtChiPhi.getEditText().getText().toString().trim());
        return monHoc;
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
    private void showWarningDialog(String warning) {
        dialog.setContentView(R.layout.ask_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        imgWarning = dialog.findViewById(R.id.imgWarning);
        btnYes = dialog.findViewById(R.id.btnYes);
        btnNo = dialog.findViewById(R.id.btnNo);
        txtWarning = dialog.findViewById(R.id.txtWarning);
        txtWarning.setText(warning);
        dialog.show();
    }

}
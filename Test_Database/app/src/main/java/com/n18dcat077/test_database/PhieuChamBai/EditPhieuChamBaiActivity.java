package com.n18dcat077.test_database.PhieuChamBai;

import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.n18dcat077.test_database.DatabaseQLCB;
import com.n18dcat077.test_database.R;
import com.n18dcat077.test_database.TTPCB.PCB;
import com.n18dcat077.test_database.validate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditPhieuChamBaiActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextInputLayout editSoPhieu, editNgayGiao, editMaGV;
    Button btnEdit, btnDelete, btnBack;
    DatabaseQLCB db;
    Button btnClose, btnCloseE, btnYes, btnNo;
    TextView txtSuccess, txtError, txtWarning;
    ImageView imgSuccess, imgError, imgWarning;
    Dialog dialog;
    Spinner spinner;
    Spinner drop;
    String zz = "";
    PhieuChamBai phieuChamBai;
    int test;
    ArrayList<PhieuChamBai> pcb = new ArrayList<>();
    ArrayList<String> activities = new ArrayList<>();

    String z = "Sửa phiếu thành công!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phieu_cham_bai);
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
            test = Integer.parseInt(intent.getStringExtra("SOPHIEU"));
            editSoPhieu.getEditText().setText(intent.getStringExtra("SOPHIEU"));
            editNgayGiao.getEditText().setText(intent.getStringExtra("NGAYGIAO"));
            editMaGV.getEditText().setText(intent.getStringExtra("MAGV"));

        }
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.YEAR));
        datePickerDialog.getDatePicker().getTouchables().get(0).performClick();
        datePickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        month += 1;
        String date = day + "/" + month + "/" + year;
        editNgayGiao.getEditText().setText(date);
    }

    private boolean checkMaGV(String MaGV) {
        db = new DatabaseQLCB(this);
        int flag = 0;
        List<String> id = db.getIdGiaoVien();
        for (int i = 0; i < id.size(); i++) {
            if (MaGV.equals(id.get(i))) {
                flag += 1;
            }
        }
        if (flag > 0) return true;
        return false;
    }

    public void setControl() {
        editSoPhieu = findViewById(R.id.editSoPhieu);
        editNgayGiao = findViewById(R.id.editNgayGiao);
        editMaGV = findViewById(R.id.editMaGV);
        btnEdit = findViewById(R.id.btnEditPCB);
        btnDelete = findViewById(R.id.btnDeletePCB);
        btnBack = findViewById(R.id.btnBackPCB);
       // spinner = findViewById(R.id.spinnerEditPCB);
        drop = findViewById(R.id.imgDropE);
        Intent intent = getIntent();
        test = Integer.parseInt(intent.getStringExtra("SOPHIEU"));
        editSoPhieu.getEditText().setText(intent.getStringExtra("SOPHIEU"));
        editNgayGiao.getEditText().setText(intent.getStringExtra("NGAYGIAO"));
        editMaGV.getEditText().setText(intent.getStringExtra("MAGV"));
        zz = intent.getStringExtra("MAGV");
        dialog = new Dialog(this);
        btnClose = dialog.findViewById(R.id.btnClose);
        imgSuccess = dialog.findViewById(R.id.imageSuccess);
        txtSuccess = (TextView) dialog.findViewById(R.id.txtSuccess);

        btnCloseE = dialog.findViewById(R.id.btnCloseE);
        imgError = dialog.findViewById(R.id.imageError);
        txtError = (TextView) dialog.findViewById(R.id.txtError);

        txtWarning = dialog.findViewById(R.id.txtWarning);
        imgWarning = dialog.findViewById(R.id.imgWarning);
        btnYes = dialog.findViewById(R.id.btnYes);
        btnNo = dialog.findViewById(R.id.btnNo);
    }

    public void setEvent() {
        db = new DatabaseQLCB(this);
//        ArrayList<String> data = (ArrayList<String>) db.getIDPCB();
//        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
//        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
//        spinner.setAdapter(adapter);
//        spinner.setSelection(test - 1);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                pcb = db.getListPCB();
//                PhieuChamBai pcb2 = pcb.get(i);
//                editSoPhieu.getEditText().setText(pcb2.getSoPhieu());
//                editNgayGiao.getEditText().setText(pcb2.getNgayGiao());
//                editMaGV.getEditText().setText(pcb2.getMaGV());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                Toast.makeText(EditPhieuChamBaiActivity.this, "Bạn chưa chọn gì cả", Toast.LENGTH_LONG).show();
//            }
//        });

        ArrayList<String> data2 = (ArrayList<String>) db.getIdGiaoVien();
        ArrayAdapter adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data2);
        adapter2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        drop.setAdapter(adapter2);
        for(int i = 0; i < data2.size(); i++) {
            if(data2.get(i).equals(zz)) {
                drop.setSelection(i);
            }
        }
        drop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                editMaGV.getEditText().setText(drop.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(EditPhieuChamBaiActivity.this, "Bạn chưa chọn gì cả", Toast.LENGTH_LONG).show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String SoPhieu = editSoPhieu.getEditText().getText().toString();
                String NgayGiao = editNgayGiao.getEditText().getText().toString();
                String MaGV = editMaGV.getEditText().getText().toString();

                if (validate.validateDate(NgayGiao) == true && checkMaGV(MaGV) == true) {
                    PhieuChamBai pcb = new PhieuChamBai(SoPhieu, NgayGiao, MaGV);
                    if (db.updatePCB(pcb) == true && validate.isNumber(SoPhieu)==true) {
                        showDialogSuccess("Sửa phiếu thành công!");
                        btnClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), PhieuChamBaiActivity.class);
                                startActivity(intent);
                            }
                        });
                    } else {
//                        Toast.makeText(EditPhieuChamBaiActivity.this, "Sửa phiếu thất bại", Toast.LENGTH_SHORT).show();
                        showDialogError("Sửa phiếu thất bại!");
                        btnCloseE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                    }
                } else {
                    if(validate.isNumber(SoPhieu)==false) {
                        showDialogError("Số Phieu không chứa chữ");
                        btnCloseE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                    }
                    else if (validate.validateDate(NgayGiao) == false){
                        showDialogError("Ngày giao không hợp lệ!");
                        btnCloseE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                    }
//                        Toast.makeText(EditPhieuChamBaiActivity.this, "Ngày giao không hợp lệ!", Toast.LENGTH_LONG).show();

                    else if (checkMaGV(MaGV) == false) {
                        //Toast.makeText(EditPhieuChamBaiActivity.this, "Mã giáo viên sai hoặc không tồn tại!", Toast.LENGTH_LONG).show();
                        showDialogError("Mã giáo viên sai hoặc không tồn tại!");
                        btnCloseE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                    }
                    else if(SoPhieu.equals("")) {
                        showDialogError("Số phiếu trống!");
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
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWarningDialog("Bạn có chắc chắn muốn xoá?");
                String SoPhieu = editSoPhieu.getEditText().getText().toString();
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<PCB> list=db.getListidMonHocInTTPCB(SoPhieu);
                        if(list.size()>0){
                            showDialogError("Xoá phiếu thất bại!");
                            btnCloseE.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.cancel();
                                }
                            });
                        }else{
                            if (db.deletePCB(SoPhieu) == true) {
                                showDialogSuccess("Xoá phiếu thành công!");
                                btnClose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(getApplicationContext(), PhieuChamBaiActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                showDialogError("Xoá phiếu thất bại!");
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
//                ArrayList<PCB> list=db.getListidMonHocInTTPCB(SoPhieu);
//                if(list.size()>0){
//                    showDialogError("Xoá phiếu thất bại!");
//                    btnCloseE.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            dialog.cancel();
//                        }
//                    });
//                }else{
//                    if (db.deletePCB(SoPhieu) == true) {
//                        showDialogSuccess("Xoá phiếu thành công!");
//                        btnClose.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Intent intent = new Intent(getApplicationContext(), PhieuChamBaiActivity.class);
//                                startActivity(intent);
//                            }
//                        });
//                    } else {
//                        showDialogError("Xoá phiếu thất bại!");
//                        btnCloseE.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                dialog.cancel();
//                            }
//                        });
//                    }
//                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PhieuChamBaiActivity.class);
                startActivity(intent);
            }
        });
        editNgayGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), PhieuChamBaiActivity.class);
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
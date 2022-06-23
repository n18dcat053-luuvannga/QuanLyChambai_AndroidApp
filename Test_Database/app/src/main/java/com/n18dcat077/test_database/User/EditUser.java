package com.n18dcat077.test_database.User;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.n18dcat077.test_database.DatabaseQLCB;
import com.n18dcat077.test_database.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditUser extends AppCompatActivity {

    TextInputLayout txtusername,txtpassword;
    Button btnSua,btnXoa, btnBack;
    DatabaseQLCB db;
    Button btnClose, btnCloseE;
    TextView txtSuccess, txtError;
    ImageView imgSuccess, imgError;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
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

    public void setControl(){
        txtusername = findViewById(R.id.editusername);
        txtpassword = findViewById(R.id.editpassword);
        btnSua = findViewById(R.id.btnEditUser);
        btnBack = findViewById(R.id.btnBackEditUser);
        btnXoa = findViewById(R.id.btnDeleteUser);
        dialog = new Dialog(this);
        btnClose = dialog.findViewById(R.id.btnClose);
        imgSuccess = dialog.findViewById(R.id.imageSuccess);
        txtSuccess = (TextView) dialog.findViewById(R.id.txtSuccess);

        btnCloseE = dialog.findViewById(R.id.btnCloseE);
        imgError = dialog.findViewById(R.id.imageError);
        txtError = (TextView) dialog.findViewById(R.id.txtError);

        Intent intent = getIntent();
        Toast.makeText(EditUser.this, intent.getStringExtra("USERNAME"), Toast.LENGTH_SHORT).show();
        txtusername.getEditText().setText(intent.getStringExtra("USERNAME"));
        txtpassword.getEditText().setText(intent.getStringExtra("PASSWORD"));


        dialog = new Dialog(this);
        btnClose = dialog.findViewById(R.id.btnClose);
        imgSuccess = dialog.findViewById(R.id.imageSuccess);
        txtSuccess = (TextView) dialog.findViewById(R.id.txtSuccess);

        btnCloseE = dialog.findViewById(R.id.btnCloseE);
        imgError = dialog.findViewById(R.id.imageError);
        txtError = (TextView) dialog.findViewById(R.id.txtError);
    }

    public void setEvent(){
        db = new DatabaseQLCB(this);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtusername.getEditText().getText().toString();
                String password = txtpassword.getEditText().getText().toString();

                User us = new User(username, password);
                if(db.updateUser(us) == true) {
                        Toast.makeText(EditUser.this, "Thêm phiếu thành công", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), PhieuChamBaiActivity.class);
//                        startActivity(intent);
                    showDialogSuccess("Sửa nhân viên thành công!");
                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                            startActivity(intent);
                        }
                    });

                } else {
                    showDialogError("Sửa nhân viên thất bại!");
                    btnCloseE.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });
                }

            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username  = txtusername.getEditText().getText().toString();

                    if(db.deleteUser(username) == true){
                        showDialogSuccess("Xóa thành công!");
                        btnClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
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
//                        Intent intent = new Intent(getApplicationContext(),ManagerGiaoVienActivity.class);
//                        startActivity(intent);
                    }

                }

        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),UserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), UserActivity.class);
        startActivity(myIntent);
        return true;
    }

    public static boolean validateMaGiaoVien(String magv) {
        Pattern pattern = Pattern.compile("(GV)+([0-9]+$)\\b");
        Matcher matcher  = pattern.matcher(magv);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

//    public static boolean validateLetters(String txt) {
//        Pattern pattern = Pattern.compile("^[a-zA-Z]+[\\-'\\s]?[a-zA-Z ]+$",Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(txt);
//        return matcher.find();
//    }
//
//    public static boolean validatephoneNumber(String sdt){
//        Pattern pattern = Pattern.compile("(84|0[3|5|7|8|9])+([0-9]{8})\\b");
//        Matcher matcher  = pattern.matcher(sdt);
//        if(matcher.matches()){
//            return true;
//        }
//        return false;
//    }
//
//    public static String chuanHoa(String s) {
//        s = s.trim();
//        s = s.replaceAll("\\s+", " ");
//        return s;
//    }
//    public static String chuanHoaDanhTuRieng(String str) {
//        str = chuanHoa(str);
//        String temp[] = str.split(" ");
//        str = "";
//        for (int i = 0; i < temp.length; i++) {
//            str += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1).toLowerCase();
//            if (i < temp.length - 1) {
//                str += " ";
//            }
//        }
//        return str;
//    }

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
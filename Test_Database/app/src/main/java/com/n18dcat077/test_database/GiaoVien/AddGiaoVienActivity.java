package com.n18dcat077.test_database.GiaoVien;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.n18dcat077.test_database.DatabaseQLCB;
import com.n18dcat077.test_database.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddGiaoVienActivity extends AppCompatActivity {

    private static final int SELECT_FILE = 1;
    private static final int PERMISSION_CODE = 1001;
    TextInputLayout txtMagv,txtHoten, txtSdt ,txtEmail;
    Button btnThem, btnBack;
    DatabaseQLCB dbGiaoVien;
    Button btnClose, btnCloseE;
    TextView txtSuccess, txtError;
    ImageView imgSuccess, imgError;
    Dialog dialog;
    ImageView imageGV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_giao_vien);
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
        txtMagv = findViewById(R.id.textMagv);
        txtHoten = findViewById(R.id.textHoten);
        txtEmail = findViewById(R.id.textEmail);
        txtSdt = findViewById(R.id.textSdt);
        btnThem = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        imageGV = findViewById(R.id.imageGV);

        dialog = new Dialog(this);
        btnClose = dialog.findViewById(R.id.btnClose);
        imgSuccess = dialog.findViewById(R.id.imageSuccess);
        txtSuccess = (TextView) dialog.findViewById(R.id.txtSuccess);

        btnCloseE = dialog.findViewById(R.id.btnCloseE);
        imgError = dialog.findViewById(R.id.imageError);
        txtError = (TextView) dialog.findViewById(R.id.txtError);
    }

    public void setEvent(){
        dbGiaoVien = new DatabaseQLCB(this);

        imageGV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check runtime permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                        // yêu cầu quyền từ người dùng
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
                    else
                        // quyền được cấp
                        pickImageFromGallery();
                } else{
                    //system os is less then marshmallow
                    pickImageFromGallery();
                }
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String magv = txtMagv.getEditText().getText().toString();
                String hoten = txtHoten.getEditText().getText().toString();
                String sdt = txtSdt.getEditText().getText().toString();
                String email = txtEmail.getEditText().getText().toString();
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageGV.getDrawable();

                if( validateEmail(email) == true && validateLetters(hoten) == true && validatephoneNumber(sdt) == true){
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    byte[] hinhanh = byteArrayOutputStream.toByteArray();
                    hoten = chuanHoaDanhTuRieng(hoten);
                    GiaoVien gv = new GiaoVien(magv,hoten,sdt,hinhanh,email);
                    if(dbGiaoVien.insertGV(gv) == true){
//                        Toast.makeText(AddGiaoVienActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(),ManagerGiaoVienActivity.class);
//                        startActivity(intent);
                        showDialogSuccess("Thêm giáo viên thành công!");
                        btnClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), ManagerGiaoVienActivity.class);
                                startActivity(intent);
                            }
                        });
                    }else{
                        //Toast.makeText(AddGiaoVienActivity.this,"Thêm thất bại",Toast.LENGTH_SHORT).show();
                        showDialogError("Thêm giáo viên thất bại!");
                        btnCloseE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                    }
                }else{
//                    if(validateMaGiaoVien(magv) == false){
//                        //Toast.makeText(AddGiaoVienActivity.this,"Họ tên không hợp lệ",Toast.LENGTH_SHORT).show();
//                        showDialogError("Mã giáo viên không hợp lệ");
//                        btnCloseE.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                dialog.cancel();
//                            }
//                        });
//                    }else
                        if(validateLetters(hoten) == false){
                        //Toast.makeText(AddGiaoVienActivity.this,"Họ tên không hợp lệ",Toast.LENGTH_SHORT).show();
                        showDialogError("Họ tên không hợp lệ");
                        btnCloseE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                    }else if(validatephoneNumber(sdt) == false){
                        //Toast.makeText(AddGiaoVienActivity.this,"Số điện thoại không hợp lệ",Toast.LENGTH_SHORT).show();
                        showDialogError("Số điện thoại không hợp lệ");
                        btnCloseE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                    }else if(validateEmail(email) == false){
                        showDialogError("Email không hợp lệ");
                        btnCloseE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                    }else{
//                        Toast.makeText(AddGiaoVienActivity.this,"Họ tên không hợp lệ",Toast.LENGTH_SHORT).show();
//                        Toast.makeText(AddGiaoVienActivity.this,"Số điện thoại không hợp lệ",Toast.LENGTH_SHORT).show();
                        showDialogError("Lỗi không xác định");
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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ManagerGiaoVienActivity.class);
                startActivity(intent);
            }
        });
    }

    //handle result of runtime permission
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    pickImageFromGallery();
                }
                else {
                    //permission was denied
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private void pickImageFromGallery () {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == SELECT_FILE && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageGV.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher  = pattern.matcher(email);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean validateMaGiaoVien(String magv) {
        Pattern pattern = Pattern.compile("(GV)+([0-9]+$)\\b");
        Matcher matcher  = pattern.matcher(magv);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean validateLetters(String txt) {
        String regx = "^[a-zA-Z]+[\\-'\\s]?[a-zA-Z ]+$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(txt);
        return matcher.find();
    }

    public static boolean validatephoneNumber(String sdt){
        Pattern pattern = Pattern.compile("(84|0[3|5|7|8|9])+([0-9]{8})\\b");
        Matcher matcher  = pattern.matcher(sdt);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    public static String chuanHoa(String s) {
        s = s.trim();
        s = s.replaceAll("\\s+", " ");
        return s;
    }
    public static String chuanHoaDanhTuRieng(String str) {
        str = chuanHoa(str);
        String temp[] = str.split(" ");
        str = "";
        for (int i = 0; i < temp.length; i++) {
            str += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1).toLowerCase();
            if (i < temp.length - 1) {
                str += " ";
            }
        }
        return str;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), ManagerGiaoVienActivity.class);
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
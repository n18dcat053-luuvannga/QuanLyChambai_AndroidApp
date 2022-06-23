package com.n18dcat077.test_database.Mail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.google.android.material.textfield.TextInputLayout;
import com.n18dcat077.test_database.DatabaseQLCB;
import com.n18dcat077.test_database.GiaoVien.GiaoVien;
import com.n18dcat077.test_database.GiaoVien.ManagerGiaoVienActivity;
import com.n18dcat077.test_database.HomeActivity;
import com.n18dcat077.test_database.LoginActivity;
import com.n18dcat077.test_database.R;
import com.n18dcat077.test_database.User.User;

public class VerifyActivity extends AppCompatActivity {

    TextInputLayout txtPass,txtRepass,txtCode;
    Button btnXacnhan;

    DatabaseQLCB dbGiaoVien;
    Button btnClose, btnCloseE;
    TextView txtSuccess, txtError;
    ImageView imgSuccess, imgError;
    Dialog dialog;
    String code,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
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
        txtPass = findViewById(R.id.verify_password);
        txtRepass = findViewById(R.id.verify_repassword);
        txtCode = findViewById(R.id.verify_code);
        btnXacnhan = findViewById(R.id.verify_xacnhan);

        Intent intent = getIntent();
        code = intent.getStringExtra("CODE");
        email = intent.getStringExtra("EMAIL");

        dialog = new Dialog(this);
        btnClose = dialog.findViewById(R.id.btnClose);
        imgSuccess = dialog.findViewById(R.id.imageSuccess);
        txtSuccess = (TextView) dialog.findViewById(R.id.txtSuccess);

        btnCloseE = dialog.findViewById(R.id.btnCloseE);
        imgError = dialog.findViewById(R.id.imageError);
        txtError = (TextView) dialog.findViewById(R.id.txtError);
        dbGiaoVien = new DatabaseQLCB(this);
    }

    public void setEvent(){
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = txtPass.getEditText().getText().toString().trim();
                String repass = txtRepass.getEditText().getText().toString().trim();
                String codeverify = txtCode.getEditText().getText().toString().trim();
                Toast.makeText(VerifyActivity.this,pass +" repass "+repass+" code  "+code+" codeverify "+codeverify,Toast.LENGTH_SHORT).show();
                if(pass.equals(repass) && codeverify.equals(code)){
                    GiaoVien giaoVien = dbGiaoVien.getGiaoVienByEmail(email);
                    User user = dbGiaoVien.getUserByUsername(giaoVien.getId());
                    user.setPassword(pass);
                    if(dbGiaoVien.updateUser(user) == true){
                        showDialogSuccess("Sửa thành công!");
                        btnClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), ManagerGiaoVienActivity.class);
                                startActivity(intent);
                            }
                        });
                    }else{
                        //Toast.makeText(EditGiaoVienActivity.this,"Sửa thất bại",Toast.LENGTH_SHORT).show();
                        showDialogError("Sửa thất bại!");
                        btnCloseE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                    }
                    showDialogSuccess("Thay đổi mật khẩu thành công!");
                    btnClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                }else{
                    if(!pass.equals(repass)){
                        showDialogError("Mật khẩu nhập lại không đúng");
                        btnCloseE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                    }else if(!codeverify.equals(code)){
                        showDialogError("Mã xác nhận không đúng");
                        btnCloseE.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                    }else{
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
    }
    public void sendEmail(String email,String code){
        BackgroundMail.newBuilder(this)
                .withUsername("tu01202880908@gmail.com")
                .withPassword("kjntfqztwfpxiwpo")
                .withMailto(email)
                .withSubject("[FORGET PASSWORD CODE]")
                .withBody(code)
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        //do some magic

                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {

                    }
                })
                .send();
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
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(myIntent);
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_bottom);
        return true;

    }
}
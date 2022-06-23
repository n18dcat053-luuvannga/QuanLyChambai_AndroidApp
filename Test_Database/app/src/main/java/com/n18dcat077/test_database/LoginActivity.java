package com.n18dcat077.test_database;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.n18dcat077.test_database.Mail.FogotActivity;
import com.n18dcat077.test_database.OTP.SendOTPActivity;


public class LoginActivity extends AppCompatActivity {
    DatabaseQLCB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final LoadingDialog loadingDialog=new LoadingDialog(LoginActivity.this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>Quản lí chấm bài</font>"));
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#83F7FF"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
//        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_baseline_arrow_back_ios_new_24);
//        upArrow.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
//        actionBar.setHomeAsUpIndicator(upArrow);
        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.pasword);
        AppCompatButton btnLogin = (AppCompatButton) findViewById(R.id.btnLogin);
//        AppCompatButton btnSignup = (AppCompatButton) findViewById(R.id.btnSignup);
        TextView forgetLable = (TextView) findViewById(R.id.forgetLable);
        TextView registerLable = (TextView) findViewById(R.id.registerLable) ;
        db = new DatabaseQLCB(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                if(user.equals("")||pass.equals("")) {
                    Toast.makeText(LoginActivity.this,"Vui lòng không bỏ trống các trường!",Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean check = db.checkAccount(user, pass);
                    if (check == true) {
                        loadingDialog.startLoadingDialog();
                        Toast.makeText(LoginActivity.this,"Login Succeed",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), StartUpAnimationActivity.class);
                        intent.putExtra("session", user);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Thông tin tài khoản hoặc mật khẩu không chính xác!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

//        btnSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
//                startActivity(intent);
//            }
//        });

        forgetLable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FogotActivity.class);
                startActivity(intent);
            }
        });

        registerLable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
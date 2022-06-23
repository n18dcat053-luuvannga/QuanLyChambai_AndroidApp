package com.n18dcat077.test_database.Mail;

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

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.google.android.material.textfield.TextInputLayout;
import com.n18dcat077.test_database.DatabaseQLCB;
import com.n18dcat077.test_database.GiaoVien.GiaoVien;
import com.n18dcat077.test_database.HomeActivity;
import com.n18dcat077.test_database.LoginActivity;
import com.n18dcat077.test_database.R;

import java.util.Random;

public class FogotActivity extends AppCompatActivity {

    public static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    TextInputLayout txtEmail;
    Button btnFind,btnCancel;

    DatabaseQLCB dbGiaoVien;
    Button btnClose, btnCloseE;
    TextView txtSuccess, txtError;
    ImageView imgSuccess, imgError;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
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
        txtEmail = findViewById(R.id.Email);
        btnCancel = findViewById(R.id.btnCancel);
        btnFind = findViewById(R.id.btnFind);

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
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getEditText().getText().toString();
                GiaoVien giaoVien = dbGiaoVien.getGiaoVienByEmail(email);
                if(giaoVien == null){
                    showDialogError("Email không tồn tại!");
                    btnCloseE.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });
                }else{
                    String code = generateRandomString(6);
                    sendEmail(email,code);
                    Intent intent = new Intent(getApplicationContext(),VerifyActivity.class);
                    intent.putExtra("CODE",code);
                    intent.putExtra("EMAIL",email);
                    startActivity(intent);
                }
            }
        });
    }

    public void sendEmail(String email,String code){
        BackgroundMail.newBuilder(this)
                .withUsername("tu01202880908@gmail.com")
                .withPassword("ejnpflemgnuzmulh")
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

    public String generateRandomString(int length) {
        StringBuilder randStr = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }

    private int getRandomNumber() {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
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

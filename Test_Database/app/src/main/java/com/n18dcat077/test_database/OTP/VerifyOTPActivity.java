package com.n18dcat077.test_database.OTP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.n18dcat077.test_database.HomeActivity;
import com.n18dcat077.test_database.R;
import com.n18dcat077.test_database.StartUpAnimationActivity;

import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity {
    EditText inputcod1,inputcod2,inputcod3,inputcod4,inputcod5,inputcod6;
    TextView textMobile;
    Button tvresend;
    String verificationId;
    Button btnverification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otpactivity);
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
        setcontrol();
        setEvent();
        setupOTPinput();
        getdataIntent();
        verificationId= getIntent().getStringExtra("verificationId");
    }
    private void setcontrol() {
        inputcod1=findViewById(R.id.inputcode1);
        inputcod2=findViewById(R.id.inputcode2);
        inputcod3=findViewById(R.id.inputCode3);
        inputcod4=findViewById(R.id.inputCode4);
        inputcod5=findViewById(R.id.inputCode5);
        inputcod6=findViewById(R.id.inputCode6);
        textMobile=findViewById(R.id.textveriMobile);
        btnverification=findViewById(R.id.btn_verify_otp);
        tvresend=findViewById(R.id.tvResendOTP);
    }
    private void setEvent() {
        btnverification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputcod1.getText().toString().trim().isEmpty()||
                        inputcod2.getText().toString().trim().isEmpty()||
                inputcod3.getText().toString().trim().isEmpty() ||
                inputcod4.getText().toString().trim().isEmpty() ||
                inputcod5.getText().toString().trim().isEmpty() ||
                inputcod6.getText().toString().trim().isEmpty()){
                    Toast.makeText(VerifyOTPActivity.this, "Nhập mã OTP", Toast.LENGTH_SHORT).show();
                return;
                }
                String code=
                        inputcod1.getText().toString()+
                                inputcod2.getText().toString()+
                                inputcod3.getText().toString()+
                                inputcod4.getText().toString()+
                                inputcod5.getText().toString()+
                                inputcod6.getText().toString();
                if(verificationId!=null){
                    btnverification.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(verificationId,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            btnverification.setVisibility(View.VISIBLE);
                            if(task.isSuccessful()){
                                Intent intent= new Intent(getApplicationContext(), StartUpAnimationActivity.class);
                                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }else{
                                Toast.makeText(VerifyOTPActivity.this, "code không hợp lệ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        tvresend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber("+84"+ getIntent().getStringExtra("mobile"),60, TimeUnit.SECONDS,
                        VerifyOTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(VerifyOTPActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newverificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verificationId= newverificationId;
                                Toast.makeText(VerifyOTPActivity.this, "đã gửi lại OTP", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
    private void getdataIntent(){
        textMobile.setText(String.format("+84-%s",getIntent().getStringExtra("mobile")));
    }

    private void setupOTPinput(){
        inputcod1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcod2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputcod2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcod3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputcod3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcod4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputcod4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcod5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputcod5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputcod6.requestFocus();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}
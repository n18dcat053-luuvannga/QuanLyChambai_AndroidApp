package com.n18dcat077.test_database;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

public class StartUpAnimationActivity extends HomeActivity {

    TextView redTextView,greenTextView,blueTextView,yellowTextView,nhomTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = this;
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.activity_start_up_animation);
        setControl();
        Animation blue_animation = AnimationUtils.loadAnimation(this, R.anim.left_to_right);
        Animation green_animation = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom);
        Animation yellow_animation = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        Animation red_animation = AnimationUtils.loadAnimation(this, R.anim.right_to_left);
        Animation nhom_animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        blueTextView.setAnimation(blue_animation);
        greenTextView.setAnimation(green_animation);
        yellowTextView.setAnimation(yellow_animation);
        redTextView.setAnimation(red_animation);

        nhomTextView.setAnimation(nhom_animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);


    }

    private void setControl() {
        redTextView = findViewById(R.id.red_textView);
        blueTextView = findViewById(R.id.blue_textView);
        greenTextView = findViewById(R.id.green_textView);
        yellowTextView = findViewById(R.id.yellow_textView);
        nhomTextView = findViewById(R.id.ten_nhom_textView);
    }



}
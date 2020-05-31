package com.tea.measuremaster;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import site.gemus.openingstartanimation.LineDrawStrategy;
import site.gemus.openingstartanimation.OpeningStartAnimation;

/**
 * 描述：闪屏页
 *
 * @author CoderPig on 2020/04/13 15:23.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        TextView tv_logo = findViewById(R.id.tv_logo);

        OpeningStartAnimation openingStartAnimation=new OpeningStartAnimation.Builder(this)
                .setAppName("Measure Elf")
                .setColorOfAppName(Color.BLUE)
                .setColorOfAppStatement(Color.RED)
                .setAppStatement("Less is more")
                .setAnimationInterval(1500L)
                .setAnimationFinishTime(2000L)
                .create();
        openingStartAnimation.show(this);
        tv_logo.postDelayed(this::jump,1300L);

    }

    /* 完成一些初始化操作 */
    private void init() {

    }

    /* 页面逻辑跳转 */
    private void jump() {
        startActivity(new Intent(this, MainMenu.class));
        finish();
    }

}

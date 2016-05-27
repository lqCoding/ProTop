package com.example.tarena.protop.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.tarena.protop.R;

public class FlashActivity extends AppCompatActivity {

    LinearLayout ll_splash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
       ll_splash= (LinearLayout) findViewById(R.id.ll_scale);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.splashanim);
        ll_splash.setVisibility(View.VISIBLE);
        ll_splash.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override

            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(FlashActivity.this,MainNews.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}

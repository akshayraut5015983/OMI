package com.omi.app.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.omi.app.R;
import com.omi.app.utils.SharePreferenceUtility;

import static com.omi.app.utils.Constants.AppConst.IS_LOGIN;

public class SplashScreenActivity extends AppCompatActivity {
    RotateAnimation r;
    private static final float ROTATE_FROM = 0.0f;
    ImageView img;
    private static final float ROTATE_TO = 360.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_splash_screen);
        img = findViewById(R.id.img);

        r = new RotateAnimation(ROTATE_FROM, ROTATE_TO, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        r.setDuration((long) 2 * 500);
        r.setRepeatCount(0);
        img.startAnimation(r);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLogin = (boolean) SharePreferenceUtility.getPreferences(SplashScreenActivity.this, IS_LOGIN, SharePreferenceUtility.PREFTYPE_BOOLEAN);
                if (isLogin) {
                    Intent i = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(i);
                }

            }
        }, 2000);
    }
}

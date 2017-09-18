package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import base.BaseAppActivity;
import test.jiao.smartapplication.R;


/**
 * 欢迎页
 */
public class WelcomeActivity extends BaseAppActivity {


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                WelcomeActivity.this.startActivity(intent);

            }
        }
    };

    protected SharedPreferences sp;
    protected SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        sp = getSharedPreferences("bjpay", MODE_PRIVATE);
        editor = sp.edit();
        //判断是否是第一次，第一次跳转到引导页，否则进入登录页
        Boolean is = sp.getBoolean("isFirstInstall",true);
        if (is) {
            startActivity(new Intent(this, GuideActivity.class));
       } else {
        handler.sendEmptyMessageDelayed(10, 1000);
        }

    }

    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }

}

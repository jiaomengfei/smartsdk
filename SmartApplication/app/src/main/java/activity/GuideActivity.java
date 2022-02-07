package activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import adapter.GuideAdapter;
import base.BaseAppActivity;
import test.jiao.smartapplication.R;


/**
 * 引导页jmf
 */

public class GuideActivity extends BaseAppActivity implements ViewPager.OnPageChangeListener,View.OnClickListener {

    private ViewPager vp;
    private RadioGroup rg;
    private RadioButton buttons[];
    private ArrayList<Integer> imgs;

    private GuideAdapter mGuideAdapter;
    protected SharedPreferences sp;
    protected SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        sp = getSharedPreferences("bjpay", MODE_PRIVATE);
        editor = sp.edit();
        //初始化View
        initVIew();
        //初始化ViewPager
        initViewPager();
        //初始化小圆点
        initButtons();
    }

    private void initVIew() {
    }

    private void initButtons() {
        rg = (RadioGroup) findViewById(R.id.guide_rg);
        buttons = new RadioButton[rg.getChildCount()];
        for (int i = 0; i < rg.getChildCount(); i++) {
            buttons[i] = (RadioButton) rg.getChildAt(i);
            rg.getChildAt(i).setEnabled(false);
        }

    }

    private void initViewPager() {
        vp = (ViewPager) findViewById(R.id.guide_vp);
        imgs=new ArrayList<>();
        imgs.add(R.drawable.guide_first);
        imgs.add(R.drawable.guide_second);
        imgs.add(R.drawable.guide_third);
        imgs.add(R.drawable.guide_forth);
        mGuideAdapter = new GuideAdapter(imgs, this);
        mGuideAdapter.setmCallBack(mCallBack);
        vp.setAdapter(mGuideAdapter);
        vp.setOnPageChangeListener(this);
    }

    private GuideAdapter.JumpCallBack mCallBack = new GuideAdapter.JumpCallBack() {

        @Override
        public void toJump() {
            editor.putBoolean("isFirstInstall",false);
            editor.commit();
            startActivity(new Intent(GuideActivity.this, WelcomeActivity.class));
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        buttons[position].setChecked(true);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    //失去焦点，关闭
    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onClick(View view) {

    }
}

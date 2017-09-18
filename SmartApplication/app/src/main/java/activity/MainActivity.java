package activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import base.BaseAppActivity;
import base.BaseFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import fragment.AcountFragment;
import fragment.HomeFragment;
import fragment.ServiceFragment;
import fragment.ShareFragment;
import test.jiao.smartapplication.R;

public class MainActivity extends BaseAppActivity {
    @BindView(R.id.main_rg)
     RadioGroup rg;

    private RadioButton buttons[];
    private ArrayList<BaseFragment> fragments;
    private int current;
    BaseFragment currentFragment = null;
    BaseFragment targartFragment = null;
    FragmentTransaction fragmentTransaction = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initButtons();
        initFragments();
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        ShareFragment shareFragment = new ShareFragment();
        ServiceFragment serviceFragment = new ServiceFragment();
        AcountFragment acountFragment = new AcountFragment();

        fragments.add(homeFragment);
        fragments.add(shareFragment);
        fragments.add(serviceFragment);
        fragments.add(acountFragment);
        //默认加载主页
        getSupportFragmentManager().beginTransaction().add(R.id.main_container, homeFragment).commitAllowingStateLoss();

    }

    private void initButtons() {
        buttons = new RadioButton[rg.getChildCount()];
        for (int i = 0; i < rg.getChildCount(); i++) {
            buttons[i] = (RadioButton) rg.getChildAt(i);
        }

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                for (int i1 = 0; i1 < buttons.length; i1++) {
                    if (buttons[i1].getId() == i) {
                        changFragment(i1);
                    }
                }
            }
        });
    }

    //切换Fragment
    private void changFragment(final int i1) {
        //获得了参数
        currentFragment = fragments.get(current);
        targartFragment = fragments.get(i1);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

                if (targartFragment.isAdded()) {
                    fragmentTransaction.hide(currentFragment).show(targartFragment).commitAllowingStateLoss();
                } else {
                    fragmentTransaction.hide(currentFragment).add(R.id.main_container, targartFragment).commitAllowingStateLoss();
                }
                current = i1;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

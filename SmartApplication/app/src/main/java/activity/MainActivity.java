package activity;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Map;

import base.BaseAppActivity;
import base.BaseFragment;
import bean.Permission;
import butterknife.BindView;
import butterknife.ButterKnife;
import fragment.AcountFragment;
import fragment.HomeFragment;
import fragment.ServiceFragment;
import fragment.ShareFragment;
import system.RuntimePermissionsCompatible;
import test.jiao.smartapplication.R;
import ui.ToastUtil;
import utils.SystemPermissionManager;

public class MainActivity extends BaseAppActivity implements View.OnClickListener{
    @BindView(R.id.main_rg)
     RadioGroup rg;

    private RadioButton buttons[];

    private ArrayList<BaseFragment> fragments;
    private int current;
    BaseFragment currentFragment = null;
    BaseFragment targartFragment = null;
    FragmentTransaction fragmentTransaction = null;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initButtons();
        initFragments();
        initPermisstions();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initPermisstions() {
        if (isNeedPermisstion()) {
            Permission permission1 = new Permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
            Permission permission2 = new Permission(Manifest.permission.CAMERA, 1);
            String[] permissions = new String[]{permission1.name, permission2.name};
            if(RuntimePermissionsCompatible.isAnyGranted(this, permissions)){


            }else{
                requestPermissions(new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE , Manifest.permission.CAMERA },
                        101);
            }

        }
    }

    /**
     * 判断是否是6.0及以上手机
     *
     * @return
     */
    private boolean isNeedPermisstion() {
        try {
            String strPackageName = this.getPackageName();
            PackageManager pm = this.getPackageManager();
            PackageInfo aPackageInfo = null;
            aPackageInfo = pm.getPackageInfo(strPackageName, 0);
            int iTargetSDKVersion = (aPackageInfo == null) ? 0
                    : aPackageInfo.applicationInfo.targetSdkVersion;
            return ((iTargetSDKVersion >= Build.VERSION_CODES.M) && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(null==permissions || permissions.length==0){
            return;
        }
        Map<String, Boolean> runtimePermissionsResults = RuntimePermissionsCompatible
                .onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        if (runtimePermissionsResults == null) {
            return;
        }

        boolean sd = runtimePermissionsResults.get(permissions[0]);
        // 容许后执行业务
        switch (requestCode) {
            case 101:
            case SystemPermissionManager.PHBJ_PERMISSIONS_SD: {

                if (sd ) {
                    // permission was granted, yay! do the calendar task you need to do
//                    if(path != null && !"".equals(path)){
//                        ForceUpdateUtil.showForceUpdateDialog(MainActivity.this, false, "新版本为您提供更好的使用体验，请您更新", path);
//                    }

                } else {

                    // permission denied, boo! Disable the functionality that depends on this permission.
                    // 不容许使用下载功能
                    ToastUtil.showToast(this, "由于手机存储权限被拒，您不能版本更新！");
                }
            }
            break;
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View view) {

    }
}

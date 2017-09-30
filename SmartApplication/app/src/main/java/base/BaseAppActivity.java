package base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import test.jiao.smartapplication.R;
import ui.SystemBarTintManager;
import utils.Constant;
import view.Loading;


/**
 * Created by Administrator on 2017/3/14 0014.
 */

public abstract class BaseAppActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "BaseAppActivity";
    /**
     * 页面上的蒙层dialog
     */
    public LoadingDialogUtil loadingdialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
        initView();
        initTitle();
        initData();
    }


    protected void initTitle() {
    }

    protected void initView() {
    }

    protected void initData() {

    }

    /**
     * 设置title内容
     *
     * @param titleRes
     */
    protected void setTitleContent(int titleRes) {
        BaseTitle baseTitle = (BaseTitle) findViewById(R.id.title);
        if (baseTitle != null) {
            baseTitle.setTitleContent(titleRes);
        } else {
           // XLog.e("null baseTitle");
        }
    }

    /**
     * 设置title内容
     *
     * @param titleRes
     */
    protected void setTitleContent(String titleRes) {
        BaseTitle baseTitle = (BaseTitle) findViewById(R.id.title);
        if (baseTitle != null) {
            baseTitle.setTitleContent(titleRes);
        } else {
           // XLog.e("null baseTitle");
        }
    }

    /**
     * 注册返回
     */
    protected void registerTitleBack() {
        BaseTitle baseTitle = (BaseTitle) findViewById(R.id.title);
        if (baseTitle != null) {
            baseTitle.registerLeftBack();
            baseTitle.getLeftLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        } else {
        }
    }

    /**
     * 注册右侧点击事件
     */
    protected void registerTitleRight(int textRes, View.OnClickListener listener) {
        BaseTitle baseTitle = (BaseTitle) findViewById(R.id.title);
        if (baseTitle != null) {
            baseTitle.registerRight(textRes);
            baseTitle.getRightLayout().setOnClickListener(listener);
        } else {
        }
    }

    /**
     * 注册右侧点击事件
     */
    protected void registerTitleRight(String textRes, View.OnClickListener listener) {
        BaseTitle baseTitle = (BaseTitle) findViewById(R.id.title);
        if (baseTitle != null) {
            baseTitle.registerRight(textRes);
            baseTitle.getRightLayout().setOnClickListener(listener);
        } else {
        }
    }


    public boolean isEmpty(String string){
        if (string != null && !string.isEmpty()){
            return false;
        }else {
            return true;

        }
    }


    public void initParam() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.nav_bg);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        loadingdialog = new LoadingDialogUtil(this, requireLoadingDialog());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingdialog = null;
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 基类中稍微封装了一下，防止在下面使用的情况中为null的情况出现
     */
    public class LoadingDialogUtil {
        public Dialog loadingdialog;

        public LoadingDialogUtil(Activity activity, boolean needDialog) {
            if (needDialog) {
                loadingdialog = Loading.dialog(activity);
            }
        }

        public void show() {
            if (loadingdialog != null) {
                if (!loadingdialog.isShowing()) {
                    loadingdialog.show();
                }
            } else {
                showErrorLog();
            }
        }

        public void dismiss() {
            if (loadingdialog != null) {
                loadingdialog.dismiss();
            } else {
                showErrorLog();
            }
        }

        public void cancel() {
            if (loadingdialog != null) {
                loadingdialog.cancel();
            } else {
                showErrorLog();
            }
        }

        private void showErrorLog() {
            Log.e("ERRRRRRRRRRRRRRRRRRRO", "要使用Dialog请查看在Activity中有没有覆盖requireLoadingDialog方法并返回true");
        }
    }

    /**
     * 当前页面中是否需要蒙层对话框，如果需要就返回true<br/>
     * 默认为没有蒙层对话框
     *
     * @deprecated use
     */
    public boolean requireLoadingDialog() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Constant.CONFIG.DEBUG) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_VOLUME_DOWN:
                    LogActivity.startThis(this);
                    return true;
                case KeyEvent.KEYCODE_VOLUME_UP:
                    CrashActivity.startThis(this);
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}

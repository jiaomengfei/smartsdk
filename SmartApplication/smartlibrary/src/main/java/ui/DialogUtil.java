package ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;



/**
 * Created by jmfz on 2017/3/15 0015.
 */

public class DialogUtil {
    private static final String TAG = "DialogUtil";

    private static Dialog showAlertDialog(Context activity, String title, String message, String positiveText, DialogInterface.OnClickListener positiveListener, String negativeText, DialogInterface.OnClickListener negativeListener, String neutralText, DialogInterface.OnClickListener neutralOnClick, OnDialogSetting callBack) {
        if (activity == null) {
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title);
        builder.setMessage(message);
        if (positiveText == null) {
            positiveText = "确定";
        }
        builder.setPositiveButton(positiveText, positiveListener);
        if (negativeText != null) {
            builder.setNegativeButton(negativeText, negativeListener);
        }
        if (neutralText != null) {
            builder.setNeutralButton(neutralText, neutralOnClick);
        }
        AlertDialog dialog = builder.create();
        if (callBack != null) {
            callBack.configDialog(dialog);
        }
        dialog.show();
        return dialog;
    }

    public interface OnDialogSetting {

        void configDialog(AlertDialog dialog);
    }

    /**
     * 弹出确认是否登陆的对话框
     */
    public static void showLoginDialog(final Activity activity) {
        toLoginActivity(activity);
//        new DialogUtil.DialogBuilder(activity, "请登录", "当前操作需要登录，请登录后再次点击").configPositive("登录", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                LoginActivity.startSelf(activity);
//            }
//        }).configNegative("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        }).configDialog(new OnDialogSetting() {
//            @Override
//            public void configDialog(AlertDialog dialog) {
//                dialog.setCancelable(false);
//            }
//        }).show();
    }

    /**
     * 直接跳转到登陆的对话框
     */
    public static void toLoginActivity(final Activity activity) {
        //LoginActivity.startSelf(activity);
//        new DialogUtil.DialogBuilder(activity, "请登录", "当前操作需要登录，请登录后再次点击").configPositive("登录", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                LoginActivity.startSelf(activity);
//            }
//        }).configNegative("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        }).configDialog(new OnDialogSetting() {
//            @Override
//            public void configDialog(AlertDialog dialog) {
//                dialog.setCancelable(false);
//            }
//        }).show();
    }

    /**
     * 弹出客服热线的对话框，这个对话框中点击确定可以去打电话
     */
    public static void showKefuTelDialog(final Context activity) {
        showKefuTelDialog(activity, "客服热线");
    }

    /**
     * 流量，手机，普惠专线，油卡充值中如果返回的数据或者要展示的商品的list为空，则弹出此对话框
     */
    public static void showItemsEmptyDialog(final Activity activity, String message) {
        new DialogBuilder(activity, "提示", message).configPositive("拨打客服", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toKeFuPhoneCall(activity);
            }
        }).configNegative("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    //    public static void showItemsEmptyDialog(final Activity activity) {
//        showItemsEmptyDialog(activity, "");
//    }
    public static void toKeFuPhoneCall(Context activity) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4000008688"));
        activity.startActivity(intent);
    }

    public static void showKefuTelDialog(final Context activity, String title) {
        new DialogUtil.DialogBuilder(activity, title, "客服热线：400-000-8688").
                configPositive("拨打", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        toKeFuPhoneCall(activity);
                    }
                }).
                configNegative("取消", null).
                show();
    }

    private static Dialog messageDialog;

    /**
     * 根据错误码显示一个显示信息的对话框
     */
    public static void showMessageDialog(final Context activity, String message) {
        if (message == null || message.length() <= 0) {
            message = "网络错误，请稍后再试";
        }
        if (messageDialog != null && messageDialog.isShowing()) {
            return;
        }
        messageDialog = new DialogUtil.DialogBuilder(activity, "提示", message).configPositive("拨打客服", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toKeFuPhoneCall(activity);
            }
        }).configNegative("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }


    private static Dialog logOutDialog;

    /**
     * 显示一个退出登录的对话框
     */
    public static void showLogOutDialog(final Activity activity) {
        if (logOutDialog != null && logOutDialog.isShowing()) {
            return;
        }
        logOutDialog = new DialogUtil.DialogBuilder(activity, "警告", "账号过期，请重新登录！").configPositive("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                LogOutRequest.exitLogin(activity);
//                SFUtil.clear(activity);
//                FileUtil.clearFiles(activity);
//                LoginActivity.startSelfClearTop(activity);
            }
        }).configDialog(new DialogUtil.OnDialogSetting() {
            @Override
            public void configDialog(AlertDialog dialog) {
                dialog.setCanceledOnTouchOutside(false);
                dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
                            return true;
                        }
                        return false;
                    }
                });
            }
        }).show();
    }


    /**
     * 对话框builder，通过{@link #configPositive(String, DialogInterface.OnClickListener)}传入确定按钮和按钮对应的事件<br/>
     * 通过{@link #configNegative(String, DialogInterface.OnClickListener)}传入取消按钮及其对应的事件<br/>
     * 通过{@link #configDialog(OnDialogSetting)}来对对话框进行配置<br/>
     * 通过{@link #configNeutral(String, DialogInterface.OnClickListener)}来传入中立的一个按钮及其事件，这个按钮的位置在对话框的最左面<br/>
     * 最后通过{@link #show()}将对话框进行显示
     */

    public static class DialogBuilder {
        private Context activity;
        private String title;
        private String message;

        /**
         * @param activity activity
         * @param title    标题
         * @param message  message
         */
        public DialogBuilder(Context activity, String title, String message) {
            this.activity = activity;
            this.title = title == null ? "提示" : title;
            if (message == null) {
                throw new NullPointerException("Dialog中的message不能为空");
            } else {
                this.message = message;
            }
        }

        String positiveText;
        DialogInterface.OnClickListener positiveOnClick;

        /**
         * 设置对话框的确定按钮以及确定按钮对应的事件
         */
        public DialogBuilder configPositive(String positiveText, DialogInterface.OnClickListener positiveOnClick) {
            this.positiveText = positiveText;
            this.positiveOnClick = positiveOnClick;
            return this;
        }

        String negativeText;
        DialogInterface.OnClickListener negativeOnClick;

        /**
         * 确定对话框的取消按钮的文字已经其对应的事件
         */
        public DialogBuilder configNegative(String negativeText, DialogInterface.OnClickListener negativeOnClick) {
            this.negativeText = negativeText;
            this.negativeOnClick = negativeOnClick;
            return this;
        }

        String neutralText;
        DialogInterface.OnClickListener neutralOnClick;

        /**
         * 设置最左面的按钮
         */
        public DialogBuilder configNeutral(String neutralText, DialogInterface.OnClickListener neutralOnClick) {
            this.neutralText = neutralText;
            this.neutralOnClick = neutralOnClick;
            return this;
        }

        OnDialogSetting dialogConfigCallBack;

        /**
         * 对对话框进行设置
         */
        public DialogBuilder configDialog(OnDialogSetting dialogConfigCallBack) {
            this.dialogConfigCallBack = dialogConfigCallBack;
            return this;
        }

        /**
         * 显示对话框
         */
        public Dialog show() {
            return DialogUtil.showAlertDialog(activity, title, message, positiveText, positiveOnClick, negativeText, negativeOnClick, neutralText, neutralOnClick, dialogConfigCallBack);
        }
    }
}

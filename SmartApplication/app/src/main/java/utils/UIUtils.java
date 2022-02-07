package utils;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.viewpager.widget.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;



import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import base.SmartAPP;
import test.jiao.smartapplication.R;
import view.PasswordEditText;


/**
 * UI工具类，包括提醒、弹出框、等待框等功能
 */
public class UIUtils {
    private static Dialog sDialog;
    private static Toast toast;
    private static Dialog dialog;
    //秘密框
    private static View mPaymentPopupView;
    private static PopupWindow mPaymentpopupWindow;

    private static final int WHAT_TOAST_SHORT = 1;
    private static final int WHAT_TOAST_LONG = 2;

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_TOAST_SHORT:
                    if (toast == null) {
                        toast = Toast.makeText(SmartAPP.getInstance(), msg.getData().getString("msg"), Toast.LENGTH_SHORT);
                    } else {
                        toast.setText(msg.getData().getString("msg"));
                    }
                    toast.show();
                    break;
                case WHAT_TOAST_LONG:
                    if (toast == null) {
                        toast = Toast.makeText(SmartAPP.getInstance(), msg.getData().getString("msg"), Toast.LENGTH_LONG);
                    } else {
                        toast.setText(msg.getData().getString("msg"));
                    }
                    toast.show();
                    break;
            }
        }
    };

    /**
     * 显示等待框
     *
     * @param context
     */
    public static void showLoadingDialog(Context context) {
        showLoadingDialog(context, null);
    }

    /**
     * 显示等待框-有提示
     *
     * @param context
     * @param message
     */
    public static void showLoadingDialog(Context context, String message) {
        if (context == null) {
            return;
        }
        sDialog = new Dialog(context, R.style.DialogFullScreen);
        LayoutInflater mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = mLayoutInflater.inflate(R.layout.dialog_loading, null);
        // 显示宽度为屏幕的4/5
        LayoutParams params = new LayoutParams(2 * SmartAPP.getInstance().getScreenWidth() / 5, LayoutParams.MATCH_PARENT);
        TextView tvMsg = (TextView) layout.findViewById(R.id.loading_msg);
        tvMsg.setText((message == null ? "请等待..." : message));
        sDialog.setContentView(layout, params);
        sDialog.show();
    }

    /**
     * 隐藏等待框
     */
    public static void dismissLoadingDialog() {
        if (sDialog != null && sDialog.isShowing()) {
            sDialog.dismiss();
            sDialog = null;
        }
    }

    /**
     * 显示toast
     *
     * @param msgStr
     */
    public static void toastShort(String msgStr) {
        Message toastMessage = new Message();
        toastMessage.what = WHAT_TOAST_SHORT;
        Bundle bundle = new Bundle();
        if (msgStr != null && !msgStr.isEmpty()) {
            bundle.putString("msg", msgStr);
            toastMessage.setData(bundle);
            handler.sendMessage(toastMessage);
        }
    }

    /**
     * 显示toast
     *
     * @param msg
     */
    public static void toastShort(int msg) {
        Message toastMessage = new Message();
        toastMessage.what = WHAT_TOAST_SHORT;
        Bundle bundle = new Bundle();
        bundle.putString("msg", SmartAPP.getInstance().getResources().getString(msg));
        toastMessage.setData(bundle);
        handler.sendMessage(toastMessage);
    }

    /**
     * 显示toast
     *
     * @param msg
     */
    public static void toastLong(int msg) {
        Message toastMessage = new Message();
        toastMessage.what = WHAT_TOAST_LONG;
        Bundle bundle = new Bundle();
        bundle.putString("msg", SmartAPP.getInstance().getResources().getString(msg));
        toastMessage.setData(bundle);
        handler.sendMessage(toastMessage);
    }

    /**
     * 显示toast
     *
     * @param msgStr
     */
    public static void toastLong(String msgStr) {
        Message toastMessage = new Message();
        toastMessage.what = WHAT_TOAST_LONG;
        Bundle bundle = new Bundle();
        bundle.putString("msg", msgStr);
        toastMessage.setData(bundle);
        handler.sendMessage(toastMessage);

    }

    /**
     * 中间显示toast
     *
     * @param msgStr
     */
    public static void toastCenter(String msgStr) {
        Toast toast = Toast.makeText(SmartAPP.getInstance(), msgStr, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 初始时获取屏幕大小
     */
    public static void initWidowSize() {
        DisplayMetrics dm = SmartAPP.getInstance().getResources().getDisplayMetrics();
//        App.getsInstance().setScreenWidth(dm.widthPixels);
//        App.getsInstance().setScreenHeight(dm.heightPixels);
    }

    /**
     * 提示-有标题、确定按钮
     *
     * @param activity
     * @param title
     * @param msg
     */
    public static void alert(Context activity, String title, String msg) {
        alert(activity, title, msg, "确定", null, null, null);
    }

    /**
     * 提示-有标题、确定按钮、确定按钮事件
     *
     * @param title
     * @param msg
     * @param l1
     */
    public static void alert(Context activity, String title, String msg, String liftText, View.OnClickListener l1) {
        alert(activity, title, msg, "取消", liftText, null, l1);
    }

    /**
     * 全文提示弹框
     *
     * @param activity
     * @param title
     * @param msg
     */
    public static synchronized void alert(Context activity, String title, String msg, String liftText, String rightText, View.OnClickListener l1, View.OnClickListener l2) {
        dialog = new Dialog(activity, R.style.DialogFullScreenAnim);
        dialog.setContentView(R.layout.dialog_alert);
        dialog.setCanceledOnTouchOutside(true);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);

        TextView titleTv = (TextView) dialog.findViewById(R.id.dialog_pwd_title);
        titleTv.setText(title);
        TextView contentTv = (TextView) dialog.findViewById(R.id.dialog_pwd_content);
        contentTv.setText(msg);

        TextView rightBtn = (TextView) dialog.findViewById(R.id.dialog_pwd_r);
        TextView liftBtn = (TextView) dialog.findViewById(R.id.dialog_pwd_l);
        liftBtn.setText(liftText);
        rightBtn.setText(rightText);

        if (rightText == null) {
            rightBtn.setVisibility(View.GONE);
        }

        if (l1 == null) {
            liftBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            });
        } else {
            liftBtn.setOnClickListener(l1);
        }

        if (l2 == null) {
            rightBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        } else {
            rightBtn.setOnClickListener(l2);
        }

        dialog.show();
    }

    public static void dismissAlertDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    /**
     * 安装apk
     *
     * @param context
     * @param file
     */
    public static void installApk(Context context, File file) {
        if (file.exists()) {
            Intent intent = new Intent();
            // 执行动作
            intent.setAction(Intent.ACTION_VIEW);
            // 执行的数据类
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    /**
     * 调用系统发送短消息界面
     *
     * @param context
     * @param mobile
     */
    public static void sendSms(Context context, String mobile) {
        Uri smsToUri = Uri.parse("smsto:" + mobile);
        Intent mIntent = new Intent(Intent.ACTION_SENDTO,
                smsToUri);
        context.startActivity(mIntent);
    }

    /**
     * 打开网页
     *
     * @param context
     * @param url
     */
    public static void openWeb(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 打开键盘
     *
     * @param context
     */
    public static void openKeypad(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏键盘
     *
     * @param context
     */
    public static void hideKeypad(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    /**
     * 支付输入框
     *
     * @param context
     * @param view    支付框显示在View的相对位置
     * @param amount  如果amount为空，则不显示。否则显示相应的金额
     * @param onclick 回调方法
     */
    public static void initPayView(final Context context, View view, String amount, final IPayViewInterface onclick) {
        mPaymentPopupView = LayoutInflater.from(context).inflate(R.layout.dialog_payment, null);
        mPaymentpopupWindow = new PopupWindow(mPaymentPopupView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
        mPaymentpopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPaymentpopupWindow.setFocusable(true);
        mPaymentpopupWindow.setOutsideTouchable(false);
        mPaymentpopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mPaymentpopupWindow.isShowing()) {
                    mPaymentpopupWindow.dismiss();
                }
            }
        });
        mPaymentpopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (mPaymentpopupWindow.isShowing()) {
            mPaymentpopupWindow.dismiss();
        } else {
            mPaymentpopupWindow.showAtLocation(view, Gravity.RIGHT | Gravity.BOTTOM, 10, 10);
        }

        mPaymentPopupView.findViewById(R.id.dialog_payment_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPaymentpopupWindow.isShowing()) {
                    mPaymentpopupWindow.dismiss();
                }
            }
        });
        if (amount != null) {
            TextView _amountTv = (TextView) mPaymentPopupView.findViewById(R.id.dialog_payment_amount);
            _amountTv.setVisibility(View.VISIBLE);
            _amountTv.setText("¥" + amount);
        }

        final PasswordEditText mPwdEt = ((PasswordEditText) mPaymentPopupView.findViewById(R.id.dialog_payment_pwd_et));
        mPaymentPopupView.findViewById(R.id.dialog_payment_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPaymentpopupWindow.isShowing()) {
                    mPaymentpopupWindow.dismiss();
                }
            }
        });
        mPwdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String _editable = editable.toString().trim();

                int length = 0;
                if (_editable.equals("")) {
                    length = 0;
                } else {
                    length = editable.length();
                }

                if (length == 6) {
                    onclick.payCallback(mPwdEt.getText().toString());
                }

            }
        });


        // 首先要对指定的输入框请求焦点。然后调用输入管理器弹出软键盘。
        // 警告：对于刚跳到一个新的界面就要弹出软键盘的情况上述代码可能由于界面为加载完全而无法弹出软键盘。此时应该适当的延迟弹出软键盘如998毫秒（保证界面的数据加载完成
        mPwdEt.setFocusable(true);
        mPwdEt.setFocusableInTouchMode(true);
        mPwdEt.requestFocus();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                InputMethodManager inputManager = (InputMethodManager) mPwdEt
                        .getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(mPwdEt, 0);
            }

        }, 100);

    }

    private static String fristPwd;
    private static int type;

    /**
     * 交易密码设置对话框
     *
     * @param context 上下文
     * @param title   标题
     * @param amount  金额
     * @param onclick 支付密码输入完毕回调
     */
    public static void showPayDialog(Context context, String title, final String amount, final IPayViewInterface onclick) {
        dialog = new Dialog(context, R.style.DialogFullScreenAnim);
        dialog.setContentView(R.layout.dialog_payment);
        dialog.setCanceledOnTouchOutside(true);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialogWindow.setAttributes(lp);

        final TextView titleTv = (TextView) dialog.findViewById(R.id.dialog_payment_title);
        final TextView amountTv = (TextView) dialog.findViewById(R.id.dialog_payment_amount);
        final TextView promptTv = (TextView) dialog.findViewById(R.id.dialog_payment_prompt);
        ImageView closeIv = (ImageView) dialog.findViewById(R.id.dialog_payment_close);
        final PasswordEditText pwdEt = (PasswordEditText) dialog.findViewById(R.id.dialog_payment_pwd_et);
        //判断用户是否有支付密码
//        WalletApi.getInstance().getIsSetPay(new ResponseCallBack<ResponseBean>() {
//            @Override
//            public void onResponseSuccess(ResponseBean result) {
//                if (result.result_code == NetWorkConfig.REQUEST_SUCCESS) {
//                    //有支付密码
//                    titleTv.setText("请输入交易密码");
//                    if (amount != null) {
//                        amountTv.setVisibility(View.VISIBLE);
//                        amountTv.setText(amount);
//                    }
//                    type = 0;
//
//                } else {
//                    titleTv.setText("设置交易密码");
//                    promptTv.setVisibility(View.VISIBLE);
//                    promptTv.setText("您还没有设置交易密码，请立即设置");
//                    type = 1;
//                }
//            }
//
//            @Override
//            public void onResponseFail(int errCode) {
//
//            }
//        });
        //监听用户输入
        pwdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String sText = s.toString().trim();
                if (sText.length() == 6) {
                    switch (type) {
                        case 0:
                            //可进行支付
                            onclick.payCallback(sText);
                            dialog.dismiss();
                            break;
                        case 1:
                            //设置支付密码 第一次输入完成
                            fristPwd = sText;
                            promptTv.setText("请再次输入支付密码");
                            pwdEt.setText("");
                            type = 2;
                            break;
                        case 2:
                            //设置支付密码 第二次输入完成
                            if (fristPwd.equals(sText)) {
                                //TODO 设置交易密码

                                titleTv.setText("请输入交易密码");
                                if (amount != null) {
                                    amountTv.setVisibility(View.VISIBLE);
                                    amountTv.setText(amount);
                                }
                                promptTv.setVisibility(View.GONE);
                                pwdEt.setText("");
                                type = 0;
                            } else {
                                UIUtils.toastShort("2次输入密码不一致，请重新设置");
                                titleTv.setText("设置交易密码");
                                promptTv.setVisibility(View.VISIBLE);
                                promptTv.setText("您还没有设置交易密码，请立即设置");
                                pwdEt.setText("");
                                type = 1;
                            }
                            break;
                    }
                }
            }
        });
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null) {
                    dismissAlertDialog();
                }
            }
        });
        dialog.show();
    }

    /**
     * 隐藏支付框
     */
    public static void dismissPayView() {
        if (mPaymentpopupWindow.isShowing()) {
            mPaymentpopupWindow.dismiss();
        }
    }

    /**
     * 支付款给回调方法
     */
    public interface IPayViewInterface {
        void payCallback(String secret);
    }


    /**
     * 提示内容弹出框
     */
    private static PopupWindow mNoticepopupWindow;

    public static void showNoticeDialog(Context context, View view, int strId) {
        showNoticeDialog(context, view, context.getResources().getString(strId));
    }

    public static void showNoticeDialog(Context context, View view, String str) {
        // 一个自定义的布局，作为显示的内容
        View noticeView = LayoutInflater.from(context).inflate(R.layout.dialog_notice, null);
        TextView contentTv = (TextView) noticeView.findViewById(R.id.dialog_notice_content_tv);
        //显示提示内容
        contentTv.setText(str);
        // 设置按钮的点击事件
        mNoticepopupWindow = new PopupWindow(noticeView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        mNoticepopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mNoticepopupWindow.setOutsideTouchable(true);
        mNoticepopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (mNoticepopupWindow.isShowing()) {
                    mNoticepopupWindow.dismiss();
                }
            }
        });
        mNoticepopupWindow.setTouchable(true);
        mNoticepopupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        mNoticepopupWindow.showAsDropDown(view);


    }

}


package view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.TextView;

import base.SmartAPP;
import test.jiao.smartapplication.R;

/**
 * Created by jmf on 2017/9/28 0028.
 */

public class DialogStyle {

    //Dialog  publishDialog = new Dialog(SmartAPP.getInstance(), R.style.DialogFullScreenMain);

//    publishDialog.setContentView(R.layout.dialog_style);
//    publishDialog.setCanceledOnTouchOutside(false);
//    Window dialogWindow = publishDialog.getWindow();
//    dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
//    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//    lp.dimAmount = 0.8f;
//    lp.width = App.getsInstance().getScreenWidth();
//    dialogWindow.setAttributes(lp);
//    dialogWindow.setGravity(Gravity.BOTTOM);

//    private void showVDialog(String content, final String url) {
//        dialog = new Dialog(this, R.style.DialogFullScreenAnim);
//        dialog.setContentView(R.layout.dialog_ver);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
//                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//                    finish();
//                }
//                return false;
//            }
//        });
//        Window dialogWindow = dialog.getWindow();
//        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialogWindow.setAttributes(lp);
//        TextView updateTv = (TextView) dialog.findViewById(R.id.dialog_pwd_content);
//        updateTv.setText(Html.fromHtml(content));
//
//        TextView updateY = (TextView) dialog.findViewById(R.id.dialog_pwd_l);
//        TextView updateN = (TextView) dialog.findViewById(R.id.dialog_pwd_r);
//
//        updateY.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (URLUtil.isValidUrl(url)) {
//                    try {
//                        Intent updateIntent = new Intent(App.getsInstance(), AppUpdateService.class);
//                        updateIntent.putExtra("app_name", App.getsInstance().getResources().getString(R.string.app_name));
//                        updateIntent.putExtra("down_url", url);
//                        App.getsInstance().startService(updateIntent);
//                    } catch (Exception e) {
//                        UIUtils.toastShort("升级失败！");
//                    }
//                } else {
//                    UIUtils.toastShort("非法的Url");
//                }
//                dialog.dismiss();
//            }
//        });
//
//        updateN.setVisibility(View.GONE);
//        updateN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }
}

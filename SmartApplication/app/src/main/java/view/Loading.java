package view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import test.jiao.smartapplication.R;


/**
 * 加载动画
 * Created by jmf on 2016/11/5.
 */

public class Loading {

    private static Dialog dialog;

    public static Dialog dialog(Context context) {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
        dialog = new Dialog(context);

        LayoutInflater inflater = LayoutInflater.from(context);

        LinearLayout inflate = (LinearLayout) inflater.inflate(R.layout.loading, null);
        ImageView imageView = (ImageView) inflate.findViewById(R.id.loading_iv);
        imageView.setImageResource(R.drawable.loading);

        AnimationDrawable drawable = (AnimationDrawable) imageView.getDrawable();
        drawable.start();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(inflate);

        return dialog;
    }

    private static String TAG = "45";

    public static void dismissDialog(Activity activity) {
        Log.e(TAG, "dismissDialog: " + 1);
        try {
            if (dialog != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "dismissDialog: " + 2);
                        dialog.dismiss();
                        Log.e(TAG, "dismissDialog: " + 3);
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "dismissDialog: " + 4);
        }
        Log.e(TAG, "dismissDialog: " + 5);
    }


}

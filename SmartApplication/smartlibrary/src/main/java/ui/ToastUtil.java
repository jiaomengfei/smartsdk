package ui;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by jmfd on 2017/7/25 0025.
 */

public class ToastUtil {

    /** 将toast设置成单例 */
    private static Toast mToast = null;
    private static String oldMsg;
    private static long oneTime = 0;
    private static long twoTime = 0;

    /**
     * Toast类型提示框,始终显示最新的toast
     * */
    public static void showToast(Context context, String msg) {
        if (null == mToast) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            mToast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (msg.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    mToast.show();
                }
            } else {
                oldMsg = msg;
                mToast.setText(oldMsg);
                mToast.show();
            }
        }
        oneTime = twoTime;
    }

    /**
     * @param context
     * @param msgId
     */
    public static void showToast(Context context, int msgId) {
        showToast(context, context.getResources().getString(msgId));
    }
}

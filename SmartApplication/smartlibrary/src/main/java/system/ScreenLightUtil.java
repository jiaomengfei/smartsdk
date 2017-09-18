package system;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by jmfz on 2017/4/13 0013.
 */

public class ScreenLightUtil {
    /**
     * 设置亮度
     *
     * @param context
     * @param light
     */
    public static void SetLight(Activity context, int light) {
        WindowManager.LayoutParams localLayoutParams = context.getWindow().getAttributes();
        localLayoutParams.screenBrightness = (light / 255.0F);
        context.getWindow().setAttributes(localLayoutParams);
    }

    /**
     * 获取亮度
     *
     * @param context
     * @return
     */
    float GetLightness(Activity context) {
        WindowManager.LayoutParams localLayoutParams = context.getWindow().getAttributes();
        float light = localLayoutParams.screenBrightness;
        return light;
    }

    /**
     * 设置屏幕常亮
     */
    public static void setScreenAlwaysLight(Activity activity) {
        if (activity==null){
            return;
        }
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * 设置屏幕可以变暗
     *
     * @param activity
     */
    public static void setScreenDark(Activity activity) {
        if (activity==null){
            return;
        }
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}

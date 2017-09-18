package system;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by jmfd on 2017/7/25 0025.
 */

public class SystemUtil {
    /**
     *
     * @param pContext
     * @param pPackageName
     * @return 获取当前手机系统SDK版本号
     * @throws PackageManager.NameNotFoundException
     */
    public static int getTargetSDKVersoin(Context pContext, String pPackageName) throws PackageManager.NameNotFoundException {
        PackageManager pm = pContext.getPackageManager();
        PackageInfo aPackageInfo = pm.getPackageInfo(pPackageName, 0);
        return aPackageInfo == null?0:aPackageInfo.applicationInfo.targetSdkVersion;
    }

    private static long	lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}

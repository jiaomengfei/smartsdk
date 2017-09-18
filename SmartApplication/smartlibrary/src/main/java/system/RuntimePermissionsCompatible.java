package system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jmf on 2017/7/25 0025.
 */

public class RuntimePermissionsCompatible {
    private static final String TAG = RuntimePermissionsCompatible.class.getSimpleName();
    private static final int MARSHMALLOW = 23;
    private static final int REQUEST_CODE = 23;

    protected RuntimePermissionsCompatible() {
    }

    public static boolean isNeedCheckPermission(Context pContext) throws PackageManager.NameNotFoundException {
        String strPackageName = pContext.getPackageName();
        int iTargetSDKVersion = SystemUtil.getTargetSDKVersoin(pContext, strPackageName);
        return iTargetSDKVersion >= 23 && Build.VERSION.SDK_INT >= 23;
    }

    public static boolean isGranted(@NonNull Context context, @NonNull String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == 0;
    }

    public static boolean isAnyGranted(@NonNull Context context, @NonNull String[] permissions) {
        String[] var5 = permissions;
        int var4 = permissions.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            String permission = var5[var3];
            if(isGranted(context, permission)) {
                return true;
            }
        }

        return false;
    }

    public static boolean areAllGranted(@NonNull Context context, @NonNull String[] permissions) {
        String[] var5 = permissions;
        int var4 = permissions.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            String permission = var5[var3];
            if(!isGranted(context, permission)) {
                return false;
            }
        }

        return true;
    }

    public static boolean shouldPrompt(@NonNull Activity activity, @NonNull String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    public static boolean shouldPrompt(@NonNull Activity activity, @NonNull String[] permissions) {
        String[] var5 = permissions;
        int var4 = permissions.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            String permission = var5[var3];
            if(shouldPrompt(activity, permission)) {
                return true;
            }
        }

        return false;
    }

    public static void requestPermission(@NonNull Activity activity, @NonNull String permission, int pReqCode) {
        requestPermissions(activity, new String[]{permission}, pReqCode);
    }

    @SuppressLint({"NewApi"})
    public static void requestPermissions(@NonNull Activity activity, @NonNull String[] permissions, int pReqCode) {
        int i;
        int[] var10;
        if(Build.VERSION.SDK_INT < 23) {
            try {
                Log.i(TAG, "No Runtime Permissions -- bridging to Activity.onRequestPermissionsResult()");
                Method var9 = activity.getClass().getMethod("onRequestPermissionsResult", new Class[]{Integer.TYPE, String[].class, int[].class});
                var9.setAccessible(true);
                var10 = new int[permissions.length];

                for(i = 0; i < permissions.length; ++i) {
                    var10[i] = isGranted(activity, permissions[i])?0:-1;
                }

                var9.invoke(activity, new Object[]{Integer.valueOf(pReqCode), permissions, var10});
            } catch (Exception var8) {
                Log.w(TAG, "Calling Activity must implement onRequestPermissionsResult()", var8);
            }

        } else {
            int alreadyGranted = 0;
            String[] var7 = permissions;
            int var6 = permissions.length;

            for(i = 0; i < var6; ++i) {
                String grantResults = var7[i];
                alreadyGranted += isGranted(activity, grantResults)?1:0;
            }

            if(alreadyGranted != permissions.length) {
                ActivityCompat.requestPermissions(activity, permissions, pReqCode);
            } else {
                var10 = new int[alreadyGranted];

                for(i = 0; i < alreadyGranted; ++i) {
                    var10[i] = 0;
                }

                activity.onRequestPermissionsResult(pReqCode, permissions, var10);
            }
        }
    }

    public static boolean isRevocable(@NonNull Context context, @NonNull String permission) {
        if(Build.VERSION.SDK_INT < 23) {
            return false;
        } else {
            PackageManager packageManager = context.getPackageManager();
            if(packageManager == null) {
                return false;
            } else {
                try {
                    PermissionInfo e = packageManager.getPermissionInfo(permission, 0);
                    int protectionLevel = e.protectionLevel & 15;
                    return protectionLevel != 0;
                } catch (Exception var5) {
                    Log.e(TAG, var5.getMessage(), var5);
                    return false;
                }
            }
        }
    }

    public static boolean isAnyRevocable(@NonNull Context context, @NonNull String[] permissions) {
        String[] var5 = permissions;
        int var4 = permissions.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            String permission = var5[var3];
            if(isRevocable(context, permission)) {
                return true;
            }
        }

        return false;
    }

    public static boolean areAllRevocable(@NonNull Context context, @NonNull String[] permissions) {
        String[] var5 = permissions;
        int var4 = permissions.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            String permission = var5[var3];
            if(!isRevocable(context, permission)) {
                return false;
            }
        }

        return true;
    }

    public static Map<String, Boolean> onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        HashMap results = new HashMap();

        for(int i = 0; i < permissions.length; ++i) {
            results.put(permissions[i], grantResults[i] == 0? Boolean.TRUE: Boolean.FALSE);
        }

        return results;
    }
}

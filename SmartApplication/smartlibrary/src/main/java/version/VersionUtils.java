package version;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class VersionUtils {

	/**
	 * 获取版本name
	 *
	 * @param context
	 *                  程序上下文
	 * @return
	 *                程序设置的版本名称；若失败则返回null
	 */
    public static String GetVersionName(Context context) {
    	String strVersionName = null;

		try {
			PackageInfo manager = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			strVersionName = manager.versionName;
		} catch (NameNotFoundException e) {
				e.printStackTrace();
		}

		return strVersionName;
	}

    /**
	 * 获取版本code
	 *
	 * @param context
	 *                  程序上下文
	 * @return
	 *                程序设置的版本code；若失败则返回null
	 */
    public static String GetVersionCode(Context context) {
    	String strVersionCode = null;
    	
		try {
			PackageInfo manager = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			strVersionCode = String.valueOf(manager.versionCode);
		} catch (NameNotFoundException e) {
				e.printStackTrace();
		}
		
		return strVersionCode;
	}
    
    
}

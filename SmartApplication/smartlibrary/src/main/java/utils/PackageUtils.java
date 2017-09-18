package utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;


public class PackageUtils {

	/**
	 * 获取编译时设置的targetsdkversion
	 *
	 * @param pContext
	 * @param pPackageName
	 * @return
	 *                成功返回targetsdkversion 否则返回0
	 * @throws NameNotFoundException
	 */
	public static int getTargetSDKVersoin(Context pContext, String pPackageName) throws NameNotFoundException {
		PackageManager pm = pContext.getPackageManager();
		PackageInfo aPackageInfo = pm.getPackageInfo(pPackageName, 0);

		return (aPackageInfo == null) ? 0 :  aPackageInfo.applicationInfo.targetSdkVersion;
	}
		    
}

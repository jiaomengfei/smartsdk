package utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;


public class PackageUtils {


	public static int getTargetSDKVersoin(Context pContext, String pPackageName) throws NameNotFoundException {
		PackageManager pm = pContext.getPackageManager();
		PackageInfo aPackageInfo = pm.getPackageInfo(pPackageName, 0);

		return (aPackageInfo == null) ? 0 :  aPackageInfo.applicationInfo.targetSdkVersion;
	}
		    
}

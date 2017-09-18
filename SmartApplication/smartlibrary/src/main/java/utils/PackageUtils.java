package utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;


public class PackageUtils {

	/**
	 * ��ȡ����ʱ���õ�targetsdkversion
	 *
	 * @param pContext
	 * @param pPackageName
	 * @return
	 *                �ɹ�����targetsdkversion ���򷵻�0
	 * @throws NameNotFoundException
	 */
	public static int getTargetSDKVersoin(Context pContext, String pPackageName) throws NameNotFoundException {
		PackageManager pm = pContext.getPackageManager();
		PackageInfo aPackageInfo = pm.getPackageInfo(pPackageName, 0);

		return (aPackageInfo == null) ? 0 :  aPackageInfo.applicationInfo.targetSdkVersion;
	}
		    
}

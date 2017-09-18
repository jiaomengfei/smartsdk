package version;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class VersionUtils {

	/**
	 * ��ȡ�汾name
	 *
	 * @param context
	 *                  ����������
	 * @return
	 *                �������õİ汾���ƣ���ʧ���򷵻�null
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
	 * ��ȡ�汾code
	 *
	 * @param context
	 *                  ����������
	 * @return
	 *                �������õİ汾code����ʧ���򷵻�null
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

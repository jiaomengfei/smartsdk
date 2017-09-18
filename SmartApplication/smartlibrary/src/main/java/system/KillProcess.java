package system;

import android.app.ActivityManager;
import android.content.Context;

/**
 * �˳����̵ļ��ַ�ʽ
 *
 * @author dzyssssss
 *
 */
public class KillProcess {

	/**
	 * �˳�APP��ʽ1
	 */
	public static void quitAppExit(){
		System.exit(0);//ֱ�ӽ�������
	}

	/**
	 * �˳�APP��ʽ2
	 */
	public static void quitAppKillProcess(){

		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * �˳�APP��ʽ3
	 *
	 * ע�⣺��Ҫ����Ȩ�ޣ�android.permission.RESTART_PACKAGES
	 *
	 * @param  paramContext
	 *                   ����������
	 * @param paramPackageName
	 *                   Ҫ�ر�Ӧ�ó���İ���
	 */
	public static void quitAppKillBackground(Context paramContext, String paramPackageName){
		ActivityManager am = (ActivityManager) paramContext.getSystemService(paramContext.ACTIVITY_SERVICE);
		if( android.os.Build.VERSION.SDK_INT < 8){
		    am.restartPackage(paramPackageName);
		}else{
		    am.killBackgroundProcesses(paramPackageName);
		}
	}
}

package system;

import android.app.ActivityManager;
import android.content.Context;

/**
 * 退出进程的几种方式
 *
 * @author dzyssssss
 *
 */
public class KillProcess {

	/**
	 * 退出APP方式1
	 */
	public static void quitAppExit(){
		System.exit(0);//直接结束程序
	}

	/**
	 * 退出APP方式2
	 */
	public static void quitAppKillProcess(){

		android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 退出APP方式3
	 *
	 * 注意：需要增加权限：android.permission.RESTART_PACKAGES
	 *
	 * @param  paramContext
	 *                   程序上下文
	 * @param paramPackageName
	 *                   要关闭应用程序的包名
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

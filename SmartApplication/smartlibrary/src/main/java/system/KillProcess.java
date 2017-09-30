package system;

import android.app.ActivityManager;
import android.content.Context;



public class KillProcess {


	public static void quitAppExit(){
		System.exit(0);//ֱ�ӽ�������
	}


	public static void quitAppKillProcess(){

		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public static void quitAppKillBackground(Context paramContext, String paramPackageName){
		ActivityManager am = (ActivityManager) paramContext.getSystemService(paramContext.ACTIVITY_SERVICE);
		if( android.os.Build.VERSION.SDK_INT < 8){
		    am.restartPackage(paramPackageName);
		}else{
		    am.killBackgroundProcesses(paramPackageName);
		}
	}
}

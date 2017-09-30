package system;

import android.os.Handler;
import android.os.Message;


public class HandlerMessageUtils {


	public static void SendMSG(Handler paramHanler, int paramMsgID,
			Object paramInfo) {
		Message msg = paramHanler.obtainMessage(paramMsgID);
		msg.obj = paramInfo;
		msg.sendToTarget();
	}
}

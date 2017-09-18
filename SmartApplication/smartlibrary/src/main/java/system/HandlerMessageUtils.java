package system;

import android.os.Handler;
import android.os.Message;

/**
 * 
* <p>Title: HandlerMessageUtils</p>
* <p>Description:handler������Ϣ������ </p>
* @author    lyue
* @date       2016-4-8
 */
public class HandlerMessageUtils {

	/**
	 *
	* <p>Title: SendMSG</p>
	* <p>Description:������Ϣ </p>
	* @param paramHanler  Handler
	* @param paramMsgID   ��Ϣid
	* @param paramInfo    ��Ϣ����
	 */
	public static void SendMSG(Handler paramHanler, int paramMsgID,
			Object paramInfo) {
		Message msg = paramHanler.obtainMessage(paramMsgID);
		msg.obj = paramInfo;
		msg.sendToTarget();
	}
}

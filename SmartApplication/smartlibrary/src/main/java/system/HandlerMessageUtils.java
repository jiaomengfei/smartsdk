package system;

import android.os.Handler;
import android.os.Message;

/**
 * 
* <p>Title: HandlerMessageUtils</p>
* <p>Description:handler发送消息工具类 </p>
* @author    lyue
* @date       2016-4-8
 */
public class HandlerMessageUtils {

	/**
	 *
	* <p>Title: SendMSG</p>
	* <p>Description:发送消息 </p>
	* @param paramHanler  Handler
	* @param paramMsgID   消息id
	* @param paramInfo    消息对象
	 */
	public static void SendMSG(Handler paramHanler, int paramMsgID,
			Object paramInfo) {
		Message msg = paramHanler.obtainMessage(paramMsgID);
		msg.obj = paramInfo;
		msg.sendToTarget();
	}
}

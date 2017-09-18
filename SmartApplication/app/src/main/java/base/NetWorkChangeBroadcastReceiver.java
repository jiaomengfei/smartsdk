package base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


/**
 * Created by zxk on 2017/4/20 0020.
 */

public class NetWorkChangeBroadcastReceiver extends BroadcastReceiver {

    public static final String NET_CHANGE = "net_change";
    //标记当前网络状态，0为无可用网络状态，1表示有。
    public static final String NET_TYPE = "net_type";

    @Override
    public void onReceive(final Context context, Intent intent) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //移动数据
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        //wifi网络
        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            //网络状态全部不可用
            Toast.makeText(context, "您的当前网络不可用，请开启网络后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mobNetInfo.isConnected() || wifiNetInfo.isConnected()) {
            //网络状态有一个可用
            SmartAPP.getInstance().getParams(new SmartAPP.GetParamCallBack() {
                @Override
                public void getParamResult(boolean result) {
                    Intent netIntent = new Intent(NET_CHANGE);
                    netIntent.putExtra(NET_TYPE,1);
                    context.sendBroadcast(netIntent);
                }
            });
            return;
        }
    }
}

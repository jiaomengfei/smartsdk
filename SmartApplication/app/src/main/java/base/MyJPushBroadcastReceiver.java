package base;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


/**
 * Created by jmf on 2017/9/22 0022.
 */

public class MyJPushBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG	= "MyReceiver";
    private NotificationManager nm;
    protected SharedPreferences sp;
    protected SharedPreferences.Editor editor;
    //private SpeechSynthesizer mSpeechSynthesizer;


    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

       // mSpeechSynthesizer = SpeechSynthesizer.getInstance();

        Bundle bundle = intent.getExtras();

//        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//            Log.d(TAG, "JPush用户注册成功");
//            String mRegistrationID=JPushInterface.getRegistrationID(PHBJApp.getInstance());
//            Log.d(TAG, mRegistrationID);
//            sp = PHBJApp.getInstance().getSharedPreferences("bjpay", MODE_PRIVATE);
//            editor = sp.edit();
//            editor.putString("jpushid",mRegistrationID);
//            editor.commit();
//
//        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
//            Log.d(TAG, "接受到推送下来的自定义消息");
//
//        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
//            Log.d(TAG, "接受到推送下来的通知");
//
//            receivingNotification(context, bundle);
//
//        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//            Log.d(TAG, "用户点击打开了通知");
//
//            openNotification(context, bundle);
//
//        } else {
//            Log.d(TAG, "Unhandled intent - " + intent.getAction());
//        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
//        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//        Log.d(TAG, " title : " + title);
//        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
//        Log.d(TAG, "message : " + message);
//        mSpeechSynthesizer.speak(message);
//        ToastUtil.showToast(PHBJApp.getInstance(),message);
//        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//        Log.d(TAG, "extras : " + extras);
    }

    private void openNotification(Context context, Bundle bundle) {
//        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//        String myValue = "";
//        try {
//            JSONObject extrasJson = new JSONObject(extras);
//            myValue = extrasJson.optString("url");
//
//        } catch (Exception e) {
//            Log.w(TAG, "Unexpected: extras is not a valid json", e);
//            return;
//        }
//        // if (TYPE_THIS.equals(myValue)) {
//
//        sharedPreferences = context.getSharedPreferences("otherSetting", Context.MODE_PRIVATE);
//        Boolean push = sharedPreferences.getBoolean("click", false);
//        if (!push) {
//            return;
//        }
//        Intent mIntent = new Intent(context, MessageActivity.class);
//        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(mIntent);
        // }
        // else if (TYPE_ANOTHER.equals(myValue)){
        // Intent mIntent = new Intent(context, AnotherActivity.class);
        // mIntent.putExtras(bundle);
        // mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // context.startActivity(mIntent);
        // }
    }
}

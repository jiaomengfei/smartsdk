package utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

//import com.base.framwork.cach.preferce.PreferceManager;
//import com.base.platform.android.application.BaseApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import ndp.pindan.android.application.PinDanApplication;
//import ndp.pindan.android.service.CheckAcService;
//import ndp.pindan.android.service.ServiceAccess;
//import ndp.pindan.configer.constant.NetStateCollect;
//import ndp.pindan.logic.AppCheckController;

/**
 * 硬件的工具类
 */
public class DeviceDetails {

    public static String getModelName() {
        return Build.MODEL;
    }

    public static String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public static String getSerialNumber() {
        return Build.SERIAL;
    }

//    public static String getIMEIID() {
//        try {
//            return ((TelephonyManager) BaseApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
//        }catch (Exception e){
//            return "";
//        }
//
//    }
//
//    private String getIMSIID() {
//        return ((TelephonyManager) BaseApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
//    }
//
//    public static String getDeviceName() {
//        String str1 = Build.MANUFACTURER;
//        String str2 = Build.MODEL;
//        String str3 = Build.PRODUCT;
//        if (str2.startsWith(str1)) {
//            return str2 + " " + str3;
//        }
//        return str1 + " " + str2 + " " + str3;
//    }
//
//    public static String getBrandName() {
//        return Build.BRAND;
//    }
//
//
//    public static String getUserDataFromServer() {
//        JSONObject localObject = new JSONObject();
//        try {
//            localObject.put("deviceId", getandroidID());
//            localObject.put("imeiId", DeviceDetails.getIMEIID());
//            localObject.put("deviceName", DeviceDetails.getDeviceName());
//            localObject.put("brand", DeviceDetails.getBrandName());
//            localObject.put("osVersion", DeviceDetails.getOSVersion());
//            localObject.put("serial", DeviceDetails.getSerialNumber());
//            localObject.put("appVersion", PlatUntil.getInerVersion());
//            WindowManager mWindowManager = (WindowManager) BaseApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
//            DisplayMetrics mDisplayMetrics = new DisplayMetrics();
//            mWindowManager .getDefaultDisplay().getMetrics(mDisplayMetrics);
//            localObject.put("screen", mDisplayMetrics.heightPixels+"x"+mDisplayMetrics.widthPixels);
//            if (AppCheckController.getInstance().getSuportJsonArray()!=null){
//                localObject.put("shopApp",AppCheckController.getInstance().getSuportJsonArray());
//            }
//            if (AppCheckController.getInstance().getCollectJsonArray()!=null){
//                localObject.put("otherApp",AppCheckController.getInstance().getCollectJsonArray());
//            }
//            if(ServiceAccess.model!=null){
//                localObject.put("curShopApp", ServiceAccess.model.getSite());
//            }
//            localObject.put("screenSize", NetStateCollect.getInstance().getScreenSizeOfDevice());
//            localObject.put("appCount", AppCheckController.getInstance().getInstallAppCount());
//            localObject.put("ramSize", NetStateCollect.getInstance().getRamSize());
//            localObject.put("curNetState", NetStateCollect.getInstance().getCurrentNetType());
//          //  Log.e("curNetState", NetStateCollect.getInstance().getScreenSizeOfDevice() + "a" + NetStateCollect.getInstance().getAppCount() + "a" + NetStateCollect.getInstance().getRamSize() + "a" + NetStateCollect.getInstance().getCurrentNetType());
//            localObject.put("appType", ((PinDanApplication) PinDanApplication.getInstance()).getAppType());
//            localObject.put("marketChannel",((PinDanApplication)PinDanApplication.getInstance()).getChannel());
//            localObject.put("mac",mac());
//            localObject.put("gcmToken", PreferceManager.getInsance().getValueBYkey("gcmToken"));
//        } catch (JSONException localJSONException) {
//        }
//        return localObject.toString();
//    }
//
//    public static String mac(){
//        try {
//            WifiManager wifi = (WifiManager) BaseApplication.getInstance().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//            WifiInfo info = wifi.getConnectionInfo();
//            return info.getMacAddress();
//        }catch (Exception e){
//            return "";
//
//        }
//
//    }
//
//    public static String getandroidID() {
//        try {
//            return Settings.Secure.getString(BaseApplication.getInstance().getContentResolver(), "android_id");
//        }catch (Exception e){
//            return Settings.Secure.getString(BaseApplication.getInstance().getContentResolver(), "android_id");
//        }
//
//
//    }
//
//    public static final String PHONE = "phone";
//    public static final String PAD = "pad";
//
//    /**
//     * 获取手机型号
//     *
//     * @return
//     */
//
//    public static String getPhoneModel() {
//        return Build.MODEL;
//    }
//
//    /**
//     * 设备的版本
//     *
//     * @return
//     */
//    public static String deviceVersion() {
//        return Build.VERSION.CODENAME;
//    }
//
//    /**
//     * 判断是手机还是pad
//     *
//     * @return 返回手机还是 pad 如果是手机的话 Common。PHONE 如果是pad的话 Common。PAD
//     */
//    public static String modelName() {
//        return null;
//
//    }
//
//    /**
//     * 获取手机的mac地址 需要 权限 <uses-permission
//     * android:name="android.permission.ACCESS_WIFI_STATE"/>
//     *
//     * @return
//     */
//    public static String macAddress(Context c) {
//        WifiManager wifi = (WifiManager) c
//                .getSystemService(Context.WIFI_SERVICE);
//        WifiInfo info = wifi.getConnectionInfo();
//        return info.getMacAddress();
//    }

    /**
     * 获取 屏幕的尺寸
     *
     * @param c 相应的activity
     * @return 屏幕的尺寸
     */
    public static DisplayMetrics screenSize(Activity c) {
        DisplayMetrics dm = new DisplayMetrics();
        c.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * @param c
     * @return 屏幕的宽
     */
    public static int screenWidth(Activity c) {
        return screenSize(c).widthPixels;
    }

    /**
     * @param c
     * @return 屏幕的高
     */
    public static int screenHeight(Activity c) {
        return screenSize(c).heightPixels;
    }


    /**
     * @param context
     * @return 返回 手机串号
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
//        getUnique(context);
        return imei;
    }

    /**
     * 获取android sdk的 版本
     *
     * @return
     */

    private static int getAndroidSDKVersion() {
        int version = 0;
        try {
            version = Integer.valueOf(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
        }
        return version;
    }

    private static String getAndrodId(Context context) {

        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return m_szAndroidID;
    }

    private static String getImei(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        return imei;
    }


//    /**
//     * 获取唯一标识
//     *
//     * @param context
//     * @return
//     */
//    public static String getDeviceId(Context context) {
//
//        if (getAndroidSDKVersion() == 8) {
//            //当版本是2.2 的时候
//            String imie = getImei(context);
//
//            if (StringTools.isNullOrEmpty(imie)) {
//
//                String deviceid = PreferceManager.getInsance()
//                        .getValueBYkey(context,
//                                "deviceId");
//                if (StringTools.isNullOrEmpty(deviceid)) {
//                    String guidStr = UUID.randomUUID().toString();
//                    PreferceManager.getInsance().saveValueBYkey(guidStr, "deviceId",
//                            context);
//                    return guidStr;
//                }
//                return deviceid;
//            }
//            return imie;
//        }
//        //---当sdk级别 不是 8 的时候返回  androidId
//        return getAndrodId(context);
//    }


    public static String getUnique(Context context) {
//        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//        TelephonyManager tm = (TelephonyManager) context
//                .getSystemService(Context.TELEPHONY_SERVICE);
////        String sn = tm.getSimSerialNumber();
////        String m_szDevIDShort = "35" + //we make this look like a valid IMEI
////
////                Build.BOARD.length()%10 +
////                Build.BRAND.length()%10 +
////                Build.CPU_ABI.length()%10 +
////                Build.DEVICE.length()%10 +
////                Build.DISPLAY.length()%10 +
////                Build.HOST.length()%10 +
////                Build.ID.length()%10 +
////                Build.MANUFACTURER.length()%10 +
////                Build.MODEL.length()%10 +
////                Build.PRODUCT.length()%10 +
////                Build.TAGS.length()%10 +
////                Build.TYPE.length()%10 +
////                Build.USER.length()%10 ; //13 digits
//        String imei = tm.getDeviceId();
//
//        StringBuilder builder = new StringBuilder();
//        if (!StringTools.isNullOrEmpty(m_szAndroidID)) {
//            builder.append(m_szAndroidID);
//        }
//
////        if (!StringTools.isNullOrEmpty(sn)){
////            builder.append(sn);
////        }
//
////        if (!StringTools.isNullOrEmpty(m_szDevIDShort)){
////            builder.append(m_szDevIDShort);
////        }
//
//        if (!StringTools.isNullOrEmpty(imei)) {
//            Logger.e("------");
//            builder.append(imei);
//        }
//
//        if (StringTools.isNullOrEmpty(builder.toString())) {
//            Logger.e("---获取deviceId为空---");
//            return "";
//        }
//
//        Logger.e("------" + builder.toString());
//        String deviceId = getDeviceId(context);

        return "";
    }

    /**
     * @param context
     * @return 返回sim卡串号
     */
    public static String getIMSI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = tm.getSubscriberId();
        return imsi;
    }

    /**
     * 获取系统的版本号
     *
     * @return
     */
    public static String getSystemVersion() {
        String version = Build.VERSION.RELEASE;
        return version;
    }

    /**
     * 获取机型
     *
     * @return
     */
    public static String getModal() {
        String modal = Build.MODEL;
        return modal;
    }

    /**
     * 获取sdk级别代号
     *
     * @return
     */
    public static String getSDKLevel() {
        String sdkLevel = Build.VERSION.SDK;
        return sdkLevel;
    }

    // 滚动条内部Listview解决方案
    public static int lv_high;



    /**
     * @param c 上下文
     * @return 判断wifi是否可用
     */
    public static boolean isWifiable(Context c) {
        boolean isWifiConnect = true;
        ConnectivityManager cm = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // check the networkInfos numbers
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
        for (int i = 0; i < networkInfos.length; i++) {
            if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                if (networkInfos[i].getType() == cm.TYPE_MOBILE) {
                    isWifiConnect = false;
                }
                if (networkInfos[i].getType() == cm.TYPE_WIFI) {
                    isWifiConnect = true;
                }
            }
        }
        return isWifiConnect;
    }



    /**
     * 2g 3g 网络是否可用
     *
     * @param context
     * @return
     */
    public boolean isGprsable(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * ***************************** 运营商判断 *********************************************
     */
    public static final String REGEX_MOBILE = "^(0)?(13[4-9]|15[0-2]|15[7-9]|18(7|8|2)|147)\\d{3}\\d+$";
    public static final String REGEX_TELECOM = "^(0)?(133|153|189|186|185)\\d{3}\\d+$";
    public static final String REGEX_UNICOM = "^(0)?(13[0-2]|15(5|6)|186|145|180|189)\\d{3}\\d+$";
    public static String CHINA_MOBILE_TYPE = "01"; // 移动
    public static String CHINA_UNICOM_TYPE = "02"; // 联通
    public static String CHINA_TELECOM_TYPE = "03"; // 电信
    /******************************** 运营商判断 **********************************************/

    /**
     * 运营商判断
     *
     * @param mobile
     * @return
     */
    public static String getMobileType(String mobile) {
        // 是不是移动
        Pattern mobie = Pattern.compile(REGEX_MOBILE);
        Matcher mobiem = mobie.matcher(mobile);
        if (mobiem.matches()) {
            return CHINA_MOBILE_TYPE;
        } else {
            // 是不是电信
            Pattern telecom = Pattern.compile(REGEX_TELECOM);
            Matcher telecomm = telecom.matcher(mobile);
            if (telecomm.matches()) {
                return CHINA_TELECOM_TYPE;
            } else {
                // 是不是联通
                Pattern unicom = Pattern.compile(REGEX_UNICOM);
                Matcher unicomm = unicom.matcher(mobile);
                if (unicomm.matches()) {
                    return CHINA_UNICOM_TYPE;
                }
            }
        }
        return "-1";
    }

    /**
     * 获取本机的ip地址
     *
     * @return 本机的ip地址
     */
    public static String getLocalIpAddress() {
        String ipaddress = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface inf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = inf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ipaddress = inetAddress.getHostAddress().toString();
                    }
                }

            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ipaddress;
    }



    /**
     * 检查是否安装地图软件
     *
     * @param context
     * @return// com.google.android.apps.maps
     */
    public static boolean installationMapStatus(Context context) {
        if (installPackageCheck(context, "com.autonavi.minimap")
                || installPackageCheck(context, "com.baidu.BaiduMap")) {
            return true;
        }
        return false;
    }

    /**
     * @param context
     * @param packageName 包名
     * @return 是否安装这个包名的 应用
     */
    public static boolean installPackageCheck(Context context,
                                              String packageName) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    packageName, 0);
            return info.versionCode >= 1 ? true : false;
        } catch (Exception e) {
            return false;
        }
    }


}

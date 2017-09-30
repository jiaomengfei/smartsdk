package device;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import system.RuntimePermissionsCompatible;

public class MobileInfoUtils {

	private volatile static MobileInfoUtils mMobileInfoUtils = null;

	public static MobileInfoUtils getSingleton() {
		if (mMobileInfoUtils == null) {
			synchronized (MobileInfoUtils.class) {
				if (mMobileInfoUtils == null) {
					mMobileInfoUtils = new MobileInfoUtils();
				}
			}
		}
		return mMobileInfoUtils;
	}


//	public String getIMEI(Context pContext) {
//		String strIMEI = "000000000000000";
//
//		TelephonyManager phone = (TelephonyManager) pContext
//				.getSystemService("phone");
//		if (phone == null) {
//			return strIMEI;
//		}
//
//		try {
//			if (RuntimePermissionsCompatible.isNeedCheckPermission(pContext)) {
//				if (RuntimePermissionsCompatible.isGranted(pContext,
//						"android.permission.READ_PHONE_STATE")) {
//					strIMEI = phone.getDeviceId();
//				}
//			} else {
//				strIMEI = phone.getDeviceId();
//			}
//		} catch (NameNotFoundException e) {
//			e.printStackTrace();
//		}
//
//		// ??????豸id??????????0??
//		if (TextUtils.isEmpty(strIMEI)) {
//			strIMEI = "000000000000000";
//		}
//
//		return strIMEI;
//	}


//	public String getIMSI(final Context pContext)	  {
//		String strIMSI = "000000000000000";
//
//	    TelephonyManager phone = (TelephonyManager)pContext.getSystemService("phone");
//	    if (phone == null){
//	    	return strIMSI;
//	    }
//
//	    try
//	    {
//	    	if (RuntimePermissionsCompatible.isNeedCheckPermission(pContext)) {
//				if (RuntimePermissionsCompatible.isGranted(pContext,
//						"android.permission.READ_PHONE_STATE")) {
//					strIMSI = phone.getSubscriberId();
//				}
//			} else {
//				strIMSI = phone.getSubscriberId();
//			}
//
//	    }
//	    catch (Throwable t)	    {
//	    	t.printStackTrace();
//	    }
//
//	    if (TextUtils.isEmpty(strIMSI)){
//	    	strIMSI = "000000000000000";
//	    }
//
//	    return strIMSI;
//	  }

	public String getModel()	  {
	    return Build.MODEL;
	  }


	  public String getManufacturer()	  {
	    return Build.MANUFACTURER;
	  }


	public String getSerialno() {
		String serialno = null;

		if (Build.VERSION.SDK_INT >= 9) {
			try {
				Class<?> c = Class.forName("android.os.SystemProperties");
				Method get = c.getMethod("get", new Class[] { String.class,
						String.class });
				serialno = (String) (String) get.invoke(c, new Object[] {
						"ro.serialno", "unknown" });
			} catch (Throwable t) {
			}
		}

		return serialno;
	}

	private String getIPAddressLocal(){
		    try
		    {
		        Enumeration en = NetworkInterface.getNetworkInterfaces();

		        while (en.hasMoreElements()) {
		          NetworkInterface intf = (NetworkInterface)en.nextElement();
		          Enumeration enumIpAddr = intf.getInetAddresses();

		          while (enumIpAddr.hasMoreElements()) {
		            InetAddress inetAddress = (InetAddress)enumIpAddr.nextElement();
		            if ((!inetAddress.isLoopbackAddress()) && (inetAddress instanceof Inet4Address))
		              return inetAddress.getHostAddress();
		          }
		        }
		    }
		    catch (Throwable e)		    {
		    }

		    return "0.0.0.0";
	}


	public String getIPAddress(Context pContext) {

		String strIP = "0.0.0.0";

	    	try {
				if (RuntimePermissionsCompatible.isNeedCheckPermission(pContext)) {
					if (RuntimePermissionsCompatible.isGranted(pContext,
							"android.permission.INTERNET")) {
						strIP = getIPAddressLocal();
					}
				} else {
					strIP = getIPAddressLocal();
				}
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}

	    	return strIP;
	}

	public int getConnectedType(Context pContext) {
		try {
			if (RuntimePermissionsCompatible.isNeedCheckPermission(pContext)) {
				if (RuntimePermissionsCompatible.isGranted(pContext,
						"android.permission.ACCESS_NETWORK_STATE")) {
					return getConnectedTypeLocal(pContext);
				}
			} else {
				return getConnectedTypeLocal(pContext);
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

    	return -1;
	}


	private int getConnectedTypeLocal(Context pContext) {
		ConnectivityManager connectivityManager = (ConnectivityManager) pContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return -1;
		}
		
		NetworkInfo networkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (networkInfo == null) {
			return -1;
		}
		
		return (networkInfo.isAvailable()) ? networkInfo.getType() : -1;
	}
}

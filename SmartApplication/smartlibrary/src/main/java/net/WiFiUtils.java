package net;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WiFiUtils {
	

	public static String getSSID(final Context pContext)  {
	    String strSSID = null;

		WifiManager wifi = (WifiManager)pContext.getSystemService("wifi");
		if (wifi == null) {
			return null;
		}

	    WifiInfo info = wifi.getConnectionInfo();
	    if (info != null){
	      strSSID = info.getSSID().replace("\"", "");
	    }

	    return strSSID;
	  }

	public static String getBssid(final Context pconContext){
		String strBSSID = null;

	    WifiManager wifi = (WifiManager)pconContext.getSystemService("wifi");
	    if (wifi == null) {
			return null;
		}

	    WifiInfo info = wifi.getConnectionInfo();
	    if (info != null){
	      strBSSID = info.getBSSID();
	    }

	    return strBSSID;
	  }


	public String getMacAddress(Context pContext)
	  {
		 String strMAC = null;

	    if (Build.VERSION.SDK_INT >= 23)	    {

	      try	      {
	        java.lang.Process p = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
	        InputStreamReader isr = new InputStreamReader(p.getInputStream());
	        BufferedReader br = new BufferedReader(isr);
	        strMAC = br.readLine();
	        br.close();
	      }
	      catch (Throwable t)
	      {
	    	  strMAC = null;
	      }
	      
	      if (strMAC != null)
	        return strMAC;
	    }
	    
	    WifiManager wifi = (WifiManager)pContext.getSystemService("wifi");
	    if (wifi == null){
	      return null;
	    }
	    
	    WifiInfo info = wifi.getConnectionInfo();
	    if (info != null)	    {
	    	strMAC = info.getMacAddress();
	    }
	    
	    return strMAC;
	  }
}

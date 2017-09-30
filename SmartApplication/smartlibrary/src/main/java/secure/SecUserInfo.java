package secure;

import android.content.Context;

public class SecUserInfo {

	private static final String USERINFO_KEY_NAME = "user_sec_info";
	private static final String USERINFO_KEY_VALUE = "user_sec_value";


	static public String getSecPwd(Context paramContext, String pAccountName){
		SecurePreferences aPreferences = new SecurePreferences(paramContext, USERINFO_KEY_NAME);
		String strKey = USERINFO_KEY_VALUE + pAccountName;
		return  aPreferences.getString(strKey, "");
	}


	static public void updateSecPwd(Context paramContext, String pAccountName, String pAccountPwd){
		SecurePreferences aPreferences = new SecurePreferences(paramContext, USERINFO_KEY_NAME);
		String strKey = USERINFO_KEY_VALUE + pAccountName;
		aPreferences.edit().putString(strKey, pAccountPwd).commit();
	}
	
}

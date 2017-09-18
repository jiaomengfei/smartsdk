package secure;

import android.content.Context;

/**
 * 安全存储用户的信息类，例如密码等信息
 *
 * @author dzyssssss
 *
 * @version 1.1 支持多账户版本
 */
public class SecUserInfo {

	private static final String USERINFO_KEY_NAME = "user_sec_info";
	private static final String USERINFO_KEY_VALUE = "user_sec_value";

	/**
	 * 获取密文密码，在内存中存在，用完及时销毁
	 * 根据用户名查找本地的存储密文
	 *
	 * @return
	 */
	static public String getSecPwd(Context paramContext, String pAccountName){
		SecurePreferences aPreferences = new SecurePreferences(paramContext, USERINFO_KEY_NAME);
		String strKey = USERINFO_KEY_VALUE + pAccountName;
		return  aPreferences.getString(strKey, "");
	}

	/**
	 * 保存密码
	 * 
	 * @param paramSecPwd
	 */
	static public void updateSecPwd(Context paramContext, String pAccountName, String pAccountPwd){
		SecurePreferences aPreferences = new SecurePreferences(paramContext, USERINFO_KEY_NAME);
		String strKey = USERINFO_KEY_VALUE + pAccountName;
		aPreferences.edit().putString(strKey, pAccountPwd).commit();
	}
	
}

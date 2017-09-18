package secure;

import android.content.Context;

/**
 * ��ȫ�洢�û�����Ϣ�࣬�����������Ϣ
 *
 * @author dzyssssss
 *
 * @version 1.1 ֧�ֶ��˻��汾
 */
public class SecUserInfo {

	private static final String USERINFO_KEY_NAME = "user_sec_info";
	private static final String USERINFO_KEY_VALUE = "user_sec_value";

	/**
	 * ��ȡ�������룬���ڴ��д��ڣ����꼰ʱ����
	 * �����û������ұ��صĴ洢����
	 *
	 * @return
	 */
	static public String getSecPwd(Context paramContext, String pAccountName){
		SecurePreferences aPreferences = new SecurePreferences(paramContext, USERINFO_KEY_NAME);
		String strKey = USERINFO_KEY_VALUE + pAccountName;
		return  aPreferences.getString(strKey, "");
	}

	/**
	 * ��������
	 * 
	 * @param paramSecPwd
	 */
	static public void updateSecPwd(Context paramContext, String pAccountName, String pAccountPwd){
		SecurePreferences aPreferences = new SecurePreferences(paramContext, USERINFO_KEY_NAME);
		String strKey = USERINFO_KEY_VALUE + pAccountName;
		aPreferences.edit().putString(strKey, pAccountPwd).commit();
	}
	
}

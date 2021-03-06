package data;

import android.annotation.SuppressLint;

/**
 * 数据类型的转换类
 *
 * @author jmf
 *
 */
public class DateConvertUtil {

	/**
	 * 将byte数组转成hexstring的可打印字符串
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuffer stringBuilder = new StringBuffer("");
		if ((src == null) || (src.length <= 0))
			return null;

		for (int i = 0; i < src.length; ++i) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2)
				stringBuilder.append(0);

			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	/**
	 * 将hexstring转换成byte数组
	 *
	 * @param hexString
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static byte[] hexStringToBytes(String hexString) {
		if ((hexString == null) || (hexString.equals("")))
			return null;

		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; ++i) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[(pos + 1)]));
		}
		return d;
	}
	
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}

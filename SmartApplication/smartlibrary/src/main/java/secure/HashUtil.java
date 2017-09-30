package secure;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import data.DateConvertUtil;


public class HashUtil {


	public static byte[] getSha1(String paramSrcData) throws NoSuchAlgorithmException {
		/**
		 * Note: This loop is needed as workaround for a bug in Android 2.3.
		 * After a failed certificate verification in a previous step the
		 * MessageDigest.getInstance("SHA") call will fail with the
		 * AlgorithmNotSupported exception. But a second try will normally
		 * succeed.
		 */
		MessageDigest md = null;
		for (int i = 0; i < 10; i++) {
			try {
				md = MessageDigest.getInstance("SHA");
				break;
			} catch (Exception e) {
			}
		}

		if (md == null) {
			throw new NoSuchAlgorithmException("�㷨��֧��!");
		}

		byte[] pbData = paramSrcData.getBytes();

		return md.digest(pbData);
	}



	public static String getSha1HexString(String paramSrcData) throws NoSuchAlgorithmException {

		byte[] pbHash = getSha1(paramSrcData);

		return DateConvertUtil.bytesToHexString(pbHash);
	}


	public static byte[] getMd5(String paramSrcData) throws NoSuchAlgorithmException {
		/**
		 * Note: This loop is needed as workaround for a bug in Android 2.3.
		 * After a failed certificate verification in a previous step the
		 * MessageDigest.getInstance("SHA") call will fail with the
		 * AlgorithmNotSupported exception. But a second try will normally
		 * succeed.
		 */
		MessageDigest md = null;
		for (int i = 0; i < 10; i++) {
			try {
				md = MessageDigest.getInstance("MD5");
				break;
			} catch (Exception e) {
			}
		}

		if (md == null) {
			throw new NoSuchAlgorithmException("");
		}

		byte[] pbData = paramSrcData.getBytes();
		return md.digest(pbData);
	}


	public static String getMd5HexString(String paramSrcData) throws NoSuchAlgorithmException {

		byte[] pbHash = getMd5(paramSrcData);
		
		return DateConvertUtil.bytesToHexString(pbHash);
	}

}

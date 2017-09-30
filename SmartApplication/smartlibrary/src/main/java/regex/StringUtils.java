package regex;

/**
 * Created by jmf on 2017/9/25 0025.
 */

public class StringUtils {
    public StringUtils() {
    }

    public static boolean isNotNull(String str) {
        boolean ret = false;
        if(str != null && str.length() > 0) {
            ret = true;
        }

        return ret;
    }

    public static boolean isStrEqual(String str1, String str2) {
        boolean ret = false;
        if(isNotNull(str1) && isNotNull(str2) && str1.equalsIgnoreCase(str2)) {
            ret = true;
        }

        return ret;
    }

    public static boolean isArrayContainInteger(int[] arr, int targetValue) {
        int[] var5 = arr;
        int var4 = arr.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            int s = var5[var3];
            if(targetValue == s) {
                return true;
            }
        }

        return false;
    }

    public static int[] stringToInts(String s) {
        if(!isNotNull(s)) {
            return null;
        } else {
            s.trim();
            s.replace(" ", "");
            int length = s.length();
            int[] n = new int[length];

            for(int i = 0; i < length; ++i) {
                n[i] = Integer.parseInt(s.substring(i, i + 1));
            }

            return n;
        }
    }
}

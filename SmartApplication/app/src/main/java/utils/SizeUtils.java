package utils;



import java.text.DecimalFormat;

import base.SmartAPP;

/**
 * Created by xiaoganggao on 15/8/6.
 */
public class SizeUtils {

    private static final float scale = SmartAPP.getInstance().getResources().getDisplayMetrics().density;

    private static final float scaledDensity = SmartAPP.getInstance().getResources().getDisplayMetrics().scaledDensity;

    public static final int CHINESE = 0x01;
    public static final int NUMBER_OR_CHARACTER = 0x02;


    /**
     * dp转成px
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转成dp
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转成px
     *
     * @param spValue
     * @param type
     * @return
     */
    public static float sp2px(float spValue, int type) {
        switch (type) {
            case CHINESE:
                return spValue * scaledDensity;
            case NUMBER_OR_CHARACTER:
                return spValue * scaledDensity * 10.0f / 18.0f;
            default:
                return spValue * scaledDensity;
        }
    }

    public static DecimalFormat decimalFormat1 = new DecimalFormat("#.0");
    public static DecimalFormat decimalFormat2 = new DecimalFormat("#.00");

    public static String to00(Double money) {
        if (money == 0) {
            return "0" + decimalFormat2.format(money);
        }
        return decimalFormat2.format(money);
    }

    public static String unit(long pxValue) {
        if (pxValue < 10000l) {
            return pxValue + "元";
        } else if (pxValue < 100000000l) {
            return decimalFormat1.format(pxValue / 10000d) + "万";
        } else if (pxValue < 10000000000000l) {
            return decimalFormat1.format(pxValue / 100000000d) + "亿";
        }
        return pxValue + "";
    }
}

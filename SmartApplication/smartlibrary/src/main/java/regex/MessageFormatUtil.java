package regex;

import java.text.DecimalFormat;
import java.text.MessageFormat;

/**
 * Created by jmf on 2017/4/12 0012.
 */

public class MessageFormatUtil {
    private static final String DIALOG_DETAIL_MESSAGE = "　　当前您收款{0}元，渠道为“{1}{2}，手续费率{3}”，请确认。";
    private static final String ITEMS_NULL_MESSAGE = "　　温馨提示：尊敬的用户朋友，由于{0}渠道需进行系统升级维护，所以目前暂不支持该项充值服务。敬请关注系统消息，充值服务恢复时间会进行及时公告。\n　　客服咨询热线：4000008688";

    /**
     * @param money   12元
     * @param typeStr 支付宝
     * @param type    T+1
     * @param feiLv   0.48%
     * @return
     */
    public static String detailMessage(String money, String typeStr, String type, double feiLv) {
        double info = feiLv * 100;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String format = decimalFormat.format(info) + "%";
        return MessageFormat.format(DIALOG_DETAIL_MESSAGE, money, typeStr, type, format);
    }

    public static String itemsNullMessage(String targetName) {
        return MessageFormat.format(ITEMS_NULL_MESSAGE, targetName);
    }
}

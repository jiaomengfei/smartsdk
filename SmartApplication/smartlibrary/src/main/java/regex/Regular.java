package regex;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 * Created by GuoSai on 2016/11/7.
 */

public class Regular {

    public static Matcher matcherPswd(EditText pswd) {
           /*8-20位字母加数字*/
        String regEx_pswd = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
        Pattern pattern_pswd = Pattern.compile(regEx_pswd);
        Matcher matcher_pswd = pattern_pswd.matcher(pswd.getText().toString());
        return matcher_pswd;
    }


    public static Matcher matcherLoginPswd(EditText pswd) {
           /*8-20位字母加数字*/
        String regEx_pswd = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        Pattern pattern_pswd = Pattern.compile(regEx_pswd);
        Matcher matcher_pswd = pattern_pswd.matcher(pswd.getText().toString());
        return matcher_pswd;
    }

    public static Matcher matcherPhone(EditText name) {
         /*手机号码*/
        final String regEx_phone = "^1[3|4|5|7|8][0-9]\\d{8}$";
        Pattern pattern_phone = Pattern.compile(regEx_phone);
        Matcher matcher_phone = pattern_phone.matcher(name.getText().toString().replace(" ", ""));
        return matcher_phone;
    }

    public static Matcher matcherPhone(String s) {
         /*手机号码*/
        final String regEx_phone = "^1[3|4|5|7|8][0-9]\\d{8}$";
        Pattern pattern_phone = Pattern.compile(regEx_phone);
        Matcher matcher_phone = pattern_phone.matcher(s.replace(" ", ""));
        return matcher_phone;
    }

    public static Matcher matcherCode(EditText name) {
        /*统一社会信用代码*/
        String regEx_zuzhi = "^[A-Z0-9]{18}$";
        Pattern pattern_zuzhi = Pattern.compile(regEx_zuzhi);
        Matcher matcher_zuzhi = pattern_zuzhi.matcher(name.getText().toString());
        return matcher_zuzhi;
    }

    public static Matcher matcherCode(String s) {
        /*统一社会信用代码*/
        String regEx_zuzhi = "^[A-Z0-9]{18}$";
        Pattern pattern_zuzhi = Pattern.compile(regEx_zuzhi);
        Matcher matcher_zuzhi = pattern_zuzhi.matcher(s);
        return matcher_zuzhi;
    }

    public static Matcher matcherIDCard(EditText id) {
        /*身份证*/
        String regEx_id = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
        Pattern pattern_id = Pattern.compile(regEx_id);
        Matcher matcher_id = pattern_id.matcher(id.getText().toString());
        return matcher_id;
    }


}

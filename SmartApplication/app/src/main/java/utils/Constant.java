package utils;

import android.content.Context;


/**
 * Created by jmfz on 2017/3/17 0017.
 */

public class Constant {
    public static class SP {
        /**
         * 用户类型 0：个人用户   1：企业用户
         */
        public static final String MEMBER_TYPE = "mem_TYPE";
        /**
         * 用户账户实名状态 0：未实名  1：已实名
         */
        public static final String MEMBER_STATUS = "mem_STATUS";
    }

    public enum REQUEST_CODE {
        /**
         * 服务器强制更新的code
         */
        FORCE_UPDATE_CODE(505),
        /**
         * 退出登录
         */
        LOGOUT_CODE(503),

        /**
         * 弹出对话框的code
         */
        DIALOG_MESSAGE(506), REAL_CHECK(803), RISK_DAY_COUNT(801), RISK_ALL_COUNT(802);

        public int getCode() {
            return code;
        }

        private int code;

        REQUEST_CODE(int code) {
            this.code = code;
        }

    }

//    public enum URL_CRT {
//        SVN("https://svn.sumile.cn/", R.raw.phbj_),
//        JYW101("http://192.168.1.101/", R.raw.phbj_),
//        DALONG("http://192.168.1.239:8080/", R.raw.phbj_),
//        ZHENGSHI("https://ebjyd.poolfee.com/", R.raw.phbj);
//        private String mUrl;
//        private int mCrtId;
//
//        URL_CRT(String url, int crtId) {
//            this.mUrl = url;
//            this.mCrtId = crtId;
//        }
//
//        public String getmUrl() {
//            return mUrl;
//        }
//
//        public int getmCrtId() {
//            return mCrtId;
//        }
//
//    }

    public static class CONFIG {
        public static BuildConfig.URL_CRT getCurrentUrl() {
            return BuildConfig.URL_CRT.URL;
        }

        public static String getAPKName(Context context) {
            return "phbj.apk";
        }

        public static String getPHZXName(Context context) {
            return "phzx.apk";
        }

        public static boolean DEBUG = BuildConfig.DEBUG;

        public static String getBaseUrl() {
            return getCurrentUrl().getmUrl();
        }

        /**
         * @return 返回true表示是https
         */
        public static boolean getRequestType() {
            if (getBaseUrl().startsWith("https")) {
                return true;
            } else {
                return false;
            }
        }
    }
}
